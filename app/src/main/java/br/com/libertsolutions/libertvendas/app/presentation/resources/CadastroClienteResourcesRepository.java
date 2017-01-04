package br.com.libertsolutions.libertvendas.app.presentation.resources;

/**
 * @author Filipe Bezerra
 */
public interface CadastroClienteResourcesRepository extends CommonResourcesRepository {

    int obtainTiposPessoaViewId();

    int obtainCpfOuCnpjViewId();

    int obtainNomeViewId();

    int obtainEmailViewId();

    int obtainEnderecoViewId();

    int obtainNumeroViewId();

    int obtainBairroViewId();

    int obtainEstadosViewId();

    int obtainCidadesViewId();

    int obtainCepViewId();

    int obtainComplementoViewId();

    int obtainTelefoneViewId();

    int obtainCelularViewId();

    String obtainStringTitleEditandoCliente();
}
