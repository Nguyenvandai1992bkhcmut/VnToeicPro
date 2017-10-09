package model;

import android.support.annotation.NonNull;

import java.io.Serializable;

import gamevocabulary.Queue;

/**
 * Created by giang on 7/7/17.
 */

public class ModelWordGame implements Serializable, Comparable<ModelWordGame> {

    public ModelWord mWord;
    public int mMark;
    public Queue mQueue;
    public boolean isLearned = false;

    public ModelWordGame(ModelWord word) {
        this.mWord = word;
        this.mMark = 0;
        this.mQueue = new Queue();
    }

    public int updateMark() {
        int mark = 0;
        for (int i = 0; i < mQueue.mResults.length; i++) {
            mark += mQueue.mResults[i];
        }

        return mark;
    }

    public void add(int x) {
        this.mQueue.add(x);
        mMark = updateMark();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ModelWordGame && mWord.equals(((ModelWordGame) obj).mWord));
    }

    @Override
    public int compareTo(@NonNull ModelWordGame o) {
        return (this.mMark < o.mMark) ? 1 : ((this.mMark == o.mMark) ? 0 : -1);
    }
}
