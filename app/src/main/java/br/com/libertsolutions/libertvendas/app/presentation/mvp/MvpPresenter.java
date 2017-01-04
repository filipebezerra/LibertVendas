package br.com.libertsolutions.libertvendas.app.presentation.mvp;

/**
 * @author Filipe Bezerra
 */
public interface MvpPresenter<V extends  MvpView> {

    void attachView(V pView);

    void registerEventBus();

    void unregisterEventBus();

    void detach();
}
