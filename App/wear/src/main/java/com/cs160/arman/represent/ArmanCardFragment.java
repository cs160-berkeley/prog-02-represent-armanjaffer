package com.cs160.arman.represent;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Arman on 3/1/16.
 */
public class ArmanCardFragment extends CardFragment {

    private int _col = 0;

    public static Fragment newInstance(int row, int col){
        Bundle bun = new Bundle();
        bun.putInt("row", row);
        bun.putInt("col", col);
        Fragment f = new ArmanCardFragment();
        f.setArguments(bun);
        return f;
    }

//    public boolean isFragmentUIActive() {
//        return isAdded() && !isDetached() && !isRemoving();
//    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int row = getArguments().getInt("row");
        int col = getArguments().getInt("col");
        this._col = col;
        int layout = R.layout.frag_0;
        if (col == 0){
            layout = R.layout.frag_0;
        }else if (col == 1){
            layout = R.layout.frag_1;
        }else if (col == 2){
            layout = R.layout.frag_2;
        }else{
            layout = R.layout.frag_3;
        }

        View result = inflater.inflate(layout, container, false);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("number", Integer.toString(_col));
                getActivity().startService(sendIntent);
                // put your onClick logic here
            }
        });

        return result;
    }


}