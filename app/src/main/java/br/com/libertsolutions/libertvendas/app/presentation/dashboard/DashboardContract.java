package br.com.libertsolutions.libertvendas.app.presentation.dashboard;

import br.com.libertsolutions.libertvendas.app.domain.pojo.ChartGroupedData;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface DashboardContract {

    interface View extends MvpView {

        void showLoading();

        void hideLoading();

        void showError();

        void hideError();

        void displayOrdersGrouped(List<ChartGroupedData> ordersGrouped);

        void showEmpty();

        void hideEmpty();

        void hideChart();
    }

    interface Presenter extends MvpPresenter<View> {

        void loadOrdersGrouped();

        void retryLoadOrdersGrouped();
    }
}
