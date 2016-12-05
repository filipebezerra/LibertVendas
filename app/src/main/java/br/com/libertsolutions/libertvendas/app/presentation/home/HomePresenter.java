package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.login.UsuarioLogadoEvent;
import org.greenrobot.eventbus.EventBus;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Filipe Bezerra
 */
class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private final SettingsRepository mSettingsRepository;

    private final Repository<Vendedor> mVendedorRepository;

    HomePresenter(HomeDependencyContainer pDependencyContainer) {
        mSettingsRepository = pDependencyContainer.getSettingsRepository();
        mVendedorRepository = pDependencyContainer.getVendedorRepository();
    }

    @Override public void attachView(HomeContract.View pView) {
        super.attachView(pView);

        if (!mSettingsRepository.isInitialDataImportationFlowDone()) {
            getView().navigateToInitialDataImportationFlow();
        } else if (!mSettingsRepository.isUserLoggedIn()) {
            //TODO: navigate to login
        } else {
            addSubscription(mVendedorRepository
                    .findById(mSettingsRepository.getLoggedInUser())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(pVendedor -> {
                        Empresa empresaLogada = null;
                        final int idEmpresaLogada = mSettingsRepository.getLoggedInUserCompany();
                        for (Empresa empresa : pVendedor.getEmpresas()) {
                            if (empresa.getIdEmpresa() == idEmpresaLogada) {
                                empresaLogada = empresa;
                                break;
                            }
                        }

                        EventBus.getDefault()
                                .postSticky(UsuarioLogadoEvent.newEvent(pVendedor, empresaLogada));
                        getView().showUsuarioLogado(pVendedor.getNome(), empresaLogada.getNome());
                    })
                    .subscribe());
        }
    }

    @Override public void resume() {
        if (mSettingsRepository.isInitialDataImportationFlowDone() &&
                !mSettingsRepository.isUserLearnedDrawer()) {
            getView().showDrawer();
            mSettingsRepository.doneUserLearnedDrawer();
        }
    }

    @Override public void handlePedidosNavigationItemSelected() {
        if (!getView().isviewingListaPedidos()) {
            getView().navigateToListaPedidos();
        }
    }

    @Override public void handleClientesNavigationItemSelected() {
        if (!getView().isViewingListaClientes()) {
            getView().navigateToListaClientes();
        }
    }

    @Override public void handleProdutosNavigationItemSelected() {
        if (!getView().isViewingListaProdutos()) {
            getView().navigateToListaProdutos();
        }
    }
}