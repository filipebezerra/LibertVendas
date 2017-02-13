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
        final int id = entity.getId();
        final int idCliente = entity.getIdCliente();
        final String codigo = entity.getCodigo();
        final String nome = entity.getNome();
        final int tipo = entity.getTipo();
        final String cpfCnpj = entity.getCpfCnpj();
        final String contato = entity.getContato();
        final String email = entity.getEmail();
        final String telefone = entity.getTelefone();
        final String telefone2 = entity.getTelefone2();
        final String endereco = entity.getEndereco();
        final String cep = entity.getCep();
        final Cidade cidade = mCidadeMapper.toViewObject(entity.getCidade());
        final String bairro = entity.getBairro();
        final String numero = entity.getNumero();
        final String complemento = entity.getComplemento();
        final String ultimaAlteracao = entity.getUltimaAlteracao();
        final boolean ativo = entity.isAtivo();
        final String cnpjEmpresa = entity.getCnpjEmpresa();
        final String cpfCnpjVendedor = entity.getCpfCnpjVendedor();
        final int status = entity.getStatus();
        final String nomeFantasia = entity.getNomeFantasia();
        final int tabelaPadrao = entity.getTabelaPadrao();

        return Cliente.map(
                id, idCliente, codigo, nome, tipo, cpfCnpj, contato, email, telefone, telefone2,
                endereco, cep, cidade, bairro, numero, complemento, ultimaAlteracao, ativo,
                cnpjEmpresa, cpfCnpjVendedor, status, nomeFantasia, tabelaPadrao
        );
    }
}
