//
// Created by dai nguyen on 5/26/17.
//
#include "SqliteWord.hpp"
#include <sstream>

SqliteWord::SqliteWord(){
    sqlite3_open_v2("/data/user/0/com.vntoeic.bkteam.vntoeicpro/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);
}

vector<Section> SqliteWord::searchAllSection(){
         vector<Section>result;
          string sql ="select * from w_section ";
               if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                   const char * er = sqlite3_errmsg(db);
                   return result;
               }
               while (sqlite3_step(stmt) == SQLITE_ROW) {
                   int id = sqlite3_column_int(stmt, 0);
                   char  * title_ = (char*)sqlite3_column_text(stmt, 1);
                   char * title = new char[strlen(title_) +1];
                   if(title_!= NULL){
                       for(int i=0;i<strlen(title_)+1;i++){
                           title[i]=title_[i];
                       }
                   }else title =(char * )"~~";
                   Section val(id,title);
                   result.push_back(val);
               }
               return result;
}

 vector<WordTag>SqliteWord::searchTaginSection(int idsection){
    vector<WordTag>result;

    stringstream ss2;
    ss2 << idsection;
    string id1 = ss2.str();
    string sql = "select * from w_tag where w_section_id="+ id1;

     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){

        return result;
      }
       while (sqlite3_step(stmt) == SQLITE_ROW) {
            int id_tag = sqlite3_column_int(stmt, 0);
            int id_section = sqlite3_column_int(stmt,1);

            char  * title_ = (char*)sqlite3_column_text(stmt, 2);
            char * title = new char[strlen(title_) +1];
            if(title_!= NULL){
                  for(int i=0;i<strlen(title_)+1;i++){
                      title[i]=title_[i];
                 }
             }else title =(char * )"~~";

            char  * token_ = (char*)sqlite3_column_text(stmt, 3);
             char * token = new char[strlen(token_) +1];
           if(token_!= NULL){
                  for(int i=0;i<strlen(token_)+1;i++){
                         token[i]=token_[i];
                   }
            }else token =(char * )"~~";


           WordTag val(id_tag,id_section,title,token);
           result.push_back(val);
          }
      return result;
 }

  vector<LessonTag>SqliteWord::searchLessonTag (int idwordtag){
        vector<LessonTag>result;

            stringstream ss2;
            ss2 << idwordtag;
            string id1 = ss2.str();
            string sql = "select * from lesson_tag where w_tag_id="+ id1;

             if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){

                return result;
              }
               while (sqlite3_step(stmt) == SQLITE_ROW) {
                    int id_lesson_tag = sqlite3_column_int(stmt, 0);
                    int id_word_tag = sqlite3_column_int(stmt,1);

                    char  * title_ = (char*)sqlite3_column_text(stmt, 2);
                    char * title = new char[strlen(title_) +1];
                    if(title_!= NULL){
                          for(int i=0;i<strlen(title_)+1;i++){
                              title[i]=title_[i];
                         }
                     }else title =(char * )"~~";




                   LessonTag val(id_lesson_tag,id_word_tag,title);
                   result.push_back(val);
                  }
              return result;

  }
  // select id word
 Word *SqliteWord::searchWordId (int id){


              stringstream ss2;
              ss2 << id;
              string id1 = ss2.str();
              string sql = "select * from word, meaning where (word.word_id =  meaning.word_id  and word.word_id="+id1+")";

               if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){

                  return NULL;
                }
                int idword;
                char * word=(char *)"";
                char * pronouce =(char *)"";
                char * example=(char *)"";
                vector<const char * >meaning;
                vector<const char * >type;
                vector<const char * >explan;
                vector<const char * >similar;

                 int f = 0;
                 while (sqlite3_step(stmt) == SQLITE_ROW) {
                       if(f==0){
                          int idword = sqlite3_column_int(stmt, 0);

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

                            f=1;
                       }
                       //meaning
                       char * meaning1 =(char *)"";
                        const void * meaning_ = sqlite3_column_blob(stmt, 7);
                        if(meaning_ !=NULL){
                            meaning1 = decode(meaning_);
                        }
                        meaning.push_back(meaning1);

                         //type
                         char * type1 =(char *)"";
                        const void * type_ = sqlite3_column_blob(stmt, 8);
                        if(type_!=NULL){
                            type1 = decode(type_);
                        }
                        type.push_back(type1);

                         //explan
                        const void * explan_ = sqlite3_column_blob(stmt, 9);
                        char *explan1=(char *)"";
                        if(explan_!=NULL){
                            explan1 = decode(explan_);
                        }
                        explan.push_back(explan1);

                         //similar
                        const void * similar_ = sqlite3_column_blob(stmt, 10);
                        char *similar1 = (char *)"";
                        if(similar_!=NULL){
                            similar1 = decode(similar_);
                        }
                        similar.push_back(similar1);


                    }
                Word * result = new Word(id,word,pronouce,example,meaning,type,explan,similar);
                return result;

    }

    // Search All Word in Lesson return Array of WordLesson
 vector<WordLesson>SqliteWord::searchWordLesson(int idlesson){

              stringstream ss2;
              ss2 << idlesson;
              string id = ss2.str();
              string sql="select * from lesson_tag,word, meaning where (word.word_id =  meaning.word_id and lesson_tag.lesson_tag_id="+id+" and word.word_id in (select word_id from word_lesson where  word_lesson.lesson_tag_id="+id+"))";
           //   string sql = "select * from lesson_tag,word, meaning where (word.word_id =  meaning.word_id and lesson_tag.lesson_tag_id="+id+")";
                vector<WordLesson> result;
               if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                  return result;
                }
                 while (sqlite3_step(stmt) == SQLITE_ROW) {
                       int idword;
                       int idlesson;
                       char * word=(char *)"";
                       char * pronouce =(char *)"";
                       char * example=(char *)"";

                       idlesson = sqlite3_column_int(stmt,0);

                       idword = sqlite3_column_int(stmt, 3);

                          //word
                      const void * word_ = sqlite3_column_blob(stmt, 4);
                      word = decode(word_);
                           //prono
                     const void * pro_ = sqlite3_column_blob(stmt, 5);
                      if(pro_!=NULL){
                            pronouce = decode(pro_);
                       }
                            //

                      const void * exam_ = sqlite3_column_blob(stmt, 6);
                       if(exam_!=NULL){
                            example = decode(exam_);
                       }


                       //meaning
                       char * meaning1 =(char *)"";
                        const void * meaning_ = sqlite3_column_blob(stmt, 10);
                        if(meaning_ !=NULL){
                            meaning1 = decode(meaning_);
                        }


                         //type
                         char * type1 =(char *)"";
                        const void * type_ = sqlite3_column_blob(stmt, 11);
                        if(type_!=NULL){
                            type1 = decode(type_);
                        }


                         //explan
                        const void * explan_ = sqlite3_column_blob(stmt, 12);
                        char *explan1=(char *)"";
                        if(explan_!=NULL){
                            explan1 = decode(explan_);
                        }


                         //similar
                        const void * similar_ = sqlite3_column_blob(stmt, 13);
                        char *similar1 = (char *)"";
                        if(similar_!=NULL){
                            similar1 = decode(similar_);
                        }


                        int check =1;
                        for(int i=0;i<result.size();i++){
                            Word * wo = result.at(i).getWord();
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
                            WordLesson re(idlesson, new Word(idword,word,pronouce,example,meaning,type,explan,similar));
                            result.push_back(re);
                        }
                    }
                return result;
 }

 vector<ModelFavoriteWord>SqliteWord::searchFavoriteWord(){
    string sql = "select * from word_favorite";
    vector<ModelFavoriteWord> result;
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
            return result;
     }
     while (sqlite3_step(stmt) == SQLITE_ROW) {
        int id = sqlite3_column_int(stmt,0);
        char * date_ = (char *)sqlite3_column_text(stmt,1);
        char * date = new char[strlen(date_)+1];
        for(int i=0;i<strlen(date_)+1;i++){
            date[i]=date_[i];
        }

        ModelFavoriteWord re(id,date);
        result.push_back(re);
    }
    return result;
 }


