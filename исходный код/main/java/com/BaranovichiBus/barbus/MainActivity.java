package com.BaranovichiBus.barbus;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.BaranovichiBus.barbus.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static List<String> Buses = new ArrayList<>();
    public static List<String> EndsArray = new ArrayList<>();
    public static List<List<String>> StopsArray = new ArrayList<>();
    public static List<String> TimesWeekdays = new ArrayList<>();
    public static List<String> TimesWeekends = new ArrayList<>();
    //Отвечают за часть с остановками
    public static List<String> Stops = new ArrayList<>();
    public static List<List<String>> BusStops = new ArrayList<>();
    public static List<List<String>> Urls = new ArrayList<>();

    public static String sBuses = "Buses.txt";
    public static String sEndsArray = "EndsArray.txt";
    public static String sStopsArray = "StopsArray.txt";
    public static String sTimesWeekdays = "TimesWeekdays.txt";
    public static String sTimesWeekends = "TimesWeekends.txt";
    public static String sStops = "Stops.txt";
    public static String sBusStops = "BusStops.txt";
    public static String sUrls = "Urls.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Parse parse = new Parse(getApplicationContext());
        parse.reader1(sBuses, MainActivity.Buses);
        parse.reader1(sEndsArray, MainActivity.EndsArray);
        parse.reader2(sStopsArray, MainActivity.StopsArray);
        parse.reader1(sTimesWeekdays, MainActivity.TimesWeekdays);
        parse.reader1(sTimesWeekends, MainActivity.TimesWeekends);
        parse.reader1(sStops, MainActivity.Stops);
        parse.reader2(sBusStops, MainActivity.BusStops);
        parse.reader2(sUrls, MainActivity.Urls);
    }

    public void reNew(View view){
        Parse parse = new Parse(getApplicationContext());
        parse.execute();
    }
}
