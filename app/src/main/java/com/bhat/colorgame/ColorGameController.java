package com.bhat.colorgame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mark Schillaci on 7/18/15.
 */
public class ColorGameController {

    private ArrayList<ColorGamePiece> gridPieces;

    private int score = 0;
    private int width;
    private int level = 1;
    private int colorDifference = 105;

    private int numberOfColumns = 2;

    Random myRandomGenerator = new Random();

    public ColorGameController(int width) {
        this.width = width;
        gridPieces = new ArrayList<ColorGamePiece>();
        createGamePieces();
    }

    public ArrayList<ColorGamePiece> getGridPieces() {
        return gridPieces;
    }

    public void levelUp(){
        level++;
        colorDifference = Math.max(5, colorDifference - 5);
        gridPieces = new ArrayList<ColorGamePiece>();
        createGamePieces();
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void createGamePieces(){
        numberOfColumns = 9;

        if(level < 30){
            numberOfColumns = (int) Math.floor(-0.006063*Math.pow(level,2) + 0.3827*level + 2.421);;
        }

        int red   = myRandomGenerator.nextInt(255 - colorDifference);
        int green = myRandomGenerator.nextInt(255 - colorDifference);
        int blue  = myRandomGenerator.nextInt(255 - colorDifference);

        int correctChoice = myRandomGenerator.nextInt(numberOfColumns*numberOfColumns)+1;

        int padding = 8;
        int trueSize = (width - (2 * padding)) / numberOfColumns;

        ColorGamePiece colorGamePiece;
        for (int i = 1; i <= (numberOfColumns * numberOfColumns); i++) {

            if (i == correctChoice) {
                colorGamePiece = new ColorGamePiece(i, trueSize, red + colorDifference,
                        green + colorDifference, blue + colorDifference, true, true);
            } else {
                colorGamePiece = new ColorGamePiece(i, trueSize, red, green, blue, false, true);
            }

            gridPieces.add(colorGamePiece);
        }


    }


}
