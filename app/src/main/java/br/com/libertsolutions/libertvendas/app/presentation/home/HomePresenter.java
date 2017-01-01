package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent.newEvent;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class HomePresenter extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter {

    private final SettingsRepository mSettingsRepository;

    private final VendedorRepository mVendedorRepository;

    private Vendedor mVendedor;

    HomePresenter(final SettingsRepository settingsRepository,
            final VendedorRepository vendedorRepository) {
        mSettingsRepository = settingsRepository;
        mVendedorRepository = vendedorRepository;
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

        LoggedUserEvent event = EventBus.getDefault().getStickyEvent(LoggedUserEvent.class);
        if (event == null) {
            addSubscription(mVendedorRepository.findById(mSettingsRepository.getLoggedInUser())
                    .observeOn(mainThread())
                    .subscribe(
                            vendedor -> {
                                mVendedor = vendedor;
                                continueInitializeView(true);
                            },
                            Timber::e
                    ));
            return;
        }

        mVendedor = event.getVendedor();
        continueInitializeView(false);
    }

    private void continueInitializeView(boolean shouldPostEvent) {
        if (shouldPostEvent) {
            EventBus.getDefault().postSticky(newEvent(mVendedor));
        }
        
        if (!mSettingsRepository.isInitialDataImportationDone()) {
            getView().startDataImportation();
            return;
        }

        List<Empresa> empresas = mVendedor.getEmpresas();
        List<String> nomeEmpresas = new ArrayList<>();
        nomeEmpresas.add(mVendedor.getEmpresaSelecionada().getNome());
        for (Empresa e : empresas) {
            if (mVendedor.getEmpresaSelecionada().getIdEmpresa() != e.getIdEmpresa()) {
                nomeEmpresas.add(e.getNome());
            }
        }

        getView().setupViews(mVendedor.getNome(), nomeEmpresas);
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

    @Override public void finalizeView() {
        clearSubscriptions();
    }
}
