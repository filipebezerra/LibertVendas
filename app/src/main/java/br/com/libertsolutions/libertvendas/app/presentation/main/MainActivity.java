package br.com.libertsolutions.libertvendas.app.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseActivity;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.CustomerListFragment;
import br.com.libertsolutions.libertvendas.app.presentation.dashboard.DashboardFragment;
import br.com.libertsolutions.libertvendas.app.presentation.login.CompletedLoginEvent;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.OrderListPageFragment;
import br.com.libertsolutions.libertvendas.app.presentation.productlist.ProductListFragment;
import br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker;
import butterknife.OnClick;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.TextDrawable.IShapeBuilder;
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

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.text.TextUtils.isEmpty;
import static br.com.libertsolutions.libertvendas.app.presentation.base.Navigator.REQUEST_INITIAL_FLOW;
import static br.com.libertsolutions.libertvendas.app.presentation.base.Navigator.REQUEST_SETTINGS;
import static br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent.logged;
import static br.com.libertsolutions.libertvendas.app.presentation.settings.SettingsFragment.RESULT_AUTO_SYNC_ORDERS_CHANGED;
import static br.com.libertsolutions.libertvendas.app.presentation.util.AndroidUtils.dpToPx;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.ACTION_CHANGED_PROFILE;
import static butterknife.ButterKnife.findById;
import static com.amulyakhare.textdrawable.util.ColorGenerator.MATERIAL;
import static org.joda.time.LocalDateTime.parse;
import static org.joda.time.format.DateTimeFormat.shortDateTime;

/**
 * @author Filipe Bezerra
 */
public class MainActivity extends BaseActivity implements Drawer.OnDrawerItemClickListener {

    private static final int DRAWER_ITEM_DASHBOARD = 1;
    private static final int DRAWER_ITEM_ORDERS_REPORT = 2;
    private static final int DRAWER_ITEM_ORDERS = 3;
    private static final int DRAWER_ITEM_CUSTOMERS = 4;
    private static final int DRAWER_ITEM_PRODUCTS = 5;
    private static final int DRAWER_ITEM_AUTO_SYNC_ORDERS = 6;
    private static final int DRAWER_ITEM_SETTINGS = 7;
    private static final int DRAWER_ITEM_LAST_SYNC = 8;

    private Drawer drawer;

    private AccountHeader accountHeader;

    private PrimaryDrawerItem dashboardDrawerItem;

    private PrimaryDrawerItem ordersReportDrawerItem;

    private PrimaryDrawerItem ordersDrawerItem;

    private PrimaryDrawerItem customersDrawerItem;

    private PrimaryDrawerItem productsDrawerItem;

    private SecondarySwitchDrawerItem autoSyncOrdersDrawerItem;

    private SecondaryDrawerItem settingsDrawerItem;

    private SecondaryDrawerItem lastSyncDrawerItem;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private BottomSheetDialog bottomSheetDialog;

