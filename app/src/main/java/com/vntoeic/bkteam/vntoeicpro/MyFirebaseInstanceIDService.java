package com.vntoeic.bkteam.vntoeicpro;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import utils.Config;

/**
 * Created by giang on 8/16/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        String oldDeviceToken = getDeviceTokenFromPref();
        storeRegIdInPref(refreshedToken);

        sendRegistrationToServer(refreshedToken, oldDeviceToken);
        broadcaseRegistrationComplete(refreshedToken);

    }

    private String getDeviceTokenFromPref() {
        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF_MESSAGE, 0);
        return pref.getString(Config.MESSAGE_TOKEN, null);
    }

    private void broadcaseRegistrationComplete(String refreshedToken) {

    }

    private void sendRegistrationToServer(String refreshedToken, String oldDeviceToken) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) changeDeviceToken(user.getUid(), refreshedToken, oldDeviceToken, "android");
    }

    private void changeDeviceToken(String uid, String refreshedToken, String oldDeviceToken, String deviceType) {

    }

    private void storeRegIdInPref(String refreshedToken) {

        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF_MESSAGE, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Config.MESSAGE_TOKEN, refreshedToken);
        editor.commit();
    }
}
