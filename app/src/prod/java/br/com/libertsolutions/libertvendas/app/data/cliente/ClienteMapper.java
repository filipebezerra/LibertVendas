package br.com.libertsolutions.libertvendas.app.data.cliente;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
class ClienteMapper extends RealmMapper<Cliente, ClienteEntity> {

    private final RealmMapper<Cidade, CidadeEntity> mCidadeMapper;

    ClienteMapper(final RealmMapper<Cidade, CidadeEntity> cidadeMapper) {
        mCidadeMapper = cidadeMapper;
    }

    @Override public ClienteEntity toEntity(final Cliente object) {
        return new ClienteEntity()
                .setId(object.getId())
                .setIdCliente(object.getIdCliente())
                .setCodigo(object.getCodigo())
                .setNome(object.getNome())
                .setTipo(object.getTipo())
                .setCpfCnpj(object.getCpfCnpj())
                .setContato(object.getContato())
                .setEmail(object.getEmail())
                .setTelefone(object.getTelefone())
                .setTelefone2(object.getTelefone2())
                .setEndereco(object.getEndereco())
                .setCep(object.getCep())
                .setCidade(mCidadeMapper.toEntity(object.getCidade()))
                .setBairro(object.getBairro())
                .setNumero(object.getNumero())
                .setComplemento(object.getComplemento())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setAtivo(object.isAtivo())
                .setCnpjEmpresa(object.getCnpjEmpresa())
                .setCpfCnpjVendedor(object.getCpfCnpjVendedor())
                .setStatus(object.getStatus())
                .setNomeFantasia(object.getNome())
                .setTabelaPadrao(object.getTabelaPadrao());
    }

    @Override public Cliente toViewObject(final ClienteEntity entity) {
        return Cliente.map(
                entity.getId(),
                entity.getIdCliente(),
                entity.getCodigo(),
                entity.getNome(),
                entity.getTipo(),
                entity.getCpfCnpj(),
                entity.getContato(),
                entity.getEmail(),
                entity.getTelefone(),
                entity.getTelefone2(),
                entity.getEndereco(),
                entity.getCep(),
                mCidadeMapper.toViewObject(entity.getCidade()),
                entity.getBairro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getUltimaAlteracao(),
                entity.isAtivo(),
                entity.getCnpjEmpresa(),
                entity.getCpfCnpjVendedor(),
                entity.getStatus(),
                entity.getNomeFantasia(),
                entity.getTabelaPadrao()
        );
    }
}
