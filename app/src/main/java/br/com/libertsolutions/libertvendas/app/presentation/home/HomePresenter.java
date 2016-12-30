package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent.newEvent;

/**
 * @author Filipe Bezerra
 */
class HomePresenter extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter {

    private final SettingsRepository mSettingsRepository;

    private LoggedUser mLoggedUser;

    HomePresenter(final SettingsRepository settingsRepository) {
        mSettingsRepository = settingsRepository;
    }

    @Override public void initializeView() {
        if (!mSettingsRepository.isInitialConfigurationDone()) {
            getView().startInitialConfiguration();
            return;
        }

        if (!mSettingsRepository.isUserLoggedIn()) {
            getView().startLogin();
            return;
        }

        if (!mSettingsRepository.isInitialDataImportationDone()) {
            getView().startDataImportation();
            return;
        }

        if (mLoggedUser == null) {
            mLoggedUser = mSettingsRepository.getLoggedInUser();
        }

        getView().setupViews(mLoggedUser.getNomeVendedor(), mLoggedUser.getNomeEmpresa());
        EventBus.getDefault().postSticky(newEvent(mLoggedUser));
    }

    @Override public void handleViewAfterResulted(final int requestCode, final int resultCode) {
        switch (requestCode) {
            case Navigator.REQUEST_SETTINGS:
            case Navigator.REQUEST_LOGIN:
            case Navigator.REQUEST_IMPORTACAO: {
                if (resultCode == Navigator.RESULT_CANCELED) {
                    if (requestCode == Navigator.REQUEST_SETTINGS &&
                            mSettingsRepository.isInitialConfigurationDone()) {
                        return;
                    }

                    getView().finalizeView();
                    return;
                }

                initializeView();
                break;
            }
        }
    }

    @Subscribe(sticky = true) public void onUsuarioLogadoEvent(LoggedUserEvent event) {
        mLoggedUser = event.getLoggedUser();
    }
}
