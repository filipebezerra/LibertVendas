package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;

/**
 * @author Filipe Bezerra
 */
interface ListaProdutosContract {

    interface View extends MvpView {

        void showLoading();
    }

    interface Presenter extends MvpPresenter<View> {

        void loadListaProdutos();
    }
}
