package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaService;
import br.com.libertsolutions.libertvendas.app.domain.dto.TabelaDto;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.util.List;
import rx.Subscriber;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class ListaProdutosPresenter extends BasePresenter<ListaProdutosContract.View>
    implements ListaProdutosContract.Presenter {

    private final TabelaService mTabelaService;

    ListaProdutosPresenter(final TabelaService tabelaService) {
        mTabelaService = tabelaService;
    }

    @Override public void loadListaProdutos() {
        addSubscription(mTabelaService
                .get("26133749000109")
                .observeOn(mainThread())
                .subscribe(
                        new Subscriber<List<TabelaDto>>() {
                            @Override public void onStart() {
                                getView().showLoading();
                            }

                            @Override public void onError(final Throwable e) {

                            }

                            @Override public void onNext(final List<TabelaDto> tabelaDtos) {

                            }

                            @Override public void onCompleted() {}
                        }
                ));
    }
}
