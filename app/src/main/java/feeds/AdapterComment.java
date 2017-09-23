package feeds;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;

import model.ModelComment;
import model.ModelFeed;
import model.PostTag;
import part7.Fragment67Content;
import supportview.ConvertTagView;
import supportview.DowloadTask;
import supportview.FlowLayout;
import supportview.FragmentReview;
import supportview.ListenPraticseActivity;

/**
 * Created by dainguyen on 8/26/17.
 */

public class AdapterComment extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DowloadTask.IDowload{
    private Context context;
    private ModelFeed data;
    private ArrayList<ModelComment>arrComment;
    public static int RESULT_LOAD_IMAGE =80;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private ImageView img_play;

    public IFeed iFeed;
    private boolean prepare_audio=false;
    private HolderView currentview = null;
    private AdapterReply.HolderCellReply currentreply = null;
    private final String LINKAUDIO="http://vntoeic.xyz/api/v1/posts/";
    private final String LINKSRC="/data/user/0/com.vntoeic.bkteam.vntoeicpro/feed/audio/xxx.mp3";

    public AdapterComment(Context context, ModelFeed modelFeed, ArrayList<ModelComment>arrComment){
        this.context = context;
        this.data = modelFeed;
        this.arrComment = arrComment;
        DowloadTask dowloadTask = new DowloadTask();
        dowloadTask.setiDowload(AdapterComment.this);
        dowloadTask.execute("0",LINKAUDIO+String.valueOf(data.getPostId())+"/audio",LINKSRC,"");
    }

