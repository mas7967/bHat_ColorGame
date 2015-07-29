package com.bhat.colorgame;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mark Schillaci on 7/29/15.
 */
public class ScoreKeeper {

    private Context context;
    private View view;
    private int id;

    private TextView scoreText;

    private static final String preText = "Score: ";

    private int score = 0;

    private long previousTime = 0;
    private double timeDifference = 0;
    private int scoreIncrease = 0;

    private static final double scoreAmplitude = 25;
    private static final double timeToOnePoint = 2;
    private static final double scoreTimeScale = (timeToOnePoint/Math.log(1/scoreAmplitude));

    public ScoreKeeper(Context context, View view, int id) {
        this.context = context;
        this.view = view;
        this.id = id;

        scoreText = (TextView) view.findViewById(id);
        updateText();
    }

    public void increaseScore(long systemTime){
        timeDifference = (systemTime - previousTime) / 1000.0;
        if(timeDifference >= 1){
            score++;
        } else {
            scoreIncrease = (int) (scoreAmplitude * Math.exp(timeDifference/scoreTimeScale));
            score+=scoreIncrease;
        }
        previousTime = systemTime;
        updateText();
    }

    public void updateText(){
        scoreText.setText(preText + Integer.toString(score));
    }
}
