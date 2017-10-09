package supportview;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import model.IPartManager;
import model.ModelPart1;
import model.ModelPart2;
import model.ModelPart5;
import model.ModelPart6;
import model.ModelPart7;
import model.ModelPartCheck;
import sqlite.ManagerPart;
import sqlite.SqlitePart1;
import sqlite.SqlitePart2;
import sqlite.SqlitePart5;
import sqlite.SqlitePart6;
import sqlite.SqlitePart7;

/**
 * Created by dainguyen on 7/16/17.
 */

public class FragmentReview extends Fragment {
    private int mode =0;
    private int part =0;
    private LinearLayout line_parent;
    private RecyclerView recyclerView;
    private View.OnClickListener onClickListener;
    private int isSortId =0;
    private IPartManager iPartManagers[];

    private ArrayList<ArrayList<IPartManager>>arrModel = new ArrayList<>();
    private static MediaPlayer mediaPlayer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    public void getData(){


        Bundle bundle = getArguments();
        if(bundle!=null){
            part = bundle.getInt("part");
            mode = bundle.getInt("mode");
            isSortId = bundle.getInt("sort");
            ManagerPart managerPart = new ManagerPart();
            if(mode==4)iPartManagers = managerPart.searchAllCheckedPart(part);
            else iPartManagers= managerPart.searchAllFavoritePart(part);

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit,container,false);
        setupLayout(view);
        return view;
    }

    public void setupLayout(View view){

        line_parent =(LinearLayout)view.findViewById(R.id.line_parent);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_submit);


