package br.com.libertsolutions.libertvendas.app;

import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeRepositories;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeService;
import br.com.libertsolutions.libertvendas.app.data.cidade.EstadoRepositories;
import br.com.libertsolutions.libertvendas.app.data.cidade.EstadoRepository;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoRepositories;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoRepository;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepositories;
import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaRepositories;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaRepository;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaService;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepositories;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorService;

import static br.com.libertsolutions.libertvendas.app.PresentationInjection.provideContext;

/**
 * @author Filipe Bezerra
 */
public final class DataInjection {

    private DataInjection() {/* No instances */}

    public static final class LocalRepositories {

        public static VendedorRepository provideVendedorRepository() {
            return VendedorRepositories.getRepository();
        }

        public static FormaPagamentoRepository provideFormaPagamentoRepository() {
            return FormaPagamentoRepositories.getRepository();
        }

        public static CidadeRepository provideCidadeRepository() {
            return CidadeRepositories.getRepository();
        }

        public static ClienteRepository provideClienteRepository() {
            return ClienteRepositories.getRepository();
        }

        public static TabelaRepository provideTabelaRepository() {
            return TabelaRepositories.getRepository();
        }

        public static EstadoRepository provideEstadoRepository() {
            return EstadoRepositories.getRepository();
        }

        public static PedidoRepository providePedidoRepository() {
            return PedidoRepositories.getRepository();
        }
    }

    public static final class RemoteRepositories {

        public static VendedorService provideVendedorService() {
            return VendedorRepositories.getService(provideContext());
        }

        public static FormaPagamentoService provideFormaPagamentoService() {
            return FormaPagamentoRepositories.getService(provideContext());
        }

        public static CidadeService provideCidadeService() {
            return CidadeRepositories.getService(provideContext());
        }

        public static ClienteService provideClienteService() {
            return ClienteRepositories.getService(provideContext());
        }

        public static TabelaService provideTabelaService() {
            return TabelaRepositories.getService(provideContext());
        }
    }
}
