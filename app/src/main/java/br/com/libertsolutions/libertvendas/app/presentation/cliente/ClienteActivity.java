package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import android.os.Bundle;
import android.view.Menu;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;

/**
 * @author Filipe Bezerra
 */
public class ClienteActivity extends LibertVendasActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAsSubActivity();
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_cliente;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
        return true;
    }
}
