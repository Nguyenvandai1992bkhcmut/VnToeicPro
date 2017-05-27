//
// Created by dai nguyen on 5/27/17.
//

#include "SqlitePart1.h"
#include <sstream>

SqliteWord::SqliteWord(){
    sqlite3_open_v2("data/data/com.vntoeic.bkteam.vntoeicpro/databases/database.db", &(this->db),SQLITE_OPEN_READWRITE,NULL);
}

