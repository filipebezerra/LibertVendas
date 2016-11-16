package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EmpresaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class VendedorMapper extends Mapper<Vendedor,VendedorEntity> {
    private final Mapper<Empresa, EmpresaEntity> mEmpresaEntityMapper;

    public VendedorMapper(Mapper<Empresa, EmpresaEntity> pEmpresaEntityMapper) {
        mEmpresaEntityMapper = pEmpresaEntityMapper;
    }

    @Override
    public VendedorEntity toEntity(Vendedor object) {
        return new VendedorEntity()
                .setIdVendedor(object.getIdVendedor())
                .setCodigo(object.getCodigo())
                .setNome(object.getNome())
                .setCpfCnpj(object.getCpfCnpj())
                .setTelefone(object.getTelefone())
                .setEmail(object.getEmail())
                .setAtivo(object.isAtivo())
                .setIdTabela(object.getIdTabela())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setEmpresas(mEmpresaEntityMapper.toEntityList(object.getEmpresas()));
    }

    @Override
    public Vendedor toViewObject(VendedorEntity entity) {
        Integer idVendedor = entity.getIdVendedor();
        String codigo = entity.getCodigo();
        String nome = entity.getNome();
        String cpfCnpj = entity.getCpfCnpj();
        String telefone = entity.getTelefone();
        String email = entity.getEmail();
        Boolean ativo = entity.isAtivo();
        Integer idTabela = entity.getIdTabela();
        String ultimaAlteracao = entity.getUltimaAlteracao();
        List<Empresa> empresas = mEmpresaEntityMapper.toViewObjectList(entity.getEmpresas());

        return new Vendedor(
                idVendedor, codigo, nome, cpfCnpj, telefone, email, ativo, idTabela,
                ultimaAlteracao, empresas
        );
    }
}
