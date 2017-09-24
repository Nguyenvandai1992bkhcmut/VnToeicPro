//
// Created by dai nguyen on 6/16/17.
//

//
// Created by dai nguyen on 6/15/17.
//

#include <stdio.h>
#include "jni.h"
#include "model.cpp"
#include "SqlitePart6.h"
#include <iostream>
#include <string>
#include <vector>
using namespace std;

extern "C"{
JNIEXPORT jobject JNICALL
funConvert(JNIEnv* env , jobject object , Part6 * part ){
    if(part==NULL)return NULL;

    jclass  cl = env -> FindClass("model/ModelPart6");
<<<<<<< HEAD
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V");
=======
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V");
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");


<<<<<<< HEAD
    jbyteArray arraytoken = env->NewByteArray(strlen(part->getContent()));
    env->SetByteArrayRegion(arraytoken,0,strlen(part->getContent()),(jbyte*)part->getContent());
    jstring content = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytoken);
     env->DeleteLocalRef(arraytoken);



=======
    jbyteArray arraycontent = env->NewByteArray(strlen(part->getContent()));
    env->SetByteArrayRegion(arraycontent,0,strlen(part->getContent()),(jbyte*)part->getContent());
    jstring content = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraycontent);
     env->DeleteLocalRef(arraycontent);


    jbyteArray arraytoken = env->NewByteArray(strlen(part->getToken()));
    env->SetByteArrayRegion(arraytoken,0,strlen(part->getToken()),(jbyte*)part->getToken());
    jstring token = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytoken);
     env->DeleteLocalRef(arraytoken);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
//question 1




 jbyteArray arra1 = env->NewByteArray(strlen(part->getA1()));
    env->SetByteArrayRegion(arra1,0,strlen(part->getA1()),(jbyte*)part->getA1());
    jstring a1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arra1);
    env->DeleteLocalRef(arra1);


 jbyteArray arrb1 = env->NewByteArray(strlen(part->getB1()));
     env->SetByteArrayRegion(arrb1,0,strlen(part->getB1()),(jbyte*)part->getB1());
     jstring b1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrb1);
     env->DeleteLocalRef(arrb1);

 jbyteArray arrc1 = env->NewByteArray(strlen(part->getC1()));
     env->SetByteArrayRegion(arrc1,0,strlen(part->getC1()),(jbyte*)part->getC1());
     jstring c1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrc1);
     env->DeleteLocalRef(arrc1);


 jbyteArray arrd1 = env->NewByteArray(strlen(part->getD1()));
     env->SetByteArrayRegion(arrd1,0,strlen(part->getD1()),(jbyte*)part->getD1());
     jstring d1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrd1);
     env->DeleteLocalRef(arrd1);

 jbyteArray arrsol1 = env->NewByteArray(strlen(part->getSol1()));
     env->SetByteArrayRegion(arrsol1,0,strlen(part->getSol1()),(jbyte*)part->getSol1());
     jstring sol1 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrsol1);
     env->DeleteLocalRef(arrsol1);

     // question2




   jbyteArray arra2 = env->NewByteArray(strlen(part->getA2()));
      env->SetByteArrayRegion(arra2,0,strlen(part->getA2()),(jbyte*)part->getA2());
      jstring a2 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arra2);
      env->DeleteLocalRef(arra2);


   jbyteArray arrb2 = env->NewByteArray(strlen(part->getB2()));
       env->SetByteArrayRegion(arrb2,0,strlen(part->getB2()),(jbyte*)part->getB2());
       jstring b2 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrb2);
       env->DeleteLocalRef(arrb2);

   jbyteArray arrc2 = env->NewByteArray(strlen(part->getC2()));
       env->SetByteArrayRegion(arrc2,0,strlen(part->getC2()),(jbyte*)part->getC2());
       jstring c2 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrc2);
       env->DeleteLocalRef(arrc2);

   jbyteArray arrd2 = env->NewByteArray(strlen(part->getD2()));
       env->SetByteArrayRegion(arrd2,0,strlen(part->getD2()),(jbyte*)part->getD2());
       jstring d2 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrd2);
       env->DeleteLocalRef(arrd2);

   jbyteArray arrsol2 = env->NewByteArray(strlen(part->getSol2()));
       env->SetByteArrayRegion(arrsol2,0,strlen(part->getSol2()),(jbyte*)part->getSol2());
       jstring sol2 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrsol2);
       env->DeleteLocalRef(arrsol2);

       //question3 ;




    jbyteArray arra3 = env->NewByteArray(strlen(part->getA3()));
       env->SetByteArrayRegion(arra3,0,strlen(part->getA3()),(jbyte*)part->getA3());
       jstring a3 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arra3);
       env->DeleteLocalRef(arra3);


    jbyteArray arrb3 = env->NewByteArray(strlen(part->getB3()));
        env->SetByteArrayRegion(arrb3,0,strlen(part->getB3()),(jbyte*)part->getB3());
        jstring b3 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrb3);
        env->DeleteLocalRef(arrb3);

    jbyteArray arrc3 = env->NewByteArray(strlen(part->getC3()));
        env->SetByteArrayRegion(arrc3,0,strlen(part->getC3()),(jbyte*)part->getC3());
        jstring c3 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrc3);
        env->DeleteLocalRef(arrc3);

    jbyteArray arrd3 = env->NewByteArray(strlen(part->getD3()));
        env->SetByteArrayRegion(arrd3,0,strlen(part->getD3()),(jbyte*)part->getD3());
        jstring d3 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrd3);
        env->DeleteLocalRef(arrd3);

    jbyteArray arrsol3 = env->NewByteArray(strlen(part->getSol3()));
        env->SetByteArrayRegion(arrsol3,0,strlen(part->getSol3()),(jbyte*)part->getSol3());
        jstring sol3 = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrsol3);
        env->DeleteLocalRef(arrsol3);

