package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.NavigateToNextEvent;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Filipe Bezerra
 */
class FinalizaPedidoPresenter implements FinalizaPedidoContract.Presenter {
    private final FinalizaPedidoContract.View mView;

    private final Repository<FormaPagamento> mFormaPagamentoRepository;

    private List<FormaPagamento> mFormaPagamentoList;

    FinalizaPedidoPresenter(
            FinalizaPedidoContract.View pView,
            Repository<FormaPagamento> pFormaPagamentoRepository) {
        mView = pView;
        mFormaPagamentoRepository = pFormaPagamentoRepository;
    }

    @Override
    public void initializeView() {
        mFormaPagamentoRepository
                .list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pFormaPagamentos -> {
                            mFormaPagamentoList = pFormaPagamentos;
                            mView.bindFormasPagamento(mFormaPagamentoList);
                        }
                );
    }

    @Override
    public void clickActionSave() {
        EventBus.getDefault().post(NavigateToNextEvent.notifyEvent());
    }

    @Override
    public void clickSelectCliente() {
        mView.navigateToListaClientesActivity();
    }
}
