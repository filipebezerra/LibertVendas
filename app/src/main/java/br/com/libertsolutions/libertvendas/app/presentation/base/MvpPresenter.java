package br.com.libertsolutions.libertvendas.app.presentation.base;

/**
 * @author Filipe Bezerra
 */
public interface MvpPresenter<V extends  MvpView> {

    void attachView(V pView);

    void registerForEvents();

    void detach();

    void unregisterForEvents();

}
