package br.com.libertsolutions.libertvendas.app.presentation.addcustomer;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;

/**
 * @author Filipe Bezerra
 */
public class SavedCustomerEvent {

    private final Customer mCustomer;

    private SavedCustomerEvent(final Customer customer) {
        mCustomer = customer;
    }

    static SavedCustomerEvent newEvent(final Customer customer) {
        return new SavedCustomerEvent(customer);
    }

    public Customer getCustomer() {
        return mCustomer;
    }
}
