package com.example.rekas.tainingapplication.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rekas.tainingapplication.model.ExecutedTraining;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rekas on 24.06.2018.
 */

public class ExecutedTrainingDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "executed_training_db1.db";
    private static final String TABLE_TRAINING = "executed_training";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER_OF_EXERCISES = "numberOfExercises";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_DATE_OF_EXECUTION = "dateOfExecution";

    public ExecutedTrainingDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TRAINING + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NUMBER_OF_EXERCISES + " INTEGER, " +
                COLUMN_DURATION + " TEXT, " +
                COLUMN_DATE_OF_EXECUTION + " DATETIME );";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
        onCreate(db);
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
    }


    public void addTraining(ExecutedTraining training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, training.getName());
        values.put(COLUMN_NUMBER_OF_EXERCISES, training.getNumberOfExercises());
        values.put(COLUMN_DURATION, training.getDuration());
        System.out.println("Dodawana data w STRINGU: " + training.getDateOfExecution().toString());
        values.put(COLUMN_DATE_OF_EXECUTION, training.getDateOfExecution().toString());

        long id = db.insert(TABLE_TRAINING, null, values);
        db.close();
    }

    public void deleteExecutedTraining(String trainingName) {
        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_EXERCISE + " WHERE " + COLUMN_TRAININGID + "=\"" + exerciseName + "\";");
        db.execSQL("DELETE FROM " + TABLE_TRAINING + " WHERE " + COLUMN_NAME + "=\"" + trainingName + "\";");

    }

    private ArrayList<ExecutedTraining> getAllColumns(String query) {
        ArrayList<ExecutedTraining> trainings = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int tmpId = 0;
        Integer tmpNumberOfExercises = 0;

        String tmpName = "", tmpDuration = "";
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

//        LocalDate tmpDate;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

//        Date tmpDate = new Date();
        String tmpDate = "";

        while (!c.isAfterLast()) {

            if (c.getString(0) != null) {
                tmpId = Integer.parseInt(c.getString(0));
            }

            if (c.getString(1) != null) {
                tmpName = c.getString(1);
            }

            if (c.getString(1) != null) {
                tmpNumberOfExercises = Integer.parseInt(c.getString(2));
            }

            if (c.getString(1) != null) {
                tmpDuration = c.getString(3);
            }

            if (c.getString(1) != null) {
                System.out.println("Pobrana data z bazy danych: " + c.getString(4));
//                tmpDate=  format.parse(c.getString(4)) ;
                tmpDate = c.getString(4);
            }
            trainings.add(new ExecutedTraining(tmpId, tmpName, tmpNumberOfExercises, tmpDuration, tmpDate));
            c.moveToNext();
        }
        db.close();
        return trainings;
    }

    public ArrayList<ExecutedTraining> getExecutedTrainingList() {
//        ArrayList<ExecutedTraining> trainings = new ArrayList<>();
//        SQLiteDatabase db = getReadableDatabase();


        String query = "SELECT * FROM " + TABLE_TRAINING + ";";

        ArrayList<ExecutedTraining> trainings = getAllColumns(query);


//        Cursor c = db.rawQuery(query, null);
//        c.moveToFirst();
//        int tmpId=0;
//        Integer tmpNumberOfExercises=0;
//
//        String tmpName="", tmpDuration="";
//        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//
////        LocalDate tmpDate;
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
//
////        Date tmpDate = new Date();
//        String tmpDate = "";
//
//        while (!c.isAfterLast()) {
//
//            if (c.getString(0) != null) {
//                tmpId = Integer.parseInt(c.getString(0));
//            }
//
//            if (c.getString(1) != null) {
//                tmpName= c.getString(1);
//            }
//
//            if (c.getString(1) != null) {
//                tmpNumberOfExercises= Integer.parseInt(c.getString(2));
//            }
//
//            if (c.getString(1) != null) {
//                tmpDuration= c.getString(3);
//            }
//
//            if (c.getString(1) != null) {
//                System.out.println("Pobrana data z bazy danych: "+c.getString(4));
////                tmpDate=  format.parse(c.getString(4)) ;
//                tmpDate=  c.getString(4) ;
//            }
//            trainings.add(new ExecutedTraining(tmpId,tmpName, tmpNumberOfExercises, tmpDuration, tmpDate));
//            c.moveToNext();
//        }
//        db.close();
        return trainings;

    }


    public ArrayList<ExecutedTraining> getLastWeekTraining() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String date = format.format(cal.getTime());

        System.out.println("Date = "+ cal.getTime());

        String query = "SELECT * FROM " + TABLE_TRAINING + " WHERE "+ COLUMN_DATE_OF_EXECUTION+">"+date+";";

        ArrayList<ExecutedTraining> trainings = getAllColumns(query);
        System.out.println("Ilosć wykonanych treningów w tygodniu: "+trainings.size());
        return trainings;
    }
//    public ExecutedTraining getLastTraining() {
//        ExecutedTraining lastTraining = new ExecutedTraining();
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT MAX( "+ COLUMN_IDTRAINING + "),"+COLUMN_IDTRAINING+","+ COLUMN_NAME +" FROM " + TABLE_TRAINING+";";
//
//        Cursor c = db.rawQuery(query, null);
//        c.moveToFirst();
//
//        String tmpString;
//
//
//        while (!c.isAfterLast()) {
//
//            if (c.getString(1) != null) {
//                tmpString = c.getString(1);
//                lastTraining.setId(Integer.parseInt( tmpString));
//            }
//
//            if (c.getString(2) != null) {
//                lastTraining.setName(c.getString(2));
//            }
//            c.moveToNext();
//        }
//        db.close();
//        return lastTraining;
//    }


}
