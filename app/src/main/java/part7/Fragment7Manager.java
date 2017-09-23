package part7;

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
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.IDataPart;
import model.IDataQuestion;
import model.IListenPart;
import model.ModelPart6;
import model.ModelPart7;

import model.ModelPartCheck;
import sqlite.ManagerPart;
import sqlite.SqlitePart6;
import sqlite.SqlitePart7;
import supportview.FragmentControl;
import supportview.FragmentPartExplan;
import supportview.IContentQuestion;
import supportview.IManagerPart;
import supportview.IPartControl;
import supportview.PartPractiseAcitvity;
import supportview.SendAsynTask;

/**
 * Created by dainguyen on 7/6/17.
 */

public class Fragment7Manager extends Fragment implements IManagerPart, IContentQuestion, IPartControl {
    private RelativeLayout relative_parent;
    public FrameLayout frame_question;
    public FrameLayout frame_explan;
    public FrameLayout frame_blur;
    public ImageView img_float;
    public ImageView img_figure;
    public ImageView img_explan;
    public ImageView img_commment;
    public ImageView img_question;



    public ArrayList<Integer> arrQuestion = new ArrayList<>();
    public ArrayList<ArrayList<String>>arrchoose = new ArrayList<>();
    public ArrayList<ArrayList<String>>arrresult = new ArrayList<>();
    public ArrayList<Integer>arrSubmit = new ArrayList<>();

    public ArrayList<IDataPart>arrData= new ArrayList<>();

    private int mode =0;
    private int index=0;
    private int key=0;
    private int pos_question=0;
    private int isfigure=0;

    private int showControl=0;
    private int subject=0;
    private int isquesion=1;
    private int isExplan=0;
    private int totalQuestion =0;


    private Fragment67Content fragment7Content;
    private FragmentPagerQuestion fragmentPagerQuestion;
    private FragmentControl fragmentControl;
    private FragmentPartExplan fragmentPartExplan;


private int part=0;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    public void getData(){
        Bundle bundle = getArguments();
        if(bundle!=null) {
            part =bundle.getInt("part");
            if(part==7){
                setUpDataPart7(bundle);
            }else if(part==6){
                setUpDataPart6(bundle);
            }


            //mode ==0  unlimitQuestion

        }
    }

