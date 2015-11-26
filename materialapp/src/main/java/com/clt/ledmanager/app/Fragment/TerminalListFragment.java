package com.clt.ledmanager.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.commondata.LedTerminateInfo;
import com.clt.commondata.LedTerminateInfoList;
import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.BaseObserverFragment;
import com.clt.ledmanager.adapter.LedSelectAdapter;
import com.clt.ledmanager.app.SelectListActivity;
import com.clt.ledmanager.app.TerminalScannActivity;
import com.clt.ledmanager.service.TCPFindTerminal;
import com.clt.ledmanager.service.TCPFindTerminal.OnCallBack;
import com.clt.ledmanager.ui.CustomerSpinner;
import com.clt.ledmanager.ui.DialogProgressBar;
import com.clt.ledmanager.util.Const;
import com.clt.ledmanager.util.NetUtil;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.app.R;

import java.util.ArrayList;

/**
 * 选择服务端 列表
 */
public class TerminalListFragment extends BaseObserverFragment {

    private View fragmentView;

    /**
     * 查找方式
     */
    private static final class Type {

        private static final int UDP_Broadcast = 0;

        private static final int TCP_Poll = 1;

    }

    //网络连接服务
//    protected IService mangerNetService ;
    //标题栏视图
    //列表
    private ListView listView;
    //适配器
    private LedSelectAdapter adapter;

    private TextView tvSearchNoResult;// 查找没有结果
    //led列表
    private ArrayList<LedTerminateInfo> ledList;
    //进度条
    private DialogProgressBar progressBar;

    private CustomerSpinner spinnerFindType;// 查找方式

    // private String terminateAddress;// 已经选中的终端ip
    //对话框
    private Dialog dialog = null;

    private Application app;
    //查找终端按钮
    private Button btnFindTerminal;

    private int findType = Type.UDP_Broadcast;

    private AsyncFindByTCP asyncFindByTCP;

    private AnimationDrawable tmAnimationDrawable;
    private ImageView imageView;


    //	private Handler nmHandler = new Handler()
//	{
    public void handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case Const.findTerminateResult:// 找到了
                    String strTerminateList = (String) msg.obj;
                    Gson gson = new Gson();
                    LedTerminateInfoList terminateInfoList = gson.fromJson(
                            strTerminateList, LedTerminateInfoList.class);
                    ledList = terminateInfoList.getTerminateList();
                    adapter.updateView(ledList);
                    app.setIp2TerminateMap(ledList);
                    // adapter.setChecked(terminateAddress, listView);
                    // if (ledList != null && !ledList.isEmpty())
                    // {
                    // progressBar.setVisibility(View.GONE);
                    // }
                    if (progressBar != null) {
                        progressBar.dismiss();
                    }

                    break;
                case Const.endFindTerminateResult:// 结束查找
                    if (ledList.isEmpty()) {
                        listView.setVisibility(View.GONE);
                        tvSearchNoResult.setVisibility(View.VISIBLE);
                        if (progressBar != null) {
                            progressBar.dismiss();
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//	};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.led_select, container, false);
        setHasOptionsMenu(true);
        return fragmentView;
    }

//    添加溢出菜单
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.fragment_terminallist, menu);
    }

    private static final int RESULT_SCANN =3001;
