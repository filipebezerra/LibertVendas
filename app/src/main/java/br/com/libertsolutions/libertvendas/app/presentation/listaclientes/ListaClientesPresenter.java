package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Filipe Bezerra
 */

class ListaClientesPresenter implements ListaClientesContract.Presenter {
    private final ListaClientesContract.View mView;
    private final Repository<Cliente> mClienteRepository;

    ListaClientesPresenter(
            ListaClientesContract.View pView, Repository<Cliente> pClienteRepository) {
        mView = pView;
        mClienteRepository = pClienteRepository;
    }

    @Override
    public void loadListaClientes() {
        mClienteRepository.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showListaClientes);
    }
}
