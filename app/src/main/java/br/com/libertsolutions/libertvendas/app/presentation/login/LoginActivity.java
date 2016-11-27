package br.com.libertsolutions.libertvendas.app.presentation.login;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.util.FeedbackHelper;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.CircularProgressButton;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.R.string.importacao;
import static br.com.libertsolutions.libertvendas.app.R.string.message_network_error;
import static br.com.libertsolutions.libertvendas.app.R.string.message_unknown_error;
import static br.com.libertsolutions.libertvendas.app.presentation.util.AndroidUtils.isDeviceConnected;

/**
 * @author Filipe Bezerra
 */
public class LoginActivity extends LibertVendasActivity implements LoginContract.View {

    @BindView(R.id.container_login) protected CoordinatorLayout mContainerLogin;
    @BindView(R.id.input_layout_cpf_cnpj) protected TextInputLayout mTextInputLayoutCpfCpnj;
    @BindView(R.id.input_layout_senha) protected TextInputLayout mTextInputLayoutSenha;
    @BindView(R.id.button_entrar) protected CircularProgressButton mButtonEntrar;

    private LoginContract.Presenter mPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new LoginPresenter(
                Injection.provideCommonResourcesRepository(this),
                Injection.provideVendedorService(this),
                Injection.provideVendedorRepository(),
                Injection.provideSettingsRepository(this));
        super.onCreate(savedInstanceState);

        mButtonEntrar.setIndeterminateProgressMode(true);
        mPresenter.attachView(this);
    }

    @Override protected int provideContentViewResource() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.button_entrar) void onClickButtonEntrar() {
        requestLoginToPresenter();
    }

    @Override public LoginViewModel extractViewModel() {
        return new LoginViewModel()
                .setCpfCnpj(mTextInputLayoutCpfCpnj.getEditText().getText().toString())
                .setSenha(mTextInputLayoutSenha.getEditText().getText().toString());
    }

    @Override public void hideRequiredMessages() {
        mTextInputLayoutCpfCpnj.setError(null);
        mTextInputLayoutSenha.setError(null);
    }

    @Override public void displayRequiredMessageForFieldCpfCnpj() {
        displayRequiredMessageForField(mTextInputLayoutCpfCpnj);
    }

    @Override public void displayRequiredMessageForFieldSenha() {
        displayRequiredMessageForField(mTextInputLayoutSenha);
    }

    @Override public void displayValidationError(String pMessage) {
        displayMessageForField(mTextInputLayoutCpfCpnj, pMessage);
        displayMessageForField(mTextInputLayoutSenha, pMessage);
    }

    private void displayRequiredMessageForField(TextInputLayout pInputLayout) {
        displayMessageForField(pInputLayout, getString(R.string.error_campo_requerido));
    }

    private void displayMessageForField(TextInputLayout pInputLayout, String pMessage) {
        pInputLayout.setError(pMessage);
        pInputLayout.getEditText().requestFocus();
    }

    @Override public void showFeedbackMessage(String pMessage) {
        if (!TextUtils.isEmpty(pMessage)) {
            FeedbackHelper.snackbar(mContainerLogin, pMessage);
        }
    }

    @Override public void showServerError() {
        FeedbackHelper.showMessageDialog(this,
                getString(R.string.message_server_error, getString(importacao)));
    }

    @Override public void showNetworkError() {
        FeedbackHelper.showMessageDialog(this,
                getString(message_network_error, getString(importacao)));
    }

    @Override public void showUnknownError() {
        FeedbackHelper.showMessageDialog(this,
                getString(message_unknown_error, getString(importacao)));
    }

    @Override public void showIdle() {
        mButtonEntrar.setProgress(CircularProgressButton.IDLE_STATE_PROGRESS);
    }

    @Override public void showLoading() {
        mButtonEntrar.setProgress(CircularProgressButton.INDETERMINATE_STATE_PROGRESS);
    }

    @Override public void blockEditEntries() {
        mTextInputLayoutCpfCpnj.clearFocus();
        mTextInputLayoutCpfCpnj.setEnabled(false);
        mTextInputLayoutSenha.clearFocus();
        mTextInputLayoutSenha.setEnabled(false);
    }

    @Override public void unblockEditEntries() {
        mTextInputLayoutCpfCpnj.setEnabled(true);
        mTextInputLayoutSenha.setEnabled(true);
    }

    @Override public void showErrorIndicator() {
        mButtonEntrar.setProgress(CircularProgressButton.ERROR_STATE_PROGRESS);
    }

    @Override public void showDeviceNotConnectedError() {
        FeedbackHelper.showOfflineMessage(this,
                (dialog, which) -> requestLoginToPresenter(), (dialog, which) -> mPresenter.finalizeView()
        );
    }

    private void requestLoginToPresenter() {
        mPresenter.clickButtonEntrar(isDeviceConnected(this));
    }

    @Override public void showCompletedIndicator() {
        mButtonEntrar.setProgress(CircularProgressButton.SUCCESS_STATE_PROGRESS);
    }

    @Override public void showChooseEmpresaParaLogar(List<Empresa> pEmpresas) {
        new MaterialDialog.Builder(this)
                .title(R.string.title_dialog_choose_empresa_para_logar)
                .items(pEmpresas)
                .itemsCallbackSingleChoice(-1, (dialog, itemView, which, text) -> {
                    mPresenter.clickChooseEmpresaParaLogar(pEmpresas.get(which));
                    return true;
                })
                .alwaysCallSingleChoiceCallback()
                .show();
    }

    @OnTextChanged({ R.id.edit_text_cpf_cnpj, R.id.edit_text_senha }) void onTextChanged() {
        mPresenter.handleEditEntriesTextChanged();
    }

    @Override public void resultAsOk(int resultCode) {
        setResult(resultCode);
        navigate().toImportacao();
    }

    @Override public void finishActivity() {
        finish();
    }

}
