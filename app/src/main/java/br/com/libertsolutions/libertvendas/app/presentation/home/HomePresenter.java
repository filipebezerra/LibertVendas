package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.sync.SyncTaskService;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.Subscriber;
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

    private final PedidoRepository mPedidoRepository;

    private Vendedor mVendedor;

    HomePresenter(
            final SettingsRepository settingsRepository,
            final VendedorRepository vendedorRepository, final PedidoRepository pedidoRepository) {
        mSettingsRepository = settingsRepository;
        mVendedorRepository = vendedorRepository;
        mPedidoRepository = pedidoRepository;
    }

    @Override public void validateInitialSetup() {
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
                                EventBus.getDefault().postSticky(newEvent(mVendedor));

                                if (!mSettingsRepository.isInitialDataImportationDone()) {
                                    getView().startDataImportation();
                                    return;
                                }

                                initializeView();
                            },
                            Timber::e
                    ));
            return;
        }

        mVendedor = event.getVendedor();

        if (!mSettingsRepository.isInitialDataImportationDone()) {
            getView().startDataImportation();
            return;
        }

        initializeView();
    }

    private void initializeView() {
        List<Empresa> empresas = mVendedor.getEmpresas();
        List<String> nomeEmpresas = new ArrayList<>();
        nomeEmpresas.add(mVendedor.getEmpresaSelecionada().getNome());
        for (Empresa e : empresas) {
            if (mVendedor.getEmpresaSelecionada().getIdEmpresa() != e.getIdEmpresa()) {
                nomeEmpresas.add(e.getNome());
            }
        }

        Settings settings = mSettingsRepository.loadSettings();
        getView().initializeDrawerHeader(mVendedor.getNome(), nomeEmpresas);
        getView().initializeDrawer(settings.isAutoSync());
        getView().initializeViews();

        addSubscription(mPedidoRepository.findAll()
                .observeOn(mainThread())
                .subscribe(new Subscriber<List<Pedido>>() {
                    @Override public void onError(final Throwable e) {
                        Timber.e(e, "Could not find all orders");
                    }

                    @Override public void onNext(final List<Pedido> pedidos) {
                        getView().initializeDrawerBadgeOrdersCounter(pedidos.size());
                    }

                    @Override public void onCompleted() {}
                }));

        if (mSettingsRepository.loadSettings().isAutoSync()) {
            SyncTaskService.schedule(PresentationInjection.provideContext(),
                    SyncTaskService.SYNC_CUSTOMERS);
        }
    }

    @Override public void handleAutoSyncChanged(final boolean isChecked) {
        mSettingsRepository.setAutoSync(isChecked);
    }

    @Override public void handleViewAfterResulted(final int requestCode, final int resultCode) {
        switch (requestCode) {
            case Navigator.REQUEST_SETTINGS: {
                Settings settings = mSettingsRepository.loadSettings();
                if (getView().getAutoSyncCheckState() != settings.isAutoSync()) {
                    getView().setAutoSyncCheckState(settings.isAutoSync());
                }
                break;
            }
        }
    }

    @Override public void handleOrdersNavigationItemSelected() {
        if (!getView().isViewingOrders()) {
            getView().navigateToOrders();
        }
    }

    @Override public void handleCustomersNavigationItemSelected() {
        if (!getView().isViewingCustomers()) {
            getView().navigateToCustomers();
        }
    }

    @Override public void handleProductsNavigationItemSelected() {
        if (!getView().isViewingProducts()) {
            getView().navigateToProducts();
        }
    }

    @Override public void handleSettingsNavigationItemSelected() {
        getView().navigateToSettings();
    }
}