void SqliteWord::insertFavoriteWord(ModelFavoriteWord favoriteWord){

    string sql = "insert into word_favorite values(?,?)";
            if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                return ;
            }

            if(sqlite3_bind_int(stmt, 1, favoriteWord.getId()) != SQLITE_OK){
                return;
            }

            if(sqlite3_bind_text(stmt, 2,favoriteWord.getDate(),-1,SQLITE_STATIC)!= SQLITE_OK){
                return;
            }

            sqlite3_step(stmt);

}

vector<ModelWordChecked>SqliteWord::searchWordChecked(int flag , int id1){
    string sql="";
    stringstream ss2;
     ss2 << id1;
     string id = ss2.str();
    if(flag==0){
        sql="select * from word_checked,word_lesson where word_checked.id = word_lesson.word_id";
    }else{
        sql ="select * from word_checked,word_lesson where word_checked.id = word_lesson.word_id and word_checked.id="+id;
    }

    vector<ModelWordChecked> result;
        if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                return result;
         }
         while (sqlite3_step(stmt) == SQLITE_ROW) {
            int idword = sqlite3_column_int(stmt,3);
            int idles = sqlite3_column_int(stmt,4);
            int resulttime = sqlite3_column_int(stmt,2);

            char * date_ = (char *)sqlite3_column_text(stmt,1);
            char * date = new char[strlen(date_)+1];
            for(int i=0;i<strlen(date_)+1;i++){
                date[i]=date_[i];
            }

            ModelWordChecked re(idword,idles,resulttime,date);
            result.push_back(re);
        }
        return result;
}

