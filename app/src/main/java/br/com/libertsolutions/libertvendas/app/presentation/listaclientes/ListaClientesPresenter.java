package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.util.ObservableUtils;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Filipe Bezerra
 */

class ListaClientesPresenter implements ListaClientesContract.Presenter {
    private final ListaClientesContract.View mView;

    private final Repository<Cliente> mClienteRepository;

    private List<Cliente> mClienteList;

    ListaClientesPresenter(
            ListaClientesContract.View pView, Repository<Cliente> pClienteRepository) {
        mView = pView;
        mClienteRepository = pClienteRepository;
    }

    @Override public void loadListaClientes() {
        Observable<List<Cliente>> clientesFromMemoryCache = ObservableUtils
                .toObservable(mClienteList);

        Observable<List<Cliente>> clientesFromDiskCache = mClienteRepository
                .list()
                .doOnNext(this::saveClientesToMemoryCache);

        Observable
                .concat(clientesFromMemoryCache, clientesFromDiskCache)
                .firstOrDefault(Collections.emptyList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showListaClientes);
    }

    private void saveClientesToMemoryCache(List<Cliente> pClienteList) {
        mClienteList = pClienteList;
    }

    @Override public void addNewClienteCadastrado(Cliente pCliente) {
        final int lastPosition = mClienteList.size();
        mClienteList.add(pCliente);
        mView.updateListaClientes(lastPosition);
    }

    @Override public void handleSingleTapUp(int pPosition) {
        if (pPosition >= 0 && pPosition < mClienteList.size()) {
            final Cliente cliente = mClienteList.get(pPosition);
            EventBus.getDefault().postSticky(ClienteSelecionadoEvent.newEvent(cliente));
        }
    }
}
