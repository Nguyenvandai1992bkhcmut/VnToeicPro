//
// Created by dai nguyen on 5/28/17.
//

#include <stdio.h>
#include "jni.h"
#include "model.cpp"
#include "SqliteControlPart.h"
#include <iostream>
#include <string>
#include <vector>

using namespace std;

extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_ManagerPart_searchAllFavoritePart(JNIEnv* env , jobject object , jint idpart ){


    jclass  cl = env -> FindClass("model/ModelPartFavorite");
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(IILjava/lang/String;)V");

    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");


    SqliteControlPart sqlite;
    int id =(int)idpart;
    vector<ModelFavoritePart>result = sqlite.searchAllFavoritePart(id);

    jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);

    for(int i =0;i<result.size();i++){

         jbyteArray arraytime = env->NewByteArray(strlen(result.at(i).getDate()));
         env->SetByteArrayRegion(arraytime,0,strlen(result.at(i).getDate()),(jbyte*)result.at(i).getDate());
         jstring time = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytime);
         env->DeleteLocalRef(arraytime);
         jobject ob = env->NewObject(cl, methodId,id,result.at(i).getId(),time);
         env->SetObjectArrayElement(arr, i, ob);
         env->DeleteLocalRef(ob);
    }

    return arr;
}
}



extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_ManagerPart_searchAllCheckedPart(JNIEnv* env , jobject object , jint idpart ){


    jclass  cl = env -> FindClass("model/ModelPartCheck");
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(IILjava/lang/String;I)V");

    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");


    SqliteControlPart sqlite;
    int id =(int)idpart;
    vector<ModelCheckPart>result = sqlite.searchAllCheckedPard(id);

    jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);

    for(int i =0;i<result.size();i++){

         jbyteArray arraytime = env->NewByteArray(strlen(result.at(i).getDate()));
         env->SetByteArrayRegion(arraytime,0,strlen(result.at(i).getDate()),(jbyte*)result.at(i).getDate());
         jstring time = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytime);
         env->DeleteLocalRef(arraytime);
         jobject ob = env->NewObject(cl, methodId,id,result.at(i).getId(),time,result.at(i).getResult());
         env->SetObjectArrayElement(arr, i, ob);
         env->DeleteLocalRef(ob);
    }

    return arr;
}
}


extern "C" {
JNIEXPORT jboolean JNICALL
Java_sqlite_ManagerPart_checkPartFavorite(JNIEnv * env , jobject object,jint part1, jint id1){

    SqliteControlPart sqlite;
    int id = (int)id1;
    int part =  (int)part1;
    bool b = sqlite.checkPartFavorite(part,id);
    return (jboolean)b;

}
}


extern "C" {
JNIEXPORT void JNICALL
Java_sqlite_ManagerPart_insertPartFavorite(JNIEnv * env , jobject object, jobject data){

    SqliteControlPart sqlite;

     jclass  cl = env -> FindClass("model/ModelPartFavorite");
          jmethodID gettype = env->GetMethodID(cl,"getType","()I");
     jmethodID getid = env->GetMethodID(cl,"getId","()I");
      jmethodID gettime = env->GetMethodID(cl,"getTime","()Ljava/lang/String;");

       jint id = (jint)env->CallIntMethod(data , getid );
       jint type = (jint)env->CallIntMethod(data , gettype );
      jstring time_ = (jstring)env->CallObjectMethod(data , gettime ,0);
       const char *time = env->GetStringUTFChars(time_, 0);

      ModelFavoritePart * part = new ModelFavoritePart((int)id, time);

      int idpart = (int)type;
      sqlite.insertPartFavorite(idpart, part);

}
}


extern "C" {
JNIEXPORT void JNICALL
Java_sqlite_ManagerPart_insertPartCheck(JNIEnv * env , jobject object, jobject data){

    SqliteControlPart sqlite;

     jclass  cl = env -> FindClass("model/ModelPartCheck");
      jmethodID gettype = env->GetMethodID(cl,"getType","()I");
     jmethodID getid = env->GetMethodID(cl,"getId","()I");
           jmethodID getresult = env->GetMethodID(cl,"getResult","()I");
      jmethodID gettime = env->GetMethodID(cl,"getTime","()Ljava/lang/String;");


       jint id = (jint)env->CallIntMethod(data , getid );
       jint type = (jint)env->CallIntMethod(data , gettype );
       jint result = (jint)(jint)env->CallIntMethod(data , getresult);
      jstring time_ = (jstring)env->CallObjectMethod(data , gettime ,0);
       const char *time = env->GetStringUTFChars(time_, 0);

      ModelCheckPart * part = new ModelCheckPart((int)id, time,(int)result);

      int idpart = (int)type;
      sqlite.insertPartCheck(idpart, part);

}
}


extern "C" {
JNIEXPORT void JNICALL
Java_sqlite_ManagerPart_deletePartFavorite(JNIEnv * env , jobject object, jint part1, jint id1){

    SqliteControlPart sqlite;

        int part = (int)part1;
        int id = (int)id1;
        sqlite.deletePartFavorite(part, id);

}
}



extern "C" {
JNIEXPORT void JNICALL
Java_sqlite_ManagerPart_deletePartCheck(JNIEnv * env , jobject object, jint part1, jint id1){

    SqliteControlPart sqlite;

        int part = (int)part1;
        int id = (int)id1;
        sqlite.deletePartCheck(part, id);

}
}