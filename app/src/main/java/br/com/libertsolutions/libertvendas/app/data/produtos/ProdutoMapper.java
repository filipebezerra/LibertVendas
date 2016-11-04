package br.com.libertsolutions.libertvendas.app.data.produtos;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
public class ProdutoMapper extends Mapper<Produto, ProdutoEntity> {
    @Override
    public ProdutoEntity toEntity(Produto object) {
        return new ProdutoEntity()
                .setIdProduto(object.getIdProduto())
                .setCodigo(object.getCodigo())
                .setCodigoBarras(object.getCodigoBarras())
                .setDescricao(object.getDescricao())
                .setUnidadeMedida(object.getUnidadeMedida())
                .setGrupo(object.getGrupo())
                .setPrecoVenda(object.getPrecoVenda())
                .setQuantidade(object.getQuantidade())
                .setObservacao(object.getObservacao())
                .setAtivo(object.isAtivo())
                .setUltimaAlteracao(object.getUltimaAlteracao());
    }

    @Override
    public Produto toViewObject(ProdutoEntity entity) {
        final Integer idProduto = entity.getIdProduto();
        final String codigo = entity.getCodigo();
        final String codigoBarras = entity.getCodigoBarras();
        final String descricao = entity.getDescricao();
        final String unidadeMedida = entity.getUnidadeMedida();
        final String grupo = entity.getGrupo();
        final Double precoVenda = entity.getPrecoVenda();
        final Float quantidade = entity.getQuantidade();
        final String observacao = entity.getObservacao();
        final Boolean ativo = entity.isAtivo();
        final String ultimaAlteracao = entity.getUltimaAlteracao();
        return new Produto(
               idProduto, codigo, codigoBarras, descricao, unidadeMedida, grupo, precoVenda,
                quantidade, observacao, ativo, ultimaAlteracao
        );
    }
}
