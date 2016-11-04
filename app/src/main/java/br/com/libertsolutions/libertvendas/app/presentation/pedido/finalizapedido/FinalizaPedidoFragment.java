package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

/**
 * @author Filipe Bezerra
 */
public class FinalizaPedidoFragment extends LibertVendasFragment
        implements FinalizaPedidoContract.View {

    @BindView(R.id.spinner_forma_pagamento) protected MaterialSpinner mSpinnerFormaPagamento;

    private FinalizaPedidoContract.Presenter mPresenter;

    private FormaPagamentoAdapter mFormaPagamentoAdapter;

    public static FinalizaPedidoFragment newInstance() {
        return new FinalizaPedidoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FinalizaPedidoPresenter(this,
                Injection.provideFormaPagamentoRepository(getContext()));
        setHasOptionsMenu(true);
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_finaliza_pedido;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finaliza_pedido, menu);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.initializeView();
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
    public void bindFormasPagamento(List<FormaPagamento> pFormaPagamentoList) {
        mFormaPagamentoAdapter = new FormaPagamentoAdapter(getContext(), pFormaPagamentoList);
        mSpinnerFormaPagamento.setAdapter(mFormaPagamentoAdapter);
    }

    @Override
    public void navigateToListaClientesActivity() {
        hostActivity().navigate().toClientes();
    }

    @OnClick(R.id.edit_text_cliente)
    void onClickEditTextCliente() {
        mPresenter.clickSelectCliente();
    }
}
