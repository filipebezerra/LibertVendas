package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface ImportacaoContract {

    interface View extends MvpView {

        void showSyncItems(List<Empresa> syncItems);

        void showSyncCitiesStarted();

        void showSyncCitiesDone();

        void showSyncDone(Empresa syncItem);

        void showSyncCitiesError();

        void showSyncError(Empresa syncItem);

        void showOfflineMessage();

        void hideLoadingWithSuccess();

        void showSuccessMessage();

        void showMenu();

        void hideLoadingWithFail();

        void showServerError();

        void showNetworkError();

        void showUnknownError();

        void finalizeView();

        void navigateToHome();
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView();

        boolean handleMenuVisibility();

        void handleActionDone();

        void startSync();

        void handleAnimationEnd(boolean success);

        void cancel();
    }
}
