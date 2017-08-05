//
// Created by dai nguyen on 6/16/17.
//

#ifndef VNTOEICPRO_SQLITEPART4_H
#define VNTOEICPRO_SQLITEPART4_H
#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqlitePart4{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:

    SqlitePart4();

    vector<Part4*>funsearchPart();
    Part4 *searchPart4Id(int id);
    vector<Part4*>randomPart4(int number);
    vector<Part4*>randomPart4Subject(int subject,int number);
    vector<Part4*>searchPart4Favorite();
<<<<<<< HEAD
=======
    vector<Part4*>searchPart4Check();
>>>>>>> master
};
#endif //VNTOEICPRO_SQLITEPART4_H
