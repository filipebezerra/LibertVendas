package br.com.libertsolutions.libertvendas.app.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseActivity;
import br.com.libertsolutions.libertvendas.app.presentation.base.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.CustomerListFragment;
import br.com.libertsolutions.libertvendas.app.presentation.dashboard.DashboardFragment;
import br.com.libertsolutions.libertvendas.app.presentation.login.CompletedLoginEvent;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.OrderListPageFragment;
import br.com.libertsolutions.libertvendas.app.presentation.productlist.ProductListFragment;
import br.com.libertsolutions.libertvendas.app.presentation.widget.SheetFloatingActionButton;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.TextDrawable.IShapeBuilder;
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondarySwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent.logged;
import static br.com.libertsolutions.libertvendas.app.presentation.util.AndroidUtils.dpToPx;
import static com.amulyakhare.textdrawable.util.ColorGenerator.MATERIAL;

/**
 * @author Filipe Bezerra
 */
public class MainActivity extends BaseActivity implements Drawer.OnDrawerItemClickListener {

    private static final int DRAWER_ITEM_DASHBOARD = 1;
    private static final int DRAWER_ITEM_ORDERS_REPORT = 2;
    private static final int DRAWER_ITEM_ORDERS = 3;
    private static final int DRAWER_ITEM_CUSTOMERS = 4;
    private static final int DRAWER_ITEM_PRODUCTS = 5;
    private static final int DRAWER_ITEM_AUTO_SYNC = 6;
    private static final int DRAWER_ITEM_SETTINGS = 7;

    private MaterialSheetFab<SheetFloatingActionButton> mMaterialSheetFab;

    private Drawer mDrawer;

    private AccountHeader mAccountHeader;

    private PrimaryDrawerItem mDashboardDrawerItem;

    private PrimaryDrawerItem mOrdersReportDrawerItem;

    private PrimaryDrawerItem mOrdersDrawerItem;

    private PrimaryDrawerItem mCustomersDrawerItem;

    private PrimaryDrawerItem mProductsDrawerItem;

    private SecondarySwitchDrawerItem mAutoSyncDrawerItem;

    private SecondaryDrawerItem mSettingsDrawerItem;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @BindView(R.id.fab_all_main_action) protected SheetFloatingActionButton mFloatingActionButton;
    @BindView(R.id.fab_sheet) protected CardView mFabSheet;
    @BindView(R.id.overlay) protected DimOverlayFrameLayout mFabOverlay;

