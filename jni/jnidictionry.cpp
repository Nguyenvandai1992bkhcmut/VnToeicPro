//
//  jnidictionry.cpp
//  vntoeic
//
//  Created by dai nguyen on 5/15/17.
//  Copyright © 2017 dai nguyen. All rights reserved.
//
#include <stdio.h>
#include "jni.h"
#include "model.cpp"
#include "SqliteDictionary.hpp"
#include <iostream>
#include <string>
#include <vector>

using namespace std;


// select all dictionary which similar name_
extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqliteDictionary_searchSimilar(JNIEnv * env , jobject object , jstring name_){

     const char *name = env->GetStringUTFChars(name_, 0);

        // TODO

     env->ReleaseStringUTFChars(name_, name);

    SqliteDictionary sqlite;
    vector<Dictionary>result =sqlite.searchAll(name);

        jclass  cl = env -> FindClass("model/Dictionary");
        jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;)V");

        jclass clcv = env -> FindClass("model/Convert");
        jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");


        jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
        for (int i =0;i<result.size();i++){

            jbyteArray array = env->NewByteArray(strlen(result[i].getName()));
              env->SetByteArrayRegion(array,0,strlen(result[i].getName()),(jbyte*)result[i].getName());
            jstring str = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);


             jbyteArray array1 = env->NewByteArray(strlen(result[i].getContent()));
             env->SetByteArrayRegion(array1,0,strlen(result[i].getContent()),(jbyte*)result[i].getContent());
             jstring str1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array1);

            jobject ob = env->NewObject(cl, methodId,result[i].getId(),str,str1);
            env->SetObjectArrayElement(arr, i, ob);
        }
        return arr;
}
}
// select dictionary with id
extern "C"{
JNIEXPORT jobject JNICALL
Java_sqlite_SqliteDictionary_searchId(JNIEnv * env , jobject object , jint id_){
        int id = (int)id_;
        SqliteDictionary sqlite;
        Dictionary *diction = sqlite.searchId(id);
         jclass  cl = env -> FindClass("model/Dictionary");
         jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;)V");

           jclass clcv = env -> FindClass("model/Convert");
           jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");

        if(diction!=NULL){
           jbyteArray array = env->NewByteArray(strlen(diction->getName()));
           env->SetByteArrayRegion(array,0,strlen(diction->getName()),(jbyte*)diction->getName());
          jstring str = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);


          jbyteArray array1 = env->NewByteArray(strlen(diction->getContent()));
           env->SetByteArrayRegion(array1,0,strlen(diction->getContent()),(jbyte*)diction->getContent());
           jstring str1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array1);
           jobject ob = env->NewObject(cl, methodId,diction->getId(),str,str1);
           return ob;
           }
          else return NULL;
}
}

//
extern "C"{
JNIEXPORT jobject JNICALL
Java_sqlite_SqliteDictionary_searchHistory(JNIEnv * env , jobject object ){

        SqliteDictionary sqlite;
        vector<Dictionary>result = sqlite.searchHistory();
         jclass  cl = env -> FindClass("model/Dictionary");
         jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;)V");

           jclass clcv = env -> FindClass("model/Convert");
           jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");

            jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
        for(int i=0;i<result.size();i++){
                    Dictionary diction = result.at(i);
                    jbyteArray array = env->NewByteArray(strlen(diction.getName()));
                 env->SetByteArrayRegion(array,0,strlen(diction.getName()),(jbyte*)diction.getName());
                  jstring str = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);
                    env->DeleteLocalRef(array);

                  jbyteArray array1 = env->NewByteArray(strlen(diction.getContent()));
                   env->SetByteArrayRegion(array1,0,strlen(diction.getContent()),(jbyte*)diction.getContent());
                   jstring str1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array1);
                    env->DeleteLocalRef(array1);

                   jobject ob = env->NewObject(cl, methodId,diction.getId(),str,str1);
                   env->SetObjectArrayElement(arr, i, ob);

                   env->DeleteLocalRef(str);
                   env->DeleteLocalRef(str1);
                   env->DeleteLocalRef(ob);
        }

         return arr;
}
}

