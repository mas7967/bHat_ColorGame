package com.bhat.colorgame;

import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mark Schillaci on 7/18/15.
 */
public class ColorGameActivityFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnTouchListener {

    View myView;
    GridView myGridView;
    TextView timeLeftText;
    TextView scoreText;

    ImageButton plusSixtyUpgrade;
    ImageButton noGridUpgrade;
    ImageButton betterContrastUpgrade;
    ImageButton noPenaltyUpgrade;

    private int plusSixtyUpgradeRemaining = 5;
    private int noGridUpgradeRemaining = 5;
    private int betterContrastUpgradeRemaining = 5;
    private int noPenaltyUpgradeRemaining = 5;
    TextView plusSixtyUpgradeRemainingText;
    TextView noGridUpgradeRemainingText;
    TextView betterContrastUpgradeRemainingText;
    TextView noPenaltyUpgradeRemainingText;

    CountDownTimer timeLeftCountDownTimer;
    long timeLeftMilliseconds;

    ColorGameController myColorGameController;
    ColorGameGridAdapter myColorGameGridAdapter;

    private static final String FORMAT = "%01d:%02d";

    private boolean hasPenalty = true;
    private boolean canClickNoPenalty = true;
    private boolean canClickNoGrid = true;

    public ColorGameActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create the view
        myView = inflater.inflate(R.layout.fragment_color_game, container, false);

        // Create the GridView
        myGridView = (GridView) myView.findViewById(R.id.color_game_grid);

        // Get screen width in dp, create GameController
        Configuration configuration = getActivity().getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        myColorGameController = new ColorGameController(getActivity(), screenWidthDp);

        // Create GridAdapter using GameController
        myColorGameGridAdapter = new ColorGameGridAdapter(getActivity(), inflater, myColorGameController.getGridPieces());

        // Set GridView's GameAdapter to that just made, give number of columns
        myGridView.setAdapter(myColorGameGridAdapter);
        myGridView.setNumColumns(myColorGameController.getNumberOfColumns());

        // Set GridView's spacing to 16 pixels, but converted to dp using GameController function
        myGridView.setVerticalSpacing(ColorGameController.convertDpToPixels(8, getActivity()));
        myGridView.setHorizontalSpacing(ColorGameController.convertDpToPixels(8, getActivity()));
        myGridView.setOnItemClickListener(this);

        // Set up our Time left
        timeLeftText = (TextView) myView.findViewById(R.id.timeText);
        timeLeftCountDownTimer = new CountDownTimer(60000, 20) {
            public void onTick(long millisUntilFinished) {
                timeLeftMilliseconds = millisUntilFinished;
                timeLeftText.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timeLeftText.setText("GAME OVER!");
            }
        }.start();

        // Set up our Score
        scoreText = (TextView) myView.findViewById(R.id.scoreText);

        // Set up our ImageButtons for upgrades
        plusSixtyUpgrade = (ImageButton) myView.findViewById(R.id.plus_sixty_upgrade);
        plusSixtyUpgrade.setOnTouchListener(this);
        noGridUpgrade = (ImageButton) myView.findViewById(R.id.no_grid_upgrade);
        noGridUpgrade.setOnTouchListener(this);
        betterContrastUpgrade = (ImageButton) myView.findViewById(R.id.better_contrast_upgrade);
        betterContrastUpgrade.setOnTouchListener(this);
        noPenaltyUpgrade = (ImageButton) myView.findViewById(R.id.no_penalty_upgrade);
        noPenaltyUpgrade.setOnTouchListener(this);

        plusSixtyUpgradeRemainingText = (TextView) myView.findViewById(R.id.plus_sixty_upgrade_remaining_text);
        noGridUpgradeRemainingText = (TextView) myView.findViewById(R.id.no_grid_upgrade_remaining_text);
        betterContrastUpgradeRemainingText = (TextView) myView.findViewById(R.id.better_contrast_upgrade_remaining_text);
        noPenaltyUpgradeRemainingText = (TextView) myView.findViewById(R.id.no_penalty_upgrade_remaining_text);

        // Return our view for some reason...
        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Get the piece touched by the user
        ColorGamePiece tappedPiece = (ColorGamePiece) parent.getItemAtPosition(position);

