//
// Created by dai nguyen on 5/28/17.
//

#include "SqliteControlPart.h"
#include <sstream>

SqliteControlPart::SqliteControlPart(){
    sqlite3_open_v2("/data/user/0/com.vntoeic.bkteam.vntoeicpro/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);}

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


    vector<ModelCheckPart>SqliteControlPart::searchAllCheckPartId(int part, int question){

           vector<ModelCheckPart>result;
                    stringstream ss2;
                    ss2 << part;
                    string idpart = ss2.str();

                    stringstream ss3;
                                        ss3 << question;
                                        string idquestion = ss3.str();
                    string sql ="select * from part"+idpart+"_checked where id ="+idquestion ;

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

void SqliteControlPart::deletePartFavoriteIdTime(int part1, int id1,const char * time1){
      stringstream ss2;
      ss2 << part1;
      string part = ss2.str();

      stringstream ss3;
      ss3 << id1;
       string id = ss3.str();

       string time =(string)time1;

       string sql = "delete from part"+part+"_favorite  where id=" + id +" and time ='"+time+"'";
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

void SqliteControlPart::deletePartCheckIdTime(int part1, int id1 , const char * time1){
      stringstream ss2;
      ss2 << part1;
      string part = ss2.str();

      stringstream ss3;
      ss3 << id1;
      string id = ss3.str();
       string time =(string)time1;
       string sql = "delete from part"+part+"_checked  where id=" + id +" and time ='"+time+"'";
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
            return ;
      }
     sqlite3_step(stmt);
}


vector<Word*>SqliteControlPart::funSeachWord(){
    vector<Word*>result;
    while (sqlite3_step(stmt) == SQLITE_ROW) {
                           int idword;
                           int idlesson;
                           char * word=(char *)"";
                           char * pronouce =(char *)"";
                           char * example=(char *)"";

                           idword = sqlite3_column_int(stmt, 0);

                              //word
                          const void * word_ = sqlite3_column_blob(stmt, 1);
                          word = decode(word_);
                               //prono
                         const void * pro_ = sqlite3_column_blob(stmt, 2);
                          if(pro_!=NULL){
                                pronouce = decode(pro_);
                           }
                                //

                          const void * exam_ = sqlite3_column_blob(stmt, 3);
                           if(exam_!=NULL){
                                example = decode(exam_);
                           }


                           //meaning
                           char * meaning1 =(char *)"";
                            const void * meaning_ = sqlite3_column_blob(stmt, 7);
                            if(meaning_ !=NULL){
                                meaning1 = decode(meaning_);
                            }


                             //type
                             char * type1 =(char *)"";
                            const void * type_ = sqlite3_column_blob(stmt, 8);
                            if(type_!=NULL){
                                type1 = decode(type_);
                            }


                             //explan
                            const void * explan_ = sqlite3_column_blob(stmt, 9);
                            char *explan1=(char *)"";
                            if(explan_!=NULL){
                                explan1 = decode(explan_);
                            }


                             //similar
                            const void * similar_ = sqlite3_column_blob(stmt, 10);
                            char *similar1 = (char *)"";
                            if(similar_!=NULL){
                                similar1 = decode(similar_);
                            }


                            int check =1;
                            for(int i=0;i<result.size();i++){
                                Word * wo = result.at(i);
                                if(wo->getId()== idword){
                                       vector<const char * >m=wo->getMeaning();
                                       m.push_back(meaning1);
                                       wo->setMeaning(m);

                                       vector<const char * >t=wo->getType();
                                        t.push_back(type1);
                                       wo->setType(t);

                                       vector<const char * >s=wo->getExplan();
                                       s.push_back(explan1);
                                       wo->setExplan(s);


                                       vector<const char * >si=wo->getSimilar();
                                       si.push_back(similar1);
                                       wo->setSimilar(si);
                                       check=0;
                                       break;
                                }
                            }
                            if(check==1){
                                vector<const char * >meaning;
                                vector<const char * >type;
                                vector<const char * >explan;
                                vector<const char * >similar;
                                meaning.push_back(meaning1);
                                type.push_back(type1);
                                explan.push_back(explan1);
                                similar.push_back(similar1);
                                Word* w= new Word(idword,word,pronouce,example,meaning,type,explan,similar);
                                result.push_back(w);
                            }
                        }

    return result;

}

 vector<Word *>SqliteControlPart::searchWordPart(int part1, int id1){
        stringstream ss2;
        ss2 << part1;
        string part = ss2.str();

        stringstream ss3;
        ss3 << id1;
        string id = ss3.str();

        string sql ="select * from word, meaning where (word.word_id =  meaning.word_id  and word.word_id in (select word_id from part"+part+"_word where part"+part+"_word.part"+part+"_id="+id+"))";

         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Word*>s;
             return s;
         }

         vector<Word*>result = funSeachWord();
         return result;

 }

 vector<Word *>SqliteControlPart::searchWordPartAware(int part1,int aware1, int id1){
         stringstream ss2;
         ss2 << part1;
         string part = ss2.str();

         stringstream ss3;
         ss3 << id1;
         string id = ss3.str();

          stringstream ss4;
          ss4 << aware1;
          string aware = ss4.str();

         string sql ="select * from word, meaning where (word.word_id =  meaning.word_id and word.aware ="+aware+"  and word.word_id in (select word_id from part"+part+"_word where part"+part+"_word.part"+part+"_id="+id+"))";

          if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
              vector<Word*>s;
              return s;
          }

          vector<Word*>result = funSeachWord();
          return result;

  }

vector<PartSubject>SqliteControlPart::searchPartSubject(int part){
            vector<PartSubject>result;
             stringstream ss2;
             ss2 << part;
             string idpart = ss2.str();
             string sql ="select * from p"+idpart+"_subject";

              if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                   const char * er = sqlite3_errmsg(db);
                   return result;
              }
              while (sqlite3_step(stmt) == SQLITE_ROW) {
                    int id = sqlite3_column_int(stmt,0);
                    const char * title1 = (const char *)sqlite3_column_text(stmt,1);
                    char * title = new char[strlen(title1)+1];
                    for(int i =0;i<strlen(title1)+1;i++){
                        title[i]=title1[i];
                    }
                    PartSubject model(id,title);
                    result.push_back(model);
              }
              return result;
}

