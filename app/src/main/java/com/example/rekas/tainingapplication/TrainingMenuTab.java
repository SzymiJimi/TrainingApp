package com.example.rekas.tainingapplication;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rekas.tainingapplication.adapters.ExerciseListAdapter;
import com.example.rekas.tainingapplication.model.Exercise;
import com.example.rekas.tainingapplication.model.Training;
import com.example.rekas.tainingapplication.service.TrainingManager;
import com.j256.ormlite.stmt.query.In;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rekas on 14.04.2018.
 */

public class TrainingMenuTab extends Fragment {


    ListAdapter exerciseAdapter;
    ListView exercisesList;
    View rootView;
    Integer secondsToEndTraining = 0;
    Integer secondsToEndBreak[];
    Integer exerciseIterator = 0;
    Integer actualExerciseIndex = 0;

    Boolean countdownBreak = false;
    Integer numberOfExercises = 0;
    TextView actualExerciseName;
    TextView actualExerciseRepetitions;
    TextView actualExerciseSeries;
    TextView actualExerciseBreak;
    TextView actualTrainingName;

    ArrayList<Exercise> exerciseList;

    Vibrator vibrator;


    Button startBtn;
    Button endSeriesBtn;
    Button stopBtn;
    TextView txtTimer;
    TextView countdownTimer;
    TextView countdownText;
    Handler customHandler = new Handler();
    LinearLayout container;
    int secs, mins, milliseconds, countdownSec = 120, countdownMin = 2, tmpSec = -1, displaySec = 0;
    boolean workout = true;

    long startTime = 0L, timeInMiliseconds = 0L, timeSwapBuff = 0L, updateTime = 0L;

    Runnable updateTimeThread = new Runnable() {

        @Override
        public void run() {
            timeInMiliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMiliseconds;
            secs = (int) (updateTime / 1000);

            mins = secs / 60;
            secs %= 60;

            if (countdownBreak) {
                if (countdownSec == 10) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(300);
                    }
                }
                if (countdownSec == 9) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        vibrator.vibrate(300);
                    }
                }

                if (countdownSec == 0) {
                    countdownBreak = false;

                    NotificationCompat.Builder b = new NotificationCompat.Builder(getContext());
                    b.setAutoCancel(true)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
                            .setTicker("{your tiny message}")
                            .setContentTitle("Koniec przerwy")
                            .setContentText("Wróć rozpocznij następne ćwiczenie!")
                            .setContentInfo("INFO");

                    NotificationManager nm = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.notify(1, b.build());


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(1200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26                        vibrator.vibrate(1200);
                    }
                    exerciseIterator++;
                    if (exerciseIterator.equals(numberOfExercises)) {
                        workout = false;

                    } else {
                        countdownSec = secondsToEndBreak[exerciseIterator];
                        changeActualExercise();
                    }

                }

                if (tmpSec != secs) {
                    countdownSec = countdownSec - 1;
                    countdownMin = countdownSec / 60;
                    displaySec = countdownSec;
                    displaySec %= 60;
                    tmpSec = secs;
                }
                countdownTimer.setText(countdownMin + ":" + String.format("%2d", displaySec));
            }


            milliseconds = (int) (updateTime % 1000);

            txtTimer.setText("" + mins + ":" + String.format("%2d", secs) + ":"
                    + String.format("%3d", milliseconds));

            customHandler.postDelayed(this, 0);
        }
    };


    public void runCountDownBreak() {

        if (!workout) {
            customHandler.removeCallbacks(updateTimeThread);
            TrainingManager trainingManager = new TrainingManager();
            trainingManager.finishTraning(timeInMiliseconds, getContext());
        } else {
            countdownBreak = true;
        }
    }

    private void changeActualExercise() {
        if (actualExerciseIndex.equals(numberOfExercises)) {

        } else {
            Exercise actualExercise = Exercise.EXERCISES_LIST.get(actualExerciseIndex);
            actualExerciseIndex++;

            exerciseList = new ArrayList<>(Exercise.EXERCISES_LIST.subList(actualExerciseIndex, numberOfExercises));
            countdownMin = Integer.parseInt(actualExercise.getDuration().split(":")[0]);
            countdownSec = countdownMin * 60 + Integer.parseInt(actualExercise.getDuration().split(":")[1]);
            actualExerciseName.setText(actualExercise.getName());
            actualExerciseSeries.setText(actualExercise.getSeries());
            actualExerciseRepetitions.setText(actualExercise.getRepetitions());
            actualExerciseBreak.setText(actualExercise.getDuration());
            setListView();
        }

    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.training_menu, container, false);

        onPause();

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        actualTrainingName = rootView.findViewById(R.id.training_trainingName);
        actualExerciseName = rootView.findViewById(R.id.actualExercise);
        actualExerciseRepetitions = rootView.findViewById(R.id.repeatField);
        actualExerciseSeries = rootView.findViewById(R.id.seriesField);
        actualExerciseBreak = rootView.findViewById(R.id.breakTimeField);

        initializeExerciseList();

        startBtn = rootView.findViewById(R.id.startBtn);
        endSeriesBtn = rootView.findViewById(R.id.btnEndSeries);
        stopBtn = rootView.findViewById(R.id.stopBtn);
        txtTimer = rootView.findViewById(R.id.timerValue);
        container = rootView.findViewById(R.id.times);
        countdownTimer = rootView.findViewById(R.id.countdownTime);
        countdownText = rootView.findViewById(R.id.countdownText);

        startBtn.setOnClickListener((view) -> {
                    startTime = SystemClock.uptimeMillis();
                    displaySec = 0;
                    customHandler.postDelayed(updateTimeThread, 0);
                }
        );

        endSeriesBtn.setOnClickListener((view) ->
        {
            runCountDownBreak();

        });

        stopBtn.setOnClickListener(view -> {
            customHandler.removeCallbacks(updateTimeThread);
            TrainingManager trainingManager = new TrainingManager();
            trainingManager.finishTraning(timeInMiliseconds, getContext());
        });

        return rootView;
    }

    private void setListView() {
        exerciseAdapter = new ExerciseListAdapter(getContext(), exerciseList);
        exercisesList = (ListView) rootView.findViewById(R.id.nextExercisesList);
        exercisesList.setAdapter(exerciseAdapter);

        exercisesList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    String training = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(getContext(), training, Toast.LENGTH_LONG).show();
                }
        );
    }

    private void initializeExerciseList() {
        if (Exercise.EXERCISES_LIST != null) {
            numberOfExercises = Exercise.EXERCISES_LIST.size();
            secondsToEndBreak = new Integer[numberOfExercises];
            actualTrainingName.setText(Training.ACTUAL_TRAINING.getName());

            changeActualExercise();

            String duration;
            String splitedString[];
            Integer breaksIterator = 0;

            for (Exercise exercise : Exercise.EXERCISES_LIST) {
                duration = exercise.getDuration();
                splitedString = duration.split(":");
                secondsToEndBreak[breaksIterator] = Integer.parseInt(splitedString[0]) * 60 + Integer.parseInt(splitedString[1]);
                secondsToEndTraining = secondsToEndTraining + secondsToEndBreak[breaksIterator];
                breaksIterator++;
            }

            setListView();

        }


    }

}