    @BindColor(R.color.color_background_card) protected int mFabSheetColor;
    @BindColor(R.color.color_accent) protected int mAccentColor;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_main;
    }

    @Override protected void onCreate(Bundle inState) {
        navigateToInitialFlowIfNeed();
        super.onCreate(inState);
        setAsHomeActivity();
        initializeFab();
        initializeDrawerHeader(inState);
        initializeDrawer(inState);
    }

    @Override protected void onStart() {
        super.onStart();
        if (settings().isUserLoggedIn()) {
            loadLoggedUser();
        }
    }

    @Override public boolean onItemClick(
            final View view, final int position, final IDrawerItem drawerItem) {
        switch ((int) drawerItem.getIdentifier()) {
            case DRAWER_ITEM_DASHBOARD: {
                if (!isViewingDashboard()) {
                    navigateToDashboard();
                }
                break;
            }
            case DRAWER_ITEM_ORDERS_REPORT: {
                if (!isViewingOrdersReport()) {
                    navigateToOrdersReport();
                }
                break;
            }
            case DRAWER_ITEM_ORDERS: {
                if (!isViewingOrderList()) {
                    navigateToOrderList();
                }
                break;
            }
            case DRAWER_ITEM_CUSTOMERS: {
                if (!isViewingCustomerList()) {
                    navigateToCustomerList();
                }
                break;
            }
            case DRAWER_ITEM_PRODUCTS: {
                if (!isViewingProductList()) {
                    navigateToProductList();
                }
                break;
            }
            case DRAWER_ITEM_SETTINGS: {
                navigate().toSettings();
                break;
            }
        }
        return false;
    }

    @OnClick(R.id.main_new_order_sheet) void onNewOrderSheetClicked() {
        mMaterialSheetFab.hideSheet();
        navigate().toAddOrder();
    }

    @OnClick(R.id.main_new_customer_sheet) void onNewCustomerSheetClicked() {
        mMaterialSheetFab.hideSheet();
        navigate().toAddCustomer();
    }

    @Override public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else if (mMaterialSheetFab.isSheetVisible()) {
            mMaterialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (mDrawer != null) {
            outState = mDrawer.saveInstanceState(outState);
        }
        if (mAccountHeader != null) {
            outState = mAccountHeader.saveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override protected void onActivityResult(
            final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case Navigator.REQUEST_INITIAL_FLOW: {
                if (resultCode == RESULT_OK) {
                    loadLoggedUser();
                } else {
                    finish();
                }
                break;
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void navigateToInitialFlowIfNeed() {
        if (!settings().isInitialFlowDone()) {
            navigate().toInitialFlow();
        }
    }

    private void initializeFab() {
        mMaterialSheetFab = new MaterialSheetFab<>(
                mFloatingActionButton, mFabSheet, mFabOverlay, mFabSheetColor, mAccentColor);
    }

    private void initializeDrawerHeader(final Bundle inState) {
        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.main_header_background)
                .withSavedInstance(inState)
                .withOnlyMainProfileImageVisible(true)
                .withCurrentProfileHiddenInList(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener((view, profile, current) -> {
                    changeLoggedUser(profile);
                    return false;
                })
                .build();
    }

    private void initializeDrawer(final Bundle inState) {
        mDashboardDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_DASHBOARD)
                .withName(R.string.main_drawer_item_dashboard)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.main_drawer_home, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mOrdersReportDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_ORDERS_REPORT)
                .withName(R.string.main_drawer_item_orders_report)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.main_drawer_orders_report, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mOrdersDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_ORDERS)
                .withName(R.string.main_drawer_item_orders)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.main_drawer_orders, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mCustomersDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_CUSTOMERS)
                .withName(R.string.main_drawer_item_customers)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.main_drawer_customers, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mProductsDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_PRODUCTS)
                .withName(R.string.main_drawer_item_products)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.main_drawer_products, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mAutoSyncDrawerItem = new SecondarySwitchDrawerItem()
                .withIdentifier(DRAWER_ITEM_AUTO_SYNC)
                .withName(R.string.main_drawer_item_auto_sync)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.main_drawer_auto_sync, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
                .withChecked(settings().getSettings().isAutomaticallySyncOrders())
                .withSelectable(false)
                .withOnCheckedChangeListener((drawerItem, buttonView, isChecked) -> {
                    settings().setAutoSyncOrders(isChecked);
                })
        ;

        mSettingsDrawerItem = new SecondaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_SETTINGS)
                .withName(R.string.main_drawer_item_settings)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.main_drawer_settings, getTheme()))
                .withSelectable(false)
        ;

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(
                        mDashboardDrawerItem,
                        mOrdersReportDrawerItem,
                        mOrdersDrawerItem,
                        mCustomersDrawerItem,
                        mProductsDrawerItem,
                        new DividerDrawerItem(),
                        mAutoSyncDrawerItem,
                        mSettingsDrawerItem
                )
                .withSavedInstance(inState)
                .withShowDrawerOnFirstLaunch(true)
                .withOnDrawerItemClickListener(this)
                .withSelectedItem(DRAWER_ITEM_DASHBOARD)
                .withFireOnInitialOnClick(true)
                .build();
    }

    private void loadLoggedUser() {
        CompletedLoginEvent completedLoginEvent
                = eventBus().getStickyEvent(CompletedLoginEvent.class);

        if (completedLoginEvent != null) {
            eventBus().removeStickyEvent(CompletedLoginEvent.class);
            showLoggedUser(completedLoginEvent.getUser());
        } else {
            Subscription subscription = settings().getLoggedUser()
                    .subscribe(this::showLoggedUser);
            mCompositeSubscription.add(subscription);
        }
    }

    private void showLoggedUser(final LoggedUser loggedUser) {
        eventBus().postSticky(logged(loggedUser));
        createProfiles(loggedUser);
    }

    private void createProfiles(final LoggedUser loggedUser) {
        IShapeBuilder builder = createShapeBuilder();

        List<IProfile> profiles = new ArrayList<>();
        IProfile activeProfile = null;
        for (Company company : loggedUser.getSalesman().getCompanies()) {
            final String name = generateNameWithTwoLetters(company.getName());

            ProfileDrawerItem profile = new ProfileDrawerItem()
                    .withName(loggedUser.getSalesman().getName())
                    .withEmail(company.getName())
                    .withIcon(builder.buildRound(name, MATERIAL.getRandomColor()))
                    .withTag(company)
                    .withSetSelected(company.equals(loggedUser.getDefaultCompany()));

            if (profile.isSelected()) {
                activeProfile = profile;
            }

            profiles.add(profile);
        }

        setProfiles(profiles, activeProfile);
    }

    private IShapeBuilder createShapeBuilder() {
        return TextDrawable.builder()
                .beginConfig()
                .fontSize(dpToPx(this, 20))
                .toUpperCase()
                .endConfig();
    }

    private String generateNameWithTwoLetters(String name) {
        String[] parts = name.trim().split("\\s+");

        if (parts.length == 1) {
            return parts[0].substring(0, 2);
        } else {
            return parts[0].substring(0, 1).concat(parts[1].substring(0, 1));
        }
    }

    private void setProfiles(final List<IProfile> profiles, final IProfile activeProfile) {
        mAccountHeader.setProfiles(profiles);
        mAccountHeader.setActiveProfile(activeProfile);
    }

    private void changeLoggedUser(IProfile profile) {
        LoggedUser loggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        final Object tag = ((ProfileDrawerItem) profile).getTag();
        final Company company = (Company) tag;

        loggedUser = loggedUser.withDefaultCompany(company);
        settings().setLoggedUser(loggedUser.getSalesman(), company);
        eventBus().postSticky(logged(loggedUser));
    }

    private boolean isViewingDashboard() {
        return isViewingFragmentByTag(DashboardFragment.TAG);
    }

    private void navigateToDashboard() {
        navigate().toDashboard();
    }

    private boolean isViewingOrdersReport() {
        return isViewingFragmentByTag(ProductListFragment.TAG);
    }

    private void navigateToOrdersReport() {
        navigate().toOrdersReport();
    }

    private boolean isViewingOrderList() {
        return isViewingFragmentByTag(OrderListPageFragment.TAG);
    }

    private void navigateToOrderList() {
        navigate().toOrderList();
    }

    private boolean isViewingCustomerList() {
        return isViewingFragmentByTag(CustomerListFragment.TAG);
    }

    private void navigateToCustomerList() {
        navigate().toCustomerList();
    }

    private boolean isViewingProductList() {
        return isViewingFragmentByTag(ProductListFragment.TAG);
    }

    private void navigateToProductList() {
        navigate().toProductList();
    }

    private boolean isViewingFragmentByTag(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment != null && fragment.isVisible();
    }

    @Override protected void onDestroy() {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }
}