vector<Grammar>SqliteControlPart::searchAllGrammar(){
              vector<Grammar>result;

             string sql ="select * from grammar";

              if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                   const char * er = sqlite3_errmsg(db);
                   return result;
              }

              while(sqlite3_step(stmt)== SQLITE_ROW){
                    int id = (int)sqlite3_column_int(stmt,0);
                    const char * title1 = (const char *)sqlite3_column_text(stmt,1);
                    char * title = new char[strlen(title1)+1];
                    for(int i=0;i<strlen(title1)+1;i++){
                        title[i]=title1[i];
                    }

                    const void * content1 = sqlite3_column_blob(stmt,2);
                    const char * content = decode(content1);
                    Grammar grammar(id,title,content);
                    result.push_back(grammar);
              }
              return result;
}

Grammar* SqliteControlPart::searchGrammarId(int id1){
              Grammar * result=NULL;
               stringstream ss2;
               ss2<<id1;
               string id = ss2.str();
                string sql ="select * from grammar where grammar_id ="+ id;

              if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                   return result;
              }

              while(sqlite3_step(stmt)== SQLITE_ROW){
                    int id = (int)sqlite3_column_int(stmt,0);
                    const char * title1 = (const char *)sqlite3_column_text(stmt,1);
                    char * title = new char[strlen(title1)+1];
                    for(int i=0;i<strlen(title1)+1;i++){
                        title[i]=title1[i];
                    }

                    const void * content1 = sqlite3_column_blob(stmt,2);
                    const char * content = decode(content1);
                     result = new Grammar(id,title,content);

              }
              return result;
}

void SqliteControlPart::updateWordAware(int idword1, int aware1){
            stringstream ss2;
               ss2<<idword1;
               string idword = ss2.str();

               stringstream ss3;
              ss3<<aware1;
              string aware = ss3.str();
                string sql ="Update Word set aware="+aware+" where word_id ="+idword;

              if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                   return ;
              }
             sqlite3_step(stmt);
}

vector<PartSubjectResult>SqliteControlPart::searhPartSubjectResult(int part1){
     stringstream ss2;
                   ss2<<part1;
                   string part = ss2.str();
                    string sql ="select * from part"+part+"_result_view";
            vector<PartSubjectResult>result;
           if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                 return result;
            }

             while(sqlite3_step(stmt)== SQLITE_ROW){
                               int id = (int)sqlite3_column_int(stmt,0);

                               const char * title1 = (const char *)sqlite3_column_text(stmt,1);
                               char * title = new char[strlen(title1)+1];
                               for(int i=0;i<strlen(title1)+1;i++){
                                   title[i]=title1[i];
                               }
                               int correct = (int)sqlite3_column_int(stmt,2);

                               int count = (int)sqlite3_column_int(stmt,3);

                              PartSubjectResult re(id,title,correct,count);
                                result.push_back(re);

                         }
                        return result;

}

bool SqliteControlPart::updateTimePart(int part1 , int id1){
       stringstream ss2;
    ss2<<part1;
    string part = ss2.str();

    stringstream ss3;
     ss3<<id1;
     string id = ss3.str();
     string sql ="update part"+part+" set time=time+1 where part"+part+".part"+part+"_id = "+id;
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){

           return false;

     }
        const char* data = "Callback function called";
   char *zErrMsg = 0;

     int rc= sqlite3_exec(db, sql.c_str(), NULL, NULL, NULL);
    if( rc != SQLITE_OK ){
            return false;
     }else{
            return true;
     }
       sqlite3_close(db);
     return true;
  }
