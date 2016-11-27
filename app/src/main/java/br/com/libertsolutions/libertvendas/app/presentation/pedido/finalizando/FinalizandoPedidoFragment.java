package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ResourcesRepositories;
import br.com.libertsolutions.libertvendas.app.presentation.util.FeedbackHelper;
import butterknife.BindView;
import butterknife.OnClick;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

/**
 * @author Filipe Bezerra
 */
public class FinalizandoPedidoFragment extends LibertVendasFragment
        implements FinalizandoPedidoContract.View {

    static final String EXTRA_ARG_PRODUTOS_SELECIONADOS
            = FinalizandoPedidoFragment.class.getSimpleName() + ".extraArgProdutosSelecionados";
    static final String EXTRA_ARG_TABELA_PRECO_PADRAO
            = FinalizandoPedidoFragment.class.getSimpleName() + ".extraArgTabelaPrecoPadrao";
    static final String EXTRA_ARG_CLIENTE_SELECIONADO
            = FinalizandoPedidoFragment.class.getSimpleName() + ".extraArgClienteSelecionado";

    @BindView(R.id.container_finalizando_pedido) protected NestedScrollView mContainerFinalizandoPedido;
    @BindView(R.id.input_layout_data_emissao) protected TextInputLayout mInputLayoutDataEmissao;
    @BindView(R.id.edit_text_data_emissao) protected EditText mEditTextDataEmissao;
    @BindView(R.id.input_layout_cliente) protected TextInputLayout mInputLayoutCliente;
    @BindView(R.id.edit_text_cliente) protected EditText mEditTextCliente;
    @BindView(R.id.edit_text_total_produtos) protected EditText mEditTextTotalProdutos;
    @BindView(R.id.input_layout_desconto) protected TextInputLayout mInputLayoutDesconto;
    @BindView(R.id.edit_text_desconto) protected EditText mEditTextDesconto;
    @BindView(R.id.spinner_forma_pagamento) protected MaterialSpinner mSpinnerFormaPagamento;
    @BindView(R.id.edit_text_observacao) protected EditText mEditTextObservacao;

    private FinalizandoPedidoContract.Presenter mPresenter;

    private FinalizandoPedidoViewModel mViewModel;

    public static FinalizandoPedidoFragment newInstance(List<ProdutoVo> pProdutosSelecionados,
            TabelaPreco pTabelaPrecoPadrao, Cliente pClienteSelecionado) {
        FinalizandoPedidoFragment fragment = new FinalizandoPedidoFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(EXTRA_ARG_PRODUTOS_SELECIONADOS,
                new ArrayList<>(pProdutosSelecionados));
        arguments.putParcelable(EXTRA_ARG_TABELA_PRECO_PADRAO,
                pTabelaPrecoPadrao);
        arguments.putParcelable(EXTRA_ARG_CLIENTE_SELECIONADO,
                pClienteSelecionado);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_finalizando_pedido;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter = new FinalizandoPedidoPresenter(
                Injection.provideFormaPagamentoRepository(),
                Injection.providePedidoRepository(),
                ResourcesRepositories.getFinalizaPedidoResourcesRepository(getContext()),
                Injection.provideSettingsRepository(getContext()));
    }

    @Override public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.attachView(this);
        mPresenter.registerForEvents();

        mViewModel = new FinalizandoPedidoViewModel(getContext(),
                mEditTextDataEmissao,
                mEditTextCliente,
                mEditTextTotalProdutos,
                mEditTextDesconto,
                mSpinnerFormaPagamento,
                mEditTextObservacao);

        FinalizandoPedidoExtrasExtractor extrasExtractor
                = new FinalizandoPedidoExtrasExtractor(getArguments());

        mPresenter.attachViewModel(mViewModel, extrasExtractor);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finalizando_pedido, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            mPresenter.clickActionSave();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.edit_text_data_emissao) void onEditTextDataEmissaoClicked() {
        mPresenter.clickSelectDataEmissao();
    }

    @Override public void showCalendarPicker(Calendar pDataEmissao) {
        new CalendarDatePickerDialogFragment()
                .setOnDateSetListener((dialog, year, monthOfYear, dayOfMonth) ->
                        mPresenter.changeDataEmissao(dialog.getSelectedDay().getDateInMillis()))
                .setPreselectedDate(
                        pDataEmissao.get(Calendar.YEAR),
                        pDataEmissao.get(Calendar.MONTH),
                        pDataEmissao.get(Calendar.DAY_OF_MONTH)
                )
                .setThemeCustom(R.style.Widget_Libert_BetterPickersDialogs)
                .show(getChildFragmentManager(), "DatePickerDialog");
    }

    @Override public void hideRequiredMessages() {
        mInputLayoutDataEmissao.setError(null);
        mSpinnerFormaPagamento.setError(null);
        mInputLayoutCliente.setError(null);
    }

    @Override public void displayRequiredMessageForDataEmissao() {
        displayRequiredMessageForField(mInputLayoutDataEmissao);
    }

    @Override public void displayRequiredMessageForFormaPagamento() {
        displayRequiredMessageForField(mSpinnerFormaPagamento);
    }

    @Override public void displayRequiredMessageForCliente() {
        displayRequiredMessageForField(mInputLayoutCliente);
    }

    @Override public void displayValidationErrorForDesconto(String pMessage) {
        mInputLayoutDesconto.setError(pMessage);
        mEditTextDesconto.requestFocus();
    }

    @Override public void showFeedbackMessage(String pMessage) {
        if (!TextUtils.isEmpty(pMessage)) {
            FeedbackHelper.snackbar(mContainerFinalizandoPedido, pMessage);
        }
    }

    private void displayRequiredMessageForField(MaterialSpinner pMaterialSpinner) {
        pMaterialSpinner.setError(getString(R.string.error_campo_requerido));
        pMaterialSpinner.requestFocus();
    }

    private void displayRequiredMessageForField(TextInputLayout pInputLayout) {
        pInputLayout.setError(getString(R.string.error_campo_requerido));
        pInputLayout.getEditText().requestFocus();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
    }
}
