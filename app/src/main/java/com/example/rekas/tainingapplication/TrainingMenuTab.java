package com.example.rekas.tainingapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by rekas on 14.04.2018.
 */

public class TrainingMenuTab  extends Fragment  {


//    private void loadList(List<>)

    Button startBtn;
    Button pauseBtn;
    Button stopBtn;
    TextView txtTimer;
    Handler customHandler =new Handler();
    LinearLayout container;

    long startTime=0L, timeInMiliseconds = 0L, timeSwapBuff=0L, updateTime=0L;

    Runnable updateTimeThread = new Runnable() {

        @Override
        public void run() {
            timeInMiliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff + timeInMiliseconds;
            int secs = (int) (updateTime/1000);
            int mins = secs/60;
            secs %=60;
            int milliseconds= (int) (updateTime%1000);
            
            txtTimer.setText(""+mins+":"+String.format("%2d", secs)+":"
                                        +String.format("%3d", milliseconds));
            customHandler.postDelayed(this,0);
        }
    };


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.training_menu, container, false);

        startBtn= rootView.findViewById(R.id.startBtn);
        pauseBtn= rootView.findViewById(R.id.btnPause);
        stopBtn = rootView.findViewById(R.id.stopBtn);
        txtTimer = rootView.findViewById(R.id.timerValue);
        container = rootView.findViewById(R.id.times);

        startBtn.setOnClickListener((view) -> {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimeThread, 0);
            }
        );

        pauseBtn.setOnClickListener((view)->
        {
            timeSwapBuff+=timeInMiliseconds;
            customHandler.removeCallbacks(updateTimeThread);
        });

        stopBtn.setOnClickListener(view -> {

    });

        return rootView;
    }

}