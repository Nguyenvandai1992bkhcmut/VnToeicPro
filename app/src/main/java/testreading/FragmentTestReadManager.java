package testreading;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.Serializable;
import java.util.ArrayList;

import model.IDataQuestion;
import model.IListenPart;
import model.ModelPart5;
import model.ModelPart6;
import model.ModelPart7;
import part5.FragmentPart5Question;

import part7.Fragment67Content;
import part7.FragmentPagerQuestion;
import sqlite.SqlitePart5;
import sqlite.SqlitePart6;
import sqlite.SqlitePart7;
import supportview.FragmentPartExplan;
import part7.FragmentPartQuestion;
import supportview.IContentQuestion;
import supportview.IManagerPart;
import supportview.IPartControl;

/**
 * Created by dainguyen on 7/10/17.
 */

public class FragmentTestReadManager extends Fragment implements IManagerPart, IContentQuestion, IPartControl {
    private RelativeLayout relative_parent;
    public FrameLayout frame_question;
    public FrameLayout frame_explan;
    public FrameLayout frame_blur;
    public ImageView img_float;
    public ImageView img_figure;
    public ImageView img_explan;
    public ImageView img_commment;
    public ImageView img_question;

    //data

    private ModelPart5[]datapart5;
    private ModelPart6[]datapart6;
    private ModelPart7[]datapart7;


    private ArrayList<Integer>arrQuestionPart5=new ArrayList<>();
    private ArrayList<Integer>arrQuestionPart6=new ArrayList<>();
    private ArrayList<Integer>arrQuestionPart7=new ArrayList<>();

    private ArrayList<String>arrChoosePart5 =new ArrayList<>();
    private ArrayList<ArrayList<String>>arrChoosePart6=new ArrayList<>();
    private ArrayList<ArrayList<String>>arrChoosePart7=new ArrayList<>();

    private ArrayList<String>arrResultPart5=new ArrayList<>();
    private ArrayList<ArrayList<String>>arrResultPart6=new ArrayList<>();
    private ArrayList<ArrayList<String>>arrResultPart7=new ArrayList<>();

    //variable Control
    private int part =5;
    private int mode=1;
    private int isFigure=0;
    private int isSubmit =0;
    private int isQuestion=1;
    private int isComment=0;
    private int isExplan=0;
    private int indexPart5=0;
    private int indexPart6=0;
    private int indexPart7=0;

    private int isaniquestion=1;
    private int isanimation=0;


    private int pos_question=0;



    private FragmentControlTestReading fragmentControl;


    /*
    Interface Imanager Part
     */
    @Override
    public Bundle getBunldeSave() {
        Bundle bundle= new Bundle();
        bundle.putInt("part",part);
        bundle.putInt("indexpart5",indexPart5);
        bundle.putInt("indexpart6",indexPart6);
        bundle.putInt("indexpart7",indexPart7);
        bundle.putIntegerArrayList("questionpart5",arrQuestionPart5);
        bundle.putIntegerArrayList("questionpart6",arrQuestionPart6);
        bundle.putIntegerArrayList("questionpart7",arrQuestionPart7);
        String s ="";

        for(int i=0;i<arrChoosePart7.size();i++){

            for(int j=0;j<arrChoosePart7.get(i).size();j++){
                if(j!=(arrChoosePart7.get(i).size()-1)){
                    s+=arrChoosePart7.get(i).get(j)+"!";

                }
                else{
                    s+=arrChoosePart7.get(i).get(j);

                }
            }
            if(i!=arrChoosePart7.size()-1)s+="~~~";

        }
        bundle.putString("choosepart7",s);
        s="";
        for(int i=0;i<arrChoosePart6.size();i++){

            for(int j=0;j<arrChoosePart6.get(i).size();j++){
                if(j!=(arrChoosePart6.get(i).size()-1)){
                    s+=arrChoosePart6.get(i).get(j)+"!";

                }
                else{
                    s+=arrChoosePart6.get(i).get(j);

                }
            }
            if(i!=arrChoosePart6.size()-1)s+="~~~";

        }
        bundle.putString("choosepart6",s);
        bundle.putStringArrayList("choosepart5",arrChoosePart5);
        return bundle;
    }

