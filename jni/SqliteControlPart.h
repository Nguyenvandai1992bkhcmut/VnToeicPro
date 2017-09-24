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
        vector<ModelCheckPart>searchAllCheckPartId(int part, int question);

    bool checkPartFavorite(int part, int id);

    void insertPartFavorite(int part, ModelFavoritePart * data);
    void insertPartCheck(int part, ModelCheckPart * data);
    void deletePartFavorite(int part, int id);
    void deletePartFavoriteIdTime(int part, int id,const char * time);
     void  deletePartCheck(int part, int id);

          void  deletePartCheckIdTime(int part, int id,const char * time);

    vector<Word*>funSeachWord();
     vector<Word *> searchWordPart(int part, int idpart);

      vector<Word *> searchWordPartAware(int part,int aware, int idpart);
     vector<PartSubject>searchPartSubject(int part);
     vector<Grammar>searchAllGrammar();
    Grammar* searchGrammarId(int idgrammar);
    void updateWordAware(int idword, int aware);
<<<<<<< HEAD
vector<PartSubjectResult>searhPartSubjectResult(int part);

  bool  updateTimePart(int part , int id);
=======
    vector<PartSubjectResult>searhPartSubjectResult(int part);

     bool  updateTimePart(int part , int id);

     vector<PostTag>searchAllPostTag();
     bool updatePostTagUse(int id);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
};

#endif //VNTOEICPRO_SQLITECONTROLPART_H
