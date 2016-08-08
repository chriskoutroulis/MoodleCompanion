package ais.koutroulis.gr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Chris on 08-Aug-16.
 */
public class ContentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            items.add("Testing the RecyclerView " + i);
        }

        View v = inflater.inflate(R.layout.fragment_list_view,container,false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerAdapter(items));
        return v;
    }
}