        // If the piece is correct...
        if(tappedPiece.isCorrect()){
            // Level up the player
            myColorGameController.levelUp();
            myGridView.setNumColumns(myColorGameController.getNumberOfColumns());
            myColorGameGridAdapter.setGridPieces(myColorGameController.getGridPieces());
            myColorGameGridAdapter.notifyDataSetChanged();

            // Update the score text
            scoreText.setText("Score: " + myColorGameController.getScoreString());

            // Increase the time left by 3 seconds
            timeLeftCountDownTimer = updateTimer(timeLeftCountDownTimer, 3000);
        } else {
            // If the piece isn't correct, check if they have the no penalty upgrade and update
            if(hasPenalty){
                timeLeftCountDownTimer = updateTimer(timeLeftCountDownTimer, -1000);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Make sure that the event is an ACTION_DOWN, then proceed
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Switch on ID's to figure out what was clicked
            switch (v.getId()) {
                case R.id.plus_sixty_upgrade:
                    // Do plus sixty
                    if (plusSixtyUpgradeRemaining > 0) {
                        plusSixtyUpgradeRemaining--;
                        plusSixtyUpgradeRemainingText.setText(Integer.toString(plusSixtyUpgradeRemaining));
                        timeLeftCountDownTimer = updateTimer(timeLeftCountDownTimer, 60000);
                    }
                    break;

                case R.id.no_grid_upgrade:
                    // Do no-grid upgrade if not already activated
                    if ( (canClickNoGrid) && (noGridUpgradeRemaining > 0) ){
                        canClickNoGrid = false;
                        noGridUpgradeRemaining--;
                        noGridUpgradeRemainingText.setText(Integer.toString(noGridUpgradeRemaining));
                        myGridView.setVerticalSpacing(ColorGameController.convertDpToPixels(0, getActivity()));
                        myGridView.setHorizontalSpacing(ColorGameController.convertDpToPixels(0, getActivity()));
                        myColorGameController.setHasPadding(false);
                        myColorGameController.createGamePieces();
                        myColorGameGridAdapter.setGridPieces(myColorGameController.getGridPieces());
                        myColorGameGridAdapter.notifyDataSetChanged();

                        Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            public void run() {
                                canClickNoGrid = true;
                                myGridView.setVerticalSpacing(ColorGameController.convertDpToPixels(8, getActivity()));
                                myGridView.setHorizontalSpacing(ColorGameController.convertDpToPixels(8, getActivity()));
                                myColorGameController.setHasPadding(true);
                                myColorGameController.createGamePieces();
                                myColorGameGridAdapter.setGridPieces(myColorGameController.getGridPieces());
                                myColorGameGridAdapter.notifyDataSetChanged();
                            }
                        };
                        // Take away no grid upgrade after 10 seconds
                        handler.postDelayed(runnable, 10000);
                    }
                    break;

                case R.id.better_contrast_upgrade:
                    // Do better contrast upgrade
                    if (betterContrastUpgradeRemaining > 0) {
                        betterContrastUpgradeRemaining--;
                        betterContrastUpgradeRemainingText.setText(Integer.toString(betterContrastUpgradeRemaining));
                        myColorGameController.setColorDifference(105);
                        myColorGameController.createGamePieces();
                        myColorGameGridAdapter.setGridPieces(myColorGameController.getGridPieces());
                        myColorGameGridAdapter.notifyDataSetChanged();
                    }
                    break;

                case R.id.no_penalty_upgrade:
                    // Do no penalty upgrade if not already activated
                    if ( (canClickNoPenalty) && (noPenaltyUpgradeRemaining > 0) ){
                        noPenaltyUpgradeRemaining--;
                        noPenaltyUpgradeRemainingText.setText(Integer.toString(noPenaltyUpgradeRemaining));
                        canClickNoPenalty = false;
                        hasPenalty = false;
                        Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            public void run() {
                                hasPenalty = true;
                                canClickNoPenalty = true;
                            }
                        };
                        // Take away no penalty upgrade after 10 seconds
                        handler.postDelayed(runnable, 10000);
                    }
                    break;
            }
        }

        return true;
    }

    public CountDownTimer updateTimer(CountDownTimer countDownTimer, long increaseAmount){
        countDownTimer.cancel();
        return new CountDownTimer(increaseAmount + timeLeftMilliseconds, 20) {
            public void onTick(long millisUntilFinished) {
                timeLeftMilliseconds = millisUntilFinished;
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
