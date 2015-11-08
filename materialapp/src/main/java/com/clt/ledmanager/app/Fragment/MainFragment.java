package com.clt.ledmanager.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.commondata.LedTerminateInfo;
import com.clt.commondata.SenderInfo;
import com.clt.commondata.SenderParameters;
import com.clt.commondata.SomeInfo;
import com.clt.entity.Program;
import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.BaseObserverFragment;
import com.clt.ledmanager.adapter.ChangeLanguageListener;
import com.clt.ledmanager.app.LedSelectActivity;
import com.clt.ledmanager.app.ScreenShotActivity;
import com.clt.ledmanager.app.UploadProgramAcitvity;
import com.clt.ledmanager.service.Clock;
import com.clt.ledmanager.ui.CustomerSpinner;
import com.clt.ledmanager.ui.DialogFactory;
import com.clt.ledmanager.ui.SwitchView;
import com.clt.ledmanager.ui.SwitchView.OnSwitchChangeListener;
import com.clt.ledmanager.upload.OnUploadListener;
import com.clt.ledmanager.upload.UploadManager;
import com.clt.ledmanager.upload.UploadProgram;
import com.clt.ledmanager.util.Const;
import com.clt.ledmanager.util.DialogUtil;
import com.clt.ledmanager.util.FileUtil;
import com.clt.ledmanager.util.NetUtil;
import com.clt.ledmanager.util.SendingCardFunctionHelper;
import com.clt.ledmanager.util.SharedPreferenceUtil;
import com.clt.ledmanager.util.SharedPreferenceUtil.ShareKey;
import com.clt.ledmanager.util.Tools;
import com.clt.netmessage.NMDeleteProgramAnswer;
import com.clt.netmessage.NMDetectSenderAnswer;
import com.clt.netmessage.NMGetProgramsNamesAnswer;
import com.clt.netmessage.NMGetSomeInfoAnswer;
import com.clt.netmessage.NMSaveBrightAndColorTempAnswer;
import com.clt.netmessage.NetMessageType;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * 首页 修改2014.6.5 caocong
 * http://developer.android.com/reference/android/app/Fragment.html#Lifecycle
 */
