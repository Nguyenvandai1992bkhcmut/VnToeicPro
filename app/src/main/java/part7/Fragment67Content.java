package part7;

<<<<<<< HEAD
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
=======
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.IDataPart;
import model.ModelPart7;
=======
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import model.IDataPart;
import model.ModelFeed;
import model.ModelPart7;
import model.ModelPartSubject;
import sqlite.ManagerPart;
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
import supportview.IContentQuestion;

/**
 * Created by dainguyen on 7/6/17.
 */

public class Fragment67Content extends Fragment {

    private int mode=0;
    private int orgX=0;
    private int offsetX=0;
    private IContentQuestion iContentQuestion;
    private IDataPart data;
<<<<<<< HEAD
=======
    private RecyclerView recyclerView;
    private ArrayList<Integer>arr;
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            data = (IDataPart) bundle.getSerializable("data");
            mode =bundle.getInt("mode");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part7_content,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(View view){

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orgX = (int) event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        offsetX = (int) (event.getX() - orgX);
<<<<<<< HEAD
                        if (offsetX < -150) iContentQuestion.nextContent();
                        else if (offsetX > 150) iContentQuestion.backContent();
=======
                        if (offsetX < -100) iContentQuestion.nextContent();
                        else if (offsetX > 100) iContentQuestion.backContent();
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                        return true;
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:

                }
                return false;
            }
        };
        View view_child= data.getViewContent(getContext());
        ScrollView scrollView =(ScrollView)view.findViewById(R.id.scrollView);
        scrollView.addView(view_child);
        view.setOnTouchListener(onTouchListener);
        view_child.setOnTouchListener(onTouchListener);
<<<<<<< HEAD
=======

        recyclerView =(RecyclerView)view.findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        arr = new ArrayList<>();

        Task task = new Task();
        task.execute();



>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
    }
    public void setiContentQuestion(IContentQuestion iPartContent){
        this.iContentQuestion = iPartContent;
    }

<<<<<<< HEAD
=======
    public class TaskPost extends AsyncTask<Void , Void, Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                URL url= null;
                if(data.getPart()==6) {
                    url = new URL("http://vntoeic.xyz/api/v1/part6s/" + String.valueOf(data.getId())+"/subjects");
                }else if(data.getPart()==7){
                    url = new URL("http://vntoeic.xyz/api/v1/part7s/" + String.valueOf(data.getId())+"/subjects");

                }

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                //httpURLConnection.setRequestProperty("user-token",params[2]);
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for(int i =0;i<arr.size();i++) {
                    jsonArray.put(arr.get(i));
                }
                jsonObject.put("subjects",jsonArray);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();
                if(httpURLConnection.getResponseCode()==200){
                    //System.out.println("oke");
                    return 1;

                }else{
                    //System.out.println(httpURLConnection.getResponseMessage());
                    //System.out.println(httpURLConnection.getResponseCode());
                    return 0;
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

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(1);
            if(i==1){
                Toast.makeText(getContext(),"Successful",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),"Fail",Toast.LENGTH_LONG).show();
            }
        }
    }

    public class Task extends AsyncTask<Void,Void,ArrayList<Integer>>{

        @Override
        protected ArrayList<Integer> doInBackground(Void... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                if(data.getPart()==6) {
                    url = new URL("http://vntoeic.xyz/api/v1/part6s/" + String.valueOf(data.getId())+"/subjects");
                }else if(data.getPart()==7){
                    url = new URL("http://vntoeic.xyz/api/v1/part7s/" + String.valueOf(data.getId())+"/subjects");

                }
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
                JSONArray ob = new JSONArray(sb.toString());

                for(int i=0;i<ob.length();i++){
                    arr.add(ob.getJSONObject(i).getInt("subject_id"));
                }
                return arr;


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection!=null)urlConnection.disconnect();
            }
            return new ArrayList<>();

        }

        @Override
        protected void onPostExecute(ArrayList<Integer> arrayList) {
            super.onPostExecute(arrayList);
            ManagerPart managerPart = new ManagerPart();
            ModelPartSubject[]subjects= managerPart.searchPartSubject(data.getPart());
            AdapterSubject adapterSubject = new AdapterSubject(subjects);
            recyclerView.setAdapter(adapterSubject);
        }
    }

    public class AdapterSubject extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ModelPartSubject[]subject;

        public AdapterSubject(ModelPartSubject[]subject){
            this.subject = subject;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CheckBox checkBox = new CheckBox(getContext());
            return new Holder(checkBox);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Holder holder1 = (Holder) holder;
            holder1.checkBox.setText(subject[position].getTitle());


                if (arr.contains(subject[position].getId())) {
                    holder1.checkBox.setChecked(true);

                } else {
                    holder1.checkBox.setChecked(false);
                }

            holder1.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        arr.add(subject[position].getId());
                    }else{
                        arr.remove(arr.indexOf(subject[position].getId()));
                    }
                    if(arr.size()==0)return;
                    TaskPost taskPost = new TaskPost();
                    taskPost.execute();
                }
            });
        }

        @Override
        public int getItemCount() {
            return subject.length;
        }

        public class  Holder extends RecyclerView.ViewHolder{
            public CheckBox checkBox;
            public Holder(View itemView) {
                super(itemView);
                checkBox = (CheckBox) itemView;
                checkBox.setTextColor(Color.WHITE);
            }
        }
    }

>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
}
