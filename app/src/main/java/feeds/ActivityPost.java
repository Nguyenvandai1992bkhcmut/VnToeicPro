package feeds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import model.PostTag;
import supportview.ConvertTagView;
import supportview.FlowLayout;

import static com.vntoeic.bkteam.vntoeicpro.R.id.edit_comment;

/**
 * Created by dainguyen on 9/5/17.
 */

public class ActivityPost extends AppCompatActivity  {
    /*
    flag  -0  new post  comment
    flag  -1   edit post
    flag  -2  edit comment
    flag  -3  edit reply
     */

    private TextView text_cancel;
    private TextView text_submit;
    private TextView text_preivew;
    private EditText edit_title;
    private EditText edit_body;
    private FlowLayout flow_tags;
    private LinearLayout line_select_tag;
    private ImageView img_line;

    private int flag;
    private ArrayList<Content>contents;
    private String title;
    private final String LINKTAG="http://vntoeic.xyz/api/v1/tags";
    private ArrayList<PostTag>used;
    private ArrayList<PostTag>normal;
    private ArrayList<PostTag>select;

    int f=0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==88){
                Bundle bundle = data.getBundleExtra("result");
                if(bundle!=null){
                    select = (ArrayList<PostTag>) bundle.getSerializable("selected");
                    if(select!=null)setUpViewTags();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setUpData();
        setUpLayout();
    }

    public void setUpData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            flag= bundle.getInt("flag");
            select = (ArrayList<PostTag>) bundle.getSerializable("selected");
            contents = (ArrayList<Content>) bundle.getSerializable("contents");
            title =bundle.getString("title");
            Task task = new Task();
            task.execute();
        }
    }

    public void setUpLayout(){
        text_cancel = (TextView)findViewById(R.id.text_cancel);
        text_preivew =(TextView)findViewById(R.id.text_preview);
        text_submit =(TextView)findViewById(R.id.text_submit);
        edit_title =(EditText)findViewById(R.id.edit_question_title);
        edit_body =(EditText)findViewById(R.id.edit_question_body);
        flow_tags =(FlowLayout) findViewById(R.id.flow_tag);
        img_line=(ImageView)findViewById(R.id.img_line_title);
        line_select_tag =(LinearLayout)findViewById(R.id.line_select_tag);
        if(flag==0){

        }else if(flag==2|| flag==3){
            line_select_tag.setVisibility(View.GONE);
            edit_title.setVisibility(View.GONE);
            img_line.setVisibility(View.GONE);
            text_preivew.setVisibility(View.GONE);
        }
        edit_body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        line_select_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(used.size()==0 && normal.size()==0){
                    Toast.makeText(getApplicationContext(),"Cann't load sever.Please check your internet!",Toast.LENGTH_LONG).show();
                    Task task = new Task();
                    task.execute();
                }else {
                    startSelectTag();
                }
            }
        });
        setUpContent();
        setUpViewTags();

    }
    public void startSelectTag(){
        Intent intent = new Intent(ActivityPost.this, ActivitySelectTag.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("selected", select);
        bundle.putSerializable("used",used);
        bundle.putSerializable("normal",normal);
        intent.putExtras(bundle);

        // edit for result
        startActivityForResult(intent,88);
        overridePendingTransition(R.anim.bot_top,R.anim.nochange);
    }

    public void setUpViewTags(){
        flow_tags.removeAllViews();
        for(int i =0;i<select.size();i++){
            TextView textview = new TextView(getApplicationContext());
            FlowLayout.LayoutParams param = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textview.setLayoutParams(param);
            textview.setPadding(10,5,10,5);
            textview.setTextColor(Color.BLACK);
            textview.setText(select.get(i).getTag_title());
            textview.setBackgroundResource(R.drawable.rect_tag);
            flow_tags.addView(textview);
        }
    }

    public void setUpContent(){
        edit_title.setText(title);

        for(int i=0;i<contents.size();i++){
            if(contents.get(i).getType()==0){
                if(edit_body.getText().length()>0){
                    edit_body.setSelection(edit_body.getText().length() - 1);
                }else{
                    edit_body.setSelection(0);
                }
                int selStart = edit_body.getSelectionStart();
                int end = edit_body.getSelectionEnd();
                String imgId = contents.get(i).getSource().get(0);
                SpannableStringBuilder builder = new SpannableStringBuilder(edit_body.getText());
                builder.replace(selStart, end, imgId);
                edit_body.setText(builder);
                if(edit_body.getText().length()>0){
                    edit_body.setSelection(edit_body.getText().length() - 1);
                }else{
                    edit_body.setSelection(0);
                }
            }else{
                for(int j=0;j<contents.get(i).getSource().size();j++) {
                    if(edit_body.getText().length()>0){
                        edit_body.setSelection(edit_body.getText().length() - 1);
                    }else{
                        edit_body.setSelection(0);
                    }
                    int selStart = edit_body.getSelectionStart();
                    int end = edit_body.getSelectionEnd();
                    String imgId = "\n"+contents.get(i).getSource().get(j)+"\n";
                    SpannableStringBuilder builder = new SpannableStringBuilder(edit_body.getText());
                    builder.replace(selStart, end, imgId);
                    edit_body.setText(builder);
                    if(edit_body.getText().length()>0){
                        edit_body.setSelection(edit_body.getText().length() - 1);
                    }else{
                        edit_body.setSelection(0);
                    }
                    TaskDowload taskDowload = new TaskDowload(selStart+1, selStart + imgId.length() - 1);
                    taskDowload.execute(contents.get(i).getSource().get(j));
                }

            }
        }


    }

    public class TaskDowload extends AsyncTask<String ,Void , InputStream>{
        private int begin;
        private int end;
        public TaskDowload(int begin, int end ){
            this.end = end;
            this.begin = begin;
        }
        @Override
        protected InputStream doInBackground(String... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return in;


            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(InputStream in) {
            super.onPostExecute(in);
            if(in==null){
                // add image tuong trung
                return;
            }

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            if(bitmap == null)return;
            float H = getApplicationContext().getResources().getDisplayMetrics().heightPixels * 0.15f;
            float heso = H/ (float) bitmap.getHeight();
            float w = (float) (heso * bitmap.getWidth());
            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) w, (int) H, false);
            ImageSpan imageSpan = new ImageSpan(getApplicationContext(),bitmap1);
            SpannableStringBuilder builder = new SpannableStringBuilder(edit_body.getText());
            builder.setSpan(imageSpan, begin,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            edit_body.setText(builder);
            if(edit_body.getText().length()>0){
                edit_body.setSelection(edit_body.getText().length() - 1);
            }else{
                edit_body.setSelection(0);
            }

        }
    }
    public class Task extends AsyncTask<Void, Void , Void>{

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(LINKTAG);
                urlConnection = (HttpURLConnection) url.openConnection();
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
                JSONArray arr = ob.getJSONArray("normal");
                normal= new ArrayList<>();
                used = new ArrayList<>();
                for(int i=0;i<arr.length();i++){
                    JSONObject o =arr.getJSONObject(i);
                    normal.add(new PostTag(o.getInt("tagId"),o.getString("title"),1));
                }
                JSONArray arr1 = ob.getJSONArray("used");
                for(int i=0;i<arr1.length();i++){
                    JSONObject o =arr1.getJSONObject(i);
                    used.add(new PostTag(o.getInt("tagId"),o.getString("title"),0));
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
            if(f==1) {

                if (used.size() == 0 && normal.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Cann't load sever.Please check your internet!", Toast.LENGTH_LONG).show();
                } else {
                    startSelectTag();
                }

            }else{
                f=1;
            }

        }
    }

}
