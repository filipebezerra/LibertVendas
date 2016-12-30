package br.com.libertsolutions.libertvendas.app.presentation.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.importacao.ImportacaoActivity;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosFragment;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoginActivity;
import br.com.libertsolutions.libertvendas.app.presentation.settings.SettingsActivity;

/**
 * @author Filipe Bezerra
 */
public class Navigator {

    public static final int REQUEST_SETTINGS = 0x1;

    public static final int REQUEST_LOGIN = 0x2;

    public static final int REQUEST_IMPORTACAO = 0x3;

    public static final int RESULT_CANCELED = LibertVendasActivity.RESULT_CANCELED;

    public static final int RESULT_OK = LibertVendasActivity.RESULT_OK;

    private final LibertVendasActivity mActivity;

    Navigator(LibertVendasActivity activity) {
        mActivity = activity;
    }

    public void toSettings() {
        final Intent settingsIntent = new Intent(mActivity, SettingsActivity.class);
        ActivityCompat.startActivityForResult(mActivity, settingsIntent, REQUEST_SETTINGS, null);
    }

    public void toListaProdutos() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        ListaProdutosFragment.newInstance(false/*, null*/),
                        ListaProdutosFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.title_fragment_lista_produtos);
    }

    public void toLogin() {
        final Intent loginIntent = new Intent(mActivity, LoginActivity.class);
        ActivityCompat.startActivityForResult(mActivity, loginIntent, REQUEST_LOGIN, null);
    }

    public void toImportacao() {
        final Intent importacaoIntent = new Intent(mActivity, ImportacaoActivity.class);
        ActivityCompat.startActivityForResult(mActivity, importacaoIntent, REQUEST_IMPORTACAO, null);
    }
}
