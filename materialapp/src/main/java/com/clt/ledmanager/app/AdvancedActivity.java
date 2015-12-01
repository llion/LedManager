package com.clt.ledmanager.app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.clt.ledmanager.IService;
import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.FragmentController;
import com.clt.ledmanager.app.Fragment.EditProgramFragment;
import com.clt.ledmanager.app.Fragment.LanguageFragment;
import com.clt.ledmanager.app.Fragment.TerminalListFragment;
import com.clt.ledmanager.service.BaseService;
import com.clt.ledmanager.service.BaseServiceFactory;
import com.clt.ledmanager.util.Const;
import com.clt.ledmanager.util.DialogUtil;
import com.clt.ledmanager.util.NetUtil;
import com.clt.netmessage.NetMessageType;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class AdvancedActivity extends BaseObservableActivity {
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private Drawer resultAppended = null;

//    private IProfile profile;
//    private IProfile profile2;
//    private IProfile profile3;
//    private IProfile profile4;
//    private IProfile profile5;

    //  Fragment切换标记
    private static final int ITEM_POSITION_TERMINAL_LIST = 1;
//    private static final int ITEM_POSITION_TERMINAL_CONTROL = 2;
//    private static final int ITEM_POSITION_SEND_CARD = 3;
//    private static final int ITEM_POSITION_TERMINAL_PROGRAM = 4;
    private static final int ITEM_POSITION_EDIT_PROGRAM = 2;
    private static final int ITEM_POSITION_LANGUAGE = 4;


    private static final String FRAGMENT_TAG_TERMINAL_LIST = "terminal_list";
    private static final String FRAGMENT_TAG_TERMINAL_CONTROL = "terminal_control";
    private static final String FRAGMENT_TAG_SEND_CARD = "send_card";
    private static final String FRAGMENT_TAG_TERMINAL_PROGRAM = "terminal_program";
    private static final String FRAGMENT_TAG_EDIT_PROGRAM = "edit_program";
    private static final String FRAGMENT_TAG_LANGUAGE = "language";


    private FragmentController fragmentController;
    private BroadcastReceiver receiver;

    private static final boolean DBG = true;
    private static final String TAG = "AdvancedActivity";

    private IService mangerNetService;// 通信服务

    private AnimationDrawable tmAnimationDrawable;
    private ImageView imageView;

    public static class MessageWrapper {

        public final static int TYPE_SERVICE_INIT = 0;
        public final static int TYPE_SERVICE_UPDATE = 1;
        public final static int TYPE_FIND_TERMINAL = 2;
        public Message msg;
        public int type;

        public MessageWrapper(int type, Message msg) {
            this.type = type;
            this.msg = msg;
        }
    }

/**
     * 异步处理消息
     */

    protected Handler nmHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Const.connectBreak:// 连接断开
                    // DialogUtil.createConnBreakDialog(BaseActivity.this).show();
                    break;
                case NetMessageType.KickOutOf:// 被踢
                    Application app = (Application) getApplication();
                    app.setOnline(false);
                    DialogUtil.createKickOfDialog(AdvancedActivity.this).show();
                    break;
                case Const.connnectFail:// 连接失败
                    Toast.makeText(AdvancedActivity.this, getResources().getString(R.string.fail_connect_to_server), Toast.LENGTH_SHORT).show();
                    break;
            }
//            发送通知被观察者
            terminateObservable.dealHandlerMessage(new MessageWrapper(MessageWrapper.TYPE_SERVICE_UPDATE, msg));
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

            mangerNetService = ((Application) getApplication()).mangerNetService = ((BaseService.LocalBinder) service).getService();

            if (mangerNetService != null) {
                terminateObservable.dealHandlerMessage(new MessageWrapper(MessageWrapper.TYPE_SERVICE_INIT, null));
                mangerNetService.setNmHandler(nmHandler);

                if (!NetUtil.isConnnected(AdvancedActivity.this)) {
                    Toast toast = Toast.makeText(AdvancedActivity.this, R.string.network_not_connected, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                mangerNetService.searchTerminate();
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

//########################################## 查找终端图片轮播效果 ##########################################

        imageView = (ImageView)findViewById(R.id.terminal_seek);
        imageView.bringToFront();
        tmAnimationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.terminal_seacher);
        imageView.setBackgroundDrawable(tmAnimationDrawable);

        tmAnimationDrawable.start();

//      动画时间 调去每帧的时间 累加起来得到总的动画时间
        int duration = 0;
        for (int i = 0; i < tmAnimationDrawable .getNumberOfFrames(); i++) {
            duration += tmAnimationDrawable .getDuration(i);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tmAnimationDrawable.stop();
                tmAnimationDrawable = null;
                imageView.setVisibility(View.INVISIBLE);
            }
        }, duration + 3000);

