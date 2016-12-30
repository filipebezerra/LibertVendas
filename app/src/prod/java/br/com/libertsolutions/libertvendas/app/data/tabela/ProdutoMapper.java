package br.com.libertsolutions.libertvendas.app.data.tabela;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
class ProdutoMapper extends RealmMapper<Produto, ProdutoEntity> {

    @Override public ProdutoEntity toEntity(final Produto object) {
        return new ProdutoEntity()
                .setId(object.getIdProduto())
                .setCodigo(object.getCodigo())
                .setCodigoBarras(object.getCodigoBarras())
                .setDescricao(object.getDescricao())
                .setUnidadeMedida(object.getUnidade())
                .setGrupo(object.getGrupo())
                .setQuantidade(object.getQuantidade())
                .setObservacao(object.getObservacao())
                .setAtivo(object.isAtivo())
                .setUltimaAlteracao(object.getUltimaAlteracao());
    }

    @Override public Produto toViewObject(final ProdutoEntity entity) {
        final Integer idProduto = entity.getId();
        final String codigo = entity.getCodigo();
        final String codigoBarras = entity.getCodigoBarras();
        final String descricao = entity.getDescricao();
        final String unidadeMedida = entity.getUnidadeMedida();
        final String grupo = entity.getGrupo();
        final Float quantidade = entity.getQuantidade();
        final String observacao = entity.getObservacao();
        final Boolean ativo = entity.isAtivo();
        final String ultimaAlteracao = entity.getUltimaAlteracao();

        return Produto.create(
                idProduto, codigo, codigoBarras, descricao, unidadeMedida, grupo,
                quantidade, observacao, ativo, ultimaAlteracao
        );
    }
}
