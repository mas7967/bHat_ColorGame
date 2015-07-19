package com.bhat.colorgame;

import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ColorGameActivityFragment extends Fragment implements AdapterView.OnItemClickListener{

    ColorGameBoard colorGameBoard;

    View myView;
    ColorGameView myCGV;

    GridView myGridView;
    ColorGameGridAdapter myColorGameGridAdapter;

    ColorGameController myColorGameController;


    public ColorGameActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_color_game, container, false);

        myGridView = (GridView) myView.findViewById(R.id.color_game_grid);

        myColorGameGridAdapter = new ColorGameGridAdapter(getActivity(), inflater, myColorGameController.getGridPieces());
        myGridView.setAdapter(myColorGameGridAdapter);

        myGridView.setNumColumns(myColorGameController.getNumberOfColumns());
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        myGridView.setVerticalSpacing(16);
        myGridView.setHorizontalSpacing(16);
        myGridView.setOnItemClickListener(this);

        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);

        myColorGameController = new ColorGameController(size.x-100);

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

        myColorGameController.levelUp();
        myGridView.setNumColumns(myColorGameController.getNumberOfColumns());
        myColorGameGridAdapter.setGridPieces(myColorGameController.getGridPieces());


    }
}