    public void setiFeed(IFeed iFeed){
        this.iFeed = iFeed;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(true){
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_feed,parent,false);
            return new HolderView(context,view);
        }
        return null;
    }

    public void addImageGallery(InputStream path){
        currentview.createViewImagefromGallery(path);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position==0){
            setUpLayoutPost(holder);
        }else {
            setLayoutPost1(holder,position-1);
        }
    }

    public void setLayoutPost1(RecyclerView.ViewHolder holder, final int position){
        final HolderView view = (HolderView) holder;
        arrComment.get(position).setDataUser();
        view.text_title.setVisibility(View.GONE);
        view.text_user.setText("- "+arrComment.get(position).getUser().getName() + "   " +data.getTime());
        view.text_like_user.setText(String.valueOf(arrComment.get(position).getUser().getLike()));
        view.text_unlike_user.setText(String.valueOf(arrComment.get(position).getUser().getUnlike()));
        view.text_like_post.setText(String.valueOf(arrComment.get(position).getLike()));
        view.text_unlike_post.setText(String.valueOf(arrComment.get(position).getUnlike()));
        view.text_count_comment.setText("Reply");
        view.text_subcribe.setVisibility(View.VISIBLE);

        view.text_subcribe.setVisibility(View.GONE);
        view.img_star.setVisibility(View.GONE);

        view.img_like_post.setImageResource(R.mipmap.approve_icon);
        view.img_unlike_post.setImageResource(R.mipmap.disapprove_con);

        if(arrComment.get(position).isApprove()){
            view.img_like_post.setImageResource(R.mipmap.approved_icon);
        }

        if(arrComment.get(position).isDisapprove()){
            view.img_unlike_post.setImageResource(R.mipmap.disapproved_icon);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_like_post:
                        if(arrComment.get(position).isApprove()){
                            view.img_like_post.setImageResource(R.mipmap.approve_icon);
                            SendToSever sendToSever = new SendToSever(1);
                            sendToSever.execute(arrComment.get(position).getLinkApprove());
                            arrComment.get(position).setLike(arrComment.get(position).getLike()-1);
                            arrComment.get(position).setApprove(false);

                        }else{
                            view.img_like_post.setImageResource(R.mipmap.approved_icon);
                            view.img_unlike_post.setImageResource(R.mipmap.disapprove_con);
                            SendToSever sendToSever = new SendToSever(0);
                            sendToSever.execute(arrComment.get(position).getLinkApprove());
                            arrComment.get(position).setLike(arrComment.get(position).getLike()+1);
                            if(arrComment.get(position).isDisapprove()) arrComment.get(position).setUnlike(arrComment.get(position).getUnlike()-1);
                            arrComment.get(position).setApprove(true);
                            arrComment.get(position).setDisapprove(false);

                        }
                        break;
                    case R.id.img_unlike_post:
                        if(arrComment.get(position).isDisapprove()){
                            view.img_unlike_post.setImageResource(R.mipmap.disapprove_con);
                            SendToSever sendToSever = new SendToSever(1);
                            sendToSever.execute(arrComment.get(position).getLinkDisApprove());
                            arrComment.get(position).setDisapprove(false);
                            arrComment.get(position).setUnlike(arrComment.get(position).getUnlike()-1);
                        }else{
                            view.img_unlike_post.setImageResource(R.mipmap.disapproved_icon);
                            view.img_like_post.setImageResource(R.mipmap.approve_icon);
                            SendToSever sendToSever = new SendToSever(0);
                            sendToSever.execute(arrComment.get(position).getLinkDisApprove());
                            if(arrComment.get(position).isApprove())arrComment.get(position).setLike(arrComment.get(position).getLike()-1);
                            arrComment.get(position).setUnlike(arrComment.get(position).getUnlike()+1);
                            arrComment.get(position).setDisapprove(true);
                            arrComment.get(position).setApprove(false);
                        }
                        break;
                }
                view.text_like_post.setText(String.valueOf(arrComment.get(position).getLike()));
                view.text_unlike_post.setText(String.valueOf(arrComment.get(position).getUnlike()));
            }
        };
        view.img_like_post.setOnClickListener(onClickListener);
        view.img_unlike_post.setOnClickListener(onClickListener);

        view.createViewAvatar(arrComment.get(position).getUser().getLinkAvatar());
        view.line_image.removeAllViews();
        int f=0;
        for(int i =0;i<arrComment.get(position).getContents().size();i++){
            if(arrComment.get(position).getContents().get(i).getType()==1) {
                view.createViewImage(data.getContents().get(i).getSource());
                f=1;
            }else if(arrComment.get(position).getContents().get(i).getType()==0){
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(param);
                ConvertTagView convertTagView = new ConvertTagView(context,arrComment.get(position).getContents().get(i).getSource().get(0));
                textView.setText(convertTagView.getSpannableString());
                view.line_image.addView(textView);
            }else{
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(param);
                int W = context.getResources().getDisplayMetrics().widthPixels;
                Picasso.with(context).load(arrComment.get(position).getContents().get(i).getSource().get(0)).centerInside().resize(W,W).into(imageView);
                view.line_image.addView(imageView);
            }
        }
        if(f==1){
            ArrayList<String>src= new ArrayList<>();
            for(int i =0;i<arrComment.get(position).getContents().size();i++){
                if(arrComment.get(position).getContents().get(i).getType()==1) {
                    for(int j=0;j<arrComment.get(position).getContents().get(i).getSource().size();j++){
                        src.add(arrComment.get(position).getContents().get(i).getSource().get(j));
                    }
                }
            }
            for(int i =0;i<view.line_image.getChildCount();i++){
                View view1 = view.line_image.getChildAt(i);
                if(view1 instanceof TableLayout){
                    setEventShowImageFull(view1,src);
                }
            }

        }
        view.line_tag.removeAllViews();
        if(position==0) {
            view.img_hot.setVisibility(View.VISIBLE);
            view.text_hot.setVisibility(View.VISIBLE);
        }else {
            view.img_hot.setVisibility(View.GONE);
            view.text_hot.setVisibility(View.GONE);
        }

        setEventComment(view);
        setEventCamera(view);
        setEventEditComment(view,arrComment.get(position).getUser_id());

        if(arrComment.get(position).getArrReplies().size()>0){
            view.recyle_reply.setVisibility(View.VISIBLE);
            AdapterReply adapterReply = new AdapterReply(position);
            LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            view.recyle_reply.setLayoutManager(manager);
            view.recyle_reply.setAdapter(adapterReply);
        }else{
            view.recyle_reply.setVisibility(View.GONE);
        }

        setEventEditAndDelete(view.text_edit,view.text_delete,2,"",arrComment.get(position).getContents(),null,null);
    }

    public void setUpLayoutPost(RecyclerView.ViewHolder holder){
        final HolderView view = (HolderView) holder;
        data.setDataUser();
        view.text_title.setText(data.getTitle());
        view.text_user.setText("ask by -"+data.getUser().getName() + "   " +data.getTime());
        view.text_like_user.setText(String.valueOf(data.getUser().getLike()));
        view.text_unlike_user.setText(String.valueOf(data.getUser().getUnlike()));
        view.text_like_post.setText(String.valueOf(data.getApproves()));
        view.text_unlike_post.setText(String.valueOf(data.getDisapproves()));
        view.text_count_comment.setText("Comment");
        view.text_subcribe.setVisibility(View.VISIBLE);
        if(data.isSub()){
            view.text_subcribe.setText("unsubcribe");
            view.img_star.setImageResource(R.mipmap.yellow_smallstar);
            // send sever
        } else {
            view.text_subcribe.setText("subcribe");
            view.img_star.setImageResource(R.mipmap.gray_smallstar);
            // send sever
        }
        View.OnClickListener onClickSubcribe = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag =0;
                if(data.isSub()){
                    flag=1;
                    data.setSub(false);
                    view.text_subcribe.setText("subcribe");
                    view.img_star.setImageResource(R.mipmap.gray_smallstar);
                }else{
                    data.setSub(true);
                    view.text_subcribe.setText("unsubcribe");
                    view.img_star.setImageResource(R.mipmap.yellow_smallstar);
                }
                SendToSever sendToSever=new SendToSever(flag);
                sendToSever.execute(data.getLinkSubcribe());
            }
        };

        view.text_subcribe.setOnClickListener(onClickSubcribe);
        view.img_star.setOnClickListener(onClickSubcribe);





        view.img_like_post.setImageResource(R.mipmap.approve_icon);

        view.img_unlike_post.setImageResource(R.mipmap.disapprove_con);

        if(data.isApp()){
            view.img_like_post.setImageResource(R.mipmap.approved_icon);
        }

        if(data.isDisapp()){
            view.img_unlike_post.setImageResource(R.mipmap.disapproved_icon);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_like_post:
                        if(data.isApp()){
                            view.img_like_post.setImageResource(R.mipmap.approve_icon);
                            SendToSever sendToSever = new SendToSever(1);
                            sendToSever.execute(data.getLinkApprove());
                            data.setApproves(data.getApproves()-1);
                            data.setApp(false);

                        }else{
                            view.img_like_post.setImageResource(R.mipmap.approved_icon);
                            view.img_unlike_post.setImageResource(R.mipmap.disapprove_con);
                            SendToSever sendToSever = new SendToSever(0);
                            sendToSever.execute(data.getLinkApprove());
                            data.setApproves(data.getApproves()+1);
                            if(data.isDisapp())data.setDisapproves(data.getDisapproves()-1);
                            data.setApp(true);
                            data.setDisapp(false);
                        }
                        break;
                    case R.id.img_unlike_post:
                        if(data.isDisapp()){
                            view.img_unlike_post.setImageResource(R.mipmap.disapprove_con);
                            SendToSever sendToSever = new SendToSever(1);
                            sendToSever.execute(data.getLinkDisApprove());
                            data.setDisapp(false);
                            data.setDisapproves(data.getDisapproves()-1);
                        }else{
                            view.img_unlike_post.setImageResource(R.mipmap.disapproved_icon);
                            view.img_like_post.setImageResource(R.mipmap.approve_icon);
                            SendToSever sendToSever = new SendToSever(0);
                            sendToSever.execute(data.getLinkDisApprove());
                            data.setDisapproves(data.getDisapproves()+1);
                            if(data.isApp())data.setApproves(data.getApproves()-1);
                            data.setDisapp(true);
                            data.setApp(false);
                        }
                        break;
                }
                view.text_like_post.setText(String.valueOf(data.getApproves()));
                view.text_unlike_post.setText(String.valueOf(data.getDisapproves()));
            }
        };
        view.img_like_post.setOnClickListener(onClickListener);
        view.img_unlike_post.setOnClickListener(onClickListener);

        view.createViewAvatar(data.getUser().getLinkAvatar());
        view.line_image.removeAllViews();
         view.createViewContent(data);


        ArrayList<String>src= data.getArraySrcImage();
        if(src.size()>0){
            for(int i =0;i<view.line_image.getChildCount();i++){
                View view1 = view.line_image.getChildAt(i);
                if(view1 instanceof TableLayout){
                    setEventShowImageFull(view1,src);
                }
            }

        }

        // audio

        if(data.isAudio()){
            View v = LayoutInflater.from(context).inflate(R.layout.cell_audio_feed,null,false);
            final ImageView img_play =(ImageView)v.findViewById(R.id.img_play);
            TextView text_notify =(TextView)v.findViewById(R.id.text_notify);
            seekBar =(SeekBar)v.findViewById(R.id.seekbar_audio);
            text_notify.setVisibility(View.GONE);
            seekBar.setVisibility(View.VISIBLE);
            img_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mediaPlayer==null){
                        if(prepare_audio) {
                            playAudio();
                            img_play.setBackgroundResource(R.mipmap.ic_stop_feed);
                        }else{
                            Toast.makeText(context,"Dowload audio fail.Please check your internet!",Toast.LENGTH_LONG).show();
                            DowloadTask dowloadTask = new DowloadTask();
                            dowloadTask.setiDowload(AdapterComment.this);
                            dowloadTask.execute("0",LINKAUDIO+String.valueOf(data.getPostId()),LINKSRC,"");
                        }
                    }else {
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                            img_play.setBackgroundResource(R.mipmap.icon_play_feed);
                        }else{
                            mediaPlayer.start();
                            img_play.setBackgroundResource(R.mipmap.ic_stop_feed);
                        }
                    }
                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.seekTo(progress*1000);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            view.line_image.addView(v);
        }
        // end event for audio
        view.line_tag.removeAllViews();
        for(int i =0;i<data.getTagTitle().size();i++){
            TextView textview = new TextView(context);
            FlowLayout.LayoutParams param = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textview.setLayoutParams(param);
            textview.setPadding(7,5,7,5);
            textview.setText(data.getTagTitle().get(i));
            textview.setBackgroundResource(R.drawable.rect_tag);
            view.line_tag.addView(textview);
        }
        view.img_hot.setVisibility(View.GONE);
        view.text_hot.setVisibility(View.GONE);
        view.recyle_reply.setVisibility(View.GONE);
        setEventComment(view);
        setEventCamera(view);
        setEventEditComment(view,data.getUserId());
        setEventEditAndDelete(view.text_edit,view.text_delete,1,data.getTitle(),data.getContents(),data.getTagIds(),data.getTagTitle());


    }
    public void playAudio() {
        File dataAudio = new File(LINKSRC);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(img_play!=null) {
                    img_play.setBackgroundResource(R.mipmap.icon_play_feed);
                }
            }
        });
        if (dataAudio.exists()) {
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(LINKSRC);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            seekBar.setMax((mediaPlayer.getDuration()) / 1000);
            Timer timer = new Timer();
            MyTimerTask timerTask = new MyTimerTask();
            timer.schedule(timerTask, 0, 200);
        }

    }

    @Override
    public void notifySuccess(int numbertask, boolean b, String url, String file) {
        prepare_audio=b;
    }

    public class MyTimerTask extends java.util.TimerTask {

        @Override
        public void run() {
            if (seekBar!=null&&mediaPlayer != null && mediaPlayer.isPlaying()) {
                final int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        seekBar.setProgress(mCurrentPosition);
                    }
                });

            }
        }
    }

    public void setEventShowImageFull(View linearLayout, final ArrayList<String>src){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iFeed.showImageFull(src);
            }
        };
        linearLayout.setOnClickListener(onClickListener);
    }

    public void setEventCamera(final HolderView view){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentview = view;
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ((AppCompatActivity)context).startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        };
        view.img_camera.setOnClickListener(onClickListener);

    }

    public void setEventComment(final HolderView view){
        View.OnClickListener onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(view.line_comment.getVisibility()== View.GONE){
                        view.line_comment.setVisibility(View.VISIBLE);
                        view.img_camera.setVisibility(View.VISIBLE);
                        if(view.isEdit==1)view.img_edit.setVisibility(View.GONE);
                        setDefault();
                         currentview = view;

                }else{
                    setDefault();
                    if(view.isEdit==1)view.img_edit.setVisibility(View.VISIBLE);
                }

            }
        };
        view.text_count_comment.setOnClickListener(onClickListener);

    }

    public void setDefault(){
        if(currentview!=null){
            currentview.img_camera.setVisibility(View.GONE);
            currentview.line_comment.setVisibility(View.GONE);
            if(currentview.isEdit==1)currentview.img_edit.setVisibility(View.VISIBLE);
            currentview =null;
        }
    }

    public void setEventEditComment(final  HolderView view , int user_id){
        if(true){
            view.setIsEdit(1);
            view.img_edit.setVisibility(View.VISIBLE);
            view.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(view.line_edit.getVisibility()==View.GONE){
                        view.line_edit.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_search);
                        view.line_edit.startAnimation(animation);
                    }else{
                        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_search_out);
                        animation.setFillAfter(false);
                        view.line_edit.startAnimation(animation);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                    view.line_edit.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }
            });
        }else{
            view.setIsEdit(0);
            view.img_edit.setVisibility(View.GONE);
        }
    }

    public void setEventEditAndDelete(TextView text_edit, TextView text_delete,final int flag, final String title, final ArrayList<Content>contents, final ArrayList<Integer>tags, final ArrayList<String>stags){
        text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putSerializable("contents",contents);
                ArrayList<PostTag>arr = new ArrayList<PostTag>();
                for(int i=0;i<stags.size();i++){
                    arr.add(new PostTag(tags.get(i),stags.get(i),0));
                }
                bundle.putSerializable("selected",arr);
                bundle.putString("title",title);
                bundle.putInt("flag",flag);
                iFeed.editComment(bundle);
            }
        });

        text_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iFeed.deleteComment();
            }
        });
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0)return 1;
        else if(position==1)return 2;
        return 3;
    }

    @Override
    public int getItemCount() {
        return arrComment.size()+1;
    }


    public class AdapterReply extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public int index_comment;
        public AdapterReply(int intdex_comment){
            this.index_comment = intdex_comment;
        }
        @Override

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cell_reply_comment,parent,false);
            return new HolderCellReply(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final HolderCellReply myholder = (HolderCellReply) holder;
            ArrayList<ModelComment.Reply> data = arrComment.get(index_comment).getArrReplies();
            myholder.text_user.setText(" - "+data.get(position).getUser().getName());
            myholder.text_time.setText(data.get(position).getTime());
            myholder.line_image.removeAllViews();
            for (int i = 0; i < data.get(position).getArrcontent().size(); i++) {

                if (data.get(position).getArrcontent().get(i).getType() == 1) {
                    myholder.createViewImage(data.get(position).getArrcontent().get(i).getSource());
                } else if(data.get(position).getArrcontent().get(i).getType()==0) {
                    TextView textView = new TextView(context);
                    textView.setTextColor(Color.BLUE);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(param);
                    ConvertTagView convertTagView = new ConvertTagView(context,data.get(position).getArrcontent().get(i).getSource().get(0));
                    textView.setText(convertTagView.getSpannableString());
                    myholder.line_image.addView(textView);
                }else{
                    ImageView imageView = new ImageView(context);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    imageView.setLayoutParams(param);
                    int W = context.getResources().getDisplayMetrics().widthPixels;
                    Picasso.with(context).load(data.get(position).getArrcontent().get(i).getSource().get(0)).centerInside().resize(W,W).into(imageView);
                    myholder.line_image.addView(imageView);
                }

            }
            if(true){
                myholder.img_edit.setVisibility(View.VISIBLE);
                myholder.img_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myholder.line_edit.getVisibility()==View.GONE){
                            myholder.line_edit.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_search);
                            myholder.line_edit.startAnimation(animation);
                        }else{
                            Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_search_out);
                            myholder.line_edit.startAnimation(animation);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    myholder.line_edit.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }
                });
            }else{
                myholder.img_edit.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return arrComment.get(index_comment).getArrReplies().size();
        }

        public class  HolderCellReply extends RecyclerView.ViewHolder{
            public LinearLayout line_image;
            public TextView text_user;
            public TextView text_time;
            public ImageView img_edit;
            public LinearLayout line_edit;
            public TextView text_edit;
            public TextView text_delete;
            public HolderCellReply(View itemView) {
                super(itemView);
                line_image = (LinearLayout)itemView.findViewById(R.id.line_image);
                text_user =(TextView)itemView.findViewById(R.id.text_user);
                text_time = (TextView)itemView.findViewById(R.id.text_time);
                img_edit = (ImageView)itemView.findViewById(R.id.img_edit);
                line_edit =(LinearLayout)itemView.findViewById(R.id.line_edit);
                text_edit =(TextView)itemView.findViewById(R.id.text_edit);
                text_delete =(TextView)itemView.findViewById(R.id.text_delete);
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
                        Picasso picasso =Picasso.with(context);
                        picasso.setIndicatorsEnabled(true);
                        picasso.load(src.get(i)).resize(imgWr1,imgWr1).centerCrop().into(imageView);

                    }
                }

            }

        }
    }

}
