package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.os.Bundle;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;

public class HomeActivity extends LibertVendasActivity {

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAsHomeActivity();
    }
}
