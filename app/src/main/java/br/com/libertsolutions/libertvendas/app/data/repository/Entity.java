package br.com.libertsolutions.libertvendas.app.data.repository;

/**
 * @author Filipe Bezerra
 */
public interface Entity<ID> {

    Entity setId(ID id);

    ID getId();
}
