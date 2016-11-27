package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TipoPessoa;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface CadastroClienteContract {

    interface View extends MvpView {

        void setViewFields(List<Integer> pViewIds);

        void setRequiredFields(List<Integer> pRequiredViewIds);

        void displayTiposPessoa(List<TipoPessoa> pTiposPessoa);

        void displayEstados(List<Estado> pEstados);

        void displayCidades(List<Cidade> pCidadesList);

        void changeTitle(String pNewTitle);

        void updateCidades();

        void setViewValue(final int pViewId, final String pViewValue);

        void setViewValue(final int pViewId, final int pPosition);

        int getViewPositionValue(final int pViewId);

        String getViewStringValue(final int pViewId);

        void changeViewForTipoPessoa(int pCharCount);

        void showFocusOnFieldCpfOuCnpj();

        void removeFocusOnFieldCpfOuCnpj();

        void hideRequiredMessages();

        boolean hasEmptyRequiredFields();

        void displayRequiredFieldMessages();

        boolean hasUnmodifiedFields();

        void showExitViewQuestion();

        void resultClienteEditado(Cliente pCliente);

        void finishView();
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView(Cliente pClienteFromExtra);

        void handleActionSave();

        void handleTiposPessoaSpinnerItemSelected(int pPosition);

        void handleTiposPessoaSpinnerNothingSelected();

        void handleEstadosItemSelected(int pPosition);

        void handleEstadosSpinnerNothingSelected();

        void handleBackPressed();
    }
}
