package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.FinalizandoPedidoFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ListaClientesFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosFragment;
import br.com.libertsolutions.libertvendas.app.presentation.widget.TabAdapter;
import butterknife.BindView;

import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_finalizando_pedido;
import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_selecione_cliente;
import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_selecione_produtos;

/**
 * @author Filipe Bezerra
 */
public class CadastroPedidoActivity extends LibertVendasActivity
        implements CadastroPedidoContract.View {

    private static final int PAGE_LISTA_CLIENTES = 1;
    private static final int PAGE_FINALIZANDO_PEDIDO = 2;

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
        mTabAdapter.addFragment(ListaProdutosFragment.newInstance(true),
                getString(title_fragment_selecione_produtos));

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
    }

    @Override protected void onStart() {
        super.onStart();
        mPresenter.registerForEvents();
    }

    @Override public void navigateToStepListaClientes() {
        if (!mTabAdapter.hasPosition(PAGE_LISTA_CLIENTES)) {
            mTabAdapter.addFragment(ListaClientesFragment.newInstance(true),
                    getString(title_fragment_selecione_cliente));
        }

        mViewPager.setCurrentItem(PAGE_LISTA_CLIENTES, true);
    }

    @Override public void navigateToStepFinalizandoPedido() {
        if (!mTabAdapter.hasPosition(PAGE_FINALIZANDO_PEDIDO)) {
            Pedido pedidoEmEdicao = getIntent().getParcelableExtra(EXTRA_PEDIDO_EDICAO);
            mTabAdapter.addFragment(FinalizandoPedidoFragment.newInstance(pedidoEmEdicao),
                    getString(title_fragment_finalizando_pedido));
        }

        mViewPager.setCurrentItem(PAGE_FINALIZANDO_PEDIDO, true);
    }

    @Override protected void onStop() {
        super.onStop();
        mPresenter.unregisterForEvents();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
