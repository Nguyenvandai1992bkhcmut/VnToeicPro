//
// Created by dai nguyen on 7/5/17.
//

#include "SqlitePart7.h"
#include <sstream>

SqlitePart7::SqlitePart7(){
    sqlite3_open_v2("/data/user/0/com.vntoeic.bkteam.vntoeicpro/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);
}

vector<Part7*>SqlitePart7::funsearchPart(){
     vector<Part7*>result;
        while (sqlite3_step(stmt) == SQLITE_ROW){
                int id = sqlite3_column_int(stmt,0);
                const void * explan = sqlite3_column_blob(stmt,1);
                char * explan1 = decode(explan);


                //
                //quesiton 1
                //
                int level = sqlite3_column_int(stmt,2);
                int time = sqlite3_column_int(stmt,3);
                int count_question = sqlite3_column_int(stmt,4);
                int idpas= sqlite3_column_int(stmt,6);
                int istext = sqlite3_column_int(stmt,7);

                const void * token = sqlite3_column_blob(stmt,8);
                char * token1 = decode(token);

                const void * content = sqlite3_column_blob(stmt,9);
                char * content1 = decode(content);

                int idquestion = sqlite3_column_int(stmt,10);

                const void *  conQuestion = sqlite3_column_blob(stmt,12);
                const char * contentQuestion = decode(conQuestion);

                const char * a = (const char * )sqlite3_column_text(stmt,13);

                 char * a1= new char[strlen(a)+1];
                  for(int i=0;i<strlen(a)+1;i++){
                          a1[i]=a[i];
                   }


                const char * b = (const char * ) sqlite3_column_text(stmt,14);

                 char * b1= new char[strlen(b)+1];
                  for(int i=0;i<strlen(b)+1;i++){
                          b1[i]=b[i];
                   }



                const char * c =  (const char * )sqlite3_column_text(stmt,15);

                 char * c1= new char[strlen(c)+1];
                  for(int i=0;i<strlen(c)+1;i++){
                          c1[i]=c[i];
                   }


                const char * d =  (const char * )sqlite3_column_text(stmt,16);

                 char * d1= new char[strlen(d)+1];
                  for(int i=0;i<strlen(d)+1;i++){
                          d1[i]=d[i];
                   }


                const char * sol = (const char * ) sqlite3_column_text(stmt,17);

                 char * sol1= new char[strlen(sol)+1];
                  for(int i=0;i<strlen(sol)+1;i++){
                          sol1[i]=sol[i];
                   }
                int check0=0;
                int check1 =0;
                int check2 =0;
                int index =0;
                for(int i =0;i<result.size();i++){
                    if(result.at(i)->getId()==id){
                        check0=1;
                        index=i;
                    }


                    vector<Passage> passages = result.at(i)->getPassage();
                    vector<QuestionPart7>arrQuestion = result.at(i)->getQuestions();

                    for(int j =0;j<passages.size();j++){
                        if(passages.at(j).getId()== idpas){
                            check1=1;
                            break;
                        }
                    }

                     for(int j =0;j<arrQuestion.size();j++){
                      if(arrQuestion.at(j).getId()== idquestion){
                           check2=1;
                           break;
                       }
                       }
                }
                 if(check0 == 0){
                                    vector<Passage>passages;
                                    Passage passage(idpas,istext,token1,content1);
                                    passages.push_back(passage);

                                   vector<QuestionPart7>questions;
                                     QuestionPart7 question(idquestion,contentQuestion,a1,b1,c1,d1,sol1);
                                    questions.push_back(question);

                                    Part7 *part =new Part7(id,explan1,level,time,count_question,passages, questions);
                                    result.push_back(part);
                                    continue;

                                }

                if(check1==0){
                    Passage passage(idpas,istext,token1,content1);
                    vector<Passage>passages = result.at(index)->getPassage();
                    passages.push_back(passage);
                    result.at(index)->setPassages(passages);
                }

                if(check2==0){
                    QuestionPart7 question(idquestion,contentQuestion,a1,b1,c1,d1,sol1);
                    vector<QuestionPart7>questions = result.at(index)->getQuestions();
                    questions.push_back(question);
                    result.at(index)->setQuestions(questions);
                }


         }
        return result;
}


Part7* SqlitePart7::searchPart7Id(int id1){
     stringstream ss2;
     ss2 << id1;
     string id = ss2.str();
     string sql = "select * from part7 , part7_passage , part7_question where (part7.part7_id="+id+ " and part7.part7_id = part7_passage.part7_id  and part7.part7_id = part7_question.part7_id)";
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
         return NULL;
     }
     vector<Part7*>result = funsearchPart() ;
     if(result.size()==0)return NULL;
     else return result.at(0);
}

