package com.example.rekas.tainingapplication;

/**
 * Created by rekas on 14.04.2018.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rekas.tainingapplication.adapters.ExerciseListAdapter;
import com.example.rekas.tainingapplication.adapters.TrainingAdapter;
import com.example.rekas.tainingapplication.model.ExecutedTraining;
import com.example.rekas.tainingapplication.model.Exercise;
import com.example.rekas.tainingapplication.model.Training;
import com.example.rekas.tainingapplication.service.ExecutedTrainingDbHandler;
import com.example.rekas.tainingapplication.service.MyExerciseDBHandler;
import com.example.rekas.tainingapplication.service.TrainingDBHandler;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainMenuTab extends Fragment{


    View rootView;
    Boolean trainingSelected= false;

    ArrayList<Training> trainings = new ArrayList<>();
    ArrayList<ExecutedTraining> executedtrainings = new ArrayList<>();
    ListAdapter trainingAdapter;
    ListView trainingList;
    AlertDialog dialog;
    Training selectedTraining;

    TextView lastTrainingDate;
    TextView allTrainingsTime;
    TextView trainingsQuantity;
    TextView lastTrainingName;


    MyExerciseDBHandler exerciseDBHandler;
    TrainingDBHandler trainingDBHandler;
    ExecutedTrainingDbHandler executedTrainingDbHandler;

    TextView trainingName;
    TextView trainingDuration;

    Integer trainingTimeInSeconds=0;

    Button startTrainingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_menu, container, false);
        initializeDataBase();

        getExecutedTrainingsFromDB();
        initializeTextViews();


        if(executedtrainings.size()>0){
            setTextViews();
        }


        startTrainingButton = (Button) rootView.findViewById(R.id.startTreningButton);

        startTrainingButton.setOnClickListener(v ->
        {
            startTraining();
        });



        Button addTrainingButton = (Button) rootView.findViewById(R.id.addTrainingButton);
        addTrainingButton.setOnClickListener(v ->
        {
            addNewTrainingBtnClicked();
        });



        Button chooseTrainingButton = (Button) rootView.findViewById(R.id.chooseTrainingButton);
        chooseTrainingButton.setOnClickListener(v ->
        {
            chooseTrainingClicked();
        });

        selectTraining();
        return rootView;
    }

    private void initializeDataBase(){
        trainingDBHandler = new TrainingDBHandler(getContext(), null, null, 1);
        exerciseDBHandler = new MyExerciseDBHandler(getContext(), null, null , 1);
        executedTrainingDbHandler = new ExecutedTrainingDbHandler(getContext(), null, null, 1);
    }

    //--------------------------------------------------------------------

    public void addNewTrainingBtnClicked(){

        Intent intent = new Intent(this.getContext(), AddTraining.class);
        startActivity(intent);

    }
    //--------------------------------------------------------------------

    public void startTraining(){
        TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabhost.getTabAt(2).select();
    }

    //--------------------------------------------------------------------

    public void selectTraining(){
            trainingTimeInSeconds=0;

            trainingName = (TextView) rootView.findViewById(R.id.mainMenuTrainingName);
            trainingName.setText("Trening nie został wybrany");

            trainingDuration= (TextView) rootView.findViewById(R.id.mainMenuTrainingDuration);
            trainingDuration.setText("Brak");

            startTrainingButton.setEnabled(false);
            trainingSelected = true;

    }

    public void setSelectedTrainingData(){
        trainingName.setText(this.selectedTraining.getName());
        trainingDuration.setText(Integer.toString(trainingTimeInSeconds/60)+":"+Integer.toString(trainingTimeInSeconds % 60));
    }


    private void initializeTrainingList(View mView){

        trainingAdapter = new TrainingAdapter(mView.getContext(), trainings);
        trainingList = (ListView) mView.findViewById(R.id.dialogTrainingList);
        trainingList.setAdapter(trainingAdapter);

        trainingList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    String time="";
                    String[] timeArray= new String[0];

                    Object object= parent.getItemAtPosition(position);
                    this.selectedTraining = (Training) object;
                    Training.ACTUAL_TRAINING=this.selectedTraining;
                    String training = String.valueOf(object);

                    Toast.makeText(getContext(), training, Toast.LENGTH_LONG).show();

                    ArrayList<Exercise> exercises = exerciseDBHandler.getExerciseList();

                    startTrainingButton.setEnabled(true);

                    Exercise.EXERCISES_LIST= exerciseDBHandler.getExercisesByTrainingId(Integer.toString( this.selectedTraining.getId()));
                    for (Exercise exercise: Exercise.EXERCISES_LIST) {
                        time = exercise.getDuration();
                        timeArray = time.split(":");
                        trainingTimeInSeconds += Integer.parseInt(timeArray[0])*60 + Integer.parseInt(timeArray[1]);
                    }
                    setSelectedTrainingData();
                    dialog.dismiss();

                }
        );

    }

    private void getExecutedTrainingsFromDB(){
        executedtrainings = executedTrainingDbHandler.getExecutedTrainingList();

    }

    private void getTrainingFromDB() {

        trainings = trainingDBHandler.getTrainingList();
    }

    private void chooseTrainingClicked(){

        getTrainingFromDB();
        View mView = getLayoutInflater().inflate(R.layout.dialog_choose_training, null);
        initializeTrainingList(mView);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mView.getContext());

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    private void initializeTextViews(){
        lastTrainingDate= rootView.findViewById(R.id.main_lastTrainingDate);
        allTrainingsTime= rootView.findViewById(R.id.main_trainingTime);
        trainingsQuantity= rootView.findViewById(R.id.main_trainingQuantity);
        lastTrainingName= rootView.findViewById(R.id.main_lastTrainingName);
    }

    private String getAllTrainingTime(ArrayList<ExecutedTraining> trainings){
        Long trainingsTime=0L;

        for (ExecutedTraining training: trainings) {
            trainingsTime += Long.parseLong(training.getDuration());
        }

        Integer  secs = (int)(long) trainingsTime/1000;
        Integer mins = secs/60;
        Integer hours = mins/60;
        mins %=60;
        secs %=60;
        return hours+"h "+mins+"min "+secs+"s";
    }

    private void setTextViews(){
        Integer size = executedtrainings.size();
        ExecutedTraining training = executedtrainings.get(size-1);

        lastTrainingDate.setText(training.getDateOfExecution());

        allTrainingsTime.setText(getAllTrainingTime(executedtrainings));

        trainingsQuantity.setText(Integer.toString( executedtrainings.size()));

        lastTrainingName.setText(executedtrainings.get(size-1).getName());
    }

}
