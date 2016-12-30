package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorService;
import br.com.libertsolutions.libertvendas.app.domain.factory.VendedorFactory;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.android.ConnectivityServices;
import br.com.libertsolutions.libertvendas.app.presentation.exceptions.ValidationError;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

import static br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent.newEvent;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private final VendedorService mVendedorService;

    private final VendedorRepository mVendedorRepository;

    private final SettingsRepository mSettingsRepository;

    private final ConnectivityServices mConnectivityServices;

    private LoginInputValues mInputValues;

    private Vendedor mVendedorLogado;

    LoginPresenter(
            final VendedorService vendedorService,
            final VendedorRepository vendedorRepository,
            final SettingsRepository settingsRepository,
            final ConnectivityServices connectivityServices) {
        mVendedorService = vendedorService;
        mVendedorRepository = vendedorRepository;
        mSettingsRepository = settingsRepository;
        mConnectivityServices = connectivityServices;
    }

    @Override public void startLogin() {
        getView().hideRequiredMessages();
        getView().showIdle();

        if (!mConnectivityServices.isOnline()) {
            getView().showOfflineMessage();
            return;
        }

        if (validateInputValues()) {
            getView().blockInputFields();
            getView().showLoading();
            addSubscription(mVendedorService.get(mInputValues.cpfCnpj, mInputValues.senha)
                    .flatMap(pVendedorDto -> {
                        if (pVendedorDto.error) {
                            return Observable
                                    .error(ValidationError.newError(pVendedorDto.mensagem));
                        } else {
                            mVendedorLogado = VendedorFactory.createVendedor(pVendedorDto);
                            return mVendedorRepository.save(mVendedorLogado);
                        }
                    })
                    .observeOn(mainThread())
                    .subscribe(this::onLoginResult, this::onLoginError, this::onLoginCompleted));
        }
    }

    private boolean validateInputValues() {
        mInputValues = getView().getInputValues();

        if (mInputValues.hasDefaultValues()) {
            if (!mInputValues.hasSenha()) {
                getView().displayRequiredMessageForFieldSenha();
            }

            if (!mInputValues.hasCpfCnpj()) {
                getView().displayRequiredMessageForFieldCpfCnpj();
            }

            getView().showFillRequiredFieldsMessage();
            return false;
        } else {
            return true;
        }
    }

    private void onLoginResult(final Vendedor vendedor) {
        if (vendedor.getEmpresas() != null && !vendedor.getEmpresas().isEmpty()) {
            getView().showChooseEmpresaParaLogar(vendedor.getEmpresas());
        } else {
            getView().displayErrorIndicator();
            getView().unblockInputFields();
            getView().displayVendedorSemEmpresasError();
        }
    }

    @Override public void handleChooseEmpresaParaLogar(final Empresa empresa) {
        mSettingsRepository.setLoggedInUser(mVendedorLogado, empresa);
        EventBus.getDefault().postSticky(newEvent(mSettingsRepository.getLoggedInUser()));
        getView().finalizeViewWithSuccess();
    }

    private void onLoginError(final Throwable error) {
        getView().displayErrorIndicator();
        getView().unblockInputFields();
        if (error instanceof HttpException) {
            getView().showServerError();
        } else if (error instanceof IOException) {
            getView().showNetworkError();
        } else if (error instanceof ValidationError) {
            getView().showValidationError(error.getMessage());
        } else {
            getView().showUnknownError();
        }
    }

    private void onLoginCompleted() {
        getView().showCompletedIndicator();
    }

    @Override public void cancel() {
        clearSubscriptions();
        getView().finalizeView();
    }
}