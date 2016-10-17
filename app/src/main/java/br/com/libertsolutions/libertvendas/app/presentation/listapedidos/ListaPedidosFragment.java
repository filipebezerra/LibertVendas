package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ListaPedidosFragment extends LibertVendasFragment
        implements ListaPedidosContract.View {

    @BindView(R.id.recycler_view_pedidos) protected RecyclerView mRecyclerViewPedidos;

    private PedidoAdapter mPedidoAdapter;

    private ListaPedidosContract.Presenter mPresenter;

    public static ListaPedidosFragment newInstance() {
        return new ListaPedidosFragment();
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_lista_pedidos;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ListaPedidosPresenter(this, Injection.providePedidoService(getContext()));

        mRecyclerViewPedidos.setHasFixedSize(true);
        mRecyclerViewPedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.loadListaPedidos();
    }

    @Override
    public void showListaPedidos(List<Pedido> pPedidoList) {
        mRecyclerViewPedidos.setAdapter(
                mPedidoAdapter = new PedidoAdapter(getContext(), pPedidoList));
    }
}
