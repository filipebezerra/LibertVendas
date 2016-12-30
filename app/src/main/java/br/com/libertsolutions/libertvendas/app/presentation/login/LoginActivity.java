package br.com.libertsolutions.libertvendas.app.presentation.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import br.com.libertsolutions.libertvendas.app.DataInjection;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FeedbackHelper;
import butterknife.BindView;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.CircularProgressButton;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class LoginActivity extends LibertVendasActivity implements LoginContract.View {

    @BindView(R.id.container_login) protected CoordinatorLayout mContainerLogin;
    @BindView(R.id.input_layout_cpf_cnpj) protected TextInputLayout mTextInputLayoutCpfCpnj;
    @BindView(R.id.input_layout_senha) protected TextInputLayout mTextInputLayoutSenha;
    @BindView(R.id.button_entrar) protected CircularProgressButton mButtonEntrar;

    private LoginContract.Presenter mPresenter;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_login;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        mPresenter = new LoginPresenter(
                DataInjection.RemoteRepositories.provideVendedorService(this),
                DataInjection.LocalRepositories.provideVendedorRepository(),
                PresentationInjection.provideSettingsRepository(this),
                PresentationInjection.provideConnectivityServices(this));
        super.onCreate(inState);
        mButtonEntrar.setIndeterminateProgressMode(true);
        mPresenter.attachView(this);
    }

    @OnClick(R.id.button_entrar) void onButtonEntrarClicked() {
        mPresenter.startLogin();
    }

    @Override public void hideRequiredMessages() {
        mTextInputLayoutCpfCpnj.setError(null);
        mTextInputLayoutSenha.setError(null);
    }

    @Override public void showIdle() {
        mButtonEntrar.setProgress(CircularProgressButton.IDLE_STATE_PROGRESS);
    }

    @Override public void showOfflineMessage() {
        FeedbackHelper.showOfflineMessage(this,
                (dialog, which) -> mPresenter.startLogin(),
                (dialog, which) -> mPresenter.cancel()
        );
    }

    @Override public LoginInputValues getInputValues() {
        return new LoginInputValues()
                .setCpfCnpj(mTextInputLayoutCpfCpnj.getEditText().getText().toString())
                .setSenha(mTextInputLayoutSenha.getEditText().getText().toString());
    }

    @Override public void displayRequiredMessageForFieldSenha() {
        displayMessageForField(mTextInputLayoutSenha,
                getString(R.string.message_field_senha_required));
    }

    @Override public void displayRequiredMessageForFieldCpfCnpj() {
        displayMessageForField(mTextInputLayoutCpfCpnj,
                getString(R.string.message_field_cpf_cnpj_required));
    }

    @Override public void showFillRequiredFieldsMessage() {
        FeedbackHelper.snackbar(mContainerLogin, getString(R.string.message_fill_required_fields));
    }

    @Override public void blockInputFields() {
        mTextInputLayoutCpfCpnj.clearFocus();
        mTextInputLayoutCpfCpnj.setEnabled(false);
        mTextInputLayoutSenha.clearFocus();
        mTextInputLayoutSenha.setEnabled(false);
    }

    @Override public void showLoading() {
        mButtonEntrar.setProgress(CircularProgressButton.INDETERMINATE_STATE_PROGRESS);
    }

    @Override public void showChooseEmpresaParaLogar(final List<Empresa> empresas) {
        new MaterialDialog.Builder(this)
                .title(R.string.title_dialog_choose_empresa_para_logar)
                .items(empresas)
                .itemsCallbackSingleChoice(-1, (dialog, itemView, which, text) -> {
                    mPresenter.handleChooseEmpresaParaLogar(empresas.get(which));
                    return true;
                })
                .alwaysCallSingleChoiceCallback()
                .show();
    }

    @Override public void displayErrorIndicator() {
        mButtonEntrar.setProgress(CircularProgressButton.ERROR_STATE_PROGRESS);
    }

    @Override public void displayVendedorSemEmpresasError() {
        displayMessageForField(mTextInputLayoutCpfCpnj,
                getString(R.string.message_vendedor_sem_empresas_vinculadas));
        FeedbackHelper.snackbar(mContainerLogin,
                getString(R.string.message_vendedor_sem_empresas_vinculadas));

    }

    private void displayMessageForField(TextInputLayout pInputLayout, String pMessage) {
        pInputLayout.setError(pMessage);
        pInputLayout.getEditText().requestFocus();
    }

    @Override public void unblockInputFields() {
        mTextInputLayoutCpfCpnj.setEnabled(true);
        mTextInputLayoutSenha.setEnabled(true);
    }

    @Override public void showCompletedIndicator() {
        mButtonEntrar.setProgress(CircularProgressButton.SUCCESS_STATE_PROGRESS);
    }

    @Override public void showServerError() {
        FeedbackHelper.showRetryDialogMessage(this, R.string.message_server_error,
                (dialog, which) -> mPresenter.startLogin(),
                (dialog, which) -> mPresenter.cancel());
    }

    @Override public void showNetworkError() {
        FeedbackHelper.showRetryDialogMessage(this, R.string.message_network_error,
                (dialog, which) -> mPresenter.startLogin(),
                (dialog, which) -> mPresenter.cancel());
    }

    @Override public void showUnknownError() {
        FeedbackHelper.showRetryDialogMessage(this, R.string.message_unknown_error,
                (dialog, which) -> mPresenter.startLogin(),
                (dialog, which) -> mPresenter.cancel());
    }

    @Override public void showValidationError(final String message) {
        FeedbackHelper.showMessageDialog(this, message);
    }

    @Override public void finalizeView() {
        finish();
    }

    @Override public void finalizeViewWithSuccess() {
        setResult(Navigator.RESULT_OK);
        finish();
    }

    @Override protected void onStop() {
        mPresenter.detach();
        super.onStop();
    }
}