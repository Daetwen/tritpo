package com.BaranovichiBus.barbus.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.BaranovichiBus.barbus.MainActivity;
import com.BaranovichiBus.barbus.R;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class AdapterBusStops extends RecyclerView.Adapter<AdapterBusStops.BusStopViewHolder> {

    private int numberItems;
    private int flag;
    private static int number;
    private static int lflag;
    private Context parent;

    public AdapterBusStops(int numberItems, int flag, Context parent){
        this.numberItems = numberItems;
        this.flag = flag;
        this.parent = parent;
    }

    @NonNull
    @Override
    public BusStopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIDforListItems = R.layout.busstopitem;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIDforListItems, parent,false);

        BusStopViewHolder busStopViewHolder = new BusStopViewHolder(view);
        return busStopViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusStopViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class BusStopViewHolder extends RecyclerView.ViewHolder {

        TextView listBusStopItem;

        public BusStopViewHolder(@NonNull View itemView) {
            super(itemView);
            listBusStopItem = itemView.findViewById(R.id.recyclerView_strings_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (flag == 0){
                        Intent intentBus = new Intent(parent, BusStopsActivity.class);
                        intentBus.putExtra(Intent.EXTRA_TITLE, MainActivity.Buses.get(position));
                        if ((position >= 9) && (position < 15)) lflag = 1;
                        else if (position >= 15 && (position < 20)) lflag = 2;
                        else if (position >= 20) lflag = 3;
                        else if (position >= 0 && position < 9) lflag = 0;
                        number = position;
                        intentBus.putExtra(Intent.EXTRA_INDEX, number*2 - lflag);
                        if (position != 9 && position != 15 && position != 20) {
                            intentBus.putExtra("EXTRA_COMPONENT_NAME", MainActivity.EndsArray.get(position * 2 - lflag));
                        }
                        intentBus.putExtra("EXTRA_COMPONENT_NAME2", MainActivity.EndsArray.get(position*2 + 1 - lflag));
                        intentBus.putExtra("FLAG", true);
                        startActivity(parent, intentBus,null);
                    }
                    else if(flag == 1){
                        Intent intentStop = new Intent(parent, BusStopsActivity.class);
                        intentStop.putExtra(Intent.EXTRA_TITLE, MainActivity.Stops.get(position));
                        intentStop.putExtra(Intent.EXTRA_INDEX, position);
                        number = position;
                        System.out.println("позиция = " + position);
                        intentStop.putExtra("EXTRA_COMPONENT_NAME", "Будние");
                        intentStop.putExtra("EXTRA_COMPONENT_NAME2", "Выходные");
                        intentStop.putExtra("FLAG", false);
                        startActivity(parent, intentStop,null);
                    }
                    else if(flag == 2){
                        Intent intentStopsLocal = new Intent(parent, InfoActivity.class);
                        intentStopsLocal.putExtra(Intent.EXTRA_TITLE, MainActivity.StopsArray.get(number*2 - lflag).get(position));
                        int lnumber = 0;
                        for (int j = 0; j < number*2 - lflag; j++){
                            lnumber += MainActivity.StopsArray.get(j).size();
                        }
                        intentStopsLocal.putExtra("EXTRA_INFO", MainActivity.TimesWeekdays.get(lnumber + position));
                        intentStopsLocal.putExtra("EXTRA_INFO2", MainActivity.TimesWeekends.get(lnumber + position));
                        startActivity(parent, intentStopsLocal,null);
                    }
                    else if(flag == 3){
                        Intent intentStopsLocal = new Intent(parent, InfoActivity.class);
                        intentStopsLocal.putExtra(Intent.EXTRA_TITLE, MainActivity.StopsArray.get(number*2 + 1 - lflag).get(position));
                        int lnumber = 0;
                        for (int j = 0; j < number*2 + 1 - lflag; j++){
                            lnumber += MainActivity.StopsArray.get(j).size();
                        }
                        intentStopsLocal.putExtra("EXTRA_INFO", MainActivity.TimesWeekdays.get(lnumber + position));
                        intentStopsLocal.putExtra("EXTRA_INFO2", MainActivity.TimesWeekends.get(lnumber + position));
                        startActivity(parent, intentStopsLocal,null);
                    }
                    else if(flag == 4){
                        Intent intentStopsLocal = new Intent(parent, WebActivity.class);
                        System.out.println("число + " + number);
                        intentStopsLocal.putExtra("REF", MainActivity.Urls.get(number*2).get(position));
                        startActivity(parent, intentStopsLocal,null);
                    }
                    else if(flag == 5){
                        Intent intentStopsLocal = new Intent(parent, WebActivity.class);
                        System.out.println("число + " + number);
                        intentStopsLocal.putExtra("REF", MainActivity.Urls.get(number*2 + 1).get(position));
                        startActivity(parent, intentStopsLocal,null);
                    }
                }
            });
        }

        void bind(int info) {
            if(flag == 0){
                listBusStopItem.setText(MainActivity.Buses.get(info));
            }
            else if (flag == 1){
                listBusStopItem.setText(MainActivity.Stops.get(info));
            }
            else if (flag == 2){
                listBusStopItem.setText(MainActivity.StopsArray.get(number*2 - lflag).get(info));
            }
            else if (flag == 3){
                listBusStopItem.setText(MainActivity.StopsArray.get(number*2 + 1 - lflag).get(info));
            }
            else if (flag == 4){
                List<String> locall = MainActivity.BusStops.get(number*2);
                listBusStopItem.setText(locall.get(info*3+1) + ") " + locall.get(info*3+2));
            }
            else if (flag == 5){
                List<String> locall = MainActivity.BusStops.get(number*2+1);
                listBusStopItem.setText(locall.get(info*3+1) + ") " + locall.get(info*3+2));
            }
        }
    }
}
