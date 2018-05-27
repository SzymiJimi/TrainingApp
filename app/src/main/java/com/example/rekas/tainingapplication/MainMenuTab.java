package com.example.rekas.tainingapplication;

/**
 * Created by rekas on 14.04.2018.
 */
import android.app.AlertDialog;
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
import com.example.rekas.tainingapplication.model.Exercise;
import com.example.rekas.tainingapplication.model.Training;
import com.example.rekas.tainingapplication.service.MyExerciseDBHandler;
import com.example.rekas.tainingapplication.service.TrainingDBHandler;

import java.util.ArrayList;

public class MainMenuTab extends Fragment{


    View rootView;

    ArrayList<Training> trainings = new ArrayList<>();
    ListAdapter trainingAdapter;
    ListView trainingList;
    AlertDialog dialog;

    MyExerciseDBHandler exerciseDBHandler;
    TrainingDBHandler trainingDBHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_menu, container, false);
        initializeDataBase();

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

        return rootView;
    }

    private void initializeDataBase(){
        trainingDBHandler = new TrainingDBHandler(getContext(), null, null, 1);
        exerciseDBHandler = new MyExerciseDBHandler(getContext(), null, null , 1);
    }

    //--------------------------------------------------------------------

    public void addNewTrainingBtnClicked(){

        Intent intent = new Intent(this.getContext(), AddTraining.class);
        startActivity(intent);

    }

    //--------------------------------------------------------------------
    private void initializeTrainingList(View mView){

        trainingAdapter = new TrainingAdapter(mView.getContext(), trainings);
        trainingList = (ListView) mView.findViewById(R.id.dialogTrainingList);
        trainingList.setAdapter(trainingAdapter);

        trainingList.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    Object object= parent.getItemAtPosition(position);
                    Training trainingClicked = (Training) object;
                    String training = String.valueOf(object);
                    Toast.makeText(getContext(), training, Toast.LENGTH_LONG).show();
                    ArrayList<Exercise> exercises = exerciseDBHandler.getExerciseList();

                    Exercise.EXERCISES_LIST= exerciseDBHandler.getExercisesByTrainingId(Integer.toString( trainingClicked.getId()));
                    TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                    tabhost.getTabAt(2).select();
                    dialog.dismiss();

                }
        );

    }

    private void getTrainingFromDB() {

        trainings = trainingDBHandler.getTrainingList();
    }

    private void chooseTrainingClicked(){

        getTrainingFromDB();
        View mView = getLayoutInflater().inflate(R.layout.dialog_choose_training, null);
        initializeTrainingList(mView);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mView.getContext());

//        EditText trainingName= (EditText) mView.findViewById(R.id.trainingName);
//        EditText seriesQuantity= (EditText) mView.findViewById(R.id.seriesQuantity);
//        EditText repeatNumber= (EditText) mView.findViewById(R.id.repeatWuantity);
//        EditText breakLength= (EditText) mView.findViewById(R.id.breakLength);
//        Button addBtnClicked= (Button) mView.findViewById(R.id.addTrainingBtn);
//        addBtnClicked.setOnClickListener(v -> {
//
//        });
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

}
