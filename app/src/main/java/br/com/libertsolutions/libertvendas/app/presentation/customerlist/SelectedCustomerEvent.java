package br.com.libertsolutions.libertvendas.app.presentation.customerlist;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;

/**
 * @author Filipe Bezerra
 */
public class SelectedCustomerEvent {

    private final Customer mCustomer;

    private SelectedCustomerEvent(final Customer customer) {
        mCustomer = customer;
    }

    public static SelectedCustomerEvent selectCustomer(final Customer customer) {
        return new SelectedCustomerEvent(customer);
    }

    public Customer getCustomer() {
        return mCustomer;
    }
}
