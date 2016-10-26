package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos.SelecioneProdutosFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Filipe Bezerra
 */
public class PedidoActivity extends LibertVendasActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAsInitialFlowActivity();
        navigate().toSelecioneProdutos();
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_pedido;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onNavigateToNextEvent(NavigateToNextEvent pEvent) {
        final Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (fragment instanceof SelecioneProdutosFragment) {
            navigate().toFinalizaPedido();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        final Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (fragment instanceof SelecioneProdutosFragment) {
            super.onBackPressed();
        } else {
            navigate().toSelecioneProdutos();
        }
    }
}
