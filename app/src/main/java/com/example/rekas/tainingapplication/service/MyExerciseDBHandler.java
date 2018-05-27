package com.example.rekas.tainingapplication.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import com.example.rekas.tainingapplication.model.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rekas on 20.05.2018.
 */

public class MyExerciseDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "exercises.db";
    public static final String TABLE_EXERCISE = "exercises";
    public static final String COLUMN_IDEXERCISE = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_REPETITIONS = "repetitions";
    public static final String COLUMN_SERIES = "series";
    public static final String COLUMN_TRAININGID = "trainingId";

    public MyExerciseDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISE + "(" +
                COLUMN_IDEXERCISE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DURATION + " TEXT , " +
                COLUMN_REPETITIONS + " TEXT , " +
                COLUMN_SERIES + " TEXT , " +
                COLUMN_TRAININGID + " TEXT );";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        onCreate(db);
    }


    public void addExercise(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, exercise.getName());
        values.put(COLUMN_DURATION, exercise.getDuration());
        values.put(COLUMN_REPETITIONS, exercise.getRepetitions());
        values.put(COLUMN_SERIES, exercise.getSeries());
        values.put(COLUMN_TRAININGID, exercise.getTrainingId());
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_EXERCISE, null, values);
        db.close();
    }

    public void deleteExercise(String exerciseName) {
        SQLiteDatabase db = getReadableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_EXERCISE + " WHERE " + COLUMN_TRAININGID + "=\"" + exerciseName + "\";");
        db.execSQL("DELETE FROM " + TABLE_EXERCISE + " WHERE " + COLUMN_NAME + "=\"" + exerciseName + "\";");

    }

    public ArrayList<Exercise> getExerciseList() {
        ArrayList<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISE+";";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Integer id=0, idTraining=0;
        String name="", duration="", repetitions="", series="";

        while (!c.isAfterLast()) {
            if (c.getString(0) != null) {
                id = Integer.parseInt(c.getString(0));
            }
            if (c.getString(c.getColumnIndex("name")) != null) {
                name=c.getString(c.getColumnIndex("name"));
            }
            if (c.getString(c.getColumnIndex("duration")) != null) {
                duration= c.getString(c.getColumnIndex("duration"));
            }
            if (c.getString(c.getColumnIndex("repetitions")) != null) {
                repetitions= c.getString(c.getColumnIndex("repetitions"));
            }
            if (c.getString(c.getColumnIndex("series")) != null) {
                series= c.getString(c.getColumnIndex("series"));
            }
            if (c.getString(c.getColumnIndex("trainingId")) != null) {
                idTraining= Integer.parseInt( c.getString(c.getColumnIndex("trainingId")));
            }
            exercises.add(new Exercise(id, name, duration,repetitions,series,idTraining));
            c.moveToNext();
        }
        db.close();
        return exercises;
    }

    public ArrayList<Exercise> getExercisesByTrainingId(String trainingId) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_EXERCISE+" WHERE " +COLUMN_TRAININGID+"="+trainingId+";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Integer id=0, idTraining=0;
        String name="", duration="", repetitions="", series="";


        while (!c.isAfterLast()) {
            if (c.getString(0) != null) {
                id = Integer.parseInt(c.getString(0));
            }
            if (c.getString(c.getColumnIndex("name")) != null) {
                name=c.getString(c.getColumnIndex("name"));
            }
            if (c.getString(c.getColumnIndex("duration")) != null) {
                duration= c.getString(c.getColumnIndex("duration"));
            }
            if (c.getString(c.getColumnIndex("repetitions")) != null) {
                repetitions= c.getString(c.getColumnIndex("repetitions"));
            }
            if (c.getString(c.getColumnIndex("series")) != null) {
                series= c.getString(c.getColumnIndex("series"));
            }
            if (c.getString(c.getColumnIndex("trainingId")) != null) {
                idTraining= Integer.parseInt( c.getString(c.getColumnIndex("trainingId")));
            }
            exercises.add(new Exercise(id, name, duration,repetitions,series,idTraining));
            c.moveToNext();
        }
        db.close();
        return exercises;
    }

}
