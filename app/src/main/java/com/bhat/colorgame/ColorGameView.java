package com.bhat.colorgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;

/**
 * Created by markschillaci1 on 7/4/15.
 */
public class ColorGameView extends View {

    Paint paintMain = new Paint();
    Paint paintSpecial = new Paint();

    Random r = new Random();

    ColorGameBoard myColorGameBoard;

    public ColorGameView(Context context, ColorGameBoard cgb) {
        super(context);
        myColorGameBoard = cgb;
    }

    @Override
    public void onDraw(Canvas canvas) {
       // canvas.drawARGB(255,0,0,0);

        // Get the current level
        int n = myColorGameBoard.numOfBlocks;
        int diff = myColorGameBoard.RGB_difference;
        int red = r.nextInt(255 - diff);
        int grn = r.nextInt(255 - diff);
        int blu = r.nextInt(255 - diff);

        paintMain.setColor(Color.argb(255, red, grn, blu));
        paintSpecial.setColor(Color.argb(255, red+diff, grn+diff, blu+diff));

        int myI = r.nextInt(n)+1;
        int myJ = r.nextInt(n)+1;

        int padding = 15;
        int trueSize = (getWidth() - (n+1)*padding) / n;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int left = j*padding + (j-1)*trueSize;
                int top = (getHeight()/6) + (i-1)*padding + (i-1)*trueSize;

                Rect rect = new Rect(left, top, left+trueSize, top+trueSize);
                if((myI==i)&&(myJ==j)){
                    canvas.drawRect(rect, paintSpecial);
                } else {
                    canvas.drawRect(rect, paintMain);
                }
            }
        }


    }
}
