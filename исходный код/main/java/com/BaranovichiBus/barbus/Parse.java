package com.BaranovichiBus.barbus;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Parse extends AsyncTask<Void, Void, Void> {
    private Context context;
    protected boolean flag = true;
    private List<String> lBuses = new ArrayList<>();
    private List<String> lLinks = new ArrayList<>();
    private List<String> lEndsArray = new ArrayList<>();
    private List<List<String>> lStopsArray = new ArrayList<>();
    private List<String> lEnds = new ArrayList<>();
    private List<String> lLinksStops = new ArrayList<>();
    private List<String> lLinksWeekdays = new ArrayList<>();
    private List<String> lLinksWeekends = new ArrayList<>();
    private List<String> lTimesWeekdays = new ArrayList<>();
    private List<String> lTimesWeekends = new ArrayList<>();
    private List<Integer> lFlags = new ArrayList<>();
    //Отвечают за часть с остановками
    private List<String> lStops = new ArrayList<>();
    private List<String> lRefs = new ArrayList<>();
    private List<List<String>> lBusStops = new ArrayList<>();
    private List<List<String>> lUrls = new ArrayList<>();

    private String urlNameMain = "https://zippybus.com/baranovichi";
    private String urlNameBuses = "https://zippybus.com/baranovichi/route/bus/";
    private String urlNameStop = "https://zippybus.com/baranovichi/stop";
    private String urlNameStops = "https://zippybus.com/baranovichi/stop/";
    private String attributeKeyTitle = "title";
    private String attributeKeyHref = "href";
    private String attributeKeyDiv = "div h4";
    private String attributeKeyListGroup = "div.list-group a";
    private String attributeKeyListGroupItem = "div a.list-group-item";
    private String attributeKeyClass6 = "col-sm-6";
    private String attributeKeyClass12 = "col-sm-12";
    private String attributeKey67 = "/67/";
    private String attributeKey12345 = "/12345/";
    private String attributeKeyContainer1 = "div.container ul.nav li a";
    private String attributeKeyContainer2 = "div.container table tbody tr ul.nav li a";
    private String attributeKeyMonday1 = "table[data-days=Monday] tr td";
    private String attributeKeyMonday2 = "table[data-days=Monday] tbody tr[data-url]";
    private String attributeKeySunday1 = "table[data-days=Sunday] tr td";
    private String attributeKeySunday2 = "table[data-days=Sunday] tbody tr[data-url]";
    private String attributeKeyDataUrl = "data-url";

    Parse (Context context) {
        this.context = context;
    }

    protected Void parseBuses() {
        try {
            Document doc = Jsoup.connect(urlNameMain).get();
            Elements links = doc.getAllElements();
            for (Element link : links) {
                String text = link.attr(attributeKeyTitle);
                if (text.contains("Автобус")) {
                    System.out.println(text);
                    lBuses.add(text);
                }
                String linkText = link.attr(attributeKeyHref);
                if (linkText.toLowerCase().contains(urlNameBuses)) {
                    lLinks.add(linkText);
                }
            }
        }
        catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return null;
    }

    private Void parseStopsAndEnds(int size) {
        try {
            List<String> titles2 = null;
            for (int i = 0; i < size; i++) {
                Document doc = Jsoup.connect(lLinks.get(i)).get();
                if (i != 9 && i != 15 && i != 20 ){
                    Elements links2 = doc.getElementsByClass(attributeKeyClass6);
                    for (int j = 0; j < links2.size(); j++) {
                        if (!links2.get(j).attr(attributeKeyHref).contains(attributeKey67)){
                            titles2 = (links2.get(j).select(attributeKeyListGroup).eachText());
                            lStopsArray.add(titles2);
                        }
                    }
                    links2 = doc.select(attributeKeyDiv);
                    for (int j = 0; j < links2.size(); j++) {
                        lEnds.add(links2.get(j).text());
                        lEndsArray.add(links2.get(j).text());
                    }
                    links2 = doc.select(attributeKeyListGroup);
                    for (int j = 0; j < links2.size(); j++) {
                        if (links2.get(j).attr(attributeKeyHref).contains(attributeKey67) && i != 19){
                            lLinksStops.add(links2.get(j).attr(attributeKeyHref).replace(attributeKey67, attributeKey12345));
                        }
                        else {
                            lLinksStops.add(links2.get(j).attr(attributeKeyHref));
                        }
                    }
                }
                else {
                    Elements links2 = doc.getElementsByClass(attributeKeyClass12);
                    if (!links2.get(2).attr(attributeKeyHref).contains(attributeKey67)){
                        titles2 = (links2.get(2).select(attributeKeyListGroup).eachText());
                        lStopsArray.add(titles2);
                    }
                    links2 = doc.select(attributeKeyDiv);
                    for (int j = 0; j < links2.size(); j++) {
                        lEnds.add(links2.get(j).text());
                        lEndsArray.add(links2.get(j).text());
                    }
                    links2 = doc.select(attributeKeyListGroup);
                    for (int j = 0; j < links2.size(); j++) {
                        if (links2.get(j).attr(attributeKeyHref).contains(attributeKey67)){
                            lLinksStops.add(links2.get(j).attr(attributeKeyHref).replace(attributeKey67, attributeKey12345));
                        }
                        else {
                            lLinksStops.add(links2.get(j).attr(attributeKeyHref));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return null;
    }

    private Void parseLinksDays(int size, List<String> linksCollction) {
        try{
            for (int j = 0; j < size; j++) {
                Document doc3 = Jsoup.connect(linksCollction.get(j)).get();
                Elements links3 = doc3.select(attributeKeyContainer1);
                boolean llFlag = false;
                for (int k = 0; k < links3.size(); k++) {
                    if (links3.get(k).text().contains("будни"))
                    {
                        llFlag = true;
                        lLinksWeekdays.add(links3.get(k).attr(attributeKeyHref));
                        lFlags.add(1);
                    }
                    else if (links3.get(k).text().contains("выходные"))
                    {
                        lLinksWeekends.add(links3.get(k).attr(attributeKeyHref));
                        if (llFlag == true) {
                            lFlags.set(lFlags.size() - 1, lFlags.get(lFlags.size()-1) + 2);
                        }
                        else if (llFlag == false){
                            lFlags.add(2);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return null;
    }

    private Void parseTimesDays (
            List<String> collectionWeekdays,
            List<String> collectionWeekends,
            List<Integer> collectionFlags) {
        try{
            Document doc;
            Elements links;
            int countWeekdays = 0;
            int countWeekends = 0;
            for (int i = 0; i < collectionFlags.size(); i++){
                if (collectionWeekdays.size() > 0) {
                    if (collectionFlags.get(i) == 1 || collectionFlags.get(i) == 3) {
                        doc = Jsoup.connect(collectionWeekdays.get(countWeekdays)).get();
                        links = doc.select(attributeKeyContainer2);
                        if (links.text().matches("(.*):[0-9]{3}")) {
                            lTimesWeekdays.add(links.text().replaceAll("1 |1$", " ").replaceAll(" ", ", "));
                            countWeekdays++;
                        } else {
                            lTimesWeekdays.add(links.text().replaceAll(" ", ", "));
                            countWeekdays++;
                        }
                    } else {
                        lTimesWeekdays.add("Не курсирует");
                    }
                }
                if (collectionWeekends.size() > 0) {
                    doc = Jsoup.connect(collectionWeekends.get(countWeekends)).get();
                    links = doc.select(attributeKeyContainer2);
                    if (collectionFlags.get(i) == 2 || collectionFlags.get(i) == 3){
                        if (links.text().matches("(.*):[0-9]{3}")){
                            lTimesWeekends.add(links.text().replaceAll("1 |1$"," ").replaceAll(" ", ", "));
                            countWeekends++;
                        }
                        else {
                            lTimesWeekends.add(links.text().replaceAll(" ", ", "));
                            countWeekends++;
                        }
                    }
                    else {
                        lTimesWeekends.add("Не курсирует");
                    }
                }
            }
        }
        catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return null;
    }

    private Void parseStops () {
        try{
            List<String> Stopss = null;
            Document doc = Jsoup.connect(urlNameStop).get();
            Stopss = doc.select(attributeKeyListGroupItem).eachText();
            lStops.addAll(Stopss);
        }
        catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return null;
    }

    private Void parseRefs () {
        try{
            Document doc = Jsoup.connect(urlNameStops).get();
            Elements refs = doc.getAllElements();
            for (Element ref: refs) {
                if (ref.attr(attributeKeyHref).contains(urlNameStops)){
                    lRefs.add(ref.attr(attributeKeyHref));
                }
            }
        }
        catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return null;
    }

    private Void parseBusStopsAndUrls (List<String> collectionRefs) {
        try{
            for (int i = 0; i < collectionRefs.size();i++) {
                Document doc6 = Jsoup.connect(collectionRefs.get(i)).get();
                List<String> busstop = null;
                busstop = doc6.select(attributeKeyMonday1).eachText();
                lBusStops.add(busstop);
                busstop = (doc6.select(attributeKeyMonday2).eachAttr(attributeKeyDataUrl));
                lUrls.add(busstop);
                busstop = doc6.select(attributeKeySunday1).eachText();
                lBusStops.add(busstop);
                busstop = (doc6.select(attributeKeySunday2).eachAttr(attributeKeyDataUrl));
                lUrls.add(busstop);
            }
        }
        catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return null;
    }

    public void saver1(String nameOfFile, List<String> collection){
        try {
            FileOutputStream fos = context.openFileOutput(nameOfFile, MODE_PRIVATE);
            for (String val : collection) {
                fos.write((val + "\r\n").getBytes());
            }
            fos.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error in saver1");
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
        }
        catch (IOException ex) {
            System.out.println("Error in saver1");
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
        }
        catch (Exception ex) {
            System.out.println("Error in saver1");
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public void saver2(String nameOfFile, List<List<String>> collection){
        try {
            FileOutputStream fos = context.openFileOutput(nameOfFile, MODE_PRIVATE);
            for (List<String> val : collection) {
                for (String str : val) {
                    fos.write((str + "\r\n").getBytes());
                }
                fos.write(("====\r\n").getBytes());
            }
            fos.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error in saver2");
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
        }
        catch (IOException ex) {
            System.out.println("Error in saver2");
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
        }
        catch (Exception ex) {
            System.out.println("Error in saver2");
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public void reader1(String nameOfFile, List<String> collection) {
        collection.clear();
        File file = new File(nameOfFile);
            try {
                FileInputStream fis = context.openFileInput(nameOfFile);
                InputStreamReader reader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("Line + " + line);
                    collection.add(line);
                }
            }
            catch (FileNotFoundException ex) {
                System.out.println("Error in reader1");
                ex.printStackTrace();
                System.out.println(ex.getLocalizedMessage());
                return;
            }
            catch (IOException ex) {
                System.out.println("Error in reader1");
                ex.printStackTrace();
                System.out.println(ex.getLocalizedMessage());
            }
            catch (Exception ex) {
                System.out.println("Error in reader1");
                ex.printStackTrace();
                System.out.println(ex.getLocalizedMessage());
            }
    }

    public void reader2(String nameOfFile, List<List<String>> collection) {
        collection.clear();
        List<String> workCollection = new ArrayList<>();
        File file = new File(nameOfFile);
            try {
                FileInputStream fis = context.openFileInput(nameOfFile);
                InputStreamReader reader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.contains("====")) {
                        workCollection.add(line);
                    }
                    else {
                        List<String> writeCollection = new ArrayList<>(workCollection);
                        System.out.println("writeCollection + " + writeCollection);
                        collection.add(writeCollection);
                        workCollection.clear();
                    }
                }
            }
            catch (FileNotFoundException ex) {
                System.out.println("Error in reader2");
                ex.printStackTrace();
                System.out.println(ex.getLocalizedMessage());
                return;
            }
            catch (IOException ex) {
                System.out.println("Error in reader2");
                ex.printStackTrace();
                System.out.println(ex.getLocalizedMessage());
            }
            catch (Exception ex) {
                System.out.println("Error in reader2");
                ex.printStackTrace();
                System.out.println(ex.getLocalizedMessage());
            }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast toast = Toast.makeText(context,
                "Процесс обновления начался.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        parseBuses();
        parseStopsAndEnds(lBuses.size());
        parseLinksDays(lLinksStops.size(), lLinksStops);
        parseTimesDays(lLinksWeekdays, lLinksWeekends, lFlags);
        //Часть,отвечающая за таб с остановками
        parseStops ();
        parseRefs ();
        parseBusStopsAndUrls (lRefs);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (flag == true) {

            saver1(MainActivity.sBuses, lBuses);
            saver1(MainActivity.sEndsArray, lEndsArray);
            saver2(MainActivity.sStopsArray, lStopsArray);
            saver1(MainActivity.sTimesWeekdays, lTimesWeekdays);
            saver1(MainActivity.sTimesWeekends, lTimesWeekends);
            saver1(MainActivity.sStops, lStops);
            saver2(MainActivity.sBusStops, lBusStops);
            saver2(MainActivity.sUrls, lUrls);

            reader1(MainActivity.sBuses, MainActivity.Buses);
            reader1(MainActivity.sEndsArray, MainActivity.EndsArray);
            reader2(MainActivity.sStopsArray, MainActivity.StopsArray);
            reader1(MainActivity.sTimesWeekdays, MainActivity.TimesWeekdays);
            reader1(MainActivity.sTimesWeekends, MainActivity.TimesWeekends);
            reader1(MainActivity.sStops, MainActivity.Stops);
            reader2(MainActivity.sBusStops, MainActivity.BusStops);
            reader2(MainActivity.sUrls, MainActivity.Urls);

            Toast toast = Toast.makeText(context,
                    "Расписание обновлено.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
        }
        else {
            Toast toast2 = Toast.makeText(context,
                    "Не удалось обновить расписания.", Toast.LENGTH_SHORT);
            toast2.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast2.show();
        }
    }
}
