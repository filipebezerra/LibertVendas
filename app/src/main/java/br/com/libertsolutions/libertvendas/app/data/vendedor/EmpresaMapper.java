package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EmpresaEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;

/**
 * @author Filipe Bezerra
 */
class EmpresaMapper extends Mapper<Empresa, EmpresaEntity> {
    @Override public EmpresaEntity toEntity(Empresa object) {
        return new EmpresaEntity()
                .setIdEmpresa(object.getIdEmpresa())
                .setNome(object.getNome())
                .setCnpj(object.getCnpj());
    }

    @Override public Empresa toViewObject(EmpresaEntity entity) {
        int idEmpresa = entity.getIdEmpresa();
        String nome = entity.getNome();
        String cnpj = entity.getCnpj();

        return new Empresa(
                idEmpresa, nome, cnpj
        );
    }
}
