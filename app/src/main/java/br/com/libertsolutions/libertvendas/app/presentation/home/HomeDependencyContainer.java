package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
class HomeDependencyContainer {

    private final SettingsRepository mSettingsRepository;

    private final Repository<Vendedor> mVendedorRepository;

    private HomeDependencyContainer(@NonNull HomeActivity pActivity) {
        mSettingsRepository = Injection.provideSettingsRepository(pActivity);
        mVendedorRepository = Injection.provideVendedorRepository();
    }

    static HomeDependencyContainer createDependencyContainer(@NonNull HomeActivity pActivity) {
        return new HomeDependencyContainer(pActivity);
    }

    SettingsRepository getSettingsRepository() {
        return mSettingsRepository;
    }

    Repository<Vendedor> getVendedorRepository() {
        return mVendedorRepository;
    }
}
