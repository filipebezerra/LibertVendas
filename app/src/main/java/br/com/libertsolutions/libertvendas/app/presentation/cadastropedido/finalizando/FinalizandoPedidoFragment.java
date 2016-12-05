package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

import static br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.FinalizandoPedidoDependencyContainer.createDependencyContainer;

/**
 * @author Filipe Bezerra
 */
public class FinalizandoPedidoFragment extends LibertVendasFragment
        implements FinalizandoPedidoContract.View {

    public static final String EXTRA_PEDIDO_EDICAO
            = FinalizandoPedidoFragment.class.getSimpleName() + ".argExtraPedidoEdicao";

    @BindView(R.id.spinner_forma_pagamento) protected MaterialSpinner mSpinnerFormaPagamento;

    private FinalizandoPedidoContract.Presenter mPresenter;

    private SparseArray<ViewGroup> mViewFields = new SparseArray<>();

    private SparseArray<ViewGroup> mRequiredFields = new SparseArray<>();

    public static FinalizandoPedidoFragment newInstance(Pedido pPedidoEmEdicao) {
        FinalizandoPedidoFragment fragment = new FinalizandoPedidoFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(EXTRA_PEDIDO_EDICAO, pPedidoEmEdicao);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_finalizando_pedido;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new FinalizandoPedidoPresenter(createDependencyContainer(this));
        mPresenter.attachView(this);
        mPresenter.initializeView(getArguments().getParcelable(EXTRA_PEDIDO_EDICAO));
        mPresenter.registerForEvents();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finalizando_pedido, menu);
    }

    @Override public void displayFormasPagamento(List<FormaPagamento> pFormasPagamentoList) {
        mSpinnerFormaPagamento.setAdapter(
                new FormasPagamentoAdapter(getContext(), pFormasPagamentoList));
    }

    @Override public void setViewFields(List<Integer> pViewIds) {
        mViewFields.clear();
        for (Integer viewId : pViewIds) {
            mViewFields.put(viewId, ButterKnife.findById(getView(), viewId));
        }
    }

    @Override public void setRequiredFields(List<Integer> pRequiredViewIds) {
        mRequiredFields.clear();
        for (Integer viewId : pRequiredViewIds) {
            mRequiredFields.put(viewId, mViewFields.get(viewId));
        }
    }

    @Override public void setViewValue(int pViewId, int pPosition) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof MaterialSpinner) {
            ((MaterialSpinner) field).setSelection(pPosition + 1, true);
        }
    }

    @Override public void setViewValue(int pViewId, String pViewValue) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof TextInputLayout) {
            ((TextInputLayout) field).getEditText().setText(pViewValue);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unregisterForEvents();
        mPresenter.detach();
    }
}
