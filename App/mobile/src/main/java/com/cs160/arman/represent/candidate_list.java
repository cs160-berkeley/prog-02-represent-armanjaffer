package com.cs160.arman.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class candidate_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        //do the finding of senator/representative

        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("1", "Senator Dianne Feinstein");
        sendIntent.putExtra("2", "Senator Barbara Boxer");
        sendIntent.putExtra("3", "Representative David Issa");
        System.out.println("THIS IS FUCKING IN THE FUCKING FRAME3");
        startService(sendIntent);

        Button person1 = (Button) findViewById(R.id.button);
        Button person2= (Button) findViewById(R.id.button1);
        Button person3 = (Button) findViewById(R.id.button2);

        person1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                intent.putExtra("number", "0");
                v.getContext().startActivity(intent);
            }
        });

        person2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                intent.putExtra("number", "1");
                v.getContext().startActivity(intent);
            }
        });

        person3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                intent.putExtra("number", "2");
                v.getContext().startActivity(intent);
            }
        });


    }
}
