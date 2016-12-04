package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;

/**
 * @author Filipe Bezerra
 */
class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private final SettingsRepository mSettingsRepository;

    HomePresenter(SettingsRepository pSettingsRepository) {
        mSettingsRepository = pSettingsRepository;
    }

    @Override public void attachView(HomeContract.View pView) {
        super.attachView(pView);
        checkInitialAppLaunch();
    }

    private void checkInitialAppLaunch() {
        if (!mSettingsRepository.isInitialDataImportationFlowDone()) {
            getView().navigateToInitialDataImportationFlow();
        }
    }

    @Override public void resume() {
        if (mSettingsRepository.isInitialDataImportationFlowDone() &&
                !mSettingsRepository.isUserLearnedDrawer()) {
            getView().showDrawer();
            mSettingsRepository.doneUserLearnedDrawer();
        }
    }
}