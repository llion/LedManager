package com.clt.ledmanager.app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.clt.ledmanager.IService;
import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.FragmentController;
import com.clt.ledmanager.app.DrawerItems.CustomPrimaryDrawerItem;
import com.clt.ledmanager.app.Fragment.ConnectRelationActivity;
import com.clt.ledmanager.app.Fragment.MainFragment;
import com.clt.ledmanager.app.Fragment.ReceiverCardFragment;
import com.clt.ledmanager.app.Fragment.SenderCardFragment;
import com.clt.ledmanager.app.Fragment.observable.TerminateObservable;
import com.clt.ledmanager.service.BaseService;
import com.clt.ledmanager.service.BaseServiceFactory;
import com.clt.ledmanager.util.Const;
import com.clt.ledmanager.util.DialogUtil;
import com.clt.netmessage.NetMessageType;
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
    private static final int ITEM_POSITION_SEND_CARD = 2;
    private static final int ITEM_POSITION_RECEIVE_CARD = 3;
    private static final int ITEM_POSITION_LINKING = 4;

    private static final String FRAGMENT_TAG_HOME = "home";
    private static final String FRAGMENT_TAG_SEND_CARD = "send_card";
    private static final String FRAGMENT_TAG_RECEIVE_CARD= "receive_card";
    private static final String FRAGMENT_TAG_LINKING = "linking";

    private FragmentController fragmentController;
    private BroadcastReceiver receiver;
    private TerminateObservable terminateObservable;
    private IService mangerNetService;// 通信服务

    public TerminateObservable getTerminateObservable() {
        return terminateObservable;
    }

    public IService getMangerNetService() {
        return mangerNetService;
    }

    public static class MessageWrapper{

        public final static int TYPE_SERVICE_INIT = 0;
        public final static int TYPE_SERVICE_UPDATE = 1;
        public Message msg;
        public int type;

        public MessageWrapper(int type,Message msg){
            this.type = type;
            this.msg = msg;
        }
    }

    /**
     * 异步处理消息
     */
    protected Handler nmHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case Const.connectBreak:// 连接断开
                    // DialogUtil.createConnBreakDialog(BaseActivity.this).show();
                    break;
                case NetMessageType.KickOutOf:// 被踢
                    Application app = (Application)getApplication();
                    app.setOnline(false);
                    DialogUtil.createKickOfDialog(AdvancedActivity.this).show();
                    break;
                case Const.connnectFail:// 连接失败
                    Toast.makeText(AdvancedActivity.this, getResources().getString(R.string.fail_connect_to_server), Toast.LENGTH_SHORT).show();
                    break;

            }
            terminateObservable.dealHandlerMessage(new MessageWrapper(MessageWrapper.TYPE_SERVICE_UPDATE,msg));
        }
    };


    /**
     * 绑定通信service
     */
    private ServiceConnection mangerNetServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            mangerNetService = null;

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            ((Application) getApplication()).mangerNetService = mangerNetService = ((BaseService.LocalBinder) service).getService();
                if (mangerNetService != null) {
                    terminateObservable.dealHandlerMessage(new MessageWrapper(MessageWrapper.TYPE_SERVICE_INIT,null));
                    mangerNetService.setNmHandler(nmHandler);
                }
        }
    };




    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.CONNECT_BREAK_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    /**
     * 广播接收器
     */
    class ConnectBreakBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(Const.CONNECT_BREAK_ACTION)) {
                nmHandler.obtainMessage(Const.connectBreak).sendToTarget();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sample);
        setContentView(R.layout.activity_sample_fragment_dark_toolbar);
        fragmentController = new FragmentController(this);

//      全局主题者
        Application.getInstance().terminateObservable = terminateObservable = new TerminateObservable();
        receiver = new ConnectBreakBroadcastReceiver();

        // 绑定mangerNetService
        Intent _intent1 = new Intent(this, BaseServiceFactory.getBaseService());
        bindService(_intent1, mangerNetServiceConnection, Context.BIND_AUTO_CREATE);

        // Handle Toolbar
        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_advanced_drawer);
        getSupportActionBar().setElevation(0);

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
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(R.drawable.btn_home_selector),
                        new CustomPrimaryDrawerItem().withName(R.string.drawer_item_send_card).withIcon(R.drawable.btn_sending_selector)
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_receive_card).withIcon(R.drawable.btn_receiving_selector),
//                        new CustomUrlPrimaryDrawerItem().withName(R.string.drawer_item_fragment_linking).withIcon(R.drawable.btn_linking_selector)
                ) // add the items we want to use with our Drawer
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        AdvancedActivity.this.finish();
                        return true;
                    }
                })

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            String tag = null;
                            switch (position) {
                                case ITEM_POSITION_HOME:

                                    getSupportActionBar().setTitle(R.string.drawer_item_home);

                                    tag = FRAGMENT_TAG_HOME;
                                    break;
                                case ITEM_POSITION_SEND_CARD:

                                    getSupportActionBar().setTitle(R.string.drawer_item_send_card);

                                    tag = FRAGMENT_TAG_SEND_CARD;
                                    invalidateOptionsMenu();
                                    break;

                                case ITEM_POSITION_RECEIVE_CARD:

                                    getSupportActionBar().setTitle(R.string.drawer_item_receive_card);
                                    tag = FRAGMENT_TAG_RECEIVE_CARD;
                                    break;
                                case ITEM_POSITION_LINKING:

                                    getSupportActionBar().setTitle(R.string.drawer_item_fragment_linking);
                                    tag = FRAGMENT_TAG_LINKING;
                                    break;
                            }
                            fragmentController.changeFragment(tag);

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
                .build();

//      添加fragment
        fragmentController.add(false, FRAGMENT_TAG_SEND_CARD, R.id.fragment_container, new SenderCardFragment());
        fragmentController.add(false,FRAGMENT_TAG_RECEIVE_CARD, R.id.fragment_container, new ReceiverCardFragment());
        fragmentController.add(false,FRAGMENT_TAG_LINKING, R.id.fragment_container, new ConnectRelationActivity());
        fragmentController.add(true,FRAGMENT_TAG_HOME, R.id.fragment_container, new MainFragment());

//        Fragment f = new MainFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();

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

    //添加Menu菜单
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
//                profile2.withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_android).backgroundColorRes(R.color.accent).sizeDp(48).paddingDp(4));
//                headerResult.updateProfileByIdentifier(profile2);
                startActivity(new Intent(AdvancedActivity.this, InfoActivity.class));
                return true;
//            case R.id.menu_2:
//                //show the arrow icon
//                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                return true;
//            case R.id.menu_3:
//                //show the hamburger icon
//                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
//                return true;
//            case R.id.menu_4:
//                //we want to replace our current header with a compact header
//                //build the new compact header
//                buildHeader(true, null);
//                //set the view to the result
//                result.setHeader(headerResult.getView());
//                //set the drawer to the header (so it will manage the profile list correctly)
//                headerResult.setDrawer(result);
//                return true;
//            case R.id.menu_5:
//                //we want to replace our current header with a normal header
//                //build the new compact header
//                buildHeader(false, null);
//                //set the view to the result
//                result.setHeader(headerResult.getView());
//                //set the drawer to the header (so it will manage the profile list correctly)
//                headerResult.setDrawer(result);
//                return true;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mangerNetServiceConnection);
    }
}
