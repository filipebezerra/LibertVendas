package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EmpresaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
class VendedorMapper extends RealmMapper<Vendedor, VendedorEntity> {

    private final RealmMapper<Empresa, EmpresaEntity> mEmpresaMapper;

    VendedorMapper(final RealmMapper<Empresa, EmpresaEntity> empresaMapper) {
        mEmpresaMapper = empresaMapper;
    }

    @Override public VendedorEntity toEntity(final Vendedor object) {
        return new VendedorEntity()
                .setId(object.getIdVendedor())
                .setCodigo(object.getCodigo())
                .setNome(object.getNome())
                .setCpfCnpj(object.getCpfCnpj())
                .setTelefone(object.getTelefone())
                .setEmail(object.getEmail())
                .setAtivo(object.isAtivo())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setAplicaDesconto(object.isAplicaDesconto())
                .setEmpresas(mEmpresaMapper.toEntityList(object.getEmpresas()))
                .setEmpresaSelecionada(mEmpresaMapper.toEntity(object.getEmpresaSelecionada()));
    }

    @Override public Vendedor toViewObject(final VendedorEntity entity) {
        return Vendedor.create(
                entity.getId(),
                entity.getCodigo(),
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.isAtivo(),
                entity.getUltimaAlteracao(),
                entity.isAplicaDesconto(),
                mEmpresaMapper.toViewObjectList(entity.getEmpresas()),
                mEmpresaMapper.toViewObject(entity.getEmpresaSelecionada())
        );
    }
}
