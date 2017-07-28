package supportview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.IListenPart;

/**
 * Created by dainguyen on 7/11/17.
 */

public class FragmentControlListening extends Fragment {
    private ImageView img_replay;
    private ImageView img_back;
    private ImageView img_next;
    private ImageView img_star;
    private ImageView img_play;
    private int mode;
    private int part;
    private int isSubmit;
    private int isPlay=0;
    private int idQuesion=0;

    private IPartControlListen iPartControlListen;

    public void setiPartControlListen(IPartControlListen iPartControlListen){
        this.iPartControlListen = iPartControlListen;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
    }

    public void getData(){
        Bundle bundle = getArguments();
        mode=bundle.getInt("mode");
        part = bundle.getInt("part");
        isSubmit = bundle.getInt("isSubmit");
        idQuesion = bundle.getInt("idQuestion");

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_listening,container,false);
        setUpLayout(view);
        return view;
    }


    public void setDefault(){
        isPlay=0;
        img_play.setImageResource(R.mipmap.icon_part1_play);

        if(iPartControlListen.checkFavorite()){
            img_star.setImageResource(R.mipmap.icon_part1_yellow_star);
        }else {
            img_star.setImageResource(R.mipmap.icon_part1_gray_star);

        }
    }

    public void setUpLayout(View view){
        img_replay =(ImageView)view.findViewById(R.id.img_replay);
        img_back =(ImageView)view.findViewById(R.id.img_back);
        img_next =(ImageView)view.findViewById(R.id.img_next);
        img_star =(ImageView)view.findViewById(R.id.img_star);
        img_play =(ImageView)view.findViewById(R.id.img_play);


        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlay==0) {
                    img_play.setImageResource(R.mipmap.icon_part1_stop);
                    iPartControlListen.playAudio();
                    isPlay=1;
                }else if(isPlay==1){
                    img_play.setImageResource(R.mipmap.icon_part1_play);
                    iPartControlListen.pauseAudio();
                    isPlay=2;
                }else {
                    if(iPartControlListen.getIsSumit()==1 || iPartControlListen.getIsFinnishAudio()==0) {
                        img_play.setImageResource(R.mipmap.icon_part1_stop);
                        iPartControlListen.resumeAudio();
                        isPlay = 1;
                    }else{
                        Toast.makeText(getContext(),"You need submit before replay audio!",Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPartControlListen.nextQuesiton();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPartControlListen.backQuestion();
            }
        });

        img_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPartControlListen.replayAudio();
            }
        });


        if(iPartControlListen.checkFavorite()){
            img_star.setImageResource(R.mipmap.icon_part1_yellow_star);
        }else {
            img_star.setImageResource(R.mipmap.icon_part1_gray_star);

        }

        img_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iPartControlListen.checkFavorite()){
                    img_star.setImageResource(R.mipmap.icon_part1_gray_star);
                    iPartControlListen.favoriteQuesiton(false);
                }else{
                    img_star.setImageResource(R.mipmap.icon_part1_yellow_star);
                    iPartControlListen.favoriteQuesiton(true);
                }
            }
        });
    }


}
