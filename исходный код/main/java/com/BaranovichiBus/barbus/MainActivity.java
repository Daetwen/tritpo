package com.BaranovichiBus.barbus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;
import androidx.loader.content.AsyncTaskLoader;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.BaranovichiBus.barbus.ui.main.SectionsPagerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> Buses = new ArrayList<>();
    public static ArrayList<String> Links = new ArrayList<>();
    public static ArrayList<String> EndsArray = new ArrayList<>();
    public static ArrayList<List<String>> StopsArray = new ArrayList<>();
    public static ArrayList<String> Ends = new ArrayList<>();
    public static ArrayList<String> LinksStops = new ArrayList<>();
    public static ArrayList<String> LinksWeekdays = new ArrayList<>();
    public static ArrayList<String> LinksWeekends = new ArrayList<>();
    public static ArrayList<String> TimesWeekdays = new ArrayList<>();
    public static ArrayList<String> TimesWeekends = new ArrayList<>();
    public static ArrayList<Integer> Flags = new ArrayList<>();
    //Отвечают за часть с остановками
    public static ArrayList<String> Stops = new ArrayList<>();
    public static ArrayList<String> Refs = new ArrayList<>();
    public static ArrayList<List<String>> BusStops = new ArrayList<>();
    public static ArrayList<List<String>> Urls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    public void reNew(View view){
        Pars pars = new Pars();
        pars.execute();
    }

    class Pars extends AsyncTask<Void, Void, Void> {
        protected boolean flag = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Процесс обновления начался.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                List<String> Titles2 = null;
                ArrayList<String> Titles = new ArrayList<>();
                Document doc = Jsoup.connect("https://zippybus.com/baranovichi").get();
                Elements links = doc.getAllElements();
                for(Element link : links){
                    String text = link.attr("title");
                    if(text.toLowerCase().contains("автобус")) {
                        System.out.println(text);
                        Buses.add(text);
                    }
                    String linkText = link.attr("href");
                    if(linkText.toLowerCase().contains("https://zippybus.com/baranovichi/route/bus/")){
                        Links.add(linkText);
                    }
                }

                for (int i = 0; i < Buses.size(); i++) {
                    Document doc2 = Jsoup.connect(Links.get(i)).get();
                    System.out.println(doc2.baseUri());
                    if (i != 9 && i != 15 && i != 20 ){
                        Elements links2 = doc2.getElementsByClass("col-sm-6");
                        for (int j = 0; j < links2.size(); j++) {
                            if (!links2.get(j).attr("href").contains("/67/")){
                                Titles2 = (links2.get(j).select("div.list-group a").eachText());
                                StopsArray.add(Titles2);
                            }
                        }
                        links2 = doc2.select("div h4");
                        for (int j = 0; j < links2.size(); j++) {
                            Ends.add(links2.get(j).text());
                            EndsArray.add(links2.get(j).text());
                        }
                        links2 = doc2.select("div.list-group a");
                        for (int j = 0; j < links2.size(); j++) {
                            if (links2.get(j).attr("href").contains("/67/") && i != 19){
                                LinksStops.add(links2.get(j).attr("href").replace("/67/", "/12345/"));
                            }
                            else {
                                LinksStops.add(links2.get(j).attr("href"));
                            }
                        }
                    }
                    else {
                        Elements links2 = doc2.getElementsByClass("col-sm-12");
                        if (!links2.get(2).attr("href").contains("/67/")){
                            Titles2 = (links2.get(2).select("div.list-group a").eachText());
                            StopsArray.add(Titles2);
                        }
                        links2 = doc2.select("div h4");
                        for (int j = 0; j < links2.size(); j++) {
                            Ends.add(links2.get(j).text());
                            EndsArray.add(links2.get(j).text());
                        }
                        links2 = doc2.select("div.list-group a");
                        for (int j = 0; j < links2.size(); j++) {
                            if (links2.get(j).attr("href").contains("/67/")){
                                LinksStops.add(links2.get(j).attr("href").replace("/67/", "/12345/"));
                            }
                            else {
                                LinksStops.add(links2.get(j).attr("href"));
                            }
                        }
                    }
                }

                for (int j = 0; j < LinksStops.size(); j++) {
                    Document doc3 = Jsoup.connect(LinksStops.get(j)).get();
                    Elements links3 = doc3.select("div.container ul.nav li a");
                    boolean llFlag = false;
                    for (int k = 0; k < links3.size(); k++) {
                        if (links3.get(k).text().contains("будни"))
                        {
                            llFlag = true;
                            LinksWeekdays.add(links3.get(k).attr("href"));
                            Flags.add(1);
                        }
                        else if (links3.get(k).text().contains("выходные"))
                        {
                            LinksWeekends.add(links3.get(k).attr("href"));
                            if (llFlag == true) {
                                Flags.set(Flags.size() - 1, Flags.get(Flags.size()-1) + 2);
                            }
                            else if (llFlag == false){
                                Flags.add(2);
                            }
                        }
                    }
                }

                Document doc4;
                Elements links4;
                int countWeekdays = 0;
                int countWeekends = 0;
                for (int i = 0; i < Flags.size(); i++){
                    if (Flags.get(i) == 1 || Flags.get(i) == 3){
                        doc4 = Jsoup.connect(LinksWeekdays.get(countWeekdays)).get();
                        links4 = doc4.select("div.container table tbody tr ul.nav li a");
                        if (links4.text().matches("(.*):[0-9]{3}")) {
                            TimesWeekdays.add(links4.text().replaceAll("1 |1$"," "));
                            countWeekdays++;
                        }
                        else {
                            TimesWeekdays.add(links4.text());
                            countWeekdays++;
                        }
                    }
                    else {
                        TimesWeekdays.add("Не курсирует");
                    }
                    doc4 = Jsoup.connect(LinksWeekends.get(countWeekends)).get();
                    links4 = doc4.select("div.container table tbody tr ul.nav li a");
                    if (Flags.get(i) == 2 || Flags.get(i) == 3){
                        if (links4.text().matches("(.*):[0-9]{3}")){
                            TimesWeekends.add(links4.text().replaceAll("1 |1$"," "));
                            countWeekends++;
                        }
                        else {
                            TimesWeekends.add(links4.text());
                            countWeekends++;
                        }
                    }
                    else {
                        TimesWeekends.add("Не курсирует");
                    }
                }

                //Часть,отвечающая за таб с остановками, готова
                List<String> Stopss = null;
                Document doc5 = Jsoup.connect("https://zippybus.com/baranovichi/stop").get();
                Stopss = doc5.select("div a.list-group-item").eachText();
                Stops.addAll(Stopss);

                Elements refs5 = doc5.getAllElements();
                for (Element ref: refs5) {
                    if (ref.attr("href").contains("https://zippybus.com/baranovichi/stop/")){
                        Refs.add(ref.attr("href"));
                    }
                }
                for (int i = 0; i < Refs.size();i++){
                    Document doc6 = Jsoup.connect(Refs.get(i)).get();
                    List<String> busstop = null;
                    busstop = doc6.select("table[data-days=Monday] tr td").eachText();
                    BusStops.add(busstop);
                    busstop = (doc6.select("table[data-days=Monday] tbody tr[data-url]").eachAttr("data-url"));
                    Urls.add(busstop);
                    busstop = doc6.select("table[data-days=Sunday] tr td").eachText();
                    BusStops.add(busstop);
                    busstop = (doc6.select("table[data-days=Sunday] tbody tr[data-url]").eachAttr("data-url"));
                    Urls.add(busstop);
                }
            }
            catch (Exception e) {
                flag = false;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (flag == true) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Расписание обновлено.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }
            else {
                Toast toast2 = Toast.makeText(getApplicationContext(),
                        "Не удалось обновить расписания.", Toast.LENGTH_SHORT);
                toast2.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast2.show();
            }
        }
    }
}