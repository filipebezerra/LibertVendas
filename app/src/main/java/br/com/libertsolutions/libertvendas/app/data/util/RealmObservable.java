package br.com.libertsolutions.libertvendas.app.data.util;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Helper class that simplifies usage newCliente these functions providing static methods with Func1<Realm,
 * T> as argument.
 *
 * @author Kirill Boyarshinov
 */
public final class RealmObservable {
    private RealmObservable() {
    }

    public static <E extends RealmModel> Observable<E> object(Context context,
            final Func1<Realm, E> function) {
        return Observable
                .create(
                        new OnSubscribeRealm<E>(context) {
                            @Override
                            public E get(Realm realm) {
                                return function.call(realm);
                            }
                        })
                .subscribeOn(Schedulers.io());
    }

    public static <E extends RealmModel> Observable<RealmList<E>> list(Context context,
            final Func1<Realm, RealmList<E>> function) {
        return Observable
                .create(
                        new OnSubscribeRealm<RealmList<E>>(context) {
                            @Override
                            public RealmList<E> get(Realm realm) {
                                return function.call(realm);
                            }
                        })
                .subscribeOn(Schedulers.io());
    }

    public static <E extends RealmModel> Observable<RealmResults<E>> results(Context context,
            final Func1<Realm, RealmResults<E>> function) {
        return Observable
                .create(
                        new OnSubscribeRealm<RealmResults<E>>(context) {
                            @Override
                            public RealmResults<E> get(Realm realm) {
                                return function.call(realm);
                            }
                        })
                .subscribeOn(Schedulers.io());
    }
}