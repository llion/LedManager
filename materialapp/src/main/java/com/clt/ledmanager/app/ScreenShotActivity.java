package com.clt.ledmanager.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.HttpUtil;
import com.clt.ledmanager.ui.DialogProgressBar;
import com.clt.ledmanager.ui.TitleBarView;
import com.clt.ledmanager.ui.TitleBarView.TitleBarListener;
import com.mikepenz.materialdrawer.app.R;

/**
 *	视频截图
 */
public class ScreenShotActivity extends BaseActivity
{
	private ImageView ivImage;
	
	private TitleBarView titleBarView;
	
	private GetImageTask getImageTask;
	
	private Application app;
	
	private DialogProgressBar progressBar;




	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		super.onCreate(savedInstanceState);

			setContentView(R.layout.screen_shot);
//			ActionBar actionBar = getActionBar();
//
////			actionBar.hide();
//			actionBar.show();


			init();
			initView();
			initListener();
			initData();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.screenshot_menu, menu);
//		return true;
//	}

	//  对home icon返回
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()){
			case android.R.id.home:
				finish();
				return true;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void init()
	{
		progressBar = new DialogProgressBar(this,null);
		app = (Application) getApplication();

	}
	
	private void initView()
	{
		titleBarView = (TitleBarView) findViewById(R.id.titlebar);
		ivImage = (ImageView) findViewById(R.id.iv_screen_shot);
	}
	/**
	 *
	 */
	private void initListener()
	{
		titleBarView.setTitleBarListener(new TitleBarListener()
		{
			
			@Override
			public void onClickRightImg(View v)
			{
				String ipAddress = app.ledTerminateInfo.getIpAddress();
				String imageUrl = "http://" + ipAddress + "/transmission/ftp/config/screenshot";
				getImageTask = new GetImageTask(imageUrl);
				getImageTask.execute();
			}
			
			@Override
			public void onClickLeftImg(View v)
			{
				finish();
			}
		});
	}
	/**
	 * 
	 */
	private void initData()
	{
		String ipAddress = app.ledTerminateInfo.getIpAddress();
		String imageUrl = "http://" + ipAddress + "/transmission/ftp/config/screenshot";
		getImageTask = new GetImageTask(imageUrl);
		getImageTask.execute();
	}
	
	/**
	 * 获得图片
	 */
	class GetImageTask extends AsyncTask<Object, Object, byte[]>
	{
		
		private String imageUrl;
		
		public GetImageTask(String imageUrl)
		{
			this.imageUrl = imageUrl;
		}
		
		protected void onPreExecute()
		{
			progressBar.show();
		}
		
		@Override
		protected void onPostExecute(byte[] result)
		{
			try
			{
				if (progressBar != null)
				{
					progressBar.dismiss();
				}
				if (result != null)
				{
					Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0,
							result.length);
					ivImage.setImageBitmap(bitmap);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		@Override
		protected byte[] doInBackground(Object... params)
		{
			if (TextUtils.isEmpty(imageUrl))
			{
				return null;
			}
			return HttpUtil.httpGetImage(imageUrl);
		}
		
	}
}
