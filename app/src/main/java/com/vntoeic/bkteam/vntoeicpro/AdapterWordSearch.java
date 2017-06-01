package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Dictionary.*;
import model.Dictionary;

/**
 * Created by dainguyen on 5/30/17.
 */

public class AdapterWordSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AppCompatActivity context;
    public onClickItem onClickItem;
    private List<Dictionary>data;

    private int length=40;
    private int colorname = Color.BLUE;
    private int colormean = Color.BLACK;
    private int flag_history =0;

    public static  Drawable drawable  = new Drawable() {
        @Override
        public void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(1);

            canvas.drawLine(0,1,2000,1,paint);

        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    };
    public AdapterWordSearch(AppCompatActivity context , Dictionary[]data){
        this.context = context;
        this.data = new ArrayList<Dictionary>(Arrays.asList(data));
        onClickItem = (AdapterWordSearch.onClickItem) context;

    }
    public void setTextColor(int color,int color1){
        this.colorname  = color;
        this.colormean = color1;
    }

    public void setHistory(){
        flag_history=1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_search,parent,false);
        return new MyHoder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyHoder hoder = (MyHoder)holder;

        hoder.word.setText(data.get(position).getName());
        String s = data.get(position).getContent();
        if(s.length()>length){
            s = data.get(position).getContent().substring(0,length)+"...";
        }
        hoder.meaning.setText(s);
        if (position!=(data.size()-1)) {
            hoder.img.setImageDrawable(drawable);
        }

        hoder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.funonClickItem(data.get(position));
            }
        });

        if(flag_history==1){
            hoder.linearLayout.removeAllViews();
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(R.mipmap.icon_remove);
            hoder.linearLayout.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem.funClickRemove(data.get(position),position);
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,data.size());

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        MyHoder myHoder =(MyHoder)holder;
        ScaleAnimation animation = new ScaleAnimation(1f,1f,0f,1f,0.5f,0f);
        animation.setDuration(400);
        animation.setStartOffset(holder.getAdapterPosition()*30);
        animation.setFillAfter(true);
        ((MyHoder) holder).view.startAnimation(animation);

    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        MyHoder myHoder =(MyHoder)holder;
        ScaleAnimation animation = new ScaleAnimation(1f,1f,1f,0f,0.5f,0f);
        animation.setDuration(400);
        animation.setStartOffset(holder.getAdapterPosition()*30);
        animation.setFillAfter(true);
        ((MyHoder) holder).view.startAnimation(animation);
    }


    public class MyHoder extends RecyclerView.ViewHolder{
        public View view;
        public TextView word;
        public TextView meaning;
        public ImageView img;
        public LinearLayout linearLayout;
        public MyHoder(View itemView) {
            super(itemView);
            this.view = itemView;
            word = (TextView)itemView.findViewById(R.id.text_name);
            word.setTypeface(MainActivity.typeface);
            meaning = (TextView)itemView.findViewById(R.id.text_content);
            meaning.setTypeface(MainActivity.typeface);
            img = (ImageView)itemView.findViewById(R.id.img_line_bot);
            meaning.setTextColor(colormean);
            word.setTextColor(colorname);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.line);
        }
    }


    public interface onClickItem{
        public void funonClickItem(Dictionary dictionary);

        public void funClickRemove(Dictionary dictionary, int position);
    }
}
