//
// Created by dai nguyen on 7/5/17.
//

#ifndef VNTOEICPRO_SQLITEPART7_H
#define VNTOEICPRO_SQLITEPART7_H
#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqlitePart7{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:

    SqlitePart7();

       vector<Part7*>funsearchPart();
       Part7 *searchPart7Id(int id);
       vector<Part7*>randomPart7(int number);
       vector< TokenPart7*>searchAllImagePart7();
       vector<Part7*>randomPart7CountQuestion(int number, int countQuestion);
       vector<Part7*>searchAllPartImage();
       vector<Part7*>randomPart7Subject(int subject,int number);
       vector<Part7*>searchPart7Favorite();
       vector<Part7*>searchPart7Check();
};
#endif //VNTOEICPRO_SQLITEPART7_H
