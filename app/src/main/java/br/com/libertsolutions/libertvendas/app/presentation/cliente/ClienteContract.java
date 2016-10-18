package br.com.libertsolutions.libertvendas.app.presentation.cliente;

/**
 * @author Filipe Bezerra
 */
interface ClienteContract {
    interface View {

        void finishView();
    }

    interface Presenter {
        void clickActionSave();
    }
}
