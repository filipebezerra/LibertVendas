package br.com.libertsolutions.libertvendas.app.domain.pojo;

import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class Vendedor {
    private final int idVendedor;

    private final String codigo;

    private final String nome;

    private final String cpfCnpj;

    private final String telefone;

    private final String email;

    private final boolean ativo;

    private final int idTabela;

    private final String ultimaAlteracao;

    private final List<Empresa> empresas;

    public Vendedor(
            int pIdVendedor, String pCodigo, String pNome, String pCpfCnpj,
            String pTelefone, String pEmail, boolean pAtivo, int pIdTabela,
            String pUltimaAlteracao, List<Empresa> pEmpresas) {
        idVendedor = pIdVendedor;
        codigo = pCodigo;
        nome = pNome;
        cpfCnpj = pCpfCnpj;
        telefone = pTelefone;
        email = pEmail;
        ativo = pAtivo;
        idTabela = pIdTabela;
        ultimaAlteracao = pUltimaAlteracao;
        empresas = pEmpresas;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getIdTabela() {
        return idTabela;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    @SuppressWarnings("StringBufferReplaceableByString") @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Vendedor{");
        sb.append("idVendedor=").append(idVendedor);
        sb.append(", codigo='").append(codigo).append('\'');
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", cpfCnpj='").append(cpfCnpj).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", ultimaAlteracao='").append(ultimaAlteracao).append('\'');
        sb.append(", idTabela=").append(idTabela);
        sb.append(", ativo=").append(ativo);
        sb.append(", telefone='").append(telefone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
