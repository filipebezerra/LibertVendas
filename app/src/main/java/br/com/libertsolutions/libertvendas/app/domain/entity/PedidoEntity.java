package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.util.RealmAutoIncrement;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class PedidoEntity implements RealmModel {
    @PrimaryKey private Integer id
            = RealmAutoIncrement.getInstance(this.getClass()).getNextIdFromModel();

    private Integer idPedido;

    private Integer tipo;

    private String numero;

    private Integer status;

    private Long dataEmissao;

    private Double desconto;

    private Float percentualDesconto;

    private String observacao;

    private ClienteEntity cliente;

    private FormaPagamentoEntity formaPagamento;

    private TabelaPrecoEntity tabelaPreco;

    private RealmList<ItemPedidoEntity> itens;

    private String ultimaAlteracao;

    private String cnpjEmpresa;

    private String cpfCnpjVendedor;

    public Integer getId() {
        return id;
    }

    public PedidoEntity setId(Integer pId) {
        if (pId > 0) {
            id = pId;
        }
        return this;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public PedidoEntity setIdPedido(Integer pIdPedido) {
        idPedido = pIdPedido;
        return this;
    }

    public Integer getTipo() {
        return tipo;
    }

    public PedidoEntity setTipo(Integer pTipo) {
        tipo = pTipo;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public PedidoEntity setNumero(String pNumero) {
        numero = pNumero;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public PedidoEntity setStatus(Integer pStatus) {
        status = pStatus;
        return this;
    }

    public Long getDataEmissao() {
        return dataEmissao;
    }

    public PedidoEntity setDataEmissao(Long pDataEmissao) {
        dataEmissao = pDataEmissao;
        return this;
    }

    public Double getDesconto() {
        return desconto;
    }

    public PedidoEntity setDesconto(Double pDesconto) {
        desconto = pDesconto;
        return this;
    }

    public Float getPercentualDesconto() {
        return percentualDesconto;
    }

    public PedidoEntity setPercentualDesconto(Float pPercentualDesconto) {
        percentualDesconto = pPercentualDesconto;
        return this;
    }

    public String getObservacao() {
        return observacao;
    }

    public PedidoEntity setObservacao(String pObservacao) {
        observacao = pObservacao;
        return this;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public PedidoEntity setCliente(
            ClienteEntity pCliente) {
        cliente = pCliente;
        return this;
    }

    public FormaPagamentoEntity getFormaPagamento() {
        return formaPagamento;
    }

    public PedidoEntity setFormaPagamento(
            FormaPagamentoEntity pFormaPagamento) {
        formaPagamento = pFormaPagamento;
        return this;
    }

    public TabelaPrecoEntity getTabelaPreco() {
        return tabelaPreco;
    }

    public PedidoEntity setTabelaPreco(TabelaPrecoEntity pTabelaPreco) {
        tabelaPreco = pTabelaPreco;
        return this;
    }

    public RealmList<ItemPedidoEntity> getItens() {
        return itens;
    }

    public PedidoEntity setItens(RealmList<ItemPedidoEntity> pItens) {
        itens = pItens;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public PedidoEntity setUltimaAlteracao(String pUltimaAlteracao) {
        ultimaAlteracao = pUltimaAlteracao;
        return this;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public PedidoEntity setCnpjEmpresa(String pCnpjEmpresa) {
        cnpjEmpresa = pCnpjEmpresa;
        return this;
    }

    public String getCpfCnpjVendedor() {
        return cpfCnpjVendedor;
    }

    public PedidoEntity setCpfCnpjVendedor(String pCpfCnpjVendedor) {
        cpfCnpjVendedor = pCpfCnpjVendedor;
        return this;
    }
}
