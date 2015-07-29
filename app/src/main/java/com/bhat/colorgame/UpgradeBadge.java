package com.bhat.colorgame;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mark Schillaci on 7/28/15.
 */
public class UpgradeBadge {

    private Context context;
    private View view;
    private int remainingUpgrades;
    private int id;

    private TextView textView;

    public UpgradeBadge(Context context, View view, int remainingUpgrades, int id) {
        this.context = context;
        this.view = view;
        this.remainingUpgrades = remainingUpgrades;
        this.id = id;
        textView = (TextView) view.findViewById(id);
    }

    public int getRemainingUpgrades() {
        return remainingUpgrades;
    }

    public void useUpgrade() {
        this.remainingUpgrades--;
        textView.setText(Integer.toString(remainingUpgrades));

        if(remainingUpgrades<=0) {
            textView.setBackground(context.getResources().getDrawable(R.drawable.upgrade_badge_background_empty));
        } else {
            textView.setBackground(context.getResources().getDrawable(R.drawable.upgrade_badge_background));
        }
    }
}
