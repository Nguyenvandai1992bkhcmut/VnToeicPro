//
// Created by dai nguyen on 6/16/17.
//

//
// Created by dai nguyen on 6/15/17.
//

#include <stdio.h>
#include "jni.h"
#include "model.cpp"
#include "SqlitePart7.h"
#include <iostream>
#include <string>
#include <vector>
using namespace std;

extern "C"{
JNIEXPORT jobject JNICALL
funConvert(JNIEnv* env , jobject object , Part7 * part ){
    if(part==NULL)return NULL;

    jclass  cl = env -> FindClass("model/ModelPart7");
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;II)V");
    jmethodID methodAdd = env -> GetMethodID(cl,"addPassage","(IILjava/lang/String;)V");
    jmethodID methodAddQuestion = env -> GetMethodID(cl,"addQuestion","(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");


    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");

   jbyteArray arraytoken = env->NewByteArray(strlen(part->getToken()));
    env->SetByteArrayRegion(arraytoken,0,strlen(part->getToken()),(jbyte*)part->getToken());
    jstring token = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytoken);
     env->DeleteLocalRef(arraytoken);

    jbyteArray arrayexplan = env->NewByteArray(strlen(part->getExplan()));
    env->SetByteArrayRegion(arrayexplan,0,strlen(part->getExplan()),(jbyte*)part->getExplan());
    jstring explan = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrayexplan);
    env->DeleteLocalRef(arrayexplan);

   jobject ob = env->NewObject(cl, methodId,part->getId(),token,explan,part->getLevel(),part->getTime());
    env->DeleteLocalRef(explan);
    env->DeleteLocalRef(token);

    vector<Passage> arrpassage = part->getPassage();
    vector<QuestionPart7>arrquestion = part->getQuestions();

    for(int i =0;i<arrpassage.size();i++){
        Passage passage = arrpassage.at(i);


             jbyteArray arrContent = env->NewByteArray(strlen(passage.getContent()));
                        env->SetByteArrayRegion(arrContent,0,strlen(passage.getContent()),(jbyte*)passage.getContent());
                        jstring content = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrContent);
                        env->DeleteLocalRef(arrContent);

              env->CallVoidMethod(ob,methodAdd,passage.getId(), passage.getIsText(),content);

                        env->DeleteLocalRef(content);
    }

    for(int i =0;i<arrquestion.size();i++){
            QuestionPart7 question = arrquestion.at(i);

             jbyteArray arrques = env->NewByteArray(strlen(question.getQuestion()));
                env->SetByteArrayRegion(arrques,0,strlen(question.getQuestion()),(jbyte*)question.getQuestion());
                jstring contentQuetion = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrques);
                env->DeleteLocalRef(arrques);



                  jbyteArray arrA = env->NewByteArray(strlen(question.getA()));
                                             env->SetByteArrayRegion(arrA,0,strlen(question.getA()),(jbyte*)question.getA());
                                             jstring a = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrA);
                                             env->DeleteLocalRef(arrA);

           jbyteArray arrB = env->NewByteArray(strlen(question.getB()));
                                      env->SetByteArrayRegion(arrB,0,strlen(question.getB()),(jbyte*)question.getB());
                                      jstring b = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrB);
                                      env->DeleteLocalRef(arrB);

               jbyteArray arrC = env->NewByteArray(strlen(question.getC()));
                                          env->SetByteArrayRegion(arrC,0,strlen(question.getC()),(jbyte*)question.getC());
                                          jstring c = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrC);
                                          env->DeleteLocalRef(arrC);

                jbyteArray arrD = env->NewByteArray(strlen(question.getD()));
                                           env->SetByteArrayRegion(arrD,0,strlen(question.getD()),(jbyte*)question.getD());
                                           jstring d = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrD);
                                           env->DeleteLocalRef(arrD);

                  jbyteArray arrsol = env->NewByteArray(strlen(question.getSol()));
                                                          env->SetByteArrayRegion(arrsol,0,strlen(question.getSol()),(jbyte*)question.getSol());
                                                          jstring sol = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrsol);
                                                          env->DeleteLocalRef(arrsol);


                  env->CallVoidMethod(ob,methodAddQuestion,question.getId(),contentQuetion,a,b,c,d,sol);

                            env->DeleteLocalRef(contentQuetion);
                            env->DeleteLocalRef(a);
                                             env->DeleteLocalRef(b);
                                             env->DeleteLocalRef(c);
                                       env->DeleteLocalRef(d);
                                           env->DeleteLocalRef(sol);
                }

    return ob;
}
}

