package com.bhat.colorgame;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by Mark Schillaci on 7/18/15.
 */
public class ColorGameActivityFragment extends Fragment implements AdapterView.OnItemClickListener{

    View myView;
    GridView myGridView;

    ColorGameController myColorGameController;
    ColorGameGridAdapter myColorGameGridAdapter;

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
        myGridView.setVerticalSpacing(ColorGameController.convertDpToPixels(8,getActivity()));
        myGridView.setHorizontalSpacing(ColorGameController.convertDpToPixels(8,getActivity()));
        myGridView.setOnItemClickListener(this);

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

        myColorGameController.levelUp();
        myGridView.setNumColumns(myColorGameController.getNumberOfColumns());
        myColorGameGridAdapter.setGridPieces(myColorGameController.getGridPieces());


    }
}
