//
// Created by dai nguyen on 6/16/17.
//

#ifndef VNTOEICPRO_SQLITEPART6_H
#define VNTOEICPRO_SQLITEPART6_H
#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqlitePart6{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:

    SqlitePart6();

       vector<Part6*>funsearchPart();
       Part6 *searchPart6Id(int id);
       vector<Part6*>randomPart6(int number);
       vector<Part6*>randomPart6Subject(int subject,int number);
       vector<Part6*>searchPart6Favorite();
<<<<<<< HEAD
=======
            vector<Part6*>searchPart6Check();
>>>>>>> master
};
#endif //VNTOEICPRO_SQLITEPART6_H
