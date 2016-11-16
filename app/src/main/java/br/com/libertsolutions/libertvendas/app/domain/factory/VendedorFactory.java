package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.VendedorDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.VendedorResponseDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.List;

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
        Preconditions.checkNotNull(pDto.empresas,
                "VendedorResponseDto.empresas can't be null");
        Preconditions.checkState(pDto.vendedor.idVendedor != 0,
                "VendedorResponseDto.vendedor.idVendedor can't be 0");
        Preconditions.checkState(!StringUtils.isEmpty(pDto.vendedor.nome),
                "VendedorResponseDtovendedor.nome can't be null");
        Preconditions.checkState(!StringUtils.isEmpty(pDto.vendedor.cpfCnpj),
                "VendedorResponseDtovendedor.cpfCnpj can't be null");
        Preconditions.checkState(!StringUtils.isEmpty(pDto.vendedor.email),
                "VendedorResponseDtovendedor.email can't be null");
        Preconditions.checkState(!pDto.empresas.isEmpty(),
                "VendedorResponseDto.vendedor.empresas can't be empty a list");

        final List<Empresa> empresas = EmpresaFactory.createListEmpresa(pDto.empresas);

        final VendedorDto vendedor = pDto.vendedor;

        return new Vendedor(
                vendedor.idVendedor, vendedor.codigo, vendedor.nome, vendedor.cpfCnpj,
                vendedor.telefone, vendedor.email, vendedor.ativo, vendedor.idTabela,
                vendedor.ultimaAlteracao, empresas
        );
    }
}
