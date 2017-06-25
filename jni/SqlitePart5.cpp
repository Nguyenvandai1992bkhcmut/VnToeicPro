//
// Created by dai nguyen on 5/30/17.
//

#include "SqlitePart5.h"
#include <sstream>

SqlitePart5::SqlitePart5(){
    sqlite3_open_v2("/data/user/0/com.vntoeic.bkteam.vntoeicpro/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);}

vector<Part5*>SqlitePart5::funsearchPart(){
    vector<Part5*>result;
    while (sqlite3_step(stmt) == SQLITE_ROW){
            int id = sqlite3_column_int(stmt,0);
            const void * question1 = sqlite3_column_blob(stmt,1);
            const void * a1 = sqlite3_column_blob(stmt,2);
            const void * b1 = sqlite3_column_blob(stmt,3);
            const void * c1=   sqlite3_column_blob(stmt,4);
            const void * d1 = sqlite3_column_blob(stmt,5);
            const char * sol =(const char*) sqlite3_column_text(stmt,6);
            const void * explan1 = sqlite3_column_blob(stmt,7);
            int level = sqlite3_column_int(stmt,7);
            int time = sqlite3_column_int(stmt,8);

            char * question = decode(question1);
            char * a= decode(a1);
            char * b= decode(b1);
            char * c= decode(c1);
            char * d= decode(d1);
            char * explan = decode(explan1);

            char * sol1= new char[strlen(sol)+1];
            for(int i=0;i<strlen(sol)+1;i++){
                sol1[i]=sol[i];
            }
            Part5 *part5= new Part5(id,question,a,b,c,d,sol1,explan,level,time);
            result.push_back(part5);
     }
     return result;
}

vector<Part5*>SqlitePart5::randomPart5(int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
         string sql = "select * from part5 order by `time` asc limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part5*>re;
             const char * xx = sqlite3_errmsg(this->db);
                          char * x = new char[strlen(xx)+1];
                          for(int i=0 ; i<strlen(xx)+1;i++){
                             x[i]=xx[i];
                          }
             Part5 *part5= new Part5(1,x,"","","","","","",0,0);
             re.push_back(part5);
             return re;
         }
        return funsearchPart();
}

vector<Part5*>SqlitePart5::randomPart5Subject(int subject1 ,int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
    stringstream ss3;
         ss3<<subject1;
         string subject = ss3.str();

         string sql = "select * from part5  where( part5_id in ( select part5_subject.part5_id from part5_subject where part5_subject.subject_id="+subject+")) order by `time` asc limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part5*>re;
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

vector<Part5*>SqlitePart5::searchPart5Favorite(){


         string sql = "select * from part5 where part5_id  in (select id from part5_favorite)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part5*>re;
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



vector<Part5*>SqlitePart5::searchPart5Check(){
         string sql = "select * from part5, part5_checked where part5.part5_id = part5_checked.id";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part5*>re;
             return re;
         }
        return funsearchPart();
}

vector<Part5*>SqlitePart5::searchPart5Id(int id1){

    stringstream ss2;
         ss2 << id1;
         string id = ss2.str();
         string sql = "select * from part5 where part5_id =" + id;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part5*>re;

             return re;
         }
        return funsearchPart();
}