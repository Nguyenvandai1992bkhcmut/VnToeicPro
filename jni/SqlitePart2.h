//
// Created by dai nguyen on 6/14/17.
//

#ifndef VNTOEICPRO_SQLITEPART2_H
#define VNTOEICPRO_SQLITEPART2_H


#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqlitePart2{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:

    SqlitePart2();

    vector<Part2*>funsearchPart();
    Part2 *searchPart2Id(int id);
    vector<Part2*>randomPart2(int number);
    vector<Part2*>randomPart2Subject(int subject,int number);
    vector<Part2*>searchPart2Favorite();
<<<<<<< HEAD
=======
    vector<Part2*>searchPart2Check();
>>>>>>> master

};
#endif //VNTOEICPRO_SQLITEPART2_H
