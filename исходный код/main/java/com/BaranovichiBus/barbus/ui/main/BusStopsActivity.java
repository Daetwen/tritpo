package com.BaranovichiBus.barbus.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.BaranovichiBus.barbus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.BaranovichiBus.barbus.ui.main.SectionsPagerAdapter;

public class BusStopsActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus1);

        boolean flag = false;
        int position = 0;
        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("FLAG")) {
            flag = intentThatStartedThisActivity.getBooleanExtra("FLAG", false);
        }
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_INDEX)) {
            position = intentThatStartedThisActivity.getIntExtra(Intent.EXTRA_INDEX, 0);
        }
        System.out.println(position);
        SectionPagerAdapterBusStop sectionPagerAdapterBusStop = new SectionPagerAdapterBusStop(this, getSupportFragmentManager(), flag, position);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionPagerAdapterBusStop);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        textView = findViewById(R.id.titleBusStop);
        if(intentThatStartedThisActivity.hasExtra("EXTRA_COMPONENT_NAME")) {
            tabs.getTabAt(0).setText(intentThatStartedThisActivity.getStringExtra("EXTRA_COMPONENT_NAME"));
        }
        if(intentThatStartedThisActivity.hasExtra("EXTRA_COMPONENT_NAME2")) {
            tabs.getTabAt(1).setText(intentThatStartedThisActivity.getStringExtra("EXTRA_COMPONENT_NAME2"));
        }
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TITLE)){
            textView.setText(intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TITLE));
        }
    }
}