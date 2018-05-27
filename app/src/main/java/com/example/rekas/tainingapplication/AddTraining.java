package com.example.rekas.tainingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rekas.tainingapplication.adapters.ExerciseListAdapter;
import com.example.rekas.tainingapplication.adapters.LastTrainingListAdapter;
import com.example.rekas.tainingapplication.model.Exercise;
import com.example.rekas.tainingapplication.model.Training;
import com.example.rekas.tainingapplication.service.MyExerciseDBHandler;
import com.example.rekas.tainingapplication.service.TrainingDBHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddTraining extends AppCompatActivity {

    String[] trainings = {"Barki", "Triceps & Plecy", "Nogi", "Klata", "Cardio"};
    ArrayList<Exercise> exercises = new ArrayList<>();
    ListAdapter exerciseAdapter;
    ListView exercisesList;
    EditText trainingName;
    Training training;

    MyExerciseDBHandler exerciseDbHandler;
    TrainingDBHandler trainingDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trainingName = (EditText) findViewById(R.id.trainingName);

        initializeDataBase();
//        addSomeData();
        initializeExerciseList();


        Button addExerciseBtn = (Button) findViewById(R.id.addExerciseBtn);
        addExerciseBtn.setEnabled(false);

        addExerciseBtn.setOnClickListener(v -> {
            addNewTrainingBtnClicked();
        });

        Button saveTitleBtn = (Button) findViewById(R.id.saveTitleButton);
        saveTitleBtn.setOnClickListener(v -> {
           addExerciseBtn.setEnabled(true);
           trainingName.setEnabled(false);
           saveTitleBtn.setEnabled(false);
           createNewTrainingRow(trainingName.getText().toString());
        });

        Button closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v ->
        {
            finish();
        });
    }

    private void initializeDataBase(){

        exerciseDbHandler = new MyExerciseDBHandler(this, null, null, 1);

        trainingDBHandler = new TrainingDBHandler(this, null, null, 1);
    }

    private void initializeExerciseList(){
        exerciseAdapter = new ExerciseListAdapter(this, exercises);
        exercisesList = (ListView) findViewById(R.id.addedExercises);
        exercisesList.setAdapter(exerciseAdapter);

        exercisesList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    String training = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(this, training, Toast.LENGTH_LONG).show();
                }
        );

    }

    private void createNewTrainingRow(String trainingName){
        training= new Training(trainingName);
        trainingDBHandler.addTraining(training);
    }



    public void getDataFromDb() {
        exercises = exerciseDbHandler.getExerciseList();
    }

    public void addNewTrainingBtnClicked() {
        Training lastTraining = trainingDBHandler.getLastTraining();
        ArrayList<Training> trainings = trainingDBHandler.getTrainingList();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_new_training, null);
        EditText trainingName = (EditText) mView.findViewById(R.id.trainingName);
        EditText seriesQuantity = (EditText) mView.findViewById(R.id.seriesQuantity);
        EditText repeatNumber = (EditText) mView.findViewById(R.id.repeatWuantity);
        EditText breakLength = (EditText) mView.findViewById(R.id.breakLength);
        Button addBtnClicked = (Button) mView.findViewById(R.id.addTrainingBtn);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        addBtnClicked.setOnClickListener(v -> {
            Exercise newExercise = new Exercise();
            newExercise.setName(trainingName.getText().toString());
            newExercise.setDuration(breakLength.getText().toString());
            newExercise.setSeries(seriesQuantity.getText().toString());
            newExercise.setRepetitions(repeatNumber.getText().toString());
            newExercise.setTrainingId(lastTraining.getId());
            exerciseDbHandler.addExercise(newExercise);
            exercises.add(newExercise);
            exercisesList.invalidateViews();
            dialog.dismiss();
        });




    }


}