//########################################## 查找终端图片轮播效果 ##########################################


        fragmentController = new FragmentController(this);
        receiver = new ConnectBreakBroadcastReceiver();

        // 绑定mangerNetService
        Intent _intent1 = new Intent(this, BaseServiceFactory.getBaseService());
        bindService(_intent1, mangerNetServiceConnection, Context.BIND_AUTO_CREATE);





        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("终端列表");

        // Create a few sample profile
//        profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile));
//        profile2 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2)).withIdentifier(2);
//        profile3 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile3));
//        profile4 = new ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);
//        profile5 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.terminal_list).withIcon(R.drawable.btn_receiving_selector),
//                        TODO 图片需要修改
//                        添加home页

//                        new PrimaryDrawerItem().withName("终端控制").withIcon(R.drawable.btn_home_selector),
//
//                        添加sending card页
//                        new CustomPrimaryDrawerItem().withName("发送卡").withIcon(R.drawable.btn_sending_selector),
//                        new SecondaryDrawerItem().withName("发送卡").withIcon(R.drawable.btn_sending_selector),
//
//                        添加节目上传
//                        new SecondaryDrawerItem().withName("终端节目").withIcon(R.drawable.btn_receiving_selector),

//                        添加节目管理
                        new PrimaryDrawerItem().withName("编辑节目").withIcon(R.drawable.btn_linking_selector),

//                        添加第二模块
                        new SectionDrawerItem().withName(R.string.drawer_item_action_settings),
                        new SecondaryDrawerItem().withName("语言选择").withIcon(FontAwesome.Icon.faw_cart_plus)
//                        new PrimaryDrawerItem().withName("语言选择").withIcon(FontAwesome.Icon.faw_cart_plus)

                )
                        // add the items we want to use with our Drawer
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
                                case ITEM_POSITION_TERMINAL_LIST:
                                    getSupportActionBar().setTitle("终端列表");
                                    tag = FRAGMENT_TAG_TERMINAL_LIST;
                                    break;

                               /* case ITEM_POSITION_TERMINAL_CONTROL:
                                    getSupportActionBar().setTitle("终端控制");
                                    tag = FRAGMENT_TAG_TERMINAL_CONTROL;
                                    break;

                                case ITEM_POSITION_SEND_CARD:
                                    getSupportActionBar().setTitle("发送卡");
                                    tag = FRAGMENT_TAG_SEND_CARD;
                                    break;

                                case ITEM_POSITION_TERMINAL_PROGRAM:
                                    getSupportActionBar().setTitle("终端节目");
                                    tag = FRAGMENT_TAG_TERMINAL_PROGRAM;
                                    break;*/

                                case ITEM_POSITION_EDIT_PROGRAM:
                                    getSupportActionBar().setTitle("编辑节目");
                                    tag = FRAGMENT_TAG_EDIT_PROGRAM;
                                    break;

                                case ITEM_POSITION_LANGUAGE:
                                    getSupportActionBar().setTitle("语言选择");
                                    tag = FRAGMENT_TAG_LANGUAGE;
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
        /*fragmentController.add(false, FRAGMENT_TAG_TERMINAL_CONTROL, R.id.fragment_container, new TerminalControlFragment());
        fragmentController.add(false, FRAGMENT_TAG_SEND_CARD, R.id.fragment_container, new SenderCardFragment());
        fragmentController.add(false, FRAGMENT_TAG_TERMINAL_PROGRAM, R.id.fragment_container, new TerminalProgramFragment());*/

        fragmentController.add(false, FRAGMENT_TAG_EDIT_PROGRAM, R.id.fragment_container, new EditProgramFragment());
        fragmentController.add(false, FRAGMENT_TAG_LANGUAGE, R.id.fragment_container, new LanguageFragment());
        fragmentController.add(true, FRAGMENT_TAG_TERMINAL_LIST, R.id.fragment_container, new TerminalListFragment());

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

//                  增加header头的用户
                /*.addProfiles(//增加加head的数据
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )*/

//                  header头的监听
              /*  .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
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
                })*/

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


    //    溢出菜单的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_terminal_info:

                startActivity(new Intent(this, InfoActivity.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mangerNetServiceConnection);
    }
}
