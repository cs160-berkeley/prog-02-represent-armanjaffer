package com.cs160.arman.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class input_method extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String use_geo_location = "current";
        setContentView(R.layout.activity_input_method);
        Button geo = (Button) findViewById(R.id.button);
        Button zip = (Button) findViewById(R.id.button1);

        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), candidate_list.class);
                intent.putExtra("type", "current");
                v.getContext().startActivity(intent);
            }
        });

        zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), zip.class);
                intent.putExtra("type", "zip");
                v.getContext().startActivity(intent);
            }
        });





    }


}
