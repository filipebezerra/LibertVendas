package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.NewCustomerEvent;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.PresentationInjection.provideEventBus;
import static br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent.newEvent;
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

    private Vendedor mLoggedUser;

    ListaClientesPresenter(
            final ClienteRepository clienteRepository,
            final boolean isSelectionMode, final Cliente clientePedidoEmEdicao) {
        mClienteRepository = clienteRepository;
        mIsSelectionMode = isSelectionMode;
        mClientePedidoEmEdicao = clientePedidoEmEdicao;
    }

    @Subscribe(sticky = true) public void onLoginEvent(LoggedUserEvent event) {
        if (mLoggedUser != null && !mLoggedUser.equals(event.getVendedor())) {
            mLoggedUser = event.getVendedor();
            loadClientes();
        }
    }

    @Override public void loadClientes() {
        addSubscription(getClienteAsObservable()
                .observeOn(mainThread())
                .subscribe(new Subscriber<List<Cliente>>() {
                    @Override public void onStart() {
                        getView().showLoading();
                    }

                    @Override public void onError(Throwable e) {
                        Timber.e(e, "Could not load customers");
                        getView().hideLoading();
                    }

                    @Override public void onNext(List<Cliente> clientes) {
                        mClientes = clientes;
                        getView().showClientes(mClientes);
                    }

                    @Override public void onCompleted() {
                        preSelectCliente();
                    }
                }));
    }

    private Observable<List<Cliente>> getClienteAsObservable() {
        return mClienteRepository
                .findByVendedorAndEmpresa(getCpfCnpjVendedor(), getCnpjEmpresaSelecionada());
    }

    private String getCpfCnpjVendedor() {
        return getLoggedUser().getCpfCnpj();
    }

    private String getCnpjEmpresaSelecionada() {
        return getLoggedUser().getEmpresaSelecionada().getCnpj();
    }

    private Vendedor getLoggedUser() {
        if (mLoggedUser == null) {
            mLoggedUser = provideEventBus().getStickyEvent(LoggedUserEvent.class).getVendedor();
        }
        return mLoggedUser;
    }

    private void preSelectCliente() {
        if (mClientePedidoEmEdicao != null) {
            int indexOf = mClientes.indexOf(mClientePedidoEmEdicao);
            if (indexOf != -1) {
                getView().updateChangedItemAtPosition(indexOf);
            }
            provideEventBus().postSticky(newEvent(mClientePedidoEmEdicao));
        }
    }

    @Override public void handleItemSelected(final int position) {
        if (position >= 0 && position < mClientes.size()) {
            final Cliente cliente = mClientes.get(position);

            if (mIsSelectionMode) {
                provideEventBus().postSticky(newEvent(cliente));
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
