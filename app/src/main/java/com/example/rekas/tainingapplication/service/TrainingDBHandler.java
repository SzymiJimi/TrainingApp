package com.example.rekas.tainingapplication.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rekas.tainingapplication.model.Exercise;
import com.example.rekas.tainingapplication.model.Training;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by rekas on 20.05.2018.
 */

public class TrainingDBHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "training_db.db";
    private static final String TABLE_TRAINING = "training";
    private static final String COLUMN_IDTRAINING = "id";
    private static final String COLUMN_NAME = "name";

    public TrainingDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TRAINING + "(" +
                COLUMN_IDTRAINING + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_NAME + " TEXT );";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
        onCreate(db);
    }

    public void dropTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
    }


    public void addTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, training.getName());

        long id= db.insert(TABLE_TRAINING, null, values);
        db.close();
    }

    public void deleteTraining(String trainingName) {
        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_EXERCISE + " WHERE " + COLUMN_TRAININGID + "=\"" + exerciseName + "\";");
        db.execSQL("DELETE FROM " + TABLE_TRAINING + " WHERE " + COLUMN_NAME + "=\"" + trainingName + "\";");

    }

    public ArrayList<Training> getTrainingList() {
        ArrayList<Training> trainings = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TRAINING+";";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int tmpId=0;
        String tmpName="";

        while (!c.isAfterLast()) {

            if (c.getString(0) != null) {
                tmpId = Integer.parseInt(c.getString(0));
            }

            if (c.getString(1) != null) {
                tmpName= c.getString(1);
            }
            trainings.add(new Training(tmpId,tmpName));
            c.moveToNext();
        }
        db.close();
        return trainings;
    }

    public Training getLastTraining() {
        Training lastTraining = new Training();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT MAX( "+ COLUMN_IDTRAINING + "),"+COLUMN_IDTRAINING+","+ COLUMN_NAME +" FROM " + TABLE_TRAINING+";";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        String tmpString;


        while (!c.isAfterLast()) {

            if (c.getString(1) != null) {
                tmpString = c.getString(1);
                lastTraining.setId(Integer.parseInt( tmpString));
            }

            if (c.getString(2) != null) {
                lastTraining.setName(c.getString(2));
            }
            c.moveToNext();
        }
        db.close();
        return lastTraining;
    }

}