//    private static final int RESULT_OPERATION =3002;

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {

            case R.id.menu_scanning:

                Intent upload_program = new Intent(getActivity(),TerminalScannActivity.class);
                startActivityForResult(upload_program,RESULT_SCANN) ;
                break;

//            case R.id.menu_operation:
//
//                Intent save_program = new Intent(getActivity(),SaveProgramFragment.class);
//                startActivityForResult(save_program,RESULT_OPERATION) ;
//                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }










    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (Application)getActivity().getApplication();
        app.setSystemLanguage();
        init();
        initView();
        initListener();

    }

    @Override
    protected void dealHandlerMessage(Message msg) {
        handleMessage(msg);
    }


    /**
     * 初始化参数
     */
    private void init() {

//        查找终端图片轮播效果
//        imageView = (ImageView)fragmentView.findViewById(R.id.terminal_seek);
//
//        tmAnimationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.terminal_seacher);
//        imageView.setBackgroundDrawable(tmAnimationDrawable);
//
//        tmAnimationDrawable.start();
//
//
//        int duration = 0;//动画时间 调去每帧的时间 累加起来得到总的动画时间
//        for (int i = 0; i < tmAnimationDrawable .getNumberOfFrames(); i++) {
//            duration += tmAnimationDrawable .getDuration(i);
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tmAnimationDrawable .stop();
//                tmAnimationDrawable =null; //这句话为解决内存溢出的关键，开始没有添加
//                imageView.setVisibility(View.INVISIBLE);
//            }
//        }, duration+2000);










//        tmAnimationDrawable.getDuration(3);//停留的时间


//        new Handler() {
//            public void handleMessage(android.os.Message msg) {
//                if (msg.what == 1) {
//
//                    //干掉图像
//                    imageView.setVisibility(View.INVISIBLE);
//                }
//            };
//        }.sendEmptyMessageDelayed(1, 3000);//三秒




        if(ledList == null) {

            ledList = new ArrayList<>();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        try {
            tvSearchNoResult = (TextView) fragmentView.findViewById(R.id.tv_search_no_result);
            // progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
            progressBar = new DialogProgressBar(getActivity(), getString(R.string.search_server));
            progressBar.show();

            listView = (ListView) fragmentView.findViewById(R.id.lv_ledinfos);
            adapter = new LedSelectAdapter(getActivity(), ledList);
            String terminateAddress = null;

//			可封装
            if (app.ledTerminateInfo != null) {
                terminateAddress = app.ledTerminateInfo.getIpAddress();
            }
            adapter.setIpAddress(terminateAddress);
            listView.setAdapter(adapter);


            spinnerFindType = (CustomerSpinner) fragmentView.findViewById(R.id.spinner_search_type);
            spinnerFindType.initView(R.array.find_term_type);
            spinnerFindType.setSelection(0, true);

            btnFindTerminal = (Button) fragmentView.findViewById(R.id.btn_search_terminal);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //标题
        btnFindTerminal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                findTerminal();
            }
        });

        //查找方式
        spinnerFindType.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    findType = Type.UDP_Broadcast;
                } else {
                    findType = Type.TCP_Poll;
                }
            }
        });


        //查找客户端
        btnFindTerminal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                findTerminal();
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0,final View arg1, final int position, long arg3) {
                // 查看密码是否已经输入过
                final String ip = adapter.getItem(position).getIpAddress();
                Application.Terminate terminate = app.getIp2TerminateMap().get(ip);

                final Intent intent = new Intent(getActivity(), SelectListActivity.class);
                String desc = ledList.get(position).getStrName() + "(" + ledList.get(position).getIpAddress() + ")";
                intent.putExtra("data",desc);
                if (terminate.isHasEnteredPass()) {
                    updateImageViewSelector(arg1);
                    adapter.setIpAddress(ip);
                    handlerSelectedServer(adapter.getItem(position));
                    startActivity(intent);
                    return;
                }


//                输入密码
                LayoutInflater password_inflater = LayoutInflater.from(getActivity());
                final View password_view = password_inflater.inflate(R.layout.password, null);

               dialog =new AlertDialog.Builder(getActivity()).setTitle("亲亲,要输入密码哦")
                       .setIcon(android.R.drawable.btn_star)
                       .setView(password_view)
                       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                终端的设定密码和目前密码
                                String currentPassword = adapter.getItem(position).getPassword();
                                EditText password = (EditText) password_view.findViewById(R.id.entry_password);
                                String entryPassword = password.getText().toString();//获取输入的密码

                                if (entryPassword.equals(currentPassword)) {
                                    Toast toast = Toast.makeText(getActivity(), " 亲亲,密码正确哦", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    adapter.setIpAddress(ip);
                                    updateImageViewSelector(arg1);
                                    handlerSelectedServer(adapter.getItem(position));

                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }
                                    startActivity(intent);


                                    app.getIp2TerminateMap().get(ip)
                                            .setHasEnteredPass(true);

                                } else {
                                    Toast toast = Toast.makeText(getActivity(), " 亲亲,密码不正确哦", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    new EditText(getActivity()).setText("");
                                    new EditText(getActivity()).setSelection(0);
                                }
                            }
                       })
                       .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 取消
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();

            }
        });
    }

    private void updateImageViewSelector(View arg1) {
        for (int i = 0; i < listView.getChildCount(); i++) {
            View childAt = listView.getChildAt(i);
            LedSelectAdapter.Holder holder = (LedSelectAdapter.Holder) childAt.getTag();
            if (childAt == arg1) {
                holder.ivSelected.setImageResource(R.drawable.right);
            } else {
                holder.ivSelected.setImageResource(R.color.transparent);
            }

        }
    }

    /**
     * onActivityResult处理选择服务端
     *
     * @param
     */
    private void handlerSelectedServer(LedTerminateInfo ledTerminateInfo) {
        app.ledTerminateInfo = ledTerminateInfo;
        if (app.ledTerminateInfo != null) {
            if (mangerNetService != null) {
                mangerNetService.onSeverAddressChanged(app.ledTerminateInfo.getIpAddress());
            }
        }
    }


    /**
     * 查找服务端
     */
    private void findTerminal() {

        if (mangerNetService == null) {
            return;
        }

        // 网络没连接
        if (!NetUtil.isConnnected(getActivity())) {
            toast(getResString(R.string.network_not_connected), 1000);
            if (progressBar != null) {
                progressBar.dismiss();
            }
            return;
        }
        adapter.clearData();
        progressBar.show();
        switch (findType) {
            case Type.UDP_Broadcast: {

                findTerinalsByUdp();
            }

            break;

            case Type.TCP_Poll: {
                findTerminalsByTcp();
            }

            break;
        }
    }

    /**
     * TCP方式查找服务端
     */
    private void findTerminalsByTcp() {
        asyncFindByTCP = new AsyncFindByTCP();
        asyncFindByTCP.execute();
    }

    /**
     * UDP方式查找服务端
     */
    private void findTerinalsByUdp() {
        mangerNetService.searchTerminate();
    }


    /**
     * TCP轮询查找
     */
    class AsyncFindByTCP extends AsyncTask<Object, Object, Object> {

        private static final int Update_ProgressBar = 1;

        private static final int Update_FindTerm = 2;

        private static final int Update_Done = 3;

        private TCPFindTerminal tcpFindTerminal;

        private ArrayList<LedTerminateInfo> mLedlists;

        public AsyncFindByTCP() {
            tcpFindTerminal = new TCPFindTerminal(getActivity(), 9043);
            mLedlists = new ArrayList<>();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            int type = (Integer) (values[0]);
            switch (type) {
                case Update_ProgressBar: {

                }
                break;

                case Update_FindTerm: {
                    adapter.updateView(mLedlists);
                    if (progressBar != null) {
                        progressBar.dismiss();
                    }
                }
                break;
                case Update_Done: {
                    if (mLedlists.isEmpty()) {
                        listView.setVisibility(View.GONE);
                        tvSearchNoResult.setVisibility(View.VISIBLE);
                        if (progressBar != null) {
                            progressBar.dismiss();
                        }
                    }
                }
                break;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
        }

        @Override
        protected Object doInBackground(Object... params) {
            if (mangerNetService == null) {
                return null;
            }
            mangerNetService.searchTerminateByTcpLoop(new OnCallBack() {

                @Override
                public void onFindSucess(LedTerminateInfo ledTerminateInfo) {
                    try {
                        LedTerminateInfo ledTerInfo = new LedTerminateInfo();
                        ledTerInfo.setIpAddress(ledTerminateInfo.getIpAddress());
                        ledTerInfo.setPassword(ledTerminateInfo.getPassword());
                        ledTerInfo.setStrName(ledTerminateInfo.getStrName());
                        mLedlists.add(ledTerInfo);
                        app.setIp2TerminateMap(mLedlists);
                        publishProgress(Update_FindTerm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFindDone() {
                    publishProgress(Update_Done);
                }

            });
            return null;
        }

    }
}
