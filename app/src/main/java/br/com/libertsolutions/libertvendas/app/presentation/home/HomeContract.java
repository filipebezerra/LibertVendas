package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.util.ExtrasExtractor;

/**
 * @author Filipe Bezerra
 */
interface HomeContract {
    interface View {
        void navigateToSettings();

        void navigateToClientes();

        void navigateToProdutos();

        void navigateToPedidos();
    }

    interface Presenter {
        void clickNavigationMenuSettings();

        void clickNavigationMenuClientes();

        void clickNavigationMenuProdutos();

        void clickNavigationMenuPedidos();

        void getClienteFromResult(ExtrasExtractor<Cliente> pClienteExtrasExtractor);
    }
}
