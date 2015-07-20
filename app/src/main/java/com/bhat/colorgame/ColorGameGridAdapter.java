package com.bhat.colorgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/**
 * Created by Mark Schillaci on 7/18/15.
 */
public class ColorGameGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ColorGamePiece> gridPieces;

    public ColorGameGridAdapter(Context context, LayoutInflater layoutInflater, ArrayList<ColorGamePiece> gridPieces) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.gridPieces = gridPieces;
    }

    public void setGridPieces(ArrayList<ColorGamePiece> gridPieces) {
        this.gridPieces = gridPieces;
    }

    @Override
    public int getCount() {
        return gridPieces.size();
    }

    @Override
    public Object getItem(int position) {
        return gridPieces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View pieceView;
        ColorGamePiece colorGamePiece = gridPieces.get(position);

        if (view == null) {
            pieceView = new View(this.context);
        } else {
            pieceView = view;
        }
        int size = colorGamePiece.getSize();
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(size, size);

        pieceView.setLayoutParams(layoutParams);
        pieceView.setBackgroundColor(colorGamePiece.getColor());

        return pieceView;
    }
}