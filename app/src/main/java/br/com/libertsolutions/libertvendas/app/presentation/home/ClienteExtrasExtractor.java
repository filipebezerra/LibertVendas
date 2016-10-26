package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.content.Intent;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.util.ExtrasExtractor;

import static br.com.libertsolutions.libertvendas.app.presentation.cliente.ClienteActivity.EXTRA_RESULT_NEW_CLIENTE;

/**
 * @author Filipe Bezerra
 */
public class ClienteExtrasExtractor implements ExtrasExtractor<Cliente> {
    private final Intent mData;

    public ClienteExtrasExtractor(Intent pData) {
        mData = pData;
    }

    @Override
    public Cliente extractExtra() {
        if (mData != null && mData.hasExtra(EXTRA_RESULT_NEW_CLIENTE)) {
            return mData.getParcelableExtra(EXTRA_RESULT_NEW_CLIENTE);
        } else {
            return null;
        }
    }
}
