package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.util.FeedbackHelper;
import butterknife.BindView;
import butterknife.OnItemSelected;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

/**
 * @author Filipe Bezerra
 */
public class ClienteActivity extends LibertVendasActivity implements ClienteContract.View {

    public static final String EXTRA_FROM_HOME
            = ClienteActivity.class.getSimpleName() + ".extraFromHome";

    public static final String EXTRA_RESULT_NEW_CLIENTE =
            ClienteActivity.class.getSimpleName()+".extraResultNewCliente";

    @BindView(R.id.container_cliente) protected CoordinatorLayout mContainerCliente;
    @BindView(R.id.spinner_tipo_pessoa) protected MaterialSpinner mSpinnerTipoPessoa;
    @BindView(R.id.spinner_uf) protected MaterialSpinner mSpinnerEstado;
    @BindView(R.id.input_layout_cpf_ou_cnpj) protected TextInputLayout mInputLayoutCpfCnpj;
    @BindView(R.id.edit_text_cpf_ou_cnpj) protected EditText mEditTextCpfCnpj;
    @BindView(R.id.input_layout_nome_cliente) protected TextInputLayout mInputLayoutNome;
    @BindView(R.id.edit_text_nome_cliente) protected EditText mEditTextNome;
    @BindView(R.id.edit_text_email) protected EditText mEditTextEmail;
    @BindView(R.id.edit_text_logradouro) protected EditText mEditTextLogradouro;
    @BindView(R.id.edit_text_numero) protected EditText mEditTextNumero;
    @BindView(R.id.edit_text_bairro) protected EditText mEditTextBairro;
    @BindView(R.id.input_layout_cidade) protected TextInputLayout mInputLayoutCidade;
    @BindView(R.id.edit_text_cidade) protected EditText mEditTextCidade;
    @BindView(R.id.edit_text_cep) protected EditText mEditTextCep;
    @BindView(R.id.edit_text_complemento) protected EditText mEditTextComplemento;
    @BindView(R.id.edit_text_telefone) protected EditText mEditTextTelefone;
    @BindView(R.id.edit_text_celular) protected EditText mEditTextCelular;

    private ClienteContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ClientePresenter(this,
                Injection.provideClienteRepository(this),
                Injection.provideClienteResourcesRepository(this));
        mPresenter.initializeView();
        extractExtras();
    }

    private void extractExtras() {
        final Bundle extras = getIntent().getExtras();
        if (extras.containsKey(EXTRA_FROM_HOME)) {
            if (extras.getBoolean(EXTRA_FROM_HOME)) {
                setAsInitialFlowActivity();
            } else {
                setAsSubActivity();
            }
        }
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_cliente;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            mPresenter.clickActionSave();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void showTiposPessoa(List<String> pTiposPessoa) {
        ArrayAdapter<String> adapterTiposPessoa = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, pTiposPessoa);
        adapterTiposPessoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTipoPessoa.setAdapter(adapterTiposPessoa);
    }

    @Override
    public void showEstados(List<String> pEstados) {
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, pEstados);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerEstado.setAdapter(adapterEstados);
    }

    @Override
    public ClienteViewModel extractViewModel() {
        return new ClienteViewModel()
                .setTipoPessoa(mSpinnerTipoPessoa.getSelectedItemPosition())
                .setCpfCnpj(mEditTextCpfCnpj.getText().toString())
                .setNome(mEditTextNome.getText().toString())
                .setEmail(mEditTextEmail.getText().toString())
                .setEndereco(mEditTextLogradouro.getText().toString())
                .setNumero(mEditTextNumero.getText().toString())
                .setBairro(mEditTextBairro.getText().toString())
                .setCidade(mEditTextCidade.getText().toString())
                .setEstado(mSpinnerEstado.getSelectedItemPosition())
                .setCep(mEditTextCep.getText().toString())
                .setComplemento(mEditTextComplemento.getText().toString())
                .setTelefone(mEditTextTelefone.getText().toString())
                .setCelular(mEditTextCelular.getText().toString())
                ;
    }

    @Override
    public void showViewsForPessoaFisica(int pCpfLenght) {
        mEditTextCpfCnpj.setText("");
        mEditTextCpfCnpj.setFilters(new InputFilter[] { new InputFilter.LengthFilter(pCpfLenght)});
        mEditTextCpfCnpj.setFocusable(true);
        mEditTextCpfCnpj.setFocusableInTouchMode(true);
    }

    @Override
    public void showViewsForPessoaJuridica(int pCnpjLenght) {
        mEditTextCpfCnpj.setText("");
        mEditTextCpfCnpj.setFilters(new InputFilter[] { new InputFilter.LengthFilter(pCnpjLenght)});
        mEditTextCpfCnpj.setFocusable(true);
        mEditTextCpfCnpj.setFocusableInTouchMode(true);
    }

    @Override
    public void removeFocusOnFieldCpfCnpj() {
        mEditTextCpfCnpj.setFocusable(false);
        mEditTextCpfCnpj.setFocusableInTouchMode(false);
    }

    @Override
    public void hideRequiredMessages() {
        mSpinnerTipoPessoa.setError(null);
        mInputLayoutCpfCnpj.setError(null);
        mInputLayoutNome.setError(null);
        mSpinnerEstado.setError(null);
        mInputLayoutCidade.setError(null);
    }

    @Override
    public void displayRequiredMessageForFieldCidade() {
        displayRequiredMessageForField(mInputLayoutCidade, mEditTextCidade);
    }

    @Override
    public void displayRequiredMessageForFieldEstado() {
        displayRequiredMessageForField(mSpinnerEstado);
    }

    @Override
    public void displayRequiredMessageForFieldNome() {
        displayRequiredMessageForField(mInputLayoutNome, mEditTextNome);
    }

    @Override
    public void displayRequiredMessageForFieldTipoPessoa() {
        displayRequiredMessageForField(mSpinnerTipoPessoa);
    }

    @Override
    public void displayRequiredMessageForFieldCpfCnpj() {
        displayRequiredMessageForField(mInputLayoutCpfCnpj, mEditTextCpfCnpj);
    }

    @Override
    public void showFeedbackMessage(String pMessage) {
        if (!TextUtils.isEmpty(pMessage)) {
            FeedbackHelper.snackbar(mContainerCliente, pMessage);
        }
    }

    @Override
    public void resultNewCliente(Cliente newCliente) {
        Intent extras = new Intent()
                .putExtra(EXTRA_RESULT_NEW_CLIENTE, newCliente);
        setResult(RESULT_OK, extras);
        finish();
    }

    @Override
    public void showExitViewQuestion() {
        FeedbackHelper.showQuestionDialog(this, R.string.exit_and_cancel_question,
                (dialog, which) -> mPresenter.finalizeView(), null);
    }

    @Override
    public void showFocusOnFieldCpfCnpj() {
        mEditTextCpfCnpj.requestFocus();
    }

    private void displayRequiredMessageForField(MaterialSpinner pMaterialSpinner) {
        pMaterialSpinner.setError(getString(R.string.error_campo_requerido));
        pMaterialSpinner.requestFocus();
    }

    private void displayRequiredMessageForField(TextInputLayout pInputLayout, View pEditText) {
        pInputLayout.setError(getString(R.string.error_campo_requerido));
        pEditText.requestFocus();
    }

    @OnItemSelected(R.id.spinner_tipo_pessoa) void onItemSelectedSpinnerTipoPessoa(int position) {
        mPresenter.clickSelectTipoPessoa();
    }

    @Override
    public void onBackPressed() {
        mPresenter.handleBackPressed();
    }
}
