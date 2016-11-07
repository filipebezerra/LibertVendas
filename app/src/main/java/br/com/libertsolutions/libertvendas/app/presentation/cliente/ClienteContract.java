package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface ClienteContract {
    interface View {
        void finishView();

        void showTiposPessoa(List<String> pTiposPessoa);

        void showEstados(List<Estado> pEstadoList);

        void showCidades(List<Cidade> pCidadeList);

        ClienteViewModel extractViewModel();

        void showViewsForPessoaFisica(int pCpfLenght);

        void showViewsForPessoaJuridica(int pCnpjLenght);

        void removeFocusOnFieldCpfCnpj();

        void hideRequiredMessages();

        void displayRequiredMessageForFieldCidade();

        void displayRequiredMessageForFieldEstado();

        void displayRequiredMessageForFieldNome();

        void displayRequiredMessageForFieldTipoPessoa();

        void displayRequiredMessageForFieldCpfCnpj();

        void showFeedbackMessage(String pMessage);

        void resultNewCliente(Cliente newCliente);

        void showExitViewQuestion();

        void showFocusOnFieldCpfCnpj();
    }

    interface Presenter {
        void initializeView();

        void clickActionSave();

        void clickSelectTipoPessoa();

        void clickSelectEstado();

        void handleBackPressed();

        void finalizeView();
    }
}
