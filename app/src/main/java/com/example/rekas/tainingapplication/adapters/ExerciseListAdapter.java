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
import com.example.rekas.tainingapplication.model.Exercise;

import java.util.ArrayList;

/**
 * Created by rekas on 20.05.2018.
 */

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {


    public ExerciseListAdapter(@NonNull Context context, ArrayList<Exercise> exercises) {
        super(context, R.layout.exercise_row, exercises);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater exerciseInflater = LayoutInflater.from(getContext());
        View exerciseView = exerciseInflater.inflate(R.layout.exercise_row, parent, false);

        Exercise singleTrainingItem = getItem(position);
        TextView exerciseName = (TextView) exerciseView.findViewById(R.id.exerciseName);
//        ImageView trainingImage = exerciseView.findViewById(R.id.trainingImage);
        TextView repeat = exerciseView.findViewById(R.id.repeat);
        TextView series = exerciseView.findViewById(R.id.series);
        TextView breakLength = (TextView) exerciseView.findViewById(R.id.breakLength);
//        TextView trainingDate = (TextView) exerciseView.findViewById(R.id.trainingDate);

        exerciseName.setText(singleTrainingItem.getName());
        repeat.setText(singleTrainingItem.getRepetitions());
        series.setText(singleTrainingItem.getSeries());
        breakLength.setText(singleTrainingItem.getDuration());
//        trainingImage.setImageResource(R.drawable.ic_fitness_center_black_24dp);
        return exerciseView;

    }
}
