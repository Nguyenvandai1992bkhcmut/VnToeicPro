//
// Created by dai nguyen on 5/26/17.
//

#include <stdio.h>
#include "jni.h"
#include "model.cpp"
#include "SqliteWord.hpp"
#include <iostream>
#include <string>
#include <vector>

using namespace std;


// select all section
extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqliteVocabulary_searchAllSection(JNIEnv * env , jobject object ){



    SqliteWord sqlite;
    vector<Section>result =sqlite.searchAllSection();

        jclass  cl = env -> FindClass("model/ModelSection");
        jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;)V");

        jclass clcv = env -> FindClass("model/Convert");
        jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");


        jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
        for (int i =0;i<result.size();i++){

            jbyteArray array = env->NewByteArray(strlen(result[i].getTitle()));
             env->SetByteArrayRegion(array,0,strlen(result[i].getTitle()),(jbyte*)result[i].getTitle());
            jstring str = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);


            jobject ob = env->NewObject(cl, methodId,result[i].getId(),str);
            env->SetObjectArrayElement(arr, i, ob);
        }
        return arr;
}
}


//select all tag in 1 id section
extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqliteVocabulary_searchTaginSection(JNIEnv * env , jobject object , jint id){
        SqliteWord sqlite;
        int id1 = (int)id;
        vector<WordTag>result =sqlite.searchTaginSection(id1);

        jclass  cl = env -> FindClass("model/ModelTag");
        jmethodID methodId = env -> GetMethodID(cl,"<init>", "(IILjava/lang/String;Ljava/lang/String;)V");

        jclass clcv = env -> FindClass("model/Convert");
        jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");


        jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
        for (int i =0;i<result.size();i++){

            jbyteArray array = env->NewByteArray(strlen(result[i].getTitle()));
             env->SetByteArrayRegion(array,0,strlen(result[i].getTitle()),(jbyte*)result[i].getTitle());
            jstring title = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);

            jbyteArray array1 = env->NewByteArray(strlen(result[i].getToken()));
            env->SetByteArrayRegion(array1,0,strlen(result[i].getToken()),(jbyte*)result[i].getToken());
             jstring token = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);

            jobject ob = env->NewObject(cl, methodId,result[i].getIdTag(), result[i].getIdSection(),title, token);
            env->SetObjectArrayElement(arr, i, ob);
        }
        return arr;
}
}

//select all lessontag in 1 id wordTag
extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqliteVocabulary_searchLessonTag(JNIEnv * env , jobject object , jint id){
        SqliteWord sqlite;
        int id1 = (int)id;
        vector<LessonTag>result =sqlite.searchLessonTag(id1);

        jclass  cl = env -> FindClass("model/ModelLesson");
        jmethodID methodId = env -> GetMethodID(cl,"<init>", "(IILjava/lang/String;)V");

        jclass clcv = env -> FindClass("model/Convert");
        jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");


        jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
        for (int i =0;i<result.size();i++){

            jbyteArray array = env->NewByteArray(strlen(result[i].getTitle()));
             env->SetByteArrayRegion(array,0,strlen(result[i].getTitle()),(jbyte*)result[i].getTitle());
            jstring title = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);


            jobject ob = env->NewObject(cl, methodId,result[i].getLessonTagId(), result[i].getWTagId(),title);
            env->SetObjectArrayElement(arr, i, ob);
        }
        return arr;
}
}


