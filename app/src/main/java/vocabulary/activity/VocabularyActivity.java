package vocabulary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;

import GameVocabulary.Activity.LearningActivity;
import GameVocabulary.Activity.PracticeActivity;
import dictionary.FragmentDictionary;
import model.Dictionary;
import model.ModelAbstractWord;
import model.ModelWord;
import model.ModelWordLesson;
import sqlite.SqliteDictionary;
import sqlite.SqliteVocabulary;
import vocabulary.adapter.PinnedSectionAdapter;
import vocabulary.fragment.TagVocabularyFragment;
import vocabulary.fragment.VocabularyFragment;
import vocabulary.fragment.WordDetailMainFragment;

/**
 * Created by giang on 6/25/17.
 */

public class VocabularyActivity extends AppCompatActivity implements PinnedSectionAdapter.OnItemClickListener{

    public static final String TAG_FRAGMENT = "tag fragment";
    public static final String SECTION_FRAGMENT = "section fragment";
    public static final String WORD = "word";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        Fragment savedFragment = getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (savedFragment == null) {

            VocabularyFragment fragment = new VocabularyFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.contentLayout, fragment).commit();
        }
    }


    public void openTagFragment(int tagId) {

        TagVocabularyFragment tagVocabularyFragment = TagVocabularyFragment.newInstance(tagId);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentLayout, tagVocabularyFragment)
                .addToBackStack(TAG_FRAGMENT)
                .commit();

    }

    @Override
    public void onItemClick(int lessonId, int wordId) {

        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        ModelWordLesson[] wordLessons = sqliteVocabulary.searchWordLesson(lessonId);

        WordDetailMainFragment fragment = WordDetailMainFragment.newInstance(wordLessons, wordId);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.contentLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void onOpenWordDetailFragment(int lessonId, int wordId){
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        ModelWordLesson[] wordLessons = sqliteVocabulary.searchWordLesson(lessonId);

        WordDetailMainFragment fragment = WordDetailMainFragment.newInstance(wordLessons, wordId);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.contentLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void onOpenLearningActivity(int lessonId) {
        Intent intent = new Intent(this, PracticeActivity.class);
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        ModelWordLesson[] wordLessons = sqliteVocabulary.searchWordLesson(lessonId);

        ModelWord[] words = new ModelWord[wordLessons.length];
        int i = 0;
        for (ModelWordLesson wordLesson :
                wordLessons) {
            words[i] = wordLesson.getmWord();
            i++;
        }
        Bundle bundle =  new Bundle();
        bundle.putSerializable(LearningActivity.WORDS, words);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
