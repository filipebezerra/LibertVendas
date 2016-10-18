package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoService;
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

    private final FormaPagamentoService mFormaPagamentoService;

    private List<FormaPagamento> mFormaPagamentoList;

    FinalizaPedidoPresenter(
            FinalizaPedidoContract.View pView, FormaPagamentoService pFormaPagamentoService) {
        mView = pView;
        mFormaPagamentoService = pFormaPagamentoService;
    }

    @Override
    public void initializeView() {
        mFormaPagamentoService
                .get()
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
}
