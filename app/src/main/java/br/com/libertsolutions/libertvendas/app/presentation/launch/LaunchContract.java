package br.com.libertsolutions.libertvendas.app.presentation.launch;

/**
 * @author Filipe Bezerra
 */
interface LaunchContract {
    interface View {
        void startHandler(Runnable runnable, int splashTime);

        void navigateToSettingsActivity();
    }

    interface Presenter {
        void startSplascreenTimeout();

        void dispatchNavigationToSettingsActivity();
    }
}
