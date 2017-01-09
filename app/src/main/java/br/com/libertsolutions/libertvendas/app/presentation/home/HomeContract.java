package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface HomeContract {

    interface View extends MvpView {

        void startInitialConfiguration();

        void startLogin();

        void startDataImportation();

        void initializeDrawerHeader(String userName, List<String> companyNames);

        void initializeDrawer(boolean autoSync);

        void initializeViews();

        void initializeDrawerBadgeOrdersCounter(int count);

        boolean getAutoSyncCheckState();

        void setAutoSyncCheckState(boolean autoSync);

        boolean isViewingDashboard();

        void navigateToDashboard();

        boolean isViewingOrders();

        void navigateToOrders();

        boolean isViewingCustomers();

        void navigateToCustomers();

        boolean isViewingProducts();

        void navigateToProducts();

        void navigateToSettings();
    }

    interface Presenter extends MvpPresenter<View> {

        void validateInitialSetup();

        void handleAutoSyncChanged(boolean isChecked);

        void handleViewAfterResulted(int requestCode, int resultCode);

        void handleDashboardNavigationItemSelected();

        void handleOrdersNavigationItemSelected();

        void handleCustomersNavigationItemSelected();

        void handleProductsNavigationItemSelected();

        void handleSettingsNavigationItemSelected();
    }
}
