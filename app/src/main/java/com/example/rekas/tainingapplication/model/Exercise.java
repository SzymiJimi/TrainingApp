package com.example.rekas.tainingapplication.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rekas on 14.04.2018.
 */

@DatabaseTable(tableName = "exercise")
public class Exercise {

    public Exercise() {
    }

    @DatabaseField(id=true)
    private Integer idExercise;

    @DatabaseField
    private String name;

    @DatabaseField
    private String duration;

    @DatabaseField
    private String repetitions;

    @DatabaseField
    private Integer load;

    @DatabaseField
    private String additionalInfo;

    @DatabaseField
    private Integer trainingId;

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

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }
}
