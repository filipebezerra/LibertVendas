package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.NovoClienteEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.ObservableUtils;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
class ListaClientesPresenter extends BasePresenter<ListaClientesContract.View>
        implements ListaClientesContract.Presenter {

    private final boolean mToSelect;

    private final ClienteRepository mClienteRepository;

    private List<Cliente> mClienteList;

    ListaClientesPresenter(boolean pToSelect, ClienteRepository pClienteRepository) {
        mToSelect = pToSelect;
        mClienteRepository = pClienteRepository;
    }

    @Override public void loadListaClientes() {
        Observable<List<Cliente>> clientesFromMemoryCache = ObservableUtils
                .toObservable(mClienteList);

        Observable<List<Cliente>> clientesFromDiskCache = mClienteRepository
                .list()
                .doOnNext(this::saveClientesToMemoryCache);

        addSubscription(Observable
                .concat(clientesFromMemoryCache, clientesFromDiskCache)
                .firstOrDefault(Collections.emptyList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cliente>>() {
                    @Override public void onStart() {
                        getView().showLoading();
                    }

                    @Override public void onError(Throwable e) {
                        Timber.e(e);
                        getView().hideLoading();
                    }

                    @Override public void onNext(List<Cliente> pClienteList) {
                        getView().showListaClientes(mClienteList);
                    }

                    @Override public void onCompleted() {}
                }));
    }

    private void saveClientesToMemoryCache(List<Cliente> pClienteList) {
        mClienteList = pClienteList;
    }

    @Override public void handleSingleTapUp(int pPosition) {
        if (pPosition >= 0 && pPosition < mClienteList.size()) {
            final Cliente cliente = mClienteList.get(pPosition);

            if (mToSelect) {
                EventBus.getDefault().postSticky(ClienteSelecionadoEvent.newEvent(cliente));
            } else {
                getView().navigateToCliente(cliente);
            }
        }
    }

    @Override public void handleResultClienteEditado(Cliente pClienteEditado) {
        int position = mClienteList.indexOf(pClienteEditado);
        if (position != -1) {
            mClienteList.set(position, pClienteEditado);
            getView().updateChangedItemAtPosition(position);
        }
    }

    @Subscribe(threadMode = MAIN) public void onClienteSavedEvent(NovoClienteEvent pEvent) {
        final Cliente cliente = pEvent.getEventValue();
        final int position = mClienteList.size();
        mClienteList.add(cliente);
        getView().updateInsertedItemAtPosition(position);
    }
}
