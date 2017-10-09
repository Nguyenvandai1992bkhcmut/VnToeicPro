package feeds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.ModelFeed;
import supportview.ConvertTagView;
import supportview.FlowLayout;

/**
 * Created by dainguyen on 8/28/17.
 */

public class HolderView extends RecyclerView.ViewHolder{
    public TextView text_title;
    public LinearLayout line_image;
    public FlowLayout line_tag;
    public ImageView img_avatar;
    public TextView text_user;
    public TextView text_like_user;
    public TextView text_unlike_user;
    public TextView text_like_post;
    public TextView text_unlike_post;
    public TextView text_count_comment;
    public ImageView img_star;
    public ImageView img_like_post;
    public ImageView img_unlike_post;
    public ImageView img_hot;
    public TextView text_hot;
    public TextView text_subcribe;
    public ImageView img_edit;
    public ImageView img_camera;
    public ImageView img_send;
    public EditText edit_comment;
    public RecyclerView recyle_reply;
    public LinearLayout line_comment;
    public LinearLayout line_edit;
    public TextView text_edit;
    public TextView text_delete;
    public int isEdit=0;



    private Context context;
    public void setIsEdit(int i){
        this.isEdit = i;
    }

    public HolderView(Context context,View itemView) {
        super(itemView);
        this.context = context;
        text_title= (TextView)itemView.findViewById(R.id.question_title);
        line_image = (LinearLayout)itemView.findViewById(R.id.line_image);
        line_tag = (FlowLayout) itemView.findViewById(R.id.line_tags);
        img_avatar  =(ImageView)itemView.findViewById(R.id.img_avatar);
        text_user =(TextView) itemView.findViewById(R.id.text_user);
        text_like_user = (TextView)itemView.findViewById(R.id.text_like_user);
        text_unlike_user =(TextView)itemView.findViewById(R.id.text_unlike_user);
        text_like_post =(TextView)itemView.findViewById(R.id.text_like_post);
        text_unlike_post =(TextView)itemView.findViewById(R.id.text_unlike_post);
        text_count_comment =(TextView)itemView.findViewById(R.id.text_count_number);
        img_star = (ImageView)itemView.findViewById(R.id.img_star);
        img_like_post=(ImageView)itemView.findViewById(R.id.img_like_post);
        img_unlike_post=(ImageView)itemView.findViewById(R.id.img_unlike_post);
        img_hot = (ImageView)itemView.findViewById(R.id.img_hot);
        text_hot=(TextView)itemView.findViewById(R.id.text_hot);
        text_subcribe = (TextView)itemView.findViewById(R.id.text_subcribe);
        line_comment = (LinearLayout) itemView.findViewById(R.id.line_comment);
        img_edit = (ImageView)itemView.findViewById(R.id.img_edit);
        img_camera =(ImageView)itemView.findViewById(R.id.img_camera);
        img_send = (ImageView)itemView.findViewById(R.id.img_send);
        edit_comment =(EditText) itemView.findViewById(R.id.edit_comment);
        recyle_reply =(RecyclerView)itemView.findViewById(R.id.recycle_reply);
        line_edit =(LinearLayout)itemView.findViewById(R.id.line_edit);
        text_edit =(TextView)itemView.findViewById(R.id.text_edit);
        text_delete =(TextView)itemView.findViewById(R.id.text_delete);
    }

    public void createViewAvatar(String src){
        Picasso.with(context).load(src).resize(300,300).centerCrop().into(img_avatar);
//        if(ImageCache.getBitmapFromMemCache(key)==null){
//            MyTask task = new MyTask(0,img_avatar,key,0,0);
//            task.execute(src);
//        }else{
//            img_avatar.setImageBitmap(ImageCache.getBitmapFromMemCache(key));
//        }
    }


