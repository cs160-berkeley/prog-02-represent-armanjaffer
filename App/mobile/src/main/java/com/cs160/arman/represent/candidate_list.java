package com.cs160.arman.represent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class candidate_list extends AppCompatActivity

{

    private ArrayList<ArrayList<String>> cong_list = new ArrayList<ArrayList<String>>();
    public static ArrayList<String> pic_list = new ArrayList<String>();
    private TwitterLoginButton loginButton;
    private boolean zippy;
    private String zip_poo;
    private String glob_lat;
    private String glob_lon;
    private String county;
    private String state;
    public String FULL_AD;
    public String FULL_SCORE;
    public String WINNER = "NO";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        //do the finding of senator/representative

        Intent zip_intent = getIntent();
        System.out.println(zip_intent.getStringExtra("type"));
        if (zip_intent.getStringExtra("type").equals("current")){
            zippy = false;
            System.out.println("INNN THE MOTHERFUCKA");
            glob_lat = zip_intent.getStringExtra("lat");
            glob_lon = zip_intent.getStringExtra("lon");
            connect_thing("-1", glob_lat, glob_lon);
        }else if (zip_intent.getStringExtra("type").equals("lat")){
            zippy = false;
            glob_lat = zip_intent.getStringExtra("lat");
            glob_lon = zip_intent.getStringExtra("lon");
            System.out.println("INNN THE NEWWWWW MOTHERFUCKA");
            connect_thing("-1", glob_lat, glob_lon);
        } else {
            zippy = true;
            System.out.println("IN ELSE STATEMENT");
            String zip = zip_intent.getStringExtra("zip");
            zip_poo = zip;
            connect_thing(zip, "-1", "-1");
            System.out.println("connect thing is finished");
        }


        //iterate through


        ImageView person1 = (ImageView) findViewById(R.id.sen_1_imageview);
        ImageView person2 = (ImageView) findViewById(R.id.sen_2_image);
        ImageView person3 = (ImageView) findViewById(R.id.rep_1_image);
        ImageView person4 = (ImageView) findViewById(R.id.rep_image_2);
        ImageView person5 = (ImageView) findViewById(R.id.rep_image_3);
        ImageView person6 = (ImageView) findViewById(R.id.rep_image_4);
        ImageView person7 = (ImageView) findViewById(R.id.rep_image_5);




        person1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                int len = cong_list.size();
                intent.putExtra("lst", cong_list.get(0));
                v.getContext().startActivity(intent);
            }
        });

        person2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                int len = cong_list.size();
                intent.putExtra("number", 1);
                intent.putExtra("lst", cong_list.get(1));
                v.getContext().startActivity(intent);
            }
        });

        person3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                int len = cong_list.size();
                intent.putExtra("number", 2);
                intent.putExtra("lst", cong_list.get(2));
                v.getContext().startActivity(intent);
            }
        });

        person4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                int len = cong_list.size();
                intent.putExtra("number", 3);
                intent.putExtra("lst", cong_list.get(3));
                v.getContext().startActivity(intent);
            }
        });

        person5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                int len = cong_list.size();
                intent.putExtra("number", len);
                intent.putExtra("lst", cong_list.get(4));
                v.getContext().startActivity(intent);
            }
        });

        person6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                int len = cong_list.size();
                intent.putExtra("number", len);
                intent.putExtra("lst", cong_list.get(5));
                v.getContext().startActivity(intent);
            }
        });

        person7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailed_view.class);
                int len = cong_list.size();
                intent.putExtra("number", len);
                intent.putExtra("lst", cong_list.get(6));
                v.getContext().startActivity(intent);
            }
        });





    }


    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("election_results_2012.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    private void getVotes(String ct, String st){
        String s = loadJSONFromAsset();
        try {
            JSONObject jObject2 = new JSONObject(s);
            Iterator<?> keys2 = jObject2.keys();
            while (keys2.hasNext()) {
                String key = (String) keys2.next();
                String[] kys = key.split(", ");
                System.out.println(ct);
                System.out.println(kys[0]);
                if (kys[0].equals(ct) && kys[1].equals(st)) {
                    JSONObject val = jObject2.getJSONObject(key);
                    try{
                        double obama = Double.parseDouble(val.getString("obama"));
                        double romney = Double.parseDouble(val.getString("romney"));
                        LinearLayout ly = (LinearLayout) findViewById(R.id.WHOLE_CL);
                        if (obama > romney){
                            WINNER = "obama";
                            ly.setBackgroundColor(0xFF4180D6);
                        }else{
                            WINNER = "romney";
                            ly.setBackgroundColor(0xFFff6666);
                        }
                    }catch (Exception e){
                        System.out.println("coudn't parse");
                    }


                    FULL_AD = ct + ", " + st;
                    FULL_SCORE = "Obama (" + val.getString("obama") + ") to Romney (" + val.getString("romney") + ")";
                    break;
                }
            }
        } catch (JSONException e) {
            Log.d("T", "JSON exception");
        }
    }




        private void find_zip(String zip, String lat, String lon){
            String geo_url = "https://maps.googleapis.com/maps/api/geocode/json?";
            String geo_key = "AIzaSyC7bqp8SB-DZy8YNrWtA3BQqBXuqH_Xae8";
            String url_string;
            if (zippy){
                url_string = geo_url + "&address=" + zip + "&key=" + geo_key;
            }else{
                url_string = geo_url + "latlng=" + lat + "," + lon+ "&key=" + geo_key;
            }try{
                System.out.println("GOING INTO SPECIAL THING");
                String results = downloadUrl(url_string);
                JsonElement object = new JsonParser().parse(results);
                Log.d("WE DONT", "GET IT");
                JsonArray arry = object.getAsJsonObject().get("results").getAsJsonArray();
                JsonObject arry2 = arry.get(0).getAsJsonObject();
                Log.i("THIS IS IT", "HELL HOLE3");
                JsonArray objz = arry2.get("address_components").getAsJsonArray();
                Log.i("THIS IS IT", "HELL HOLE4");
                for (int p = 0; p < objz.size(); p++) {
                    JsonObject temp = objz.get(p).getAsJsonObject();
                    String st = temp.get("types").getAsJsonArray().get(0).toString();
                    Log.i("WHT IT BE", st);
                    if (st.toString().equals("\"administrative_area_level_2\"")) {
                        county = temp.get("long_name").getAsString();
                        Log.i("WE OUT HERE", "LOOK AT MEEE");
                        Log.i("WE OUT HERE", county);
                    }
                    if (st.toString().equals("\"administrative_area_level_1\"")) {
                        state = temp.get("short_name").getAsString();
                        Log.i("WE OUT HERE", state);
                    }
                }
            }catch (Exception e){
                System.out.println("THIS IS A MEGA SHIT");
            }
        }











    public void connect_thing(String zip, String lat, String longit) {
        String api_num = "&apikey=3d4edffb86e547eabb5402be5f52e315";
        // Gets the URL from the UI's text field.
        String url_string;
        if (zip == "-1"){
            System.out.println("CONSTRUCTING LAT LONG");
            url_string = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + lat + "&longitude=" + longit + api_num;
        }else{
            System.out.println("IN URL FOR ZIP");
            url_string = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zip + api_num;

        }
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        System.out.println("GOT PAST CONNECTIVITY MANAGER");
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        System.out.println("GOT ACTIVE NETWORK INFO");
        System.out.println(networkInfo);
        System.out.println(networkInfo.isConnected());
        if (!networkInfo.equals(null) && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(url_string);
            System.out.println("PAST EXECUTE");
        } else {
            System.out.println("No network connection available.");
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
                find_zip(zip_poo, glob_lat, glob_lon);
                return sb.toString();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //do something here
            String[] two_json = result.split("\n");
            System.out.println(result);
            try{
                JSONObject json_object = new JSONObject(result);
                JSONArray jarray = json_object.getJSONArray("results");
                int lens = json_object.getInt("count");
                System.out.println("GOING INTO 4 LOOOPPPPPP");
                for (int i = 0; i < lens; i++){
                    JSONObject objects = jarray.getJSONObject(i);
                    String tit;
                    if (objects.getString("title").equals("Rep")){
                        tit = "Representative";
                    }else{
                        tit = "Senator";
                    }
                    String name = tit + " " + objects.getString("first_name") + " " +
                            objects.getString("last_name");
                    System.out.println("name " + name);
                    String party;
                    if (objects.getString("party").equals("R")){
                        party = "Republican Party";
                        System.out.println("REPUB");
                    }else{
                        party = "Democratic Party";
                        System.out.println("DEMOCRAT ");
                    }
                    String email = objects.getString("oc_email");
                    System.out.println("oc_email " + email);
                    String website = objects.getString("website");
                    System.out.println("website " + website);
                    String id = objects.getString("bioguide_id");
                    System.out.println("id " + id);

                    set_pics(i, id);

                    String term_end = objects.getString("term_end");
                    System.out.println("term end " + term_end);
                    String tweet_str = objects.getString("twitter_id");
                    String[] arr = {name, party, email, website, id, term_end, tweet_str};
                    cong_list.add(new ArrayList<String>(Arrays.asList(arr)));
                    System.out.println(cong_list);
                }
                set_fields();
                getVotes(county, state);
                send_intent();
            }catch (Exception e) {
                System.out.println("arman you are lazy af");
            }
        }

    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
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

    private void set_fields(){
        int len = cong_list.size();
        TextView[][] tvs = init_tvs();
        ImageView[] imgs = init_imgs();
        for (int i = 0; i < len; i++){
            ArrayList<String> view_contents = cong_list.get(i);
            String[] filtered = {view_contents.get(0), view_contents.get(2), view_contents.get(3)};
            int filter_len = filtered.length;
            for (int j = 0; j < filter_len ; j++){
                if (j == 0){
                    if (cong_list.get(i).get(1).equals("Republican Party")){
                        tvs[i][0].setTextColor(0xFFDC143C);
                        tvs[i][j].setText(filtered[j] + " (R)");
                    }else if(cong_list.get(i).get(1).equals("Democratic Party")){
                        tvs[i][0].setTextColor(0xFF3232FF);
                        tvs[i][j].setText(filtered[j] + " (D)");

                    }else{
                        tvs[i][0].setTextColor(0x00000000);
                        tvs[i][j].setText(filtered[j] + " (I)");

                    }
                }else{
                    tvs[i][j].setText(filtered[j]);
                    tvs[i][j].setTextColor(0x0);
                }
            }
            set_img(view_contents.get(4), view_contents.get(0), imgs[i]);
            set_bg(view_contents.get(1), i);
            set_visible(i);
            tweet_shit(view_contents.get(6), i);
        }
    }

    private void set_pics(int i, String id){
        ImageView[] aps = init_imgs();
        ImageView iv = aps[i];
        String url = "https://theunitedstates.io/images/congress/225x275/"+id+".jpg";
        Picasso.with(getApplicationContext()).load(url).into(iv);

    }

    private TextView[][] init_tvs(){
          TextView[][] tvs = {
                {
                        (TextView) findViewById(R.id.sen_1),
                        (TextView) findViewById(R.id.sen_1_website),
                        (TextView) findViewById(R.id.party1)
                },
                {
                        (TextView) findViewById(R.id.sen_2),
                        (TextView) findViewById(R.id.sen_2_email),
                        (TextView) findViewById(R.id.sen_2_website),
                },
                {
                        (TextView) findViewById(R.id.rep_1),
                        (TextView) findViewById(R.id.rep_1_email),
                        (TextView) findViewById(R.id.rep_1_website),
                },
                {
                          (TextView) findViewById(R.id.rep_2),
                          (TextView) findViewById(R.id.rep_2_email),
                          (TextView) findViewById(R.id.rep_2_website),
                },
                {
                        (TextView) findViewById(R.id.rep_3),
                        (TextView) findViewById(R.id.rep_3_email),
                        (TextView) findViewById(R.id.rep_3_website),
                },
                {
                          (TextView) findViewById(R.id.rep_4),
                          (TextView) findViewById(R.id.rep_4_email),
                          (TextView) findViewById(R.id.rep_4_website),
                },
                {
                          (TextView) findViewById(R.id.rep_5),
                          (TextView) findViewById(R.id.rep_5_email),
                          (TextView) findViewById(R.id.rep_5_website),
                }
        };
        return tvs;
    }

    private ImageView[] init_imgs(){

        ImageView[] imgs = {
                (ImageView) findViewById(R.id.sen_1_imageview),
                (ImageView) findViewById(R.id.sen_2_image),
                (ImageView) findViewById(R.id.rep_1_image),
                (ImageView) findViewById(R.id.rep_image_2),
        };
        return imgs;
    }

    private LinearLayout[] init_layouts(){
        LinearLayout[] apple ={
            (LinearLayout) findViewById(R.id.layout_1),
            (LinearLayout) findViewById(R.id.layout_2),
            (LinearLayout) findViewById(R.id.layout_3),
            (LinearLayout) findViewById(R.id.layout_4),
            (LinearLayout) findViewById(R.id.layout_5),
            (LinearLayout) findViewById(R.id.layout_6),
            (LinearLayout) findViewById(R.id.layout_7)
        };
        return apple;
    }

    private void set_img(String bio_id, String name, ImageView iv){
        try {
            String s_url = "https://theunitedstates.io/images/congress/450x550/" + bio_id + ".jpg";
            InputStream is = (InputStream) new URL(s_url).getContent();
            Drawable d = Drawable.createFromStream(is, "name");
            iv.setBackground(d);

        } catch (Exception e) {
            System.out.println("FUCCLLLKKK");
        }
    }

    private void set_bg(String party, int i){
        LinearLayout[] layouts = init_layouts();
        if (party.equals("Republican Party")){
            layouts[i].setBackgroundColor(0xFFE16262);
        }else{
            layouts[i].setBackgroundColor(0xFF4180D6);
        }
        layouts[i].setBackgroundColor(0xFFFFFFFF);
    }

    private void send_intent(){
        System.out.println("BOUTTA CREATE THE INTENT");
        Intent intent = new Intent(this, PhoneToWatchService.class);
        StringBuilder sb = new StringBuilder();
        for (ArrayList<String> s_a : cong_list){
            for (String s : s_a){
                sb.append(s);
                sb.append(",");
            }
            sb.append("%");
        }
        intent.putExtra("cong", sb.toString() + "%" + WINNER + "%" + FULL_AD + "%" + FULL_SCORE);
        System.out.println("county = " + county);
        System.out.println(FULL_AD);
        System.out.println(FULL_SCORE);
        System.out.println("BOUTTA SEND THE GOODS TO PHONE TO WATCH");
        startService(intent);
    }


    private void set_visible(int i){
        int[] lays = {
                R.id.layout_1,
                R.id.layout_2,
                R.id.layout_3,
                R.id.layout_4,
                R.id.layout_5,
                R.id.layout_6,
                R.id.layout_7,
        };

        LinearLayout lay = (LinearLayout) findViewById(lays[i]);
        lay.setVisibility(View.VISIBLE);
    }



    public int[] ret_ly(){
        int[] lays = {
                R.id.layout_1,
                R.id.layout_2,
                R.id.layout_3,
                R.id.layout_4,
                R.id.layout_5,
                R.id.layout_6,
                R.id.layout_7,
        };
        return lays;
    }



    private void tweet_shit(String tweet_str, int i){
        // TODO: Use a more specific parent
        final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();
        // TODO: Base this Tweet ID on some data from elsewhere in your app
        long tweetId = 631879971628183552L;


        int shit = ret_ly()[i];
        final LinearLayout ly = (LinearLayout) findViewById(shit);
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService search = twitterApiClient.getStatusesService();

        search.userTimeline(null, tweet_str, null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                TweetView tweetView = new TweetView(candidate_list.this, result.data.get(0));
//                set_the_big_shit(result.data.get(0).user.profileImageUrl);
                ly.addView(tweetView);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Load Tweet failure", exception);
            }
        });


    }



    public void set_the_big_shit(String res){

        System.out.println("IN SET BIG SHIT");
        pic_list.add(res);
        System.out.println("THIS IS THE SIZE" + Integer.toString(pic_list.size()));
        ImageView[] ivs = init_imgs();
        try {
            downloadUrl_aps(res, ivs[pic_list.size() - 1]);
        }catch(Exception e){
            System.out.println("THIS IS THE BITMAP FUCK UP");
        }
    }


    private void downloadUrl_aps(String myurl, ImageView apple) throws IOException {
        // Do some validation here
        Picasso.with(getApplicationContext()).load(myurl).into(apple);
    }









}


