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
<<<<<<< HEAD
=======
                char * token =(char *)"";
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                vector<const char * >meaning;
                vector<const char * >type;
                vector<const char * >explan;
                vector<const char * >similar;

                 int f = 0;
                 while (sqlite3_step(stmt) == SQLITE_ROW) {
                       if(f==0){
                          int idword = sqlite3_column_int(stmt, 0);

<<<<<<< HEAD
                          //word
                          const void * word_ = sqlite3_column_blob(stmt, 1);
                           word = decode(word_);
                           //prono
                           const void * pro_ = sqlite3_column_blob(stmt, 2);
=======
                          const void * token_ = sqlite3_column_blob(stmt,1);
                          token = decode(token_);
                          //word
                          const void * word_ = sqlite3_column_blob(stmt, 2);
                           word = decode(word_);
                           //prono
                           const void * pro_ = sqlite3_column_blob(stmt, 3);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                           if(pro_!=NULL){
                                 pronouce = decode(pro_);
                           }
                            //

<<<<<<< HEAD
                            const void * exam_ = sqlite3_column_blob(stmt, 3);
=======
                            const void * exam_ = sqlite3_column_blob(stmt, 4);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                            if(exam_!=NULL){
                             example = decode(exam_);
                             }

                            f=1;
                       }
                       //meaning
                       char * meaning1 =(char *)"";
<<<<<<< HEAD
                        const void * meaning_ = sqlite3_column_blob(stmt, 7);
=======
                        const void * meaning_ = sqlite3_column_blob(stmt, 8);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                        if(meaning_ !=NULL){
                            meaning1 = decode(meaning_);
                        }
                        meaning.push_back(meaning1);

                         //type
                         char * type1 =(char *)"";
<<<<<<< HEAD
                        const void * type_ = sqlite3_column_blob(stmt, 8);
=======
                        const void * type_ = sqlite3_column_blob(stmt, 9);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                        if(type_!=NULL){
                            type1 = decode(type_);
                        }
                        type.push_back(type1);

                         //explan
<<<<<<< HEAD
                        const void * explan_ = sqlite3_column_blob(stmt, 9);
=======
                        const void * explan_ = sqlite3_column_blob(stmt, 10);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                        char *explan1=(char *)"";
                        if(explan_!=NULL){
                            explan1 = decode(explan_);
                        }
                        explan.push_back(explan1);

                         //similar
<<<<<<< HEAD
                        const void * similar_ = sqlite3_column_blob(stmt, 10);
=======
                        const void * similar_ = sqlite3_column_blob(stmt, 11);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                        char *similar1 = (char *)"";
                        if(similar_!=NULL){
                            similar1 = decode(similar_);
                        }
                        similar.push_back(similar1);


                    }
<<<<<<< HEAD
                Word * result = new Word(id,word,pronouce,example,meaning,type,explan,similar);
=======
                Word * result = new Word(id,token,word,pronouce,example,meaning,type,explan,similar);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
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
<<<<<<< HEAD

                       idlesson = sqlite3_column_int(stmt,0);

                       idword = sqlite3_column_int(stmt, 3);

                          //word
                      const void * word_ = sqlite3_column_blob(stmt, 4);
                      word = decode(word_);
                           //prono
                     const void * pro_ = sqlite3_column_blob(stmt, 5);
=======
                        char * token= (char*)"";
                       idlesson = sqlite3_column_int(stmt,0);

                       idword = sqlite3_column_int(stmt, 3);
                        const void * token_ = sqlite3_column_blob(stmt,4);
                         token = decode(token_);
                          //word
                      const void * word_ = sqlite3_column_blob(stmt, 5);
                      word = decode(word_);
                           //prono
                     const void * pro_ = sqlite3_column_blob(stmt, 6);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                      if(pro_!=NULL){
                            pronouce = decode(pro_);
                       }
                            //

<<<<<<< HEAD
                      const void * exam_ = sqlite3_column_blob(stmt, 6);
=======
                      const void * exam_ = sqlite3_column_blob(stmt, 7);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                       if(exam_!=NULL){
                            example = decode(exam_);
                       }


                       //meaning
                       char * meaning1 =(char *)"";
<<<<<<< HEAD
                        const void * meaning_ = sqlite3_column_blob(stmt, 10);
=======
                        const void * meaning_ = sqlite3_column_blob(stmt, 11);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                        if(meaning_ !=NULL){
                            meaning1 = decode(meaning_);
                        }


                         //type
                         char * type1 =(char *)"";
