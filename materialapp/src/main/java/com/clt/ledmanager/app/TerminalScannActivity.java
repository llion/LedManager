package com.clt.ledmanager.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mikepenz.materialdrawer.app.R;

public class TerminalScannActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_scann);

        init();
    }

    public void init(){

//           调用扫描
        Intent scan = new Intent("com.color.android.SCAN");
        startActivity(scan);
    }

}
