//
//  SqliteDictionary.hpp
//  vntoeic
//
//  Created by dai nguyen on 5/15/17.
//  Copyright © 2017 dai nguyen. All rights reserved.
//

#ifndef SqliteDictionary_hpp
#define SqliteDictionary_hpp

#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqliteDictionary{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:
    SqliteDictionary();
    vector<Dictionary> searchAll(const char * s);
    vector<DictionaryFavorite> searchFavorite();
    Dictionary * searchId(int id);
    void insertFavorite(DictionaryFavorite dic);
};
#endif /* SqliteDictionary_hpp */
