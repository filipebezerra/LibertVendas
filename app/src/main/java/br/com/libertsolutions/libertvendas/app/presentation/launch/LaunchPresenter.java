package br.com.libertsolutions.libertvendas.app.presentation.launch;

/**
 * @author Filipe Bezerra
 */
class LaunchPresenter implements LaunchContract.Presenter {
    private static final int SPLASH_TIME = 3000;

    private final LaunchContract.View mView;

    LaunchPresenter(LaunchContract.View view) {
        mView = view;
    }

    @Override
    public void startSplascreenTimeout() {
        mView.startHandler(this::dispatchNavigationToSettingsActivity, SPLASH_TIME);
    }

    @Override
    public void dispatchNavigationToSettingsActivity() {
        mView.navigateToSettingsActivity();
    }
}
