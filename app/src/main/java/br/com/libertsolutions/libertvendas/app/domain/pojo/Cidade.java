package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Cidade {
    private final int idCidade;

    private final String codMunicipio;

    private final String nome;

    private final Estado estado;

    public Cidade(int pIdCidade, String pCodMunicipio, String pNome, Estado pEstado) {
        idCidade = pIdCidade;
        codMunicipio = pCodMunicipio;
        nome = pNome;
        estado = pEstado;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public String getCodMunicipio() {
        return codMunicipio;
    }

    public String getNome() {
        return nome;
    }

    public Estado getEstado() {
        return estado;
    }
}
