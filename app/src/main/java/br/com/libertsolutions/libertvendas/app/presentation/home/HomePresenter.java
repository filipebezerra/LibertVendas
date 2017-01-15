package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.os.Build;
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
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
        List<IProfile> profiles = new ArrayList<>();
        final Empresa empresaSelecionada = mVendedor.getEmpresaSelecionada();
        profiles.add(new ProfileDrawerItem()
                .withName(mVendedor.getNome())
                .withEmail(empresaSelecionada.getNome())
                .withIdentifier(empresaSelecionada.getIdEmpresa()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            profiles.addAll(mVendedor.getEmpresas()
                    .stream()
                    .filter(empresa -> empresa.getIdEmpresa() != empresaSelecionada.getIdEmpresa())
                    .map(empresa -> new ProfileDrawerItem()
                            .withName(mVendedor.getNome())
                            .withEmail(empresa.getNome())
                            .withIdentifier(empresa.getIdEmpresa()))
                    .collect(Collectors.toList()));
        } else {
            for (Empresa empresa : mVendedor.getEmpresas()) {
                if (empresa.getIdEmpresa() != empresaSelecionada.getIdEmpresa()) {
                    profiles.add(new ProfileDrawerItem()
                            .withName(mVendedor.getNome())
                            .withEmail(empresa.getNome())
                            .withIdentifier(empresa.getIdEmpresa()));
                }
            }
        }
        getView().initializeDrawerHeader(profiles);

        Settings settings = mSettingsRepository.loadSettings();
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
            SyncTaskService.schedule(PresentationInjection.provideContext());
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

    @Override public void handleDashboardNavigationItemSelected() {
        if (!getView().isViewingDashboard()) {
            getView().navigateToDashboard();
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

    @Override public void handleProfileChanged(final IProfile profile, final boolean current) {
        if (!current) {
            for (Empresa empresa : mVendedor.getEmpresas()) {
                if (empresa.getIdEmpresa() == profile.getIdentifier()) {
                    changeCompanySelected(empresa);
                    break;
                }
            }
        }
    }

    private void changeCompanySelected(Empresa companySelected) {
        Vendedor vendedorWithAnotherCompany = Vendedor.selectCompany(mVendedor, companySelected);
        addSubscription(mVendedorRepository.save(vendedorWithAnotherCompany)
                .observeOn(mainThread())
                .subscribe(
                        new Subscriber<Vendedor>() {
                            @Override public void onError(final Throwable e) {
                                Timber.e(e, "Could not save changed company selected");
                            }

                            @Override public void onNext(final Vendedor vendedor) {
                                mVendedor = vendedor;
                            }

                            @Override public void onCompleted() {
                                EventBus.getDefault().postSticky(newEvent(mVendedor));
                            }
                        }
                )
        );
    }
}
