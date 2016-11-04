package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Estado {
    private final int idEstado;

    private final String uf;

    private final String nome;

    public Estado(int pIdEstado, String pUf, String pNome) {
        idEstado = pIdEstado;
        uf = pUf;
        nome = pNome;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public String getUf() {
        return uf;
    }

    public String getNome() {
        return nome;
    }
}
