package com.cs160.arman.represent;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.CardFrame;
import android.support.wearable.view.CardScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Arman on 3/1/16.
 */
public class ArmanCardFragment extends CardFragment {

    private int _col = 0;
    private int _row = 0;
    private ArrayList<String> bad_boi_bob;
    private String add;
    private String scr;

    public static Fragment newInstance(int row, int col, ArrayList<String> king_kong, String ad, String score, String winner){
        Bundle bun = new Bundle();
        bun.putInt("row", row);
        bun.putInt("col", col);
        bun.putString("ad", ad);
        bun.putString("score", score);
        bun.putStringArrayList("data", king_kong);
        bun.putString("winner", winner);
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
        this._row = row;
        this.add = getArguments().getString("ad");
        this.scr = getArguments().getString("score");
        String winner = getArguments().getString("winner");
        this.bad_boi_bob = getArguments().getStringArrayList("data");


        int layout = R.layout.frag_0;
        int ln = bad_boi_bob.size();
        if (this._col == ln || bad_boi_bob.get(this._col).split(",").length < 4){
            Log.i("WE ARE IN", "ROW 1");
            layout = R.layout.frag_3;
        }
        View result = inflater.inflate(layout, container, false);

        if (this._col == ln || bad_boi_bob.get(this._col).split(",").length < 4){
            Log.i("NOTHING IS HAPPENING", "WHAT SHOULD WE DO");
            TextView score_tv = (TextView) result.findViewById(R.id.chicken1);
            TextView ad_tv = (TextView) result.findViewById(R.id.chicken2);
            score_tv.setText(this.scr);
            ad_tv.setText(this.add);
            CardScrollView ly = (CardScrollView) result.findViewById(R.id.card_scroll_view);
            if (winner == "romney"){
                ly.setBackgroundColor(0xFFf47171);
            }else{
                ly.setBackgroundColor(0xFF4180D6);
            }
        }else{
            CardScrollView csv = (CardScrollView) result.findViewById(R.id.card_scroll_view);
            TextView tv1 = (TextView) result.findViewById(R.id.tv1);
            TextView tv2 = (TextView) result.findViewById(R.id.tv2);
            CardFrame cf = (CardFrame) result.findViewById(R.id.cardframe);

            String bob = bad_boi_bob.get(this._col);
            System.out.println(bob);

            String[] individual = bob.split(",");
            System.out.println(Arrays.toString(individual));
            //String[] arr = {name, party, email, website, id, term_end};
            String name = individual[0];
            String party = individual[1];
            String id = individual[4];
            tv1.setText(name);
            tv2.setText(party);
            if (party.equals("Democratic Party")){
                tv2.setTextColor(0xFF4180D6);
                csv.setBackgroundColor(0xFF4180D6);
//                cf.setBackgroundColor(0xFF4180D6);
            }else{
                tv2.setTextColor(0xFFf47171);
                csv.setBackgroundColor(0xFFf47171);
//                cf.setBackgroundColor(0xFFf47171);
            }

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("content", bad_boi_bob.get(_col));
                    sendIntent.putExtra("number", Integer.toString(_col));
                    getActivity().startService(sendIntent);
                    // put your onClick logic here
                }
            });
        }


        return result;
    }


}