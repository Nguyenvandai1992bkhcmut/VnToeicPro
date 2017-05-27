//
// Created by dai nguyen on 5/27/17.
//

#ifndef VNTOEICPRO_SQLITEPART1_H
#define VNTOEICPRO_SQLITEPART1_H

#endif //VNTOEICPRO_SQLITEPART1_H

#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqlitePart1{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:
    SqlitePart1();


};
#endif /* SqliteDictionary_hpp */
