package br.com.libertsolutions.libertvendas.app.presentation.login;

import android.os.Bundle;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;

public class LoginActivity extends LibertVendasActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_login;
    }
}
