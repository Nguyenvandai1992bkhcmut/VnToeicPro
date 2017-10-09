//
// Created by dai nguyen on 5/27/17.
//

#include <stdio.h>
#include "jni.h"
#include "model.cpp"
#include "SqlitePart1.h"
#include <iostream>
#include <string>
#include <vector>
using namespace std;

extern "C"{
JNIEXPORT jobject JNICALL
funConvert(JNIEnv* env , jobject object , Part1 * part ){
    if(part==NULL)return NULL;

    jclass  cl = env -> FindClass("model/ModelPart1");
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V");

    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");


    jbyteArray arraytoken = env->NewByteArray(strlen(part->getToken()));
    env->SetByteArrayRegion(arraytoken,0,strlen(part->getToken()),(jbyte*)part->getToken());
    jstring token = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytoken);
     env->DeleteLocalRef(arraytoken);

    jbyteArray arrayA = env->NewByteArray(strlen(part->getA_script()));
     env->SetByteArrayRegion(arrayA,0,strlen(part->getA_script()),(jbyte*)part->getA_script());
     jstring a = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayA);
          env->DeleteLocalRef(arrayA);

     jbyteArray arrayB = env->NewByteArray(strlen(part->getB_script()));
    env->SetByteArrayRegion(arrayB,0,strlen(part->getB_script()),(jbyte*)part->getB_script());
    jstring b = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayB);
         env->DeleteLocalRef(arrayB);

    jbyteArray arrayC = env->NewByteArray(strlen(part->getC_script()));
    env->SetByteArrayRegion(arrayC,0,strlen(part->getC_script()),(jbyte*)part->getC_script());
    jstring c = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayC);
         env->DeleteLocalRef(arrayC);

    jbyteArray arrayD = env->NewByteArray(strlen(part->getD_script()));
    env->SetByteArrayRegion(arrayD,0,strlen(part->getD_script()),(jbyte*)part->getD_script());
    jstring d = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayD);
    env->DeleteLocalRef(arrayD);


    jbyteArray arraysol = env->NewByteArray(strlen(part->getSol()));
    env->SetByteArrayRegion(arraysol,0,strlen(part->getSol()),(jbyte*)part->getSol());
    jstring sol = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraysol);
             env->DeleteLocalRef(arraysol);


    jobject ob = env->NewObject(cl, methodId,part->getId(),token,a,b,c,d,sol,part->getLevel(),part->getTime());
             env->DeleteLocalRef(token);
             env->DeleteLocalRef(a);
            env->DeleteLocalRef(b);
             env->DeleteLocalRef(c);
            env->DeleteLocalRef(d);
            env->DeleteLocalRef(sol);


    return ob;
}
}

extern "C"{
JNIEXPORT jobject JNICALL
Java_sqlite_SqlitePart1_searchPart1Id(JNIEnv * env , jobject object ,jint id_){

    SqlitePart1 sqlite;
    int id =(int)id_;
    Part1*part =sqlite.searchPart1Id(id);
    return funConvert(env, object, part);

}
}

extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqlitePart1_randomPart1(JNIEnv * env , jobject object ,jint number1){

    SqlitePart1 sqlite;
    int number =(int)number1;
    vector<Part1*>result =sqlite.randomPart1(number);
     jclass  cl = env -> FindClass("model/ModelPart1");

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
Java_sqlite_SqlitePart1_randomPart1Subject(JNIEnv * env , jobject object ,jint subject1,jint number1){

    SqlitePart1 sqlite;
    int number =(int)number1;
    int subject= (int)subject1;
    vector<Part1*>result =sqlite.randomPart1Subject(subject,number);
     jclass  cl = env -> FindClass("model/ModelPart1");

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
Java_sqlite_SqlitePart1_searchPart1Favorite(JNIEnv * env , jobject object){

    SqlitePart1 sqlite;

    vector<Part1*>result =sqlite.searchPart1Favorite();
     jclass  cl = env -> FindClass("model/ModelPart1");

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
Java_sqlite_SqlitePart1_searchPart1Check(JNIEnv * env , jobject object){

    SqlitePart1 sqlite;

    vector<Part1*>result =sqlite.searchPart1Check();
     jclass  cl = env -> FindClass("model/ModelPart1");

     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
     for(int i=0;i<result.size();i++){
        jobject ob = funConvert(env,object,result.at(i));
        env->SetObjectArrayElement(arr, i, ob);
     }
    return arr;

}
}

