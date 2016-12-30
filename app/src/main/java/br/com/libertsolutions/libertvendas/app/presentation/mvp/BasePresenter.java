package br.com.libertsolutions.libertvendas.app.presentation.mvp;

import org.greenrobot.eventbus.EventBus;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Filipe Bezerra
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mView;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override public void attachView(final V view) {
        mView = view;
    }

    @Override public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Override public void detach() {
        mView = null;
        mCompositeSubscription.clear();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void addSubscription(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

    public void clearSubscriptions() {
        mCompositeSubscription.clear();
    }

    public V getView() {
        return mView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before "
                    + "requesting data to the Presenter");
        }
    }
}
