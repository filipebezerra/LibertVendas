package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class LoggedUser {

    private final Salesman salesman;

    private final Company defaultCompany;

    private LoggedUser(final Salesman salesman, final Company defaultCompany) {
        this.salesman = salesman;
        this.defaultCompany = defaultCompany;
    }

    public static LoggedUser create(final Salesman salesman, final Company defaultCompany) {
        return new LoggedUser(salesman, defaultCompany);
    }

    public LoggedUser withDefaultCompany(Company company) {
        return create(getSalesman(), company);
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public Company getDefaultCompany() {
        return defaultCompany;
    }
}
