package com.cs160.arman.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class zip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip);
        Button submit = (Button) findViewById(R.id.button2);
        final EditText zip = (EditText) findViewById(R.id.editText2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zip.getText().toString().length() == 5) {
                    Intent intent = new Intent(v.getContext(), candidate_list.class);
                    intent.putExtra("type", "zip");
                    intent.putExtra("zip", Integer.parseInt(zip.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            }
        });

    }
}
