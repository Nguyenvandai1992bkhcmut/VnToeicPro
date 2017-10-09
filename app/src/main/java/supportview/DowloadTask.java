package supportview;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dainguyen on 7/18/17.
 */

public class DowloadTask extends AsyncTask<String,Void,Void> {

    private IDowload iDowload;
    public void setiDowload(IDowload iDowload){
        this.iDowload = iDowload;
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[1]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("content-token",params[3]);
            connection.setDoInput(true);
            connection.connect();
            String ss= connection.getHeaderField("Content-Length");

            InputStream input = connection.getInputStream();

            File data =new File(params[2]);

            try{
                data.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
            OutputStream outputStream = new FileOutputStream(data);
            byte[]arr = new byte[1024];
            int length =0;
            int total =0;
            while ((length=input.read(arr))>0){
                total+=length;
                outputStream.write(arr,0,length);
            }
            //iDowload.notifySuccess(Integer.valueOf(params[0]),true,params[1],params[2]);
           if(ss!=null && total != 3006 &total== Integer.valueOf(ss)) iDowload.notifySuccess(Integer.valueOf(params[0]),true,params[1],params[2]);
            else {
               data.delete();
               iDowload.notifySuccess(Integer.valueOf(params[0]),false,params[1],params[2]);
           }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            iDowload.notifySuccess(Integer.valueOf(params[0]),false,params[1],params[2]);
        }
        return null;
    }


    public interface IDowload{
        public void notifySuccess(int numbertask,boolean b,String url,String file);
    }
}
