package com.bhat.colorgame;

import android.graphics.Color;

/**
 * Created by Mark Schillaci on 7/18/15.
 */
public class ColorGamePiece {

    private int position;
    private int size;
    private int color;
    private boolean isCorrect;
    private boolean hasPadding;

    public ColorGamePiece(int position, int size, int red, int green, int blue, boolean isCorrect, boolean hasPadding) {
        this.position = position;
        this.size = size;
        this.color = Color.argb(255, red, green, blue);
        this.isCorrect = isCorrect;
        this.hasPadding = hasPadding;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int red, int green, int blue) {
        this.color = Color.argb(255, red, green, blue);
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean hasPadding() {
        return hasPadding;
    }

    public void setHasPadding(boolean hasPadding) {
        this.hasPadding = hasPadding;
    }
}
