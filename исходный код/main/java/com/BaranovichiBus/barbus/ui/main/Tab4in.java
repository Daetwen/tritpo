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

public class Tab4in   extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterBusStop adapterBusStop;
    private Context parent;
    private boolean flag;
    private Integer position;

    public Tab4in(Context parent, boolean flag, Integer position){
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
            adapterBusStop = new AdapterBusStop(MainActivity.StopsArray.get(position).size(),2, parent);
        }
        else {
            adapterBusStop = new AdapterBusStop(MainActivity.Urls.get(position*2).size(),4, parent);
            System.out.println(position);
            System.out.println(MainActivity.Urls.get(position*2).size());
        }
        mRecyclerView.setAdapter(adapterBusStop);
        return root;
    }
}
