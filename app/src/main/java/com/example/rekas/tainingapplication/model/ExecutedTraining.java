package com.example.rekas.tainingapplication.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rekas on 24.06.2018.
 */

public class ExecutedTraining {

    public static ArrayList<ExecutedTraining> EXECUTED_TRAININGS;

    private Integer id;
    private String name;
    private Integer numberOfExercises;
    private String duration;
    private String dateOfExecution;

    public ExecutedTraining() {
    }

    public ExecutedTraining(String name, Integer numberOfExercises, String duration, String dateOfExecution) {
        this.name = name;
        this.numberOfExercises = numberOfExercises;
        this.duration = duration;
        this.dateOfExecution = dateOfExecution;
    }

    public ExecutedTraining(Integer id, String name, Integer numberOfExercises, String duration, String dateOfExecution) {
        this.id = id;
        this.name = name;
        this.numberOfExercises = numberOfExercises;
        this.duration = duration;
        this.dateOfExecution = dateOfExecution;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfExercises() {
        return numberOfExercises;
    }

    public void setNumberOfExercises(Integer numberOfExercises) {
        this.numberOfExercises = numberOfExercises;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDateOfExecution() {
        return dateOfExecution;
    }

    public void setDateOfExecution(String dateOfExecution) {
        this.dateOfExecution = dateOfExecution;
    }
}
