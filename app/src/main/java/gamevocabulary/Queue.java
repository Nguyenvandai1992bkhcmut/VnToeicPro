package gamevocabulary;

import java.util.Arrays;

/**
 * Created by giang on 7/7/17.
 */

public class Queue {
    public int[] mResults;

    public Queue() {
        mResults = new int[9];
        Arrays.fill(mResults, 0);
    }

    public void add(int x) {
        int[] newArr = new int[9];
        newArr[0] = x;
        System.arraycopy(mResults, 0, newArr, 1, 8);
        mResults = newArr;
    }
}
