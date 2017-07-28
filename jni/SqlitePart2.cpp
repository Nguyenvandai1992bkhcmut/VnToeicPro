//
// Created by dai nguyen on 6/14/17.
//

#include "SqlitePart2.h"
#include <sstream>

SqlitePart2::SqlitePart2(){

    sqlite3_open_v2("/data/user/0/com.vntoeic.bkteam.vntoeicpro/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);

}


vector<Part2*>SqlitePart2::funsearchPart(){
     vector<Part2*>result;
        while (sqlite3_step(stmt) == SQLITE_ROW){
                int id = sqlite3_column_int(stmt,0);
                const void * token = sqlite3_column_blob(stmt,1);
                char * token1 = decode(token);

               const char * script = (const char *)sqlite3_column_text(stmt,2);
                char *script1= new char[strlen(script)+1];
                for(int i=0;i<strlen(script)+1;i++){
                                    script1[i]=script[i];
                }

                const char * a = (const char *)sqlite3_column_text(stmt,3);
                 char *a1= new char[strlen(a)+1];
                    for(int i=0;i<strlen(a)+1;i++){
                             a1[i]=a[i];
                     }

                const char * b =(const char *) sqlite3_column_text(stmt,4);
                 char * b1= new char[strlen(b)+1];
                    for(int i=0;i<strlen(b)+1;i++){
                             b1[i]=b[i];
                     }

                  const   char * c = (const char *)sqlite3_column_text(stmt,5);
                     char *c1= new char[strlen(c)+1];
                        for(int i=0;i<strlen(c)+1;i++){
                                 c1[i]=c[i];
                         }

                const char * sol =(const char*) sqlite3_column_text(stmt,6);
                int level = sqlite3_column_int(stmt,7);
                int time = sqlite3_column_int(stmt,8);


                char * sol1= new char[strlen(sol)+1];
                for(int i=0;i<strlen(sol)+1;i++){
                    sol1[i]=sol[i];
                }


                Part2 *part2= new Part2(id,token1,script1,a1,b1,c1,sol1,level,time);
                result.push_back(part2);
         }
        return result;
}

Part2* SqlitePart2::searchPart2Id(int id1){
     stringstream ss2;
     ss2 << id1;
     string id = ss2.str();
     string sql = "select * from part2 where part2_id="+id;
     if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
         return NULL;
     }
     vector<Part2*>result = funsearchPart() ;
     if(result.size()==0)return NULL;
     else return result.at(0);
}

vector<Part2*>SqlitePart2::randomPart2(int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
         if(db==NULL){
          vector<Part2*>re;
          return re;
         }
         string sql = "select * from part2 order by `time` limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part2*>re;
             return re;
         }
        return funsearchPart();
}

vector<Part2*>SqlitePart2::randomPart2Subject(int subject1 ,int number1){
    stringstream ss2;
         ss2 << number1;
         string number = ss2.str();
    stringstream ss3;
         ss3<<subject1;
         string subject = ss3.str();

         string sql = "select * from part2  where( part2_id in ( select part2_subject.part2_id from part2_subject where part2_subject.subject_id="+subject+")) order by `time` asc limit "+ number;
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part2*>re;
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

vector<Part2*>SqlitePart2::searchPart2Favorite(){


         string sql = "select * from part2 where part2_id  in (select id from part2_favorite)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part2*>re;
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


vector<Part2*>SqlitePart2::searchPart2Check(){


         string sql = "select * from part2 where part2_id  in (select id from part2_checked)";
         if(sqlite3_prepare_v2((this->db),sql.c_str(), -1, &(this->stmt), NULL) != SQLITE_OK){
             vector<Part2*>re;
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

