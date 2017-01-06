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

    public static List<Cliente> createListCliente(List<ClienteDto> pDtos) {
        List<Cliente> clienteList = new ArrayList<>();

        for (ClienteDto dto : pDtos) {
            clienteList.add(createCliente(dto));
        }
        return clienteList;
    }

    private static Cliente createCliente(ClienteDto pDto) {
        final Cidade cidade = CidadeFactory.createCidade(pDto.cidade);

        return Cliente.create(
                pDto.idCliente, pDto.codigo, pDto.nome, pDto.tipo, pDto.cpfCnpj, pDto.contato,
                pDto.email, pDto.telefone, pDto.telefone2, pDto.endereco, pDto.cep, cidade,
                pDto.bairro, pDto.numero, pDto.complemento, pDto.ultimaAlteracao, pDto.ativo
        );
    }

    public static ClienteDto createDto(ClienteEntity clienteEntity) {
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
        clienteDto.cidade = CidadeFactory.createDto(clienteEntity.getCidade());
        clienteDto.bairro = clienteEntity.getBairro();
        clienteDto.numero = clienteEntity.getNumero();
        clienteDto.complemento = clienteEntity.getComplemento();
        clienteDto.ultimaAlteracao = clienteEntity.getUltimaAlteracao();
        clienteDto.ativo = clienteEntity.isAtivo();
        return clienteDto;
    }

    public static List<ClienteDto> createListDto(List<ClienteEntity> clientes) {
        List<ClienteDto> clienteList = new ArrayList<>();

        for (ClienteEntity cliente : clientes) {
            clienteList.add(createDto(cliente));
        }
        return clienteList;
    }
}
