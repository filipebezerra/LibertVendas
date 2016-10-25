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
                .setNome(object.getNome())
                .setQuantidadeEstoque(object.getQuantidadeEstoque())
                .setPreco(object.getPreco());
    }

    @Override
    public Produto toViewObject(ProdutoEntity entity) {
        final String nome = entity.getNome();
        final Float quantidadeEstoque = entity.getQuantidadeEstoque();
        final Double preco = entity.getPreco();
        return new Produto(
              nome, quantidadeEstoque, preco
        );
    }
}