jbyteArray arrexplan = env->NewByteArray(strlen(part->getExplan()));
        env->SetByteArrayRegion(arrexplan,0,strlen(part->getExplan()),(jbyte*)part->getExplan());
        jstring explan = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrexplan);
        env->DeleteLocalRef(arrexplan);

<<<<<<< HEAD
    jobject ob = env->NewObject(cl, methodId,part->getId(),content,a1,b1,c1,d1,sol1,a2,b2,c2,d2,sol2,a3,b3,c3,d3,sol3,explan,part->getLevel(),part->getTime());
=======
    jobject ob = env->NewObject(cl, methodId,part->getId(),token,content,a1,b1,c1,d1,sol1,a2,b2,c2,d2,sol2,a3,b3,c3,d3,sol3,explan,part->getLevel(),part->getTime());
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
    env->DeleteLocalRef(content);
    env->DeleteLocalRef(explan);

        env->DeleteLocalRef(a1);
            env->DeleteLocalRef(b1);
                env->DeleteLocalRef(c1);
                    env->DeleteLocalRef(d1);
                        env->DeleteLocalRef(sol1);


          env->DeleteLocalRef(a2);
              env->DeleteLocalRef(b2);
                  env->DeleteLocalRef(c2);
                      env->DeleteLocalRef(d2);
                          env->DeleteLocalRef(sol2);


          env->DeleteLocalRef(a3);
              env->DeleteLocalRef(b3);
                  env->DeleteLocalRef(c3);
                      env->DeleteLocalRef(d3);
                          env->DeleteLocalRef(sol3);

    return ob;
}
}

extern "C"{
JNIEXPORT jobject JNICALL
Java_sqlite_SqlitePart6_searchPart6Id(JNIEnv * env , jobject object ,jint id_){

    SqlitePart6 sqlite;
    int id =(int)id_;
    Part6*part =sqlite.searchPart6Id(id);
    return funConvert(env, object, part);

}
}

extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqlitePart6_randomPart6(JNIEnv * env , jobject object ,jint number1){

    SqlitePart6 sqlite;
    int number =(int)number1;
    vector<Part6*>result =sqlite.randomPart6(number);
     jclass  cl = env -> FindClass("model/ModelPart6");

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
Java_sqlite_SqlitePart6_randomPart6Subject(JNIEnv * env , jobject object ,jint subject1,jint number1){

    SqlitePart6 sqlite;
    int number =(int)number1;
    int subject= (int)subject1;
    vector<Part6*>result =sqlite.randomPart6Subject(subject,number);
     jclass  cl = env -> FindClass("model/ModelPart6");

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
Java_sqlite_SqlitePart6_searchPart6Favorite(JNIEnv * env , jobject object){

    SqlitePart6 sqlite;

    vector<Part6*>result =sqlite.searchPart6Favorite();
     jclass  cl = env -> FindClass("model/ModelPart6");

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
Java_sqlite_SqlitePart6_searchPart6Check(JNIEnv * env , jobject object){

    SqlitePart6 sqlite;

    vector<Part6*>result =sqlite.searchPart6Check();
     jclass  cl = env -> FindClass("model/ModelPart6");

     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
     for(int i=0;i<result.size();i++){
        jobject ob = funConvert(env,object,result.at(i));
        env->SetObjectArrayElement(arr, i, ob);
     }
    return arr;

}
}

