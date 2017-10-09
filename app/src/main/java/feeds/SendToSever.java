package feeds;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dainguyen on 8/31/17.
 */

public class SendToSever extends AsyncTask<String,Void,Void> {
    public  final static  String USER_TOKEN="eyJhbGciOiJSUzI1NiIsImtpZCI6ImY1ZDQ0ZjJjMjRmODVkYjlkNDVkYjE4NDVkNjZkYWZkNjRmZWVhMzIifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdm50b2VpYy04NDliZCIsIm5hbWUiOiLEkOG6oWkgTmd1eeG7hW4gVsSDbiIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLUNwNjJjdTMzb1JFL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUdnL3YzMGNuc1FlcG5rL3M5Ni1jL3Bob3RvLmpwZyIsImF1ZCI6InZudG9laWMtODQ5YmQiLCJhdXRoX3RpbWUiOjE1MDQ0MjY4MjAsInVzZXJfaWQiOiJMTWRBSHlFTW5SZVZCRm5ZVkJVTHRPMG9vREsyIiwic3ViIjoiTE1kQUh5RU1uUmVWQkZuWVZCVUx0TzBvb0RLMiIsImlhdCI6MTUwNDQyNjgyMCwiZXhwIjoxNTA0NDMwNDIwLCJlbWFpbCI6IjE0MTA3NTNAaGNtdXQuZWR1LnZuIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZ29vZ2xlLmNvbSI6WyIxMTEzODg4NzAwNDcxOTE3MTcyODIiXSwiZW1haWwiOlsiMTQxMDc1M0BoY211dC5lZHUudm4iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.gkOjVqoRuxHYSrPBRpr0a_RWHfwLGwAN7Ge7ur1SRRS05p5gMO0sI8toMn2ArPt9jomxvP-3f6X9hbAT07WAzLImqdKryWBqbjkg1hl7idHuI9ztAJhH5wX8LogXhXcibsgHk81Tinq7pbbGiiCCkjG89fYCPSkGCN-2u6IEUacwgXgxQ5gsuzzlRRmj9RtMNeTbVLrDPFAKivf5hfOEJzR_VFQB8GSRhUb5PnZWT-C9v5lfA3nWf4WniFUT0AwthDB-TwEcMstjqscTdOiyvJ5xTZ_ksL8cdUdYHgDNl8l0XeBOHMVlwQp2tzjCClEH-_cSbbFJqewnFHDL4QXyYg";
    public  final static  int USER_ID =23;
//    private final static String LINK_POST="http://vntoeic.xyz/api/v1/posts/";
    private int type;
    public SendToSever(int flag){
        this.type = flag;
        // type==0 post elst delete request_property
        //1 - subcribe
        //2 - unsubcribe
        //3 - like_comemnt
        //4 - dislike comement
        //5 - like post
        //6 - dislike post

    }
    @Override
    protected Void doInBackground(String... params) {
        subcribePost(params);
        return null;
    }

    public void subcribePost(String... params){
        try {
            URL url = null;
            url = new URL(params[0]);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            if(type==0) {
                httpURLConnection.setRequestMethod("POST");
            }else{
                httpURLConnection.setRequestMethod("DELETE");
            }
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.setRequestProperty("user-token", USER_TOKEN);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                //System.out.println("oke");
            } else {
                //System.out.println(httpURLConnection.getResponseMessage());
                //System.out.println(httpURLConnection.getResponseCode());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
