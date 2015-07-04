package com.bhat.colorgame;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;


/**
 * A placeholder fragment containing a simple view.
 */
public class ColorGameActivityFragment extends Fragment implements OnTouchListener{

    ColorGameBoard colorGameBoard;

    View myView;
    ColorGameView myCGV;

    public ColorGameActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_color_game, container, false);

        colorGameBoard = new ColorGameBoard();

        RelativeLayout relativeLayout = (RelativeLayout) myView.findViewById(R.id.cgv);
        myCGV = new ColorGameView(getActivity(), colorGameBoard);

        relativeLayout.addView(myCGV);

        myCGV.setOnTouchListener(this);

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public boolean onTouch(View v, MotionEvent event) {
        colorGameBoard.setNumberOfBlocks(colorGameBoard.numOfBlocks + 1);

        myCGV.invalidate();

        return false;
    }
}