public class MainFragment extends BaseObserverFragment implements
        ChangeLanguageListener {
    private static final String TAG = "MainFragment";

    private Application app;


    private ArrayList<LedTerminateInfo> ledList = new ArrayList<LedTerminateInfo>();// 服务端列表

    private GestureDetector gestureDetector;// 手势

    private SharedPreferenceUtil sharedPreferenceUtil;// 偏好设置

    //private SenderInfo app.senderInfo;// 探测发送卡回传信息


    /**
     * 视图：查找服务端
     */
    private Button searchButton;// 查找服务端按钮

    private TextView tvTerminateConInfo;// 现在连接的终端显示信息

    private AlertDialog searchTerminateDialog;// 查找终端提示框

    private TextView tvConnTime;// 连接时长

    /**
     * 视图：亮度，色温
     */
    private SeekBar seekbarBright, seekbarColorTemp;// 亮度拖动块

    private TextView tvBrightValue, tvColorTempValue;// 亮度值,色温值

    private OnSeekBarChangeListener slBright, slColorTemp;// 亮度色温改变监听器

    private Button btnSaveBrightAndColorTemp;// 保存亮度、色温、全部保存

    /**
     * 视图：测试模式，屏幕开关,语言切换
     */
    private CustomerSpinner spinnerTestMode, spinnerOnOff, spinnerLanguage;// 下拉框

    private String[] arrTestMode, arrOnOff, arrLanguage;

    private OnCheckedChangeListener showOnOffListener;// 屏幕开关监听器

    private SwitchView switchView;// 显示和关闭开关

    private Button btnScreenShot;//屏幕截图

    /**
     * 视图：自动亮度调节
     */
    private CustomerSpinner spinnerAutoBright;

    private String[] arrAutoBright;

    /**
     * 视图：节目管理
     */
    private CustomerSpinner spinnerProgramManager;// 节目切换下来菜单

    private Button btnUploadProgram;// 上传节目按钮

    private TableRow trUploadProgress;// 上传进度视图容器

    private ProgressBar pbUploadProgress;// 上传进度条

    private TextView tvUploadProgress;// 上传进度文字

    private Dialog deleteProgramDialog = null;// 删除节目提示框

    private String[] arrProgramsNames;

    private int deleteProgramIndex;


    private BroadcastReceiver receiver;

    private static final int REQUST_CODE_SEARCH = 1001;


    private View trTestMode;
    private View btnEmpTestmode;
    private Drawer result;
    private LinearLayout llLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        initView(llLayout);
        initListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Replace LinearLayout by the type of the root element of the layout you're trying to load
        llLayout = (LinearLayout) inflater.inflate(R.layout.main, container, false);

        return llLayout; // We must return the loaded Layout

    }


    private void init() {

        try {
            app = (Application) getActivity().getApplication();
            // 手势
//             gestureDetector = new GestureDetector(this);

            // 判断网络是否连接
            if (!NetUtil.isWifiConnect(getActivity())) {
                Toast.makeText(getActivity(), getResources().getString(R.string.connect_network), Toast.LENGTH_SHORT).show();
            }

            // 偏好设置
            sharedPreferenceUtil = SharedPreferenceUtil.getInstance(fragmentActivity, null);
            // 查找提示框
            searchTerminateDialog = new DialogUtil().createDownloadDialog(fragmentActivity);

            // 连接时长
            receiver = new MyBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Const.CONNECT_TIME_ACTION);// 连接时长
            filter.addAction(Const.MODIF_TERM_NAME_ACTION);// 修改终端名
            fragmentActivity.registerReceiver(receiver, filter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentActivity.unregisterReceiver(receiver);
    }


    @Override
    public void onResume() {
        super.onResume();
        showConnectInfo(app.isOnline());

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    /**
     * 初始化视图
     *
     * @param layout
     */
    private void initView(LinearLayout layout) {

        try {
//           调用扫描
            Button scan = (Button) layout.findViewById(R.id.btn_scan_ap);
            scan.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent scan = new Intent("com.color.android.SCAN");
                    startActivity(scan);
                }
            });

            /** 服务端信息 **/
            tvTerminateConInfo = (TextView) layout.findViewById(R.id.tv_terminate_coninfo);
            tvConnTime = (TextView) layout.findViewById(R.id.tv_terminate_conntime);

            // 显示连接状态;
            showConnectInfo();
            searchTerminateDialog = new DialogUtil().createDownloadDialog(getActivity());
            searchButton = (Button) layout.findViewById(R.id.btnSearch);

            /** 亮度 ***/
            tvBrightValue = (TextView) layout.findViewById(R.id.tvBrightValue);
            seekbarBright = (SeekBar) layout.findViewById(R.id.sbBright);
            int defaultBright = sharedPreferenceUtil.getInt(
                    ShareKey.KEY_BRIGHT, 255);
            seekbarBright.setMax(255);
            seekbarBright.setProgress(defaultBright);
            tvBrightValue.setText(String.valueOf((int) (defaultBright / 255.0f * 100)) + "%");

            /** 色温 ***/
            tvColorTempValue = (TextView) layout.findViewById(R.id.tvColorTempValue);
            seekbarColorTemp = (SeekBar) layout.findViewById(R.id.sbColorTempHint);
            int defaultColorTemp = sharedPreferenceUtil.getInt(
                    ShareKey.KEY_COLOR_TEMP, 6500);
            seekbarColorTemp.setMax(80);
            seekbarColorTemp.setProgress((defaultColorTemp - 2000) / 100);
            Log.i("cc", defaultColorTemp + "");
            tvColorTempValue.setText(String.valueOf(defaultColorTemp) + "K");

            // 保存亮度和色温
            btnSaveBrightAndColorTemp = (Button) layout.findViewById(R.id.btn_save_bright_colortemp);


            /** 测试模式 ***/
            spinnerTestMode = (CustomerSpinner) layout.findViewById(R.id.spinner_test_mode_type);
            spinnerTestMode.initView(R.array.test_mode);
            spinnerTestMode.setSelection(0, true);
            /****** 自动亮度调节（C1S） ***********/
            spinnerAutoBright = (CustomerSpinner) layout.findViewById(R.id.spinner_auto_bright_adjust);
            spinnerAutoBright.initView(R.array.on_off);
            spinnerAutoBright.setSelection(0, true);
            /** 开关 ***/
            spinnerOnOff = (CustomerSpinner) layout.findViewById(R.id.spinner_on_off);
            spinnerOnOff.initView(R.array.on_off);
            spinnerOnOff.setSelection(0, true);

            /** 语言 ***/
            spinnerLanguage = (CustomerSpinner) layout.findViewById(R.id.spinner_change_language);
            spinnerLanguage.initView(R.array.language);
            int languageIndex = sharedPreferenceUtil.getInt(ShareKey.LANGUAGE,
                    0);
            spinnerLanguage.setSelection(languageIndex, true);


            /** 节目管理 **/
            spinnerProgramManager = (CustomerSpinner) layout.findViewById(R.id.spinner_program_manager);
            spinnerProgramManager.setText(getString(R.string.none));

//            节目上传
            btnUploadProgram = (Button) layout.findViewById(R.id.btn_upload_program);

            trUploadProgress = (TableRow) layout.findViewById(R.id.tr_upload_program);
            tvUploadProgress = (TextView) layout.findViewById(R.id.tv_upload_progress);
            pbUploadProgress = (ProgressBar) trUploadProgress
                    .findViewById(R.id.pb_upload_progress);

            /**
             * 屏幕截图
             */
            btnScreenShot = (Button) layout.findViewById(R.id.btn_screen_shot);


            trTestMode = layout.findViewById(R.id.tr_test_mode);
            btnEmpTestmode = layout.findViewById(R.id.btn_emp_tesmode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化监听器
     */
    private void initListener() {
        // 查找按钮
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //Home页查找button
                if (mangerNetService != null) {

                    Intent intent = new Intent(getActivity(), LedSelectActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

        /**
         * 亮度和色温保存
         */

        // 亮度
        seekbarBright.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            /**
             * 当用户开始滑动滑块时调用该方法
             */
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            /**
             * 当进度条发生变化时调用该方法
             */
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                if (mangerNetService == null)
                    return;

                mangerNetService.SetBrightness(seekbarBright.getProgress());

                int percent = (int) (seekbarBright.getProgress() / 255.0f * 100);
                tvBrightValue.setText(String.valueOf(percent) + "%");

                btnSaveBrightAndColorTemp.setText(getResources().getString(
                        R.string.save));
                btnSaveBrightAndColorTemp.setClickable(true);
                sharedPreferenceUtil.putInt(ShareKey.KEY_BRIGHT, seekbarBright.getProgress());

            }
        });


        // 色温
        seekbarColorTemp.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            /**
             * 当用户开始滑动滑块时调用该方法
             */
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * 当进度条发生变化时调用该方法
             */
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (mangerNetService == null)
                    return;

                int value = seekbarColorTemp.getProgress() * 100 + 2000;
                mangerNetService.SetColorTemp(value);
                tvColorTempValue.setText(String.valueOf(value) + "K");

                btnSaveBrightAndColorTemp.setText(getResources()
                        .getString(R.string.save));
                btnSaveBrightAndColorTemp.setClickable(true);

                sharedPreferenceUtil.putInt(ShareKey.KEY_COLOR_TEMP, value);
            }
        });
        /**
         * 保存亮度和色温
         */
        btnSaveBrightAndColorTemp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mangerNetService == null) {
                    return;
                }
                mangerNetService.saveBrightAndColorTemp(seekbarBright.getProgress(), seekbarColorTemp.getProgress() * 100 + 2000);

            }
        });
        // 测试模式
        spinnerTestMode.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    if (mangerNetService != null) {
                        mangerNetService.setTestMode(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        // 自动亮度调节
        spinnerAutoBright.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    if (mangerNetService != null) {
                        setAutoBright(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        // 开关屏幕
        spinnerOnOff.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    if (mangerNetService == null) {
                        return;
                    }
                    if (position == 0) {
                        mangerNetService.SetShowOnOff(true);
                    } else {
                        mangerNetService.SetShowOnOff(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        //开关屏
        spinnerOnOff.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (mangerNetService == null) {
                    return;
                }
                if (position == 0) {
                    mangerNetService.SetShowOnOff(true);
                } else {
                    mangerNetService.SetShowOnOff(false);
                }
            }
        });


        /**
         * 节目上传监听
         */
        btnUploadProgram.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mangerNetService == null || !mangerNetService.isConnecting()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.fail_connect_to_server), Toast.LENGTH_SHORT).show();
                    return;
                }

