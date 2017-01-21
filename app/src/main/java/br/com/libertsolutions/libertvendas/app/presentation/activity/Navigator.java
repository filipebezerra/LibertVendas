package br.com.libertsolutions.libertvendas.app.presentation.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.CadastroClienteActivity;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.CadastroPedidoActivity;
import br.com.libertsolutions.libertvendas.app.presentation.dashboard.DashboardFragment;
import br.com.libertsolutions.libertvendas.app.presentation.home.HomeActivity;
import br.com.libertsolutions.libertvendas.app.presentation.importacao.ImportacaoActivity;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ListaClientesFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listapedidos.OrderListPageFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosFragment;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoginActivity;
import br.com.libertsolutions.libertvendas.app.presentation.orderdetail.OrderDetailActivity;
import br.com.libertsolutions.libertvendas.app.presentation.settings.SettingsActivity;

/**
 * @author Filipe Bezerra
 */
public final class Navigator {

    public static final int RESULT_CANCELED = LibertVendasActivity.RESULT_CANCELED;

    public static final int RESULT_OK = LibertVendasActivity.RESULT_OK;

    public static final int REQUEST_SETTINGS = 0x1;

    public static final int REQUEST_EDITAR_CLIENTE = 0x2;

    public static final int REQUEST_EDITAR_PEDIDO = 0x3;

    private final LibertVendasActivity mActivity;

    Navigator(LibertVendasActivity activity) {
        mActivity = activity;
    }

    public void toSettings(final boolean isInitialConfiguration) {
        final Intent settingsIntent = new Intent(mActivity, SettingsActivity.class);

        if (isInitialConfiguration) {
            settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ActivityCompat.startActivity(mActivity, settingsIntent, null);
        } else {
            ActivityCompat
                    .startActivityForResult(mActivity, settingsIntent, REQUEST_SETTINGS, null);
        }
    }

    public void toHome() {
        final Intent homeIntent = IntentCompat
                .makeMainActivity(new ComponentName(mActivity, HomeActivity.class))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityCompat.startActivity(mActivity, homeIntent, null);
    }

    public void toLogin() {
        final Intent loginIntent = new Intent(mActivity, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityCompat.startActivity(mActivity, loginIntent, null);
    }

    public void toImportacao() {
        final Intent importationIntent = new Intent(mActivity, ImportacaoActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityCompat.startActivity(mActivity, importationIntent, null);
    }

    public void toDashboard() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        DashboardFragment.newInstance(), DashboardFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.title_fragment_dashboard);
    }

    public void toCustomers() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        ListaClientesFragment.newInstance(false, null), ListaClientesFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_clientes);
    }

    public void toProducts() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        ListaProdutosFragment.newInstance(false, null), ListaProdutosFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_produtos);
    }

    public void toOrders() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        OrderListPageFragment.newInstance(), OrderListPageFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_pedidos);
    }

    public void toCadastroCliente(@Nullable final Cliente cliente) {
        final Intent cadastroClienteIntent = new Intent(mActivity, CadastroClienteActivity.class);

        if (cliente == null) {
            ActivityCompat.startActivity(mActivity, cadastroClienteIntent, null);
        } else {
            cadastroClienteIntent.putExtra(CadastroClienteActivity.EXTRA_CLIENTE_EDICAO, cliente);
            ActivityCompat.startActivityForResult(
                    mActivity, cadastroClienteIntent, REQUEST_EDITAR_CLIENTE, null);
        }
    }

    public void toCadastroPedido(@Nullable final Pedido pedido) {
        final Intent cadastroPedidoIntent = new Intent(mActivity, CadastroPedidoActivity.class);

        if (pedido == null) {
            ActivityCompat.startActivity(mActivity, cadastroPedidoIntent, null);
        } else {
            cadastroPedidoIntent.putExtra(CadastroPedidoActivity.EXTRA_PEDIDO_EDICAO, pedido);
            ActivityCompat.startActivityForResult(
                    mActivity, cadastroPedidoIntent, REQUEST_EDITAR_PEDIDO, null);
        }
    }

    public void toOrderDetail(@NonNull final Pedido order) {
        OrderDetailActivity.create(mActivity, order);
    }
}
