package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
class VendedorMapper extends Mapper<Vendedor,VendedorEntity> {
    @Override
    public VendedorEntity toEntity(Vendedor object) {
        return new VendedorEntity()
                .setIdVendedor(object.getIdVendedor())
                .setCodigo(object.getCodigo())
                .setNome(object.getNome())
                .setCpfCnpj(object.getCpfCnpj())
                .setTelefone(object.getTelefone())
                .setEmail(object.getEmail())
                .setAtivo(object.isAtivo())
                .setIdTabela(object.getIdTabela())
                .setUltimaAlteracao(object.getUltimaAlteracao());
    }

    @Override
    public Vendedor toViewObject(VendedorEntity entity) {
        Integer idVendedor = entity.getIdVendedor();
        String codigo = entity.getCodigo();
        String nome = entity.getNome();
        String cpfCnpj = entity.getCpfCnpj();
        String telefone = entity.getTelefone();
        String email = entity.getEmail();
        Boolean ativo = entity.isAtivo();
        Integer idTabela = entity.getIdTabela();
        String ultimaAlteracao = entity.getUltimaAlteracao();

        return new Vendedor(
                idVendedor, codigo, nome, cpfCnpj, telefone, email, ativo, idTabela, ultimaAlteracao
        );
    }
}
