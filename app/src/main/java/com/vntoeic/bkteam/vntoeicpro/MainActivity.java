package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import model.Dictionary;
import model.DictionaryFavorite;
import model.ModelFavoriteWord;
import model.ModelGrammar;
import model.ModelLesson;
import model.ModelPart1;
import model.ModelPart2;
import model.ModelPart3;
import model.ModelPart4;
import model.ModelPart5;
import model.ModelPart6;
import model.ModelPartCheck;
import model.ModelPartFavorite;
import model.ModelPartSubject;
import model.ModelSection;
import model.ModelTag;
import model.ModelWord;
import model.ModelWordChecked;
import model.ModelWordLesson;
import sqlite.SqliteDictionary;
import sqlite.SqlitePart1;
import sqlite.SqlitePart2;
import sqlite.SqlitePart3;
import sqlite.SqlitePart4;
import sqlite.SqlitePart5;
import sqlite.SqlitePart6;
import sqlite.SqliteVocabulary;
import dictionary.*;

public class MainActivity extends AppCompatActivity implements AdapterWordSearch.onClickItem{
    private PageAdapterMain pageAdapterMain;
    public static Typeface typeface ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDatabase();
        SqliteDictionary sqliteDictionary = new SqliteDictionary();
        Boolean b = sqliteDictionary.checkFavorite(100);
        SqliteDictionary sqlite = new SqliteDictionary();
        DictionaryFavorite favorite = sqlite.searchFavoriteDictionary(100);

        SqlitePart6  sqlitePart6 = new SqlitePart6();
        ModelPart6 kk[]=  sqlitePart6.randomPart6(10);
        sqlitePart6.insertPartFavorite(new ModelPartFavorite(6,100,"time"));
        sqlitePart6.insertPartFavorite(new ModelPartFavorite(6,99,"time"));
        sqlitePart6.insertPartFavorite(new ModelPartFavorite(6,98,"tiime"));
        kk = sqlitePart6.searchPart6Favorite();

        SqlitePart4 sqlitePart4 = new SqlitePart4();
        ModelPart4 ll[] = sqlitePart4.randomPart4(10);
        sqlitePart4.insertPartFavorite(new ModelPartFavorite(4,99,"time"));
        sqlitePart4.insertPartFavorite(new ModelPartFavorite(4,98,"time"));
        sqlitePart4.insertPartFavorite(new ModelPartFavorite(4,97,"time"));



        ll = sqlitePart4.searchPart4Favorite();
        ModelPart4 l = sqlitePart4.searchPart4Id(99);

        SqlitePart3  sqlitePart3 = new SqlitePart3();
        ModelPart3 ddd[]= sqlitePart3.randomPart3(10);
       // SqlitePart2 ss = new SqlitePart2();
      //  ModelPart2 d[] = ss.randomPart2(10);
       // ModelPart2 a = ss.searchPart2Id(1);
//        d  = ss.searchPart2Favorite();
//        d=ss.randomPart2Subject(1,10);


