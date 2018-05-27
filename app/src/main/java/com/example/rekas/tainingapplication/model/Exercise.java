package com.example.rekas.tainingapplication.model;


import java.util.ArrayList;

/**
 * Created by rekas on 14.04.2018.
 */

public class Exercise {

    public static ArrayList<Exercise> EXERCISES_LIST;

    private Integer idExercise;
    private String name;
    private String duration;
    private String repetitions;
    private String series;
    private Integer trainingId;

    public Exercise(){

    }

    public Exercise(String name, String duration, String repetitions,String series ,Integer trainingId) {
        this.name = name;
        this.duration = duration;
        this.repetitions = repetitions;
        this.series = series;
        this.trainingId = trainingId;
    }

    public Exercise(Integer id, String name, String duration, String repetitions,String series ,Integer trainingId) {
        this.idExercise = id;
        this.name = name;
        this.duration = duration;
        this.repetitions = repetitions;
        this.series = series;
        this.trainingId = trainingId;
    }

    public Integer getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(Integer idExercise) {
        this.idExercise = idExercise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }
}
