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
        for (int i = 1; i <= myColorGameBoard.numOfBlocks; i++) {
            paintMain.setColor(Color.argb(64, r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            Rect rect = new Rect(r.nextInt(getWidth()/2), r.nextInt(getHeight()/2), r.nextInt(1000), r.nextInt(1900));
            canvas.drawRect(rect, paintMain);
        }
    }
}
