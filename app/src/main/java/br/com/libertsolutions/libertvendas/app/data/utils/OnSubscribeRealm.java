package br.com.libertsolutions.libertvendas.app.data.utils;

import android.support.annotation.NonNull;
import io.realm.Realm;
import io.realm.exceptions.RealmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * {@link Observable.OnSubscribe} for RealmObject subclass that follows Observable contract
 *
 * @author Kirill Boyarshinov
 */
abstract class OnSubscribeRealm<T> implements Observable.OnSubscribe<T> {

    private final List<Subscriber<? super T>> mSubscribers = new ArrayList<>();

    private final AtomicBoolean mCanceled = new AtomicBoolean();

    private final Object mLock = new Object();

    @Override public void call(final Subscriber<? super T> subscriber) {
        synchronized (mLock) {
            boolean canceled = mCanceled.get();
            if (!canceled && !mSubscribers.isEmpty()) {
                subscriber.add(newUnsubscribeAction(subscriber));
                mSubscribers.add(subscriber);
                return;
            } else if (canceled) {
                return;
            }
        }
        subscriber.add(newUnsubscribeAction(subscriber));
        mSubscribers.add(subscriber);

        Realm realm = Realm.getDefaultInstance();
        boolean withError = false;

        T object = null;
        try {
            if (!mCanceled.get()) {
                realm.beginTransaction();
                object = get(realm);
                if (object != null && !mCanceled.get()) {
                    realm.commitTransaction();
                } else {
                    realm.cancelTransaction();
                }
            }
        } catch (RuntimeException e) {
            realm.cancelTransaction();
            sendOnError(new RealmException("Error during transaction.", e));
            withError = true;
        } catch (Error e) {
            realm.cancelTransaction();
            sendOnError(e);
            withError = true;
        }
        if (object != null && !mCanceled.get() && !withError) {
            sendOnNext(object);
        }

        try {
            realm.close();
        } catch (RealmException ex) {
            sendOnError(ex);
            withError = true;
        }
        if (!withError) {
            sendOnCompleted();
        }
        mCanceled.set(false);
    }

    private void sendOnNext(T object) {
        for (int i = 0; i < mSubscribers.size(); i++) {
            Subscriber<? super T> subscriber = mSubscribers.get(i);
            subscriber.onNext(object);
        }
    }

    private void sendOnError(Throwable e) {
        for (int i = 0; i < mSubscribers.size(); i++) {
            Subscriber<? super T> subscriber = mSubscribers.get(i);
            subscriber.onError(e);
        }
    }

    private void sendOnCompleted() {
        for (int i = 0; i < mSubscribers.size(); i++) {
            Subscriber<? super T> subscriber = mSubscribers.get(i);
            subscriber.onCompleted();
        }
    }

    @NonNull private Subscription newUnsubscribeAction(final Subscriber<? super T> subscriber) {
        return Subscriptions.create(() -> {
            synchronized (mLock) {
                mSubscribers.remove(subscriber);
                if (mSubscribers.isEmpty()) {
                    mCanceled.set(true);
                }
            }
        });
    }

    public abstract T get(Realm realm);
}