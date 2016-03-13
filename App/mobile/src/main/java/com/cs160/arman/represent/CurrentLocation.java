package com.cs160.arman.represent;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrentLocation extends Service
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{



    private GoogleApiClient mGoogleApiClient = null;
    private FusedLocationProviderApi fusedLocationProviderApi;
    public static String TAG = "GPSActivity";
    public static int UPDATE_INTERVAL_MS = 3;
    public static int FASTEST_INTERVAL_MS = 3;
    private static final String DEBUG_TAG = "HttpExample";

    public CurrentLocation() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passin
        System.out.println("HERREEEEEEE");
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_INTERVAL_MS);

        // Send request for location updates
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient,
                        locationRequest, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.getStatus().isSuccess()) {
                            Log.d(TAG, "Successfully requested");
                        } else {
                            Log.e(TAG, status.getStatusMessage());
                        }
                    }
                });

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println(String.valueOf(LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient).getLongitude()));
        System.out.println(String.valueOf(LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient).getLatitude()));
        String lat = String.valueOf(LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient).getLatitude());
        String lon = String.valueOf(LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient).getLongitude());
        Intent intent = new Intent(this, candidate_list.class);
        if (!lat.equals(lon)) {
            intent.putExtra("lon", lon);
            intent.putExtra("lat", lat);
        }else{
            System.out.println("LAT LON DIDNT UPDATE");
        }
        mGoogleApiClient.disconnect();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type", "current");
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



}
