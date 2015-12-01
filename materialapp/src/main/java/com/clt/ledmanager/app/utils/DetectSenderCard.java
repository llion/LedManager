package com.clt.ledmanager.app.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.clt.ledmanager.IService;
import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.app.BaseObservableActivity;
import com.clt.ledmanager.util.DialogUtil;
import com.mikepenz.materialdrawer.app.R;

public class DetectSenderCard extends BaseObservableActivity {

    private Application app;
    private IService mangerNetService;
    private Dialog detectWaittingDialog;// 探卡时的等待圆圈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_sender_card);


        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_detect_sender_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("探测发送卡");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        finish();
    }

    public void init(){

        mangerNetService = ((Application) getApplication()).mangerNetService;
        detectWaittingDialog = DialogUtil.createDownloadDialog(DetectSenderCard.this);
        if (mangerNetService != null) {
            mangerNetService.DetectSender();
            if (detectWaittingDialog != null) {
                detectWaittingDialog.show();
            }
        }
    }
}
