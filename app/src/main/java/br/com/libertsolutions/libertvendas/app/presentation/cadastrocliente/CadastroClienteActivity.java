package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

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
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TipoPessoa;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.util.AndroidUtils;
import br.com.libertsolutions.libertvendas.app.presentation.util.FeedbackHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

import static br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.CadastroClienteDependencyContainer.createDependencyContainer;
import static butterknife.OnItemSelected.Callback.NOTHING_SELECTED;

/**
 * @author Filipe Bezerra
 */
public class CadastroClienteActivity extends LibertVendasActivity
        implements CadastroClienteContract.View {

    public static final String EXTRA_CLIENTE_EDICAO
            = CadastroClienteActivity.class.getSimpleName() + ".extraClienteEdicao";

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

    private CadastroClienteContract.Presenter mPresenter;

    private SparseArray<ViewGroup> mViewFields = new SparseArray<>();

    private SparseArray<ViewGroup> mRequiredFields = new SparseArray<>();

    private CidadesAdapter mCidadesAdapter;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_cadastro_cliente;
    }

    @Override protected void onCreate(@Nullable Bundle inState) {
        mPresenter = new CadastroClientePresenter(createDependencyContainer(this));
        super.onCreate(inState);
        setAsInitialFlowActivity();
        mPresenter.attachView(this);
        mPresenter.initializeView(getIntent().getParcelableExtra(EXTRA_CLIENTE_EDICAO));
    }

    @Override protected void onStart() {
        super.onStart();
        mPresenter.registerForEvents();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_cliente, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            mPresenter.handleActionSave();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @OnItemSelected(R.id.spinner_tipo_pessoa) void onTiposPessoaSpinnerItemSelected(int pPosition) {
        mPresenter.handleTiposPessoaSpinnerItemSelected(pPosition);
    }

    @OnItemSelected(value = R.id.spinner_tipo_pessoa, callback = NOTHING_SELECTED)
    void onTiposPessoaSpinnerNothingSelected() {
        mPresenter.handleTiposPessoaSpinnerNothingSelected();
    }

    @OnItemSelected(R.id.spinner_estados) void onEstadosSpinnerItemSelected(int pPosition) {
        mPresenter.handleEstadosItemSelected(pPosition);
    }

    @OnItemSelected(value =  R.id.spinner_estados, callback = NOTHING_SELECTED)
    void onEstadosSpinnerNothingSelected() {
        mPresenter.handleEstadosSpinnerNothingSelected();
    }

    @Override public void setViewFields(List<Integer> pViewIds) {
        mViewFields.clear();
        for (Integer viewId : pViewIds) {
            mViewFields.put(viewId, ButterKnife.findById(this, viewId));
        }
    }

    @Override public void setRequiredFields(List<Integer> pRequiredViewIds) {
        mRequiredFields.clear();
        for (Integer viewId : pRequiredViewIds) {
            mRequiredFields.put(viewId, mViewFields.get(viewId));
        }
    }

    @Override public void displayTiposPessoa(List<TipoPessoa> pTiposPessoa) {
        mTiposPessoaSpinner.setAdapter(new TiposPessoaAdapter(this, pTiposPessoa));
    }

    @Override public void displayEstados(List<Estado> pEstados) {
        mEstadosSpinner.setAdapter(new EstadosAdapter(this, pEstados));
    }

    @Override public void displayCidades(List<Cidade> pCidadesList) {
        mCidadesSpinner.setAdapter(mCidadesAdapter = new CidadesAdapter(this, pCidadesList));
    }

    @Override public void changeTitle(String pNewTitle) {
        getSupportActionBar().setTitle(pNewTitle);
    }

    @Override public void updateCidades() {
        if (mCidadesAdapter.isEmpty()) {
            mCidadesSpinner.setSelection(0, true);
        }
        mCidadesAdapter.notifyDataSetChanged();
    }

    @Override public void setViewValue(final int pViewId, final String pViewValue) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof TextInputLayout) {
            ((TextInputLayout) field).getEditText().setText(pViewValue);
        }
    }

    @Override public void setViewValue(final int pViewId, final int pPosition) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof MaterialSpinner) {
            ((MaterialSpinner) field).setSelection(pPosition + 1, true);
        }
    }

    @Override public int getViewPositionValue(final int pViewId) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof MaterialSpinner) {
            return ((MaterialSpinner) field).getSelectedItemPosition();
        }
        return -1;
    }

    @Override public String getViewStringValue(final int pViewId) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof TextInputLayout) {
            return ((TextInputLayout) field).getEditText().getText().toString();
        }
        return "";
    }

    @Override public void changeViewForTipoPessoa(int pCharCount) {
        changeFieldCpfOuCnpjEditText(pCharCount);
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

    @Override public void hideRequiredMessages() {
        for (int i = 0; i < mRequiredFields.size() - 1; i++) {
            ViewGroup requiredField = mRequiredFields.valueAt(i);
            if (requiredField instanceof MaterialSpinner) {
                ((MaterialSpinner) requiredField).setError(null);
            } else if (requiredField instanceof TextInputLayout) {
                ((TextInputLayout) requiredField).setError(null);
            }
        }
    }

    @Override public boolean hasEmptyRequiredFields() {
        for (int i = 0; i < mRequiredFields.size() - 1; i++) {
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

        for (int i = 0; i < mRequiredFields.size() - 1; i++) {
            ViewGroup requiredField = mRequiredFields.valueAt(i);

            if (requiredField instanceof MaterialSpinner) {
                if (((MaterialSpinner) requiredField).getSelectedItem() == null) {
                    ((MaterialSpinner) requiredField).setError(R.string.error_campo_requerido);

                    if (firstRequiredField == null) {
                        firstRequiredField = requiredField;
                    }
                }
            } else if (requiredField instanceof TextInputLayout) {
                if (TextUtils.isEmpty(((TextInputLayout) requiredField).getEditText().getText())) {
                    ((TextInputLayout) requiredField).setError(getString(R.string.error_campo_requerido));

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
                && TextUtils.isEmpty(mCelularInputLayout.getEditText().getText());
    }

    @Override public void showExitViewQuestion() {
        FeedbackHelper.showQuestionDialog(this, R.string.exit_and_cancel_question,
                (dialog, which) -> finishView(), null);
    }

    @Override public void onBackPressed() {
        mPresenter.handleBackPressed();
    }

    @Override public void finishView() {
        finish();
    }

    @Override protected void onStop() {
        super.onStop();
        mPresenter.unregisterForEvents();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
