package GameVocabulary;

import java.util.ArrayList;

import model.ModelMeaning;
import model.ModelWord;

/**
 * Created by giang on 7/5/17.
 */

public class ModelContentHelper {

    public static ArrayList<ModelMeaning> parseToModelMeaning(ModelWord word){
        ArrayList<ModelMeaning> result  = new ArrayList<>();

        for (int i = 0; i < word.getmMeanings().length; i++) {
            ModelMeaning meaning = new ModelMeaning(
                    word.getmId(),
                    word.getmMeanings()[i],
                    word.getmTypes()[i],
                    word.getmExplanations()[i],
                    word.getmSimilars()[i]
            );
            result.add(meaning);
        }

        return result;
    }

    public static ArrayList<ModelMeaning> parseToModelMeaning(ArrayList<ModelWord> words) {
        ArrayList<ModelMeaning> result = new ArrayList<>();
        for (ModelWord word:words) {
            result.addAll(parseToModelMeaning(word));
        }

        return result;
    }
}