//select Word with id
extern "C"{
JNIEXPORT jobject JNICALL
Java_sqlite_SqliteVocabulary_searchWordId(JNIEnv * env , jobject object , jint id){
        SqliteWord sqlite;
        int id1 = (int)id;
        Word *word =sqlite.searchWordId(id1);
        if(word==NULL)return NULL;
        jclass javastring = env->FindClass("java/lang/String");
        jclass  cl = env -> FindClass("model/ModelWord");
        jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V");

        jclass clcv = env -> FindClass("model/Convert");
        jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");


        jobjectArray arrmeaning = env->NewObjectArray((int)(word->getMeaning().size()), javastring, NULL);
        jobjectArray arrtype = env->NewObjectArray((int)(word->getType().size()), javastring, NULL);
        jobjectArray arrexplan = env->NewObjectArray((int)(word->getExplan().size()), javastring, NULL);
        jobjectArray arrsimilar = env->NewObjectArray((int)(word->getSimilar().size()), javastring, NULL);

        for(int i=0;i<(int)((word->getMeaning()).size());i++){
                      const char * m = (word->getMeaning()).at(i);
                     jbyteArray array = env->NewByteArray(strlen(m));
                      env->SetByteArrayRegion(array,0,strlen(m),(jbyte*)m);
                     jstring meaning1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);

                      jbyteArray array1 = env->NewByteArray(strlen(word->getType().at(i)));
                      env->SetByteArrayRegion(array1,0,strlen(word->getType().at(i)),(jbyte*)word->getType().at(i));
                      jstring type1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array1);



                     jbyteArray array2 = env->NewByteArray(strlen(word->getExplan().at(i)));
                      env->SetByteArrayRegion(array2,0,strlen(word->getExplan().at(i)),(jbyte*)word->getExplan().at(i));
                      jstring explan1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array2);




                    jbyteArray array3 = env->NewByteArray(strlen(word->getSimilar().at(i)));
                    env->SetByteArrayRegion(array3,0,strlen(word->getSimilar().at(i)),(jbyte*)word->getSimilar().at(i));
                    jstring similar1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array3);



                     env->SetObjectArrayElement(arrmeaning, i, meaning1);
                     env->SetObjectArrayElement(arrtype, i, type1);
                     env->SetObjectArrayElement(arrexplan, i, explan1);
                     env->SetObjectArrayElement(arrsimilar, i, similar1);

           }

            jbyteArray array3 = env->NewByteArray(strlen(word->getWord()));
            env->SetByteArrayRegion(array3,0,strlen(word->getWord()),(jbyte*)word->getWord());
             jstring wordname= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array3);
                       env->DeleteLocalRef(array3);

               jbyteArray array4 = env->NewByteArray(strlen(word->getPronounce()));
               env->SetByteArrayRegion(array4,0,strlen(word->getPronounce()),(jbyte*)word->getPronounce());
                jstring pro= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array4);
                          env->DeleteLocalRef(array4);

             jbyteArray array5 = env->NewByteArray(strlen(word->getExample()));
              env->SetByteArrayRegion(array5,0,strlen(word->getExample()),(jbyte*)word->getExample());
             jstring exam= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array5);
                       env->DeleteLocalRef(array5);

            jbyteArray array6 = env->NewByteArray(strlen(word->getToken()));
            env->SetByteArrayRegion(array6,0,strlen(word->getToken()),(jbyte*)word->getToken());
             jstring token= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array6);
                       env->DeleteLocalRef(array6);

            jobject ob = env->NewObject(cl, methodId,word->getId(),token,wordname,pro,exam,arrmeaning,arrtype,arrexplan,arrsimilar);

          env->DeleteLocalRef(token);
                    env->DeleteLocalRef(exam);
                              env->DeleteLocalRef(pro);
                                        env->DeleteLocalRef(wordname);
        return ob;
}
}


extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqliteVocabulary_searchWordLesson(JNIEnv * env , jobject object , jint id){
        SqliteWord sqlite;
        int id1 = (int)id;
        vector<WordLesson>result =sqlite.searchWordLesson(id1);

        jclass  clwordles = env -> FindClass("model/ModelWordLesson");
        jmethodID methodId_wordles = env -> GetMethodID(clwordles,"<init>", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V");

        jclass  cl = env -> FindClass("model/ModelWord");
        jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V");

        jclass javastring = env->FindClass("java/lang/String");

        jclass clcv = env -> FindClass("model/Convert");
        jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");


        jobjectArray arr = env->NewObjectArray((int)result.size(), clwordles, NULL);
        for (int ii =0;ii<result.size();ii++){
               Word * word = result.at(ii).getWord();
             jobjectArray arrmeaning = env->NewObjectArray((int)(word->getMeaning().size()), javastring, NULL);
             jobjectArray arrtype = env->NewObjectArray((int)(word->getType().size()), javastring, NULL);
              jobjectArray arrexplan = env->NewObjectArray((int)(word->getExplan().size()), javastring, NULL);
              jobjectArray arrsimilar = env->NewObjectArray((int)(word->getSimilar().size()), javastring, NULL);

               for(int i=0;i<(int)((word->getMeaning()).size());i++){
                        const char * m = (word->getMeaning()).at(i);
                        jbyteArray array = env->NewByteArray(strlen(m));
                         env->SetByteArrayRegion(array,0,strlen(m),(jbyte*)m);
                         jstring meaning1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);
                         env->DeleteLocalRef(array);

                                  jbyteArray array1 = env->NewByteArray(strlen(word->getType().at(i)));
                                  env->SetByteArrayRegion(array1,0,strlen(word->getType().at(i)),(jbyte*)word->getType().at(i));
                                  jstring type1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array1);
                                     env->DeleteLocalRef(array1);

                                 jbyteArray array2 = env->NewByteArray(strlen(word->getExplan().at(i)));
                                  env->SetByteArrayRegion(array2,0,strlen(word->getExplan().at(i)),(jbyte*)word->getExplan().at(i));
                                  jstring explan1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array2);
                                   env->DeleteLocalRef(array2);

                                jbyteArray array3 = env->NewByteArray(strlen(word->getSimilar().at(i)));
                                env->SetByteArrayRegion(array3,0,strlen(word->getSimilar().at(i)),(jbyte*)word->getSimilar().at(i));
                                jstring similar1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array3);
                                 env->DeleteLocalRef(array3);


                                 env->SetObjectArrayElement(arrmeaning, i, meaning1);
                                 env->SetObjectArrayElement(arrtype, i, type1);
                                 env->SetObjectArrayElement(arrexplan, i, explan1);
                                 env->SetObjectArrayElement(arrsimilar, i, similar1);

                                 env->DeleteLocalRef(meaning1);
                                  env->DeleteLocalRef(type1);
                                   env->DeleteLocalRef(explan1);
                                   env->DeleteLocalRef(similar1);

               }

                jbyteArray array3 = env->NewByteArray(strlen(word->getWord()));
                 env->SetByteArrayRegion(array3,0,strlen(word->getWord()),(jbyte*)word->getWord());
                 jstring wordname= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array3);
                 env->DeleteLocalRef(array3);

                 jbyteArray array4 = env->NewByteArray(strlen(word->getPronounce()));
                 env->SetByteArrayRegion(array4,0,strlen(word->getPronounce()),(jbyte*)word->getPronounce());
                 jstring pro= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array4);
                 env->DeleteLocalRef(array4);

                 jbyteArray array5 = env->NewByteArray(strlen(word->getExample()));
                  env->SetByteArrayRegion(array5,0,strlen(word->getExample()),(jbyte*)word->getExample());
                  jstring exam= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array5);
                   env->DeleteLocalRef(array5);

                   jbyteArray array6 = env->NewByteArray(strlen(word->getToken()));
                               env->SetByteArrayRegion(array6,0,strlen(word->getToken()),(jbyte*)word->getToken());
                                jstring token= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array6);
                                                   env->DeleteLocalRef(array6);



            jobject re = env->NewObject(clwordles,methodId_wordles, result.at(ii).getLessonId(),word->getId(),token,wordname,pro,exam,arrmeaning,arrtype,arrexplan,arrsimilar);
            env->SetObjectArrayElement(arr, ii, re);

            env->DeleteLocalRef(wordname);
            env->DeleteLocalRef(pro);
            env->DeleteLocalRef(exam);
            env->DeleteLocalRef(arrmeaning);
            env->DeleteLocalRef(arrtype);
            env->DeleteLocalRef(arrexplan);
            env->DeleteLocalRef(arrsimilar);
            env->DeleteLocalRef(re);
        }
        return arr;
}
}


// search All FavoriteWord
extern "C"{
    JNIEXPORT jobjectArray JNICALL
    Java_sqlite_SqliteVocabulary_searchFavoriteWord(JNIEnv * env , jobject object ){
         SqliteWord sqlite;

          vector<ModelFavoriteWord>result =sqlite.searchFavoriteWord();
          jclass clcv = env -> FindClass("model/Convert");
          jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");

          jclass  cl = env -> FindClass("model/ModelFavoriteWord");
          jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;)V");

          jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
          for(int i =0;i<result.size();i++){
                  ModelFavoriteWord word = result.at(i);
                 jbyteArray array = env->NewByteArray(strlen(word.getDate()));
                 env->SetByteArrayRegion(array,0,strlen(word.getDate()),(jbyte*)word.getDate());
                  jstring date= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);
                  env->DeleteLocalRef(array);

                  jobject ob = env->NewObject(cl,methodId,word.getId(), date);
                  env->SetObjectArrayElement(arr, i, ob);
                  env->DeleteLocalRef(date);
                  env->DeleteLocalRef(ob);
          }
          return arr;
    }

}