// select all favorite
extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqliteDictionary_searchFavorite(JNIEnv * env , jobject object ){


    SqliteDictionary sqlite;
    vector<DictionaryFavorite>result =sqlite.searchFavorite();

        jclass  cl = env -> FindClass("model/DictionaryFavorite");
        jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;)V");

        jclass clcv = env -> FindClass("model/Convert");
        jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");


        jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
        for (int i =0;i<result.size();i++){

            jbyteArray array = env->NewByteArray(strlen(result[i].getTime()));
              env->SetByteArrayRegion(array,0,strlen(result[i].getTime()),(jbyte*)result[i].getTime());
            jstring str = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);


             jbyteArray array1 = env->NewByteArray(strlen(result[i].getMeaning()));
             env->SetByteArrayRegion(array1,0,strlen(result[i].getMeaning()),(jbyte*)result[i].getMeaning());
             jstring str1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array1);

            jobject ob = env->NewObject(cl, methodId,result[i].getId(),str,str1);
            env->SetObjectArrayElement(arr, i, ob);
        }
        return arr;
}
}

// insert favovite
extern "C"{
JNIEXPORT void JNICALL
Java_sqlite_SqliteDictionary_insertFavorite(JNIEnv * env, jobject object , jobject data){
        jclass cl =  env-> FindClass("model/DictionaryFavorite");

        jmethodID getid = env->GetMethodID(cl,"getId","()I");
        jmethodID gettime = env->GetMethodID(cl,"getTime","()Ljava/lang/String;");
        jmethodID getmeaning = env->GetMethodID(cl,"getMeaning","()Ljava/lang/String;");

        jstring time_ = (jstring)env->CallObjectMethod(data , gettime ,0);

        const char *time = env->GetStringUTFChars(time_, 0);

        jstring meaning_  =(jstring)env->CallObjectMethod(data , getmeaning ,0);

        const char *meaning = env->GetStringUTFChars(meaning_, 0);

        jint id_ = (jint)env->CallIntMethod(data,getid);

        int id =(int)id_;

        DictionaryFavorite dicfavorite(id,time, meaning);
        SqliteDictionary sqlite;
        sqlite.insertFavorite(dicfavorite);

}

}



extern "C"{
JNIEXPORT void JNICALL
Java_sqlite_SqliteDictionary_updateHistory(JNIEnv * env, jobject object , jint id, jint his){

          SqliteDictionary sqlite;
           sqlite.updateHistory((int)id,(int)his);

}

}

extern "C"{
JNIEXPORT jboolean JNICALL
Java_sqlite_SqliteDictionary_checkFavorite(JNIEnv * env, jobject object , jint id){

          SqliteDictionary sqlite;
          bool b =sqlite.checkFavorite((int)id);
          return (jboolean)b;

}

}


extern "C"{
JNIEXPORT jobject  JNICALL
Java_sqlite_SqliteDictionary_searchFavoriteDictionary(JNIEnv * env, jobject object , jint id1){

          SqliteDictionary sqlite;
          int id =(int)id1;
          DictionaryFavorite *dic  =sqlite.searchFavoriteDictionary(id);
          if(dic==NULL)return NULL;

            jclass  cl = env -> FindClass("model/DictionaryFavorite");
                jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;)V");

                jclass clcv = env -> FindClass("model/Convert");
                jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString",  "([B)Ljava/lang/String;");



                    jbyteArray array = env->NewByteArray(strlen(dic->getTime()));
                      env->SetByteArrayRegion(array,0,strlen(dic->getTime()),(jbyte*)dic->getTime());
                    jstring str = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array);


                     jbyteArray array1 = env->NewByteArray(strlen(dic->getMeaning()));
                     env->SetByteArrayRegion(array1,0,strlen(dic->getMeaning()),(jbyte*)dic->getMeaning());
                     jstring str1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, array1);

                    jobject ob = env->NewObject(cl, methodId,dic->getId(),str,str1);


                    return ob;

}

}