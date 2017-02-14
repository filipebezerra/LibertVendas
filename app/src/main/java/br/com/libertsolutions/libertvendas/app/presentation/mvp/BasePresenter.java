package br.com.libertsolutions.libertvendas.app.presentation.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static br.com.libertsolutions.libertvendas.app.PresentationInjection.provideEventBus;

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
        if (!provideEventBus().isRegistered(this)) {
            provideEventBus().register(this);
        }
    }

    @Override public void unregisterEventBus() {
        if (provideEventBus().isRegistered(this)) {
            provideEventBus().unregister(this);
        }
    }

    @Override public void detach() {
        mView = null;
        clearSubscriptions();
        unregisterEventBus();
    }

    public void addSubscription(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

    public void clearSubscriptions() {
        mCompositeSubscription.clear();
    }

    public void removeSubscription(Subscription subscription) {
        if (subscription != null) {
            mCompositeSubscription.remove(subscription);
        }
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
