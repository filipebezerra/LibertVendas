package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.ClienteDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ClienteFactories {
    private ClienteFactories() {/* No instances */}

    public static List<Cliente> createListCliente(List<ClienteDto> pDtos) {
        Preconditions.checkNotNull(pDtos, "List<ClienteDto> can't be null");

        List<Cliente> clienteList = new ArrayList<>();

        for (ClienteDto dto : pDtos) {
            clienteList.add(createCliente(dto));
        }
        return clienteList;
    }

    private static Cliente createCliente(ClienteDto pDto) {
        Preconditions.checkNotNull(
                pDto, "ClienteDto can't be null");
        Preconditions.checkState(
                pDto.idCliente != 0, "ClienteDto.idCliente can't be 0");
        Preconditions.checkState(
                !StringUtils.isEmpty(pDto.nome), "ClienteDto.nome can't be empty");
        Preconditions.checkState(
                !StringUtils.isEmpty(pDto.cpfCnpj), "ClienteDto.cpfCnpj can't be empty");
        Preconditions.checkState(
                pDto.cidade != null, "ClienteDto.cidade can't be null");

        final Cidade cidade = CidadeFactory.createCidade(pDto.cidade);

        return new Cliente(
                pDto.idCliente, pDto.codigo, pDto.nome, pDto.tipo, pDto.cpfCnpj, pDto.contato,
                pDto.email, pDto.telefone, pDto.telefone2, pDto.endereco, pDto.cep, cidade,
                pDto.bairro, pDto.numero, pDto.complemento, pDto.ultimaAlteracao, pDto.ativo
        );
    }
}
