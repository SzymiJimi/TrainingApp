package com.example.rekas.tainingapplication.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rekas on 14.04.2018.
 */

public class Training {

    public static Training ACTUAL_TRAINING;

    private Integer id;
    private String name;

    public Training(){

    }

    public Training(String name) {
        this.name = name;
    }

    public Training(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
