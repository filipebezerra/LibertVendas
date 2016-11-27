package br.com.libertsolutions.libertvendas.app;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepositories;
import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeService;
import br.com.libertsolutions.libertvendas.app.data.cidades.EstadoRepositories;
import br.com.libertsolutions.libertvendas.app.data.cidades.EstadoRepository;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoRepositories;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoRepository;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.importacao.ImportacaoRepositories;
import br.com.libertsolutions.libertvendas.app.data.importacao.ImportacaoRepository;
import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoRepositories;
import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoService;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoRepositories;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoRepository;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoService;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepositories;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.tabelaspreco.TabelaPrecoRepositories;
import br.com.libertsolutions.libertvendas.app.data.tabelaspreco.TabelaPrecoRepository;
import br.com.libertsolutions.libertvendas.app.data.tabelaspreco.TabelaPrecoService;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepositories;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorService;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ClienteResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.CommonResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ImportacaoResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ResourcesRepositories;
import br.com.libertsolutions.libertvendas.app.presentation.resources.SelecioneProdutosResourcesRepository;

/**
 * @author Filipe Bezerra
 */
public class Injection {

    public static ImportacaoRepository provideImportacaoRepository(@NonNull Context pContext) {
        return ImportacaoRepositories.getImportacaoRepository(pContext);
    }

    public static ImportacaoResourcesRepository provideImportacaoResourcesRepository(
            @NonNull Context pContext) {
        return ResourcesRepositories.getImportacaoResourcesRepository(pContext);
    }

    public static SettingsRepository provideSettingsRepository(@NonNull Context pContext) {
        return SettingsRepositories.getRepository(pContext);
    }

    public static FormaPagamentoService provideFormaPagamentoService(@NonNull Context pContext) {
        return FormaPagamentoRepositories.getService(pContext);
    }

    public static FormaPagamentoRepository provideFormaPagamentoRepository() {
        return FormaPagamentoRepositories.getRepository();
    }

    public static CidadeService provideCidadeService(@NonNull Context pContext) {
        return CidadeRepositories.getService(pContext);
    }

    public static CidadeRepository provideCidadeRepository() {
        return CidadeRepositories.getRepository();
    }

    public static EstadoRepository provideEstadoRepository() {
        return EstadoRepositories.getRepository();
    }

    public static ProdutoService provideProdutoService(@NonNull Context pContext) {
        return ProdutoRepositories.getService(pContext);
    }

    public static ProdutoRepository provideProdutoRepository() {
        return ProdutoRepositories.getRepository();
    }

    public static ClienteService provideClienteService(@NonNull Context pContext) {
        return ClienteRepositories.getService(pContext);
    }

    public static ClienteRepository provideClienteRepository() {
        return ClienteRepositories.getRepository();
    }

    public static CommonResourcesRepository provideCommonResourcesRepository(
            @NonNull Context pContext) {
        return ResourcesRepositories.getCommonResources(pContext);
    }

    public static ClienteResourcesRepository provideClienteResourcesRepository(
            @NonNull Context context) {
        return ResourcesRepositories.getClienteResources(context);
    }

    public static PedidoService providePedidoService(@NonNull Context pContext) {
        return PedidoRepositories.getService(pContext);
    }

    public static PedidoRepository providePedidoRepository() {
        return PedidoRepositories.getRepository();
    }

    public static VendedorService provideVendedorService(@NonNull Context pContext) {
        return VendedorRepositories.getService(pContext);
    }

    public static VendedorRepository provideVendedorRepository() {
        return VendedorRepositories.getRepository();
    }

    public static TabelaPrecoService provideTabelaPrecoService(@NonNull Context pContext) {
        return TabelaPrecoRepositories.getService(pContext);
    }

    public static TabelaPrecoRepository provideTabelaPrecoRepository() {
        return TabelaPrecoRepositories.getRepository();
    }

    public static SelecioneProdutosResourcesRepository provideSelecioneProdutosResourcesRepository(
            @NonNull Context context) {
        return ResourcesRepositories.getSelecioneProdutosResourcesRepository(context);
    }

}
