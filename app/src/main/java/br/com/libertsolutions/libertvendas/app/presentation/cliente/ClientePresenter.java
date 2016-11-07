package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ClienteResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Filipe Bezerra
 */
class ClientePresenter implements ClienteContract.Presenter {
    private final ClienteContract.View mView;

    private final Repository<Cliente> mClienteRepository;

    private final ClienteResourcesRepository mResourcesRepository;

    private final Repository<Estado> mEstadoRepository;

    private final Repository<Cidade> mCidadeRepository;

    private List<String> mTiposPessoaList;

    private List<Estado> mEstadoList;

    private List<Cidade> mCidadeList;

    ClientePresenter(
            ClienteContract.View pView,
            Repository<Cliente> pClienteRepository,
            ClienteResourcesRepository pResourcesRepository,
            Repository<Estado> pEstadoRepository,
            Repository<Cidade> pCidadeRepository) {
        mView = pView;
        mClienteRepository = pClienteRepository;
        mResourcesRepository = pResourcesRepository;
        mEstadoRepository = pEstadoRepository;
        mCidadeRepository = pCidadeRepository;
    }

    @Override public void initializeView() {
        mTiposPessoaList = mResourcesRepository.loadTiposPessoa();
        mView.showTiposPessoa(mTiposPessoaList);

        mEstadoRepository
                .list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pEstados -> {
                            mEstadoList = pEstados;
                            mView.showEstados(mEstadoList);
                        }
                );
    }

    @Override public void clickActionSave() {
        mView.hideRequiredMessages();

        final ClienteViewModel viewModel = mView.extractViewModel();

        if (!shownViewModelErrors(viewModel)) {
            Cliente newCliente = new Cliente(
                    viewModel.nome,
                    viewModel.tipoPessoa,
                    viewModel.cpfCnpj,
                    null, //viewModel.contato,
                    viewModel.email,
                    viewModel.telefone,
                    viewModel.celular,
                    viewModel.endereco,
                    viewModel.cep,
                    mCidadeList.get(viewModel.cidade),
                    viewModel.bairro,
                    viewModel.numero,
                    viewModel.complemento
            );

            mClienteRepository
                    .save(newCliente)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mView::resultNewCliente);
        }
    }

    private boolean shownViewModelErrors(ClienteViewModel pViewModel) {
        if (!pViewModel.hasRequiredValues()) {
            if (!pViewModel.hasCidade()) {
                mView.displayRequiredMessageForFieldCidade();
            } else if (!pViewModel.hasEstado()) {
                mView.displayRequiredMessageForFieldEstado();
            }

            if (!pViewModel.hasNome()) {
                mView.displayRequiredMessageForFieldNome();
            }

            if (!pViewModel.hasTipoPessoa()) {
                mView.displayRequiredMessageForFieldTipoPessoa();
            } else if (!pViewModel.hasCpfCnpj()) {
                mView.displayRequiredMessageForFieldCpfCnpj();
            }

            mView.showFeedbackMessage(mResourcesRepository.obtainStringMessageFieldsRequired());
            return true;
        } else {
            return false;
        }
    }

    @Override public void clickSelectTipoPessoa() {
        final String tipoPessoa = getValue(mTiposPessoaList, mView.extractViewModel().tipoPessoa);
        if (!StringUtils.isEmpty(tipoPessoa)) {
            if (tipoPessoa.equalsIgnoreCase("Pessoa Física")) {
                mView.showViewsForPessoaFisica(11);
            } else {
                mView.showViewsForPessoaJuridica(14);
            }
            mView.showFocusOnFieldCpfCnpj();
        } else {
            mView.removeFocusOnFieldCpfCnpj();
        }
    }

    @Override public void clickSelectEstado() {
        Estado estado = getValue(mEstadoList, mView.extractViewModel().estado);
        if (estado != null) {
            ((CidadeRepository)mCidadeRepository)
                    .list(estado.getIdEstado())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            pCidades -> {
                                mCidadeList = pCidades;
                                mView.showCidades(mCidadeList);
                            }
                    );
        }
    }

    @Override public void handleBackPressed() {
        if (canGoBack()) {
            mView.finishView();
        } else {
            mView.showExitViewQuestion();
        }
    }

    @Override public void finalizeView() {
        mView.finishView();
    }

    private boolean canGoBack() {
        return mView.extractViewModel().hasDefaultValues();
    }

    private <T> T getValue(List<T> list, int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }
}
