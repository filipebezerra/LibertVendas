package br.com.libertsolutions.libertvendas.app.data.clientes;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class ClienteMapper extends Mapper<Cliente, ClienteEntity> {
    @Override
    public ClienteEntity toEntity(Cliente object) {
        return new ClienteEntity()
                .setId(object.getId())
                .setCodigo(object.getCodigo())
                .setNome(object.getNome())
                .setTipo(object.getTipo())
                .setCpfOuCnpj(object.getCpfOuCnpj())
                .setEmail(object.getEmail())
                .setTelefone(object.getTelefone())
                .setTelefone2(object.getTelefone2())
                .setEndereco(object.getEndereco())
                .setCep(object.getCep())
                .setBairro(object.getBairro())
                .setNumero(object.getNumero())
                .setComplemento(object.getComplemento())
                .setCidade(object.getCidade())
                .setUf(object.getUf());
    }

    @Override
    public Cliente toViewObject(ClienteEntity entity) {
        final long id = entity.getId();
        final String codigo = entity.getCodigo();
        final String nome = entity.getNome();
        final int tipo = entity.getTipo();
        final String cpfOuCnpj = entity.getCpfOuCnpj();
        final String email = entity.getEmail();
        final String telefone = entity.getTelefone();
        final String telefone2 = entity.getTelefone2();
        final String endereco = entity.getEndereco();
        final String cep = entity.getCep();
        final String bairro = entity.getBairro();
        final String numero = entity.getNumero();
        final String complemento = entity.getComplemento();
        final String cidade = entity.getCidade();
        final String uf = entity.getUf();
        return Cliente.existingCliente(
                id, codigo, nome, tipo, cpfOuCnpj, email, telefone, telefone2,
                endereco, cep, bairro, numero, complemento, cidade, uf
        );
    }
}