    public void createViewImagefromGallery(InputStream src){

        int selStart = edit_comment.getSelectionStart();
        int end = edit_comment.getSelectionEnd();

        Bitmap bitmap = BitmapFactory.decodeStream(src);
        if(bitmap == null)return;
        float H = context.getResources().getDisplayMetrics().heightPixels * 0.15f;
        float heso = H/ (float) bitmap.getHeight();
        float w = (float) (heso * bitmap.getWidth());
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) w, (int) H, false);

        ImageSpan imageSpan = new ImageSpan(context,bitmap1);
        SpannableStringBuilder builder = new SpannableStringBuilder(edit_comment.getText());

        String imgId = "\n#photo  1#\n";


        builder.replace(selStart,end,imgId);

        builder.setSpan(imageSpan, selStart+1, selStart + imgId.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        edit_comment.setText(builder);
        edit_comment.setSelection(edit_comment.getText().length()-1);
        String s = edit_comment.getText().toString();


    }
    public void createViewImage(ArrayList<String> src){
        if(src.size()==0)return;

        TableLayout tableLayout = new TableLayout(context);
        int W = context.getResources().getDisplayMetrics().widthPixels;

        ArrayList<TableRow>rows = new ArrayList<>();
        int numberImage = src.size();

        int numberRow=1;
        int imgWr1=W;
        int imgWr2=W;


        if(numberImage>=4)numberRow=2;
        if(numberImage==2 ){
            imgWr1=W/2;
        }else if(numberImage==3){
            imgWr1=W/3;
        } else if(numberImage==4){
            imgWr1=W/2;
            imgWr2= W/2;
        }else if(numberImage==5){
            imgWr1 = W/3;
            imgWr2=W/2;
        }else if(numberImage>=6){
            imgWr1=W/3;
            imgWr2=W/3;
        }

        int H= imgWr1;
        if(numberRow==2)H= H+imgWr2;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(W, H);
        params.setMargins(0, 10, 0, 0);
        tableLayout.setLayoutParams(params);
        tableLayout.setBackgroundColor(Color.GRAY);

        line_image.addView(tableLayout);

        TableRow row = new TableRow(context);
        rows.add(row);
        TableLayout.LayoutParams params1 = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,imgWr1);
        row.setLayoutParams(params1);
        tableLayout.addView(row);

        if(numberRow==2) {

            TableRow row1 = new TableRow(context);
            rows.add(row1);
            TableLayout.LayoutParams params2 = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imgWr2);
            row1.setLayoutParams(params2);
            tableLayout.addView(row1);
        }

        if(numberImage<=2){
            for(int i=0;i<numberImage;i++){
                ImageView imageView = new ImageView(context);
                TableRow.LayoutParams  params2 = new TableRow.LayoutParams(imgWr1,imgWr1,1f);
                imageView.setLayoutParams(params2);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                rows.get(0).addView(imageView);
//                if (ImageCache.getBitmapFromMemCache(src.get(i)) == null) {
//                    MyTask task = new MyTask(1,imageView,src.get(i),imgWr1,imgWr1);
//                    task.execute();
//                } else {
//                    imageView.setImageBitmap(ImageCache.getBitmapFromMemCache(src.get(i)));
//                }
                Picasso picasso =Picasso.with(context);
                picasso.setIndicatorsEnabled(true);
                picasso.load(src.get(i)).resize(imgWr1,imgWr1).centerCrop().into(imageView);            }
        }else if(numberImage==3){
            for(int i=0;i<3;i++){
                ImageView imageView = new ImageView(context);
                TableRow.LayoutParams params2=new TableRow.LayoutParams(imgWr1,imgWr1,1f);

                imageView.setLayoutParams(params2);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                rows.get(0).addView(imageView);

//                if (ImageCache.getBitmapFromMemCache(src.get(i)) == null) {
//                    MyTask task =task=new MyTask(1,imageView,src.get(i),imgWr1,imgWr1);
//                    task.execute();
//                } else {
//                    imageView.setImageBitmap(ImageCache.getBitmapFromMemCache(src.get(i)));
//                }
                Picasso picasso =Picasso.with(context);
                picasso.setIndicatorsEnabled(true);
                picasso.load(src.get(i)).resize(imgWr1,imgWr1).centerCrop().into(imageView);            }
        }else if (numberImage==4){
            for(int i=0;i<4;i++){
                ImageView imageView = new ImageView(context);
                TableRow.LayoutParams  params2 =null;
                if(i<2)params2=new TableRow.LayoutParams(imgWr1,imgWr1,1f);
                else params2=new TableRow.LayoutParams(imgWr2,imgWr2,1f);
                imageView.setLayoutParams(params2);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if(i<2)rows.get(0).addView(imageView);
                else rows.get(1).addView(imageView);

//                if (ImageCache.getBitmapFromMemCache(src.get(i)) == null) {
//                    MyTask task = new MyTask(1,imageView,src.get(i),imgWr1,imgWr1);
//                    task.execute();
//                } else {
//                    imageView.setImageBitmap(ImageCache.getBitmapFromMemCache(src.get(i)));
//                }
                Picasso picasso =Picasso.with(context);
                picasso.setIndicatorsEnabled(true);
                picasso.load(src.get(i)).resize(imgWr1,imgWr1).centerCrop().into(imageView);
            }
        }else if(numberImage==5){
            for(int i=0;i<5;i++){
                ImageView imageView = new ImageView(context);
                TableRow.LayoutParams  params2 =null;
                if(i<3)params2=new TableRow.LayoutParams(imgWr1,imgWr1,1f);
                else params2=new TableRow.LayoutParams(imgWr2,imgWr2,1f);
                imageView.setLayoutParams(params2);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if(i<=2)rows.get(0).addView(imageView);
                else rows.get(1).addView(imageView);

//                if (ImageCache.getBitmapFromMemCache(src.get(i)) == null) {
//                    MyTask task = null;
//                    if(i<=2)task=new MyTask(1,imageView,src.get(i),imgWr1,imgWr1);
//                    else task =new MyTask(1,imageView,src.get(i),imgWr2,imgWr2);
//                    task.execute();
//                } else {
//                    imageView.setImageBitmap(ImageCache.getBitmapFromMemCache(src.get(i)));
//                }

                if(i<=2){
                    Picasso picasso =Picasso.with(context);
                    picasso.setIndicatorsEnabled(true);
                    picasso.load(src.get(i)).resize(imgWr1,imgWr1).centerCrop().into(imageView);
                }
                else {
                    Picasso picasso =Picasso.with(context);
                    picasso.setIndicatorsEnabled(true);
                    picasso.load(src.get(i)).resize(imgWr2,imgWr2).centerCrop().into(imageView);                }
            }
        }else if(numberImage>=6){
            for(int i=0;i<6;i++){
                ImageView imageView = new ImageView(context);
                TableRow.LayoutParams  params2 =new TableRow.LayoutParams(imgWr1,imgWr1,1f);
                imageView.setLayoutParams(params2);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if(i<=2)rows.get(0).addView(imageView);
                else rows.get(1).addView(imageView);

//                if (ImageCache.getBitmapFromMemCache(src.get(i)) == null) {
//                    MyTask task = new MyTask(1,imageView,src.get(i),imgWr1,imgWr1);
//                    task.execute();
//                } else {
//                    imageView.setImageBitmap(ImageCache.getBitmapFromMemCache(src.get(i)));
//                }
                Picasso picasso =Picasso.with(context);
                picasso.setIndicatorsEnabled(true);
                picasso.load(src.get(i)).resize(imgWr1,imgWr1).centerCrop().into(imageView);

            }
        }

    }

    public void createViewContent(ModelFeed datas){
        ArrayList<Content>arr = datas.getContents();
        for(int i =0;i<arr.size();i++){
            if(arr.get(i).getType()==0) {
                this.createViewImage(arr.get(i).getSource());
            }else if(arr.get(i).getType()==2){
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(param);
                ConvertTagView convertTagView = new ConvertTagView(context,arr.get(i).getSource().get(0));
                textView.setText(convertTagView.getSpannableString());
                this.line_image.addView(textView);
            }else if(arr.get(i).getType()==1){
                ImageView imageView = new ImageView(context);
                imageView.setBackgroundColor(Color.RED);
                int W = context.getResources().getDisplayMetrics().widthPixels;
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(W, W);
                imageView.setLayoutParams(param);
                Picasso.with(context).load(arr.get(i).getSource().get(0)).centerCrop().resize(W,W).into(imageView);
                this.line_image.addView(imageView);
            }else  if(arr.get(i).getType()==4){
                String id = arr.get(i).getSource().get(0);
                Question question = datas.getQuestionId(id);
                if(question!=null){
                    View view1 = question.getViewQuestion(context);
                    this.line_image.addView(view1);
                }
            }
        }
    }

    public class  MyTask extends AsyncTask<String,Bitmap,Bitmap> {
        int f =0;
        private ImageView view;
        private String key;
        private int W;
        private int H;
        public MyTask(int f,ImageView view,String key, int W, int H){
            this.view = view;
            this.f = f;
            this.key = key;
            this.W = W;
            this.H = H;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap=null;
            if(f==1)bitmap = getBitmapFromURL(key);
            else bitmap = getBitmapFromURL(params[0]);
            if(f==0) {
                System.out.println("DOWNLOAD " + params[0]);
                if (params[0].startsWith("https://lh3")) {
                    try {
                        System.out.println("sleeppppppppppppppp");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("awake");
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(f==0){
                view.setImageBitmap(bitmap);
            }else{
                view.setImageBitmap(bitmap);
            }

        }

        public Bitmap getBitmapFromURL(String src) {

            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                Bitmap bitmap1 = null;

                if(f==0) {
                    bitmap1 = Bitmap.createScaledBitmap(bitmap,300,300,false);
                }else{
                    float heso = W / (float) bitmap.getWidth();
                    float h = (float) (heso * bitmap.getHeight());
                    bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) W, (int) h, false);
                }
                ImageCache.addBitmapToMemoryCache(key,bitmap1);
                return bitmap1;

            } catch (Exception e) {
                // img_avatar.setImageResource(R.mipmap.essentialwordsicon);
                e.printStackTrace();
            }
            return null;

        }

    }

}