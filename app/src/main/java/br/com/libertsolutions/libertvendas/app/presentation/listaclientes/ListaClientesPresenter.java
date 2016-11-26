package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.home.NewClienteCadastradoEvent;
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

    private final Repository<Cliente> mClienteRepository;

    private List<Cliente> mClienteList;

    ListaClientesPresenter(Repository<Cliente> pClienteRepository) {
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
                });
    }

    private void saveClientesToMemoryCache(List<Cliente> pClienteList) {
        mClienteList = pClienteList;
    }

    @Override public void handleSingleTapUp(int pPosition) {
        if (pPosition >= 0 && pPosition < mClienteList.size()) {
            final Cliente cliente = mClienteList.get(pPosition);
            EventBus.getDefault().postSticky(ClienteSelecionadoEvent.newEvent(cliente));
        }
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onNewClienteCadastrado(
            NewClienteCadastradoEvent pEvent) {
        Cliente cliente = pEvent.getCliente();
        EventBus.getDefault().removeStickyEvent(pEvent);
        if (cliente != null) {
            final int lastPosition = mClienteList.size();
            mClienteList.add(cliente);
            getView().updateListaClientes(lastPosition);
        }
    }

}