    private BottomSheetBehavior<View> bottomSheetBehavior;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_main;
    }

    @Override protected void onCreate(Bundle inState) {
        navigateToInitialFlowIfNeed();
        super.onCreate(inState);
        setAsHomeActivity();
        initBottomSheet();
        initDrawerHeader(inState);
        initDrawer(inState);
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

    @OnClick(R.id.fab_all_main_action) void onFabClicked() {
        bottomSheetBehavior.setState(STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    @Override public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (drawer != null) {
            outState = drawer.saveInstanceState(outState);
        }
        if (accountHeader != null) {
            outState = accountHeader.saveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override protected void onActivityResult(
            final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_INITIAL_FLOW: {
                if (resultCode == RESULT_OK) {
                    loadLoggedUser();
                } else {
                    finish();
                }
                break;
            }
            case REQUEST_SETTINGS: {
                if (resultCode == RESULT_AUTO_SYNC_ORDERS_CHANGED) {

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

    private void initBottomSheet() {
        final View bottomSheetView = getLayoutInflater().inflate(R.layout.view_bottom_sheet, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setOnDismissListener(dialog ->
                bottomSheetBehavior.setState(STATE_COLLAPSED));
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        findById(bottomSheetView, R.id.main_new_order)
                .setOnClickListener(v -> {
                    navigate().toAddOrder();
                    bottomSheetDialog.dismiss();
                });
        findById(bottomSheetView, R.id.main_new_customer)
                .setOnClickListener(v -> {
                    navigate().toAddCustomer();
                    bottomSheetDialog.dismiss();
                });
    }

    private void initDrawerHeader(final Bundle inState) {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.main_header_background)
                .withSavedInstance(inState)
                .withOnlyMainProfileImageVisible(true)
                .withCurrentProfileHiddenInList(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener((view, profile, current) -> {
                    EventTracker.action(ACTION_CHANGED_PROFILE);
                    changeLoggedUser(profile);
                    return false;
                })
                .build();
    }

    private void initDrawer(final Bundle inState) {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        createDashboardDrawerItem(),
                        createOrdersReportDrawerItem(),
                        createOrdersDrawerItem(),
                        createCustomersDrawerItem(),
                        createProductsDrawerItem(),
                        new DividerDrawerItem(),
                        createSettingsDrawerItem(),
                        createAutoSyncOrdersDrawerItem(),
                        createLastSyncDrawerItem()
                )
                .withSavedInstance(inState)
                .withShowDrawerOnFirstLaunch(true)
                .withOnDrawerItemClickListener(this)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override public void onDrawerOpened(final View drawerView) {
                        updateAutoSyncOrdersDrawerItem();
                        updateLastSyncDrawerItem();
                    }

                    @Override public void onDrawerClosed(final View drawerView) {}

                    @Override public void onDrawerSlide(final View drawerView,
                            final float slideOffset) {}
                })
                .withSelectedItem(DRAWER_ITEM_DASHBOARD)
                .withFireOnInitialOnClick(true)
                .build();
    }

    private PrimaryDrawerItem createDashboardDrawerItem() {
        if (dashboardDrawerItem == null) {
            dashboardDrawerItem = new PrimaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_DASHBOARD)
                    .withName(R.string.main_drawer_item_dashboard)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.main_drawer_home, getTheme()))
                    .withSelectedIconColorRes(R.color.color_primary);
        }
        return dashboardDrawerItem;
    }

    private PrimaryDrawerItem createOrdersReportDrawerItem() {
        if (ordersReportDrawerItem == null) {
            ordersReportDrawerItem = new PrimaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_ORDERS_REPORT)
                    .withName(R.string.main_drawer_item_orders_report)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.main_drawer_orders_report,
                                    getTheme()))
                    .withSelectedIconColorRes(R.color.color_primary);
        }
        return ordersReportDrawerItem;
    }

    private PrimaryDrawerItem createOrdersDrawerItem() {
        if (ordersDrawerItem == null) {
            ordersDrawerItem = new PrimaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_ORDERS)
                    .withName(R.string.main_drawer_item_orders)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.main_drawer_orders, getTheme()))
                    .withSelectedIconColorRes(R.color.color_primary);
        }
        return ordersDrawerItem;
    }

    private PrimaryDrawerItem createCustomersDrawerItem() {
        if (customersDrawerItem == null) {
            customersDrawerItem = new PrimaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_CUSTOMERS)
                    .withName(R.string.main_drawer_item_customers)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.main_drawer_customers, getTheme()))
                    .withSelectedIconColorRes(R.color.color_primary);
        }
        return customersDrawerItem;
    }

    private PrimaryDrawerItem createProductsDrawerItem() {
        if (productsDrawerItem == null) {
            productsDrawerItem = new PrimaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_PRODUCTS)
                    .withName(R.string.main_drawer_item_products)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.main_drawer_products, getTheme()))
                    .withSelectedIconColorRes(R.color.color_primary);
        }
        return productsDrawerItem;
    }

    private SecondaryDrawerItem createSettingsDrawerItem() {
        if (settingsDrawerItem == null) {
            settingsDrawerItem = new SecondaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_SETTINGS)
                    .withName(R.string.main_drawer_item_settings)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.main_drawer_settings, getTheme()))
                    .withSelectable(false);
        }
        return settingsDrawerItem;
    }

    private SecondarySwitchDrawerItem createAutoSyncOrdersDrawerItem() {
        if (autoSyncOrdersDrawerItem == null) {
            autoSyncOrdersDrawerItem = new SecondarySwitchDrawerItem()
                    .withIdentifier(DRAWER_ITEM_AUTO_SYNC_ORDERS)
                    .withName(R.string.main_drawer_item_auto_sync_orders)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.main_drawer_auto_sync, getTheme()))
                    .withSelectedIconColorRes(R.color.color_primary)
                    .withChecked(settings().getSettings().isAutomaticallySyncOrders())
                    .withSelectable(false)
                    .withOnCheckedChangeListener((drawerItem, buttonView, isChecked) ->
                            settings().setAutoSyncOrders(isChecked));
        }
        return autoSyncOrdersDrawerItem;
    }

    private SecondaryDrawerItem createLastSyncDrawerItem() {
        if (lastSyncDrawerItem == null) {
            lastSyncDrawerItem = new SecondaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_LAST_SYNC)
                    .withDescription(getLastSyncDrawerItemDescription())
                    .withSelectable(false);
        }
        return lastSyncDrawerItem;
    }

    private void updateLastSyncDrawerItem() {
        String lastSyncDrawerItemDescription = getLastSyncDrawerItemDescription();
        if (!lastSyncDrawerItem.getDescription().toString()
                .equals(lastSyncDrawerItemDescription)) {
            drawer.updateItem(lastSyncDrawerItem.withDescription(lastSyncDrawerItemDescription));
        }
    }

    private String getLastSyncDrawerItemDescription() {
        final String lastSyncTime = settings().getLastSyncTime();
        if (!isEmpty(lastSyncTime)) {
            return getString(R.string.main_drawer_item_desc_last_sync,
                    shortDateTime().print(parse(lastSyncTime)));
        } else {
            return "";
        }
    }

    private void updateAutoSyncOrdersDrawerItem() {
        boolean isEnabled = settings().getSettings().isAutomaticallySyncOrders();
        if (isEnabled != autoSyncOrdersDrawerItem.isChecked()) {
            drawer.updateItem(autoSyncOrdersDrawerItem.withChecked(isEnabled));
        }
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
            compositeSubscription.add(subscription);
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
        accountHeader.setProfiles(profiles);
        accountHeader.setActiveProfile(activeProfile);
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
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }
}
