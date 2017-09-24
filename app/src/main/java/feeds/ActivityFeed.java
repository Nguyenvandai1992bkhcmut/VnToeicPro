package feeds;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import model.ModelFeed;
import model.User;
import supportview.ListenPraticseActivity;


/**
 * Created by dainguyen on 8/20/17.
 */

public class ActivityFeed extends AppCompatActivity  implements IFeed {
    private TextView text_notify;
    private RecyclerView recyclerView;
    private AdapterFeed adapterFeed;
    private LayManager manager = null;
    private int flag=0;
    private int flag1=0;
    private int length=0;
    private ArrayList<ModelFeed>datas= new ArrayList<>();
    public static ArrayList<User>arrUser = new ArrayList<>();
    private static final String LINKPOST="http://vntoeic.xyz/api/v1/posts?limit=";
    private final static String LINKUSER ="http://vntoeic.xyz/api/v1/users/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getData(0,5);
        setUpLayout();
    }

    public void getData(int begin ,int limit){
        TaskGetPost taskGetPost = new TaskGetPost();
        taskGetPost.execute(String.valueOf(begin),String.valueOf(limit));
    }


    public static User getUser(int user_id){
        User user = null;
        for(int i=0;i<arrUser.size();i++){
            if( arrUser.get(i).getUse_id() == user_id){
                user = arrUser.get(i);
                return user;
            }
        }
        User user1 = setDataUser(user_id);
        arrUser.add(user1);
        return setDataUser(user_id);

    }

    public  static void updateUser(int user_id ){
        User user = null;
        for(int i=0;i<arrUser.size();i++){
            if( arrUser.get(i).getUse_id() == user_id){
                User user1 = setDataUser(user_id);
                arrUser.set(i,user1);
                return;
            }
        }
    }
    public static User setDataUser(int userId){
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(LINKUSER + String.valueOf(userId));
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            JSONObject ob = new JSONObject(sb.toString());
            User user = new User(userId,ob.getString("name"),ob.getString("imgUrl"),ob.getInt("approves"),ob.getInt("disapproves"));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }



    public void setUpLayout(){
        recyclerView= (RecyclerView)findViewById(R.id.recycle);
        text_notify = (TextView)findViewById(R.id.text_notify);
        Timer timer = new Timer();

        MyTimeTask timeTask = new MyTimeTask();
        timer.schedule(timeTask,0,3000);

    }

    @Override
    public void showDetailFeed(ModelFeed modelFeed) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",modelFeed);
        Intent intent = new Intent(this,ActivityComment.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showImageFull(ArrayList<String> src) {

    }

    @Override
    public void deleteComment() {

    }

    @Override
    public void editComment(Bundle bundle) {

    }

    class LayManager extends LinearLayoutManager{

        public LayManager(Context context) {
            super(context);
        }

        public LayManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public LayManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

            final int result = super.scrollVerticallyBy(dy, recycler, state);

            if (findLastVisibleItemPosition() == (datas.size() - 1) &flag==1) {
                getData(datas.get(datas.size()-1).getPostId()-1,5);

            }

            return result;

        }

    }

    class TaskGetPost extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                if(flag==0) {
                    url = new URL(LINKPOST + String.valueOf(params[1]));
                }else{
                    url = new URL(LINKPOST+String.valueOf(params[1])+"&begin-id="+params[0]);
                }

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("user-token", SendToSever.USER_TOKEN);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                JSONArray ob = new JSONArray(sb.toString());
                for (int i =0;i<ob.length();i++){
                    int f=0;
                    for(int j=0;j<datas.size();j++){
                        if(datas.get(j).getPostId()==ob.getJSONObject(i).getInt("postId")){
                            f=1;
                            break;
                        }
                    }
                    if(f==1)break;
                    datas.add(new ModelFeed(ob.getJSONObject(i)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(datas.size()==0){
                text_notify.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }else {
                if (flag == 0) {
                    adapterFeed = new AdapterFeed(ActivityFeed.this, datas);
                    adapterFeed.setiFeed(ActivityFeed.this);
                    manager = new LayManager(ActivityFeed.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapterFeed);
                    length = datas.size();
                    flag = 1;
                } else {
                    adapterFeed.changeData(datas);
                    adapterFeed.notifyItemRangeInserted(length, datas.size() - length);
                    length = datas.size();
                }
            }

        }
    }

    class TaskGetHot extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {

            Random random = new Random();
            int x = random.nextInt(160)+100;
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {

                url = new URL("http://vntoeic.xyz/api/v1/posts/" + String.valueOf(x));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("user-token", SendToSever.USER_TOKEN);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                JSONObject ob = new JSONObject(sb.toString());
                datas.set(0,new ModelFeed(ob));


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection!=null)urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterFeed.changeHot(datas.get(0));
            adapterFeed.notifyItemChanged(0);
        }
    }

    class MyTimeTask extends TimerTask{

        @Override
        public void run() {
            if( manager!=null) {

                if (manager.findFirstVisibleItemPosition() == 0) {
                    TaskGetHot taskGetHot = new TaskGetHot();
                    taskGetHot.execute();
                }
            }
        }
    }
}
