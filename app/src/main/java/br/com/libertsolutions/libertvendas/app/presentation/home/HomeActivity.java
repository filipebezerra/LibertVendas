package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;

/**
 * @author Filipe Bezerra
 */
public class HomeActivity extends LibertVendasActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.View {

    private HomeContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new HomePresenter(this);

        setAsHomeActivity();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_home;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        item.setChecked(false);

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_pedidos) {

        } else if (id == R.id.nav_clientes) {
            mPresenter.clickNavigationMenuClientes();
        } else if (id == R.id.nav_produtos) {
            mPresenter.clickNavigationMenuProdutos();
        } else if (id == R.id.nav_settings) {
            mPresenter.clickNavigationMenuSettings();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void navigateToSettings() {
        navigate().toSettings();
    }

    @Override
    public void navigateToClientes() {
        navigate().toClientes(getSupportFragmentManager());
    }

    @Override
    public void navigateToProdutos() {
        navigate().toProdutos(getSupportFragmentManager());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
