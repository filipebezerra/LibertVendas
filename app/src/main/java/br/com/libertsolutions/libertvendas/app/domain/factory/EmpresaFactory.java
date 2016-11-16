package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.EmpresaDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class EmpresaFactory {
    private EmpresaFactory() {/* No instances */}

    public static List<Empresa> createListEmpresa(List<EmpresaDto> pDtos) {
        Preconditions.checkNotNull(pDtos, "List<EmpresaDto> can't be null");

        List<Empresa> empresaList = new ArrayList<>();

        for (EmpresaDto dto : pDtos) {
            empresaList.add(createEmpresa(dto));
        }
        return empresaList;
    }

    private static Empresa createEmpresa(EmpresaDto pDto) {
        Preconditions.checkNotNull(
                pDto, "EmpresaDto can't be null");
        Preconditions.checkState(
                pDto.idEmpresa != 0, "EmpresaDto.idEmpresa can't be 0");
        Preconditions.checkState(
                !StringUtils.isEmpty(pDto.nome), "EmpresaDto.nome can't be empty");
        Preconditions.checkState(
                !StringUtils.isEmpty(pDto.cnpj), "EmpresaDto.cnpj can't be empty");

        return new Empresa(
                pDto.idEmpresa, pDto.nome, pDto.cnpj
        );
    }
}
