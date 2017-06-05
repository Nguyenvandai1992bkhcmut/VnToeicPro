package Dictionary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.AdapterWordSearch;
import com.vntoeic.bkteam.vntoeicpro.R;

import model.Dictionary;
import sqlite.SqliteDictionary;

/**
 * Created by dainguyen on 5/31/17.
 */

public class FragMentHistory extends Fragment {
    AdapterWordSearch adapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_history_search_diction,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(View view){
        recyclerView =  (RecyclerView)view.findViewById(R.id.recycle_his);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        SqliteDictionary sqlite = new SqliteDictionary();
        Dictionary[]data = sqlite.searchHistory();
        adapter = new AdapterWordSearch((DictionaryActivity)getContext(),data);
        adapter.setTextColor(Color.CYAN,Color.WHITE);
        adapter.setHistory();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void removeItemView(int positioin){


    }
}
