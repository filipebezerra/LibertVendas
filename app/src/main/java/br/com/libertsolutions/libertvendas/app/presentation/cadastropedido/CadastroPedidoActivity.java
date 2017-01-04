package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.FinalizandoPedidoFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ListaClientesFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosFragment;
import br.com.libertsolutions.libertvendas.app.presentation.widget.TabAdapter;
import butterknife.BindView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class CadastroPedidoActivity extends LibertVendasActivity
        implements CadastroPedidoContract.View {

    private static final int PAGE_LISTA_PRODUTOS = 0;
    private static final int PAGE_LISTA_CLIENTES = 1;
    private static final int PAGE_FINALIZANDO_PEDIDO = 2;

    @IntDef({ PAGE_LISTA_PRODUTOS, PAGE_LISTA_CLIENTES, PAGE_FINALIZANDO_PEDIDO })
    private @interface Step {}

    public static final String EXTRA_PEDIDO_EDICAO
            = CadastroPedidoActivity.class.getSimpleName() + ".extraPedidoEdicao";

    public static final String RESULT_PEDIDO_EDITADO
            = CadastroPedidoActivity.class.getSimpleName() + ".resultPedidoEditado";

    @BindView(R.id.fragment_container) protected ViewPager mViewPager;

    private CadastroPedidoContract.Presenter mPresenter;

    private TabAdapter mTabAdapter;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_cadastro_pedido;
    }

    @Override protected void onCreate(@Nullable Bundle inState) {
        mPresenter = new CadastroPedidoPresenter();
        super.onCreate(inState);
        setAsInitialFlowActivity();
        mPresenter.attachView(this);

        mTabAdapter = new TabAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {}

            @Override public void onPageSelected(int position) {
                getSupportActionBar().setSubtitle(mTabAdapter.getPageTitle(position));
            }

            @Override public void onPageScrollStateChanged(int state) {}
        });

        getSupportActionBar().setSubtitle(mTabAdapter.getPageTitle(mViewPager.getCurrentItem()));
        mPresenter.initializeView(getIntent().getParcelableExtra(EXTRA_PEDIDO_EDICAO));
    }

    @Override protected void onStart() {
        super.onStart();
        mPresenter.registerEventBus();
    }

    @Override public void navigateToStepListaProdutos() {
        addStepListaProdutos(null);
        mViewPager.setCurrentItem(PAGE_LISTA_PRODUTOS, true);
    }

    @Override public void navigateToStepListaClientes() {
        addStepListaClientes(null);
        mViewPager.setCurrentItem(PAGE_LISTA_CLIENTES, true);
    }

    @Override public void navigateToStepFinalizandoPedido() {
        addStepFinalizandoPedido();
        mViewPager.setCurrentItem(PAGE_FINALIZANDO_PEDIDO, true);
    }

    @Override public void initializeSteps(List<ItemPedido> itensPedido, Cliente cliente) {
        addStepListaProdutos(itensPedido);
        addStepListaClientes(cliente);
        addStepFinalizandoPedido();
    }

    private void addStepListaProdutos(List<ItemPedido> itensPedido) {
        if (!mTabAdapter.hasPosition(PAGE_LISTA_PRODUTOS)) {
            mTabAdapter.addFragment(ListaProdutosFragment.newInstance(true, itensPedido),
                    getString(R.string.title_fragment_selecione_produtos));
        }
    }

    private void addStepListaClientes(Cliente cliente) {
        if (!mTabAdapter.hasPosition(PAGE_LISTA_CLIENTES)) {
            mTabAdapter.addFragment(ListaClientesFragment.newInstance(true, cliente),
                    getString(R.string.title_fragment_selecione_cliente));
        }
    }

    private void addStepFinalizandoPedido() {
        if (!mTabAdapter.hasPosition(PAGE_FINALIZANDO_PEDIDO)) {
            final Pedido pedidoEmEdicao = getIntent().getParcelableExtra(EXTRA_PEDIDO_EDICAO);
            mTabAdapter.addFragment(FinalizandoPedidoFragment.newInstance(pedidoEmEdicao),
                    getString(R.string.title_fragment_finalizando_pedido));
        }
    }

    @Override protected void onStop() {
        mPresenter.unregisterEventBus();
        super.onStop();
    }

    @Override protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}
