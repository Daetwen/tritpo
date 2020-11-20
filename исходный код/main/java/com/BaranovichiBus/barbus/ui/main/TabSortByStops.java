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

public class TabSortByStops extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterBusStops adapterBusStop;
    private Context parent;

    public TabSortByStops(Context parent){
        this.parent = parent;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab3sort_by_stops, container, false);
        mRecyclerView =(RecyclerView) root.findViewById(R.id.recyclerView_strings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBusStop = new AdapterBusStops(MainActivity.Stops.size(),1, parent);
        mRecyclerView.setAdapter(adapterBusStop);
        return root;
    }
}
