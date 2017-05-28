//
// Created by dai nguyen on 5/27/17.
//

#include "SqlitePart1.h"
#include <sstream>

SqlitePart1::SqlitePart1(){
    sqlite3_open_v2("data/data/com.vntoeic.bkteam.vntoeicpro/databases/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);
}
vector<Part1*>SqlitePart1::funsearchPart(){
    vector<Part1*>result;
    while (sqlite3_step(stmt) == SQLITE_ROW){
            int id = sqlite3_column_int(stmt,0);
            const void * token = sqlite3_column_blob(stmt,1);
            const void * asr = sqlite3_column_blob(stmt,2);
            const void * bsr = sqlite3_column_blob(stmt,3);
            const void * csr=   sqlite3_column_blob(stmt,4);
            const void * dsr = sqlite3_column_blob(stmt,5);
            const char * sol =(const char*) sqlite3_column_text(stmt,6);
            int level = sqlite3_column_int(stmt,7);
            int time = sqlite3_column_int(stmt,8);

            char * token1 = decode(token);
            char * asr1= decode(asr);
            char * bsr1= decode(bsr);
            char * csr1= decode(csr);
            char * dsr1= decode(dsr);

            char * sol1= new char[strlen(sol)+1];
            for(int i=0;i<strlen(sol)+1;i++){
                sol1[i]=sol[i];
            }
            Part1 *part1= new Part1(id,token1,asr1,bsr1,csr1,dsr1,sol1,level,time);
            result.push_back(part1);
     }
     return result;
}


Part1* SqlitePart1::searchPart1Id(int id1){
     stringstream ss2;
     ss2 << id1;
     string id = ss2.str();
     string sql = "select * from part1 where part1_id="+id;
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
         return NULL;
     }
     vector<Part1*>result = funsearchPart() ;
     if(result.size()==0)return NULL;
     else return result.at(0);
}

vector<Part1*>SqlitePart1::randomPart1(int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
         string sql = "select * from part1 order by `time` limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part1*>re;
             return re;
         }
        return funsearchPart();
}

vector<Part1*>SqlitePart1::randomPart1Subject(int subject1 ,int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
    stringstream ss3;
         ss3<<subject1;
         string subject = ss3.str();

         string sql = "select * from part1  where( part1_id in ( select part1_subject.part1_id from part1_subject where part1_subject.subject_id="+subject+")) order by `time` asc limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part1*>re;
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




vector<Part1*>SqlitePart1::searchPart1Favorite(){


         string sql = "select * from part1 where part1_id  in (select id from part1_favorite)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part1*>re;
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
