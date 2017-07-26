//
// Created by dai nguyen on 6/14/17.
//

#include <stdio.h>
#include "jni.h"
#include "model.cpp"
#include "SqlitePart2.h"
#include <iostream>
#include <string>
#include <vector>
using namespace std;


extern "C"{
JNIEXPORT jobject JNICALL
funConvert(JNIEnv* env , jobject object , Part2 * part ){
    if(part==NULL)return NULL;

    jclass  cl = env -> FindClass("model/ModelPart2");
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V");

    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");


    jbyteArray arraytoken = env->NewByteArray(strlen(part->getToken()));
    env->SetByteArrayRegion(arraytoken,0,strlen(part->getToken()),(jbyte*)part->getToken());
    jstring token = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytoken);
     env->DeleteLocalRef(arraytoken);

    jbyteArray arrayA = env->NewByteArray(strlen(part->getScript()));
     env->SetByteArrayRegion(arrayA,0,strlen(part->getScript()),(jbyte*)part->getScript());
     jstring script = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayA);
     env->DeleteLocalRef(arrayA);

     jbyteArray arrayAA = env->NewByteArray(strlen(part->getA()));
          env->SetByteArrayRegion(arrayAA,0,strlen(part->getA()),(jbyte*)part->getA());
          jstring a = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayAA);
          env->DeleteLocalRef(arrayAA);



     jbyteArray arrayB = env->NewByteArray(strlen(part->getB()));
          env->SetByteArrayRegion(arrayB,0,strlen(part->getB()),(jbyte*)part->getB());
          jstring b = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayB);
          env->DeleteLocalRef(arrayB);


     jbyteArray arrayC = env->NewByteArray(strlen(part->getC()));
          env->SetByteArrayRegion(arrayC,0,strlen(part->getC()),(jbyte*)part->getC());
          jstring c = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayC);
          env->DeleteLocalRef(arrayC);

    jbyteArray arraysol = env->NewByteArray(strlen(part->getSol()));
    env->SetByteArrayRegion(arraysol,0,strlen(part->getSol()),(jbyte*)part->getSol());
    jstring sol = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraysol);
    env->DeleteLocalRef(arraysol);


    jobject ob = env->NewObject(cl, methodId,part->getId(),token,script,a,b,c,sol,part->getLevel(),part->getTime());
    env->DeleteLocalRef(token);
    env->DeleteLocalRef(script);
    env->DeleteLocalRef(sol);
       env->DeleteLocalRef(a);
           env->DeleteLocalRef(b);
               env->DeleteLocalRef(c);


    return ob;
}
}

extern "C"{
JNIEXPORT jobject JNICALL
Java_sqlite_SqlitePart2_searchPart2Id(JNIEnv * env , jobject object ,jint id_){

    SqlitePart2 sqlite;
    int id =(int)id_;
    Part2*part =sqlite.searchPart2Id(id);
    return funConvert(env, object, part);

}
}

extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqlitePart2_randomPart2(JNIEnv * env , jobject object ,jint number1){

    SqlitePart2 sqlite;
    int number =(int)number1;
    vector<Part2*>result =sqlite.randomPart2(number);
     jclass  cl = env -> FindClass("model/ModelPart2");

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
Java_sqlite_SqlitePart2_randomPart2Subject(JNIEnv * env , jobject object ,jint subject1,jint number1){

    SqlitePart2 sqlite;
    int number =(int)number1;
    int subject= (int)subject1;
    vector<Part2*>result =sqlite.randomPart2Subject(subject,number);
     jclass  cl = env -> FindClass("model/ModelPart2");

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
Java_sqlite_SqlitePart2_searchPart2Favorite(JNIEnv * env , jobject object){

    SqlitePart2 sqlite;

    vector<Part2*>result =sqlite.searchPart2Favorite();
     jclass  cl = env -> FindClass("model/ModelPart2");

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
Java_sqlite_SqlitePart2_searchPart2Check(JNIEnv * env , jobject object){

    SqlitePart2 sqlite;

    vector<Part2*>result =sqlite.searchPart2Check();
     jclass  cl = env -> FindClass("model/ModelPart2");

     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
     for(int i=0;i<result.size();i++){
        jobject ob = funConvert(env,object,result.at(i));
        env->SetObjectArrayElement(arr, i, ob);
     }
    return arr;

}
}