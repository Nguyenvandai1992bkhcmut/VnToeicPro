//
// Created by dai nguyen on 5/30/17.
//

#include <stdio.h>
#include "jni.h"
#include "model.cpp"
#include "SqlitePart5.h"
#include <iostream>
#include <string>
#include <vector>
using namespace std;

extern "C"{
JNIEXPORT jobject JNICALL
funConvert(JNIEnv* env , jobject object , Part5 * part ){
    if(part==NULL)return NULL;

    jclass  cl = env -> FindClass("model/ModelPart5");
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V");

    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");


    jbyteArray arrayquestion = env->NewByteArray(strlen(part->getQuestion()));
    env->SetByteArrayRegion(arrayquestion,0,strlen(part->getQuestion()),(jbyte*)part->getQuestion());
    jstring question = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayquestion);
     env->DeleteLocalRef(arrayquestion);

    jbyteArray arrayA = env->NewByteArray(strlen(part->getA()));
     env->SetByteArrayRegion(arrayA,0,strlen(part->getA()),(jbyte*)part->getA());
     jstring a = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayA);
     env->DeleteLocalRef(arrayA);

     jbyteArray arrayB = env->NewByteArray(strlen(part->getB()));
    env->SetByteArrayRegion(arrayB,0,strlen(part->getB()),(jbyte*)part->getB());
    jstring b = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayB);
         env->DeleteLocalRef(arrayB);

    jbyteArray arrayC = env->NewByteArray(strlen(part->getC()));
    env->SetByteArrayRegion(arrayC,0,strlen(part->getC()),(jbyte*)part->getC());
    jstring c = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayC);
         env->DeleteLocalRef(arrayC);

    jbyteArray arrayD = env->NewByteArray(strlen(part->getD()));
    env->SetByteArrayRegion(arrayD,0,strlen(part->getD()),(jbyte*)part->getD());
    jstring d = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayD);
    env->DeleteLocalRef(arrayD);


    jbyteArray arraysol = env->NewByteArray(strlen(part->getSol()));
    env->SetByteArrayRegion(arraysol,0,strlen(part->getSol()),(jbyte*)part->getSol());
    jstring sol = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraysol);
    env->DeleteLocalRef(arraysol);

    jbyteArray arrayexplan = env->NewByteArray(strlen(part->getExplan()));
    env->SetByteArrayRegion(arrayexplan,0,strlen(part->getExplan()),(jbyte*)part->getExplan());
    jstring explan = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayexplan);
    env->DeleteLocalRef(arrayexplan);

    jobject ob = env->NewObject(cl, methodId,part->getId(),question,a,b,c,d,sol,explan,part->getLevel(),part->getTime());
             env->DeleteLocalRef(question);
             env->DeleteLocalRef(a);
            env->DeleteLocalRef(b);
             env->DeleteLocalRef(c);
            env->DeleteLocalRef(d);
            env->DeleteLocalRef(sol);
            env->DeleteLocalRef(explan);


    return ob;
}
}
extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqlitePart5_randomPart5(JNIEnv * env , jobject object ,jint number1){

    SqlitePart5 sqlite;
    int number =(int)number1;
    vector<Part5*>result =sqlite.randomPart5(number);
     jclass  cl = env -> FindClass("model/ModelPart5");
     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
     for(int i=0;i<result.size();i++){
        jobject ob = funConvert(env,object,result.at(i));
        env->SetObjectArrayElement(arr, i, ob);
     }
    return arr;

}
}

extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqlitePart5_randomPart5Subject(JNIEnv * env , jobject object ,jint subject1,jint number1){

    SqlitePart5 sqlite;
    int number =(int)number1;
    int subject= (int)subject1;
    vector<Part5*>result =sqlite.randomPart5Subject(subject,number);
     jclass  cl = env -> FindClass("model/ModelPart5");

     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
     for(int i=0;i<result.size();i++){
        jobject ob = funConvert(env,object,result.at(i));
        env->SetObjectArrayElement(arr, i, ob);
     }
    return arr;

}
}


extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqlitePart5_searchPart5Favorite(JNIEnv * env , jobject object){

    SqlitePart5 sqlite;

    vector<Part5*>result =sqlite.searchPart5Favorite();
     jclass  cl = env -> FindClass("model/ModelPart5");

     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
     for(int i=0;i<result.size();i++){
        jobject ob = funConvert(env,object,result.at(i));
        env->SetObjectArrayElement(arr, i, ob);
     }
    return arr;

}
}
