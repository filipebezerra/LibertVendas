package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import android.os.Bundle;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;

public class PedidoActivity extends LibertVendasActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAsInitialFlowActivity();
        navigate().toSelecioneProdutos(getSupportFragmentManager());
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_pedido;
    }
}
