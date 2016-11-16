package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Empresa {
    private final int idEmpresa;

    private final String nome;

    private final String cnpj;

    public Empresa(int pIdEmpresa, String pNome, String pCnpj) {
        idEmpresa = pIdEmpresa;
        nome = pNome;
        cnpj = pCnpj;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    @Override public boolean equals(Object pAnotherEmpresa) {
        if (this == pAnotherEmpresa) {
            return true;
        }
        if (pAnotherEmpresa == null || getClass() != pAnotherEmpresa.getClass()) {
            return false;
        }

        Empresa empresa = (Empresa) pAnotherEmpresa;

        if (getIdEmpresa() != empresa.getIdEmpresa()) {
            return false;
        }
        return getCnpj().equals(empresa.getCnpj());
    }

    @Override public int hashCode() {
        int result = getIdEmpresa();
        result = 31 * result + getCnpj().hashCode();
        return result;
    }
}