vector<Part7*>SqlitePart7::randomPart7CountQuestion(int number1 , int countQuestion1){
        stringstream ss2;
         ss2 << number1;
         string number = ss2.str();

         stringstream ss3;
                  ss3 << countQuestion1;
                  string count = ss3.str();

         if(db==NULL){
          vector<Part7*>re;
          return re;
         }
         string sql ="select * from part7 , part7_passage , part7_question where (part7.part7_id = part7_passage.part7_id  and part7.part7_id = part7_question.part7_id and part7.part7_id in (select part7_id from part7 where part7.count_question="+count+"  order by time limit "+number+" ))";

         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part7*>re;
             return re;
         }
        return funsearchPart();
}

vector<Part7*>SqlitePart7::randomPart7(int number1){
        stringstream ss2;
         ss2 << number1;
         string number = ss2.str();

         if(db==NULL){
          vector<Part7*>re;
          return re;
         }
         string sql ="select * from part7 , part7_passage , part7_question where (part7.part7_id = part7_passage.part7_id  and part7.part7_id = part7_question.part7_id and part7.part7_id in (select part7_id from part7  order by time limit "+number+" ))";

         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part7*>re;
             return re;
         }
        return funsearchPart();
}

vector< char *>SqlitePart7::searchAllImagePart7(){
          vector<char *>result;
         if(db==NULL){

          return result;
         }

         string sql ="select part7_passage_token from part7_passage where part7_passage.is_text=0";


         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){

             return result;
         }
          while (sqlite3_step(stmt) == SQLITE_ROW){
                  const void * content = sqlite3_column_blob(stmt,0);
                  char * content1 = decode(content);
                   result.push_back(content1);
          }
        return result;
}

vector<Part7*>SqlitePart7::randomPart7Subject(int subject1 ,int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
    stringstream ss3;
         ss3<<subject1;
         string subject = ss3.str();

         string sql ="select * from part7 , part7_passage , part7_question where (part7.part7_id = part7_passage.part7_id and part7.part7_id = part7_question.part7_id and part7.part7_id in (select part7_id from part7 where part7.part7_id in(select part7_id from part7_subject where part7_subject.subject_id="+subject+" ) order by time limit "+number+"))";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part7*>re;
             /*
             const char * xx = sqlite3_errmsg(this->db);
             char * x = new char[strlen(xx)+1];
             for(int i=0 ; i<strlen(xx)+1;i++){
                x[i]=xx[i];
             }
             Part1 * p = new Part1(1,x,(const char *)" ",(const char *)" ",(const char *)" ",(const char *)" ",(const char *)" ",0,0);
             re.push_back(p);
             */
             return re;
         }
        return funsearchPart();
}

vector<Part7*>SqlitePart7::searchPart7Favorite(){


         string sql = "select * from part7 , part7_passage , part7_question where (part7.part7_id = part7_passage.part7_id and part7.part7_id = part7_question.part7_id and part7.part7_id in (select id from part7_favorite))";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part7*>re;
             /*
             const char * xx = sqlite3_errmsg(this->db);
             char * x = new char[strlen(xx)+1];
             for(int i=0 ; i<strlen(xx)+1;i++){
                x[i]=xx[i];
             }
             Part1 * p = new Part1(1,x,(const char *)" ",(const char *)" ",(const char *)" ",(const char *)" ",(const char *)" ",0,0);
             re.push_back(p);
             */
             return re;
         }
        return funsearchPart();
}


vector<Part7*>SqlitePart7::searchPart7Check(){
         string sql = "select * from part7 , part7_passage , part7_question where (part7.part7_id = part7_passage.part7_id and part7.part7_id = part7_question.part7_id and part7.part7_id in (select id from part7_checked))";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part7*>re;
             return re;
         }
        return funsearchPart();
}

vector<Part7*>SqlitePart7::searchAllPartImage(){
         string sql = " select * from part7 , part7_passage , part7_question where (part7.part7_id = part7_passage.part7_id and part7.part7_id = part7_question.part7_id and part7_passage.is_text=0)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part7*>re;
             return re;
         }
        return funsearchPart();
}
