package com.clt.ledmanager.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.clt.ledmanager.app.Fragment.SenderCardFragment;
import com.clt.ledmanager.app.Fragment.TerminalControlFragment;
import com.clt.ledmanager.app.Fragment.TerminalProgramFragment;
import com.clt.ledmanager.ui.PagerSlidingTabStrip;
import com.mikepenz.materialdrawer.app.R;

public class SelectListActivity extends BaseObservableActivity {

    private PagerSlidingTabStrip        mTabs;
    private ViewPager					mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list);


        // 处理 Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_select_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("" + (intent.getStringExtra("data")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initView();
        initData();
        initListener();


    }
//   对home icon返回
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

    private void initView() {

        mTabs = (PagerSlidingTabStrip) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
    }

    private void initData() {

//         设置adapter

        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));

        mTabs.setViewPager(mViewPager);
    }

    private void initListener(){

    }




class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] titles = { "终端控制", "发送卡", "终端节目" };
    TerminalControlFragment  tcFragment;
    SenderCardFragment  scFragment;
    TerminalProgramFragment tpFragment;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                tcFragment = new TerminalControlFragment();
                return tcFragment;
            case 1:
                scFragment = new SenderCardFragment();
                return scFragment;
            case 2:
                tpFragment = new TerminalProgramFragment();
                return tpFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        if (titles != null) {
            return titles.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
}

