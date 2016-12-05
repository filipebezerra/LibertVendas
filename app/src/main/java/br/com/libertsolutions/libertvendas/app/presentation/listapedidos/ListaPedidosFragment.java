package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.CadastroPedidoActivity;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemClickListener;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemTouchListener;
import butterknife.BindView;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ListaPedidosFragment extends LibertVendasFragment
        implements ListaPedidosContract.View, OnItemClickListener {

    private static final String ARG_LISTA_PEDIDOS_NAO_ENVIADOS
            = ListaPedidosFragment.class.getSimpleName() + ".argListaPedidosNaoEnviados";

    @BindView(R.id.recycler_view_pedidos) protected RecyclerView mRecyclerViewPedidos;

    private ListaPedidosContract.Presenter mPresenter;

    private ListaPedidosAdapter mRecyclerViewAdapter;

    private boolean mListaPedidosNaoEnviados;

    private MaterialDialog mProgressDialog;

    private OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    private OnItemTouchListener mRecyclerViewItemTouchListener = null;

    public static ListaPedidosFragment newInstance(boolean listaPedidosNaoEnviados) {
        ListaPedidosFragment fragment = new ListaPedidosFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_LISTA_PEDIDOS_NAO_ENVIADOS, listaPedidosNaoEnviados);
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_lista_pedidos;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewPedidos.setHasFixedSize(true);
        mRecyclerViewPedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_LISTA_PEDIDOS_NAO_ENVIADOS)) {
            mListaPedidosNaoEnviados = arguments.getBoolean(ARG_LISTA_PEDIDOS_NAO_ENVIADOS);
        }

        mPresenter = new ListaPedidosPresenter(Injection.providePedidoRepository());
        mPresenter.attachView(this);
        mPresenter.loadListaPedidos(mListaPedidosNaoEnviados);
    }

    @Override public void showLoading() {
        mProgressDialog = new MaterialDialog.Builder(getContext())
                .content(getString(R.string.loading_pedidos))
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    @Override public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override public void showListaPedidos(List<Pedido> pPedidoList) {
        mRecyclerViewPedidos.setAdapter(
                mRecyclerViewAdapter = new ListaPedidosAdapter(
                        getContext(), !mListaPedidosNaoEnviados, pPedidoList));

        mRecyclerViewPedidos
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        mRecyclerViewLayoutListener = this::recyclerViewFinishLoading);
    }

    private void recyclerViewFinishLoading() {
        mRecyclerViewPedidos
                .getViewTreeObserver()
                .removeOnGlobalLayoutListener(mRecyclerViewLayoutListener);

        if (mRecyclerViewItemTouchListener != null) {
            mRecyclerViewPedidos.removeOnItemTouchListener(mRecyclerViewItemTouchListener);
        }

        mRecyclerViewPedidos.addOnItemTouchListener(
                mRecyclerViewItemTouchListener
                        = new OnItemTouchListener(getContext(), mRecyclerViewPedidos, this));

        mPresenter.registerForEvents();
        hideLoading();
    }

    @Override public void onSingleTapUp(View view, int position) {
        mPresenter.handleSingleTapUp(position);
    }

    @Override public void onLongPress(View view, int position) {}

    @Override public void navigateToCadastroPedido(Pedido pPedido) {
        hostActivity().navigate().toCadastroPedido(pPedido);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Navigator.REQUEST_EDITAR_PEDIDO && resultCode == Navigator.RESULT_OK) {
            mPresenter.handleResultPedidoEditado(
                    data.getParcelableExtra(CadastroPedidoActivity.RESULT_PEDIDO_EDITADO));
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void updateInsertedItemAtPosition(int pPosition) {
        mRecyclerViewAdapter.notifyItemInserted(pPosition);
        mRecyclerViewPedidos.smoothScrollToPosition(pPosition);
    }

    @Override
    public void updateChangedItemAtPosition(int pPosition) {
        mRecyclerViewAdapter.notifyItemChanged(pPosition);
        mRecyclerViewPedidos.smoothScrollToPosition(pPosition);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unregisterForEvents();
        mPresenter.detach();
    }
}
