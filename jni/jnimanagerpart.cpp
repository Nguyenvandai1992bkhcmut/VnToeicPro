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

extern "C"{
JNIEXPORT jobject JNICALL
funconvertWord(JNIEnv * env , jobject object, Word * word){
     jclass  cl = env -> FindClass("model/ModelWord");

    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");

    jclass javastring = env->FindClass("java/lang/String");

      jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V");

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


           jobject re = env->NewObject(cl,methodId,word->getId(),wordname,pro,exam,arrmeaning,arrtype,arrexplan,arrsimilar);


                             env->DeleteLocalRef(wordname);
                            env->DeleteLocalRef(pro);
                            env->DeleteLocalRef(exam);
                            env->DeleteLocalRef(arrmeaning);
                            env->DeleteLocalRef(arrtype);
                            env->DeleteLocalRef(arrexplan);
                            env->DeleteLocalRef(arrsimilar);
                            return re;

}
}

extern "C" {
JNIEXPORT jobjectArray JNICALL
Java_sqlite_ManagerPart_searchWordPart(JNIEnv * env , jobject object, jint part1, jint id1){

    SqliteControlPart sqlite;
    int part = (int)part1;
    int id= (int )id1;
    jclass  cl = env -> FindClass("model/ModelWord");
    vector<Word*>result = sqlite.searchWordPart(part,id);
     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);



     for (int ii =0;ii<result.size();ii++){
                Word * word = result.at(ii);
                jobject ob = funconvertWord(env, object,word);
                 env->SetObjectArrayElement(arr, ii, ob);
                 env->DeleteLocalRef(ob);


        }
       return arr;

}
}


extern "C" {
JNIEXPORT jobjectArray JNICALL
Java_sqlite_ManagerPart_searchWordPartAware(JNIEnv * env , jobject object, jint part1, jint is_aware,jint id1){

    SqliteControlPart sqlite;
    int part = (int)part1;
    int id= (int )id1;
    int aware= (int)is_aware;
    jclass  cl = env -> FindClass("model/ModelWord");
    vector<Word*>result = sqlite.searchWordPartAware(part,aware,id);
     jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);



     for (int ii =0;ii<result.size();ii++){
                Word * word = result.at(ii);
                jobject ob = funconvertWord(env, object,word);
                 env->SetObjectArrayElement(arr, ii, ob);
                 env->DeleteLocalRef(ob);


        }
       return arr;

}
}


extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_ManagerPart_searchPartSubject(JNIEnv* env , jobject object , jint idpart ){


    jclass  cl = env -> FindClass("model/ModelPartSubject");
    jmethodID methodId = env -> GetMethodID(cl,"<init>", "(IILjava/lang/String;)V");

    jclass clcv = env -> FindClass("model/Convert");
    jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");


    SqliteControlPart sqlite;
    int id =(int)idpart;
    vector<PartSubject>result = sqlite.searchPartSubject(id);

    jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);

    for(int i =0;i<result.size();i++){

         jbyteArray arraytime = env->NewByteArray(strlen(result.at(i).getPartSubject()));
         env->SetByteArrayRegion(arraytime,0,strlen(result.at(i).getPartSubject()),(jbyte*)result.at(i).getPartSubject());
         jstring time = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytime);
         env->DeleteLocalRef(arraytime);

         jobject ob = env->NewObject(cl, methodId,id,result.at(i).getPartId(),time);
         env->SetObjectArrayElement(arr, i, ob);
         env->DeleteLocalRef(ob);
    }

    return arr;
}
}

// grammar

extern "C"{
JNIEXPORT jobject JNICALL
funconvertGrammar(JNIEnv * env , jobject object , Grammar  grammar){
     jclass  cl = env -> FindClass("model/ModelGrammar");
     jmethodID methodId = env -> GetMethodID(cl,"<init>", "(ILjava/lang/String;Ljava/lang/String;)V");

     jclass clcv = env -> FindClass("model/Convert");
     jmethodID methodId1 = env -> GetStaticMethodID(clcv, "convertCStringToJniSafeString","([B)Ljava/lang/String;");

      jbyteArray arraytitle = env->NewByteArray(strlen(grammar.getTitle()));
      env->SetByteArrayRegion(arraytitle,0,strlen(grammar.getTitle()),(jbyte*)grammar.getTitle());
      jstring title = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraytitle);
      env->DeleteLocalRef(arraytitle);

       jbyteArray arraycontent = env->NewByteArray(strlen(grammar.getContent()));
       env->SetByteArrayRegion(arraycontent,0,strlen(grammar.getContent()),(jbyte*)grammar.getContent());
       jstring content = (jstring)env->CallStaticObjectMethod(clcv, methodId1, arraycontent);
       env->DeleteLocalRef(arraycontent);

       jobject ob = env->NewObject(cl, methodId, grammar.getId(), title, content);
       return ob;


}
}

extern "C"{
JNIEXPORT jobjectArray JNICALL
Java_sqlite_ManagerPart_searchAllGrammar(JNIEnv* env , jobject object ){

    SqliteControlPart sqlite;

    vector<Grammar>result = sqlite.searchAllGrammar();

     jclass  cl = env -> FindClass("model/ModelGrammar");
    jobjectArray arr = env->NewObjectArray((int)result.size(), cl, NULL);

    for(int i =0;i<result.size();i++){

        jobject ob = funconvertGrammar(env,object,result.at(i));
        env->SetObjectArrayElement(arr, i, ob);
        env->DeleteLocalRef(ob);
    }

    return arr;
}
}


extern "C"{
JNIEXPORT jobject JNICALL
Java_sqlite_ManagerPart_searchGrammarId(JNIEnv* env , jobject object , jint idgrammar ){

    SqliteControlPart sqlite;

    Grammar * grammar = sqlite.searchGrammarId((int)idgrammar);
    if(grammar==NULL)return NULL;
    jobject ob = funconvertGrammar(env,object,*grammar);
    return ob;
}
}


extern "C"{
JNIEXPORT void JNICALL
Java_sqlite_ManagerPart_updateWordAware(JNIEnv* env , jobject object , jint idword, jint aware ){

    SqliteControlPart sqlite;

    sqlite.updateWordAware((int)idword,(int)aware);
}
}