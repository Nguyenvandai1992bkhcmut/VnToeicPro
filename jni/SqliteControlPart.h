//
// Created by dai nguyen on 5/28/17.
//

#ifndef VNTOEICPRO_SQLITECONTROLPART_H
#define VNTOEICPRO_SQLITECONTROLPART_H

#include <stdio.h>
#include <string>
#include <sqlite3.h>
#include <iostream>
#include <vector>
#include "model.cpp"
#include "edcode.hpp"
using namespace std;

class SqliteControlPart{
    sqlite3* db;
    sqlite3_stmt * stmt;
public:

    SqliteControlPart();

    vector<ModelFavoritePart>searchAllFavoritePart(int part);
    vector<ModelCheckPart>searchAllCheckedPard(int part);

    bool checkPartFavorite(int part, int id);

    void insertPartFavorite(int part, ModelFavoritePart * data);
    void insertPartCheck(int part, ModelCheckPart * data);
    void deletePartFavorite(int part, int id);
     void  deletePartCheck(int part, int id);

    vector<Word*>funSeachWord();
     vector<Word *> searchWordPart(int part, int idpart);

      vector<Word *> searchWordPartAware(int part,int aware, int idpart);
     vector<PartSubject>searchPartSubject(int part);
     vector<Grammar>searchAllGrammar();
    Grammar* searchGrammarId(int idgrammar);
    void updateWordAware(int idword, int aware);
vector<PartSubjectResult>searhPartSubjectResult(int part);

  bool  updateTimePart(int part , int id);
};

#endif //VNTOEICPRO_SQLITECONTROLPART_H
