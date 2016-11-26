package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.util.ObservableUtils;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
class ListaClientesPresenter extends BasePresenter<ListaClientesContract.View>
    implements ListaClientesContract.Presenter {

    private final boolean mToSelect;

    private final Repository<Cliente> mClienteRepository;

    private List<Cliente> mClienteList;

    ListaClientesPresenter(boolean pToSelect, Repository<Cliente> pClienteRepository) {
        mToSelect = pToSelect;
        mClienteRepository = pClienteRepository;
    }

    @Override public void loadListaClientes() {
        Observable<List<Cliente>> clientesFromMemoryCache = ObservableUtils
                .toObservable(mClienteList);

        Observable<List<Cliente>> clientesFromDiskCache = mClienteRepository
                .list()
                .doOnNext(this::saveClientesToMemoryCache);

        Subscription listListaClientesSubscription = Observable
                .concat(clientesFromMemoryCache, clientesFromDiskCache)
                .firstOrDefault(Collections.emptyList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cliente>>() {
                    @Override
                    public void onStart() {
                        getView().showLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        getView().hideLoading();
                    }

                    @Override
                    public void onNext(List<Cliente> pClienteList) {
                        getView().showListaClientes(mClienteList);
                    }

                    @Override
                    public void onCompleted() {}
                });
        addSubscription(listListaClientesSubscription);
    }

    private void saveClientesToMemoryCache(List<Cliente> pClienteList) {
        mClienteList = pClienteList;
    }

    @Override public void handleSingleTapUp(int pPosition) {
        if (pPosition >= 0 && pPosition < mClienteList.size()) {
            final Cliente cliente = mClienteList.get(pPosition);

            if (mToSelect) {
                EventBus.getDefault().post(ClienteSelecionadoEvent.newEvent(cliente));
            } else {
                getView().navigateToCliente(cliente);
            }
        }
    }

}
