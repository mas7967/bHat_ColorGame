package com.bhat.colorgame;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Mark Schillaci on 7/29/15.
 */
public class GameTimer {

    private Context context;
    private View view;
    long timeRemaining;
    private int frequency;
    private int id;

    private static final String FORMAT = "%01d:%02d";

    private TextView timeLeftText;

    private CountDownTimer timeLeftCountDownTimer;


    public GameTimer(Context context, View view, long startTime, int frequency, int id) {
        this.context = context;
        this.view = view;
        this.timeRemaining = startTime;
        this.frequency = frequency;
        this.id = id;

        timeLeftText = (TextView) view.findViewById(id);

        timeLeftCountDownTimer = new CountDownTimer(timeRemaining, frequency) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                timeLeftText.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timeLeftText.setText("GAME OVER!");
            }
        }.start();
    }

    public void updateTimer(long increaseAmount){
        timeLeftCountDownTimer.cancel();
        timeLeftCountDownTimer = new CountDownTimer(increaseAmount + timeRemaining, frequency) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                timeLeftText.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timeLeftText.setText("GAME OVER!");
            }
        }.start();
    }

}
