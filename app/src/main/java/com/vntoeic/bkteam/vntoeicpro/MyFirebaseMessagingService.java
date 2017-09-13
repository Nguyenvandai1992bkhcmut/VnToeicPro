package com.vntoeic.bkteam.vntoeicpro;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by giang on 8/15/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            handleNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (JSONException e) {


            }
        }
    }

    private void handleDataMessage(JSONObject json) {

    }

    private void handleNotification(String body) {
        //Broadcast push notification

    }
}
