package com.example.rekas.tainingapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rekas.tainingapplication.R;
import com.example.rekas.tainingapplication.model.Exercise;
import com.example.rekas.tainingapplication.model.Training;

import java.util.ArrayList;

/**
 * Created by rekas on 21.05.2018.
 */

public class TrainingAdapter extends ArrayAdapter<Training> {


    public TrainingAdapter(@NonNull Context context, ArrayList<Training> trainings) {
        super(context, R.layout.row_choose_training, trainings);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater exerciseInflater = LayoutInflater.from(parent.getContext());
        View trainingView = exerciseInflater.inflate(R.layout.row_choose_training, parent, false);
        Training singleTrainingItem = getItem(position);
        TextView trainingName = (TextView) trainingView.findViewById(R.id.rowCTrainingName);
        TextView trainingTime = trainingView.findViewById(R.id.rowCTrainingTime);
        TextView exerciseQuantity = trainingView.findViewById(R.id.rowCTrExerciseQuantity);

        trainingName.setText(singleTrainingItem.getName());
//        trainingTime.setText(singleTrainingItem.getRepetitions());
//        exerciseQuantity.setText(singleTrainingItem.getSeries());
//        trainingImage.setImageResource(R.drawable.ic_fitness_center_black_24dp);
        return trainingView;

    }

}