        ModelPart3 mm = sqlitePart3.searchPart3Id(10);
        sqlitePart3.insertPartFavorite(new ModelPartFavorite(3,99,"time"));
        ModelPartCheck mmmm[]=sqlitePart3.searchAllCheckedPart(3);
        ddd = sqlitePart3.searchPart3Favorite();

//        Dictionary re1 = st.searchId(10);
//        DictionaryFavorite df = new DictionaryFavorite(100,"dai dep trai","best");
//        st.insertFavorite(df);
//        DictionaryFavorite[]re2 = st.searchFavorite();
//
//        SqliteVocabulary vc = new SqliteVocabulary();
//        ModelSection[]arr = vc.searchAllSection();
//        ModelTag[]arr1 = vc.searchTaginSection(0);
//        arr1= vc.searchTaginSection(1);
//
//        ModelLesson []arr11 = vc.searchLessonTag(1);
//        arr11= vc.searchLessonTag(3);
//        arr11= vc.searchLessonTag(2);
//        arr11= vc.searchLessonTag(4);
//
//        ModelWord modelWord = vc.searchWordId(10);
//        ModelWordLesson md[] = vc.searchWordLesson(1);
//
//        ModelFavoriteWord mm[] = vc.searchFavoriteWord();
//        ModelFavoriteWord m = new ModelFavoriteWord(1,"nguyenvandai_time");
//        ModelFavoriteWord m1 = new ModelFavoriteWord(1,"nguyenvandai_time");
//        ModelFavoriteWord m2 = new ModelFavoriteWord(1,"nguyenvandai_time");
//        vc.insertFavoriteWord(m);
//        vc.insertFavoriteWord(m1);
//        vc.insertFavoriteWord(m2);
//        mm = vc.searchFavoriteWord();
//
//        ModelWordChecked checked[]=vc.searchWordCheckedId(1);
//        ModelWordChecked modelWordChecked = new ModelWordChecked(29,0,1,"time_check_insert");
//        vc.insertWordChecked(modelWordChecked);
//        checked = vc.searchWordChecked();
//
//        Boolean b = vc.checkFavoriteWord(1);
//
//        b= vc.checkWordChecked(29);
//        vc.deleteWordChecked(29);
//        b= vc.checkWordChecked(29);
//        vc.deleteWordFavorite(1);
//        b= vc.checkFavoriteWord(1);
//
//
//       SqlitePart1 sqlite = new SqlitePart1();
//        ModelPart1 p1 = sqlite.searchPart1Id(10);
//        ModelPart1 p2[] = sqlite.randomPart1(10);
//        ModelPart1 p3[] = sqlite.randomPart1Subject(1,20);
//        ModelPart1 p4[] = sqlite.searchPart1Favorite();
//         b = sqlite.checkPartFavorite(1,1);
//        b = sqlite.checkPartFavorite(1,10);
//        ModelPartFavorite md1 = new ModelPartFavorite(1,100,"test insert");
//        sqlite.insertPartFavorite(md1);
//        ModelPartFavorite f1[] =  sqlite.searchAllFavoritePart(1);
//        sqlite.deletePartFavorite(1,100);
//        f1 =  sqlite.searchAllFavoritePart(1);
//
//        ModelPartCheck mc = new ModelPartCheck(1,1000,"test insert",0);
//        sqlite.insertPartCheck(mc);
//        ModelPartCheck c1[] = sqlite.searchAllCheckedPart(1);
//        sqlite.deletePartCheck(1,1000);
//        c1 = sqlite.searchAllCheckedPart(1);
//
//        ModelWord[]ww = sqlite.searchWordPart(5,1);
//        ww = sqlite.searchWordPartAware(5,1,1);
//        sqlite.updateWordAware(671,1);
//        ww = sqlite.searchWordPartAware(5,1,1);
//        ww = sqlite.searchWordPartAware(5,0,1);
//
//        ModelPartSubject[]pp = sqlite.searchPartSubject(1);
//        pp =sqlite.searchPartSubject(2);
//        pp =sqlite.searchPartSubject(3);
//        pp =sqlite.searchPartSubject(4);
//        pp =sqlite.searchPartSubject(5);
////
//        ModelGrammar grammar[] = sqlite.searchAllGrammar();
//        ModelGrammar grammar1 = sqlite.searchGrammarId(1);
//        SqlitePart5 sqlite22 = new SqlitePart5();
//        ModelPart5 []md5 = sqlite22.randomPart5(10);
//        md5 = sqlite22.randomPart5Subject(1,20);
//        ModelPartFavorite ma = new ModelPartFavorite(5,1,"anlongnhe");
//        sqlite22.insertPartFavorite(ma);
//        md5 = sqlite22.searchPart5Favorite();


        setUplayout();
    }

    public void setUplayout(){
        typeface =Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/VietAPk.vn-MyriadPro_SBI.ttf");
        pageAdapterMain = new PageAdapterMain(getSupportFragmentManager());
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager_main);
        viewPager.setAdapter(pageAdapterMain);
        viewPager.setCurrentItem(1);
        viewPager.setPageTransformer(true,new AccordionTransformer());
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
            String outputString = context.getApplicationInfo().dataDir +"/database.db";


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



    @Override
    public void funonClickItem(Dictionary dictionary) {
        final Intent intent = new Intent(getApplicationContext(), DictionaryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("word",dictionary);
        intent.putExtras(bundle);
        this.startActivityForResult(intent,1);
        MainActivity.this.overridePendingTransition(R.anim.left_to_right_in , R.anim.left_to_right);
    }

    @Override
    public void funClickRemove(Dictionary dictionary, int postion) {

    }
}
