package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EmpresaEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;

/**
 * @author Filipe Bezerra
 */
class EmpresaMapper extends RealmMapper<Empresa, EmpresaEntity> {

    @Override public EmpresaEntity toEntity(final Empresa object) {
        return new EmpresaEntity()
                .setId(object.getIdEmpresa())
                .setNome(object.getNome())
                .setCnpj(object.getCnpj())
                .setIdTabela(object.getIdTabela());
    }

    @Override public Empresa toViewObject(final EmpresaEntity entity) {
        return Empresa.create(
                entity.getId(),
                entity.getNome(),
                entity.getCnpj(),
                entity.getIdTabela()
        );
    }
}
