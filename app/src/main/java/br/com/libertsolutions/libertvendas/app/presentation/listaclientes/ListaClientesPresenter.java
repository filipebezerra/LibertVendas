package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteService;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author Filipe Bezerra
 */

class ListaClientesPresenter implements ListaClientesContract.Presenter {
    private final ListaClientesContract.View mView;
    private final ClienteService mClienteService;

    ListaClientesPresenter(
            ListaClientesContract.View pView, ClienteService pClienteService) {
        mView = pView;
        mClienteService = pClienteService;
    }

    @Override
    public void loadListaClientes() {
        mClienteService.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showListaClientes);
    }
}
