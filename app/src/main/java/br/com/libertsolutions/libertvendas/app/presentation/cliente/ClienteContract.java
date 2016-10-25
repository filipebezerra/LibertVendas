package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface ClienteContract {
    interface View {
        void finishView();

        void showTiposPessoa(List<String> pTiposPessoa);

        void showEstados(List<String> pEstados);

        ClienteViewModel extractViewModel();

        void showViewsForPessoaFisica(int pCpfLenght);

        void showViewsForPessoaJuridica(int pCnpjLenght);

        void removeFocusInCpfCnpj();

        void hideRequiredMessages();

        void displayRequiredMessageForFieldCidade();

        void displayRequiredMessageForFieldEstado();

        void displayRequiredMessageForFieldNome();

        void displayRequiredMessageForFieldTipoPessoa();

        void displayRequiredMessageForFieldCpfCnpj();

        void showFeedbackMessage(String pMessage);

        void resultNewCliente(Cliente newCliente);

        void showExitViewQuestion();
    }

    interface Presenter {
        void initializeView();

        void clickActionSave();

        void clickSelectTipoPessoa();

        void handleBackPressed();

        void finalizeView();
    }
}
