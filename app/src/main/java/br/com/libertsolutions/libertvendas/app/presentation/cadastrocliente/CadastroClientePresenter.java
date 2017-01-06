package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.cidade.EstadoRepository;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.data.sync.SyncTaskService;
import br.com.libertsolutions.libertvendas.app.data.utils.ApiUtils;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TipoPessoa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.resources.CadastroClienteResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ObservableUtils;
import br.com.libertsolutions.libertvendas.app.presentation.utils.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.NewCustomerEvent.newEvent;
import static br.com.libertsolutions.libertvendas.app.presentation.utils.StringUtils.equalsIgnoringNullOrWhitespace;
import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class CadastroClientePresenter extends BasePresenter<CadastroClienteContract.View>
        implements CadastroClienteContract.Presenter {

    private final ClienteRepository mClienteRepository;

    private final EstadoRepository mEstadoRepository;

    private final CidadeRepository mCidadeRepository;

    private final CadastroClienteResourcesRepository mResourcesRepository;

    private Cliente mClienteEmEdicao;

    private final List<TipoPessoa> mTiposPessoa = Arrays.asList(TipoPessoa.values());

    private List<Estado> mEstados;

    private List<Cidade> mCidades;

    private Vendedor mVendedorLogado;

    CadastroClientePresenter(
            final ClienteRepository clienteRepository,
            final EstadoRepository estadoRepository, final CidadeRepository cidadeRepository,
            final CadastroClienteResourcesRepository resourcesRepository) {
        mClienteRepository = clienteRepository;
        mEstadoRepository = estadoRepository;
        mCidadeRepository = cidadeRepository;
        mResourcesRepository = resourcesRepository;
    }

    @Override public void initializeView(final Cliente clienteEmEdicao) {
        mClienteEmEdicao = clienteEmEdicao;
        initializeViewFields();
        initializeViewRequiredFields();
        getView().displayTiposPessoa(mTiposPessoa);
        loadEstados();
        initializeFieldsIfEmEdicao();
        getLoggedUser();
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

    private void loadEstados() {
        Observable<List<Estado>> estadosFromMemoryCache = ObservableUtils
                .toObservable(mEstados);

        Observable<List<Estado>> estadosFromDiskCache = mEstadoRepository
                .findAll()
                .doOnNext(pEstadosList -> mEstados = pEstadosList);

        addSubscription(Observable.concat(estadosFromMemoryCache, estadosFromDiskCache)
                .firstOrDefault(emptyList())
                .observeOn(mainThread())
                .subscribe(
                        list -> {
                            getView().displayEstados(mEstados);
                            initializeEstadosFieldsIfEmEdicao();
                        },

                        Timber::e
                ));
    }

    private void initializeEstadosFieldsIfEmEdicao() {
        if (isEditing()) {
            int indexOfEstado = mEstados.indexOf(mClienteEmEdicao.getCidade().getEstado());
            getView().setViewValue(mResourcesRepository.obtainEstadosViewId(), indexOfEstado);
        }
    }

    private void initializeFieldsIfEmEdicao() {
        if (isEditing()) {
            getView().changeTitle(mResourcesRepository.obtainStringTitleEditandoCliente());

            for (TipoPessoa tipoPessoa : mTiposPessoa) {
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

            if (!StringUtils.isNullOrEmpty(mClienteEmEdicao.getEmail())) {
                getView().setViewValue(
                        mResourcesRepository.obtainEmailViewId(),
                        mClienteEmEdicao.getEmail());
            }

            if (!StringUtils.isNullOrEmpty(mClienteEmEdicao.getEndereco())) {
                getView().setViewValue(
                        mResourcesRepository.obtainEnderecoViewId(),
                        mClienteEmEdicao.getEndereco());
            }

            if (!StringUtils.isNullOrEmpty(mClienteEmEdicao.getNumero())) {
                getView().setViewValue(
                        mResourcesRepository.obtainNumeroViewId(),
                        mClienteEmEdicao.getNumero());
            }

            if (!StringUtils.isNullOrEmpty(mClienteEmEdicao.getBairro())) {
                getView().setViewValue(
                        mResourcesRepository.obtainBairroViewId(),
                        mClienteEmEdicao.getBairro());
            }

            if (!StringUtils.isNullOrEmpty(mClienteEmEdicao.getCep())) {
                getView().setViewValue(
                        mResourcesRepository.obtainCepViewId(),
                        mClienteEmEdicao.getCep());
            }

            if (!StringUtils.isNullOrEmpty(mClienteEmEdicao.getComplemento())) {
                getView().setViewValue(
                        mResourcesRepository.obtainComplementoViewId(),
                        mClienteEmEdicao.getComplemento());
            }

            if (!StringUtils.isNullOrEmpty(mClienteEmEdicao.getTelefone())) {
                getView().setViewValue(
                        mResourcesRepository.obtainTelefoneViewId(),
                        mClienteEmEdicao.getTelefone());
            }

            if (!StringUtils.isNullOrEmpty(mClienteEmEdicao.getTelefone2())) {
                getView().setViewValue(
                        mResourcesRepository.obtainCelularViewId(),
                        mClienteEmEdicao.getTelefone2());
            }
        }
    }

    private void getLoggedUser() {
        LoggedUserEvent event = EventBus.getDefault().getStickyEvent(LoggedUserEvent.class);
        mVendedorLogado = event.getVendedor();
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
            return Cliente.changed(
                    mClienteEmEdicao,
                    nomeCliente,
                    mTiposPessoa.get(positionTipoPessoa).getIntType(),
                    cpfOuCnpj,
                    email,
                    telefone,
                    celular,
                    endereco,
                    cep,
                    mCidades.get(positionCidade),
                    bairro,
                    numero,
                    complemento,
                    ApiUtils.formatApiDateTime(System.currentTimeMillis()),
                    mVendedorLogado.getEmpresaSelecionada().getCnpj(),
                    mVendedorLogado.getCpfCnpj()
            );
        } else {
            return Cliente.createNew(
                    nomeCliente,
                    mTiposPessoa.get(positionTipoPessoa).getIntType(),
                    cpfOuCnpj,
                    email,
                    telefone,
                    celular,
                    endereco,
                    cep,
                    mCidades.get(positionCidade),
                    bairro,
                    numero,
                    complemento,
                    mVendedorLogado.getEmpresaSelecionada().getCnpj(),
                    mVendedorLogado.getCpfCnpj()
            );
        }
    }

    private void notifyClienteSalvo(final Cliente cliente) {
        if (isEditing()) {
            getView().resultClienteEditado(cliente);
        } else {
            EventBus.getDefault().post(newEvent(cliente));
            getView().finishView();
        }
        SyncTaskService.schedule(PresentationInjection.provideContext(),
                SyncTaskService.SYNC_CUSTOMERS);
    }

    @Override public void handleTipoPessoaSelected(final int position) {
        TipoPessoa tipoPessoa = getItemFromList(mTiposPessoa, position);
        if (tipoPessoa != null) {
            getView().changeViewForTipoPessoa(tipoPessoa.getCharCount());
            getView().showFocusOnFieldCpfOuCnpj();
        } else {
            getView().removeFocusOnFieldCpfOuCnpj();
        }
    }

    @Override public void handleNoneTipoPessoaSelected() {
        getView().removeFocusOnFieldCpfOuCnpj();
    }

    @Override public void handleEstadoSelected(final int position) {
        Estado estado = getItemFromList(mEstados, position);
        if (estado != null) {
            addSubscription(mCidadeRepository.findByIdEstado(estado.getIdEstado())
                    .observeOn(mainThread())
                    .subscribe(
                            this::cacheAndDisplayCidades,

                            Timber::e
                    ));
        }
    }

    private void cacheAndDisplayCidades(List<Cidade> cidades) {
        mCidades = cidades;
        getView().displayCidades(mCidades);
        initializeCidadesFieldsIfEmEdicao();
    }

    private void initializeCidadesFieldsIfEmEdicao() {
        if (isEditing()) {
            int indexOfCidade = mCidades.indexOf(mClienteEmEdicao.getCidade());
            getView().setViewValue(mResourcesRepository.obtainCidadesViewId(), indexOfCidade);
        }
    }

    @Override public void handleNoneEstadoSelected() {
        if (mCidades != null && !mCidades.isEmpty()) {
            mCidades.clear();
            getView().updateCidades();
        }
    }

    @Override public void handleExiting() {
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

    private <T> T getItemFromList(List<T> list, int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    private boolean isEditing() {
        return mClienteEmEdicao != null;
    }

}
