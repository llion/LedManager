package com.clt.ledmanager.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.FragmentController;
import com.clt.ledmanager.adapter.SpinnerAdapter;
import com.clt.ledmanager.app.AdvancedActivity;
import com.clt.ledmanager.util.Const;
import com.clt.ledmanager.util.SharedPreferenceUtil;
import com.mikepenz.materialdrawer.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 上传节目
 */
public class LanguageFragment extends Fragment
{

    private LinearLayout view;
    private ListView language_listView;
    private SpinnerAdapter spinnerAdapter;
    public FragmentController fragmentController;

    private SharedPreferenceUtil sharedPreferenceUtil;// 偏好设置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragment_setting_language, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        initListener();

    }


    private void init() {


        fragmentController = new FragmentController(getActivity());
        language_listView = (ListView) view.findViewById(R.id.setting_language_list_view);
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getActivity(), null);

        List <String> data_list = new ArrayList<String>();
            data_list.add("简体中文");
            data_list.add("繁體中文");
            data_list.add("English");

        spinnerAdapter = new SpinnerAdapter (getActivity(), data_list);

        language_listView.setAdapter(spinnerAdapter);

    }


    /**
     * 语言切换
     */
    private void initListener(){

        language_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

                sharedPreferenceUtil.putInt(SharedPreferenceUtil.ShareKey.LANGUAGE, position);

//                更新资源配置
                resources.updateConfiguration(config, dm);

                Intent i = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getPackageName());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Type", "changeLanguage");
                getActivity().startActivity(i);

                fragmentController.changeFragment(AdvancedActivity.FRAGMENT_TAG_TERMINAL_LIST);
                //getActivity().finish();//finish掉自己
                Toast.makeText(getActivity(), "切换成功", Toast.LENGTH_SHORT).show();

            }
        });
    }
}