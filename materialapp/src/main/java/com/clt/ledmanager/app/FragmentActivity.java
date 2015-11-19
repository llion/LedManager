package com.clt.ledmanager.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.clt.ledmanager.app.Fragment.DrawerFragment;
import com.clt.ledmanager.app.Fragment.TerminalControlFragment;
import com.clt.ledmanager.app.Fragment.SecondDrawerFragment;
import com.mikepenz.materialdrawer.app.R;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_fragment_dark_toolbar);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_fragment_linking);

        //ignore the DemoFragment and it's layout it's just to showcase the handle with an keyboard
        if (savedInstanceState == null) {
            Fragment f = DrawerFragment.newInstance("Demo");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case R.id.menu_1:
//                Fragment f = new MainFragment.newInstance("Demo");
                Fragment f = new TerminalControlFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                return true;
            case R.id.menu_2:
                Fragment f2 = SecondDrawerFragment.newInstance("Demo 2");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f2).commit();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
