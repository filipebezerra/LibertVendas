package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido.NovoPedidoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos.ProdutosSelecionadosEvent;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos.SelecioneProdutosFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
public class OldPedidoActivity extends LibertVendasActivity {

    public static final String EXTRA_RESULT_NEW_PEDIDO
            = OldPedidoActivity.class.getSimpleName() + ".extraResultNewPedido";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAsInitialFlowActivity();
        navigate().toSelecioneProdutos();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.activity_old_pedido;
    }

    @Override protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = MAIN) public void onProdutosSelecionadosEvent(
            ProdutosSelecionadosEvent pEvent) {
        navigate().toFinalizaPedido(pEvent.getProdutoVoList(), pEvent.getTabelaPreco());
    }

    @Subscribe(threadMode = MAIN) public void onClienteSelecionadoEvent(
            ClienteSelecionadoEvent pEvent) {
        getSupportFragmentManager().popBackStack();
        setTitle(R.string.title_fragment_finalizando_pedido);
    }

    @Subscribe(threadMode = MAIN) public void onNovoPedidoEvent(NovoPedidoEvent pEvent) {
        Intent extras = new Intent().putExtra(EXTRA_RESULT_NEW_PEDIDO, pEvent.getPedido());
        setResult(RESULT_OK, extras);
        finish();
    }

    @Override public void onBackPressed() {
        final Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (fragment instanceof SelecioneProdutosFragment) {
            super.onBackPressed();
        } else {
            navigate().toSelecioneProdutos();
        }
    }
}
