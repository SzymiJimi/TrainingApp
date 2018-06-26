package com.example.rekas.tainingapplication.service;

import android.content.Context;

import com.example.rekas.tainingapplication.model.ExecutedTraining;
import com.example.rekas.tainingapplication.model.Exercise;
import com.example.rekas.tainingapplication.model.Training;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rekas on 24.06.2018.
 */

public class TrainingManager {



    public void finishTraning(Long trainingTimeInMs, Context context){
        List<Exercise> trainingExercises =Exercise.EXERCISES_LIST;
        Training actualTraining = Training.ACTUAL_TRAINING;

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String date = format.format(new Date());

        ExecutedTrainingDbHandler executedTrainingDbHandler = new ExecutedTrainingDbHandler(context, null, null, 1);
        ExecutedTraining executedTraining = new ExecutedTraining(actualTraining.getName(), trainingExercises.size(), trainingTimeInMs.toString(),date);
        ExecutedTraining.EXECUTED_TRAININGS.add(executedTraining);
        executedTrainingDbHandler.addTraining(executedTraining);

    }

}
