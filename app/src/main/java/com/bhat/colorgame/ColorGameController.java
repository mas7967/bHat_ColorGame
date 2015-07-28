package com.bhat.colorgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mark Schillaci on 7/18/15.
 */
public class ColorGameController {

    private ArrayList<ColorGamePiece> gridPieces;

    private Context context;

    private int score = 0;
    private int width;
    private int level = 1;
    private int colorDifference = 105;
    private int trueSize;

    private int numberOfColumns = 2;

    private boolean hasPadding = true;

    Random myRandomGenerator = new Random();

    public ColorGameController(Context context, int width) {
        this.context = context;
        this.width = width;
        gridPieces = new ArrayList<ColorGamePiece>();
        createGamePieces();
    }

    public ArrayList<ColorGamePiece> getGridPieces() {
        return gridPieces;
    }

    public void levelUp(){
        level++;
        score++;
        colorDifference = Math.max(5, colorDifference - 5);
        createGamePieces();
    }

    public String getScoreString(){
        return Integer.toString(score);
    }

    public void setColorDifference(int colorDifference) {
        this.colorDifference = colorDifference;
    }

    public void setHasPadding(boolean hasPadding) {
        this.hasPadding = hasPadding;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void createGamePieces(){
        gridPieces = new ArrayList<ColorGamePiece>();
        numberOfColumns = 9;

        if(level < 30){
            numberOfColumns = (int) Math.floor(-0.006063*Math.pow(level,2) + 0.3827*level + 2.421);;
        }

        int red   = myRandomGenerator.nextInt(255 - colorDifference);
        int green = myRandomGenerator.nextInt(255 - colorDifference);
        int blue  = myRandomGenerator.nextInt(255 - colorDifference);

        int correctChoice = myRandomGenerator.nextInt(numberOfColumns*numberOfColumns)+1;

        updateTrueSize();

        ColorGamePiece colorGamePiece;
        for (int i = 1; i <= (numberOfColumns * numberOfColumns); i++) {

            if (i == correctChoice) {
                colorGamePiece = new ColorGamePiece(i, trueSize, red + colorDifference,
                        green + colorDifference, blue + colorDifference, true);
            } else {
                colorGamePiece = new ColorGamePiece(i, trueSize, red, green, blue, false);
            }

            gridPieces.add(colorGamePiece);
        }

    }

    public static int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    public void updateTrueSize() {
        int padding = convertDpToPixels(8, context);
        trueSize = (convertDpToPixels(width, context) - ((numberOfColumns + 1) * padding)) / numberOfColumns;
        if (!hasPadding) {
            trueSize = (convertDpToPixels(width, context) - (2 * padding)) / numberOfColumns;
        }
    }


    public void updateGamePieceSizes() {
        updateTrueSize();

        ColorGamePiece colorGamePiece;
        for (int i = 0; i < (numberOfColumns * numberOfColumns); i++) {
            colorGamePiece = (ColorGamePiece) gridPieces.get(i);
            colorGamePiece.setSize(trueSize);
        }
    }

    public void updateGamePieceDifferences() {
        ColorGamePiece colorGamePiece;
        for (int i = 0; i < (numberOfColumns * numberOfColumns); i++) {
            colorGamePiece = (ColorGamePiece) gridPieces.get(i);

            if(colorGamePiece.isCorrect()) {
                colorGamePiece.setColor(Math.min(Color.red(colorGamePiece.getColor()) + colorDifference, 255),
                        Math.min(Color.green(colorGamePiece.getColor()) + colorDifference, 255),
                        Math.min(Color.blue(colorGamePiece.getColor()) + colorDifference, 255));
            }
        }
    }

}
