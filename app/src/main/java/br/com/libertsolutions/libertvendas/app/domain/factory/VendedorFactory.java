package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.VendedorDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.VendedorResponseDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class VendedorFactory {

    private VendedorFactory() {/* No instances */}

    public static Vendedor createVendedor(VendedorResponseDto pDto) {
        final List<Empresa> empresas = EmpresaFactory.createListEmpresa(pDto.empresas);
        final VendedorDto vendedor = pDto.vendedor;

        return Vendedor.create(
                vendedor.idVendedor, vendedor.codigo, vendedor.nome, vendedor.cpfCnpj,
                vendedor.telefone, vendedor.email, vendedor.ativo, vendedor.idTabela,
                vendedor.ultimaAlteracao, pDto.vendedor.aplicaDesconto, empresas);
    }
}
