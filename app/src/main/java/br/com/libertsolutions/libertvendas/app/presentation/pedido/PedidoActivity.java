package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ListaClientesFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosFragment;
import br.com.libertsolutions.libertvendas.app.presentation.widget.TabAdapter;
import butterknife.BindView;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_finalizando_pedido;
import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_selecione_cliente;
import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_selecione_produtos;

/**
 * @author Filipe Bezerra
 */
public class PedidoActivity extends LibertVendasActivity implements PedidoContract.View {

    private static final int PAGE_LISTA_PRODUTOS = 0;
    private static final int PAGE_LISTA_CLIENTES = 1;
    private static final int PAGE_FINALIZANDO_PEDIDO = 2;

    @BindView(R.id.fragment_container) protected ViewPager mViewPager;

    private PedidoContract.Presenter mPresenter;

    private TabAdapter mTabAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAsInitialFlowActivity();
        mPresenter = new PedidoPresenter();
        mPresenter.attachView(this);

        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mTabAdapter.addFragment(ListaProdutosFragment.newInstance(true),
                getString(title_fragment_selecione_produtos));

        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSubtitle(mTabAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        getSupportActionBar().setSubtitle(mTabAdapter.getPageTitle(mViewPager.getCurrentItem()));
    }

    @Override protected int provideContentViewResource() {
        return R.layout.activity_pedido;
    }

    @Override public void goToListaClientesStep() {
        if (!mTabAdapter.hasPosition(PAGE_LISTA_CLIENTES)) {
            mTabAdapter.addFragment(ListaClientesFragment.newInstance(true),
                    getString(title_fragment_selecione_cliente));
        }

        mViewPager.setCurrentItem(PAGE_LISTA_CLIENTES, true);
    }

    @Override public void goToFinalizandoPedidoStep(List<ProdutoVo> pProdutosSelecionados,
            TabelaPreco pTabelaPrecoPadrao, Cliente pClienteSelecionado) {
        if (!mTabAdapter.hasPosition(PAGE_FINALIZANDO_PEDIDO)) {
            FinalizandoPedidoFragment fragment = FinalizandoPedidoFragment
                    .newInstance(pProdutosSelecionados, pTabelaPrecoPadrao, pClienteSelecionado);
            mTabAdapter.addFragment(fragment, getString(title_fragment_finalizando_pedido));
        }

        mViewPager.setCurrentItem(PAGE_FINALIZANDO_PEDIDO, true);
    }

    @Override public void finishView() {
        finish();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

}
