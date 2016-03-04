package com.cs160.arman.represent;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class detailed_view extends AppCompatActivity {

    final ArrayList<HashMap<String, String>> details = new ArrayList<HashMap<String, String>>();


    private void hardcode(){
        HashMap<String, String> h1 = new HashMap<String, String>();
        h1.put("name", "Sen. Barbara Boxer");
        h1.put("party", "Democrat");
        h1.put("email", "bigb@congress.com");
        h1.put("website", "www.barbaraboxer.com");
        h1.put("term", "January 20th, 2017");
        h1.put("committees", "Veterans Affairs, Foreign Relations, Budget");
        h1.put("bills", "Clean Water Act, 4/10/15 \nTPP Bill, 11/28/15 \nDream Act, 1/23/16");
        h1.put("image", Integer.toString(R.drawable.sen_boxer));

        HashMap<String, String> h2 = new HashMap<String, String>();
        h2.put("name", "Sen. Dianne Feinstein");
        h2.put("party", "Democrat");
        h2.put("email", "diannefeinstein@congress.com");
        h2.put("website", "www.feinstein.senate.gov");
        h2.put("term", "January 20th, 2017");
        h2.put("committees", "Veterans Affairs, Foreign Relations, Budget");
        h2.put("bills", "Flint Water Act, 4/10/15 \nTPP Bill, 11/28/15 \n Factory Farming Bill, 1/23/16");
        h2.put("image", Integer.toString(R.drawable.sen_fein));

        HashMap<String, String> h3 = new HashMap<String, String>();
        h3.put("name", "Rep. Darrell Issa");
        h3.put("party", "Republican");
        h3.put("email", "darrellissa@congress.com");
        h3.put("website", "www.darrellissa.com");
        h3.put("term", "January 20th, 2017");
        h3.put("committees", "Judiciary, Foreign Affairs");
        h3.put("bills", "Criminal Alien Accountability Act, 2/28/16 \n Fallen Heroes Act of 2016, 11/28/15 \n Factory Farming Bill, 1/23/16");
        h3.put("image", Integer.toString(R.drawable.issa));


        details.add(h1);
        details.add(h2);
        details.add(h3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Intent intent = getIntent();
        int num = Integer.valueOf(intent.getStringExtra("number"));
        hardcode();
        HashMap<String, String> h = details.get(0);
        if (num < details.size() && num > 0){
            h = details.get(num);
        }

        TextView name = (TextView) findViewById(R.id.textView);
        TextView party = (TextView) findViewById(R.id.textView1);
        TextView email = (TextView) findViewById(R.id.textView2);
        TextView website = (TextView) findViewById(R.id.textView3);
        TextView term = (TextView) findViewById(R.id.textView4);
        TextView committees = (TextView) findViewById(R.id.textView5);
        TextView bills = (TextView) findViewById(R.id.textView6);
        ImageView picture = (ImageView) findViewById(R.id.imageView);


        name.setText(h.get("name"));
        party.setText(h.get("party"));
        email.setText(h.get("email"));
        website.setText(h.get("website"));
        term.setText("Term ends on " + h.get("term"));
        committees.setText("Committees:\n" + h.get("committees"));
        bills.setText("Bills sponsored:\n" + h.get("bills"));


        picture.setImageResource(Integer.valueOf(h.get("image")));
        View lay = (View) findViewById(R.id.layout);
        if (h.get("party") == "Democrat"){
            System.out.println("in blue");
            lay.setBackgroundColor(0xFF4180D6);
        }else if (h.get("party") == "Republican"){
            System.out.println("in red");
            lay.setBackgroundColor(0xFFf47171);
        }else{
            System.out.println("in white");
            lay.setBackgroundColor(0xFFFFFFFF);
        }






//        View bg_id = findViewById(R.id.editText2);
//        //background
//        if (h.get("party") == "Republican"){
//            bg_id.setBackgroundColor(0xFF0000);
//        }else{
//            bg_id.setBackgroundColor(0x0000FF);
//        }


    }
}