<<<<<<< HEAD
                        const void * type_ = sqlite3_column_blob(stmt, 11);
=======
                        const void * type_ = sqlite3_column_blob(stmt, 12);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                        if(type_!=NULL){
                            type1 = decode(type_);
                        }


                         //explan
<<<<<<< HEAD
                        const void * explan_ = sqlite3_column_blob(stmt, 12);
=======
                        const void * explan_ = sqlite3_column_blob(stmt, 13);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                        char *explan1=(char *)"";
                        if(explan_!=NULL){
                            explan1 = decode(explan_);
                        }


                         //similar
<<<<<<< HEAD
                        const void * similar_ = sqlite3_column_blob(stmt, 13);
=======
                        const void * similar_ = sqlite3_column_blob(stmt, 14);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
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
<<<<<<< HEAD
                            WordLesson re(idlesson, new Word(idword,word,pronouce,example,meaning,type,explan,similar));
=======
                            WordLesson re(idlesson, new Word(idword,token,word,pronouce,example,meaning,type,explan,similar));
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                            result.push_back(re);
                        }
                    }
                return result;
 }

<<<<<<< HEAD
 vector<ModelFavoriteWord>SqliteWord::searchFavoriteWord(){
    string sql = "select * from word_favorite";
=======
 vector<ModelFavoriteWord>SqliteWord::searchFavoriteWord(int lesson1){
    stringstream ss2;
            ss2 << lesson1;
             string lesson = ss2.str();
    string sql = "select * from word_lesson where word_lesson.lesson_tag_id="+ lesson;
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
    vector<ModelFavoriteWord> result;
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
            return result;
     }
     while (sqlite3_step(stmt) == SQLITE_ROW) {
        int id = sqlite3_column_int(stmt,0);
<<<<<<< HEAD
        char * date_ = (char *)sqlite3_column_text(stmt,1);
        char * date = new char[strlen(date_)+1];
        for(int i=0;i<strlen(date_)+1;i++){
            date[i]=date_[i];
        }
=======

         char * date_ = (char *)sqlite3_column_text(stmt,2);
                    char * date = new char[strlen(date_)+1];
                    for(int i=0;i<strlen(date_)+1;i++){
                        date[i]=date_[i];
                    }
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

        ModelFavoriteWord re(id,date);
        result.push_back(re);
    }
    return result;
 }


<<<<<<< HEAD
void SqliteWord::insertFavoriteWord(ModelFavoriteWord favoriteWord){

=======
void SqliteWord::insertFavoriteWord(int color,ModelFavoriteWord favoriteWord){
    string sql = "insert into word_lesson values(?,?,?)";
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
                    return ;
                }
    if(sqlite3_bind_int(stmt, 2, color) != SQLITE_OK){
               return;
     }

     if(sqlite3_bind_int(stmt, 1, favoriteWord.getId()) != SQLITE_OK){
                    return;
      }
       if(sqlite3_bind_text(stmt, 3,favoriteWord.getDate(),-1,SQLITE_STATIC)!= SQLITE_OK){
                       return;
                   }
            sqlite3_step(stmt);


/*
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
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
<<<<<<< HEAD
=======
            */
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

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
<<<<<<< HEAD
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
=======
int SqliteWord::checkFavotiteWord(int id1){
    stringstream ss2;
    ss2 << id1;
    string id = ss2.str();
    string sql = "select lesson_tag_id from word_lesson where word_lesson.word_id ="+ id;
    if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
          return false;
    }
    int count =-1;
    while (sqlite3_step(stmt) == SQLITE_ROW) {
         count = sqlite3_column_int(this->stmt,0);
    }
    return count;
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

}

// check favoriteword
<<<<<<< HEAD
void SqliteWord::deleteWordFavorite(int id1){
    stringstream ss2;
    ss2 << id1;
    string id = ss2.str();
    string sql = "delete from word_favorite where word_favorite.id="+ id;
=======
void SqliteWord::deleteWordFavorite(int lesson,int id1){
    stringstream ss2;
    ss2 << id1;
    string id = ss2.str();

    stringstream ss3;
       ss3 << lesson;
       string lesson1 = ss3.str();

    string sql = "delete from word_lesson where (word_lesson.word_id="+ id +" and word_lesson.lesson_tag_id ="+ lesson1+" )";
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
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