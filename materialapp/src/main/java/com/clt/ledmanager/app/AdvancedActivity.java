package com.clt.ledmanager.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.clt.ledmanager.app.DrawerItems.CustomPrimaryDrawerItem;
import com.clt.ledmanager.app.DrawerItems.CustomUrlPrimaryDrawerItem;
import com.clt.ledmanager.app.Fragment.ConnectRelationActivity;
import com.clt.ledmanager.app.Fragment.MainFragment;
import com.clt.ledmanager.app.Fragment.ReceiverCardFragment;
import com.clt.ledmanager.app.Fragment.SenderCardActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class AdvancedActivity extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private Drawer resultAppended = null;
    private IProfile profile;
    private IProfile profile2;
    private IProfile profile3;
    private IProfile profile4;
    private IProfile profile5;

//  Fragment切换标记
    private static final int ITEM_POSITION_HOME = 1;
    private static final int ITEM_POSITION_FREE = 2;
    private static final int ITEM_POSITION_CUSTOM = 3;
    private static final int ITEM_POSITION_DRAWER = 4;

    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sample);
        setContentView(R.layout.activity_sample_fragment_dark_toolbar);

        // Handle Toolbar
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_advanced_drawer);

        // Create a few sample profile
        profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile));
        profile2 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2)).withIdentifier(2);
        profile3 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile3));
        profile4 = new ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);
        profile5 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(R.drawable.btn_home_selector),
                        //here we use a customPrimaryDrawerItem we defined in our sample app
                        //this custom DrawerItem extends the PrimaryDrawerItem so it just overwrites some methods
                        new CustomPrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(R.drawable.btn_sending_selector),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(R.drawable.btn_receiving_selector),
                        new CustomUrlPrimaryDrawerItem().withName(R.string.drawer_item_fragment_drawer).withIcon(R.drawable.btn_linking_selector)
//                        new SectionDrawerItem().withName(R.string.drawer_item_section_header)
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cart_plus),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_database).withEnabled(false),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withSelectedIconColor(Color.RED).withIconTintingEnabled(true).withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Bullhorn"),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withEnabled(false)
                ) // add the items we want to use with our Drawer
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        AdvancedActivity.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })

//                抽屉固定窗口
//                .addStickyDrawerItems(
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github)
//                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Fragment f = null;
                            switch (position) {
                                case ITEM_POSITION_HOME:

                                    getSupportActionBar().setTitle(R.string.drawer_item_home);
                                    f = new MainFragment();
                                    break;
                                case ITEM_POSITION_FREE:

                                    getSupportActionBar().setTitle(R.string.drawer_item_free_play);
                                    f = new SenderCardActivity();
                                    break;

                                case ITEM_POSITION_CUSTOM:

                                    getSupportActionBar().setTitle(R.string.drawer_item_custom);
                                    f = new ReceiverCardFragment();
                                    break;
                                case ITEM_POSITION_DRAWER:

                                    getSupportActionBar().setTitle(R.string.drawer_item_fragment_drawer);
                                    f = new ConnectRelationActivity();
                                    break;
                            }
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();

                            //吐司提示
//                            if (drawerItem instanceof Nameable) {
//                                Toast.makeText(AdvancedActivity.this, ((Nameable) drawerItem).getName().getText(AdvancedActivity.this), Toast.LENGTH_SHORT).show();
//                            }

                            if (drawerItem instanceof Badgeable) {
                                Badgeable badgeable = (Badgeable) drawerItem;
                                if (badgeable.getBadge() != null) {
                                    //note don't do this if your badge contains a "+"
                                    //only use toString() if you set the test as String
                                    int badge = Integer.valueOf(badgeable.getBadge().toString());
                                    if (badge > 0) {
                                        badgeable.withBadge(String.valueOf(badge - 1));
                                        result.updateItem(drawerItem);
                                    }
                                }
                            }
                        }
                        return false;
                    }
                })
//                .withSavedInstance(savedInstanceState)
                .build();
        //now we add the second drawer on the other site.
        //use the .append method to add this drawer to the first one
//        resultAppended = new DrawerBuilder()
//                .withActivity(this)
//                .withFooter(R.layout.footer)
//                .withDisplayBelowStatusBar(true)
//                .withSavedInstance(savedInstanceState)
//                .addDrawerItems(
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye),
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home),
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad),
//                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
//                        new DividerDrawerItem(),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withEnabled(false),
//                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
//                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_bullhorn)
//                )
//                .withDrawerGravity(Gravity.END)
//                .append(result);
//
        Fragment f = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();

    }

    /**
     * small helper method to reuse the logic to build the AccountHeader
     * this will be used to replace the header of the drawer with a compact/normal header
     *
     * @param compact
     * @param savedInstanceState
     */
    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(//增加加head的数据
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }
    //初始化
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_1:
                //update the profile2 and set a new image.
                profile2.withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_android).backgroundColorRes(R.color.accent).sizeDp(48).paddingDp(4));
                headerResult.updateProfileByIdentifier(profile2);
                return true;
            case R.id.menu_2:
                //show the arrow icon
                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return true;
            case R.id.menu_3:
                //show the hamburger icon
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                return true;
            case R.id.menu_4:
                //we want to replace our current header with a compact header
                //build the new compact header
                buildHeader(true, null);
                //set the view to the result
                result.setHeader(headerResult.getView());
                //set the drawer to the header (so it will manage the profile list correctly)
                headerResult.setDrawer(result);
                return true;
            case R.id.menu_5:
                //we want to replace our current header with a normal header
                //build the new compact header
                buildHeader(false, null);
                //set the view to the result
                result.setHeader(headerResult.getView());
                //set the drawer to the header (so it will manage the profile list correctly)
                headerResult.setDrawer(result);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}
