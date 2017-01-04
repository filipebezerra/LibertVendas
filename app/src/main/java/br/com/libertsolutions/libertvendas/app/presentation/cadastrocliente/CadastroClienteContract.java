package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TipoPessoa;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface CadastroClienteContract {

    interface View extends MvpView {

        void setViewFields(List<Integer> viewIds);

        void setRequiredFields(List<Integer> requiredViewIds);

        void displayTiposPessoa(List<TipoPessoa> tiposPessoa);

        void displayEstados(List<Estado> estados);

        void setViewValue(int viewId, int itemPosition);

        void setViewValue(final int viewId, final String viewValue);

        void changeTitle(String newTitle);

        void changeViewForTipoPessoa(int charCount);

        void showFocusOnFieldCpfOuCnpj();

        void removeFocusOnFieldCpfOuCnpj();

        void displayCidades(List<Cidade> cidades);

        void updateCidades();

        void hideRequiredMessages();

        boolean hasEmptyRequiredFields();

        void displayRequiredFieldMessages();

        String getViewStringValue(int viewId);

        int getViewPositionValue(int viewId);

        void resultClienteEditado(Cliente clienteEditado);

        boolean hasUnmodifiedFields();

        void showExitViewQuestion();

        void finishView();
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView(Cliente clienteEmEdicao);

        void handleActionSave();

        void handleTipoPessoaSelected(int position);

        void handleNoneTipoPessoaSelected();

        void handleEstadoSelected(int position);

        void handleNoneEstadoSelected();

        void handleExiting();
    }
}
