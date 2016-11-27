package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.res.Resources;
import br.com.libertsolutions.libertvendas.app.R;

/**
 * @author Filipe Bezerra
 */
public class CadastroClienteResourcesRepositoryImpl extends CommonResourcesRepositoryImpl
    implements CadastroClienteResourcesRepository {

    public CadastroClienteResourcesRepositoryImpl(Resources resources) {
        super(resources);
    }

    @Override public int obtainTiposPessoaViewId() {
        return R.id.spinner_tipo_pessoa;
    }

    @Override public int obtainCpfOuCnpjViewId() {
        return R.id.input_layout_cpf_ou_cnpj;
    }

    @Override public int obtainNomeViewId() {
        return R.id.input_layout_nome_cliente;
    }

    @Override public int obtainEmailViewId() {
        return R.id.input_layout_email;
    }

    @Override public int obtainEnderecoViewId() {
        return R.id.input_layout_endereco;
    }

    @Override public int obtainNumeroViewId() {
        return R.id.input_layout_numero;
    }

    @Override public int obtainBairroViewId() {
        return R.id.input_layout_bairro;
    }

    @Override public int obtainEstadosViewId() {
        return R.id.spinner_estados;
    }

    @Override public int obtainCidadesViewId() {
        return R.id.spinner_cidades;
    }

    @Override public int obtainCepViewId() {
        return R.id.input_layout_cep;
    }

    @Override public int obtainComplementoViewId() {
        return R.id.input_layout_complemento;
    }

    @Override public int obtainTelefoneViewId() {
        return R.id.input_layout_telefone;
    }

    @Override public int obtainCelularViewId() {
        return R.id.input_layout_celular;
    }

    @Override public String obtainStringTitleEditandoCliente() {
        return getResources().getString(R.string.title_activity_cadastro_cliente_editando);
    }
}
