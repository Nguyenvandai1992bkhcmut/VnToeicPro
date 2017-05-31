package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity.VocabularyActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VocabularyActivity.class);
                startActivity(intent);
            }
        });

        checkDatabase();


    }


    public void checkDatabase() {
        File data = getDatabasePath("Database.db");
        // if(data.exists() == false)
        if (true) {
            copyDatabase(getApplicationContext());
        }

    }
    public void copyDatabase(Context context){
        try{
            InputStream inputStream = context.getAssets().open("Database.db");
            String outputString = "data/data/com.vntoeic.bkteam.vntoeicpro/databases/database.db";
            OutputStream outputStream = new FileOutputStream(outputString);

            byte[]arr = new byte[10240];
            int length =0;
            while ((length =inputStream.read(arr))>0){
                outputStream.write(arr,0,length);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}
