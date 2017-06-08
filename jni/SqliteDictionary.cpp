//
//  SqliteDictionary.cpp
//  vntoeic
//
//  Created by dai nguyen on 5/15/17.
//  Copyright © 2017 dai nguyen. All rights reserved.
//

#include "SqliteDictionary.hpp"
#include <sstream>

SqliteDictionary::SqliteDictionary(){
    sqlite3_open_v2("/data/user/0/com.vntoeic.bkteam.vntoeicpro/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);
}


vector<Dictionary> SqliteDictionary::searchAll(const char * s){
         vector<Dictionary>result;
         string ss = (string)s;
       string sql ="select * from dictionary where word glob '"+ss+"*' limit 20";
       if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
           const char * er = sqlite3_errmsg(db);
           return result;
       }
       while (sqlite3_step(stmt) == SQLITE_ROW) {
           int id = sqlite3_column_int(stmt, 0);
           char  * name = (char*)sqlite3_column_text(stmt, 1);
           char * name1 = new char[strlen(name) +1];
           if(name!= NULL){
               for(int i=0;i<strlen(name)+1;i++){
                   name1[i]=name[i];
               }
           }else name1 =(char * )"~~";
           const void * content = sqlite3_column_blob(stmt, 2);
           char * content1 = decode(content);
           Dictionary val(id,name1,content1);
           result.push_back(val);

       }
       return result;
}

Dictionary * SqliteDictionary :: searchId(int id){

    stringstream ss2;
    ss2 << id;
    string id1 = ss2.str();
     string sql ="select * from dictionary where id ="+id1;
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                const char * er = sqlite3_errmsg(db);
                return NULL;
            }
     Dictionary * dic = NULL;
     while (sqlite3_step(stmt) == SQLITE_ROW) {
           int id = sqlite3_column_int(stmt, 0);
           char  * name = (char*)sqlite3_column_text(stmt, 1);
           char * name1 = new char[strlen(name) +1];
                if(name!= NULL){
                    for(int i=0;i<strlen(name)+1;i++){
                        name1[i]=name[i];
                    }
                }else name1 =(char * )"~~";
                const void * content = sqlite3_column_blob(stmt, 2);
                char * content1 = decode(content);
                dic = new Dictionary(id,name1, content1);

            }
     return dic;
}


vector<DictionaryFavorite> SqliteDictionary::searchFavorite(){
         vector<DictionaryFavorite>result;
       string sql ="select * from dictionary_favorite";
       if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
           const char * er = sqlite3_errmsg(db);
           return result;
       }
       while (sqlite3_step(stmt) == SQLITE_ROW) {
           int id = sqlite3_column_int(stmt, 0);
            const char  * time = (const  char*)sqlite3_column_text(stmt, 1);
              char * time1 = new char[strlen(time) +1];
           if(time!= NULL){
               for(int i=0;i<strlen(time)+1;i++){
                   time1[i]=time[i];
               }
           }else time1 =(char * )"~~";
         const   char * meaning = (const char * )sqlite3_column_text(stmt, 2);
           char * meaning1 = new char[strlen(meaning)+1];
           if(meaning!=NULL){
                   for(int i =0;i<strlen(meaning)+1;i++){
                           meaning1[i]= meaning[i];
                   }
           }else meaning1 =(char *)"~~";

           DictionaryFavorite val(id,time1,meaning1);
           result.push_back(val);

       }
       return result;
}

void SqliteDictionary::insertFavorite(DictionaryFavorite dic){
        string sql = "insert into dictionary_favorite values(?,?,?)";
        if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
            return ;
        }

        if(sqlite3_bind_int(stmt, 1, dic.getId()) != SQLITE_OK){
            return;
        }

        if(sqlite3_bind_text(stmt, 2,dic.getTime(),-1,SQLITE_STATIC)!= SQLITE_OK){
            return;
        }
        if(sqlite3_bind_text(stmt, 3,dic.getMeaning(),-1,SQLITE_STATIC)!= SQLITE_OK){
            return;
        }

        sqlite3_step(stmt);
}

vector<Dictionary>SqliteDictionary::searchHistory(){
     string sql = "select * from dictionary where history =1";
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                vector<Dictionary>re;
                return re;
       }
       vector<Dictionary>result;

            while (sqlite3_step(stmt) == SQLITE_ROW) {
                  int id = sqlite3_column_int(stmt, 0);
                  char  * name = (char*)sqlite3_column_text(stmt, 1);
                  char * name1 = new char[strlen(name) +1];
                       if(name!= NULL){
                           for(int i=0;i<strlen(name)+1;i++){
                               name1[i]=name[i];
                           }
                       }else name1 =(char * )"~~";
                       const void * content = sqlite3_column_blob(stmt, 2);
                       char * content1 = decode(content);
                       Dictionary dic(id,name1, content1);
                       result.push_back(dic);

                   }
            return result;
}

void SqliteDictionary::updateHistory(int id1, int his1){
    stringstream ss2;
    ss2 << id1;
    string id = ss2.str();

     stringstream ss3;
     ss3 << his1;
     string his = ss3.str();

    string sql = "update dictionary set history=" +his +"  where id =" + id;
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                return ;
       }
      sqlite3_step(stmt);
}

bool SqliteDictionary::checkFavorite(int id1){
     stringstream ss2;
     ss2 << id1;
     string id = ss2.str();
     string sql = "select count(*) from dictionary_favorite  where `id` =" + id;
      if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
           return false;
       }
       int count =0;
       while (sqlite3_step(stmt) == SQLITE_ROW) {
            count = sqlite3_column_int(stmt,0);
       }
       if(count==0)return false;
       else return true;
}

DictionaryFavorite * SqliteDictionary::searchFavoriteDictionary(int id1){
        stringstream ss2;
        ss2 << id1;
        string id = ss2.str();
         string sql ="select * from dictionary_favorite where `id`="+id;
               if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                   const char * er = sqlite3_errmsg(db);
                   return NULL ;
               }

               DictionaryFavorite *result=NULL;
               while (sqlite3_step(stmt) == SQLITE_ROW) {
                   int id = sqlite3_column_int(stmt, 0);

                    const char  * time = (const  char*)sqlite3_column_text(stmt, 1);
                      char * time1 = new char[strlen(time) +1];
                   if(time!= NULL){
                       for(int i=0;i<strlen(time)+1;i++){
                           time1[i]=time[i];
                       }
                   }else time1 =(char * )"~~";
                 const   char * meaning = (const char * )sqlite3_column_text(stmt, 2);
                   char * meaning1 = new char[strlen(meaning)+1];
                   if(meaning!=NULL){
                           for(int i =0;i<strlen(meaning)+1;i++){
                                   meaning1[i]= meaning[i];
                           }
                   }else meaning1 =(char *)"~~";

                   result= new DictionaryFavorite(id,time1,meaning1);

               }
               return result;
}