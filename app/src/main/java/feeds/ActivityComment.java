package feeds;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.ModelComment;
import model.ModelFeed;

/**
 * Created by dainguyen on 8/28/17.
 */

public class ActivityComment extends AppCompatActivity implements IFeed{
    private RecyclerView recyclerView;
    private AdapterComment adapterComment;
    private TextView text_notify;
    private int isTop=1;
    private ArrayList<String>pathImage = new ArrayList<>();
    private Dialog dialog;
    WindowManager.LayoutParams params;

    private ModelFeed data;
    private ArrayList<ModelComment>arrComment= new ArrayList<>();
    private final static String LINKCOMMENT ="http://vntoeic.xyz/api/v1/posts/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setUpLayout();
        setUpData();
    }

    public void setUpData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            data = (ModelFeed) bundle.getSerializable("data");
        }
        if(data!=null){
            TaskGetPost taskGetPost = new TaskGetPost();
            taskGetPost.execute();
        }
    }


    public void setUpLayout(){
        recyclerView= (RecyclerView)findViewById(R.id.recycle);
        text_notify = (TextView)findViewById(R.id.text_notify);
    }

    @Override
    public void showDetailFeed(ModelFeed modelFeed) {

    }

    @Override
    public void showImageFull(ArrayList<String> src) {
       Intent intent = new Intent(this,ActivityImageComment.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data",src);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void deleteComment() {

    }

    @Override
    public void editComment(Bundle bundle) {
        Intent intent = new Intent(this,ActivityPost.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,88);
    }

    class TaskGetPost extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            URL url1 = null;
            HttpURLConnection urlConnection1 = null;
            try {

                url = new URL(LINKCOMMENT + String.valueOf(data.getPostId()) + "/comments/top");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                JSONObject ob = new JSONObject(sb.toString());
                arrComment.add(new ModelComment(data.getPostId(),ob));
            }catch (Exception e) {
                isTop=0;
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            try{

                url1 = new URL(LINKCOMMENT + String.valueOf(data.getPostId())+"/comments/");
                urlConnection1 = (HttpURLConnection) url1.openConnection();
                urlConnection1.setDoInput(true);
                InputStream in1 = new BufferedInputStream(urlConnection1.getInputStream());
                BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
                StringBuilder sb1 = new StringBuilder();
                String line1;
                while ((line1 = br1.readLine()) != null) {
                    sb1.append(line1 + "\n");
                }
                br1.close();
                JSONArray ob1 = new JSONArray(sb1.toString());
                if(arrComment.size()==0)return null;
                for (int i =0;i<ob1.length();i++){
                    JSONObject o = ob1.getJSONObject(i);
                    if(isTop==1 & o.getInt("commentId")==arrComment.get(0).getComment_id()){
                        continue;
                    }else {
                        arrComment.add(new ModelComment(data.getPostId(),o));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                urlConnection1.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


                    adapterComment = new AdapterComment(ActivityComment.this,data,arrComment);
                    adapterComment.setiFeed(ActivityComment.this);
                    LinearLayoutManager manager = new LinearLayoutManager(ActivityComment.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapterComment);



        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AdapterComment.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            if(pathImage.contains(selectedImage.getPath()))return;
            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                adapterComment.addImageGallery(imageStream);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }





}
