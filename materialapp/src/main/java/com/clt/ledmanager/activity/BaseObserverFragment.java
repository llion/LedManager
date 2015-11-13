package com.clt.ledmanager.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clt.ledmanager.IService;
import com.clt.ledmanager.app.AdvancedActivity;
import com.clt.ledmanager.ui.DialogFactory;
import com.mikepenz.materialdrawer.app.R;

import java.util.Observable;
import java.util.Observer;

/**
 * BaseObserverFragment基类
 *
 */
public abstract class BaseObserverFragment extends Fragment implements Observer {
    Dialog enterPassDialog = null;
    protected IService mangerNetService;

    //	/**
//	 * 异步处理消息
//	 */
//	protected Handler nmHandler = new Handler()
//	{
//		public void handleMessage(android.os.Message msg)
//		{
//			switch (msg.what)
//			{
//				case Const.connectBreak:// 连接断开
//					// DialogUtil.createConnBreakDialog(BaseActivity.this).show();
//				break;
//				case NetMessageType.KickOutOf:// 被踢
//					Application app = (Application) (getActivity().getApplication());
//					app.setOnline(false);
//					DialogUtil.createKickOfDialog(getActivity()).show();
//				break;
//				case Const.connnectFail:// 连接失败
//					toast(getResources().getString(
//							R.string.fail_connect_to_server), 1000);
//				break;
//
//			}
//			dealHandlerMessage(msg);
//		}
//	};
    protected AdvancedActivity fragmentActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        添加观察者
        fragmentActivity = (AdvancedActivity) getActivity();
        fragmentActivity.getTerminateObservable().addObserver(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

//        删除观察者
        fragmentActivity.getTerminateObservable().deleteObserver(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected abstract void dealHandlerMessage(Message msg);

    /**
     * Toast显示信息
     *
     * @param msg  消息
     * @param time 时间
     */
    public void toast(String msg, int time) {
        Toast.makeText(getActivity(), msg, time).show();
    }

    /**
     * Activity之间的跳转
     *
     * @param activityClass
     */
    protected void skip(Class<?> activityClass) {

            Intent intent = new Intent();
            intent.setClass(getActivity(), activityClass);
            startActivity(intent);
    }

    /**
     * 获得字符串资源
     *
     * @param resId 资源id
     * @return
     */
    public String getResString(int resId) {
        return getResources().getString(resId);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 接收广播：心跳检测连接断开
    }

    /**
     * 显示密码输入框
     */
    protected void showEnterPassDialog(final String rightPassword,
                                       final OnPassDialogSubmitCallback callBack) {
        try {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.entry_password, null);
            final EditText etPass = (EditText) view
                    .findViewById(R.id.et_entry_password);
            Button btnSubmit = (Button) view.findViewById(R.id.btn_submit);
            Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

            btnSubmit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String entryPassword = etPass.getText().toString();
                    if (entryPassword.equals(rightPassword)) {
                        if (callBack != null) {
                            callBack.onSubmit();
                            if (enterPassDialog != null) {
                                enterPassDialog.dismiss();
                            }
                        }

                    }

                }
            });
            btnCancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (enterPassDialog != null) {
                        enterPassDialog.dismiss();
                    }

                }
            });

            enterPassDialog = DialogFactory.createDialog(getActivity(), view);
            enterPassDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * enterPassDialog的sumbit点击后回调
     */
    public interface OnPassDialogSubmitCallback {
        void onSubmit();
    }

    @Override
    public void update(Observable observable, Object data) {
        AdvancedActivity.MessageWrapper messageWrapper = (AdvancedActivity.MessageWrapper) data;
        switch (messageWrapper.type) {
            case AdvancedActivity.MessageWrapper.TYPE_SERVICE_INIT:
                mangerNetService = fragmentActivity.getMangerNetService();
                break;
            case AdvancedActivity.MessageWrapper.TYPE_SERVICE_UPDATE:
                dealHandlerMessage(messageWrapper.msg);
                break;
        }
    }
}
