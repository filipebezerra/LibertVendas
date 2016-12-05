package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ListaPedidosFragment extends LibertVendasFragment
        implements ListaPedidosContract.View {

    private static final String ARG_LISTA_PEDIDOS_NAO_ENVIADOS
            = ListaPedidosFragment.class.getSimpleName() + ".argListaPedidosNaoEnviados";

    @BindView(R.id.recycler_view_pedidos) protected RecyclerView mRecyclerViewPedidos;

    private ListaPedidosContract.Presenter mPresenter;

    private ListaPedidosAdapter mRecyclerViewAdapter;

    private boolean mListaPedidosNaoEnviados;

    private MaterialDialog mProgressDialog;

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
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mRecyclerViewPedidos
                                .getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                        hideLoading();
                        mPresenter.registerForEvents();
                    }
                });
    }

    @Override public void updateInsertedItemAtPosition(int pPosition) {
        mRecyclerViewAdapter.notifyItemInserted(pPosition);
        mRecyclerViewPedidos.smoothScrollToPosition(pPosition);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unregisterForEvents();
        mPresenter.detach();
    }
}
