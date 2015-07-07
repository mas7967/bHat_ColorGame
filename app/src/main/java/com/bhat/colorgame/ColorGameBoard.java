package com.bhat.colorgame;

/**
 * Created by markschillaci1 on 7/4/15.
 */
public class ColorGameBoard {

    int numOfBlocks;
    int level;
    int RGB_difference;

    public ColorGameBoard() {

        numOfBlocks = 2;
        level = 1;
        RGB_difference = 105;
    }


    public void setNumberOfBlocks(int i) {
        numOfBlocks = i;
    }

    public void levelUp() {
        level++;
        RGB_difference = Math.max(5, RGB_difference - 5);
    }
}
