package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.VendedorDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class VendedorFactory {

    private VendedorFactory() {/* No instances */}

    public static Vendedor createVendedor(
            VendedorDto vendedor, List<Empresa> empresas, Empresa empresaSelecionada) {
        return Vendedor.create(
                vendedor.idVendedor,
                vendedor.codigo,
                vendedor.nome,
                vendedor.cpfCnpj,
                vendedor.telefone,
                vendedor.email,
                vendedor.ativo,
                vendedor.ultimaAlteracao,
                vendedor.aplicaDesconto,
                empresas,
                empresaSelecionada
        );
    }
}
