package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.NewCustomerEvent;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ObservableUtils;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent.newEvent;
import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class ListaClientesPresenter extends BasePresenter<ListaClientesContract.View>
        implements ListaClientesContract.Presenter {

    private final ClienteRepository mClienteRepository;

    private final boolean mIsSelectionMode;
    private final Cliente mClientePedidoEmEdicao;

    private List<Cliente> mClientes;

    ListaClientesPresenter(
            final ClienteRepository clienteRepository,
            final boolean isSelectionMode, final Cliente clientePedidoEmEdicao) {
        mClienteRepository = clienteRepository;
        mIsSelectionMode = isSelectionMode;
        mClientePedidoEmEdicao = clientePedidoEmEdicao;
    }

    @Override public void loadClientes() {
        Observable<List<Cliente>> clientesFromMemoryCache = ObservableUtils.toObservable(mClientes);

        LoggedUserEvent event = EventBus.getDefault().getStickyEvent(LoggedUserEvent.class);
        final String cpfCnpjVendedor = event.getVendedor().getCpfCnpj();
        final String cnpjEmpresa = event.getVendedor().getEmpresaSelecionada().getCnpj();

        Observable<List<Cliente>> clientesFromDiskCache = mClienteRepository
                .findByVendedorAndEmpresa(cpfCnpjVendedor, cnpjEmpresa)
                .doOnNext(clientes -> mClientes = clientes);

        addSubscription(Observable
                .merge(clientesFromMemoryCache, clientesFromDiskCache)
                .lastOrDefault(emptyList())
                .observeOn(mainThread())
                .subscribe(new Subscriber<List<Cliente>>() {
                    @Override public void onStart() {
                        getView().showLoading();
                    }

                    @Override public void onError(Throwable e) {
                        Timber.e(e, "Could not load customers list from local database");
                        getView().hideLoading();
                    }

                    @Override public void onNext(List<Cliente> pClienteList) {
                        getView().showClientes(mClientes);
                    }

                    @Override public void onCompleted() {
                        preSelectCliente();
                    }
                }));
    }

    private void preSelectCliente() {
        if (mClientePedidoEmEdicao != null) {
            int indexOf = mClientes.indexOf(mClientePedidoEmEdicao);
            if (indexOf != -1) {
                getView().updateChangedItemAtPosition(indexOf);
            }
            EventBus.getDefault().postSticky(newEvent(mClientePedidoEmEdicao));
        }
    }

    @Override public void handleItemSelected(final int position) {
        if (position >= 0 && position < mClientes.size()) {
            final Cliente cliente = mClientes.get(position);

            if (mIsSelectionMode) {
                EventBus.getDefault().postSticky(newEvent(cliente));
            } else {
                getView().navigateToCadastroCliente(cliente);
            }
        }
    }

    @Override public void handleResultClienteEditado(final Cliente clienteEditado) {
        int position = mClientes.indexOf(clienteEditado);
        if (position != -1) {
            mClientes.set(position, clienteEditado);
            getView().updateChangedItemAtPosition(position);
        }
    }

    @Override public void refreshCustomerList() {
        loadClientes();
    }

    @Subscribe public void onNewCustomerEvent(NewCustomerEvent event) {
        final Cliente cliente = event.getCliente();
        final int position = mClientes.size();
        mClientes.add(cliente);
        getView().updateInsertedItemAtPosition(position);
    }
}
