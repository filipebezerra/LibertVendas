package br.com.libertsolutions.libertvendas.app;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeRepositories;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeService;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoRepositories;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoRepository;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaRepositories;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaRepository;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaService;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepositories;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorService;

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
    }

    public static final class RemoteRepositories {

        public static VendedorService provideVendedorService(@NonNull Context context) {
            return VendedorRepositories.getService(context);
        }

        public static FormaPagamentoService provideFormaPagamentoService(@NonNull Context context) {
            return FormaPagamentoRepositories.getService(context);
        }

        public static CidadeService provideCidadeService(@NonNull Context context) {
            return CidadeRepositories.getService(context);
        }

        public static ClienteService provideClienteService(@NonNull Context context) {
            return ClienteRepositories.getService(context);
        }

        public static TabelaService provideTabelaService(@NonNull Context context) {
            return TabelaRepositories.getService(context);
        }
    }
}
