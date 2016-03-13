package com.cs160.arman.represent;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class detailed_view extends AppCompatActivity {

    final ArrayList<HashMap<String, String>> details = new ArrayList<HashMap<String, String>>();
    private StringBuilder committees = new StringBuilder();
    private StringBuilder bills = new StringBuilder();
    private TextView tv_name;
    private TextView tv_term;
    private TextView tv_party;
    private TextView tv_com_det;
    private TextView tv_bill_det;
    private ImageView img;
    private ScrollView apricot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        Intent intent = getIntent();
        ArrayList<String> bad_boi = intent.getStringArrayListExtra("lst");

        tv_name = (TextView) findViewById(R.id.name);
//        tv_party = (TextView) findViewById(R.id.party);
        tv_term = (TextView) findViewById(R.id.term_end);
        tv_com_det = (TextView) findViewById(R.id.com_det);
        tv_bill_det  = (TextView) findViewById(R.id.bills_contents);
        img = (ImageView) findViewById(R.id.imageview
        );
        apricot = (ScrollView) findViewById(R.id.layout);

        String str_name = bad_boi.get(0);
        String str_party = bad_boi.get(1);
        String str_email = bad_boi.get(2);
        String str_website = bad_boi.get(3);
        String str_id = bad_boi.get(4);
        String term_end = bad_boi.get(5);
//        if (str_party.equals("Republican Party")){
//            apricot.setBackgroundColor(0xFFE16262);
//        }else{
//            apricot.setBackgroundColor(0xFF4180D6);
//        }
        connect_thing(str_id);

        tv_name.setText(str_name);
//        tv_party.setText(str_party);
        tv_term.setText(str_party+ "\n\n\n\n\nTerm ends on:\n" + term_end);
        String url = "https://theunitedstates.io/images/congress/225x275/"+str_id+".jpg";
        Picasso.with(getApplicationContext()).load(url).into(img);

    }


    public void connect_thing(String bio_id) {
        String api_num = "&apikey=3d4edffb86e547eabb5402be5f52e315";
        // Gets the URL from the UI's text field.


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        System.out.println("GOT PAST CONNECTIVITY MANAGER");
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (!networkInfo.equals(null) && networkInfo.isConnected()) {
            String[] urls = {
                    "http://congress.api.sunlightfoundation.com/committees?member_ids="+ bio_id + "&apikey=3d4edffb86e547eabb5402be5f52e315",
                    "http://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bio_id + "&apikey=3d4edffb86e547eabb5402be5f52e315",
//                    "https://theunitedstates.io/images/congress/225x275/"+ bio_id +".jpg"
            };
            new DownloadWebpageTask().execute(urls);
        } else {
            System.out.println("No network connection available.");
        }
    }




    private String downloadUrl(String myurl) throws IOException {
        try {
            System.out.println("IN DOQNLODASD URL");

            URL url = new URL(myurl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            System.out.println("PAST SETTING URL CONNECTIOn");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("READING LINES");
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                System.out.println(stringBuilder.toString());
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] urls) {
            System.out.println("IN DO IN BACKGROUND");
            // params comes from the execute() call: params[0] is the url.
            try {
                StringBuilder sb = new StringBuilder();
                for (String s : urls){
                    System.out.println("THIS SHIT WAS DOWNLOADED");
                    System.out.println(s);
                    sb.append(downloadUrl(s));
                }
                return sb.toString();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //do something here
            String[] json_strings = result.split("\n");
            System.out.println("two json length " + Integer.toString(json_strings.length));
            try {
                if (json_strings.length == 2) {
                    System.out.println("INSIDE 2 check");

                    JSONArray json_array_com = new JSONObject(json_strings[0]).getJSONArray("results");
                    for (int i = 0; i < json_array_com.length(); i++) {
                        System.out.println(i);

                        JSONObject com_obj = json_array_com.getJSONObject(i);
                        try {
                            committees.append(Integer.toString(i+1) + ".  " + com_obj.getString("name") + "\n");
                        } catch (Exception e) {
                            System.out.println("SHIT");
                        }
                    }

                    System.out.println("GOT TO BILLS");
                    JSONArray json_array_bil = new JSONObject(json_strings[1]).getJSONArray("results");
                    for (int i = 0; i < json_array_bil.length(); i++) {
                        System.out.println(i);
                        JSONObject bil_obj = json_array_bil.getJSONObject(i);
                        try {
                            if (!bil_obj.getString("short_title").equals(bil_obj.getString("apple"))){
                                bills.append(bil_obj.getString("introduced_on") + ": " + bil_obj.getString("short_title") + " - " +
                                        bil_obj.getString("official_title") + "\n\n");
                            }else{
                                bills.append(bil_obj.getString("introduced_on") + ": " +
                                        bil_obj.getString("official_title") + "\n\n");
                            }
                        } catch (Exception e) {
                            try {
                                bills.append(bil_obj.getString("introduced_on") + ": " +
                                        bil_obj.getString("official_title") + "\n\n");
                            } catch (Exception e1) {
                                System.out.println("SHITSHIT");
                            }
                        }
                    }
                }
            }catch (Exception e){
                System.out.println("BIG FUCK UP");
            }
            System.out.println(committees.toString());
            System.out.println(bills.toString());
            shitTheBed();
            System.out.println("GOT TO END OF FUNC");
        }

    }


    private void shitTheBed(){
        tv_bill_det.setText(bills.toString());
        tv_com_det.setText(committees.toString());
    }

}
