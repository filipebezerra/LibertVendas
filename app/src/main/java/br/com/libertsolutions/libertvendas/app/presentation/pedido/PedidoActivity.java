package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoFragment;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.listaclientes.ListaClientesFragment;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.listaprodutos.ListaProdutosFragment;
import br.com.libertsolutions.libertvendas.app.presentation.widget.TabAdapter;
import butterknife.BindView;

import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_finalizando_pedido;
import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_selecione_cliente;
import static br.com.libertsolutions.libertvendas.app.R.string.title_fragment_selecione_produtos;

/**
 * @author Filipe Bezerra
 */
public class PedidoActivity extends LibertVendasActivity implements PedidoContract.View {

    @BindView(R.id.fragment_container) protected ViewPager mViewPager;

    private PedidoContract.Presenter mPresenter;

    private TabAdapter mTabAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAsInitialFlowActivity();
        mPresenter = new PedidoPresenter();
        mPresenter.attachView(this);

        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mTabAdapter.addFragment(ListaProdutosFragment.newInstance(),
                getString(title_fragment_selecione_produtos));
        mTabAdapter.addFragment(ListaClientesFragment.newInstance(),
                getString(title_fragment_selecione_cliente));

        mViewPager.setAdapter(mTabAdapter);
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

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    @Override public void goToFinalizandoPedidoStep() {
        if (mTabAdapter.getCount() == 2) {
            mTabAdapter.addFragment(FinalizandoPedidoFragment.newInstance(),
                    getString(title_fragment_finalizando_pedido));
        }

        mViewPager.setCurrentItem(3, true);
    }
}
