package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.cidades.EstadoRepository;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.data.util.ApiUtils;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TipoPessoa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.login.UsuarioLogadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.resources.CadastroClienteResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.ObservableUtils;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils.equalsIgnoringNullOrWhitespace;
import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
class CadastroClientePresenter extends BasePresenter<CadastroClienteContract.View>
        implements CadastroClienteContract.Presenter {

    private final CadastroClienteResourcesRepository mResourcesRepository;

    private final ClienteRepository mClienteRepository;

    private final EstadoRepository mEstadoRepository;

    private final CidadeRepository mCidadeRepository;

    private Cliente mClienteEmEdicao;

    private final List<TipoPessoa> mTiposPessoaList = Arrays.asList(TipoPessoa.values());

    private List<Estado> mEstadosList;

    private List<Cidade> mCidadesList;

    private Vendedor mVendedorLogado;

    private Empresa mEmpresaLogada;

    CadastroClientePresenter(CadastroClienteDependencyContainer pDependencyContainer) {
        mResourcesRepository = pDependencyContainer.resourcesRepository();
        mClienteRepository = pDependencyContainer.clienteRepository();
        mEstadoRepository = pDependencyContainer.estadoRepository();
        mCidadeRepository = pDependencyContainer.cidadeRepository();
    }

    @Override public void initializeView(Cliente pClienteFromExtra) {
        mClienteEmEdicao = pClienteFromExtra;
        getView().displayTiposPessoa(mTiposPessoaList);
        loadEstados();

        initializeViewFields();
        initializeViewRequiredFields();
        initializeFieldsIfEmEdicao();
    }

    private void initializeViewFields() {
        List<Integer> viewIds = new ArrayList<>();
        viewIds.add(mResourcesRepository.obtainTiposPessoaViewId());
        viewIds.add(mResourcesRepository.obtainCpfOuCnpjViewId());
        viewIds.add(mResourcesRepository.obtainNomeViewId());
        viewIds.add(mResourcesRepository.obtainEmailViewId());
        viewIds.add(mResourcesRepository.obtainEnderecoViewId());
        viewIds.add(mResourcesRepository.obtainNumeroViewId());
        viewIds.add(mResourcesRepository.obtainBairroViewId());
        viewIds.add(mResourcesRepository.obtainEstadosViewId());
        viewIds.add(mResourcesRepository.obtainCidadesViewId());
        viewIds.add(mResourcesRepository.obtainCepViewId());
        viewIds.add(mResourcesRepository.obtainComplementoViewId());
        viewIds.add(mResourcesRepository.obtainTelefoneViewId());
        viewIds.add(mResourcesRepository.obtainCelularViewId());
        getView().setViewFields(viewIds);
    }

    private void initializeViewRequiredFields() {
        List<Integer> requiredViewIds = new ArrayList<>();
        requiredViewIds.add(mResourcesRepository.obtainTiposPessoaViewId());
        requiredViewIds.add(mResourcesRepository.obtainCpfOuCnpjViewId());
        requiredViewIds.add(mResourcesRepository.obtainNomeViewId());
        requiredViewIds.add(mResourcesRepository.obtainEstadosViewId());
        requiredViewIds.add(mResourcesRepository.obtainCidadesViewId());
        getView().setRequiredFields(requiredViewIds);
    }

    private void initializeFieldsIfEmEdicao() {
        if (isEditing()) {
            getView().changeTitle(mResourcesRepository.obtainStringTitleEditandoCliente());

            for (TipoPessoa tipoPessoa : mTiposPessoaList) {
                if (tipoPessoa.equals(mClienteEmEdicao.getTipo())) {
                    getView().setViewValue(
                            mResourcesRepository.obtainTiposPessoaViewId(),
                            tipoPessoa.ordinal());
                }
            }

            getView().setViewValue(
                    mResourcesRepository.obtainCpfOuCnpjViewId(),
                    mClienteEmEdicao.getCpfCnpj());

            getView().setViewValue(
                    mResourcesRepository.obtainNomeViewId(),
                    mClienteEmEdicao.getNome());

            if (!StringUtils.isEmpty(mClienteEmEdicao.getEmail())) {
                getView().setViewValue(
                        mResourcesRepository.obtainEmailViewId(),
                        mClienteEmEdicao.getEmail());
            }

            if (!StringUtils.isEmpty(mClienteEmEdicao.getEndereco())) {
                getView().setViewValue(
                        mResourcesRepository.obtainEnderecoViewId(),
                        mClienteEmEdicao.getEndereco());
            }

            if (!StringUtils.isEmpty(mClienteEmEdicao.getNumero())) {
                getView().setViewValue(
                        mResourcesRepository.obtainNumeroViewId(),
                        mClienteEmEdicao.getNumero());
            }

            if (!StringUtils.isEmpty(mClienteEmEdicao.getBairro())) {
                getView().setViewValue(
                        mResourcesRepository.obtainBairroViewId(),
                        mClienteEmEdicao.getBairro());
            }

            if (!StringUtils.isEmpty(mClienteEmEdicao.getCep())) {
                getView().setViewValue(
                        mResourcesRepository.obtainCepViewId(),
                        mClienteEmEdicao.getCep());
            }

            if (!StringUtils.isEmpty(mClienteEmEdicao.getComplemento())) {
                getView().setViewValue(
                        mResourcesRepository.obtainComplementoViewId(),
                        mClienteEmEdicao.getComplemento());
            }

            if (!StringUtils.isEmpty(mClienteEmEdicao.getTelefone())) {
                getView().setViewValue(
                        mResourcesRepository.obtainTelefoneViewId(),
                        mClienteEmEdicao.getTelefone());
            }

            if (!StringUtils.isEmpty(mClienteEmEdicao.getTelefone2())) {
                getView().setViewValue(
                        mResourcesRepository.obtainCelularViewId(),
                        mClienteEmEdicao.getTelefone2());
            }
        }
    }

    private void loadEstados() {
        Observable<List<Estado>> estadosFromMemoryCache = ObservableUtils
                .toObservable(mEstadosList);

        Observable<List<Estado>> estadosFromDiskCache = mEstadoRepository
                .list()
                .doOnNext(pEstadosList -> mEstadosList = pEstadosList);

        addSubscription(Observable.concat(estadosFromMemoryCache, estadosFromDiskCache)
                .firstOrDefault(Collections.emptyList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pEstadosList -> {
                            getView().displayEstados(mEstadosList);
                            initializeEstadosFieldsIfEmEdicao();
                        },

                        Timber::e
                ));
    }

    private void initializeEstadosFieldsIfEmEdicao() {
        if (isEditing()) {
            int indexOfEstado = mEstadosList.indexOf(mClienteEmEdicao.getCidade().getEstado());
            getView().setViewValue(mResourcesRepository.obtainEstadosViewId(), indexOfEstado);
        }
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onUsuarioLogadoEvent(
            UsuarioLogadoEvent pEvent) {
        mVendedorLogado = pEvent.getVendedor();
        mEmpresaLogada = pEvent.getEmpresa();
    }

    @Override public void handleActionSave() {
        getView().hideRequiredMessages();

        if (!getView().hasEmptyRequiredFields()) {
            addSubscription(mClienteRepository.save(clienteFromFields())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::notifyClienteSalvo,

                            Timber::e
                    ));
        } else {
            getView().displayRequiredFieldMessages();
        }
    }

    private Cliente clienteFromFields() {
        final String nomeCliente = getView().getViewStringValue(
                mResourcesRepository.obtainNomeViewId());

        final int positionTipoPessoa = getView().getViewPositionValue(
                mResourcesRepository.obtainTiposPessoaViewId());

        final String cpfOuCnpj = getView().getViewStringValue(
                mResourcesRepository.obtainCpfOuCnpjViewId());

        final String email = getView().getViewStringValue(
                mResourcesRepository.obtainEmailViewId());

        final String telefone = getView().getViewStringValue(
                mResourcesRepository.obtainTelefoneViewId());

        final String celular = getView().getViewStringValue(
                mResourcesRepository.obtainCelularViewId());

        final String endereco = getView().getViewStringValue(
                mResourcesRepository.obtainEnderecoViewId());

        final String cep = getView().getViewStringValue(
                mResourcesRepository.obtainCepViewId());

        final int positionCidade = getView().getViewPositionValue(
                mResourcesRepository.obtainCidadesViewId());

        final String bairro = getView().getViewStringValue(
                mResourcesRepository.obtainBairroViewId());

        final String numero = getView().getViewStringValue(
                mResourcesRepository.obtainNumeroViewId());

        final String complemento = getView().getViewStringValue(
                mResourcesRepository.obtainComplementoViewId());

        if (isEditing()) {
            return new Cliente(
                    mClienteEmEdicao.getId(),
                    mClienteEmEdicao.getIdCliente(),
                    mClienteEmEdicao.getCodigo(),
                    nomeCliente,
                    mTiposPessoaList.get(positionTipoPessoa).getIntType(),
                    cpfOuCnpj,
                    mClienteEmEdicao.getContato(),
                    email,
                    telefone,
                    celular,
                    endereco,
                    cep,
                    mCidadesList.get(positionCidade),
                    bairro,
                    numero,
                    complemento,
                    ApiUtils.formatApiDateTime(System.currentTimeMillis()),
                    mClienteEmEdicao.isAtivo()
            );
        } else {
            return new Cliente(
                    nomeCliente,
                    mTiposPessoaList.get(positionTipoPessoa).getIntType(),
                    cpfOuCnpj,
                    email,
                    telefone,
                    celular,
                    endereco,
                    cep,
                    mCidadesList.get(positionCidade),
                    bairro,
                    numero,
                    complemento
            );
        }
    }

    private void notifyClienteSalvo(final Cliente pCliente) {
        if (isEditing()) {
            getView().resultClienteEditado(pCliente);
        } else {
            EventBus.getDefault().post(NovoClienteEvent.newEvent(pCliente));
            getView().finishView();
        }
    }

    @Override public void handleTiposPessoaSpinnerItemSelected(int pPosition) {
        TipoPessoa tipoPessoa = getItemFromList(mTiposPessoaList, pPosition);
        if (tipoPessoa != null) {
            getView().changeViewForTipoPessoa(tipoPessoa.getCharCount());
            getView().showFocusOnFieldCpfOuCnpj();
        } else {
            getView().removeFocusOnFieldCpfOuCnpj();
        }
    }

    @Override public void handleTiposPessoaSpinnerNothingSelected() {
        getView().removeFocusOnFieldCpfOuCnpj();
    }

    @Override public void handleEstadosItemSelected(int pPosition) {
        Estado estado = getItemFromList(mEstadosList, pPosition);
        if (estado != null) {
            addSubscription(mCidadeRepository.list(estado.getIdEstado())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::cacheAndDisplayCidades,

                            Timber::e
                    ));
        }
    }

    private void cacheAndDisplayCidades(List<Cidade> pCidadesList) {
        mCidadesList = pCidadesList;
        getView().displayCidades(mCidadesList);
        initializeCidadesFieldsIfEmEdicao();
    }

    @Override public void handleEstadosSpinnerNothingSelected() {
        if (mCidadesList != null && !mCidadesList.isEmpty()) {
            mCidadesList.clear();
            getView().updateCidades();
        }
    }

    private void initializeCidadesFieldsIfEmEdicao() {
        if (isEditing()) {
            int indexOfCidade = mCidadesList.indexOf(mClienteEmEdicao.getCidade());
            getView().setViewValue(mResourcesRepository.obtainCidadesViewId(), indexOfCidade);
        }
    }

    @Override public void handleBackPressed() {
        if (hasUnmodifiedFields()) {
            getView().finishView();
        } else {
            getView().showExitViewQuestion();
        }
    }

    private boolean hasUnmodifiedFields() {
        if (!isEditing()) {
            return getView().hasUnmodifiedFields();
        }

        Cliente clienteFromFields = clienteFromFields();

        return clienteFromFields.getTipo() == mClienteEmEdicao.getTipo() &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getCpfCnpj(), mClienteEmEdicao.getCpfCnpj()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getNome(), mClienteEmEdicao.getNome()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getEmail(), mClienteEmEdicao.getEmail()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getEndereco(), mClienteEmEdicao.getEndereco()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getNumero(), mClienteEmEdicao.getNumero()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getBairro(), mClienteEmEdicao.getBairro()) &&
                clienteFromFields.getCidade()
                        .equals(mClienteEmEdicao.getCidade()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getCep(), mClienteEmEdicao.getCep()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getComplemento(), mClienteEmEdicao.getComplemento()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getTelefone(), mClienteEmEdicao.getTelefone()) &&
                equalsIgnoringNullOrWhitespace(
                        clienteFromFields.getTelefone2(), mClienteEmEdicao.getTelefone2());
    }

    private boolean isEditing() {
        return mClienteEmEdicao != null;
    }

    private <T> T getItemFromList(List<T> list, int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }
}