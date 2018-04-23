package com.example.rekas.tainingapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rekas.tainingapplication.adapters.LastTrainingListAdapter;

import java.util.List;

/**
 * Created by rekas on 14.04.2018.
 */

public class StatsMenuTab extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stats_menu, container, false);
        String [] trainings = {"Barki", "Triceps & Plecy", "Nogi", "Klata", "Cardio"};
        ListAdapter trainingAdapter = new LastTrainingListAdapter(getContext(), trainings);
        ListView lastTrainingList = (ListView) rootView.findViewById(R.id.lastTrainingList);
        lastTrainingList.setAdapter(trainingAdapter);

        lastTrainingList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id)->{
                        String training = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(getActivity(), training, Toast.LENGTH_LONG).show();
                }
        );
        return rootView;
    }

}