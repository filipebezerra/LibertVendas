package br.com.libertsolutions.libertvendas.app.presentation.cliente;

/**
 * @author Filipe Bezerra
 */
class ClientePresenter implements ClienteContract.Presenter {
    private final ClienteContract.View mView;

    ClientePresenter(ClienteContract.View pView) {
        mView = pView;
    }

    @Override
    public void clickActionSave() {
        mView.finishView();
    }
}
