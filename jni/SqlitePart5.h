//
// Created by dai nguyen on 5/30/17.
//

#ifndef VNTOEICPRO_SQLITEPART5_H
#define VNTOEICPRO_SQLITEPART5_H


#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqlitePart5{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:

    SqlitePart5();
    vector<Part5*>funsearchPart();
    vector<Part5*>randomPart5(int number);
    vector<Part5*>randomPart5Subject(int subject,int number);
        vector<Part5*>searchPart5Favorite();

};

#endif //VNTOEICPRO_SQLITEPART5_H
