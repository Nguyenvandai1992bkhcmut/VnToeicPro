package vocabulary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.Arrays;

import gamevocabulary.Activity.LearningActivity;
import gamevocabulary.Activity.PracticeActivity;
import listenvocabularry.ListenActivity;
import model.ModelWord;
import model.ModelWordLesson;
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
    private static final String VOCABULARY_FRAGMENT = "vocabulary fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        Fragment savedFragment = getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (savedFragment == null) {

            VocabularyFragment fragment = new VocabularyFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.contentLayout, fragment, VOCABULARY_FRAGMENT)
            .addToBackStack(null)
            .commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (currentFragment != null && ((TagVocabularyFragment)currentFragment).mViewPager.getCurrentItem() == 1 && keyCode == KeyEvent.KEYCODE_BACK) {
            ((TagVocabularyFragment)currentFragment).mViewPager.setCurrentItem(0, true);
            return true;
        } else return super.onKeyDown(keyCode, event);
    }

    public void openTagFragment(int tagId) {

        TagVocabularyFragment tagVocabularyFragment = TagVocabularyFragment.newInstance(tagId);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentLayout, tagVocabularyFragment, TAG_FRAGMENT)
                .addToBackStack(null)
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

    public void onOpenLearningActivity(ArrayList<Integer> listLessonId) {
        Intent intent = new Intent(this, LearningActivity.class);
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        ArrayList<ModelWordLesson> modelWordLessonArrayList = new ArrayList<>();
        for (int lessonId: listLessonId) {
            ArrayList<ModelWordLesson> wordLessons = new ArrayList<>(Arrays.asList(sqliteVocabulary.searchWordLesson(lessonId)));
            modelWordLessonArrayList.addAll(wordLessons);
        }


        ModelWord[] words = new ModelWord[modelWordLessonArrayList.size()];
        int i = 0;
        for (ModelWordLesson wordLesson :
                modelWordLessonArrayList) {
            words[i] = wordLesson.getmWord();
            i++;
        }
        Bundle bundle =  new Bundle();
        bundle.putSerializable(LearningActivity.WORDS, words);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onOpenPractingActivity(ArrayList<Integer> listLessonId) {
        Intent intent = new Intent(this, PracticeActivity.class);
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        ArrayList<ModelWordLesson> modelWordLessonArrayList = new ArrayList<>();
        for (int lessonId: listLessonId) {
            ArrayList<ModelWordLesson> wordLessons = new ArrayList<>(Arrays.asList(sqliteVocabulary.searchWordLesson(lessonId)));
            modelWordLessonArrayList.addAll(wordLessons);
        }


        ModelWord[] words = new ModelWord[modelWordLessonArrayList.size()];
        int i = 0;
        for (ModelWordLesson wordLesson :
                modelWordLessonArrayList) {
            words[i] = wordLesson.getmWord();
            i++;
        }
        Bundle bundle =  new Bundle();
        bundle.putSerializable(LearningActivity.WORDS, words);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onOpenListenActivity(ArrayList<Integer> listLessonId) {
        Intent intent = new Intent(this, ListenActivity.class);
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        ArrayList<ModelWordLesson> modelWordLessonArrayList = new ArrayList<>();
        for (int lessonId: listLessonId) {
            ArrayList<ModelWordLesson> wordLessons = new ArrayList<>(Arrays.asList(sqliteVocabulary.searchWordLesson(lessonId)));
            modelWordLessonArrayList.addAll(wordLessons);
        }


        ModelWord[] words = new ModelWord[modelWordLessonArrayList.size()];
        int i = 0;
        for (ModelWordLesson wordLesson :
                modelWordLessonArrayList) {
            words[i] = wordLesson.getmWord();
            i++;
        }
        Bundle bundle =  new Bundle();
        bundle.putSerializable(ListenActivity.LIST_WORDS, words);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
