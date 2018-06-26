package com.example.rekas.tainingapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rekas.tainingapplication.R;
import com.example.rekas.tainingapplication.model.ExecutedTraining;

import java.util.ArrayList;

/**
 * Created by rekas on 23.04.2018.
 */

public class LastTrainingListAdapter extends ArrayAdapter<ExecutedTraining> {

    public LastTrainingListAdapter(@NonNull Context context) {
        super(context, R.layout.last_training_row , ExecutedTraining.EXECUTED_TRAININGS);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater trainingInflater = LayoutInflater.from(getContext());
        View lastTrainingView = trainingInflater.inflate(R.layout.last_training_row, parent, false);

        ExecutedTraining singleTrainingItem = getItem(position);
        TextView trainingName = (TextView) lastTrainingView.findViewById(R.id.trainingName);
        ImageView trainingImage = lastTrainingView.findViewById(R.id.trainingImage);
        TextView textTrainingDuration = lastTrainingView.findViewById(R.id.textTrainingDuration);
        TextView textDate = lastTrainingView.findViewById(R.id.textDate);
        TextView trainingDuration = (TextView) lastTrainingView.findViewById(R.id.trainingDuration);
        TextView trainingDate = (TextView) lastTrainingView.findViewById(R.id.trainingDate);

        Long timeInMiliseconds = Long.parseLong(singleTrainingItem.getDuration());
        Integer  secs = (int)(long) timeInMiliseconds/1000;
        Integer mins = secs/60;
        secs %=60;

        trainingName.setText(singleTrainingItem.getName());
        String duration = mins+":"+secs;
        trainingDuration.setText(duration);
        trainingDate.setText(singleTrainingItem.getDateOfExecution());
//        trainingImage.setImageResource(R.drawable.ic_fitness_center_black_24dp);
        return lastTrainingView;


    }
}
