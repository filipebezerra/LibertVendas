package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmAutoIncrement;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class PedidoEntity implements Entity<Integer>, RealmModel {

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

    private TabelaEntity tabela;

    private RealmList<ItemPedidoEntity> itens;

    private String ultimaAlteracao;

    private String cnpjEmpresa;

    private String cpfCnpjVendedor;

    @Override public PedidoEntity setId(final Integer id) {
        if (id > 0) {
            this.id = id;
            this.numero = String.valueOf(id);
        }
        return this;
    }

    @Override public Integer getId() {
        return id;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public PedidoEntity setIdPedido(final Integer idPedido) {
        this.idPedido = idPedido;
        return this;
    }

    public Integer getTipo() {
        return tipo;
    }

    public PedidoEntity setTipo(final Integer tipo) {
        this.tipo = tipo;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public PedidoEntity setNumero(final String numero) {
        this.numero = numero;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public PedidoEntity setStatus(final Integer status) {
        this.status = status;
        return this;
    }

    public Long getDataEmissao() {
        return dataEmissao;
    }

    public PedidoEntity setDataEmissao(final Long dataEmissao) {
        this.dataEmissao = dataEmissao;
        return this;
    }

    public Double getDesconto() {
        return desconto;
    }

    public PedidoEntity setDesconto(final Double desconto) {
        this.desconto = desconto;
        return this;
    }

    public Float getPercentualDesconto() {
        return percentualDesconto;
    }

    public PedidoEntity setPercentualDesconto(final Float percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
        return this;
    }

    public String getObservacao() {
        return observacao;
    }

    public PedidoEntity setObservacao(final String observacao) {
        this.observacao = observacao;
        return this;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public PedidoEntity setCliente(
            final ClienteEntity cliente) {
        this.cliente = cliente;
        return this;
    }

    public FormaPagamentoEntity getFormaPagamento() {
        return formaPagamento;
    }

    public PedidoEntity setFormaPagamento(final FormaPagamentoEntity formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }

    public TabelaEntity getTabela() {
        return tabela;
    }

    public PedidoEntity setTabela(final TabelaEntity tabela) {
        this.tabela = tabela;
        return this;
    }

    public RealmList<ItemPedidoEntity> getItens() {
        return itens;
    }

    public PedidoEntity setItens(final RealmList<ItemPedidoEntity> itens) {
        this.itens = itens;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public PedidoEntity setUltimaAlteracao(final String ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
        return this;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public PedidoEntity setCnpjEmpresa(final String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
        return this;
    }

    public String getCpfCnpjVendedor() {
        return cpfCnpjVendedor;
    }

    public PedidoEntity setCpfCnpjVendedor(final String cpfCnpjVendedor) {
        this.cpfCnpjVendedor = cpfCnpjVendedor;
        return this;
    }
}
