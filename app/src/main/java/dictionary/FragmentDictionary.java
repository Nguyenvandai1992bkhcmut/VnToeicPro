package dictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.Content;
import model.Dictionary;
import model.DictionaryFavorite;
import model.Example;
import model.Meanings;
import sqlite.SqliteDictionary;

/**
 * Created by dainguyen on 5/30/17.
 */

public class FragmentDictionary extends Fragment {
    private Dictionary dictionary;
    private ImageView img_vol;
    private TextView text_name;
    private TextView text_pro;
    private ImageView img_star;
    private RecyclerView recyclerView;
    private int flag =0;
    PopupWindow popupWindow;
    public ArrayList<String>arrMeaning;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle!= null){
            dictionary = (Dictionary) bundle.getSerializable("word");
        }
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_dictionary,container,false);

        getMeaning();
        setUpLayout(view);
        return view;
    }

    public void getMeaning(){
        arrMeaning = new ArrayList<>();
        SqliteDictionary sqlite = new SqliteDictionary();
        DictionaryFavorite favorite = sqlite.searchFavoriteDictionary(dictionary.getId());
        if(favorite!=null) {
            String mean = favorite.getMeaning();
            String s[] = mean.split("!~!~!");
            for (int i = 0; i < s.length; i++) {
                arrMeaning.add(s[i]);
            }
        }
    }

    public void setUpLayout(final View view){
        text_name = (TextView)view.findViewById(R.id.text_name);
        text_name.setTypeface(MainActivity.typeface);
        text_pro  =(TextView)view.findViewById(R.id.text_pro);
        text_pro.setTypeface(MainActivity.typeface);
        img_star = (ImageView) view.findViewById(R.id.img_star);

        SqliteDictionary sqlite = new SqliteDictionary();
        if(sqlite.checkFavorite(dictionary.getId())){
            img_star.setBackgroundResource(R.mipmap.icon_heart);
        }else img_star.setBackgroundResource(R.mipmap.icon_heart_while);

        img_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0) {
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_favorite_out);
                    final Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.scale_favorite_in);

                    img_star.startAnimation(animation);
                    img_star.setVisibility(View.GONE);
                    ChangeImageHeart();
                    if (!popupWindow.isShowing()) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            // Do something for lollipop and above versions

                            Window window = getActivity().getWindow();


                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.background));
                        }
                        popupWindow.setAnimationStyle(R.style.PauseDialogFavorit);
                        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                        ((ImageView) popupWindow.getContentView().findViewById(R.id.img_tick)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChangeImageDefault();
                                if (popupWindow.isShowing()) popupWindow.dismiss();
                                SaveMeaning();
                                if(AdapterDiction.arrMeaningSave.size()!=0)img_star.setBackgroundResource(R.mipmap.icon_heart);
                                else img_star.setBackgroundResource(R.mipmap.icon_heart_while);

                                img_star.setVisibility(View.VISIBLE);
                                img_star.startAnimation(animation1);

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    // Do something for lollipop and above versions

                                    Window window = getActivity().getWindow();


                                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.header));
                                }
                                flag=0;
                            }
                        });
                    }
                    flag=1;
                }
            }
        });

        View view_tick=LayoutInflater.from(getContext()).inflate(R.layout.popup_favorite_diction,null);
        int h = (int) (getResources().getDisplayMetrics().density*110);
        popupWindow = new PopupWindow(view_tick, ViewGroup.LayoutParams.MATCH_PARENT,h);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);

        ArrayList<Content> arrContent = ParseDatatoContent(dictionary);
        text_name.setText(dictionary.getName());
        text_pro.setText(arrContent.get(0).getVol());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_dicmain);
        AdapterDiction adapterDiction = new AdapterDiction(getContext());
        adapterDiction.setContent(ParseDatatoContent(dictionary));
        /*
        add meaning save
         */
        adapterDiction.setArrMeaningSave(arrMeaning);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterDiction);


    }

    public ArrayList<Content> ParseDatatoContent(Dictionary data){
        ArrayList<Content> contents = new ArrayList<>();
        if(data ==null)return null;
        String s =data.getContent();
        String[] arr = s.split("@");
        for(int i =0;i<arr.length;i++){
            if(arr[i].length()==0)continue;
            else{
                String s1 = arr[i];

                String []arr1 = s1.split(" \\*  ");
                if(arr1.length >1){
                    String s2 = arr1[0];
                    String []arr2 = s2.split("/");
                    String parent = "@"+arr2[0];
                    String vol ="";
                    if(arr2.length>1&& (!arr2[1].equals(" ")) )vol += "/" + arr2[1] +"/";

                    ArrayList<Meanings> meanings = new ArrayList<>();

                    ArrayList<Example> arrexample = new ArrayList<>();

                    ArrayList<String>example = new ArrayList<>();

                    String type ="";
                    String mean ="";

                    ArrayList<Example>arrExtend = new ArrayList<>();

                    for(int j =1;j<arr1.length;j++){
                        String s3 = arr1[j];
                        String[]extend = s3.split(" !");
                        String s4 = extend[0];

                        if(extend.length>1){
                            ArrayList<String>arrStringextend= new ArrayList<>();
                            for(int ii=1;ii<extend.length;ii++){
                                String[] ex = extend[ii].split(" - ");
                                String nameex = ex[0];
                                if(ex.length>1)for(int i2 =1;i2<ex.length;i2++){
                                    arrStringextend.add(ex[i2]);
                                }
                                if(nameex.length()>1)arrExtend.add(new Example(0,type,nameex,arrStringextend));
                                arrStringextend = new ArrayList<>();
                            }
                        }

                        String []arrmeaning = s4.split(" - ");
                        type =arrmeaning[0];

                        //lay meaning
                        if(arrmeaning.length >1){
                            for(int h =1; h<arrmeaning.length;h++){
                                String [] arrexam = arrmeaning[h].split(" =");
                                mean =arrexam[0];
                                if(arrexam.length>1) for(int k =1;k<arrexam.length;k++)example.add(arrexam[k]);
                                if(mean.length() >1) arrexample.add(new Example(1,type,mean,example));
                                example = new ArrayList<>();
                            }
                        }

                        meanings.add(new Meanings(type,arrexample,arrExtend));
                        arrExtend = new ArrayList<>();
                        arrexample = new ArrayList<>();
                    }

                    Content  content = new Content(parent,vol,meanings);
                    contents.add(content);
                }
                else{
                    String[]arr2 = s1.split("-");
                    String s2= arr2[0];
                    String []arr3 = s2.split("/");
                    String parent = "@"+arr3[0];
                    String vol ="";
                    for(int j =1;j<arr3.length;j++)if(arr3[j].length()>0)vol += "/" + arr3[j] +"/";

                    ArrayList<Meanings> meanings = new ArrayList<>();

                    ArrayList<Example> arrexample = new ArrayList<>();

                    ArrayList<String>example = new ArrayList<>();
                    ArrayList<Example>arrExtend = new ArrayList<>();
                    String mean ="";

                    if(arr2.length>1){
                        ArrayList<String>arrextend= new ArrayList<>();
                        for(int j =1 ;j<arr2.length;j++){
                            String s3 = arr2[j];

                            String []ex = s3.split(" !");
                            String s4 =ex[0];
                            if(ex.length>1){
                                for(int i2 =1;i2<ex.length;i2++){
                                    String[]arrex= ex[i2].split(" - ");
                                    String nameex = arrex[0];
                                    if(arrex.length>1)for(int i3 =1; i3<arrex.length;i3++){
                                        arrextend.add(arrex[i3]);
                                    }
                                    if(nameex.length()>1)arrExtend.add(new Example(0,"none",nameex,arrextend));
                                    arrextend = new ArrayList<>();
                                }
                            }

                            String[]arrmean = s4.split(" - ");
                            mean = arrmean[0];
                            if(arrmean.length>1) for(int h =1; h<arrmean.length ;h++)example.add(arrmean[h]);

                            if(mean.length()>1)arrexample.add(new Example(1,"none",mean,example));
                            example = new ArrayList<>();

                        }
                    }
                    meanings.add(new Meanings("",arrexample,arrExtend));
                    Content  content = new Content(parent,vol,meanings);
                    contents.add(content);
                }

            }
        }
        return contents;
    }

    public void ChangeImageHeart(){
        for(int i =0;i<AdapterDiction.arrHoder.size();i++){
            AdapterDiction.arrHoder.get(i).changeImage();
        }
    }

    public void ChangeImageDefault(){
        for(int i =0;i<AdapterDiction.arrHoder.size();i++){
            AdapterDiction.arrHoder.get(i).changeImageDefault();
        }
    }

    public void SaveMeaning(){
        String s="";
        for(int i=0;i<AdapterDiction.arrMeaningSave.size();i++){
            if(i==0)s+= AdapterDiction.arrMeaningSave.get(i);
            else s+="!~!~!" + AdapterDiction.arrMeaningSave.get(i);
        }

        SqliteDictionary sqite = new SqliteDictionary();
        DictionaryFavorite favorite = new DictionaryFavorite(dictionary.getId(),"time",s);
        sqite.insertFavorite(favorite);
    }

}