extern "C"{
JNIEXPORT jobject JNICALL
Java_sqlite_SqlitePart7_searchPart7Id(JNIEnv * env , jobject object ,jint id_){

    SqlitePart7 sqlite;
    int id =(int)id_;
    Part7*part =sqlite.searchPart7Id(id);
    return funConvert(env, object, part);

}
}

extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqlitePart7_randomPart7(JNIEnv * env , jobject object ,jint number1){

    SqlitePart7 sqlite;
    int number =(int)number1;
    vector<Part7*>result =sqlite.randomPart7(number);
     jclass  cl = env -> FindClass("model/ModelPart7");

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
Java_sqlite_SqlitePart7_randomPart7CountQuestion(JNIEnv * env , jobject object ,jint number1, jint count1){

    SqlitePart7 sqlite;
    int number =(int)number1;
    int count=(int)count1;
    vector<Part7*>result =sqlite.randomPart7CountQuestion(number,count);
     jclass  cl = env -> FindClass("model/ModelPart7");

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
Java_sqlite_SqlitePart7_searchAllImagePart7(JNIEnv * env , jobject object ){

    SqlitePart7 sqlite;

    vector<TokenPart7*>result =sqlite.searchAllImagePart7();

     jclass cl = env->FindClass("model/ModelTokenPart7");
       jmethodID methodId = env -> GetMethodID(cl,"<init>", "(IILjava/lang/String;)V");

     jclass clcv = env -> FindClass("model/Convert");
     jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");



     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);

     for(int i=0;i<result.size();i++){
          jbyteArray arrA = env->NewByteArray(strlen(result.at(i)->getToken()));
          env->SetByteArrayRegion(arrA,0,strlen(result.at(i)->getToken()),(jbyte*)result.at(i)->getToken());
            jstring a = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arrA);

            jobject ob = env->NewObject(cl, methodId,result.at(i)->getIdPart7(),result.at(i)->getIdPassage(),a);
              env->SetObjectArrayElement(arr, i, ob);

            env->DeleteLocalRef(arrA);
            env->DeleteLocalRef(a);
     }
    return arr;

}
}

extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_SqlitePart7_randomPart7Subject(JNIEnv * env , jobject object ,jint subject1,jint number1){

    SqlitePart7 sqlite;
    int number =(int)number1;
    int subject= (int)subject1;
    vector<Part7*>result =sqlite.randomPart7Subject(subject,number);
     jclass  cl = env -> FindClass("model/ModelPart7");

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
Java_sqlite_SqlitePart7_searchPart7Favorite(JNIEnv * env , jobject object){

    SqlitePart7 sqlite;

    vector<Part7*>result =sqlite.searchPart7Favorite();
     jclass  cl = env -> FindClass("model/ModelPart7");

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
Java_sqlite_SqlitePart7_searchPart7Check(JNIEnv * env , jobject object){

    SqlitePart7 sqlite;

    vector<Part7*>result =sqlite.searchPart7Check();
     jclass  cl = env -> FindClass("model/ModelPart7");

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
Java_sqlite_SqlitePart7_searchAllPartImage(JNIEnv * env , jobject object){

    SqlitePart7 sqlite;

    vector<Part7*>result =sqlite.searchAllPartImage();
     jclass  cl = env -> FindClass("model/ModelPart7");

     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);
     for(int i=0;i<result.size();i++){
        jobject ob = funConvert(env,object,result.at(i));
        env->SetObjectArrayElement(arr, i, ob);
     }
    return arr;

}
}


