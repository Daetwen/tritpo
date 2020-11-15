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

import java.util.ArrayList;

public class Tab2sort_by_bus  extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterBusStop adapterBusStop;
    private Context parent;

    public Tab2sort_by_bus(Context parent){
        this.parent = parent;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab2sort_by_bus, container, false);
        mRecyclerView =(RecyclerView) root.findViewById(R.id.recyclerView_strings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBusStop = new AdapterBusStop(MainActivity.Buses.size(),0, parent);
        mRecyclerView.setAdapter(adapterBusStop);
        return root;
    }
}

