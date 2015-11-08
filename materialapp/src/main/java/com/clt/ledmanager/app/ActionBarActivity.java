package com.clt.ledmanager.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.HttpUtil;
import com.clt.ledmanager.ui.DialogProgressBar;
import com.clt.ledmanager.ui.TitleBarView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class ActionBarActivity extends AppCompatActivity {

    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_actionbar);
        setTitle(R.string.drawer_item_action_bar_drawer);

        // Handle Toolbar
        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(ActionBarActivity.this, ((Nameable) drawerItem).getName().getText(ActionBarActivity.this), Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                }).build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

	/**
     *	视频截图
     */
    public static class ScreenShotActivity extends BaseActivity
    {
        private ImageView ivImage;

        private TitleBarView titleBarView;

        private GetImageTask getImageTask;

        private Application app;

        private DialogProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
                setContentView(R.layout.screen_shot);
                init();
                initView();
                initListener();
                initData();



        }
        /**
         *
         */
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
            titleBarView.setTitleBarListener(new TitleBarView.TitleBarListener()
            {

                @Override
                public void onClickRightImg(View v)
                {
                    String ipAddress = app.ledTerminateInfo.getIpAddress();
                    String imageUrl = "http://" + ipAddress
                            + "/transmission/ftp/config/screenshot";
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
            String imageUrl = "http://" + ipAddress
                    + "/transmission/ftp/config/screenshot";
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
}
