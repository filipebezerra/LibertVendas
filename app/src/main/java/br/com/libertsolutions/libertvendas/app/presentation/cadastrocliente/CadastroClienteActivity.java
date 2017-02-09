package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TipoPessoa;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.utils.AndroidUtils;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FeedbackHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

import static br.com.libertsolutions.libertvendas.app.DataInjection.LocalRepositories.provideCidadeRepository;
import static br.com.libertsolutions.libertvendas.app.DataInjection.LocalRepositories.provideClienteRepository;
import static br.com.libertsolutions.libertvendas.app.DataInjection.LocalRepositories.provideEstadoRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.resources.ResourcesRepositories.getCadastroClienteResources;
import static butterknife.OnItemSelected.Callback.NOTHING_SELECTED;

/**
 * @author Filipe Bezerra
 */
public class CadastroClienteActivity extends LibertVendasActivity
        implements CadastroClienteContract.View {

    public static final String EXTRA_CLIENTE_EDICAO
            = CadastroClienteActivity.class.getSimpleName() + ".extraClienteEdicao";

    public static final String RESULT_CLIENTE_EDITADO
            = CadastroClienteActivity.class.getSimpleName() + ".resultClienteEditado";

    @BindView(R.id.spinner_tipo_pessoa) protected MaterialSpinner mTiposPessoaSpinner;
    @BindView(R.id.input_layout_cpf_ou_cnpj) protected TextInputLayout mCpfOuCnpjInputLayout;
    @BindView(R.id.input_layout_nome_cliente) protected TextInputLayout mNomeInputLayout;
    @BindView(R.id.input_layout_email) protected TextInputLayout mEmailInputLayout;
    @BindView(R.id.input_layout_endereco) protected TextInputLayout mEnderecoInputLayout;
    @BindView(R.id.input_layout_numero) protected TextInputLayout mNumeroInputLayout;
    @BindView(R.id.input_layout_bairro) protected TextInputLayout mBairroInputLayout;
    @BindView(R.id.spinner_estados) protected MaterialSpinner mEstadosSpinner;
    @BindView(R.id.spinner_cidades) protected MaterialSpinner mCidadesSpinner;
    @BindView(R.id.input_layout_cep) protected TextInputLayout mCepInputLayout;
    @BindView(R.id.input_layout_complemento) protected TextInputLayout mComplementoInputLayout;
    @BindView(R.id.input_layout_telefone) protected TextInputLayout mTelefoneInputLayout;
    @BindView(R.id.input_layout_celular) protected TextInputLayout mCelularInputLayout;
    @BindView(R.id.input_layout_nome_fantasia) protected TextInputLayout mNomeFantasiaInputLayout;

    private CadastroClienteContract.Presenter mPresenter;

    private SparseArray<ViewGroup> mViewFields = new SparseArray<>();

    private SparseArray<ViewGroup> mRequiredFields = new SparseArray<>();

    private CidadesAdapter mCidadesAdapter;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_cadastro_cliente;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        mPresenter = new CadastroClientePresenter(
                provideClienteRepository(), provideEstadoRepository(), provideCidadeRepository(),
                getCadastroClienteResources(this));
        super.onCreate(inState);
        setAsInitialFlowActivity();
        mPresenter.attachView(this);
        mPresenter.initializeView(getIntent().getParcelableExtra(EXTRA_CLIENTE_EDICAO));
    }

    @Override public void setViewFields(final List<Integer> viewIds) {
        mViewFields.clear();
        for (Integer viewId : viewIds) {
            mViewFields.put(viewId, ButterKnife.findById(this, viewId));
        }
    }

    @Override public void setRequiredFields(final List<Integer> requiredViewIds) {
        mRequiredFields.clear();
        for (Integer viewId : requiredViewIds) {
            mRequiredFields.put(viewId, mViewFields.get(viewId));
        }
    }

    @Override public void displayTiposPessoa(final List<TipoPessoa> tiposPessoa) {
        mTiposPessoaSpinner.setAdapter(new TiposPessoaAdapter(this, tiposPessoa));
    }

    @Override public void displayEstados(final List<Estado> estados) {
        mEstadosSpinner.setAdapter(new EstadosAdapter(this, estados));
    }

    @Override public void setViewValue(final int viewId, final int itemPosition) {
        final ViewGroup field = mViewFields.get(viewId);
        if (field != null && field instanceof MaterialSpinner) {
            ((MaterialSpinner) field).setSelection(itemPosition + 1, true);
        }
    }

    @Override public void setViewValue(final int viewId, final String viewValue) {
        final ViewGroup field = mViewFields.get(viewId);
        if (field != null && field instanceof TextInputLayout) {
            ((TextInputLayout) field).getEditText().setText(viewValue);
        }
    }

    @Override public void changeTitle(final String newTitle) {
        getSupportActionBar().setTitle(newTitle);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_cliente, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mPresenter.handleExiting();
                return true;
            }
            case R.id.action_save: {
                mPresenter.handleActionSave();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnItemSelected(R.id.spinner_tipo_pessoa) void onTipoPessoaSelected(int pPosition) {
        mPresenter.handleTipoPessoaSelected(pPosition);
    }

    @Override public void changeViewForTipoPessoa(final int charCount) {
        changeFieldCpfOuCnpjEditText(charCount);
    }

    private void changeFieldCpfOuCnpjEditText(int length) {
        EditText editText = mCpfOuCnpjInputLayout.getEditText();
        editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(length)});
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        if (!TextUtils.isEmpty(editText.getText())) {
            if (editText.getText().length() > length) {
                editText.setText(editText.getText().subSequence(0, length -1));
            }
        }
    }

    @Override public void showFocusOnFieldCpfOuCnpj() {
        mCpfOuCnpjInputLayout.getEditText().requestFocus();
    }

    @Override public void removeFocusOnFieldCpfOuCnpj() {
        mCpfOuCnpjInputLayout.getEditText().setFocusable(false);
        mCpfOuCnpjInputLayout.getEditText().setFocusableInTouchMode(false);
    }

    @Override public void displayCidades(final List<Cidade> cidades) {
        mCidadesSpinner.setAdapter(mCidadesAdapter = new CidadesAdapter(this, cidades));
    }

    @Override public void updateCidades() {
        if (mCidadesAdapter.isEmpty()) {
            mCidadesSpinner.setSelection(0, true);
        }
        mCidadesAdapter.notifyDataSetChanged();
    }

    @Override public void hideRequiredMessages() {
        for (int i = 0; i < mRequiredFields.size(); i++) {
            ViewGroup requiredField = mRequiredFields.valueAt(i);
            if (requiredField instanceof MaterialSpinner) {
                ((MaterialSpinner) requiredField).setError(null);
            } else if (requiredField instanceof TextInputLayout) {
                ((TextInputLayout) requiredField).setError(null);
            }
        }
    }

    @Override public boolean hasEmptyRequiredFields() {
        for (int i = 0; i < mRequiredFields.size(); i++) {
            ViewGroup requiredField = mRequiredFields.valueAt(i);

            boolean isEmpty = false;
            if (requiredField instanceof MaterialSpinner) {
                isEmpty = ((MaterialSpinner) requiredField).getSelectedItem() == null;
            } else if (requiredField instanceof TextInputLayout) {
                isEmpty = TextUtils
                        .isEmpty(((TextInputLayout) requiredField).getEditText().getText());
            }

            if (isEmpty) {
                return true;
            }
        }
        return false;
    }

    @Override public void displayRequiredFieldMessages() {
        ViewGroup firstRequiredField = null;

        for (int i = 0; i < mRequiredFields.size(); i++) {
            ViewGroup requiredField = mRequiredFields.valueAt(i);

            if (requiredField instanceof MaterialSpinner) {
                if (((MaterialSpinner) requiredField).getSelectedItem() == null) {
                    ((MaterialSpinner) requiredField)
                            .setError(R.string.error_campo_requerido);

                    if (firstRequiredField == null) {
                        firstRequiredField = requiredField;
                    }
                }
            } else if (requiredField instanceof TextInputLayout) {
                if (TextUtils.isEmpty(((TextInputLayout) requiredField).getEditText().getText())) {
                    ((TextInputLayout) requiredField)
                            .setError(getString(R.string.error_campo_requerido));

                    if (firstRequiredField == null) {
                        firstRequiredField = requiredField;
                    }
                }
            }
        }

        if (firstRequiredField != null) {
            if (firstRequiredField instanceof MaterialSpinner) {
                firstRequiredField.requestFocusFromTouch();
                firstRequiredField.performClick();
                AndroidUtils.hideKeyboard(this, getCurrentFocus());
            } else {
                AndroidUtils.focusThenShowKeyboard(this, firstRequiredField);
            }
        }
    }

    @Override public String getViewStringValue(final int viewId) {
        final ViewGroup field = mViewFields.get(viewId);
        if (field != null && field instanceof TextInputLayout) {
            return ((TextInputLayout) field).getEditText().getText().toString();
        }
        return "";
    }

    @Override public int getViewPositionValue(final int viewId) {
        final ViewGroup field = mViewFields.get(viewId);
        if (field != null && field instanceof MaterialSpinner) {
            return ((MaterialSpinner) field).getSelectedItemPosition();
        }
        return -1;
    }

    @Override public void resultClienteEditado(final Cliente clienteEditado) {
        Intent data = new Intent().putExtra(RESULT_CLIENTE_EDITADO, clienteEditado);
        setResult(Navigator.RESULT_OK, data);
        finish();
    }

    @Override public boolean hasUnmodifiedFields() {
        return (mTiposPessoaSpinner.getSelectedItem() == null)
                && TextUtils.isEmpty(mCpfOuCnpjInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mNomeInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mEmailInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mEnderecoInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mNumeroInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mBairroInputLayout.getEditText().getText())
                && (mEstadosSpinner.getSelectedItem() == null)
                && (mCidadesSpinner.getSelectedItem() == null)
                && TextUtils.isEmpty(mCepInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mComplementoInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mTelefoneInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mCelularInputLayout.getEditText().getText())
                && TextUtils.isEmpty(mNomeFantasiaInputLayout.getEditText().getText());
    }

    @Override public void showExitViewQuestion() {
        FeedbackHelper.showQuestionDialog(this, R.string.exit_and_cancel_question,
                (dialog, which) -> finishView(), null);
    }

    @Override public void finishView() {
        finish();
    }

    @OnItemSelected(value = R.id.spinner_tipo_pessoa, callback = NOTHING_SELECTED)
    void onNoneTipoPessoaSelected() {
        mPresenter.handleNoneTipoPessoaSelected();
    }

    @OnItemSelected(R.id.spinner_estados) void onEstadoSelected(int position) {
        mPresenter.handleEstadoSelected(position);
    }

    @OnItemSelected(value =  R.id.spinner_estados, callback = NOTHING_SELECTED)
    void onNoneEstadoSelected() {
        mPresenter.handleNoneEstadoSelected();
    }

    @Override public void onBackPressed() {
        mPresenter.handleExiting();
    }

    @Override protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}
