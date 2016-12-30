package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.ClienteDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class ClienteFactories {

    private ClienteFactories() {/* No instances */}

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
}