    private void setUpDataPart6(Bundle bundle) {
        mode = bundle.getInt("mode");
        key = bundle.getInt("key");
        if (mode == 0) {

            SqlitePart6 sqlitePart6 = new SqlitePart6();
            ModelPart6 data[] = sqlitePart6.randomPart6(1);
                   /*

             */
            ManagerPart managerPart = new ManagerPart();
            managerPart.updateTimePart(part, data[0].getId());

            /*

             */
            arrData.add(data[0]);
            arrQuestion.add(data[0].getId());
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> arr1 = new ArrayList<>();
            for (int j = 0; j < data[0].getArrQuestion().size(); j++) {
                arr.add(data[0].getArrQuestion().get(j).getSol());
                arr1.add("Not choose");
            }
            arrSubmit.add(0);
            arrchoose.add(arr1);
            arrresult.add(arr);
            totalQuestion+=data[0].getArrQuestion().size();


        }else if (mode==1){
            if (key == 0) {
                SqlitePart6 sqlitePart6 = new SqlitePart6();
                ModelPart6 data[] = sqlitePart6.randomPart6(4);
                for (int i = 0; i < data.length; i++) {
                    arrData.add(data[i]);
                    arrQuestion.add(data[i].getId());
                    ArrayList<String> arr = new ArrayList<>();
                    ArrayList<String> arr1 = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        arr.add(data[index].getArrQuestion().get(j).sol);
                        arr1.add("Not choose");
                    }
                    arrSubmit.add(0);
                    arrchoose.add(arr1);
                    arrresult.add(arr);
                }
            }else if(key==1) {
                index = bundle.getInt("begin", 0);
                arrQuestion = bundle.getIntegerArrayList("question");
                SqlitePart6 sqlitePart6 = new SqlitePart6();

                for (int i = 0; i < arrQuestion.size(); i++) {
                    ModelPart6 modelPart6 = sqlitePart6.searchPart6Id(arrQuestion.get(i));
                    arrData.add(modelPart6);
                    ArrayList<String> re = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        re.add(modelPart6.getArrQuestion().get(j).sol);
                    }
                    arrresult.add(re);
                    arrSubmit.add(0);
                }
                String choosen = bundle.getString("choose");
                String arr[] = choosen.split("~~~");
                for (int i = 0; i < arr.length; i++) {
                    String arr1[] = arr[i].split("!");
                    ArrayList<String> ch = new ArrayList<>();
                    for (int j = 0; j < arr1.length; j++) {
                        ch.add(arr1[j]);
                    }
                    arrchoose.add(ch);
                }
            }else if(mode==2){
                subject = bundle.getInt("subject");
                SqlitePart6  sqlitePart6 = new SqlitePart6();
                ModelPart6 data[]=sqlitePart6.randomPart6Subject(subject,1);
                arrData.add(data[0]);
                arrQuestion.add(data[0].getId());
                ArrayList<String>arr = new ArrayList<>();
                ArrayList<String>arr1 = new ArrayList<>();
                for(int j=0;j<3;j++){
                    arr.add(data[index].getArrQuestion().get(j).sol);
                    arr1.add("Not choose");
                }
                arrchoose.add(arr1);
                arrresult.add(arr);
                arrSubmit.add(0);
            }else if(mode==3){
                SqlitePart6 sqlitePart6 = new SqlitePart6();
                ModelPart6 data[]= sqlitePart6.searchPart6Favorite();

                for (int i = 0; i < data.length; i++) {
                    arrData.add(data[i]);
                    arrQuestion.add(data[i].getId());
                    arrSubmit.add(0);
                    ArrayList<String> arr = new ArrayList<>();
                    ArrayList<String> arr1 = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        arr.add(data[index].getArrQuestion().get(j).sol);
                        arr1.add("Not choose");
                    }
                    arrchoose.add(arr1);
                    arrresult.add(arr);
                    arrSubmit.add(0);
                }
                index = bundle.getInt("position");
            }else if(mode==4) {
                SqlitePart6 sqlitePart6 = new SqlitePart6();
                ModelPart6 data[] = sqlitePart6.searchPart6Check();
                for (int i = 0; i < data.length; i++) {
                    arrData.add(data[i]);
                    arrQuestion.add(data[i].getId());
                    arrSubmit.add(0);
                    ArrayList<String> arr = new ArrayList<>();
                    ArrayList<String> arr1 = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        arr.add(data[index].getArrQuestion().get(j).sol);
                        arr1.add("Not choose");
                    }
                    arrchoose.add(arr1);
                    arrresult.add(arr);
                    arrSubmit.add(0);
                }
                index = bundle.getInt("position");
            }
        }
    }

    private void setUpDataPart7(Bundle bundle) {
        mode = bundle.getInt("mode");
        key = bundle.getInt("key");
        if (mode == 0) {

            SqlitePart7 sqlitePart7 = new SqlitePart7();
            ModelPart7 data[] = sqlitePart7.randomPart7(1);

                   /*

             */
            ManagerPart managerPart = new ManagerPart();
            managerPart.updateTimePart(part, data[0].getId());

            /*

             */
            arrData.add(data[0]);
            arrQuestion.add(data[0].getId());
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> arr1 = new ArrayList<>();
            for (int j = 0; j < data[0].getQuestions().size(); j++) {
                arr.add(data[0].getQuestions().get(j).getSol());
                arr1.add("Not choose");
            }
            arrSubmit.add(0);
            arrchoose.add(arr1);
            arrresult.add(arr);
            totalQuestion+=data[0].getQuestions().size();

        }else if (mode==1){
            if(key==0) {
                SqlitePart7 sqlitePart7 = new SqlitePart7();
                ModelPart7 data1[] = sqlitePart7.randomPart7CountQuestion(3, 2);
                addData(data1);

                data1 = sqlitePart7.randomPart7CountQuestion(3, 3);
                addData(data1);
                data1 = sqlitePart7.randomPart7CountQuestion(2, 4);
                addData(data1);
                data1 = sqlitePart7.randomPart7CountQuestion(5, 5);
                addData(data1);
                index = 0;
            }else if(key==1) {
                index = bundle.getInt("begin", 0);
                arrQuestion = bundle.getIntegerArrayList("question");
                SqlitePart7 sqlitePart7 = new SqlitePart7();



                for (int i = 0; i < arrQuestion.size(); i++) {
                    ModelPart7 modelPart7 = sqlitePart7.searchPart7Id(arrQuestion.get(i));
                    arrData.add(modelPart7);
                    ArrayList<String> re = new ArrayList<>();
                    for (int j = 0; j < modelPart7.getQuestions().size(); j++) {
                        re.add(modelPart7.getQuestions().get(j).getSol());
                    }
                    arrresult.add(re);
                    arrSubmit.add(0);
                }
                String choosen = bundle.getString("choose");
                String arr[] = choosen.split("~~~");
                for (int i = 0; i < arr.length; i++) {
                    String arr1[] = arr[i].split("!");
                    ArrayList<String> ch = new ArrayList<>();
                    for (int j = 0; j < arr1.length; j++) {
                        ch.add(arr1[j]);
                    }
                    arrchoose.add(ch);
                }
            }
        }
    }

    public void addData(ModelPart7 data1[]){

        for(int i =0;i<data1.length;i++) {
            arrData.add(data1[i]);
            index++;
            arrQuestion.add(data1[i].getId());
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> arr1 = new ArrayList<>();
            for (int j = 0; j < data1[0].getQuestions().size(); j++) {
                arr.add(data1[0].getQuestions().get(j).getSol());
                arr1.add("Not choose");
            }
            arrchoose.add(arr1);
            arrresult.add(arr);
            arrSubmit.add(0);
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
        frame_blur = (FrameLayout)view.findViewById(R.id.frame_blur);
        img_float = (ImageView)view.findViewById(R.id.img_float);
        img_figure = (ImageView)view.findViewById(R.id.img_figure);
        img_explan = (ImageView)view.findViewById(R.id.img_explan);
        img_commment = (ImageView)view.findViewById(R.id.img_comment);
        img_question =(ImageView)view.findViewById(R.id.img_question);
        changeLayoutFrame();
        setUpEventImageFloat();
        setContent();
        setContentQuestion();
        setUpEventImageFigure();
        setUpEventImgExplan();
        setUpEventImgQuestion();
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

    public void setUpEventImageFigure(){
        img_figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrSubmit.get(index)==1) {
                    if(isfigure ==0){

                        img_figure.setImageResource(R.mipmap.icon_figure_attach);
                        img_question.setImageResource(R.mipmap.icon_question_detach);
                        img_explan.setImageResource(R.mipmap.icon_explan_detach);
                        isfigure=1;
                        isExplan=0;
                        isquesion=0;
                        setContentQuestion();
                    }
                }
            }
        });
    }

    public void setUpEventImgExplan(){
        img_explan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrSubmit.get(index)==1 ) {
                    if(isExplan==0) {
                        isfigure=0;
                        isquesion=0;
                        isExplan=1;
                        img_figure.setImageResource(R.mipmap.icon_figure_detach);
                        img_question.setImageResource(R.mipmap.icon_question_detach);
                        img_explan.setImageResource(R.mipmap.icon_explan_attach);

                        showExplan();
                    }
                }
            }
        });
    }

    public void setUpEventImgQuestion(){
        img_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isquesion==0){

                    img_figure.setImageResource(R.mipmap.icon_figure_detach);
                    img_question.setImageResource(R.mipmap.icon_question_active);
                    img_explan.setImageResource(R.mipmap.icon_explan_detach);
                    isfigure=0;
                    isquesion=1;
                    isExplan=0;
                    setContentQuestion();

                }
            }
        });
    }

    public void showExplan(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) arrData.get(index));
        bundle.putInt("submit",arrSubmit.get(index));
        bundle.putInt("part",part);
        fragmentPartExplan = new FragmentPartExplan();
        fragmentPartExplan.setArguments(bundle);
        if (frame_explan.getChildCount() > 0) {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_explan.getId(), fragmentPartExplan).commit();
        } else getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(), fragmentPartExplan).commit();

    }

    public void showFragmentControl(){
        fragmentControl = new FragmentControl();
        fragmentControl.setIPartControl(Fragment7Manager.this);
        Bundle bundle = new Bundle();
        bundle.putInt("id",arrQuestion.get(index));
        bundle.putInt("mode",mode);
        bundle.putIntegerArrayList("arrsubmit",arrSubmit);
        bundle.putInt("part",part);
        ArrayList<String > choose = new ArrayList<>();
        ArrayList<String> re = new ArrayList<>();
        for(int i =0;i<arrchoose.size();i++){
            String s ="";
            String r="";
            for(int j=0;j<arrchoose.get(i).size();j++){
                if(j!=(arrchoose.get(i).size()-1)) {
                    s += arrchoose.get(i).get(j) + "!";
                    r += arrresult.get(i).get(j) + "!";
                }else {
                    s += arrchoose.get(i).get(j);
                    r += arrresult.get(i).get(j);
                }
            }
            choose.add(s);
            re.add(r);
        }

        bundle.putStringArrayList("choose",choose);
        bundle.putStringArrayList("result",re);
        fragmentControl.setArguments(bundle);
        if(frame_blur.getChildCount()==0) {
            getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_blur, fragmentControl).commit();
        }else{
            getChildFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_blur, fragmentControl).commit();
        }
    }


    public void setContent(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) arrData.get(index));

        bundle.putInt("question",index);
        fragment7Content = new Fragment67Content();
        fragment7Content.setArguments(bundle);
        fragment7Content.setiContentQuestion(this);
        if(frame_question.getChildCount()>0){
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in,R.anim.scale_content_out).replace(frame_question.getId(), fragment7Content).commit();

        }else {

                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in,R.anim.scale_content_out).add(frame_question.getId(), fragment7Content).commit();

        }



    }

    /*
    add subject part 6 7
     */

    public void setContentQuestion(){
        //Bundle Question

        Bundle bundle1 = new Bundle();
        bundle1.putInt("position",pos_question);
        int count = ((IListenPart)arrData.get(index)).getCountQuestion();
        bundle1.putInt("countquestion",count);

         fragmentPagerQuestion = new FragmentPagerQuestion();
        fragmentPagerQuestion.setArguments(bundle1);
        fragmentPagerQuestion.setiContentQuestion(this);
        if(frame_explan.getChildCount()>0) {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_explan.getId(), fragmentPagerQuestion).commit();
        }else {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(), fragmentPagerQuestion).commit();
        }




    }
    /*
    begin Interface Imanager Part
     */

    @Override
    public Bundle getBunldeSave() {
        Bundle bundle = new Bundle();
        bundle.putInt("begin",index);
        bundle.putIntegerArrayList("question",arrQuestion);
        String s ="";

        for(int i=0;i<arrchoose.size();i++){

            for(int j=0;j<arrchoose.get(i).size();j++){
                if(j!=(arrchoose.get(i).size()-1)){
                    s+=arrchoose.get(i).get(j)+"!";

                }
                else{
                    s+=arrchoose.get(i).get(j);

                }
            }
            if(i!=arrchoose.size()-1)s+="~~~";

        }
        bundle.putString("choose",s);
        return bundle;
    }

    @Override
    public int getIsSubmit() {
        if(arrSubmit.contains(0))return 0;
        else return 1;

    }

    @Override
    public void setBundle(Bundle bundle) {
        this.setArguments(bundle);
    }

    @Override
    public void timeSubmit() {
        showImage();
        for(int i=0;i<arrSubmit.size();i++)arrSubmit.set(i,1);
        sendToSever();
        setContent();
        setContentQuestion();
    }

    @Override
    public void sendResultToSever(String... arr) {
        SendAsynTask sendAsynTask = new SendAsynTask();
        sendAsynTask.execute(arr[0],arr[1],arr[2]);
    }


    /*
    end ---- Imanager Part
     */


    /*
    interface IquestionPart
     */

    @Override
    public void addChoose(int position,String choose) {
        arrchoose.get(index).set(position,choose);
        if(mode!=1) {
            if (!arrchoose.get(index).contains("Not choose") && arrSubmit.get(index) == 0) {
                if (showControl == 0) {
                    showFragmentControl();
                    showControl = 1;
                }

            }
        }else if(mode==1) {
            if (!arrSubmit.contains(0)) {
                if(showControl==0) {
                    showFragmentControl();
                    showControl=1;
                }
            }
        }
    }



    /*
    end IquestionPart
     */

    /*
    Image
     */

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

    public void hideImage(){
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_img_out);

        img_question.startAnimation(animation);
        img_question.setVisibility(View.GONE);
        img_explan.startAnimation(animation);
        img_explan.setVisibility(View.GONE);
        img_commment.startAnimation(animation);
        img_commment.setVisibility(View.GONE);
        img_figure.startAnimation(animation);
        img_figure.setVisibility(View.GONE);
        defaultImage();
    }

    public void defaultImage(){
        isquesion=1;
        isExplan=0;
        isfigure=0;
        img_figure.setImageResource(R.mipmap.icon_figure_detach);
        img_question.setImageResource(R.mipmap.icon_question_active);
        img_explan.setImageResource(R.mipmap.icon_explan_detach);
    }
    /*
       end Image
        */

    /*
    Iterface Control
     */
    @Override
    public void removeFragmentControl() {
        if(frame_blur.getChildCount()>0) {
            img_float.setVisibility(View.VISIBLE);
            getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentControl).commit();
        }
    }

    @Override
    public void changeQuestion(int index1,int ques) {
        removeFragmentControl();
        this.index =index1;
        pos_question=ques;
        isfigure=0;
        defaultImage();
        setContent();
        setContentQuestion();
    }

    @Override
    public void changePart(int part) {

    }

    @Override
    public void notfySubmit() {
        removeFragmentControl();
        if(mode!=1) {
            if (arrchoose.get(index).contains("Not choose")) {
                String text = "Câu  " + String.valueOf(arrchoose.get(index).indexOf("Not choose") + 1) + " chưa hoàn thành ";
                showDialogNofiSubmit(text);
            } else {
                arrSubmit.set(index, 1);
                showImage();
                sendToSever();
                setContentQuestion();
                ((PartPractiseAcitvity)(getContext())).showAds();
            }
        }else if(mode==1){
            int passage = checkFinnish();
            if(passage!=-1){
                        /*
                        chua lam xong khong cho submmit
                         */
                String text= "Passage " + String.valueOf(passage+1) +" chưa hoàn thành ";
                showDialogNofiSubmit(text);
                return;
            }else{
                PartPractiseAcitvity.stopTime();
                timeSubmit();
            }
        }

    }

    public int checkFinnish(){
        int b = -1;
        for(int i=0;i<arrchoose.size();i++){
            if(arrchoose.get(i).contains("Not choose")){
                b=i;
                return b;
            }
        }
        return b;
    }

    /*
    end
     */

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

    /*
    Iterface Ipart Content
     */



    @Override
    public void nextContent() {
        if(mode==0 || mode==2){
//            if((index== arrQuestion.size()-1) && arrSubmit.get(index)==0){
//                Toast.makeText(getContext(),"You need submit before change quesiton!",Toast.LENGTH_LONG).show();
//
//            }else {
            index = arrQuestion.size() - 1;
//                if (arrSubmit.get(index) == 1) {
//                    getData();
//                    index++;
//                }

            getData();
            index++;
                setNewQuestion();
                setContent();
                setContentQuestion();
        //}

        }else if (mode==1){
            if(index!=arrQuestion.size()-1){
                index++;
                isfigure=0;
                pos_question=0;
                defaultImage();
                setContent();
                setContentQuestion();
            }

        }else if(mode==3 || mode==4){
            if(index!=arrData.size()-1) {
                index++;
                isfigure = 0;
                pos_question=0;
                if(arrSubmit.get(index) ==1)showImage();
                else hideImage();
                if(arrchoose.get(index).contains("Not choose"))showControl=0;
                else showControl=1;
                setContent();
                setContentQuestion();
            }

        }
    }

    public void setNewQuestion(){
        pos_question=0;
        showControl=0;
        //index = arrData.size()-1;
        isfigure=0;
        hideImage();
    }

    @Override
    public void backContent() {
        if(index!=0) {
            index--;
            setNewQuestion();
            setContent();
            setContentQuestion();
        }
//        if(mode==0 || mode==2)return;
//        else if(index!=0){
//            index--;
//            isfigure=0;
//            pos_question=0;
//            if(mode==3 || mode==4){
//                defaultImage();
//                if(arrchoose.get(index).contains("Not choose"))showControl=0;
//                else showControl=1;
//
//            }
//            setContent();
//            setContentQuestion();
//        }
    }

    @Override
    public Bundle getBunldeQuestion(int position) {
        Bundle bundle = new Bundle();
        IDataQuestion iDataQuestion = null;
        if(part==6)iDataQuestion = ((ModelPart6)arrData.get(index)).getArrQuestion().get(position);
        else iDataQuestion = ((ModelPart7)arrData.get(index)).getQuestions().get(position);
        bundle.putSerializable("data", (Serializable) iDataQuestion);
        bundle.putString("select",arrchoose.get(index).get(position));
        bundle.putInt("figure",isfigure);
        bundle.putInt("mode",mode);
        bundle.putInt("issubmit",arrSubmit.get(index));
        bundle.getInt("fulltest",0);
        bundle.putInt("part",part);

        return bundle;
    }

    public void sendToSever(){
        if(mode!=1) {
            ManagerPart managerPart = new ManagerPart();
            managerPart.updateTimePart(part, arrData.get(index).getId());
            DateFormat df = new SimpleDateFormat("EEE d MMM yyyy, HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            //send to sever
            for (int i = 0; i < ((IListenPart)(arrData.get(index))).getCountQuestion(); i++) {
                sendResultToSever(String.valueOf(((IListenPart)arrData.get(index)).getLinkFigure(i+1)), arrchoose.get(index).get(i),((IListenPart)arrData.get(index)).getToken());

                if (arrchoose.get(index).get(i).equals(arrresult.get(index).get(i))) {
                    PartPractiseAcitvity.updateTextCorrect(true, totalQuestion);
                    managerPart.insertPartCheck(new ModelPartCheck(part, arrQuestion.get(index), date, 1));
                } else {
                    PartPractiseAcitvity.updateTextCorrect(false,totalQuestion);
                    managerPart.insertPartCheck(new ModelPartCheck(part, arrQuestion.get(index), date, 0));
                }
            }

        }else if(mode==1){
            ManagerPart managerPart = new ManagerPart();

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            for(int j=0;j<arrQuestion.size();j++) {
                managerPart.updateTimePart(part, arrData.get(j).getId());

                //send to sever

                for (int i = 0; i < ((IListenPart)(arrData.get(index))).getCountQuestion(); i++) {

                    sendResultToSever(String.valueOf(((IListenPart)arrData.get(index)).getLinkFigure(i+1)), arrchoose.get(j).get(i),((IListenPart)arrData.get(index)).getToken());
                    if (arrchoose.get(j).get(i).equals(arrresult.get(j).get(i))) {
                        managerPart.insertPartCheck(new ModelPartCheck(part, arrQuestion.get(j), date, 1));
                    } else {
                        managerPart.insertPartCheck(new ModelPartCheck(part, arrQuestion.get(j), date, 0));
                    }
                }
            }
        }
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


    /*
    end
     */

}
