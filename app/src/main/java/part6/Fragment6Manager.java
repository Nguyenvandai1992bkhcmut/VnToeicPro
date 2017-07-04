package part6;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.ModelPart6;
import model.ModelPartCheck;
import sqlite.SqlitePart6;
import supportview.FragmentPartExplan;
import supportview.IManagerPart;
import supportview.PartPractiseAcitvity;
import supportview.SendAsynTask;

/**
 * Created by dainguyen on 7/2/17.
 */

public class Fragment6Manager extends Fragment implements FragmentPart6Content.IPart6Manager,FragmentPart6Question.IQuestionPart6,Fragment6Control.IPart6Control, IManagerPart{

    public FrameLayout frame_question;
    public FrameLayout frame_explan;
    public FrameLayout frame_blur;
    public ImageView img_float;
    public ImageView img_figure;
    public ImageView img_explan;
    public ImageView img_commment;

    private ModelPart6[]data;
    public ArrayList<Integer> arrQuestion = new ArrayList<>();
    public ArrayList<ArrayList<String>>arrchoose = new ArrayList<>();
    public ArrayList<ArrayList<String>>arrresult = new ArrayList<>();
    private int key=0;
    public  int mode=0;
    public int index=0;
    public  int issubmit=0;

    public int subject=0;
    public int pos_question=0;
    public int isfigure=0;

    public int showControl =0;
    public int isExplan =0;

    FragmentPart6Content fragmentPart6Content;
    FragmentPart6Question fragmentPart6Question;
    Fragment6Control fragment6Control;
    FragmentPartExplan fragmentPart6Explan;

