package dictionary;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.AdapterWordSearch;
import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.Content;
import model.Example;
import model.Meanings;

/**
 * Created by dainguyen on 5/30/17.
 */

public class AdapterDiction extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int flag =0;
    private ArrayList<Content>contents;
    private ArrayList<Meanings>meaning;
    private Meanings examples;
    private ArrayList<String> meaningChild;
    private ArrayList<Example>extend;
    public static ArrayList<Myhoder2>arrHoder = new ArrayList<>();
    public static ArrayList<String>arrMeaningSave ;


    public AdapterDiction(Context context){
        this.context = context;
    }

    public void setArrMeaningSave(ArrayList<String>arr){
        this.arrMeaningSave = arr;
    }

    public void setMeaning(ArrayList<Meanings>data){
        this.flag=1;
        this.meaning = data;
    }

    public void setContent (ArrayList<Content> data){
        this.flag=0;
        this.contents = data;
    }

    public void setExamples(Meanings data){
        this.flag= 2;
        this.examples = data;
    }

    public void setExtend(ArrayList<Example>data){
        this.flag =3;
        this.extend = data;
    }

    public void setMeaningChild(ArrayList<String>data){
        this.flag =4;
        this.meaningChild = data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(flag==0){
            view= LayoutInflater.from(context).inflate(R.layout.cell_content_dictionary,parent,false);
            return new MyHoder(view);
        }else if(flag==1){
            view = LayoutInflater.from(context).inflate(R.layout.cell_meaning_diction,parent,false);
            return new Myhoder1(view);
        }else if(flag==2){
            view = LayoutInflater.from(context).inflate(R.layout.cell_meaning_child_diction,parent,false);
            Myhoder2 myhoder2 = new Myhoder2(view);
            arrHoder.add(myhoder2);
            return myhoder2;
        }else if(flag==4){
            view = LayoutInflater.from(context).inflate(R.layout.cell_meaning_example,parent,false);
            return new Myhoder3(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.cell_meaning_child_diction,parent,false);
            return new Myhoder2(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==0){
            setLayoutContent(holder,position);
        }else if(getItemViewType(position)==1){
            setLayoutMeaning(holder,position);
        }else if(getItemViewType(position)==2){
            setLayoutMeaningChild(holder,position);
        }else  if(getItemViewType(position)==4){
            setLayoutMeaningExample(holder,position);
        }else setLayoutExtend(holder,position);
    }



    @Override
    public int getItemViewType(int position) {
        return flag;
    }

    @Override
    public int getItemCount() {

        if(flag==0)return contents.size();
        else if(flag==1)return meaning.size();
        else if(flag==2) return examples.Example.size();
        else if(flag==3)return extend.size();
        else return meaningChild.size();

    }

    public void setLayoutMeaning(RecyclerView.ViewHolder holder,int position){
        Myhoder1 myhoder = (Myhoder1)holder;
        myhoder.text_type.setText(meaning.get(position).type);
        String s = meaning.get(position).type;

         if(s.contains("danh"))myhoder.img_type.setImageResource(R.mipmap.noun);
        else if(s.contains("động"))myhoder.img_type.setImageResource(R.mipmap.verb);
        else if(s.contains("tính")) myhoder.img_type.setImageResource(R.mipmap.adj);
        else if(s.contains("giới"))myhoder.img_type.setImageResource(R.mipmap.pre);
        else if(s.contains("phó"))myhoder.img_type.setImageResource(R.mipmap.adv);
        else if(s.contains("liên"))myhoder.img_type.setImageResource(R.mipmap.conj);
        else myhoder.img_type.setImageResource(R.mipmap.none);

        AdapterDiction adaptermean = new AdapterDiction(context);
        adaptermean.setExamples(meaning.get(position));

        AdapterDiction adapterextend = new AdapterDiction(context);
        adapterextend.setExtend(meaning.get(position).extend);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);


        myhoder.recycle_mean.setLayoutManager(linearLayoutManager);
        myhoder.recycle_mean.setAdapter(adaptermean);

        myhoder.recycler_extend.setLayoutManager(linearLayoutManager1);
        myhoder.recycler_extend.setAdapter(adapterextend);


    }

    public void setLayoutContent(RecyclerView.ViewHolder holder,int position){
        MyHoder myHoder = (MyHoder)holder;
        AdapterDiction adapterDiction = new AdapterDiction(context);
        adapterDiction.setMeaning(contents.get(position).getMeaningses());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        myHoder.recyclerView.setLayoutManager(linearLayoutManager);
        myHoder.recyclerView.setAdapter(adapterDiction);



    }

    private void setLayoutExtend(RecyclerView.ViewHolder holder, int position) {
        final Myhoder2 myhoder = (Myhoder2)holder;
        myhoder.text_mean.setText(extend.get(position).getMean());
        myhoder.text_mean.setTextColor(context.getResources().getColor(R.color.text_example));
        myhoder.img_mean.setBackgroundResource(R.mipmap.icon_assign);
        RecyclerView recycle = new RecyclerView(context);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        AdapterDiction adapter = new AdapterDiction(context);
        adapter.setMeaningChild(extend.get(position).exam);
        recycle.setLayoutManager(manager);
        recycle.setAdapter(adapter);
        myhoder.linearLayout.addView(recycle);
    }

    public void setLayoutMeaningChild(RecyclerView.ViewHolder holder, final int position){
        final Myhoder2 myhoder = (Myhoder2)holder;
        myhoder.text_mean.setText(examples.Example.get(position).getMean());

        if(arrMeaningSave.contains(examples.Example.get(position).getMean())){
            myhoder.text_mean.setTextColor(Color.RED);
        }
            if (examples.Example.get(position).exam.size() == 0) {
                myhoder.img_mean.setBackgroundResource(R.mipmap.icon_assign);
                myhoder.idimgold = R.mipmap.icon_assign;
            } else {
                myhoder.idimgold = R.mipmap.icon_explan;
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myhoder.linearLayout.getChildCount() == 0) {
                            RecyclerView recyclerView = new RecyclerView(context);
                            AdapterDiction adapterDiction = new AdapterDiction(context);
                            adapterDiction.setMeaningChild(examples.Example.get(position).exam);
                            LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapterDiction);
                            myhoder.linearLayout.addView(recyclerView);
                        } else {
                            myhoder.linearLayout.removeAllViews();
                        }
                    }
                };
                myhoder.text_mean.setOnClickListener(onClickListener);
            }


        myhoder.img_mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(myhoder.flag==1){
                    if(arrMeaningSave.contains(examples.Example.get(position).getMean())){
                        myhoder.img_mean.setBackgroundResource(R.mipmap.icon_heart_while);
                        arrMeaningSave.remove(examples.Example.get(position).getMean());
                        myhoder.text_mean.setTextColor(Color.WHITE);
                    }else{
                        myhoder.img_mean.setBackgroundResource(R.mipmap.icon_heart);
                        arrMeaningSave.add(examples.Example.get(position).getMean());
                        myhoder.text_mean.setTextColor(Color.RED);
                    }
                }else if (myhoder.linearLayout.getChildCount() == 0) {
                    RecyclerView recyclerView = new RecyclerView(context);
                    AdapterDiction adapterDiction = new AdapterDiction(context);
                    adapterDiction.setMeaningChild(examples.Example.get(position).exam);
                    LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapterDiction);
                    myhoder.linearLayout.addView(recyclerView);
                } else {
                    myhoder.linearLayout.removeAllViews();
                }
            }
        });

    }

    public void setLayoutMeaningExample(RecyclerView.ViewHolder holder, int position){
        Myhoder3 myhoder=(Myhoder3)holder;
        String s = meaningChild.get(position);
        int x =0;
        for(int i =0;i<s.length();i++){
            if(s.charAt(i)=='+'){
                x=i;
                break;
            }
        }
        if(x!=0) {
            String s1 = s.substring(0, x) + ": " + s.substring(x + 1, s.length());
            SpannableString spannableString = new SpannableString(s1);
            spannableString.setSpan(new ForegroundColorSpan(Color.CYAN), 0, x + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), x + 3, s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            myhoder.textView.setText(spannableString);
        }else myhoder.textView.setText(s);


    }

    class MyHoder extends RecyclerView.ViewHolder{
        public View view;

        RecyclerView recyclerView;
        public MyHoder(View itemView) {
            super(itemView);
            this.view = itemView;
            recyclerView = (RecyclerView)itemView.findViewById(R.id.recycle_child);

        }
    }

    class Myhoder1 extends RecyclerView.ViewHolder{
        public View view;
        public ImageView img_type;
        public TextView text_type;
        public RecyclerView recycle_mean;
        public RecyclerView recycler_extend;
        public Myhoder1(View itemView) {
            super(itemView);
            this.view = itemView;
            img_type = (ImageView)itemView.findViewById(R.id.img_type);
            text_type = (TextView)itemView.findViewById(R.id.text_type);
            text_type.setTypeface(MainActivity.typeface);
            recycle_mean = (RecyclerView) itemView.findViewById(R.id.recycle_means);
            recycler_extend = (RecyclerView)itemView.findViewById(R.id.recycle_extends);

        }
    }

    class Myhoder2 extends RecyclerView.ViewHolder {
        public View view;
        public int flag ;
        public LinearLayout linearLayout;
        public ImageView img_mean;
        public TextView text_mean;
        public int idimgold;
        public Myhoder2(View itemView) {
            super(itemView);
            this.view = itemView;
            linearLayout = (LinearLayout)itemView.findViewById(R.id.line_has_recycle);
            text_mean = (TextView)itemView.findViewById(R.id.text_mean);
            text_mean.setTypeface(MainActivity.typeface);
            img_mean = (ImageView)itemView.findViewById(R.id.img_mean);
            flag=0;
        }

        public void changeImage(){
            if(arrMeaningSave.contains(text_mean.getText().toString())){
                img_mean.setBackgroundResource(R.mipmap.icon_heart);
                text_mean.setTextColor(Color.RED);
            }else {
                img_mean.setBackgroundResource(R.mipmap.icon_heart_while);
                text_mean.setTextColor(Color.WHITE);
            }

            Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_heart_in);
            img_mean.startAnimation(animation);

            flag=1;
        }
        public void changeImageDefault(){
            for(int i =0;i<arrHoder.size();i++){
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_heart_out);
                arrHoder.get(i).img_mean.startAnimation(animation);
                arrHoder.get(i).img_mean.setBackgroundResource(arrHoder.get(i).idimgold);
                arrHoder.get(i).flag=0;
            }
        }
    }


    class Myhoder3 extends RecyclerView.ViewHolder{
        public TextView textView;
        public Myhoder3(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.content_example);
            textView.setTypeface(MainActivity.typeface);
        }
    }


}
