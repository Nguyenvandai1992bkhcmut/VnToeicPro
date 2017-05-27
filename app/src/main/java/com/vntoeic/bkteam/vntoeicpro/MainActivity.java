package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import model.Dictionary;
import model.DictionaryFavorite;
import model.*;


import sqlite.SqliteDictionary;
import sqlite.SqliteVocabulary;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDatabase();
        SqliteDictionary st = new SqliteDictionary();
        Dictionary[] re = st.searchSimilar("go");
        Dictionary re1 = st.searchId(10);
        DictionaryFavorite df = new DictionaryFavorite(100,"dai dep trai","best");
        st.insertFavorite(df);
        DictionaryFavorite[]re2 = st.searchFavorite();

        SqliteVocabulary vc = new SqliteVocabulary();
        ModelSection[]arr = vc.searchAllSection();
        ModelTag[]arr1 = vc.searchTaginSection(0);
        arr1= vc.searchTaginSection(1);

        ModelLesson []arr11 = vc.searchLessonTag(1);
        arr11= vc.searchLessonTag(3);
        arr11= vc.searchLessonTag(2);
        arr11= vc.searchLessonTag(4);

        ModelWord modelWord = vc.searchWordId(10);
        ModelWordLesson md[] = vc.searchWordLesson(1);

        ModelFavoriteWord mm[] = vc.searchFavoriteWord();
        ModelFavoriteWord m = new ModelFavoriteWord(1,"nguyenvandai_time");
        ModelFavoriteWord m1 = new ModelFavoriteWord(1,"nguyenvandai_time");
        ModelFavoriteWord m2 = new ModelFavoriteWord(1,"nguyenvandai_time");
        vc.insertFavoriteWord(m);
        vc.insertFavoriteWord(m1);
        vc.insertFavoriteWord(m2);
        mm = vc.searchFavoriteWord();

        ModelWordChecked checked[]=vc.searchWordCheckedId(1);
        ModelWordChecked modelWordChecked = new ModelWordChecked(29,0,1,"time_check_insert");
        vc.insertWordChecked(modelWordChecked);
        checked = vc.searchWordChecked();

        Boolean b = vc.checkFavoriteWord(1);

        b= vc.checkWordChecked(29);
        vc.deleteWordChecked(29);
        b= vc.checkWordChecked(29);
        vc.deleteWordFavorite(1);
        b= vc.checkFavoriteWord(1);
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