    private final String LINKPART6 ="Http://vntoeic.com/api/v1/part6/result/";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
    }

    public void getData(){
        Bundle bundle = getArguments();
        if(bundle!=null) {
            mode = bundle.getInt("mode");
            key = bundle.getInt("key");
            //mode ==0  unlimitQuestion
            if(mode==0){

                SqlitePart6 sqlitePart6 = new SqlitePart6();
                data=sqlitePart6.randomPart6(1);
                arrQuestion.add(data[0].getId());
                ArrayList<String>arr = new ArrayList<>();
                ArrayList<String>arr1 = new ArrayList<>();
                for(int j=0;j<3;j++){
                    arr.add(data[index].getArrQuestion().get(j).sol);
                    arr1.add("Not choose");
                }
                arrchoose.add(arr1);
                arrresult.add(arr);

            }
            else if(mode==1) {
                // 40 question
                if (key == 0) {
                    SqlitePart6 sqlitePart6 = new SqlitePart6();
                    data = sqlitePart6.randomPart6(4);
                    for (int i = 0; i < 4; i++) {
                        arrQuestion.add(data[i].getId());
                        ArrayList<String> arr = new ArrayList<>();
                        ArrayList<String> arr1 = new ArrayList<>();
                        for (int j = 0; j < 3; j++) {
                            arr.add(data[index].getArrQuestion().get(j).sol);
                            arr1.add("Not choose");
                        }
                        arrchoose.add(arr1);
                        arrresult.add(arr);
                    }
                }else if(key==1){
                    index = bundle.getInt("begin",0);
                    arrQuestion = bundle.getIntegerArrayList("question");
                    SqlitePart6 sqlitePart6 = new SqlitePart6();
                    data=new ModelPart6[arrQuestion.size()];;

                    for(int i =0;i<arrQuestion.size();i++){
                        ModelPart6 modelPart6 = sqlitePart6.searchPart6Id(arrQuestion.get(i));
                        data[i]= modelPart6;
                        ArrayList<String>re = new ArrayList<>();
                        for(int j=0;j<3;j++){
                            re.add(modelPart6.getArrQuestion().get(j).sol);
                        }
                        arrresult.add(re);
                    }
                    String choosen = bundle.getString("choose");
                    String arr[] = choosen.split("~~~",4);
                    for(int i =0;i<arr.length;i++){
                        String arr1[] = arr[i].split("!",3);
                        ArrayList<String>ch = new ArrayList<>();
                        for(int j=0;j<arr1.length;j++){
                            ch.add(arr1[j]);
                        }
                        arrchoose.add(ch);
                    }
                }
            }else if(mode==2){
                subject = bundle.getInt("subject");
                SqlitePart6  sqlitePart6 = new SqlitePart6();
                data=sqlitePart6.randomPart6Subject(subject,1);
                arrQuestion.add(data[0].getId());
                ArrayList<String>arr = new ArrayList<>();
                ArrayList<String>arr1 = new ArrayList<>();
                for(int j=0;j<3;j++){
                    arr.add(data[index].getArrQuestion().get(j).sol);
                    arr1.add("Not choose");
                }
                arrchoose.add(arr1);
                arrresult.add(arr);
            }
            }else if(mode==3){
                SqlitePart6 sqlitePart6 = new SqlitePart6();
                data= sqlitePart6.searchPart6Favorite();
                    for (int i = 0; i < data.length; i++) {
                    arrQuestion.add(data[i].getId());
                    ArrayList<String> arr = new ArrayList<>();
                    ArrayList<String> arr1 = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        arr.add(data[index].getArrQuestion().get(j).sol);
                        arr1.add("Not choose");
                    }
                    arrchoose.add(arr1);
                    arrresult.add(arr);
                 }
                index = bundle.getInt("position");
            }else if(mode==4){
                SqlitePart6 sqlitePart6 = new SqlitePart6();
                data= sqlitePart6.searchPart6Check();
                for (int i = 0; i < data.length; i++) {
                    arrQuestion.add(data[i].getId());
                    ArrayList<String> arr = new ArrayList<>();
                    ArrayList<String> arr1 = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        arr.add(data[index].getArrQuestion().get(j).sol);
                        arr1.add("Not choose");
                    }
                    arrchoose.add(arr1);
                    arrresult.add(arr);
                }
                index = bundle.getInt("position");
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
        frame_question = (FrameLayout)view.findViewById(R.id.frame_part6_question);
        frame_explan = (FrameLayout)view.findViewById(R.id.frame_part6_explan);
        frame_blur = (FrameLayout)view.findViewById(R.id.frame_blur);
        img_float = (ImageView)view.findViewById(R.id.img_float);
        img_figure = (ImageView)view.findViewById(R.id.img_figure);
        img_explan = (ImageView)view.findViewById(R.id.img_explan);
        img_commment = (ImageView)view.findViewById(R.id.img_comment);
        setUpEventImageFloat();
        setContent();
        setContentQuestion();
        setUpEventImageFigure();
        setUpEventImgExplan();
    }

    public void setUpEventImageFloat(){
        img_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frame_blur.getChildCount()==0) {
                   showFragmentControl();
                }else {
                    getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragment6Control).commit();
                }

            }
        });
    }

    public void showFragmentControl(){
        fragment6Control = new Fragment6Control();
        fragment6Control.setIPart6Control(Fragment6Manager.this);
        Bundle bundle = new Bundle();
        bundle.putInt("id",arrQuestion.get(index));
        bundle.putIntegerArrayList("question",arrQuestion);
        bundle.putInt("mode",mode);
        bundle.putInt("submit",issubmit);
        ArrayList<String > choose = new ArrayList<>();
        ArrayList<String> re = new ArrayList<>();
        for(int i =0;i<arrchoose.size();i++){
            String s ="";
            String r="";
            for(int j=0;j<3;j++){
                if(j!=2) {
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
        fragment6Control.setArguments(bundle);
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_blur, fragment6Control).commit();
    }

    public void setUpEventImageFigure(){
        img_figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(issubmit==1) {
                    isfigure = 1;
                    setContentQuestion();
                }
            }
        });
    }

    public void setUpEventImgExplan(){
        img_explan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(issubmit==1 ) {
                    showExplan();
                }
            }
        });
    }

    public void showExplan(){
        if(isExplan==0) {
            Bundle bundle = new Bundle();
            if (mode == 0|| mode==2) bundle.putSerializable("data", data[0]);
            else bundle.putSerializable("data", data[index]);
            fragmentPart6Explan = new FragmentPartExplan();
            fragmentPart6Explan.setArguments(bundle);
            if (frame_explan.getChildCount() > 0) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_explan.getId(), fragmentPart6Explan).commit();
            } else {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(), fragmentPart6Explan).commit();
            }
            isExplan=1;
        }else{
            setContentQuestion();
            isExplan=0;
        }
    }

    public void setContent(){
        if(frame_question.getChildCount()>0){
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart6Content).commit();
        }

        // Bundle content
        Bundle bundle = new Bundle();
        if(mode==0||mode==2)bundle.putSerializable("data",data[0]);
        else bundle.putSerializable("data",data[index]);
        bundle.putInt("question",index);
        fragmentPart6Content = new FragmentPart6Content();
        fragmentPart6Content.setArguments(bundle);
        fragmentPart6Content.setIPart6Manager(this);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_question.getId(),fragmentPart6Content).commit();
    }

    public void setContentQuestion(){
        //Bundle Question

        Bundle bundle1 = new Bundle();
        if(mode==0 || mode==2)bundle1.putSerializable("dataquestion",data[0].getArrQuestion().get(pos_question));
        else bundle1.putSerializable("dataquestion",data[index].getArrQuestion().get(pos_question));
        bundle1.putString("select",arrchoose.get(index).get(pos_question));
        bundle1.putInt("position",pos_question);
        if(mode==0 || mode==2)bundle1.putInt("idquestion",data[0].getId());
        else bundle1.putInt("idquestion",data[index].getId());
        bundle1.putInt("figure",isfigure);

        if(mode==0 && index<arrQuestion.size()-1){
            issubmit=1;
        }
        bundle1.putInt("issubmit",issubmit);

        fragmentPart6Question = new FragmentPart6Question();
        fragmentPart6Question.setiQuestionPart6(this);
        fragmentPart6Question.setArguments(bundle1);
        if(frame_explan.getChildCount()>0){
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_explan.getId(),fragmentPart6Question).commit();
        }else {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(), fragmentPart6Question).commit();
        }

    }

    @Override
    public void nextQues() {
        if(mode==0 || mode==2){
            if(index<arrQuestion.size()-1){
                setNewQuestion();
                setContent();
                setContentQuestion();
            }
            else if(issubmit==1) {
                SqlitePart6 sqlitePart6 = new SqlitePart6();
                if (mode == 0) data = sqlitePart6.randomPart6(1);
                else data = sqlitePart6.randomPart6Subject(subject, 1);
                ArrayList<String> arr = new ArrayList<>();
                ArrayList<String> arr1 = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    arr.add(data[0].getArrQuestion().get(j).sol);
                    arr1.add("Not choose");
                }
                arrQuestion.add(data[0].getId());
                arrresult.add(arr);
                arrchoose.add(arr1);
                setNewQuestion();
                setContent();
                setContentQuestion();
            }else {
                showDialogNofiSubmit("Bạn Chưa Submit");
            }

        }else if (mode==1){
            if(index!=3){
                index++;
                isfigure=0;
                pos_question=0;
                setContent();
                setContentQuestion();
            }

        }
    }

    public void setNewQuestion(){
        pos_question=0;
        index = arrQuestion.size()-1;
        showControl=0;
        issubmit=0;
        isfigure=0;
        img_figure.setVisibility(View.GONE);
        img_explan.setVisibility(View.GONE);
        img_commment.setVisibility(View.GONE);
    }

    @Override
    public void backQues() {
        if(mode!=1)return;
        else if(index!=0){
            index--;
            isfigure=0;
            pos_question=0;
            setContent();
            setContentQuestion();
        }
    }

    public void sendToSever(){
        if(mode==0 || mode==2) {
            SqlitePart6 sqlitePart6 = new SqlitePart6();
            sqlitePart6.updateTimePart(6, data[0].getId());
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            //send to sever

            for (int i = 0; i < 3; i++) {

                sendResultToSever(String.valueOf(data[0].getId()), String.valueOf(i + 1), arrchoose.get(index).get(i));
                if (arrchoose.get(index).get(i).equals(arrresult.get(index).get(i))) {
                    PartPractiseAcitvity.updateTextCorrect(true, arrQuestion.size() * 3);
                    sqlitePart6.insertPartCheck(new ModelPartCheck(6, arrQuestion.get(index), date, 1));
                } else {
                    PartPractiseAcitvity.updateTextCorrect(false, arrQuestion.size() * 3);
                    sqlitePart6.insertPartCheck(new ModelPartCheck(6, arrQuestion.get(index), date, 0));
                }
            }

        }else if(mode==1){
            SqlitePart6 sqlitePart6 = new SqlitePart6();

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            for(int j=0;j<arrQuestion.size();j++) {
                sqlitePart6.updateTimePart(6, data[j].getId());


                //send to sever

                for (int i = 0; i < 3; i++) {

                    sendResultToSever(String.valueOf(data[j].getId()), String.valueOf(i + 1), arrchoose.get(j).get(i));
                    if (arrchoose.get(j).get(i).equals(arrresult.get(j).get(i))) {
                        PartPractiseAcitvity.updateTextCorrect(true, arrQuestion.size() * 3);
                        sqlitePart6.insertPartCheck(new ModelPartCheck(6, arrQuestion.get(j), date, 1));
                    } else {
                        PartPractiseAcitvity.updateTextCorrect(false, arrQuestion.size() * 3);
                        sqlitePart6.insertPartCheck(new ModelPartCheck(6, arrQuestion.get(j), date, 0));
                    }
                }
            }
        }
    }

    @Override
    public void addChoose(String choose) {
        arrchoose.get(index).set(pos_question,choose);
        if(mode==0|| mode==2) {
            if (!arrchoose.get(index).contains("Not choose")) {
                if(showControl==0) {
                    showFragmentControl();
                    showControl=1;
                }

            }
        }else if(mode==1) {
            if (checkFinnish()) {
                if(showControl==0) {
                    showFragmentControl();
                    showControl=1;
                }
            }
        }
    }

    public boolean checkFinnish(){
        boolean b = true;
        for(int i=0;i<arrchoose.size();i++){
            if(arrchoose.get(i).contains("Not choose")){
                b=false;
                break;
            }
        }
        return b;
    }

    @Override
    public void next() {
        if(pos_question!=2){
            isfigure=0;
            pos_question++;
            setContentQuestion();
        }
    }

    @Override
    public void back() {
        if(pos_question!=0){
            isfigure=0;
            pos_question--;
            setContentQuestion();
        }
    }

    @Override
    public void removeFragmentControl() {
        if(frame_blur.getChildCount()>0) {
            getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragment6Control).commit();
        }
    }

    @Override
    public void changeQuestion(int id,int ques) {
        removeFragmentControl();
        this.index = arrQuestion.indexOf(id);
        pos_question=ques;
        if(mode==0 || mode==2){
            SqlitePart6 sqlitePart6 = new SqlitePart6();
            data = new ModelPart6[1];
            data[0] = sqlitePart6.searchPart6Id(arrQuestion.get(this.index));
            issubmit=1;
            isfigure=0;
        }
        setContent();
        setContentQuestion();
    }

    @Override
    public void notfySubmit() {
        removeFragmentControl();
        if(mode==0 || mode==2){
            if(arrchoose.get(index).contains("Not choose")){
                String text ="Câu  " + String.valueOf(arrchoose.get(index).indexOf("Not choose") +1) +" chưa hoàn thành ";
                showDialogNofiSubmit(text);
            }else{
                issubmit =1;
                img_explan.setVisibility(View.VISIBLE);
                img_figure.setVisibility(View.VISIBLE);
                sendToSever();
                setContentQuestion();
            }
        }
        else if(mode==1){
            if(!checkFinnish()&& issubmit==0){
                        /*
                        chua lam xong khong cho submmit
                         */
                int passage =0;
                for(int i=0;i<arrchoose.size();i++){
                    if(arrchoose.get(i).contains("Not choose")){
                        passage=i;
                        break;
                    }
                }
                String text= "Passage " + String.valueOf(passage+1) +" chưa hoàn thành ";
                showDialogNofiSubmit(text);
                return;
            }else if(issubmit==0){
                PartPractiseAcitvity.stopTime();
                timeSubmit();
            }
        }
    }

    public void showDialogNofiSubmit(String text){
        final Dialog dialog = new Dialog(getContext(),R.style.ActivityDialog);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
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

    @Override
    public Bundle getBunldeSave() {
        Bundle bundle = new Bundle();
        bundle.putInt("begin",index);
        bundle.putIntegerArrayList("question",arrQuestion);
        String s ="";

        for(int i=0;i<arrchoose.size();i++){

            for(int j=0;j<3;j++){
                if(j!=2){
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
        return issubmit;
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.setArguments(bundle);
    }

    @Override
    public void timeSubmit(){
        img_figure.setVisibility(View.VISIBLE);
        img_explan.setVisibility(View.VISIBLE);
        issubmit=1;
        sendToSever();
        setContent();
        setContentQuestion();
    }

    @Override
    public void sendResultToSever(String... arr) {
        SendAsynTask sendAsynTask = new SendAsynTask();

        sendAsynTask.execute(LINKPART6,arr[0],arr[1],arr[2]);
    }




}
