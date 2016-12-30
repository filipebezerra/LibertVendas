package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EmpresaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import java.util.List;

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
                .setIdTabela(object.getIdTabela())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setAplicaDesconto(object.isAplicaDesconto())
                .setEmpresas(mEmpresaMapper.toEntityList(object.getEmpresas()));
    }

    @Override public Vendedor toViewObject(final VendedorEntity entity) {
        Integer id = entity.getId();
        String codigo = entity.getCodigo();
        String nome = entity.getNome();
        String cpfCnpj = entity.getCpfCnpj();
        String telefone = entity.getTelefone();
        String email = entity.getEmail();
        Boolean ativo = entity.isAtivo();
        Integer idTabela = entity.getIdTabela();
        String ultimaAlteracao = entity.getUltimaAlteracao();
        Boolean aplicaDesconto = entity.isAplicaDesconto();
        List<Empresa> empresas = mEmpresaMapper.toViewObjectList(entity.getEmpresas());

        return Vendedor.create(
                id, codigo, nome, cpfCnpj, telefone, email, ativo, idTabela, ultimaAlteracao,
                aplicaDesconto, empresas
        );
    }
}