//              发送请求
                Intent intent = new Intent(getActivity(), UploadProgramAcitvity.class);
                startActivityForResult(intent, 0);

            }
        });
        /**
         * 节目切换
         */
        spinnerProgramManager.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (app.programs != null && app.programs.size() >= 1) {
                    mangerNetService.setPlayProgram(app.programs.get(position));
                }
            }

        });
        /**
         * 节目详情
         */
        spinnerProgramManager
                .setOnItemLongClickListener(new OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, final int position, long id) {

                        deleteProgramIndex = position;
                        final Program program = app.programs.get(position);
                        if (program == null) {
                            return false;
                        }

                        // Builder builder = new Builder(fragmentActivity);
                        // builder.setTitle(getString(R.string.delete_program)
                        // + app.programs.get(position).getFileName());
                        // builder.setMessage(getString(R.string.path) + ":"
                        // + app.programs.get(position).getPath());
                        // builder.setPositiveButton(
                        // getString(R.string.submit),
                        // new DialogInterface.OnClickListener()
                        // {
                        //
                        // @Override
                        // public void onClick(DialogInterface dialog,
                        // int which)
                        // {
                        // if (app.programs != null
                        // && app.programs.size() >= 1)
                        // {
                        // mangerNetService
                        // .deletePlayProgram(app.programs
                        // .get(position));
                        // }
                        // }
                        // });
                        // builder.setNegativeButton(
                        // getString(R.string.cancel),
                        // new DialogInterface.OnClickListener()
                        // {
                        //
                        // @Override
                        // public void onClick(DialogInterface dialog,
                        // int which)
                        // {
                        // if (deleteProgramDialog.isShowing())
                        // {
                        // deleteProgramDialog.dismiss();
                        // }
                        // }
                        //
                        // });
                        // deleteProgramDialog = builder.create();
                        // deleteProgramDialog.show();

                        LayoutInflater inflater = LayoutInflater.from(fragmentActivity);
                        View mView = inflater.inflate(R.layout.program_delete, null);
                        Button btnSubmit = (Button) mView.findViewById(R.id.btn_submit);
                        Button btnCancel = (Button) mView.findViewById(R.id.btn_cancel);
                        TextView tvName = (TextView) mView.findViewById(R.id.tv_program_name);
                        TextView tvSize = (TextView) mView.findViewById(R.id.tv_program_size);
                        TextView tvPath = (TextView) mView.findViewById(R.id.tv_program_path);
                        TextView tvFullPath = (TextView) mView.findViewById(R.id.tv_program_full_path);
                        tvSize.setText(Tools.byte2KbOrMb(program.getSize()) + "");

                        switch (program.getPathType()) {
                            case Program.UDISK:// U盘
                                tvPath.setText(getString(R.string.from_usb_disk));
                                break;
                            case Program.SDCARD:
                                tvPath.setText(getString(R.string.from_sd_card));
                                break;
                        }
                        tvName.setText(program.getFileName().substring(0, program.getFileName().lastIndexOf(".")));
                        tvFullPath.setText(program.getPath());

                        btnSubmit.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                if (program != null) {
                                    mangerNetService.deletePlayProgram(program);
                                }
                                if (deleteProgramDialog != null) {
                                    deleteProgramDialog.dismiss();
                                }
                            }
                        });

                        btnCancel.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (deleteProgramDialog != null) {
                                    deleteProgramDialog.dismiss();
                                }
                            }
                        });
                        deleteProgramDialog = DialogFactory.createDialog(fragmentActivity, mView);
                        deleteProgramDialog.show();
                        return true;
                    }
                });

        // 语言切换
        spinnerLanguage.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Resources resources = getResources();// 获得res资源对象
                Configuration config = resources.getConfiguration();// 获得设置对象
                DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨

                if (position == 0) {
                    Application.getInstance().xmlLanguage(Const.ZH_rCN);
                    config.locale = Locale.SIMPLIFIED_CHINESE;
                } else if (position == 1) {
                    Application.getInstance().xmlLanguage(Const.ZH_rTW);
                    config.locale = Locale.TRADITIONAL_CHINESE;
                } else if (position == 2) {
                    Application.getInstance().xmlLanguage(Const.EN);
                    config.locale = Locale.ENGLISH;
                }
                sharedPreferenceUtil.putInt(ShareKey.LANGUAGE, position);

                resources.updateConfiguration(config, dm);

                Intent i = fragmentActivity.getBaseContext().getPackageManager().getLaunchIntentForPackage(fragmentActivity.getPackageName());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Type", "changeLanguage");
                getActivity().startActivity(i);

                // getParent().finish();
                // Toast.makeText(fragmentActivity,getResources().getString(R.string.app_need_to_reboot),1000).show();

            }

        });


        //屏幕截图
        btnScreenShot.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScreenShotActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }


    /**
     * 设置视频亮度调节
     */
    protected void setAutoBright(int value) {

        if (app.senderInfo == null) {
            return;
        }

        //构建一个SenderParameters数据封装类
        SenderParameters params = new SenderParameters();
        params.setbBigPack(app.senderInfo.isBigPacket());
        params.setbAutoBright(value == 0 ? true : false);
        params.setM_frameRate(app.senderInfo.getFrameRate());
        params.setRealParamFlag(app.senderInfo.isRealParamFlags());
        params.setbZeroDelay(app.senderInfo.isBZeroDelay());
        params.setRgbBitsFlag(app.senderInfo.getTenBitFlag());
        params.setbHDCP(app.senderInfo.isBHDCP());
        params.setPorts(app.senderInfo.getPorts());
        params.setInputType(app.senderInfo.getInputType());
        mangerNetService.setBasicParams(params);
    }

    /**
     * 显示连接信息
     */
    public void showConnectInfo() {
        boolean bConnectted = false;

        if (mangerNetService != null) {
            bConnectted = mangerNetService.isConnecting();
        }
        if (app.ledTerminateInfo != null) {
            String strTerminateConInfo = app.ledTerminateInfo.getStrName();
            String ipAddress = app.ledTerminateInfo.getIpAddress();
            if (!TextUtils.isEmpty(ipAddress)) {
                strTerminateConInfo += "(" + ipAddress + ")";
            }

            if (bConnectted) {
                strTerminateConInfo += "(" + getResources().getString(R.string.online) + ")";
            } else {
                strTerminateConInfo += "(" + getResources().getString(R.string.offline) + ")";
            }

//          设置TextView 查找终端的服务器
            tvTerminateConInfo.setText(strTerminateConInfo);
        }
        // strTerminateConInfo = sharedPreferenceUtil.getString(
        // ShareKey.TerminateName,
        // getResources().getString(R.string.terminal_not_specified));
        // String ipAddress = sharedPreferenceUtil.getString(
        // ShareKey.TerminateAddress, "");
        // 设置与服务器的链接状态

    }

    /**
     * 显示连接信息
     */
    public void showConnectInfo(boolean bConnectted) {
        try {
            String strTerminateConInfo = null;
            String ipAddress = null;
            if (app.ledTerminateInfo == null) {

                strTerminateConInfo = getString(R.string.terminal_not_specified);

            } else {
                strTerminateConInfo = app.ledTerminateInfo.getStrName();
                ipAddress = app.ledTerminateInfo.getIpAddress();

                if (!TextUtils.isEmpty(ipAddress)) {
                    strTerminateConInfo += "(" + ipAddress + ")";
                }
                if (bConnectted) {
                    strTerminateConInfo += "(" + getResources().getString(R.string.online) + ")";
                } else {
                    strTerminateConInfo += "(" + getResources().getString(R.string.offline) + ")";
                }
            }

            // String strTerminateConInfo=app.ledTerminateInfo.getStrName();
            // String ipAddress=app.ledTerminateInfo.getIpAddress();
            // String strTerminateConInfo;
            // strTerminateConInfo = sharedPreferenceUtil.getString(
            // ShareKey.TerminateName,
            // getResources().getString(R.string.terminal_not_specified));
            // String ipAddress = sharedPreferenceUtil.getString(
            // ShareKey.TerminateAddress, "");

            tvTerminateConInfo.setText(strTerminateConInfo);
            // 设置与服务器的链接状态
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void onLedListSearchEnd(ArrayList<LedTerminateInfo> ledList) {
        try {
            if (ledList == null)
                return;

            this.ledList = ledList;
            Intent intent = new Intent(getActivity(), LedSelectActivity.class);
            intent.putExtra("ledList", ledList);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * onActivityResult处理选择上传文件
     *
     * @param data
     */
    private void handlerSelectedUploadProgram(Intent data) {
        try {
            String fileName = data.getStringExtra("FileName");

            // findViewById(R.id.tr_manager_program).setVisibility(View.GONE);
            // findViewById(R.id.tr_uploadprogram).setVisibility(View.VISIBLE);
            // btnUploadProgram.setVisibility(View.GONE);
            // btnContrlUpload.setVisibility(View.VISIBLE);
            // selectUploadProgramPath = data.getStringExtra("FilePath");
            // tvUploadProgress.setText(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * onActivityResult处理选择服务端
     *
     * @param data
     */
    private void handlerSelectedServer(Intent data) {

        app.ledTerminateInfo = (LedTerminateInfo) data.getExtras().get("terminateInfo");
        if (app.ledTerminateInfo != null) {

            if (mangerNetService != null) {
                mangerNetService.onSeverAddressChanged(app.ledTerminateInfo
                        .getIpAddress());
            }
        }
    }

    /**
     * 返回来的发送者
     *
     * @param
     */
    protected void onRcvSenderInfo(SenderInfo senderInfo) {
        try {
            if (app.senderInfo == null)
                return;

            this.app.senderInfo = app.senderInfo;

            if (seekbarBright != null) {
                seekbarBright.setOnSeekBarChangeListener(null);
                seekbarBright.setProgress(app.senderInfo.getRealTimeBright());
                seekbarBright.setOnSeekBarChangeListener(slBright);

                int percent = (int) (seekbarBright.getProgress() / 255.0f * 100);
                tvBrightValue.setText(String.valueOf(percent) + "%");
            }

            if (seekbarColorTemp != null) {
                seekbarColorTemp.setOnSeekBarChangeListener(null);
                int progress = app.senderInfo.getRealTimeClrTemp() - 2000;
                seekbarColorTemp.setProgress(progress);
                seekbarColorTemp.setOnSeekBarChangeListener(slColorTemp);

                tvColorTempValue.setText(String.valueOf(app.senderInfo
                        .getRealTimeClrTemp()) + "K");
            }

            if (switchView != null) {
                switchView.setOnSwitchChangeListener(null);
                switchView.setSwitchStatus(app.senderInfo.isbShowOn());
                switchView
                        .setOnSwitchChangeListener(new OnSwitchChangeListener() {

                            @Override
                            public void onSwitchChanged(boolean open) {
                                if (mangerNetService == null)
                                    return;
                                mangerNetService.SetShowOnOff(open);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理message
     */
    @Override
    protected void dealHandlerMessage(Message netMessage) {
        try {
            switch (netMessage.what) {
                case NetMessageType.ConnectSuccess:// 连接上服务器
                {
                    // app.clear();
                    app.setOnline(true);
                    showConnectInfo(true);
                    tvConnTime.setText(Tools.formatDuring(Clock.pastTime));
                    /**
                     * 如果已经连接上， 1.立即探测发送卡 2.返回节目列表
                     */
                    Toast.makeText(fragmentActivity,
                            getString(R.string.detect_sender_card), Toast.LENGTH_SHORT)
                            .show();
                    mangerNetService.DetectSender();
                    mangerNetService.getProgramsNames();
                    mangerNetService.getSomeInfo();
                }
                break;
                case NetMessageType.DetectSenderAnswer:// 探测发送卡结果
                {
                    String strMessage = (String) netMessage.obj;
                    Gson gson = new Gson();
                    NMDetectSenderAnswer nmDetectSenderAnswer = gson.fromJson(
                            strMessage, NMDetectSenderAnswer.class);
                    if (nmDetectSenderAnswer != null
                            && nmDetectSenderAnswer.getErrorCode() == 1) {
                        this.app.senderInfo = nmDetectSenderAnswer
                                .getSenderInfo();
                        updateView();
                        Toast.makeText(fragmentActivity,
                                getString(R.string.detect_card_success),
                                Toast.LENGTH_LONG).show();
                    } else if (nmDetectSenderAnswer == null || nmDetectSenderAnswer.getErrorCode() == 0) {
                        Toast.makeText(fragmentActivity, getString(R.string.detect_card_fail), Toast.LENGTH_LONG)
                                .show();
                    }
                }
                break;
                case Const.connectBreak:// 断网
                {
                    showConnectInfo(false);
                }
                break;
                case NetMessageType.KickOutOf:// 被踢
                {
                    showConnectInfo(false);

                }
                break;
                case NetMessageType.SaveBrightAndColorTempAnswer:// 保存色温亮度
                {
                    String message = (String) netMessage.obj;
                    Gson testModegson = new Gson();
                    NMSaveBrightAndColorTempAnswer answer = testModegson
                            .fromJson(message,
                                    NMSaveBrightAndColorTempAnswer.class);
                    if (answer.getErrorCode() == 0) {
                        Toast.makeText(fragmentActivity,
                                getResources().getString(R.string.save_fail),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(fragmentActivity, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();

                        // 让保存按钮变得不可点击
                        btnSaveBrightAndColorTemp.setClickable(false);
                        btnSaveBrightAndColorTemp.setText(getResources().getString(R.string.saved));
                    }
                }
                break;
                case NetMessageType.getProgramsNamesAnswer:// 获得节目信息
                {
                    String str = (String) netMessage.obj;
                    Gson gsons = new Gson();
                    NMGetProgramsNamesAnswer nmGetProgramsNamesAnswer = gsons
                            .fromJson(str, NMGetProgramsNamesAnswer.class);
                    app.programs = nmGetProgramsNamesAnswer.getProgramsNames();
                    updateProgreamsView();
                }
                break;
                case NetMessageType.deleteProgramAnswer:// 删除节目结果
                {
                    String reslut = (String) netMessage.obj;
                    Gson mGson = new Gson();
                    NMDeleteProgramAnswer nmDeleteProgramAnswer = mGson
                            .fromJson(reslut, NMDeleteProgramAnswer.class);
                    if (nmDeleteProgramAnswer.getErrorCode() == 1) {
                        app.programs.remove(deleteProgramIndex);
                        updateProgreamsView();
                        Toast.makeText(
                                fragmentActivity,
                                getResources().getString(
                                        R.string.delete_success), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(fragmentActivity,
                                getResources().getString(R.string.delete_fail),
                                Toast.LENGTH_SHORT).show();

                    }
                }
                break;
                case NetMessageType.GetSomeInfoAnswer: {
                    String reslut = (String) netMessage.obj;
                    Gson mGson = new Gson();
                    NMGetSomeInfoAnswer answer = mGson.fromJson(reslut,
                            NMGetSomeInfoAnswer.class);
                    if (answer.getErrorCode() == 1) {
                        SomeInfo someInfo = answer.getSomeInfo();
                        if (someInfo != null) {
                            app.someInfo = someInfo;
                        }
                    }
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取节目清单后更新
     */
    private void updateProgreamsView() {
        try {
            if (app.programs != null && !app.programs.isEmpty()) {
                int size = app.programs.size();
                arrProgramsNames = new String[size];
                Program program = null;
                for (int i = 0; i < size; i++) {
                    program = app.programs.get(i);
                    String fileName = program.getFileName();
                    String from = null;
                    if (program.getPathType() == Program.SDCARD) {
                        from = getString(R.string.from_sd_card);
                    } else if (program.getPathType() == Program.UDISK) {
                        from = getString(R.string.from_usb_disk);
                    } else if (program.getPathType() == Program.INTERNAL_STORAGE) {
                        from = getString(R.string.from_internal_storage);
                    }
                    arrProgramsNames[i] = getProgramName(fileName);
                }
                spinnerProgramManager.initView(arrProgramsNames);
                // spinnerProgramManager.setSelection(0, true);
                if (arrProgramsNames.length == 0) {
                    spinnerProgramManager.setText(getString(R.string.none));

                } else {
                    spinnerProgramManager
                            .setText(getString(R.string.please_select));

                }

            } else {
                spinnerProgramManager.setText(getString(R.string.none));
            }
        } catch (Exception e) {

        }

    }

    /**
     * 探测发送卡成功后更新UI
     */
    private void updateView() {
        try {
            if (app.senderInfo != null) {
                int features = SendingCardFunctionHelper
                        .getSupportedFeatures(app.senderInfo);
                /**
                 * 亮度色温
                 */
                seekbarBright.setProgress(app.senderInfo.getRealTimeBright());
                int percent = (int) (app.senderInfo.getRealTimeBright() / 255.0f * 100);
                tvBrightValue.setText(String.valueOf(percent) + "%");

                seekbarColorTemp.setProgress((app.senderInfo
                        .getRealTimeClrTemp() - 2000) / 100);
                tvColorTempValue.setText(String.valueOf(app.senderInfo
                        .getRealTimeClrTemp()) + "K");
                // 判断是否有测试模式功能
                if (SendingCardFunctionHelper.haveFeature(features,
                        SendingCardFunctionHelper.SCF_SET_TEST_MODE)) {
                    // 测试模式
                    trTestMode.setVisibility(View.VISIBLE);
                    spinnerTestMode.setSelection(app.senderInfo.getTestMode(),
                            true);
                    btnEmpTestmode.setVisibility(
                            View.INVISIBLE);

                } else {
                    trTestMode.setVisibility(View.GONE);
                    btnEmpTestmode.setVisibility(View.GONE);
                }
//				// 判断是否有自动亮度调节功能
//				if (SendingCardFunctionHelper.haveFeature(features,
//						SendingCardFunctionHelper.SCF_AUTO_BRIGHT))
//				{
//					findViewById(R.id.tr_auto_bright_adjust).setVisibility(
//							View.VISIBLE);
//					spinnerAutoBright.setSelection(
//							app.senderInfo.isAutoBright() ? 0 : 1, true);
//				}
//				else
//				{
//					findViewById(R.id.tr_auto_bright_adjust).setVisibility(
//							View.GONE);
//				}
                // 开关
                int onOffIndex = app.senderInfo.isbShowOn() ? 0 : 1;
                spinnerOnOff.setSelection(onOffIndex, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleActivityForResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 0:
//                if (resultCode == Activity.RESULT_OK) {
//                    String type = data.getStringExtra("type");
//                    LedTerminateInfo terminateInfo = (LedTerminateInfo) data.getSerializableExtra("terminateInfo");
//                    if (terminateInfo != null) {
//                        app.ledTerminateInfo = terminateInfo;
//                        showConnectInfo(true);
//                    }
//                }
//                break;
//        }
    }

    /**
     * 获得节目名称   如 new.vsn->new
     *
     * @param name
     * @return
     */
    private String getProgramName(String name) {
        if (!TextUtils.isEmpty(name)) {
            if (name.endsWith(".vsn") || name.endsWith(".VSN")) {
                return name.substring(0, name.lastIndexOf("."));
            }
        }
        return null;
    }

    /**
     * 选择服务端后回到这个页面
     */
    public void handleActivityForResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String type = data.getStringExtra("type");
            if (type.equalsIgnoreCase("selectServer")) {// 选择服务端
                handlerSelectedServer(data);
            } else if (type.equalsIgnoreCase("selectProgram")) {// 选择节目
                handlerSelectedUploadProgram(data);
            } else if (type.equalsIgnoreCase("uploadProgram")) {
                UploadProgram uploadProgram = (UploadProgram) data.getSerializableExtra("ProgramInfo");
                onUploadPic(uploadProgram);
            }

        }
    }

    /**
     * 上传节目
     *
     * @author Administrator
     */
    private class UploadAsyncTask extends AsyncTask<Object, Object, Object> {

        private UploadProgram uploadProgram;

        private static final int PROGRESS = 1;

        private boolean uploadSuccess;

        private long totalSize;

        // private long percentSize;

        private Program program;

        private long createTime;

        public UploadAsyncTask(UploadProgram uploadProgram) {
            uploadSuccess = false;
            this.uploadProgram = uploadProgram;
            this.totalSize = uploadProgram.getTotalSize();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trUploadProgress.setVisibility(View.VISIBLE);
            pbUploadProgress.setProgress(0);
            tvUploadProgress.setText("0");

        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            int flag = (Integer) values[0];
            if (flag == PROGRESS) {
                long precentSize = (Long) values[1];
                //long totalSize = (Long) values[2];
                pbUploadProgress
                        .setProgress((int) (precentSize * 100 / totalSize));
                String progressStr = Tools.byte2KbOrMb(precentSize) + "/"
                        + Tools.byte2KbOrMb(totalSize);
                tvUploadProgress.setText((int) (precentSize * 100 / totalSize)
                        + "%");


            }

        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            trUploadProgress.setVisibility(View.GONE);
            if (uploadSuccess) {
                Toast.makeText(fragmentActivity,
                        getString(R.string.upload_sucessful), Toast.LENGTH_LONG).show();


                if (program != null) {
                    app.programs.add(program);
                    updateProgreamsView();
                    spinnerProgramManager.setText(getProgramName(program.getFileName()));
                    mangerNetService.setPlayProgram(program);
                }

            } else {
                Toast.makeText(fragmentActivity,
                        getString(R.string.upload_fail), Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected Object doInBackground(Object... params) {
            UploadManager uploadManager = new UploadManager(uploadProgram);
            uploadSuccess = uploadManager.executeUpload(
                    app.ledTerminateInfo.getIpAddress(), new OnUploadListener() {
                        @Override
                        public void onUploadprogress(long percentSize,
                                                     long totalSize) {
                            publishProgress(new Object[]{PROGRESS,
                                    percentSize, totalSize});
                        }
                    });
            if (!uploadSuccess) {
                return null;
            }
            program = new Program();
            program.setFileName(uploadProgram.getVsnFileTask().getLocalFile()
                    .getName());
            program.setPath("/mnt/sdcard/Android/data/com.color.home/files/Ftp/program");
            program.setPathType(Program.SDCARD);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            program.setCreateTime(format.format(createTime));

            // /**
            // * 3.删除本地节目文件
            // */
            FileUtil.deleteLocalFile(uploadProgram.getVsnFileTask()
                    .getLocalFile());
            return null;
        }
    }

    /**
     * 上传节目
     */
    public void onUploadPic(UploadProgram uploadProgram) {
        if (mangerNetService == null || !mangerNetService.isConnecting()) {
            Toast.makeText(fragmentActivity,
                    getResources().getString(R.string.fail_connect_to_server),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (uploadProgram == null) {
            Toast.makeText(fragmentActivity,
                    getString(R.string.upload_fail), Toast.LENGTH_LONG).show();
            return;
        }
        UploadAsyncTask task = new UploadAsyncTask(uploadProgram);
        task.execute();
    }

    @Override
    public void OnChangeLanager() {

    }

    /**
     * 广播接收器
     *
     * @author Administrator
     */
    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(Const.CONNECT_TIME_ACTION)) {

                long time = intent.getLongExtra("connTime", 0);
                tvConnTime.setText(Tools.formatDuring(time));
            } else if (action.equalsIgnoreCase(Const.MODIF_TERM_NAME_ACTION)) {

                showConnectInfo(app.isOnline);
            }
        }
    }
}
