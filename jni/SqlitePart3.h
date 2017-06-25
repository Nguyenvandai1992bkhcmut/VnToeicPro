//
// Created by dai nguyen on 6/15/17.
//

#ifndef VNTOEICPRO_SQLITEPART3_H
#define VNTOEICPRO_SQLITEPART3_H
#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqlitePart3{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:

    SqlitePart3();

    vector<Part3*>funsearchPart();
    Part3 *searchPart3Id(int id);
    vector<Part3*>randomPart3(int number);
    vector<Part3*>randomPart3Subject(int subject,int number);
    vector<Part3*>searchPart3Favorite();
};
#endif //VNTOEICPRO_SQLITEPART3_H
