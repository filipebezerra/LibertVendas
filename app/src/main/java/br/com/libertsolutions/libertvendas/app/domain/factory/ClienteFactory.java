package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.ClienteDto;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class ClienteFactory {

    private ClienteFactory() {/* No instances */}

    public static List<Cliente> toPojoList(
            final List<ClienteDto> clienteDtos, final String cpfCnpjVendedor,
            final String cnpjEmpresa) {
        List<Cliente> clienteList = new ArrayList<>();

        for (ClienteDto dto : clienteDtos) {
            clienteList.add(toPojo(dto, cpfCnpjVendedor, cnpjEmpresa));
        }
        return clienteList;
    }

    private static Cliente toPojo(
            ClienteDto clienteDto, String cpfCnpjVendedor, String cnpjEmpresa) {
        final Cidade cidade = CidadeFactory.toPojo(clienteDto.cidade);

        return Cliente.create(
                clienteDto.idCliente, clienteDto.codigo, clienteDto.nome, clienteDto.tipo,
                clienteDto.cpfCnpj, clienteDto.contato, clienteDto.email, clienteDto.telefone,
                clienteDto.telefone2, clienteDto.endereco, clienteDto.cep, cidade,
                clienteDto.bairro, clienteDto.numero, clienteDto.complemento,
                clienteDto.ultimaAlteracao, clienteDto.ativo, cpfCnpjVendedor, cnpjEmpresa,
                clienteDto.nomeFantasia, clienteDto.tabelaPadrao
        );
    }

    public static ClienteDto toDto(ClienteEntity clienteEntity) {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.idCliente = clienteEntity.getIdCliente();
        clienteDto.codigo = clienteEntity.getCodigo();
        clienteDto.nome = clienteEntity.getNome();
        clienteDto.tipo = clienteEntity.getTipo();
        clienteDto.cpfCnpj = clienteEntity.getCpfCnpj();
        clienteDto.contato = clienteEntity.getContato();
        clienteDto.email = clienteEntity.getEmail();
        clienteDto.telefone = clienteEntity.getTelefone();
        clienteDto.telefone2 = clienteEntity.getTelefone2();
        clienteDto.endereco = clienteEntity.getEndereco();
        clienteDto.cep = clienteEntity.getCep();
        clienteDto.cidade = CidadeFactory.toDto(clienteEntity.getCidade());
        clienteDto.bairro = clienteEntity.getBairro();
        clienteDto.numero = clienteEntity.getNumero();
        clienteDto.complemento = clienteEntity.getComplemento();
        clienteDto.ultimaAlteracao = clienteEntity.getUltimaAlteracao();
        clienteDto.ativo = clienteEntity.isAtivo();
        return clienteDto;
    }

    public static List<ClienteDto> toDtoList(List<ClienteEntity> clienteEntities) {
        List<ClienteDto> clienteList = new ArrayList<>();

        for (ClienteEntity cliente : clienteEntities) {
            clienteList.add(toDto(cliente));
        }
        return clienteList;
    }
}
