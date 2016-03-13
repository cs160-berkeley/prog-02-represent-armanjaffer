package com.cs160.arman.represent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import java.util.Arrays;
import java.util.List;

public class sen_1 extends FragmentActivity implements SensorEventListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "jGy3JZRfFkBMOiBCGOdDMpyUr";
    private static final String TWITTER_SECRET = "VFnZxDUBrqF5eUvucb9URVm5OnmXNtgPl2tI75pTwWkAEWpw21 ";


    public boolean on = false;
    public static ArrayList<String> bad_boy_bob;
    public static  String full_ad;
    public static String full_score;
    public static String winner;


    /** Minimum movement force to consider. */
    private static final int MIN_FORCE = 10;

    /**
     * Minimum times in a shake gesture that the direction of movement needs to
     * change.
     */
    private static final int MIN_DIRECTION_CHANGE = 2;

    /** Maximum pause between movements. */
    private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 10000;

    /** Maximum allowed time for shake gesture. */
    private static final int MAX_TOTAL_DURATION_OF_SHAKE = 15000;

    /** Time when the gesture started. */
    private long mFirstDirectionChangeTime = 0;

    /** Time when the last movement started. */
    private long mLastDirectionChangeTime;

    /** How many movements are considered so far. */
    private int mDirectionChangeCount = 0;

    /** The last x position. */
    private float lastX = 0;

    /** The last y position. */
    private float lastY = 0;

    /** The last z position. */
    private float lastZ = 0;

    private boolean shake = false;

    @Override
    public void onSensorChanged(SensorEvent se) {
        // get sensor data
        float x = se.values[0];
        float y = se.values[0];
        float z = se.values[0];

        // calculate movement
        float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

        if (totalMovement > MIN_FORCE) {

            // get time
            long now = System.currentTimeMillis();

            // store first movement time
            if (mFirstDirectionChangeTime == 0) {
                mFirstDirectionChangeTime = now;
                mLastDirectionChangeTime = now;
            }

            // check if the last movement was not long ago
            long lastChangeWasAgo = now - mLastDirectionChangeTime;
            if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE) {

                // store movement data
                mLastDirectionChangeTime = now;
                mDirectionChangeCount++;

                // store last sensor data
                lastX = x;
                lastY = y;
                lastZ = z;

                // check how many movements are so far
                if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {

                    // check total duration
                    long totalDuration = now - mFirstDirectionChangeTime;
                    if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
                        //this is how we know there's a shake
                        shake = true;
                        shake_that();
                        resetShakeParameters();
                    }
                }

            } else {
                resetShakeParameters();
            }
        }
    }

    private void shake_that(){
        Intent intent = new Intent(this, WatchToPhoneService.class);
        intent.putExtra("number", "-1");
        intent.putExtra("content", "");
        startService(intent);
    }


    /**
     * Resets the shake parameters to their default values.
     */
    private void resetShakeParameters() {
        mFirstDirectionChangeTime = 0;
        mDirectionChangeCount = 0;
        mLastDirectionChangeTime = 0;
        lastX = 0;
        lastY = 0;
        lastZ = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    private SensorManager mSensorManager;

    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        on = true;
        System.out.println("IN ON CREATE");
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_sen_1);
        System.out.println("IN ON CREATE");
        final DotsPageIndicator mPageIndicator;
        final GridViewPager mViewPager;

        Intent poop = getIntent();
        System.out.println("GOT INTENT");
        String papa = poop.getStringExtra("CAT_NAME");


        String[] func = papa.split("%");

        full_score = func[func.length - 1];
        full_ad = func[func.length - 2];
        winner = func[func.length - 3];

        System.out.println(full_score);
        System.out.println(full_ad);

        ArrayList<String> chicken = new ArrayList<String>(Arrays.asList(papa.split("%")));
        System.out.println(chicken.get(chicken.size() - 1));
        System.out.println(chicken.get(chicken.size() - 2));
        chicken.remove(chicken.size() - 1);
        chicken.remove(chicken.size() - 1);
        chicken.remove(chicken.size() - 1);


        bad_boy_bob = chicken;
//
//        ArrayList<String> chicken_little = new ArrayList<String>();
//        for (int i = 0; i < chicken.size(); i++)
//            chicken_little.add("pres view");

        chicken.add("placeholder");
        final String[][] data = {
                chicken.toArray(new String[chicken.size()])
//                chicken_little.toArray(new String[chicken.size()])
        };

        // Get UI references
        mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        mViewPager = (GridViewPager) findViewById(R.id.pager);

        // Assigns an adapter to provide the content for this pager
        mViewPager.setAdapter(new GridPagerAdapter(getFragmentManager(), data));
        mPageIndicator.setPager(mViewPager);





        //shake
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);




    }


    @Override

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }





    private static final class GridPagerAdapter extends FragmentGridPagerAdapter {

        String[][] mData;

        private GridPagerAdapter(FragmentManager fm, String[][] data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getFragment(int row, int column) {
            System.out.println("THIS IS FULL AD" + full_ad);
            System.out.println("THIS IS FULL SCORE" + full_score);
            return ArmanCardFragment.newInstance(row, column, bad_boy_bob, full_ad, full_score, winner);
        }

        @Override
        public int getRowCount() {
            return mData.length ;
        }

        @Override
        public int getColumnCount(int row) {
            return mData[row].length;
        }
    }


    public boolean isOn(){
        return on;
    }

}
