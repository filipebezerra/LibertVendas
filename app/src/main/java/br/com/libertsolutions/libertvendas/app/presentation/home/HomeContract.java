package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface HomeContract {

    interface View extends MvpView {

        void setupViews(final String nomeVendedor, final List<String> nomeEmpresas,
                final boolean sincronizarPedidoAutomaticamente);

        void startInitialConfiguration();

        void startLogin();

        void startDataImportation();

        void finalizeView();
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView();

        void handleViewAfterResulted(int requestCode, int resultCode);

        void handleSincronizacaoAutomaticaChanged(boolean isEnabled);

        void finalizeView();
    }
}
