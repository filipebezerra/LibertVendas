package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.VendedorResponseDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;

/**
 * @author Filipe Bezerra
 */
public class VendedorFactory {
    private VendedorFactory() {/* No instances */}

    public static Vendedor createVendedor(VendedorResponseDto pDto) {
        Preconditions.checkNotNull(pDto,
                "VendedorResponseDto can't be null");
        Preconditions.checkNotNull(pDto.vendedor,
                "VendedorResponseDto.vendedor can't be null");
        Preconditions.checkState(pDto.vendedor.idVendedor != 0,
                "VendedorResponseDto.vendedor.idVendedor can't be 0");
        Preconditions.checkState(pDto.vendedor.nome != null,
                "VendedorResponseDtovendedor..nome can't be null");
        Preconditions.checkState(pDto.vendedor.cpfCnpj != null,
                "VendedorResponseDtovendedor..cpfCnpj can't be null");

        final VendedorResponseDto.VendedorDto vendedor = pDto.vendedor;

        return new Vendedor(
                vendedor.idVendedor, vendedor.codigo, vendedor.nome, vendedor.cpfCnpj,
                vendedor.telefone, vendedor.email, vendedor.ativo, vendedor.idTabela,
                vendedor.ultimaAlteracao
        );
    }
}
