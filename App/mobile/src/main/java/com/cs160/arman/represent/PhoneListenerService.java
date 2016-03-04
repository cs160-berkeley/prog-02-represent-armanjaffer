package com.cs160.arman.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {

//what does this do? it waits for a message to be recieved and then does something
    //here what it's doing is

//   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
private static final String TOAST = "/send_toast";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( true ) {
            System.out.println("Got to Phone Listener");
            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            System.out.println(value);
            if (Integer.valueOf(value) != -1) {
                Intent intent = new Intent(this, detailed_view.class);
                intent.putExtra("number", value);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                System.out.println("THIS IS IN PHONE LISTER AND RIGHT IF");
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Random r_gen = new Random();
                int rx = r_gen.nextInt(122 - 74);
                int ry = r_gen.nextInt(40 - 32);
                int x = 74 + rx;
                int y = 32 + ry;
                String toast_text = "Coordinates: (" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
                Toast toast = Toast.makeText(context, toast_text, duration);
                toast.show();
            }
            // Make a toast with the String

            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
