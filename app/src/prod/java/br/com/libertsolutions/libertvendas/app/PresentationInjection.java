package br.com.libertsolutions.libertvendas.app;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepositories;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.CadastroClienteResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.FinalizandoPedidoResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ResourcesRepositories;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ConnectivityServices;
import br.com.libertsolutions.libertvendas.app.presentation.utils.PresentationServices;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Filipe Bezerra
 */
public final class PresentationInjection {

    private static EventBus sEventBus;

    private PresentationInjection() {/* No instances */}

    public static Context provideContext() {
        return LibertVendasApplication.getInstance();
    }

    public static ConnectivityServices provideConnectivityServices() {
        return PresentationServices.provideConnectivityServices(provideContext());
    }

    public static EventBus provideEventBus() {
        if (sEventBus == null) {
            sEventBus = EventBus.builder()
                    .logNoSubscriberMessages(BuildConfig.DEBUG)
                    .sendNoSubscriberEvent(BuildConfig.DEBUG)
                    .build();
        }
        return sEventBus;
    }

    public static SettingsRepository provideSettingsRepository() {
        return SettingsRepositories.getRepository(provideContext());
    }

    public static CadastroClienteResourcesRepository provideCadastroClienteResourcesRepository() {
        return ResourcesRepositories.getCadastroClienteResources(provideContext());
    }

    public static FinalizandoPedidoResourcesRepository provideFinalizandoPedidoResourcesRepository() {
        return ResourcesRepositories.getFinalizandoPedidoResources(provideContext());
    }
}