        if(iPartManagers.length!=0) {
            setUpModel();
            AdapterReview adapterReview = new AdapterReview();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterReview);
        }else{
            TextView textView1 = new TextView(getContext());
            textView1.setLayoutParams(recyclerView.getLayoutParams());
            textView1.setGravity(Gravity.CENTER);
            textView1.setTextColor(Color.WHITE);
            textView1.setText("Your list is empty!\nCheck question you want remember");
            line_parent.removeView(recyclerView);
            line_parent.addView(textView1);
        }
    }

    public void setUpModel(){
        if(isSortId==1){
            for (int i = 0; i < iPartManagers.length; i++) {
                int k = 0;
                for (int j = 0; j < arrModel.size(); j++) {
                    if (arrModel.get(j).get(0).getId()==iPartManagers[i].getId()) {
                        arrModel.get(j).add(iPartManagers[i]);
                        k = 1;
                        break;
                    }
                }
                if (k == 0) {
                    ArrayList<IPartManager> arr = new ArrayList<>();
                    arr.add(iPartManagers[i]);
                    arrModel.add(arr);
                }
            }
        }else{
            for (int i = 0; i < iPartManagers.length; i++) {
                int k = 0;
                String time = iPartManagers[i].getTime().split(",")[0];
                for (int j = 0; j < arrModel.size(); j++) {
                    String time1= arrModel.get(j).get(0).getTime().split(",")[0];
                    if (time.equals(time1)) {
                        arrModel.get(j).add(iPartManagers[i]);
                        k = 1;
                        break;
                    }
                }
                if (k == 0) {
                    ArrayList<IPartManager> arr = new ArrayList<>();
                    arr.add(iPartManagers[i]);
                    arrModel.add(arr);
                }
            }
        }
    }

    public void setUpControlAudio(){
        if (mediaPlayer != null) {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }



    public void playAudio(String dowload, String url, final ImageView img) {
        File dataAudio = new File(url);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                img.setImageResource(R.mipmap.icon_part1_play);
            }
        });
        if (!dataAudio.exists()) {
            try {

                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(dowload);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class AdapterReview  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ArrayList<Holder>arrholer = new ArrayList<>();
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_part5_review,parent,false);

                    Holder holder = new AdapterReview.Holder(view);
            arrholer.add(holder);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final AdapterReview.Holder holder1 = (AdapterReview.Holder)holder;
            if(part<=5) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                holder1.linearLayout.setLayoutParams(params);
            }
            if(isSortId==1) {
                holder1.line_time.setVisibility(View.GONE);
                holder1.linearLayout.removeAllViews();
                if (part == 5) {
                    SqlitePart5 sqlitePart5 = new SqlitePart5();
                    ModelPart5 data = sqlitePart5.searchPart5Id(arrModel.get(position).get(0).getId())[0];
                    holder1.linearLayout.addView(data.getViewContent(getContext()));

                } else if (part == 6) {
                    SqlitePart6 sqlitePart6 = new SqlitePart6();
                    ModelPart6 data = sqlitePart6.searchPart6Id(arrModel.get(position).get(0).getId());
                    holder1.linearLayout.addView(data.getViewContent(getContext()));

                } else if (part == 7) {
                    SqlitePart7 sqlitePart7 = new SqlitePart7();
                    ModelPart7 data = sqlitePart7.searchPart7Id(arrModel.get(position).get(0).getId());
                    holder1.linearLayout.addView(data.getViewContent(getContext()));
                }else if(part==1){
                    SqlitePart1 sqlitePart1 = new SqlitePart1();
                   final ModelPart1 data = sqlitePart1.searchPart1Id(arrModel.get(position).get(0).getId());
                    View view = data.getViewContent(getContext());
                    holder1.img_play = (ImageView)view.findViewById(R.id.img_play);
                    holder1.linearLayout.addView(view);
                    holder1.img_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setUpControlAudio();
                            playAudio(data.getLinkDowload(),data.getSrcFile(),holder1.img_play);
                            for(int i=0;i<arrholer.size();i++) {
                                if(i==position)  arrholer.get(i).img_play.setImageResource(R.mipmap.icon_part1_stop);
                                else arrholer.get(i).img_play.setImageResource(R.mipmap.icon_part1_play);
                            }

                        }
                    });
                }else if(part==2){
                    SqlitePart2 sqlitePart2 = new SqlitePart2();
                    final ModelPart2  data = sqlitePart2.searchPart2Id(arrModel.get(position).get(0).getId());
                    View view = data.getViewContent(getContext());
                    holder1.img_play = (ImageView) view.findViewById(R.id.img_play);
                    holder1.linearLayout.addView(view);
                    holder1.img_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setUpControlAudio();
                            playAudio(data.getLinkDowload(),data.getSrcFile(),holder1.img_play);
                            for(int i=0;i<arrholer.size();i++) {
                                if(i==position)  arrholer.get(i).img_play.setImageResource(R.mipmap.icon_part1_stop);
                                else arrholer.get(i).img_play.setImageResource(R.mipmap.icon_part1_play);
                            }

                        }
                    });
                }


                setupRecycleChild(holder1,position);

            }else{
                ((AdapterReview.Holder) holder).linearLayout.setVisibility(View.GONE);
                if(getItemCount()>=5 && position!=0)holder1.recyclerView.setVisibility(View.GONE);
                else{
                    holder1.recyclerView.setVisibility(View.VISIBLE);
                    setupRecycleChild(holder1,position);
                }
                holder1.text_time.setText(arrModel.get(position).get(0).getTime().split(",")[0]);
                holder1.line_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder1.recyclerView.getVisibility()==View.GONE){
                            holder1.recyclerView.setVisibility(View.VISIBLE);
                            setupRecycleChild(holder1,position);
                        }else{
                            holder1.recyclerView.setVisibility(View.GONE);
                        }
                    }
                });
            }



            onClickListener =new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if(part>=5)intent = new Intent(getActivity() , PartPractiseAcitvity.class);
                    else intent=  new Intent(getActivity(),ListenPraticseActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("mode",mode);
                    bundle.putInt("part",part);
                    if(mode==3){
                        bundle.putString("title","Favorite");
                        bundle.putInt("key",1);
                        bundle.putInt("position",position);
                    }
                    else{
                        bundle.putString("title","History");
                        bundle.putInt("key",2);
                        bundle.putInt("position",position);
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            };


        }

        public void setupRecycleChild(AdapterReview.Holder holder1, int position){
            LinearLayoutManager layoutManager;
            Boolean b = false;
            if(mode==3)b= true;

            layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,b);


            AdapterReview.AdapterCheck adapterCheck = new AdapterReview.AdapterCheck(setUpDataChild(position));
            holder1.recyclerView.setLayoutManager(layoutManager);
            holder1.recyclerView.setAdapter(adapterCheck);
        }


        public ArrayList<ArrayList<IPartManager>> setUpDataChild(int postion){
            if(isSortId==1){
                ArrayList<ArrayList<IPartManager>>datapart = new ArrayList<>();
                int num=0;
                for (int i = 0; i < arrModel.get(postion).size(); i++) {
                    int k = 0;
                    for (int j = 0; j < num; j++) {
                        if (datapart.get(j).get(0).getTime().equals(arrModel.get(postion).get(i).getTime())) {
                            datapart.get(j).add(arrModel.get(postion).get(i));
                            k = 1;
                            break;
                        }
                    }
                    if (k == 0) {
                        ArrayList<IPartManager> arr = new ArrayList<>();
                        arr.add(arrModel.get(postion).get(i));
                        datapart.add(arr);
                        num++;
                    }
                }

                return datapart;
            }else{
                ArrayList<ArrayList<IPartManager>>datapart = new ArrayList<>();
                int num=0;
                for (int i = 0; i < arrModel.get(postion).size(); i++) {
                    int k = 0;
                    for (int j = 0; j <num; j++) {
                        if (datapart.get(j).get(0).getId()==arrModel.get(postion).get(i).getId() &&
                                datapart.get(j).get(0).getTime().equals(arrModel.get(postion).get(i).getTime())){
                            datapart.get(j).add(arrModel.get(postion).get(i));
                            k = 1;
                            break;
                        }
                    }
                    if (k == 0) {
                        ArrayList<IPartManager> arr = new ArrayList<>();
                        arr.add(arrModel.get(postion).get(i));
                        datapart.add(arr);
                        num++;
                    }
                }

                return datapart;
            }
        }
        @Override
        public int getItemCount() {
            return arrModel.size();

        }

        public class Holder extends RecyclerView.ViewHolder{

            public LinearLayout linearLayout;
            public RecyclerView recyclerView;
            public LinearLayout line_time;
            public TextView text_time;
            public ImageView img_play;
            public Holder(View itemView) {
                super(itemView);
                recyclerView = (RecyclerView) itemView.findViewById(R.id.recycle_check);
                linearLayout = (LinearLayout)itemView.findViewById(R.id.line_content);
                text_time = (TextView)itemView.findViewById(R.id.text_time);
                line_time = (LinearLayout)itemView.findViewById(R.id.line_time);

            }
        }

        public class  AdapterCheck extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

            ArrayList<ArrayList<IPartManager>>datachild;
            private ArrayList<Holder>arrayList= new ArrayList<>();
            public AdapterCheck(ArrayList<ArrayList<IPartManager>>data) {
                this.datachild = data;
            }
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_review_child,parent,false);
                Holder holder =new AdapterReview.AdapterCheck.Holder(view);
                arrayList.add(holder);
                return holder;

            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

                if(isSortId==1){
                    ((Holder)holder).text_bot.setVisibility(View.GONE);
                    TextView textView = new TextView(getContext());
                    if(part==1)textView.setTextColor(Color.WHITE);
                    else  textView.setTextColor(Color.GREEN);
                    textView.setText(datachild.get(position).get(0).getTime());
                    ((AdapterReview.AdapterCheck.Holder)holder).line_time.addView(textView);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.LEFT_OF,R.id.line_check);


                    ((Holder)holder).line_time.setLayoutParams(params);
                    //((Holder)holder).line_time.setGravity(Gravity.CENTER);

                }
                else {
                    ((Holder)holder).line_time.removeAllViews();
                    if(part<=5){
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.LEFT_OF,R.id.line_check);
                        ((Holder)holder).line_time.setLayoutParams(params);
                        //((Holder)holder).line_time.setGravity(Gravity.CENTER);
                    }
                    int id = datachild.get(position).get(0).getId();
                    View view =null;
                    if(part==5){
                        SqlitePart5 sqlitePart5 = new SqlitePart5();
                        view =sqlitePart5.searchPart5Id(id)[0].getViewContent(getContext());
                    }else if(part==6){
                        SqlitePart6 sqlitePart6 = new SqlitePart6();
                        view =sqlitePart6.searchPart6Id(id).getViewContent(getContext());
                    }else if(part==7){
                        SqlitePart7 sqlitePart7 = new SqlitePart7();
                        view =sqlitePart7.searchPart7Id(id).getViewContent(getContext());
                    }else if(part==1) {
                        SqlitePart1 sqlitePart1 = new SqlitePart1();
                        final ModelPart1 data = sqlitePart1.searchPart1Id(id);
                        view = data.getViewContent(getContext());
                        ((Holder)holder).img_play = (ImageView)view.findViewById(R.id.img_play);
                        ((Holder)holder).img_play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setUpControlAudio();
                                playAudio(data.getLinkDowload(),data.getSrcFile(),((Holder)holder).img_play);
                                for(int i=0;i<arrayList.size();i++) {
                                    if(i==position)  arrayList.get(i).img_play.setImageResource(R.mipmap.icon_part1_stop);
                                    else arrayList.get(i).img_play.setImageResource(R.mipmap.icon_part1_play);
                                }

                            }
                        });
                    }else if(part==2){
                        SqlitePart2 sqlitePart2 = new SqlitePart2();
                        final ModelPart2  data = sqlitePart2.searchPart2Id(id);
                         view = data.getViewContent(getContext());
                        ((Holder)holder).img_play = (ImageView)view.findViewById(R.id.img_play);
                        ((Holder)holder).img_play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setUpControlAudio();
                                playAudio(data.getLinkDowload(),data.getSrcFile(),((Holder)holder).img_play);
                                for(int i=0;i<arrayList.size();i++) {
                                    if(i==position)  arrayList.get(i).img_play.setImageResource(R.mipmap.icon_part1_stop);
                                    else arrayList.get(i).img_play.setImageResource(R.mipmap.icon_part1_play);
                                }

                            }
                        });
                    }else {
                        view = new TextView(getContext());
                        ((TextView)view).setText("Question " + String.valueOf(id));
                    }

                    ((Holder)holder).line_time.addView(view);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((Holder) holder).tab_control.getVisibility()==View.GONE){
                            ((Holder) holder).tab_control.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_heart_in);

                            ((Holder) holder).tab_control.startAnimation(animation);

                        }else{
                            Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_heart_out);
                            animation.setFillAfter(true);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    ((Holder) holder).tab_control.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ((Holder) holder).tab_control.startAnimation(animation);


                        }
                    }
                });

                ((Holder) holder).img_review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });
                ((Holder) holder).img_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int id = datachild.get(holder.getLayoutPosition()).get(0).getId();
                        String time = datachild.get(holder.getLayoutPosition()).get(0).getTime();
                        ManagerPart managerPart = new ManagerPart();
                       if(mode==4) managerPart.deletePartCheckIdTime(part,id,time);
                        else managerPart.deletePartFavoriteIdTime(part,id,time);
                        datachild.remove(holder.getLayoutPosition());
                        AdapterCheck.this.notifyItemRemoved(holder.getLayoutPosition());


                    }
                });
                ((Holder) holder).line_check.removeAllViews();
                for(int i=0;i<datachild.get(position).size();i++) {
                    if(datachild.get(position).get(i).getResult()==-1)break;
                    ImageView imageView = new ImageView(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    imageView.setLayoutParams(params);
                    if (datachild.get(position).get(i).getResult() == 1) {
                        imageView.setBackgroundResource(R.mipmap.icon_true);
                    } else {
                        imageView.setBackgroundResource(R.mipmap.icon_false);
                    }
                    ((Holder) holder).line_check.addView(imageView);
                }
            }

            @Override
            public int getItemCount() {
                return datachild.size();
            }

            public class Holder extends RecyclerView.ViewHolder{
                public LinearLayout line_check;

                public LinearLayout line_time;
                public TableLayout tab_control;
                public ImageView img_remove;
                public ImageView img_review;
                public TextView text_bot;
                public ImageView img_play;
                public Holder(View itemView) {
                    super(itemView);
                    line_check = (LinearLayout) itemView.findViewById(R.id.line_check);
                    if((part==6 || part==7)&& isSortId==1)line_check.setOrientation(LinearLayout.HORIZONTAL);
                    line_time =(LinearLayout) itemView.findViewById(R.id.line_time);
                    text_bot =(TextView)itemView.findViewById(R.id.text_line_bot);
                    tab_control =(TableLayout)itemView.findViewById(R.id.line_control);
                    img_remove = (ImageView)itemView.findViewById(R.id.img_remove);
                    img_review =(ImageView)itemView.findViewById(R.id.img_review);
                    if(isSortId==0){
                        if(part==1 ||part==2)text_bot.setVisibility(View.GONE);
                    }

                }


            }
        }
    }
}
