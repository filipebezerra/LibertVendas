package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public final class LoggedUser {

    private final int idVendedor;

    private final String nomeVendedor;

    private final String cpfVendedor;

    private final int idEmpresa;

    private final String nomeEmpresa;

    private final String cnpjEmpresa;

    public static LoggedUser create(
            final int idVendedor, final String nomeVendedor,
            final String cpfVendedor, final int idEmpresa, final String nomeEmpresa,
            final String cnpjEmpresa) {
        return new LoggedUser(idVendedor, nomeVendedor, cpfVendedor, idEmpresa,
                nomeEmpresa, cnpjEmpresa);
    }

    private LoggedUser(
            final int idVendedor, final String nomeVendedor,
            final String cpfVendedor, final int idEmpresa, final String nomeEmpresa,
            final String cnpjEmpresa) {
        this.idVendedor = idVendedor;
        this.nomeVendedor = nomeVendedor;
        this.cpfVendedor = cpfVendedor;
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public String getCpfVendedor() {
        return cpfVendedor;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }
}
