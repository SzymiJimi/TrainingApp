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
import android.widget.TextView;
import android.widget.Toast;

import com.example.rekas.tainingapplication.adapters.LastTrainingListAdapter;
import com.example.rekas.tainingapplication.model.ExecutedTraining;
import com.example.rekas.tainingapplication.service.CustomDateManager;
import com.example.rekas.tainingapplication.service.ExecutedTrainingDbHandler;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rekas on 14.04.2018.
 */

public class StatsMenuTab extends Fragment {


    ArrayList<ExecutedTraining> trainingsInLat7days;

    TextView amountWeekTrainings;
    TextView amountWeekExercises;
    TextView weekTrainingTime;
    TextView avgWeekTrainingTime;
    TextView dayOfLongestTraining;
    TextView timeOfLongestTraining;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stats_menu, container, false);
        ExecutedTrainingDbHandler dbHandler = new ExecutedTrainingDbHandler(getContext(), null,null,1);
        ExecutedTraining.EXECUTED_TRAININGS= dbHandler.getExecutedTrainingList();
        ListAdapter trainingAdapter = new LastTrainingListAdapter(getContext());
        trainingsInLat7days= dbHandler.getLastWeekTraining();

        initializeTextViews(rootView);
        if(ExecutedTraining.EXECUTED_TRAININGS.size()>0)
        {
            setTextViews();
        }

//        String [] trainings = {"Barki", "Triceps & Plecy", "Nogi", "Klata", "Cardio"};

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


    private void initializeTextViews(View rootView){
        amountWeekTrainings = rootView.findViewById(R.id.stats_trainingsIn7);
        amountWeekExercises = rootView.findViewById(R.id.stats_exercisesIn7);
        weekTrainingTime = rootView.findViewById(R.id.stats_trainingTimeIn7);
        avgWeekTrainingTime = rootView.findViewById(R.id.stats_avgTrainingTimeIn7);
        dayOfLongestTraining = rootView.findViewById(R.id.stats_dayOfLngstTraining);
        timeOfLongestTraining = rootView.findViewById(R.id.stats_timeOfLngstTraining);
    }

    private void setTextViews(){
        String trainingNumber = Integer.toString(trainingsInLat7days.size()) ;
        amountWeekTrainings.setText(trainingNumber);
        amountWeekExercises.setText(getExercisesNumber().toString());
        weekTrainingTime.setText(getWeekTrainingTime());
        avgWeekTrainingTime.setText(getAvgWeekTrainingTime());
        ExecutedTraining longestTraining = getLongestTraining();
        dayOfLongestTraining.setText(getDayOfLongestTraining(longestTraining));
        timeOfLongestTraining.setText(getTimeOfLongestTraining(longestTraining));
    }

    private Integer getExercisesNumber(){
        Integer exercisesNumber=0;

        for (ExecutedTraining training: trainingsInLat7days) {
            exercisesNumber += training.getNumberOfExercises();
        }

        return exercisesNumber;
    }

    private String getWeekTrainingTime(){

        Long trainingsTime=0L;

        for (ExecutedTraining training: trainingsInLat7days) {
            trainingsTime += Long.parseLong(training.getDuration());
        }

        Integer  secs = (int)(long) trainingsTime/1000;
        Integer mins = secs/60;
        Integer hours = mins/60;
        mins %=60;
        secs %=60;
        return hours+"h "+mins+"min "+secs+"s";
    }

    private String getAvgWeekTrainingTime(){
        Long trainingsTime=0L;

        for (ExecutedTraining training: trainingsInLat7days) {
            trainingsTime += Long.parseLong(training.getDuration());
        }
        trainingsTime = trainingsTime/ trainingsInLat7days.size();

        Integer  secs = (int)(long) trainingsTime/1000;
        Integer mins = secs/60;
        Integer hours = mins/60;
        mins %=60;
        secs %=60;
        return hours+"h "+mins+"min "+secs+"s";
    }

    private ExecutedTraining getLongestTraining(){
        ExecutedTraining longestTraining = trainingsInLat7days.get(0);
        Long longestTime = Long.parseLong(longestTraining.getDuration());
        Long tmpTime = 0L;

        for (ExecutedTraining training : trainingsInLat7days) {
            tmpTime = Long.parseLong(training.getDuration());
            if (tmpTime > longestTime) {
                longestTraining = training;
                longestTime = tmpTime;
            }
        }
        return longestTraining;

    }

    private String getDayOfLongestTraining(ExecutedTraining longestTraining) {

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
//        String stringDayOfWeek;
        try {
            c.setTime(formatter.parse(longestTraining.getDateOfExecution()));
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            CustomDateManager dateManager = new CustomDateManager();
            String stringDayOfWeek = dateManager.dayOfWeekToString(dayOfWeek);
            return stringDayOfWeek;
        } catch (ParseException e) {

            e.printStackTrace();
            return "";
        }

    }

    private String getTimeOfLongestTraining(ExecutedTraining longestTraining){
        Long trainingsTime= Long.parseLong(longestTraining.getDuration());
        Integer  secs = (int)(long) trainingsTime/1000;
        Integer mins = secs/60;
        Integer hours = mins/60;
        mins %=60;
        secs %=60;
        return hours+"h "+mins+"min "+secs+"s";
    }

}