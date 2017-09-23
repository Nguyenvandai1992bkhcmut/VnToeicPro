package feeds;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.ModelFeed;
import supportview.ConvertTagView;
import supportview.FlowLayout;

/**
 * Created by dainguyen on 8/20/17.
 */

public class AdapterFeed extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ModelFeed>datas;
    private ArrayList<Integer>arrDowload = new ArrayList<>();
    private Context context;
    private IFeed iFeed;



    public AdapterFeed(Context context, ArrayList<ModelFeed>datas){
        this.context = context;
        this.datas = datas;
        for(int i =0;i<datas.size();i++)arrDowload.add(0);
    }

    public void setiFeed(IFeed iFeed){
        this.iFeed = iFeed;
    }

    public void changeData(ArrayList<ModelFeed>datas){
        this.datas = datas;

    }

    public void changeHot(ModelFeed data){
        datas.set(0,data);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_feed,parent,false);
        return new HolderView(context,view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       setUpLayout((HolderView) holder,position);
    }

    private void setUpLayout(HolderView view, final int position) {
        /*
        reload data for user
         */
        datas.get(position).setDataUser();

        view.text_title.setText(datas.get(position).getTitle());
        view.text_user.setText(datas.get(position).getUser().getName() + "   " +datas.get(position).getTime());
        view.text_like_user.setText(String.valueOf(datas.get(position).getUser().getLike()));
        view.text_unlike_user.setText(String.valueOf(datas.get(position).getUser().getUnlike()));
        view.text_like_post.setText(String.valueOf(datas.get(position).getApproves()));
        view.text_unlike_post.setText(String.valueOf(datas.get(position).getDisapproves()));
       view.text_count_comment.setText(String.valueOf(datas.get(position).getCountComment()));
        if(datas.get(position).isSub()){
            view.img_star.setImageResource(R.mipmap.yellow_smallstar);
        } else view.img_star.setImageResource(R.mipmap.gray_smallstar);

        view.img_avatar.setBackgroundColor(Color.RED);
        view.img_like_post.setImageResource(R.mipmap.approve_icon);
        view.img_unlike_post.setImageResource(R.mipmap.disapprove_con);

        if(datas.get(position).isApp()){
            view.img_like_post.setImageResource(R.mipmap.approved_icon);
        }

        if(datas.get(position).isDisapp()){
            view.img_unlike_post.setImageResource(R.mipmap.disapproved_icon);
        }

        view.createViewAvatar(datas.get(position).getUser().getLinkAvatar());
        view.line_image.removeAllViews();

        view.createViewContent(datas.get(position));

        if(datas.get(position).isAudio()){
            View v = LayoutInflater.from(context).inflate(R.layout.cell_audio_feed,null,false);
            view.line_image.addView(v);
        }

        view.line_tag.removeAllViews();
        for(int i =0;i<datas.get(position).getTagTitle().size();i++){
            TextView textview = new TextView(context);
            FlowLayout.LayoutParams param = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textview.setLayoutParams(param);
            textview.setPadding(7,5,7,5);
            textview.setText(datas.get(position).getTagTitle().get(i));
            textview.setBackgroundResource(R.drawable.rect_tag);
            view.line_tag.addView(textview);
        }

        if(position!=0){
            view.img_hot.setVisibility(View.GONE);
            view.text_hot.setVisibility(View.GONE);
        }else{
            view.img_hot.setVisibility(View.VISIBLE);
            view.text_hot.setVisibility(View.VISIBLE);
        }

        view.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iFeed.showDetailFeed(datas.get(position));
            }
        });
        view.line_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iFeed.showDetailFeed(datas.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }



}
