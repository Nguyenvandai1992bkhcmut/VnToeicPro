#include "SqlitePart6.h"
#include <sstream>

SqlitePart6::SqlitePart6(){
    sqlite3_open_v2("/data/user/0/com.vntoeic.bkteam.vntoeicpro/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);
}

vector<Part6*>SqlitePart6::funsearchPart(){
     vector<Part6*>result;
        while (sqlite3_step(stmt) == SQLITE_ROW){
                int id = sqlite3_column_int(stmt,0);
<<<<<<< HEAD
                const void * content = sqlite3_column_blob(stmt,1);


                char * content1 = decode(content);


=======


              const void * token_ = sqlite3_column_blob(stmt,1);


                char * token = decode(token_);

                    const void * content = sqlite3_column_blob(stmt,2);


                                char * content1 = decode(content);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                //
                //quesiton 1
                //



<<<<<<< HEAD
                                 const char *tg1 =(const char*) sqlite3_column_text(stmt,2);
=======
                                 const char *tg1 =(const char*) sqlite3_column_text(stmt,3);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                  char * a1= new char[strlen(tg1)+1];
                                  for(int i=0;i<strlen(tg1)+1;i++){
                                        a1[i]=tg1[i];
                                   }


<<<<<<< HEAD
                                 const char * tg2 =(const char*) sqlite3_column_text(stmt,3);
=======
                                 const char * tg2 =(const char*) sqlite3_column_text(stmt,4);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * b1= new char[strlen(tg2)+1];
                                 for(int i=0;i<strlen(tg2)+1;i++){
                                        b1[i]=tg2[i];
                                 }

<<<<<<< HEAD
                                const char *tg3 =(const char*) sqlite3_column_text(stmt,4);
=======
                                const char *tg3 =(const char*) sqlite3_column_text(stmt,5);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * c1= new char[strlen(tg3)+1];
                                 for(int i=0;i<strlen(tg3)+1;i++){
                                        c1[i]=tg3[i];
                                 }

<<<<<<< HEAD
                                const char *tg4 =(const char*) sqlite3_column_text(stmt,5);
=======
                                const char *tg4 =(const char*) sqlite3_column_text(stmt,6);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * d1= new char[strlen(tg4)+1];
                                 for(int i=0;i<strlen(tg4)+1;i++){
                                        d1[i]=tg4[i];
                                 }

<<<<<<< HEAD
                                const char * tg5 =(const char*) sqlite3_column_text(stmt,6);
=======
                                const char * tg5 =(const char*) sqlite3_column_text(stmt,7);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * sol1= new char[strlen(tg5)+1];
                                 for(int i=0;i<strlen(tg5)+1;i++){
                                        sol1[i]=tg5[i];
                                 }
                                 //
                                //question 2
                                //

<<<<<<< HEAD
                               const char * tg7 =(const char*) sqlite3_column_text(stmt,7);
=======
                               const char * tg7 =(const char*) sqlite3_column_text(stmt,8);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * a2= new char[strlen(tg7)+1];
                                 for(int i=0;i<strlen(tg7)+1;i++){
                                        a2[i]=tg7[i];
                                 }
<<<<<<< HEAD
                                 const char *tg8 =(const char*) sqlite3_column_text(stmt,8);
=======
                                 const char *tg8 =(const char*) sqlite3_column_text(stmt,9);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * b2= new char[strlen(tg8)+1];
                                 for(int i=0;i<strlen(tg8)+1;i++){
                                        b2[i]=tg8[i];
                                 }
<<<<<<< HEAD
                                  const char * tg9 =(const char*) sqlite3_column_text(stmt,9);
=======
                                  const char * tg9 =(const char*) sqlite3_column_text(stmt,10);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * c2= new char[strlen(tg9)+1];
                                 for(int i=0;i<strlen(tg9)+1;i++){
                                        c2[i]=tg9[i];
                                 }

<<<<<<< HEAD
                                const char *tg10 =(const char*) sqlite3_column_text(stmt,10);
=======
                                const char *tg10 =(const char*) sqlite3_column_text(stmt,11);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * d2= new char[strlen(tg10)+1];
                                 for(int i=0;i<strlen(tg10)+1;i++){
                                        d2[i]=tg10[i];
                                 }

<<<<<<< HEAD
                                const char *tg11 =(const char*) sqlite3_column_text(stmt,11);
=======
                                const char *tg11 =(const char*) sqlite3_column_text(stmt,12);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * sol2= new char[strlen(tg11)+1];
                                 for(int i=0;i<strlen(tg11)+1;i++){
                                        sol2[i]=tg11[i];
                                 }


                                 // question 3


<<<<<<< HEAD
                                const char *tg13 =(const char*) sqlite3_column_text(stmt,12);
=======
                                const char *tg13 =(const char*) sqlite3_column_text(stmt,13);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * a3= new char[strlen(tg13)+1];
                                 for(int i=0;i<strlen(tg13)+1;i++){
                                        a3[i]=tg13[i];
                                 }

<<<<<<< HEAD
                                  const char * tg14 =(const char*) sqlite3_column_text(stmt,13);
=======
                                  const char * tg14 =(const char*) sqlite3_column_text(stmt,14);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * b3= new char[strlen(tg14)+1];
                                 for(int i=0;i<strlen(tg14)+1;i++){
                                        b3[i]=tg14[i];
                                 }

<<<<<<< HEAD
                                const char *tg15 =(const char*) sqlite3_column_text(stmt,14);
=======
                                const char *tg15 =(const char*) sqlite3_column_text(stmt,15);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * c3= new char[strlen(tg15)+1];
                                 for(int i=0;i<strlen(tg15)+1;i++){
                                        c3[i]=tg15[i];
                                 }

<<<<<<< HEAD
                                const char *tg16 =(const char*) sqlite3_column_text(stmt,15);
=======
                                const char *tg16 =(const char*) sqlite3_column_text(stmt,16);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * d3= new char[strlen(tg16)+1];
                                 for(int i=0;i<strlen(tg16)+1;i++){
                                        d3[i]=tg16[i];
                                 }

<<<<<<< HEAD
                                const char *tg17 =(const char*) sqlite3_column_text(stmt,16);
=======
                                const char *tg17 =(const char*) sqlite3_column_text(stmt,17);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                                 char * sol3= new char[strlen(tg17)+1];
                                 for(int i=0;i<strlen(tg17)+1;i++){
                                        sol3[i]=tg17[i];
                                 }

<<<<<<< HEAD
                  const char *tg18 =(const char*) sqlite3_column_blob(stmt,17);
=======
                  const char *tg18 =(const char*) sqlite3_column_blob(stmt,18);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                   char * explan= decode(tg18);



<<<<<<< HEAD
                int level = sqlite3_column_int(stmt,18);
                int time = sqlite3_column_int(stmt,19);
                Part6 *part6= new Part6(id,content1,a1,b1,c1,d1,sol1,a2,b2,c2,d2,sol2,a3,b3,c3,d3,sol3,explan,level,time);
=======
                int level = sqlite3_column_int(stmt,19);
                int time = sqlite3_column_int(stmt,20);
                Part6 *part6= new Part6(id,token,content1,a1,b1,c1,d1,sol1,a2,b2,c2,d2,sol2,a3,b3,c3,d3,sol3,explan,level,time);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
                result.push_back(part6);
         }
        return result;
}


Part6* SqlitePart6::searchPart6Id(int id1){
     stringstream ss2;
     ss2 << id1;
     string id = ss2.str();
     string sql = "select * from part6 where part6_id="+id;
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
         return NULL;
     }
     vector<Part6*>result = funsearchPart() ;
     if(result.size()==0)return NULL;
     else return result.at(0);
}

vector<Part6*>SqlitePart6::randomPart6(int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
         if(db==NULL){
          vector<Part6*>re;
          return re;
         }
         string sql = "select * from part6 order by `time` limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part6*>re;
             return re;
         }
        return funsearchPart();
}

vector<Part6*>SqlitePart6::randomPart6Subject(int subject1 ,int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
    stringstream ss3;
         ss3<<subject1;
         string subject = ss3.str();

         string sql = "select * from part6  where( part6_id in ( select part6_subject.part6_id from part6_subject where part6_subject.subject_id="+subject+")) order by `time` asc limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part6*>re;
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

vector<Part6*>SqlitePart6::searchPart6Favorite(){


         string sql = "select * from part6 where part6_id  in (select id from part6_favorite)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part6*>re;
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


vector<Part6*>SqlitePart6::searchPart6Check(){
         string sql = "select * from part6 where part6_id in (select id from part6_checked)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part6*>re;
             return re;
         }
        return funsearchPart();
}