void SqliteWord::insertWordChecked(ModelWordChecked wchecked){
     string sql = "insert into word_checked values(?,?,?)";
                if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                    return ;
                }

                if(sqlite3_bind_int(stmt, 1, wchecked.getIdWord()) != SQLITE_OK){
                    return;
                }

                if(sqlite3_bind_text(stmt, 2,wchecked.getTime(),-1,SQLITE_STATIC)!= SQLITE_OK){
                    return;
                }
                if(sqlite3_bind_int(stmt, 3, wchecked.getIdResult()) != SQLITE_OK){
                    return;
                }

                sqlite3_step(stmt);
}
// check favoriteword
bool SqliteWord::checkFavotiteWord(int id1){
    stringstream ss2;
    ss2 << id1;
    string id = ss2.str();
    string sql = "select count(*)from word_favorite where word_favorite.id ="+ id;
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
          return false;
    }
    int count =0;
    while (sqlite3_step(stmt) == SQLITE_ROW) {
         count = sqlite3_column_int(this->stmt,0);
    }
    if(count == 0)return false;
    else return true;

}

// check favoriteword
void SqliteWord::deleteWordFavorite(int id1){
    stringstream ss2;
    ss2 << id1;
    string id = ss2.str();
    string sql = "delete from word_favorite where word_favorite.id="+ id;
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
          return;
    }
    sqlite3_step(stmt);
}

void SqliteWord::deleteWordChecked(int id1){
    stringstream ss2;
    ss2 << id1;
    string id = ss2.str();
    string sql = "delete from word_checked where word_checked.id="+ id;
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
          return;
    }
    sqlite3_step(stmt);
}



bool SqliteWord::checkWordChecked(int id1){
    stringstream ss2;
    ss2 << id1;
    string id = ss2.str();
    string sql = "select count(*)from word_checked where word_checked.id ="+ id;
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
          return false;
    }
    int count =0;
    while (sqlite3_step(stmt) == SQLITE_ROW) {
         count = sqlite3_column_int(this->stmt,0);
    }
    if(count == 0)return false;
    else return true;

}