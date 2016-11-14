package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorService;
import br.com.libertsolutions.libertvendas.app.domain.factory.VendedorFactory;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.resources.CommonResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.ValidationError;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Filipe Bezerra
 */
class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View mView;

    private final CommonResourcesRepository mResourcesRepository;

    private final VendedorService mVendedorService;

    private final Repository<Vendedor> mVendedorRepository;

    private final SettingsRepository mSettingsRepository;

    private Subscription mSubscription;

    private boolean mFailed = false;

    LoginPresenter(
            LoginContract.View view, CommonResourcesRepository pCommonResourcesRepository,
            VendedorService pVendedorService, Repository<Vendedor> pVendedorRepository,
            SettingsRepository pSettingsRepository) {
        mView = view;
        mResourcesRepository = pCommonResourcesRepository;
        mVendedorService = pVendedorService;
        mVendedorRepository = pVendedorRepository;
        mSettingsRepository = pSettingsRepository;
        if (mSettingsRepository.hasUsuarioLogado()) {
            mView.resultAsOk(Navigator.RESULT_OK);
        }
    }

    @Override public void initializeView() {
        mView.showIdle();
    }

    @Override public void clickButtonEntrar(boolean pDeviceConnected) {
        mView.hideRequiredMessages();
        mView.showIdle();

        final LoginViewModel viewModel = mView.extractViewModel();

        if (!shownViewModelErrors(viewModel)) {
            if (pDeviceConnected) {
                mSubscription = mVendedorService
                        .get(viewModel.cpfCnpj, viewModel.senha)
                        .flatMap(pVendedorDto -> {
                            if (pVendedorDto.error) {
                                return Observable
                                        .error(ValidationError.newError(pVendedorDto.mensagem));
                            } else {
                                return mVendedorRepository
                                        .save(VendedorFactory.createVendedor(pVendedorDto))
                                        .doOnNext(pVendedor -> mSettingsRepository
                                                .setUsuarioLogado(pVendedor.getIdVendedor()));
                            }
                        })
                        .doOnError(pThrowable -> mFailed = true)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(LoginSubscriber.newLoginSubscriber(mView));
            } else {
                mView.showDeviceNotConnectedError();
            }
        }
    }

    private boolean shownViewModelErrors(LoginViewModel pViewModel) {
        if (pViewModel.hasDefaultValues()) {
            if (!pViewModel.hasSenha()) {
                mView.displayRequiredMessageForFieldSenha();
            }

            if (!pViewModel.hasCpfCnpj()) {
                mView.displayRequiredMessageForFieldCpfCnpj();
            }

            mView.showFeedbackMessage(mResourcesRepository.obtainStringMessageFieldsRequired());
            return true;
        } else {
            return false;
        }
    }

    @Override public void handleEditEntriesTextChanged() {
        if (mFailed) {
            mView.showIdle();
        }
    }

    @Override public void stopWork() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override public void finalizeView() {
        stopWork();
        mView.finishActivity();
    }
}
