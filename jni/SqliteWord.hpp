//
//  SqliteDictionary.hpp
//  vntoeic
//
//  Created by dai nguyen on 5/15/17.
//  Copyright © 2017 dai nguyen. All rights reserved.
//

#ifndef SqliteWord_hpp
#define SqliteWord_hpp

#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqliteWord{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:
    SqliteWord();
    vector<Section>searchAllSection();
    vector<WordTag>searchTaginSection(int idsection);
    vector<LessonTag>searchLessonTag (int idwordtag);
    Word * searchWordId(int id);

    vector<WordLesson>searchWordLesson(int idlesson);

<<<<<<< HEAD
    vector<ModelFavoriteWord>searchFavoriteWord();

    void insertFavoriteWord(ModelFavoriteWord favoriteWord);
=======
    vector<ModelFavoriteWord>searchFavoriteWord(int leesson);

    void insertFavoriteWord(int color,ModelFavoriteWord favoriteWord);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
    vector<ModelWordChecked>searchWordChecked(int flag , int id);

    void insertWordChecked(ModelWordChecked checked);

<<<<<<< HEAD
    bool checkFavotiteWord(int id);

    void deleteWordFavorite(int id);
=======
    int checkFavotiteWord(int id);

    void deleteWordFavorite(int lesson,int id);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

    void deleteWordChecked(int id);

    bool checkWordChecked(int id);
};
#endif /* SqliteDictionary_hpp */
