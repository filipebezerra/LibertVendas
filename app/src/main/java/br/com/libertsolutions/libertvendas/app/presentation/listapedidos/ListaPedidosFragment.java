package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;

/**
 * @author Filipe Bezerra
 */
public class ListaPedidosFragment extends LibertVendasFragment
        implements ListaPedidosContract.View {

    @BindView(R.id.recycler_view_produtos) protected RecyclerView mRecyclerViewProdutos;

    private PedidoAdapter mPedidoAdapter;

    private ListaPedidosContract.Presenter mPresenter;

    public static ListaPedidosFragment newInstance() {
        return new ListaPedidosFragment();
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_lista_produtos;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ListaPedidosPresenter(this, Injection.providePedidoService(getContext()));

        mRecyclerViewProdutos.setHasFixedSize(true);
        mRecyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.loadListaPedidos();
    }

    @Override
    public void showListaPedidos(List<Pedido> pPedidoList) {
        mRecyclerViewProdutos.setAdapter(
                mPedidoAdapter = new PedidoAdapter(getContext(), pPedidoList));
    }
}
