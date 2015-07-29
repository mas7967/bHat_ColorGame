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
    ColorGameController myColorGameController;
    ColorGameGridAdapter myColorGameGridAdapter;

    TextView timeLeftText;
    private static final String FORMAT = "%01d:%02d";
    CountDownTimer timeLeftCountDownTimer;
    long timeLeftMilliseconds;

    TextView scoreText;

    ImageButton plusSixtyUpgrade;
    ImageButton noGridUpgrade;
    ImageButton betterContrastUpgrade;
    ImageButton noPenaltyUpgrade;

    UpgradeBadge plusSixtyUpgradeBadge;
    UpgradeBadge noGridUpgradeBadge;
    UpgradeBadge betterContrastUpgradeBadge;
    UpgradeBadge noPenaltyUpgradeBadge;

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

        // Set up our ImageButtons for upgrades, along with badges
        plusSixtyUpgrade      = (ImageButton) myView.findViewById(R.id.plus_sixty_upgrade);
        plusSixtyUpgrade.setOnTouchListener(this);
        noGridUpgrade         = (ImageButton) myView.findViewById(R.id.no_grid_upgrade);
        noGridUpgrade.setOnTouchListener(this);
        betterContrastUpgrade = (ImageButton) myView.findViewById(R.id.better_contrast_upgrade);
        betterContrastUpgrade.setOnTouchListener(this);
        noPenaltyUpgrade      = (ImageButton) myView.findViewById(R.id.no_penalty_upgrade);
        noPenaltyUpgrade.setOnTouchListener(this);

        plusSixtyUpgradeBadge      = new UpgradeBadge(getActivity(), myView, 5, R.id.plus_sixty_upgrade_badge);
        noGridUpgradeBadge         = new UpgradeBadge(getActivity(), myView, 5, R.id.no_grid_upgrade_badge);
        betterContrastUpgradeBadge = new UpgradeBadge(getActivity(), myView, 5, R.id.better_contrast_upgrade_badge);
        noPenaltyUpgradeBadge      = new UpgradeBadge(getActivity(), myView, 5, R.id.no_penalty_upgrade_badge);

        // Return our view
        return myView;
    }

    // Handles the clicking of the game pieces (colored squares)
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

    // Handles clicking of the upgrade buttons and putting the upgrades into effect
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Make sure that the event is an ACTION_DOWN, then proceed
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Switch on ID's to figure out what was clicked
            switch (v.getId()) {
                case R.id.plus_sixty_upgrade:
                    // Do plus sixty upgrade
                    if (plusSixtyUpgradeBadge.getRemainingUpgrades() > 0) {
                        plusSixtyUpgradeBadge.useUpgrade();
                        timeLeftCountDownTimer = updateTimer(timeLeftCountDownTimer, 60000);
                    }
                    break;

                case R.id.no_grid_upgrade:
                    // Do no grid upgrade if not already activated
                    if ( (canClickNoGrid) && (noGridUpgradeBadge.getRemainingUpgrades() > 0) ){
                        canClickNoGrid = false;
                        noGridUpgradeBadge.useUpgrade();
                        updatePadding(0, false);

                        Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            public void run() {
                                canClickNoGrid = true;
                                updatePadding(8, true);
                            }
                        };
                        // Take away no grid upgrade after 10 seconds
                        handler.postDelayed(runnable, 10000);
                    }
                    break;

                case R.id.better_contrast_upgrade:
                    // Do better contrast upgrade
                    if (betterContrastUpgradeBadge.getRemainingUpgrades() > 0) {
                        betterContrastUpgradeBadge.useUpgrade();
                        myColorGameController.setColorDifference(105);
                        myColorGameController.updateGamePieceDifferences();
                        setAndNotify();
                    }
                    break;

                case R.id.no_penalty_upgrade:
                    // Do no penalty upgrade if not already activated
                    if ( (canClickNoPenalty) && (noPenaltyUpgradeBadge.getRemainingUpgrades() > 0) ){
                        noPenaltyUpgradeBadge.useUpgrade();
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

    // Updates padding when the no grid upgrade is turned on/off
    public void updatePadding(int newPadding, boolean newHasPadding){
        myGridView.setVerticalSpacing(ColorGameController.convertDpToPixels(newPadding, getActivity()));
        myGridView.setHorizontalSpacing(ColorGameController.convertDpToPixels(newPadding, getActivity()));
        myColorGameController.setHasPadding(newHasPadding);

        myColorGameController.updateGamePieceSizes();
        setAndNotify();
    }

    // Handles a change in the amount of time left by canceling countdown and creating new one
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

    // Set the grid pieces and notify the adapater of a data set change
    private void setAndNotify() {
        myColorGameGridAdapter.setGridPieces(myColorGameController.getGridPieces());
        myColorGameGridAdapter.notifyDataSetChanged();
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

}
