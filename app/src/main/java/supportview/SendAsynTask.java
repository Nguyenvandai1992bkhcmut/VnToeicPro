package supportview;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dainguyen on 7/4/17.
 */
public class SendAsynTask extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url= null;
<<<<<<< HEAD
            if(params.length==2){
                url= new URL(params[0]);
            }
            else if(params.length==3) {
                url = new URL(params[0]+params[1]); //Enter URL here
            }else if(params.length==4){
                url = new URL(params[0]+params[1]+"/"+params[2]);
            }
=======
            url= new URL(params[0]);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
<<<<<<< HEAD
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            if(params.length==2)jsonObject.put("chosen",params[1]);
            else if(params.length==3)jsonObject.put("chosen", params[2]);
            else jsonObject.put("chosen",params[3]);
=======
            httpURLConnection.setRequestProperty("content-token",params[2]);
            //httpURLConnection.setRequestProperty("user-token",params[2]);
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chosen",params[1]);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();
            if(httpURLConnection.getResponseCode()==200){
                //System.out.println("oke");

            }else{
                //System.out.println(httpURLConnection.getResponseMessage());
                //System.out.println(httpURLConnection.getResponseCode());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}