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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import model.Dictionary;

/**
 * Created by dainguyen on 5/30/17.
 */

public class AdapterWordSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Dictionary[]data;
    private int length=40;
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
    public AdapterWordSearch(Context context , Dictionary[]data){
        this.context = context;
        this.data = data;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_search,parent,false);
        return new MyHoder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyHoder hoder = (MyHoder)holder;

        hoder.word.setText(data[position].getName());
        String s = data[position].getContent();
        if(s.length()>length){
            s = data[position].getContent().substring(0,length)+"...";
        }
        hoder.meaning.setText(s);
        if (position!=(data.length-1)) {
            hoder.img.setImageDrawable(drawable);
        }

        hoder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("word",data[position]);
                ((AppCompatActivity)context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class MyHoder extends RecyclerView.ViewHolder{
        public View view;
        public TextView word;
        public TextView meaning;
        public ImageView img;
        public MyHoder(View itemView) {
            super(itemView);
            this.view = itemView;
            word = (TextView)itemView.findViewById(R.id.text_name);
            meaning = (TextView)itemView.findViewById(R.id.text_content);
            img = (ImageView)itemView.findViewById(R.id.img_line_bot);
        }
    }
}
