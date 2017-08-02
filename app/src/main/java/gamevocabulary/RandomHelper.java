package gamevocabulary;

import java.util.ArrayList;
import java.util.Arrays;

import model.ModelMeaning;
import model.ModelWord;
import model.ModelWordGame;

/**
 * Created by giang on 7/3/17.
 */

public class RandomHelper {

    private static int wordInPack = 4;
    public static int random;

    public static ModelWordGame[] getPack(ModelWordGame[] listWords) {
        Arrays.sort(listWords);
        int i = 0;
        while(listWords[i].mMark >= 6) i++;
        int n = listWords.length - i;
        if (n <= 6) {
            switch (n){
                case 6: {
                    wordInPack = 3;
                    break;
                }
                default: {
                    wordInPack = n;
                    break;
                }
            }

        }
        ModelWordGame[] result = new ModelWordGame[wordInPack];
        System.arraycopy(listWords, i, result, 0, wordInPack);
        return result;
    }

    public static int getTestTimes(ModelWordGame[] pack) {
        int sum = 0;
        for (ModelWordGame wordGame :
                pack) {
            sum += wordGame.mMark;
        }

        return (int) ((7 - (int)Math.ceil(sum / ((double)pack.length))) * ((double)pack.length));
    }

    public static ModelWordGame getWord(ModelWordGame[] listWords) {

        Arrays.sort(listWords);
        int less = listWords[listWords.length - 1].mMark;
        ArrayList<ModelWordGame> sameMark = new ArrayList<>();

        int i = listWords.length - 1;
        while (i >= 0) {
            if (listWords[i].mMark == less){
                sameMark.add(listWords[i]);
            }
            else break;
            i--;
        }

        random = (int) (Math.random() * sameMark.size());
        return sameMark.get(random);
    }

    public static ArrayList<String> createAnswerEngVi(ModelWordGame[] listWord, ModelWordGame key) {
        Arrays.sort(listWord);
        ArrayList<ModelWordGame> newList = new ArrayList<>();
        int i = 0;
        while(i < listWord.length && listWord[i].mMark != 0) {
            if (listWord[i] != key) newList.add(listWord[i]);
            i++;
        }

        if (newList.size() < 3) {
            i = newList.size();
            while (i < listWord.length) {
                if (listWord[i] != key && !checkIfWordGameAlreadyInList(newList, listWord[i])) newList.add(listWord[i]);
                i++;
            }
        }

        ArrayList<String> result=  new ArrayList<>();
        ArrayList<ModelMeaning> listRandom = new ArrayList<>();
        //create true Answer
        ModelWord answer = key.mWord;
        int rand = (int) (Math.random() * answer.getmMeanings().length);
        ModelMeaning answerContent = new ModelMeaning(
                answer.getmId(),
                answer.getmMeanings()[rand],
                answer.getmTypes()[rand],
                answer.getmExplanations()[rand],
                answer.getmSimilars()[rand]
        );
        result.add(answerContent.getMeaning());

        //create false answer
        for (int k = 0; k < newList.size(); k++) {
            ModelWord word = newList.get(k).mWord;
            for (int j = 0; j < word.getmMeanings().length; j++) {
                ModelMeaning meaning = new ModelMeaning(
                        word.getmId(),
                        word.getmMeanings()[j],
                        word.getmTypes()[j],
                        word.getmExplanations()[j],
                        word.getmSimilars()[j]
                );
                listRandom.add(meaning);
            }
        }

        for (int k = 0; k < 3; k++){
            rand = (int) (Math.random() * listRandom.size());
            ModelMeaning meaning = listRandom.remove(rand);
            result.add(meaning.getMeaning());
        }

        return permute(result);
    }

    private static boolean checkIfWordGameAlreadyInList(ArrayList<ModelWordGame> listWord, ModelWordGame another) {
        for (ModelWordGame wordGane :
                listWord) {
            if (wordGane.equals(another)) return true;
        }

        return false;
    }

    public static ArrayList<String> createAnswerViEng(ModelWordGame[] listWord, ModelWordGame key) {
        Arrays.sort(listWord);
        ArrayList<ModelWordGame> newList = new ArrayList<>();
        int i = 0;
        while(i < listWord.length && listWord[i].mMark != 0 ) {
            if (listWord[i] != key) newList.add(listWord[i]);
            i++;
        }

        if (newList.size() < 3) {
            i = newList.size();
            while (i < listWord.length) {
                if (listWord[i] != key && !checkIfWordGameAlreadyInList(newList, listWord[i])) newList.add(listWord[i]);
                i++;
            }
        }

        ArrayList<String> result=  new ArrayList<>();
        //create true Answer
        ModelWord answer = key.mWord;
        result.add(answer.getmWord());

        //create false answer

        int rand;
        for (int k = 0; k < 3; k++){
            rand = (int) (Math.random() * newList.size());
            ModelWordGame wordGame = newList.remove(rand);
            result.add(wordGame.mWord.getmWord());
        }

        return permute(result);
    }


    private static ArrayList<String> permute(ArrayList<String> listAnswer) {
        ArrayList<String> result =  new ArrayList<>();
        int n = listAnswer.size();

        for (int i = 0; i < n; i++) {
            int random = (int) (Math.random() * (listAnswer.size()));
            result.add(listAnswer.get(random));
            listAnswer.remove(random);
        }

        return result;
    }

}
