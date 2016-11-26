package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorService;
import br.com.libertsolutions.libertvendas.app.domain.factory.VendedorFactory;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.resources.CommonResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.ValidationError;
import org.greenrobot.eventbus.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Filipe Bezerra
 */
class LoginPresenter extends BasePresenter<LoginContract.View> 
        implements LoginContract.Presenter {

    private final CommonResourcesRepository mResourcesRepository;

    private final VendedorService mVendedorService;

    private final Repository<Vendedor> mVendedorRepository;

    private final SettingsRepository mSettingsRepository;

    private Vendedor mVendedor;

    private boolean mFailed = false;

    LoginPresenter(
            CommonResourcesRepository pCommonResourcesRepository,
            VendedorService pVendedorService, Repository<Vendedor> pVendedorRepository,
            SettingsRepository pSettingsRepository) {
        mResourcesRepository = pCommonResourcesRepository;
        mVendedorService = pVendedorService;
        mVendedorRepository = pVendedorRepository;
        mSettingsRepository = pSettingsRepository;
    }

    @Override
    public void attachView(LoginContract.View pView) {
        super.attachView(pView);
        if (mSettingsRepository.hasUsuarioLogado()) {
            addSubscription(mVendedorRepository
                    .findById(mSettingsRepository.getUsuarioLogado())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(pVendedor -> {
                        Empresa empresaLogada = null;
                        final int idEmpresaLogada = mSettingsRepository.getEmpresaLogada();
                        for (Empresa empresa : pVendedor.getEmpresas()) {
                            if (empresa.getIdEmpresa() == idEmpresaLogada) {
                                empresaLogada = empresa;
                                break;
                            }
                        }

                        if (empresaLogada == null) {
                            getView().displayValidationError(
                                    mResourcesRepository
                                            .obtainStringMessageVendedorSemEmpresaLogada());
                            getView().showFeedbackMessage(
                                    mResourcesRepository
                                            .obtainStringMessageVendedorSemEmpresaLogada());
                            return;
                        }

                        EventBus.getDefault()
                                .postSticky(UsuarioLogadoEvent.newEvent(pVendedor, empresaLogada));
                        getView().resultAsOk(Navigator.RESULT_OK);
                    })
                    .subscribe());
            return;
        }

        getView().showIdle();
    }

    @Override public void clickButtonEntrar(boolean pDeviceConnected) {
        getView().hideRequiredMessages();
        getView().showIdle();

        final LoginViewModel viewModel = getView().extractViewModel();

        if (!shownViewModelErrors(viewModel)) {
            if (pDeviceConnected) {
                addSubscription(mVendedorService
                        .get(viewModel.cpfCnpj, viewModel.senha)
                        .flatMap(pVendedorDto -> {
                            if (pVendedorDto.error) {
                                return Observable
                                        .error(ValidationError.newError(pVendedorDto.mensagem));
                            } else {
                                mVendedor = VendedorFactory.createVendedor(pVendedorDto);
                                return mVendedorRepository.save(mVendedor);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(pThrowable -> mFailed = true)
                        .doOnNext(pVendedor -> {
                            if (pVendedor.getEmpresas() != null &&
                                    !pVendedor.getEmpresas().isEmpty()) {
                                getView().showChooseEmpresaParaLogar(pVendedor.getEmpresas());
                            } else {
                                getView().showErrorIndicator();
                                getView().unblockEditEntries();
                                getView().displayValidationError(
                                        mResourcesRepository
                                                .obtainStringMessageVendedorSemEmpresasVinculadas());
                                getView().showFeedbackMessage(
                                        mResourcesRepository
                                                .obtainStringMessageVendedorSemEmpresasVinculadas());
                            }
                        })
                        .subscribe(LoginSubscriber.newLoginSubscriber(getView())));
            } else {
                getView().showDeviceNotConnectedError();
            }
        }
    }

    private boolean shownViewModelErrors(LoginViewModel pViewModel) {
        if (pViewModel.hasDefaultValues()) {
            if (!pViewModel.hasSenha()) {
                getView().displayRequiredMessageForFieldSenha();
            }

            if (!pViewModel.hasCpfCnpj()) {
                getView().displayRequiredMessageForFieldCpfCnpj();
            }

            getView().showFeedbackMessage(mResourcesRepository.obtainStringMessageFieldsRequired());
            return true;
        } else {
            return false;
        }
    }

    @Override public void handleEditEntriesTextChanged() {
        if (mFailed) {
            getView().showIdle();
        }
    }

    @Override public void clickChooseEmpresaParaLogar(Empresa pEmpresa) {
        mSettingsRepository.setUsuarioLogado(mVendedor.getIdVendedor());
        mSettingsRepository.setEmpresaLogada(pEmpresa.getIdEmpresa());
        EventBus.getDefault().postSticky(UsuarioLogadoEvent.newEvent(mVendedor, pEmpresa));
        getView().resultAsOk(Navigator.RESULT_OK);
    }

    @Override public void finalizeView() {
        getView().finishActivity();
    }

}
