package model;

/**
 * Created by dainguyen on 5/22/17.
 */

public class Convert {
    public static String convertCStringToJniSafeString(byte[] input) {
        try {
            String nativeString = new String(input, "UTF-8"); // please debate what the safest charset should be?
            return nativeString;
        } catch (Exception e) {
            // TODO Simplistic Error handling, tune to your needs.
            e.printStackTrace();
            return "fail"; //JSTRING_CONVERT_FAIL
        }
    }
}
