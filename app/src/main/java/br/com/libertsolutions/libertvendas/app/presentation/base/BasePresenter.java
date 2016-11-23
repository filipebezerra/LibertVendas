package br.com.libertsolutions.libertvendas.app.presentation.base;

import org.greenrobot.eventbus.EventBus;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Filipe Bezerra
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mView;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override public void attachView(V pView) {
        mView = pView;
    }

    @Override public void registerForEvents() {
        EventBus.getDefault().register(this);
    }

    @Override public void unregisterForEvents() {
        EventBus.getDefault().unregister(this);
    }

    @Override public void detach() {
        mView = null;
        mCompositeSubscription.clear();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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

    public void addSubscription(Subscription pSubscription) {
        mCompositeSubscription.add(pSubscription);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before "
                    + "requesting data to the Presenter");
        }
    }

}
