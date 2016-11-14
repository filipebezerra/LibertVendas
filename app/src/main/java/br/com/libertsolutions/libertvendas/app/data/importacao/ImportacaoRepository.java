package br.com.libertsolutions.libertvendas.app.data.importacao;

/**
 * @author Filipe Bezerra
 */
public interface ImportacaoRepository {
    boolean isImportacaoInicialFeita();

    void setImportacaoInicialComoFeita();
}
