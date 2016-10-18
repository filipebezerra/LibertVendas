package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;

/**
 * @author Filipe Bezerra
 */
public class ClienteActivity extends LibertVendasActivity implements ClienteContract.View {

    public static final String EXTRA_FROM_HOME
            = ClienteActivity.class.getSimpleName() + ".extraFromHome";

    private ClienteContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ClientePresenter(this);
        extractExtras();
    }

    private void extractExtras() {
        final Bundle extras = getIntent().getExtras();
        if (extras.containsKey(EXTRA_FROM_HOME)) {
            if (extras.getBoolean(EXTRA_FROM_HOME)) {
                setAsInitialFlowActivity();
            } else {
                setAsSubActivity();
            }
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            mPresenter.clickActionSave();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finishView() {
        finish();
    }
}
