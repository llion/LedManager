package com.clt.ledmanager.app;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.commondata.SomeInfo;
import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.service.Clock;
import com.clt.ledmanager.ui.DialogFactory;
import com.clt.ledmanager.ui.TitleBarView;
import com.clt.ledmanager.ui.TitleBarView.TitleBarListener;
import com.clt.ledmanager.util.Const;
import com.clt.ledmanager.util.Tools;
import com.clt.netmessage.NMChangeTermNameAnswer;
import com.clt.netmessage.NetMessageType;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.app.R;

import java.util.Observable;
import java.util.Observer;

/**
 * 查看信息
 */
public class InfoActivity extends BaseActivity implements Observer
{

	private TextView tvTermName, tvTermIp, tvTermState, tvTermDuring;
	
	private Button btnModifTermName;
	
	private ProgressBar pbSdcardRoom;
	
	private TextView tvSdcardRoom, tvLedManagerVersion, tvLedServerVersion,
			tvSenderVersion, tvColorLightVersion;
	
	private Dialog dialogTermName;

	private Application app;
	
	private BroadcastReceiver receiver;
	
	private String newTermName;
	
	// 标题栏视图
	private TitleBarView titleBarView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_info);
		init();
		initView();
		initListener();
		initData();

		
	}
	
	private void init()
	{
		app = (Application) getApplication();

//		全局主题者
		Application.getInstance().terminateObservable.addObserver(InfoActivity.this);

		// 连接时长
		receiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.CONNECT_TIME_ACTION);
		registerReceiver(receiver, filter);
	}
	
	private void initView()
	{
		titleBarView = (TitleBarView) findViewById(R.id.titlebar);
		
		tvTermName = (TextView) findViewById(R.id.tv_term_name);
		tvTermIp = (TextView) findViewById(R.id.tv_term_ip);
		tvTermState = (TextView) findViewById(R.id.tv_term_state);
		tvTermDuring = (TextView) findViewById(R.id.tv_term_during);
		btnModifTermName = (Button) findViewById(R.id.btn_modif_term_name);
		
		pbSdcardRoom = (ProgressBar) findViewById(R.id.pb_sdcard_room);
		tvSdcardRoom = (TextView) findViewById(R.id.tv_sdcard_room);
		
		tvLedManagerVersion = (TextView) findViewById(R.id.tv_version_ledmanager);
		tvLedServerVersion = (TextView) findViewById(R.id.tv_version_ledserver);
		tvSenderVersion = (TextView) findViewById(R.id.tv_version_sender);
		tvColorLightVersion = (TextView) findViewById(R.id.tv_version_img);
		
	}
	
	private void initListener()
	{
		// 标题监听
		titleBarView.setTitleBarListener(new TitleBarListener()
		{

//			右边title图片
			@Override
			public void onClickRightImg(View v)
			{
			}
			
			@Override
			public void onClickLeftImg(View v)
			{
				InfoActivity.this.finish();
			}
		});


		// 修改终端名
		btnModifTermName.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				LayoutInflater inflater = LayoutInflater
						.from(InfoActivity.this);
				View view = inflater.inflate(R.layout.modify_term_name, null);
				final TextView tvOldName = (TextView) view
						.findViewById(R.id.tv_term_name);
				final EditText etNewName = (EditText) view
						.findViewById(R.id.et_entry_name);
				final Button btnSubmit = (Button) view
						.findViewById(R.id.btn_submit);
				final Button btnCancel = (Button) view
						.findViewById(R.id.btn_cancel);
				if (app.ledTerminateInfo != null)
				{
					tvOldName.setText(app.ledTerminateInfo.getStrName());
				}
				
				btnCancel.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						if (dialogTermName != null)
						{
							dialogTermName.dismiss();
						}
					}
				});
				btnSubmit.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						newTermName = etNewName.getText().toString();
						if (TextUtils.isEmpty(newTermName))
						{
							etNewName.requestFocus();
							return;
						}
						if (((Application) getApplication()).mangerNetService != null)
						{
							((Application) getApplication()).mangerNetService.modifyTermName(newTermName);
						}
						if (dialogTermName != null)
						{
							dialogTermName.dismiss();
						}
					}
				});
				dialogTermName = DialogFactory.createDialog(InfoActivity.this,
						view);
				dialogTermName.show();

			}
		});
	}
	
    /*
	 * 获得版本号
	 * 
	 * @param context
	 * @return
	 * */

	public String getVerName(Context context)
	{
		String verName = null;
		try
		{
			verName = context.getPackageManager().getPackageInfo(
					"com.clt.ledmanager", 0).versionName;
		}
		catch (NameNotFoundException e)
		{
			Log.e("版本号获取异常", e.getMessage());
			e.printStackTrace();
		}
		
		return verName;
	}
	
	private void initData()
	{
		if (!TextUtils.isEmpty(getVerName(this)))
		{
			tvLedManagerVersion.setText(getVerName(this));
		}
		if (app.someInfo != null)
		{
			SomeInfo someInfo = app.someInfo;
			int progress = (int) ((someInfo.getSdTotalSize() - someInfo
					.getSdAviableSize()) * 100 / someInfo.getSdTotalSize());
			pbSdcardRoom.setProgress(progress);
			String sdcardRoom = getString(R.string.sdcard_room);
			String availableSize = Tools.byte2KbOrMb(someInfo
					.getSdAviableSize());
			String totalSize = Tools.byte2KbOrMb(someInfo.getSdTotalSize());
			tvSdcardRoom.setText(String.format(sdcardRoom, availableSize,
					totalSize));
			
			tvLedServerVersion.setText(someInfo.getLedServerVersion());
			
			tvColorLightVersion.setText(someInfo.getColorlightVersion());
		}
		
		if (app.ledTerminateInfo != null)
		{
			tvTermName.setText(app.ledTerminateInfo.getStrName());
			tvTermIp.setText("(" + app.ledTerminateInfo.getIpAddress() + ")");
			
			String state = null;
			if (app.isOnline)
			{
				state = getString(R.string.online);
			}
			else
			{
				state = getString(R.string.offline);
				
			}
			tvTermState.setText("(" + state + ")");
			
			tvTermDuring.setText(Tools.formatDuring(Clock.pastTime));
			
		}
		if (app.senderInfo != null)
		{
			
			tvSenderVersion.setText(app.senderInfo.getMajorVersion() + "."
					+ app.senderInfo.getMinorVersion());
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void update(Observable observable, Object data) {
		AdvancedActivity.MessageWrapper messageWrapper = (AdvancedActivity.MessageWrapper) data;
		switch (messageWrapper.type) {
			case AdvancedActivity.MessageWrapper.TYPE_SERVICE_INIT:

				break;
			case AdvancedActivity.MessageWrapper.TYPE_SERVICE_UPDATE:
				dealHandlerMessage(messageWrapper.msg);
				break;
		}
	}

    /*
	 * 广播接收器
	 * 
	 * @author Administrator
	 * */

	class MyBroadcastReceiver extends BroadcastReceiver
	{
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			if (action.equalsIgnoreCase(Const.CONNECT_TIME_ACTION))
			{
				long time = intent.getLongExtra("connTime", 0);
				tvTermDuring.setText(Tools.formatDuring(time));
			}
		}
	}
	
	@Override
	protected void dealHandlerMessage(Message netMessage)
	{
		switch (netMessage.what)
		{
			case NetMessageType.ModifyTerminateNameAnswer:// 修改终端名结果
			{
				
				String reslut = (String) netMessage.obj;
				Gson mGson = new Gson();
				NMChangeTermNameAnswer answer = mGson.fromJson(reslut,
						NMChangeTermNameAnswer.class);
				if (answer.getErrorCode() == 1)
				{
					Toast.makeText(InfoActivity.this,
							getResources().getString(R.string.setting_success),
							Toast.LENGTH_SHORT).show();
					app.ledTerminateInfo.setStrName(newTermName);
					tvTermName.setText(newTermName);
					
					sendBroadcast(new Intent(Const.MODIF_TERM_NAME_ACTION));
				}
				else
				{
					Toast.makeText(InfoActivity.this,
							getResources().getString(R.string.setting_fail),
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}
}
