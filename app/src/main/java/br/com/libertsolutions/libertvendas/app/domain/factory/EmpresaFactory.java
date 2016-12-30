package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.EmpresaDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class EmpresaFactory {

    private EmpresaFactory() {/* No instances */}

    public static List<Empresa> createListEmpresa(List<EmpresaDto> pDtos) {
        List<Empresa> empresaList = new ArrayList<>();

        for (EmpresaDto dto : pDtos) {
            empresaList.add(createEmpresa(dto));
        }
        return empresaList;
    }

    private static Empresa createEmpresa(EmpresaDto pDto) {
        return Empresa.create(
                pDto.idEmpresa, pDto.nome, pDto.cnpj
        );
    }
}