// insert favoviteWord
extern "C"{
JNIEXPORT void JNICALL
Java_sqlite_SqliteVocabulary_insertFavoriteWord(JNIEnv * env, jobject object , jobject data){
        jclass cl =  env-> FindClass("model/ModelFavoriteWord");

        jmethodID getid = env->GetMethodID(cl,"getmWordId","()I");
        jmethodID gettime = env->GetMethodID(cl,"getmDate","()Ljava/lang/String;");

        jstring time_ = (jstring)env->CallObjectMethod(data , gettime ,0);
        jint id_ = (jint)env->CallIntMethod(data,getid);
        const char *time = env->GetStringUTFChars(time_, 0);

        int id =(int)id_;

        ModelFavoriteWord favorite(id,time);
        SqliteWord sqlite;
        sqlite.insertFavoriteWord(favorite);

}

}
extern "C"{
     JNIEXPORT jobjectArray JNICALL
     funsearchWordChecked(JNIEnv * env , jobject object , vector<ModelWordChecked>result ){
            jclass clcv = env -> FindClass("model/Convert");
            jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");

             jclass  cl = env -> FindClass("model/ModelWordChecked");
             jmethodID methodId = env -> GetMethodID(cl,"<init>", "(IIILjava/lang/String;)V");

              jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
               for(int i =0;i<result.size();i++){
                       ModelWordChecked word = result.at(i);
                       jbyteArray array = env->NewByteArray(strlen(word.getTime()));
                       env->SetByteArrayRegion(array,0,strlen(word.getTime()),(jbyte*)word.getTime());
                       jstring date= (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);
                        env->DeleteLocalRef(array);

                          jobject ob = env->NewObject(cl,methodId,word.getIdWord(),word.getIdLesson(),word.getIdResult(), date);
                          env->SetObjectArrayElement(arr, i, ob);
                          env->DeleteLocalRef(date);
                          env->DeleteLocalRef(ob);
               }
               return arr;
     }
}

// search All FavoriteWord
extern "C"{
    JNIEXPORT jobjectArray JNICALL
    Java_sqlite_SqliteVocabulary_searchWordChecked(JNIEnv * env , jobject object ){
         SqliteWord sqlite;

          vector<ModelWordChecked>result =sqlite.searchWordChecked(0,0);
          return funsearchWordChecked(env, object,result);
    }

}

// search All FavoriteWord with id word
extern "C"{
    JNIEXPORT jobjectArray JNICALL
    Java_sqlite_SqliteVocabulary_searchWordCheckedId(JNIEnv * env , jobject object , jint id_){
         SqliteWord sqlite;
           int id=id_;

          vector<ModelWordChecked>result =sqlite.searchWordChecked(1,id);
          return funsearchWordChecked(env, object,result);
    }

}

extern "C"{
JNIEXPORT void JNICALL
Java_sqlite_SqliteVocabulary_insertWordChecked(JNIEnv * env, jobject object , jobject data){
        jclass cl =  env-> FindClass("model/ModelWordChecked");

        jmethodID getid = env->GetMethodID(cl,"getmWordId","()I");
        jmethodID getresult = env->GetMethodID(cl,"getmResult","()I");
        jmethodID gettime = env->GetMethodID(cl,"getmTimeChecked","()Ljava/lang/String;");

        jstring time_ = (jstring)env->CallObjectMethod(data , gettime ,0);
        jint id_ = (jint)env->CallIntMethod(data,getid);
        int id =(int)id_;

        jint result_ = (jint)env->CallIntMethod(data,getresult);
         int result =(int)result_;

        const char *time = env->GetStringUTFChars(time_, 0);
        ModelWordChecked checked(id,0,result,time);
        SqliteWord sqlite;
        sqlite.insertWordChecked(checked);

}
}


extern "C"{
JNIEXPORT jboolean JNICALL
Java_sqlite_SqliteVocabulary_checkFavoriteWord(JNIEnv * env, jobject object , jint idword){
        SqliteWord sqlite;
        int id = (int)idword;
        bool check = sqlite.checkFavotiteWord( id);
        return (jboolean)check;
}
}

extern "C"{
JNIEXPORT void JNICALL
Java_sqlite_SqliteVocabulary_deleteWordFavorite(JNIEnv * env, jobject object , jint idword){
        SqliteWord sqlite;
        int id = (int)idword;
        sqlite.deleteWordFavorite( id);
}
}

extern "C"{
JNIEXPORT void JNICALL
Java_sqlite_SqliteVocabulary_deleteWordChecked(JNIEnv * env, jobject object , jint idword){
        SqliteWord sqlite;
        int id = (int)idword;
        sqlite.deleteWordChecked( id);
}
}


extern "C"{
JNIEXPORT jboolean JNICALL
Java_sqlite_SqliteVocabulary_checkWordChecked(JNIEnv * env, jobject object , jint idword){
        SqliteWord sqlite;
        int id = (int)idword;
        bool check = sqlite.checkWordChecked(id);
        return (jboolean)check;
}
}