package br.com.libertsolutions.libertvendas.app.presentation.launch;

import android.os.Bundle;
import android.os.Handler;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;

/**
 * @author Filipe Bezerra
 */
public class LaunchActivity extends LibertVendasActivity implements LaunchContract.View {

    private LaunchContract.Presenter mPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LaunchPresenter(this);
        mPresenter.startSplascreenTimeout();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.activity_launch;
    }

    @Override public void startHandler(Runnable runnable, int splashTime) {
        new Handler().postDelayed(runnable, splashTime);
    }

    @Override public void navigateToSettingsActivity() {
        navigate().toSettings(true);
    }
}