    @Override
    public int getIsSubmit() {
        return isSubmit;
    }

    @Override
    public void setBundle(Bundle bundle) {
        setArguments(bundle);
    }

    @Override
    public void timeSubmit() {
        isSubmit=1;
        defaultImage();
        showImage();
        setContent();
        setContentQuestion();
    }

    @Override
    public void sendResultToSever(String... arr) {

    }




    //
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    public void getData(){
        Bundle bundle =getArguments();
        if(bundle!=null) {
            mode= bundle.getInt("mode");
            int key= bundle.getInt("key");
            if(key==0) {
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                datapart5 = sqlitePart5.randomPart5(40);
                for (int i = 0; i < 40; i++) {
                    arrQuestionPart5.add(datapart5[i].getId());
                    arrChoosePart5.add("Not choose");
                    arrResultPart5.add(datapart5[i].getResult());
                }


                SqlitePart6 sqlitePart6 = new SqlitePart6();
                datapart6 = sqlitePart6.randomPart6(4);

                for (int i = 0; i < 4; i++) {
                    arrQuestionPart6.add(datapart6[i].getId());
                    ArrayList<String> re = new ArrayList<>();
                    ArrayList<String> ch = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        re.add(datapart6[i].getArrQuestion().get(j).getSol());
                        ch.add("Not choose");
                    }
                    arrResultPart6.add(re);
                    arrChoosePart6.add(ch);
                }

                SqlitePart7 sqlitePart7 = new SqlitePart7();
                datapart7 = new ModelPart7[13];
                ModelPart7 data1[] = sqlitePart7.randomPart7CountQuestion(3, 2);
                addData(data1);
                data1 = sqlitePart7.randomPart7CountQuestion(3, 3);
                addData(data1);
                data1 = sqlitePart7.randomPart7CountQuestion(2, 4);
                addData(data1);
                data1 = sqlitePart7.randomPart7CountQuestion(5, 5);
                addData(data1);
                indexPart7 = 0;
            }else{
                arrQuestionPart5 = bundle.getIntegerArrayList("questionpart5");
                arrQuestionPart6 = bundle.getIntegerArrayList("questionpart6");
                arrQuestionPart7 = bundle.getIntegerArrayList("questionpart7");
                indexPart5 = bundle.getInt("indexpart5");
                indexPart6 = bundle.getInt("indexpart6");
                indexPart7 = bundle.getInt("indexpart7");
                part= bundle.getInt("parttestread");


                String choosen = bundle.getString("choosepart6");
                String arr[] = choosen.split("~~~");
                for(int i =0;i<arr.length;i++){
                    String arr1[] = arr[i].split("!");
                    ArrayList<String>ch = new ArrayList<>();
                    for(int j=0;j<arr1.length;j++){
                        ch.add(arr1[j]);
                    }
                    arrChoosePart6.add(ch);
                }

                SqlitePart6 sqlitePart6 = new SqlitePart6();
                datapart6= new ModelPart6[arrQuestionPart6.size()];
                for(int i =0;i<arrQuestionPart6.size();i++){
                    ModelPart6 modelPart6 = sqlitePart6.searchPart6Id(arrQuestionPart6.get(i));
                    datapart6[i]= modelPart6;
                    ArrayList<String>re = new ArrayList<>();
                    for(int j=0;j<3;j++){
                        re.add(modelPart6.getArrQuestion().get(j).sol);
                    }
                    arrResultPart6.add(re);
                }


                SqlitePart7 sqlitePart7 = new SqlitePart7();
                datapart7 = new ModelPart7[arrQuestionPart7.size()];


                for (int i = 0; i < arrQuestionPart7.size(); i++) {
                    ModelPart7 modelPart7 = sqlitePart7.searchPart7Id(arrQuestionPart7.get(i));
                    datapart7[i] = modelPart7;
                    ArrayList<String> re = new ArrayList<>();
                    for (int j = 0; j < modelPart7.getQuestions().size(); j++) {
                        re.add(modelPart7.getQuestions().get(j).getSol());
                    }
                    arrResultPart7.add(re);
                }
                 choosen = bundle.getString("choosepart7");
                 arr = choosen.split("~~~");
                    for(int i =0;i<arr.length;i++){
                    String arr1[] = arr[i].split("!");
                    ArrayList<String>ch = new ArrayList<>();
                    for(int j=0;j<arr1.length;j++){
                        ch.add(arr1[j]);
                    }
                    arrChoosePart7.add(ch);
                }
                arrChoosePart5 = bundle.getStringArrayList("choosepart5");


                SqlitePart5 sqlite = new SqlitePart5();
                datapart5=new ModelPart5[arrQuestionPart5.size()];;
                for(int i =0;i<arrQuestionPart5.size();i++){
                    ModelPart5 modelPart5[] = sqlite.searchPart5Id(arrQuestionPart5.get(i));
                    datapart5[i]=modelPart5[0];
                    arrResultPart5.add(modelPart5[0].getResult());
                }


            }
        }
    }

    public void addData(ModelPart7 data1[]){

        for(int i =0;i<data1.length;i++) {
            datapart7[indexPart7]=  data1[i];
            indexPart7++;
            arrQuestionPart7.add(data1[i].getId());
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> arr1 = new ArrayList<>();
            for (int j = 0; j < data1[0].getQuestions().size(); j++) {
                arr.add(data1[0].getQuestions().get(j).getSol());
                arr1.add("Not choose");
            }
            arrChoosePart7.add(arr1);
            arrResultPart7.add(arr);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part6_manager,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(View view){
        relative_parent =(RelativeLayout)view.findViewById(R.id.relate_parent);

      frame_question = (FrameLayout)view.findViewById(R.id.frame_part6_question);
       frame_explan = (FrameLayout)view.findViewById(R.id.frame_part6_explan);
        changeLayoutFrame();
        frame_blur = (FrameLayout)view.findViewById(R.id.frame_blur);
        img_float = (ImageView)view.findViewById(R.id.img_float);
        img_figure = (ImageView)view.findViewById(R.id.img_figure);
        img_explan = (ImageView)view.findViewById(R.id.img_explan);
        img_commment = (ImageView)view.findViewById(R.id.img_comment);
        img_question =(ImageView)view.findViewById(R.id.img_question);
        setUpEventImageFloat();
        setContent();
        setContentQuestion();
        setUpEventImageFigure();
        setUpEventImgExplan();
        setUpEventImgQuestion();
    }

    public void setUpEventImgQuestion(){
        img_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSubmit==1){
                    if(isQuestion==0){
                        isQuestion=1;
                        img_question.setImageResource(R.mipmap.icon_question_active);
                        isFigure=0;
                        img_figure.setImageResource(R.mipmap.icon_figure_detach);
                        if(part!=5){
                            isExplan=0;
                            isComment=0;
                            img_explan.setImageResource(R.mipmap.icon_explan_detach);
                            img_commment.setImageResource(R.mipmap.icon_comment_detach);
                        }
                        if(part==5)setContent();
                        else setContentQuestion();
                    }
                }
            }
        });
    }

    public void setUpEventImgExplan(){
        img_explan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSubmit==1){
                    if(isExplan==0) {
                        isExplan = 1;
                        img_explan.setImageResource(R.mipmap.icon_explan_attach);
                        img_commment.setImageResource(R.mipmap.icon_comment_detach);
                        isComment = 0;
                        if (part != 5) {
                            img_figure.setImageResource(R.mipmap.icon_figure_detach);
                            img_question.setImageResource(R.mipmap.icon_question_detach);
                            isQuestion = 0;
                            isFigure = 0;
                        }
                        setContentQuestion();
                    }

                }
            }
        });
    }

    public void setUpEventImageFigure(){
        img_figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSubmit==1) {
                    if(isFigure ==0){
                        isFigure=1;
                        img_figure.setImageResource(R.mipmap.icon_figure_attach);
                        isQuestion=0;
                        img_question.setImageResource(R.mipmap.icon_question_detach);
                        if(part!=5){
                            img_explan.setImageResource(R.mipmap.icon_explan_detach);
                            img_commment.setImageResource(R.mipmap.icon_comment_detach);
                            isComment=0;
                            isExplan=0;
                        }
                        if(part==5)setContent();
                        else setContentQuestion();
                    }
                }
            }
        });
    }

    public void setUpEventImageFloat(){
        img_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frame_blur.getChildCount()==0) {
                    showFragmentControl();
                    img_float.setVisibility(View.GONE);
                }

            }
        });
    }

    public void showImage(){
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_img);
        img_question.setVisibility(View.VISIBLE);
        img_question.startAnimation(animation);
        img_explan.setVisibility(View.VISIBLE);
        img_explan.startAnimation(animation);
        img_commment.setVisibility(View.VISIBLE);
        img_commment.startAnimation(animation);
        img_figure.setVisibility(View.VISIBLE);
        img_figure.startAnimation(animation);
    }

    public void defaultImage(){
        isQuestion=1;
        if(part==5)isExplan=1;
        else isExplan=0;
        isFigure=0;
        isComment=0;
        img_figure.setImageResource(R.mipmap.icon_figure_detach);
        img_question.setImageResource(R.mipmap.icon_question_active);
        if(part!=5)img_explan.setImageResource(R.mipmap.icon_explan_detach);
        else{
            img_explan.setImageResource(R.mipmap.icon_explan_attach);
        }
        img_commment.setImageResource(R.mipmap.icon_comment_detach);
    }

    public void setContent(){
        switch (part){
            case 5:
                setContentPart5();
                break;
            case 6:
                setContentPart6();
                break;
            case 7:
                setContentPart7();
        }
    }


    public void setContentPart5(){

        FragmentPart5Question fragmentPart5Question = new FragmentPart5Question();
        Bundle bundle = new Bundle();
        bundle.putInt("mode",mode);
        bundle.putInt("question",indexPart5);
        bundle.putInt("submit",isSubmit);
        bundle.putInt("figure",isFigure);
        bundle.putSerializable("data",datapart5[indexPart5]);
        bundle.putString("select",arrChoosePart5.get(indexPart5));
        fragmentPart5Question = new FragmentPart5Question();
        fragmentPart5Question.setArguments(bundle);
        fragmentPart5Question.setiContentQuestion(this);
        if(frame_question.getChildCount()>0) {
            if (isaniquestion == 1) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in, R.anim.scale_content_out).replace(frame_question.getId(), fragmentPart5Question).commit();
            }else{
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_question.getId(), fragmentPart5Question).commit();
            }
        }else {
            if (isaniquestion == 1) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in, R.anim.scale_question_out).add(frame_question.getId(), fragmentPart5Question).commit();
            }else{
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_question.getId(), fragmentPart5Question).commit();

            }
        }
    }

    public void setContentPart6(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",datapart6[indexPart6]);
        bundle.putInt("question",indexPart6);
        Fragment67Content fragmentPart6Content = new Fragment67Content();
        fragmentPart6Content.setArguments(bundle);
        fragmentPart6Content.setiContentQuestion(this);
        if(frame_question.getChildCount()>0){
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in,R.anim.scale_content_out).replace(frame_question.getId(),fragmentPart6Content).commit();
        }else getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in,R.anim.scale_content_out).add(frame_question.getId(),fragmentPart6Content).commit();
    }

    public void setContentPart7(){

        Bundle bundle = new Bundle();

        bundle.putSerializable("data",datapart7[indexPart7]);
        bundle.putInt("question",indexPart7);
        Fragment67Content fragment7Content = new Fragment67Content();
        fragment7Content.setArguments(bundle);
        fragment7Content.setiContentQuestion(this);
        if(frame_question.getChildCount()>0){
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in,R.anim.scale_content_out).replace(frame_question.getId(), fragment7Content).commit();

        }else {

            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in,R.anim.scale_content_out).add(frame_question.getId(), fragment7Content).commit();

        }
    }

    public void setContentQuestion(){
        switch (part){
            case 5:
                setContentQuestionPart5();
                break;
            case 6:
            case 7:
                setContentQuestionPart67();
        }
    }

    public void setContentQuestionPart5(){

                Bundle bundle = new Bundle();
                bundle.putSerializable("data", datapart5[indexPart5]);
                bundle.putInt("submit", isSubmit);
                bundle.putInt("part", part);
                FragmentPartExplan fragmentExplan = new FragmentPartExplan();
                fragmentExplan.setArguments(bundle);
                if (frame_explan.getChildCount() > 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_explan.getId(), fragmentExplan).commit();


                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(), fragmentExplan).commit();
                }

    }



    public void setContentQuestionPart67() {
        if (isQuestion == 1 || isFigure == 1) {
            Bundle bundle1 = new Bundle();
            bundle1.putInt("position", pos_question);
            int count = 0;
            if (part == 6) {
                count = 3;
            } else {
                count = ((IListenPart) (datapart7[indexPart7])).getCountQuestion();
            }
            bundle1.putInt("countquestion", count);

            FragmentPagerQuestion fragmentPartQuestion = new FragmentPagerQuestion();
            fragmentPartQuestion.setiContentQuestion(this);
            fragmentPartQuestion.setArguments(bundle1);
            if (frame_explan.getChildCount() > 0) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_explan.getId(), fragmentPartQuestion).commit();
            } else {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(), fragmentPartQuestion).commit();
            }
        } else {
            if (isExplan == 1) {
                Bundle bundle = new Bundle();
                if (part == 6) bundle.putSerializable("data", datapart6[indexPart6]);
                else bundle.putSerializable("data", datapart7[indexPart7]);
                bundle.putInt("submit", isSubmit);
                bundle.putInt("part", part);
                FragmentPartExplan fragmentExplan = new FragmentPartExplan();
                fragmentExplan.setArguments(bundle);
                if (frame_explan.getChildCount() > 0) {
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_explan.getId(), fragmentExplan).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(), fragmentExplan).commit();
                }
            }else {
                // view Comment
            }
        }

    }

    public int getNumberQuestion(){
        int numberQuestion=0;
        if(part==5)numberQuestion= indexPart5;
        else if(part==6){
            numberQuestion= 41;
            for(int i=0;i<indexPart6;i++){
                numberQuestion+=arrResultPart6.get(i).size();
            }

        }else if(part==7){
            numberQuestion= 53;
            for(int i=0;i<indexPart7;i++){
                numberQuestion+=arrResultPart7.get(i).size();
            }
        }
        return numberQuestion;
    }

    public void showFragmentControl(){
        fragmentControl = new FragmentControlTestReading();
        fragmentControl.setiPartControl(FragmentTestReadManager.this);
       Bundle bundle = new Bundle();
       if(part==5) bundle.putInt("id",arrQuestionPart5.get(indexPart5));
        else if(part==6) bundle.putInt("id",arrQuestionPart6.get(indexPart6));
        else if(part==7) bundle.putInt("id",arrQuestionPart7.get(indexPart7));


        bundle.putIntegerArrayList("questionpart5",arrQuestionPart5);
        bundle.putIntegerArrayList("questionpart6",arrQuestionPart6);
        bundle.putIntegerArrayList("questionpart7",arrQuestionPart7);
        bundle.putInt("mode",mode);
        bundle.putInt("submit",isSubmit);
        bundle.putInt("part",part);
        ArrayList<String > choose7 = new ArrayList<>();
       ArrayList<String> re7 = new ArrayList<>();
        for(int i =0;i<arrChoosePart7.size();i++){
           String s ="";
            String r="";
            for(int j=0;j<arrChoosePart7.get(i).size();j++){
                if(j!=(arrChoosePart7.get(i).size()-1)) {
                    s += arrChoosePart7.get(i).get(j) + "!";
                    r += arrChoosePart7.get(i).get(j) + "!";
                }else {
                    s += arrChoosePart7.get(i).get(j);
                    r += arrChoosePart7.get(i).get(j);
                }
            }
            choose7.add(s);
            re7.add(r);
        }

        ArrayList<String > choose6 = new ArrayList<>();
        ArrayList<String> re6 = new ArrayList<>();
        for(int i =0;i<arrChoosePart6.size();i++){
            String s ="";
            String r="";
            for(int j=0;j<arrChoosePart6.get(i).size();j++){
                if(j!=(arrChoosePart6.get(i).size()-1)) {
                    s += arrChoosePart6.get(i).get(j) + "!";
                    r += arrChoosePart6.get(i).get(j) + "!";
                }else {
                    s += arrChoosePart6.get(i).get(j);
                    r += arrChoosePart6.get(i).get(j);
                }
            }
            choose6.add(s);
            re6.add(r);
        }

        bundle.putStringArrayList("choosepart7",choose7);
        bundle.putStringArrayList("resultpart7",re7);

        bundle.putStringArrayList("choosepart6",choose6);
        bundle.putStringArrayList("resultpart6",re6);

        bundle.putStringArrayList("choosepart5",arrChoosePart5);
        bundle.putStringArrayList("resultpart5",arrResultPart5);


        fragmentControl.setArguments(bundle);
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_blur, fragmentControl).commit();
    }

   /*
   IContent
    */

    @Override
    public void nextContent() {
        if(isSubmit==1)defaultImage();
        switch (part){
            case 5:
                nextContentPart5();
                break;
            case 6:
                nextContentPart6();
                break;
            case 7:
                nextContentPart7();
                break;
        }
    }

    private void nextContentPart5(){
        if(indexPart5!=(datapart5.length-1)){
            indexPart5++;

        }else {

            part=6;
            changeLayoutFrame();
            indexPart6=0;
            pos_question=0;
        }
        setContent();
        setContentQuestion();
    }

    private void nextContentPart6(){
        pos_question=0;
        if(indexPart6!=(datapart6.length-1)){
            indexPart6++;

        }else {
            part=7;
            indexPart7=0;

        }
        setContent();
        setContentQuestion();

    }

    private void nextContentPart7(){
        pos_question=0;
        if(indexPart7!=(datapart7.length-1)){
            indexPart7++;
            setContent();
            setContentQuestion();
        }else{
            part=5;
            changeLayoutFrame();
            indexPart5=0;
        }
        setContent();
        setContentQuestion();
    }


    @Override
    public void backContent() {
        if(isSubmit==1)defaultImage();
        switch (part) {
            case 5:
                backContentPart5();
                break;
            case 6:
                backContentPart6();
                break;
            case 7:
                backContentPart7();
                break;
        }
    }

    private   void backContentPart7(){
        pos_question=0;
        if(indexPart7!=0){
            indexPart7--;

        }else{
            part=6;
            indexPart6 = datapart6.length-1;

        }
        setContent();
        setContentQuestion();
    }

    private   void backContentPart6() {
        pos_question=0;
        if(indexPart6!=0){
            indexPart6--;
        }else{
            part=5;
            changeLayoutFrame();
            indexPart5 = datapart5.length-1;
        }
        setContent();
        setContentQuestion();
    }

    private void backContentPart5() {
        if(indexPart5!=0){
            indexPart5--;
        }else{

            part=7;
            changeLayoutFrame();
            indexPart7 = datapart7.length-1;
            pos_question=0;
        }
        setContent();
        setContentQuestion();
    }





    @Override
    public Bundle getBunldeQuestion(int position) {
        Bundle bundle = new Bundle();
        IDataQuestion iDataQuestion = null;
        if(part==6){
            iDataQuestion =datapart6[indexPart6].getArrQuestion().get(position);
            bundle.putString("select",arrChoosePart6.get(indexPart6).get(position));
            bundle.putInt("part",6);
        }
        else{
            iDataQuestion = datapart7[indexPart7].getQuestions().get(position);
            bundle.putString("select",arrChoosePart7.get(indexPart7).get(position));
            bundle.putInt("part",7);
        }
        bundle.putSerializable("data", (Serializable) iDataQuestion);
        bundle.putInt("figure",isFigure);
        bundle.putInt("mode",mode);
        bundle.putInt("issubmit",isSubmit);
        bundle.putInt("fulltest",1);
        bundle.putInt("numberQuestion",getNumberQuestion());
        return bundle;
    }
    @Override
    public void addChoose(int pos_question,String choose) {
        switch (part){
            case 5:
                addChoosePart5(choose);
                break;
            case 6:
                addChoosePart6(choose);
                break;
            case 7:
                addChoosePart7(choose);
                break;
        }
    }

    private void addChoosePart5(String choose) {
        arrChoosePart5.set(indexPart5,choose);
    }

    private void addChoosePart6(String choose){
        arrChoosePart6.get(indexPart6).set(pos_question,choose);
    }

    private void addChoosePart7(String choose){
        arrChoosePart7.get(indexPart7).set(pos_question,choose);
    }

    @Override
    public void removeFragmentControl() {
        if(frame_blur.getChildCount()>0) {
            img_float.setVisibility(View.VISIBLE);
            getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentControl).commit();
        }
    }

    @Override
    public void changeQuestion(int passage, int index) {
        if(part==5){
            indexPart5= index;
        }else if(part==6) {
            indexPart6 = passage;
            pos_question = index;
        }else if(part ==7) {
            indexPart7= passage;
            pos_question=index;
        }
        if(isSubmit==1)defaultImage();
        changeLayoutFrame();
        setContent();
        setContentQuestion();

    }

    @Override
    public void changePart(int part) {
        this.part= part;
    }


    private boolean check_submit=true;

    @Override
    public void notfySubmit() {
        //check
        int num = checkSubmit();
        if(true){
            removeFragmentControl();
            isSubmit=1;
            timeSubmit();
            setContent();
            setContentQuestion();
        }else {
            String text = "Câu hỏi "+String.valueOf(num) +" chưa hoàn thành!";
            showDialogNofiSubmit(text);
        }
    }

    public void showDialogNofiSubmit(String text){
        final Dialog dialog = new Dialog(getContext(),R.style.ActivityDialog);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels*0.8);
        params.width=width;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setContentView(R.layout.dialog_submit);
        dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setAttributes(params);
        TextView textView = (TextView)dialog.findViewById(R.id.text_notify);
        TextView text_continues = (TextView)dialog.findViewById(R.id.text_start);
        textView.setText(text);
        text_continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public int checkSubmit(){

        int checkPart=-1;
        for(int i=0;i<datapart5.length;i++){
            if(arrChoosePart5.get(i).equals("Not choose")){
                checkPart=i+1;
                check_submit=false;
                return checkPart;
            }
        }
        checkPart=41;

        for(int i=0;i<datapart6.length;i++){
            if(arrChoosePart6.get(i).contains("Not choose")){
                checkPart+=arrChoosePart6.get(i).indexOf("Not choose");
                check_submit=false;
                return checkPart;
            }else {
                checkPart+=arrChoosePart6.get(i).size();
            }
        }

        checkPart=53;

        for(int i=0;i<datapart7.length;i++){
            if(arrChoosePart7.get(i).contains("Not choose")){
                checkPart+=arrChoosePart7.get(i).indexOf("Not choose");
                check_submit=false;
                return checkPart;
            }else {
                checkPart+=arrChoosePart7.get(i).size();
            }
        }
        return checkPart;
    }


    public void changeLayoutFrame(){

        relative_parent.removeView(frame_question);
        relative_parent.removeView(frame_explan);
        frame_question.setId(R.id.frame_part6_question);
        frame_explan.setId(R.id.frame_part6_explan);
        if(part==5){

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

            relative_parent.addView(frame_question,0,params);

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params1.addRule(RelativeLayout.BELOW,R.id.frame_part6_question);

           // params1.addRule(RelativeLayout.BELOW,R.id.frame_part6_question);

            relative_parent.addView(frame_explan,1,params1);
        }else{

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);

            relative_parent.addView(frame_explan,0,params);

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params1.addRule(RelativeLayout.ABOVE,R.id.frame_part6_explan);

            relative_parent.addView(frame_question,1,params1);
        }
    }
}
