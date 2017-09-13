package utils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by giang on 8/17/17.
 */

public class LoginUtils {
    public interface OnLoginWithServer {

        void updateUI(FirebaseUser user);
    }

    public static class ModelUserLogin {
        FirebaseUser mUser;
        String mUserToken;
        String mDeviceToken;

        public ModelUserLogin(FirebaseUser user, String userToken, String deviceToken) {
            this.mUser = user;
            this.mUserToken = userToken;
            this.mDeviceToken = deviceToken;
        }
    }

    public static class LoginWithServer extends AsyncTask<ModelUserLogin, Void, FirebaseUser> {

        private final OnLoginWithServer mListener;

        public LoginWithServer(OnLoginWithServer onLoginWithServer) {
            this.mListener = onLoginWithServer;
        }


        @Override
        protected FirebaseUser doInBackground(ModelUserLogin... params) {
            ModelUserLogin userLogin = params[0];
            String userToken = userLogin.mUserToken;
            String deviceToken = userLogin.mDeviceToken;

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("deviceToken", deviceToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(mediaType, jsonBody.toString());
            Request request = new Request.Builder()
                    .url("http://vntoeic.xyz/api/v1/sessions")
                    .post(body)
                    .addHeader("user-token", userToken)
                    .addHeader("content-type", "application/json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Log.d("response ===== ", response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userLogin.mUser;
        }

        @Override
        protected void onPostExecute(FirebaseUser user) {
            mListener.updateUI(user);
        }
    }

}
