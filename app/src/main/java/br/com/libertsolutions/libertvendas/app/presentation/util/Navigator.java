package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;

import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.home.HomeActivity;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ListaClientesFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosFragment;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoginActivity;
import br.com.libertsolutions.libertvendas.app.presentation.settings.SettingsActivity;

/**
 * @author Filipe Bezerra
 */

public class Navigator {
    public static final int REQUEST_SETTINGS = 0x1;

    public static final int RESULT_OK = Activity.RESULT_OK;
    public static final int RESULT_CANCELED = Activity.RESULT_CANCELED;

    private final LibertVendasActivity mActivity;

    public Navigator(LibertVendasActivity activity) {
        mActivity = activity;
    }

    public void toSettings() {
        final Intent settingsIntent = new Intent(mActivity, SettingsActivity.class);
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

    public void toClientes(FragmentManager pSupportFragmentManager) {
        pSupportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ListaClientesFragment.newInstance())
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_clientes);
    }

    public void toProdutos(FragmentManager pSupportFragmentManager) {
        pSupportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ListaProdutosFragment.newInstance())
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_produtos);
    }
}
