package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
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

    private List<String> mTiposPessoaList;

    private List<String> mEstadosList;

    ClientePresenter(
            ClienteContract.View pView,
            Repository<Cliente> pClienteRepository,
            ClienteResourcesRepository pResourcesRepository) {
        mView = pView;
        mClienteRepository = pClienteRepository;
        mResourcesRepository = pResourcesRepository;
    }

    @Override
    public void initializeView() {
        mTiposPessoaList = mResourcesRepository.loadTiposPessoa();
        mView.showTiposPessoa(mTiposPessoaList);
        mEstadosList = mResourcesRepository.loadEstados();
        mView.showEstados(mEstadosList);
    }

    @Override
    public void clickActionSave() {
        mView.hideRequiredMessages();
        boolean hasEmptyRequiredFields = false;

        final ClienteViewModel viewModel = mView.extractViewModel();

        if (StringUtils.isEmpty(viewModel.cidade)) {
            mView.displayRequiredMessageForFieldCidade();
            hasEmptyRequiredFields = true;
        }

        if (StringUtils.isEmpty(getValue(mEstadosList, viewModel.estado))) {
            mView.displayRequiredMessageForFieldEstado();
            hasEmptyRequiredFields = true;
        }

        if (StringUtils.isEmpty(viewModel.nome)) {
            mView.displayRequiredMessageForFieldNome();
            hasEmptyRequiredFields = true;
        }

        if (StringUtils.isEmpty(getValue(mTiposPessoaList, viewModel.tipoPessoa))) {
            mView.displayRequiredMessageForFieldTipoPessoa();
            hasEmptyRequiredFields = true;
        } else if (StringUtils.isEmpty(viewModel.cpfCnpj)) {
            mView.displayRequiredMessageForFieldCpfCnpj();
            hasEmptyRequiredFields = true;
        }

        if (!hasEmptyRequiredFields) {
            Cliente newCliente = Cliente.newCliente(
                    viewModel.nome,
                    viewModel.tipoPessoa,
                    viewModel.cpfCnpj,
                    viewModel.email,
                    viewModel.telefone,
                    viewModel.celular,
                    viewModel.endereco,
                    viewModel.cep,
                    viewModel.bairro,
                    viewModel.numero,
                    viewModel.complemento,
                    viewModel.cidade,
                    getValue(mEstadosList, viewModel.estado)
            );

            mClienteRepository
                    .save(newCliente)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mView::resultNewCliente);
        } else {
            mView.showFeedbackMessage(mResourcesRepository.obtainStringMessageFieldsRequired());
        }
    }

    @Override
    public void clickSelectTipoPessoa() {
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

    @Override
    public void handleBackPressed() {
        if (canGoBack()) {
            mView.finishView();
        } else {
            mView.showExitViewQuestion();
        }
    }

    @Override
    public void finalizeView() {
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
