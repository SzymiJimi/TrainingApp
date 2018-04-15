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
    TextView countdownTimer;
    TextView countdownText;
    Handler customHandler =new Handler();
    LinearLayout container;
    int secs, mins, milliseconds, countdownSec=120, countdownMin=2, tmpSec=-1, displaySec=0;
    boolean workout=true;

    long startTime=0L, timeInMiliseconds = 0L, timeSwapBuff=0L, updateTime=0L;

    Runnable updateTimeThread = new Runnable() {

        @Override
        public void run() {
            timeInMiliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff + timeInMiliseconds;
            secs = (int) (updateTime/1000);

            if(countdownSec==0)
            {
                countdownSec=121;
                if(workout)
                {
                    countdownText.setText("Do końca przerwy");
                    workout=false;
                }else{
                    countdownText.setText("Do końca serii");
                    workout=true;
                }
            }

            if(tmpSec!=secs){
                countdownSec=countdownSec-1;
                countdownMin=countdownSec/60;
                displaySec=countdownSec;
                displaySec%=60;
                tmpSec=secs;
            }

            mins = secs/60;
            secs %=60;

            milliseconds= (int) (updateTime%1000);

            txtTimer.setText(""+mins+":"+String.format("%2d", secs)+":"
                                        +String.format("%3d", milliseconds));
            countdownTimer.setText(countdownMin+":"+String.format("%2d", displaySec));
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
        countdownTimer = rootView.findViewById(R.id.countdownTime);
        countdownText = rootView.findViewById(R.id.countdownText);

        startBtn.setOnClickListener((view) -> {
                startTime = SystemClock.uptimeMillis();
                countdownSec=120;
                displaySec=0;
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