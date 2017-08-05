package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import model.ModelPart7;
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
import sqlite.SqlitePart7;
import sqlite.SqliteVocabulary;
import dictionary.*;

public class MainActivity extends AppCompatActivity implements AdapterWordSearch.onClickItem{
    private PageAdapterMain pageAdapterMain;
    public static Typeface typeface ;
    private int flag_test_read = 0;
    private Bundle bundle= null;
    ServerSocket serverSocket;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDatabase();
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                String ip = getIPAddress(true);
//                try {
//                    serverSocket = new ServerSocket(9999);
//                    Socket socket  = new Socket("",88);
//
//                    while (true){
//                        sleep(5000);
//                        int size= socket.getReceiveBufferSize();
//                        if(size !=0){
//
//                        }
//
//                    }
//                } catch (IOException e) {
//
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        thread.start();
//

//        SqlitePart7 sqlitePart7 = new SqlitePart7();
//        ModelPart7 data[] = sqlitePart7.randomPart7(10);
//        data = sqlitePart7.randomPart7CountQuestion(10,2);
//        data = sqlitePart7.randomPart7Subject(1,10);
//        sqlitePart7.insertPartCheck(new ModelPartCheck(7,10,"time",1));
//        sqlitePart7.insertPartFavorite(new ModelPartFavorite(7,10,"time"));
//        data = sqlitePart7.searchPart7Check();
//        data = sqlitePart7.searchPart7Favorite();

//        SqliteDictionary sqliteDictionary = new SqliteDictionary();
//        Boolean b = sqliteDictionary.checkFavorite(100);
//        SqliteDictionary sqlite = new SqliteDictionary();
//        DictionaryFavorite favorite = sqlite.searchFavoriteDictionary(100);
//
//        SqlitePart6  sqlitePart6 = new SqlitePart6();
//        sqlitePart6.insertPartCheck(new ModelPartCheck(6,1,"time",0));
//        sqlitePart6.insertPartCheck(new ModelPartCheck(6,1,"time",1));
//        sqlitePart6.insertPartCheck(new ModelPartCheck(6,1,"time",1));
//        ModelPart6 da[] = sqlitePart6.searchPart6Check();
        /// ModelPart6 kk[]=  sqlitePart6.randomPart6(10);


//        sqlitePart6.insertPartFavorite(new ModelPartFavorite(6,100,"time"));
//        sqlitePart6.insertPartFavorite(new ModelPartFavorite(6,99,"time"));
//        sqlitePart6.insertPartFavorite(new ModelPartFavorite(6,98,"tiime"));
//        kk = sqlitePart6.searchPart6Favorite();
//
//        SqlitePart4 sqlitePart4 = new SqlitePart4();
//        ModelPart4 ll[] = sqlitePart4.randomPart4(10);
//        sqlitePart4.insertPartFavorite(new ModelPartFavorite(4,99,"time"));
//        sqlitePart4.insertPartFavorite(new ModelPartFavorite(4,98,"time"));
//        sqlitePart4.insertPartFavorite(new ModelPartFavorite(4,97,"time"));
//
//
//
//        ll = sqlitePart4.searchPart4Favorite();
//        ModelPart4 l = sqlitePart4.searchPart4Id(99);
//
//        SqlitePart3  sqlitePart3 = new SqlitePart3();
//        ModelPart3 ddd[]= sqlitePart3.randomPart3(10);
//       // SqlitePart2 ss = new SqlitePart2();
//      //  ModelPart2 d[] = ss.randomPart2(10);
//       // ModelPart2 a = ss.searchPart2Id(1);
////        d  = ss.searchPart2Favorite();
////        d=ss.randomPart2Subject(1,10);
//
//
//        ModelPart3 mm = sqlitePart3.searchPart3Id(10);
//        sqlitePart3.insertPartFavorite(new ModelPartFavorite(3,99,"time"));
//        ModelPartCheck mmmm[]=sqlitePart3.searchAllCheckedPart(3);
//        ddd = sqlitePart3.searchPart3Favorite();

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
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    public void setUplayout(){
        typeface =Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/VietAPk.vn-MyriadPro_SBI.ttf");
        pageAdapterMain = new PageAdapterMain(getSupportFragmentManager());
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager_main);
        viewPager.setAdapter(pageAdapterMain);
        viewPager.setCurrentItem(1);
        viewPager.setPageTransformer(true,new CubeOutTransformer());
    }


    public void checkDatabase() {
        File data = getDatabasePath("Database.db");

        File datapart7 = new File(getApplicationContext().getApplicationInfo().dataDir+"/part7/");
        Boolean b  =datapart7.mkdirs();
        File datapart7image = new File(getApplicationContext().getApplicationInfo().dataDir+"/part7/image");
        b= datapart7image.mkdirs();

        File datapart3 = new File(getApplicationContext().getApplicationInfo().dataDir+"/part3/");
        b  =datapart3.mkdirs();
        File datapart3audio = new File(getApplicationContext().getApplicationInfo().dataDir+"/part3/audio");
        b= datapart3audio.mkdirs();

        File datapart4 = new File(getApplicationContext().getApplicationInfo().dataDir+"/part4/");
        b  =datapart4.mkdirs();
        File datapart4audio = new File(getApplicationContext().getApplicationInfo().dataDir+"/part4/audio");
        b= datapart4audio.mkdirs();


        File datapart2 = new File(getApplicationContext().getApplicationInfo().dataDir+"/part2/");
        b  =datapart2.mkdirs();
        File datapart2audio = new File(getApplicationContext().getApplicationInfo().dataDir+"/part2/audio");
        b= datapart2audio.mkdirs();

        File datapart1 = new File(getApplicationContext().getApplicationInfo().dataDir+"/part1/");
        b  =datapart1.mkdirs();
        File datapart1audio = new File(getApplicationContext().getApplicationInfo().dataDir+"/part1/audio");
        b= datapart1audio.mkdirs();
        File datapart1image = new File(getApplicationContext().getApplicationInfo().dataDir+"/part1/image");
        b= datapart1image.mkdirs();


// have the object build the directory structure, if needed.
        // if(data.exists() == false)
        if (false) {
            copyDatabase(getApplicationContext());
        }

    }
    public void copyDatabase(Context context){
        try{
            InputStream inputStream = context.getAssets().open("Database.db");
            String outputString = context.getApplicationInfo().dataDir +"/database.db";

            OutputStream outputStream = new FileOutputStream(outputString);
            byte[]arr = new byte[1024];
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