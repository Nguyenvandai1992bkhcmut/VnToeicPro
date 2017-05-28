//
// Created by dai nguyen on 5/28/17.
//

#include "SqliteControlPart.h"
#include <sstream>

SqliteControlPart::SqliteControlPart(){
    sqlite3_open_v2("data/data/com.vntoeic.bkteam.vntoeicpro/databases/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);
}

 vector<ModelFavoritePart>SqliteControlPart::searchAllFavoritePart(int part){
         vector<ModelFavoritePart>result;
         stringstream ss2;
         ss2 << part;
         string idpart = ss2.str();
         string sql ="select * from part"+idpart+"_favorite";

          if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
               const char * er = sqlite3_errmsg(db);
               return result;
          }
          while (sqlite3_step(stmt) == SQLITE_ROW) {
                int id = sqlite3_column_int(stmt,0);
                const char * time1 = (const char *)sqlite3_column_text(stmt,1);
                char * time = new char[strlen(time1)+1];
                for(int i =0;i<strlen(time1)+1;i++){
                    time[i]=time1[i];
                }
                ModelFavoritePart model(id,time);
                result.push_back(model);
          }
          return result;
 }

   vector<ModelCheckPart>SqliteControlPart::searchAllCheckedPard(int part){

        vector<ModelCheckPart>result;
                 stringstream ss2;
                 ss2 << part;
                 string idpart = ss2.str();
                 string sql ="select * from part"+idpart+"_checked";

                  if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                       const char * er = sqlite3_errmsg(db);
                       return result;
                  }
                  while (sqlite3_step(stmt) == SQLITE_ROW) {
                        int id = sqlite3_column_int(stmt,0);
                        int re = sqlite3_column_int(stmt,2);
                        const char * time1 = (const char *)sqlite3_column_text(stmt,1);
                        char * time = new char[strlen(time1)+1];
                        for(int i =0;i<strlen(time1)+1;i++){
                            time[i]=time1[i];
                        }
                        ModelCheckPart model(id,time,re);
                        result.push_back(model);
                  }
                  return result;
   }

bool SqliteControlPart::checkPartFavorite(int part1, int id1){
    stringstream ss2;
      ss2 << part1;
      string part = ss2.str();

        stringstream ss3;
            ss3 << id1;
           string id = ss3.str();
        string sql = "select count(*)from part"+part+"_favorite where id = " + id;
        if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
            return false;
         }
        int count =0;
        while (sqlite3_step(stmt) == SQLITE_ROW){
             count = sqlite3_column_int(stmt,0);
        }
        if(count==0)return false;
        else return true;
}

void SqliteControlPart::insertPartFavorite(int part1, ModelFavoritePart * data){
    stringstream ss2;
    ss2 << part1;
    string part = ss2.str();
    string sql = "insert into part"+part+"_favorite values(?,?)";
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
           return ;
     }

    if(sqlite3_bind_int(stmt, 1, data->getId()) != SQLITE_OK){
                return;
    }

    if(sqlite3_bind_text(stmt, 2,data->getDate(),-1,SQLITE_STATIC)!= SQLITE_OK){
          return;
     }
     sqlite3_step(stmt);
}

void SqliteControlPart::insertPartCheck(int part1, ModelCheckPart * data){
    stringstream ss2;
        ss2 << part1;
        string part = ss2.str();
        string sql = "insert into part"+part+"_checked values(?,?,?)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
               return ;
         }

        if(sqlite3_bind_int(stmt, 1, data->getId()) != SQLITE_OK){
                    return;
        }

        if(sqlite3_bind_text(stmt, 2,data->getDate(),-1,SQLITE_STATIC)!= SQLITE_OK){
              return;
         }

         if(sqlite3_bind_int(stmt, 3, data->getResult()) != SQLITE_OK){
                return;
          }
         sqlite3_step(stmt);
}

void SqliteControlPart::deletePartFavorite(int part1, int id1){
      stringstream ss2;
      ss2 << part1;
      string part = ss2.str();

      stringstream ss3;
      ss3 << id1;
       string id = ss3.str();

       string sql = "delete from part"+part+"_favorite  where id=" + id;
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
            return ;
      }
     sqlite3_step(stmt);
}

void SqliteControlPart::deletePartCheck(int part1, int id1){
      stringstream ss2;
      ss2 << part1;
      string part = ss2.str();

      stringstream ss3;
      ss3 << id1;
      string id = ss3.str();

       string sql = "delete from part"+part+"_checked  where id=" + id;
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
            return ;
      }
     sqlite3_step(stmt);
}
