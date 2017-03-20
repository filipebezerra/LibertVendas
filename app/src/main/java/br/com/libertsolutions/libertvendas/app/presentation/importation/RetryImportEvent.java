package br.com.libertsolutions.libertvendas.app.presentation.importation;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;

/**
 * @author Filipe Bezerra
 */
class RetryImportEvent {

    private final Company mCompany;

    private RetryImportEvent(final Company company) {
        mCompany = company;
    }

    static RetryImportEvent newEvent(final Company company) {
        return new RetryImportEvent(company);
    }

    public Company getCompany() {
        return mCompany;
    }
}
