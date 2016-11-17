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
import br.com.libertsolutions.libertvendas.app.presentation.home.NewPedidoCadastradoEvent;
import butterknife.BindView;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
public class ListaPedidosFragment extends LibertVendasFragment
        implements ListaPedidosContract.View {

    private static final String ARG_LISTA_PEDIDOS_NAO_ENVIADOS
            = ListaPedidosFragment.class.getSimpleName() + ".argListaPedidosNaoEnviados";

    @BindView(R.id.recycler_view_pedidos) protected RecyclerView mRecyclerViewPedidos;

    private PedidoAdapter mPedidoAdapter;

    private ListaPedidosContract.Presenter mPresenter;

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
        mPresenter = new ListaPedidosPresenter(this,
                Injection.providePedidoRepository(getContext()));

        mRecyclerViewPedidos.setHasFixedSize(true);
        mRecyclerViewPedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_LISTA_PEDIDOS_NAO_ENVIADOS)) {
            mListaPedidosNaoEnviados = arguments.getBoolean(ARG_LISTA_PEDIDOS_NAO_ENVIADOS);

            mPresenter.loadListaPedidos(mListaPedidosNaoEnviados);
        }
    }

    @Override public void showListaPedidos(List<Pedido> pPedidoList) {
        mRecyclerViewPedidos.setAdapter(
                mPedidoAdapter = new PedidoAdapter(
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
                    }
                });
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

    @Override public void updateListaPedidos(int pPosition) {
        mPedidoAdapter.notifyItemInserted(pPosition);
    }

    @Override public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onNewPedidoCadastradoEvent(
            NewPedidoCadastradoEvent pEvent) {
        mPresenter.addNewPedidoCadastrado(pEvent.getPedido());
        EventBus.getDefault().removeStickyEvent(pEvent);
    }
}
