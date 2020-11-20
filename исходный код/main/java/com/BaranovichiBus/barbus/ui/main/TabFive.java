package com.BaranovichiBus.barbus.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.BaranovichiBus.barbus.MainActivity;
import com.BaranovichiBus.barbus.R;

public class TabFive extends Fragment {
    private RecyclerView mRecyclerView;
    private AdapterBusStops adapterBusStop;
    private Context parent;
    private boolean flag;
    private Integer position;

    public TabFive(Context parent, boolean flag, Integer position){
        this.parent = parent;
        this.flag = flag;
        this.position = position;
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab2sort_by_bus, container, false);
        mRecyclerView =(RecyclerView) root.findViewById(R.id.recyclerView_strings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (this.flag == true){
            adapterBusStop = new AdapterBusStops(MainActivity.StopsArray.get(position + 1).size(), 3, parent);
        }
        else {
            adapterBusStop = new AdapterBusStops(MainActivity.Urls.get(position*2 + 1).size(),5, parent);
        }
        mRecyclerView.setAdapter(adapterBusStop);
        return root;
    }
}
