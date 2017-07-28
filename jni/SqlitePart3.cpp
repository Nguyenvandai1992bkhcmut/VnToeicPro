//
// Created by dai nguyen on 6/15/17.
//

#include "SqlitePart3.h"
#include <sstream>

SqlitePart3::SqlitePart3(){
    sqlite3_open_v2("/data/user/0/com.vntoeic.bkteam.vntoeicpro/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);
}

vector<Part3*>SqlitePart3::funsearchPart(){
     vector<Part3*>result;
        while (sqlite3_step(stmt) == SQLITE_ROW){
                int id = sqlite3_column_int(stmt,0);
                const void * token = sqlite3_column_blob(stmt,1);
                const void * script = sqlite3_column_blob(stmt,2);

                char * token1 = decode(token);
                char * script1= decode(script);


                //
                //quesiton 1
                //

                const char * tg =(const char*) sqlite3_column_text(stmt,3);
                char * q1= new char[strlen(tg)+1];
                for(int i=0;i<strlen(tg)+1;i++){
                    q1[i]=tg[i];
                }

                 const char *tg1 =(const char*) sqlite3_column_text(stmt,4);
                  char * a1= new char[strlen(tg1)+1];
                  for(int i=0;i<strlen(tg1)+1;i++){
                        a1[i]=tg1[i];
                   }


                 const char * tg2 =(const char*) sqlite3_column_text(stmt,5);
                 char * b1= new char[strlen(tg2)+1];
                 for(int i=0;i<strlen(tg2)+1;i++){
                        b1[i]=tg2[i];
                 }

                const char *tg3 =(const char*) sqlite3_column_text(stmt,6);
                 char * c1= new char[strlen(tg3)+1];
                 for(int i=0;i<strlen(tg3)+1;i++){
                        c1[i]=tg3[i];
                 }

                const char *tg4 =(const char*) sqlite3_column_text(stmt,7);
                 char * d1= new char[strlen(tg4)+1];
                 for(int i=0;i<strlen(tg4)+1;i++){
                        d1[i]=tg4[i];
                 }

                const char * tg5 =(const char*) sqlite3_column_text(stmt,8);
                 char * sol1= new char[strlen(tg5)+1];
                 for(int i=0;i<strlen(tg5)+1;i++){
                        sol1[i]=tg5[i];
                 }
                 //
                //question 2
                //

                const char *tg6 =(const char*) sqlite3_column_text(stmt,9);
                 char * q2= new char[strlen(tg6)+1];
                 for(int i=0;i<strlen(tg6)+1;i++){
                        q2[i]=tg6[i];
                 }

               const char * tg7 =(const char*) sqlite3_column_text(stmt,10);
                 char * a2= new char[strlen(tg7)+1];
                 for(int i=0;i<strlen(tg7)+1;i++){
                        a2[i]=tg7[i];
                 }
                 const char *tg8 =(const char*) sqlite3_column_text(stmt,11);
                 char * b2= new char[strlen(tg8)+1];
                 for(int i=0;i<strlen(tg8)+1;i++){
                        b2[i]=tg8[i];
                 }
                  const char * tg9 =(const char*) sqlite3_column_text(stmt,12);
                 char * c2= new char[strlen(tg9)+1];
                 for(int i=0;i<strlen(tg9)+1;i++){
                        c2[i]=tg9[i];
                 }

                const char *tg10 =(const char*) sqlite3_column_text(stmt,13);
                 char * d2= new char[strlen(tg10)+1];
                 for(int i=0;i<strlen(tg10)+1;i++){
                        d2[i]=tg10[i];
                 }

                const char *tg11 =(const char*) sqlite3_column_text(stmt,14);
                 char * sol2= new char[strlen(tg11)+1];
                 for(int i=0;i<strlen(tg11)+1;i++){
                        sol2[i]=tg11[i];
                 }


                 // question 3


                const char *tg12 =(const char*) sqlite3_column_text(stmt,15);
                 char * q3= new char[strlen(tg12)+1];
                 for(int i=0;i<strlen(tg12)+1;i++){
                        q3[i]=tg12[i];
                 }

                const char *tg13 =(const char*) sqlite3_column_text(stmt,16);
                 char * a3= new char[strlen(tg13)+1];
                 for(int i=0;i<strlen(tg13)+1;i++){
                        a3[i]=tg13[i];
                 }

                  const char * tg14 =(const char*) sqlite3_column_text(stmt,17);
                 char * b3= new char[strlen(tg14)+1];
                 for(int i=0;i<strlen(tg14)+1;i++){
                        b3[i]=tg14[i];
                 }

                const char *tg15 =(const char*) sqlite3_column_text(stmt,18);
                 char * c3= new char[strlen(tg15)+1];
                 for(int i=0;i<strlen(tg15)+1;i++){
                        c3[i]=tg15[i];
                 }

                const char *tg16 =(const char*) sqlite3_column_text(stmt,19);
                 char * d3= new char[strlen(tg16)+1];
                 for(int i=0;i<strlen(tg16)+1;i++){
                        d3[i]=tg16[i];
                 }

                const char *tg17 =(const char*) sqlite3_column_text(stmt,20);
                 char * sol3= new char[strlen(tg17)+1];
                 for(int i=0;i<strlen(tg17)+1;i++){
                        sol3[i]=tg17[i];
                 }
              //
               // question 3
              //

                int level = sqlite3_column_int(stmt,21);
                int time = sqlite3_column_int(stmt,22);
                Part3 *part3= new Part3(id,token1,script1,q1,a1,b1,c1,d1,sol1,q2,a2,b2,c2,d2,sol2,q3,a3,b3,c3,d3,sol3,level,time);
                result.push_back(part3);
         }
        return result;
}


Part3* SqlitePart3::searchPart3Id(int id1){
     stringstream ss2;
     ss2 << id1;
     string id = ss2.str();
     string sql = "select * from part3 where part3_id="+id;
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
         return NULL;
     }
     vector<Part3*>result = funsearchPart() ;
     if(result.size()==0)return NULL;
     else return result.at(0);
}

vector<Part3*>SqlitePart3::randomPart3(int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
         if(db==NULL){
          vector<Part3*>re;
          return re;
         }
         string sql = "select * from part3 order by `time` limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part3*>re;
             return re;
         }
        return funsearchPart();
}

vector<Part3*>SqlitePart3::randomPart3Subject(int subject1 ,int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
    stringstream ss3;
         ss3<<subject1;
         string subject = ss3.str();

         string sql = "select * from part3  where( part3_id in ( select part3_subject.part3_id from part3_subject where part3_subject.subject_id="+subject+")) order by `time` asc limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part3*>re;
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

vector<Part3*>SqlitePart3::searchPart3Favorite(){


         string sql = "select * from part3 where part3_id  in (select id from part3_favorite)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part3*>re;
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

vector<Part3*>SqlitePart3::searchPart3Check(){


         string sql = "select * from part3 where part3_id  in (select id from part3_checked)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part3*>re;
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

