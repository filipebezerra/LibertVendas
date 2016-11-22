package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.events.UsuarioLogadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ResourcesRepositories;
import br.com.libertsolutions.libertvendas.app.presentation.util.FeedbackHelper;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import smtchahal.materialspinner.MaterialSpinner;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
public class FinalizaPedidoFragment extends LibertVendasFragment
        implements FinalizaPedidoContract.View {

    public static final String ARG_PRODUTOS_SELECIONADOS
            = FinalizaPedidoFragment.class.getSimpleName() + ".argProdutosSelecionados";

    public static final String ARG_TABELA_PRECO_PADRAO
            = FinalizaPedidoFragment.class.getSimpleName() + ".argTabelaPrecoPadrao";

    private FinalizaPedidoContract.Presenter mPresenter;

    @BindView(R.id.container_finalizando_pedido) protected NestedScrollView mContainerFinalizaPedido;
    @BindView(R.id.input_layout_data_emissao) protected TextInputLayout mInputLayoutDataEmissao;
    @BindView(R.id.edit_text_data_emissao) protected EditText mEditTextDataEmissao;
    @BindView(R.id.input_layout_cliente) protected TextInputLayout mInputLayoutCliente;
    @BindView(R.id.edit_text_cliente) protected EditText mEditTextCliente;
    @BindView(R.id.edit_text_total_produtos) protected EditText mEditTextTotalProdutos;
    @BindView(R.id.edit_text_desconto) protected EditText mEditTextDesconto;
    @BindView(R.id.spinner_forma_pagamento) protected MaterialSpinner mSpinnerFormaPagamento;
    @BindView(R.id.edit_text_observacao) protected EditText mEditTextObservacao;


    public static FinalizaPedidoFragment newInstance(List<ProdutoVo> pProdutosSelecionados,
            TabelaPreco pTabelaPreco) {
        FinalizaPedidoFragment fragment = new FinalizaPedidoFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARG_PRODUTOS_SELECIONADOS,
                new ArrayList<>(pProdutosSelecionados));
        arguments.putParcelable(ARG_TABELA_PRECO_PADRAO, pTabelaPreco);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FinalizaPedidoPresenter(this,
                Injection.provideFormaPagamentoRepository(getContext()),
                Injection.providePedidoRepository(getContext()),
                ResourcesRepositories.getFinalizaPedidoResourcesRepository(getContext()));
        setHasOptionsMenu(true);
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_finalizando_pedido;
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finalizando_pedido, menu);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FinalizaPedidoViewModel finalizaPedidoViewModel = new FinalizaPedidoViewModel(getContext(),
                mEditTextDataEmissao,
                mEditTextCliente,
                mEditTextTotalProdutos,
                mEditTextDesconto,
                mSpinnerFormaPagamento,
                mEditTextObservacao);
        mPresenter.initializeView(finalizaPedidoViewModel,
                new ProdutosSelecionadosArgumentExtractor(getArguments()),
                new TabelaPrecoPadraoArgumentExtractor(getArguments()));
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            mPresenter.clickActionSave();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override public void navigateToListaClientes() {
        hostActivity().navigate().toClientes();
    }

    @Override public void showCalendarPicker(Calendar pDataEmissao) {
        new CalendarDatePickerDialogFragment()
                .setOnDateSetListener((dialog, year, monthOfYear, dayOfMonth) ->
                        mPresenter.setDataEmissao(year, monthOfYear, dayOfMonth))
                .setPreselectedDate(
                        pDataEmissao.get(Calendar.YEAR),
                        pDataEmissao.get(Calendar.MONTH),
                        pDataEmissao.get(Calendar.DAY_OF_MONTH)
                )
                .setThemeCustom(R.style.Widget_Libert_BetterPickersDialogs)
                .show(getChildFragmentManager(), "DatePickerDialog");
    }

    @Override public void hideRequiredMessages() {

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

    @Override public void showFeedbackMessage(String pMessage) {
        if (!TextUtils.isEmpty(pMessage)) {
            FeedbackHelper.snackbar(mContainerFinalizaPedido, pMessage);
        }
    }

    @OnClick(R.id.edit_text_cliente) void onClickEditTextCliente() {
        mPresenter.clickSelectCliente();
    }

    @OnTouch(R.id.edit_text_data_emissao) boolean onTouchEditTextDataEmissao(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            mPresenter.clickSelectDataEmissao();
            return true;
        }
        return false;
    }

    @Override public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onClienteSelecionadoEvent(
            ClienteSelecionadoEvent pEvent) {
        mPresenter.handleClienteSelecionadoEvent(pEvent.getCliente());
        EventBus.getDefault().removeStickyEvent(pEvent);
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onUsuarioLogadoEvent(
            UsuarioLogadoEvent pEvent) {
        mPresenter.handleUsuarioLogadoEvent(pEvent.getVendedor());
    }

    @Override public void resultNovoPedido(Pedido pPedido) {
        EventBus.getDefault().post(NovoPedidoEvent.newEvent(pPedido));
    }

    private void displayRequiredMessageForField(MaterialSpinner pMaterialSpinner) {
        pMaterialSpinner.setError(getString(R.string.error_campo_requerido));
        pMaterialSpinner.requestFocus();
    }

    private void displayRequiredMessageForField(TextInputLayout pInputLayout) {
        pInputLayout.setError(getString(R.string.error_campo_requerido));
        pInputLayout.getEditText().requestFocus();
    }
}
