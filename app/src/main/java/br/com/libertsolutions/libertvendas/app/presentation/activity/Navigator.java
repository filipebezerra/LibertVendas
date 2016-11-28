package br.com.libertsolutions.libertvendas.app.presentation.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.CadastroClienteActivity;
import br.com.libertsolutions.libertvendas.app.presentation.home.HomeActivity;
import br.com.libertsolutions.libertvendas.app.presentation.importacao.ImportacaoActivity;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ListaClientesFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listapedidos.TabsFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosFragment;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoginActivity;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.PedidoActivity;
import br.com.libertsolutions.libertvendas.app.presentation.settings.SettingsActivity;

/**
 * @author Filipe Bezerra
 */
public class Navigator {

    public static final int REQUEST_SETTINGS = 0x1;

    public static final int REQUEST_EDITAR_CLIENTE = 0x2;

    public static final int RESULT_OK = Activity.RESULT_OK;

    public static final int RESULT_CANCELED = Activity.RESULT_CANCELED;

    private final LibertVendasActivity mActivity;

    Navigator(LibertVendasActivity activity) {
        mActivity = activity;
    }

    public void toSettings(boolean isFromLaunch) {
        final Intent settingsIntent = new Intent(mActivity, SettingsActivity.class)
                .putExtra(SettingsActivity.EXTRA_FROM_LAUNCH, isFromLaunch);
        ActivityCompat.startActivityForResult(mActivity, settingsIntent, REQUEST_SETTINGS, null);
    }

    public void toLogin() {
        final Intent loginIntent = new Intent(mActivity, LoginActivity.class);
        ActivityCompat.startActivity(mActivity, loginIntent, null);
    }

    public void toHome() {
        final Intent homeIntent = new Intent(mActivity, HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityCompat.startActivity(mActivity, homeIntent, null);
    }

    public void toListaClientes(boolean pToSelect) {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ListaClientesFragment.newInstance(pToSelect))
                .addToBackStack(null)
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_clientes);
    }

    public void toListaProdutos(boolean pToSelect) {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ListaProdutosFragment.newInstance(pToSelect))
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_produtos);
    }

    public void toPedidos() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, TabsFragment.newInstance())
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_pedidos);
    }

    public void toPedido() {
        final Intent pedidoIntent = new Intent(mActivity, PedidoActivity.class);
        ActivityCompat.startActivity(mActivity, pedidoIntent, null);
    }

    public void toCadastroCliente(@Nullable Cliente pCliente) {
        final Intent cadastroClienteIntent = new Intent(mActivity, CadastroClienteActivity.class);

        if (pCliente == null) {
            ActivityCompat.startActivity(mActivity, cadastroClienteIntent, null);
        } else {
            cadastroClienteIntent.putExtra(CadastroClienteActivity.EXTRA_CLIENTE_EDICAO, pCliente);
            ActivityCompat.startActivityForResult(
                    mActivity, cadastroClienteIntent, REQUEST_EDITAR_CLIENTE, null);
        }
    }

    public void toImportacao() {
        final Intent importacaoIntent = new Intent(mActivity, ImportacaoActivity.class);
        ActivityCompat.startActivity(mActivity, importacaoIntent, null);
    }
}
