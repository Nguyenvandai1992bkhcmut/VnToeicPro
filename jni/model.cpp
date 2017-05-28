//
//  model.hpp
//  vntoeic
//
//  Created by dai nguyen on 5/15/17.
//  Copyright © 2017 dai nguyen. All rights reserved.
//

#ifndef model_hpp
#define model_hpp

#include <stdio.h>
#include <string>
#include<vector>

using namespace std;


//
class Dictionary{
private:
    int id ;
    char *name;
    char * content;
public:
Dictionary(int id , char* name,  char* content){
        this ->id= id ;
        this ->name = name ;
        this ->content = content;
    }
    
    int getId(){
        return this->id;
    }
    
    char* getName(){
        return this->name;
    }
    
    char* getContent(){
        return this ->content;
    }
    
    void setId(int id){
        this->id = id;
    }
    
    void setName(char* name){
        this ->name = name;
    }
    
    void setContent( char* content){
        this ->content = content;
    }

};

class DictionaryFavorite{
private:
    int id;
    const char * time;
    const char * meaning;
public :
    DictionaryFavorite(int id,const  char * time ,const char * meaning){
        this->id = id ;
        this->time = time;
        this ->meaning = meaning;
    }

    //get
    int getId(){
        return this->id;
    }

    const char * getTime(){
        return this->time;
    }

    const char * getMeaning(){
        return this ->meaning;
    }
};


// word

class Section{
    private:
        int id;
        const char * title;
    public:
        Section(int id, const char * title){
            this->id = id;
            this->title= title;
        }

        //get

        int getId(){
            return this->id;
        }

        const char* getTitle(){
             return this->title;
        }

};

class WordTag{
    private:
        int id_tag;
        int id_section;
        const char * title;
        const char * token;

    public:
        WordTag(int id, int id1 , const char * title , const char* token){
            this->id_tag = id;
            this->id_section = id1;
            this->title = title;
            this->token = token ;
        }

        //get

        int getIdTag(){
            return this->id_tag;
        }

        int getIdSection(){
            return this->id_section;
        }

        const char * getTitle(){
            return this->title;
        }

        const char * getToken(){
            return this->token;
        }
};

class LessonTag{
    private:
        int lesson_tag_id;
        int w_tag_id;
        const char * title;

    public:
        LessonTag(int idles,  int idw , const char * title){
            this->lesson_tag_id = idles;
            this->w_tag_id = idw;
            this->title = title;
        }

        //get
        int getLessonTagId(){
              return this->lesson_tag_id;
        }

        int getWTagId(){
            return this->w_tag_id;
        }

        const char* getTitle(){
            return this->title;
        }
};

class Word{
    private :
        int id;
        const char * word;
        const char * pronounce;
        const char * example;
        vector<const char *>meaning;
        vector<const char *>type;
        vector<const char *>explan;
        vector<const char *>similar;

    public:
        Word(int id, const char * word, const char * pro , const char * example , vector<const char * >meaning , vector<const char *>type , vector<const char *>explan,vector<const char *>similar){
               this->id = id;
                this->word = word;
                this->pronounce = pro;
                this->example = example;
                this->meaning = meaning;
                this->type =type ;
                this->explan = explan;
                this->similar = similar;
        }

         //get
         int getId(){
            return this->id;
         }

         const char *getWord(){
            return this->word;
         }

         const char * getPronounce(){
              return this->pronounce;
         }

         const char * getExample(){
               return this->example;
         }

         vector<const char *>getMeaning(){
                return this->meaning;
         }

         vector<const char *>getType(){
                return this->type;
           }
         vector<const char *>getExplan(){
                return this->explan;
         }
         vector<const char *>getSimilar(){
                return this->similar;
          }

          void setMeaning(vector<const char *>meaning){
            this->meaning= meaning;
          }

          void setExplan(vector<const char *>explan){
             this->explan= meaning;
          }
          void setType(vector<const char *>type){
                      this->type= type;
                    }
         void setSimilar(vector<const char *>similar){
                 this->similar= meaning;
           }
};
// Model Favorite Word

class ModelFavoriteWord{
    private:
        int id;
        const char *date;

    public:
        ModelFavoriteWord(int id , const char * date){
            this->id= id;
            this->date = date;
        }

        //get

        int getId(){
            return this->id;
        }

        const char* getDate(){
            return this->date;
        }
};
// model wordChecked
class ModelWordChecked{
    private :
        int idword;
        int idlesson;
        int result;
        const char * time;
    public :
        ModelWordChecked(int idword, int idlesson, int result, const char * time){
            this->idword= idword;
            this->idlesson = idlesson;
            this->result= result;
            this->time= time;
        }
        //get

        int getIdWord(){
            return this->idword;
        }

        int getIdLesson(){
            return this->idlesson;
        }

        int getIdResult(){
            return this->result;
        }

        const char * getTime(){
            return this->time;
        }
};
// Model WordLesson

class WordLesson{
    private:
        Word  *word;
        int lessonId;

    public:
        WordLesson( int lessonId,Word *word ){
            this->word = word;
            this->lessonId = lessonId;
        }

        //get
        Word * getWord(){
            return this ->word;
        }

        int getLessonId(){
            return this->lessonId;
        }
};
// grammar

class Grammar{
    private:
        int id ;
        char *  title ;
        char *  content;
    
    public :
    Grammar(int id, char * title , char * content){
        this ->id = id;
        this ->title = title;
        this ->content = content;
    };
    //get
    int getId(){
        return this->id;
    }
    
    char* getTitle(){
        return this ->title;
    }
    
    char * getContent(){
        return this->content;
    }
    //set
    void setId(int id){
        this->id = id;
    }
    void setTitle(char * title){
        if(title!= NULL){
            delete []title;
            title= NULL;
        }
        this ->title = title;
    }
    void setContent(char * content){
        if(content!= NULL){
            delete []content;
            content = NULL;
        }
        this ->content = content;
    }
    ~Grammar(){
        if(title!= NULL){
            delete []title;
            title= NULL;
        }
        if(content!= NULL){
            delete []content;
            content= NULL;
        }
    }
};
//

// Meaning


class Meaning{
private:
    int id_meaning;
    int id_word;
    char * meaning;
    char * meaning_type;
    char * explan;
    char * similar;
    
public:
    Meaning(int id_m, int id_w, char*meaning, char * explan , char * similar){
        this ->id_meaning = id_m;
        this ->id_word = id_w;
        this ->meaning = meaning;
        this ->explan = explan;
        this ->similar = similar;
    };
    
    //get
    
    int getIdMeaning(){
        return id_meaning;
    }
    
    int getIdWord(){
        return id_word;
    }
    
    char* getMeaning(){
        return this->meaning;
    }
    
    char* getMeaningType(){
        return this->meaning_type;
    }
    
    char* getExplan(){
        return this->explan;
    }
    
    char* getSimilar(){
        return this->similar;
    }
    
    //set

};

class PSubject{
private:
    int type;
    int id;
    char* title;
public:
    PSubject(int type, int id, char * title){
        this ->type = type;
        this->id= id;
        this->title= title;
    }
    
    //get
    
    int getType(){
        return type;
    }
    
    int getId(){
        return getId();
    }

    char*getTitle(){
        return title;
    }
    
    //set
    
    //de
    
    ~PSubject(){
        if(this->title==NULL){
            delete []title;
            title= NULL;
        }
    }
    
};

class Part1{
private:
    int id;
    const char * token;
    const char * a_script;
    const char * b_script;
    const char * c_script;
    const char * d_script;
    const char* sol;
    int level;
    int time;
public:
    Part1(int id, const char *token,const char * a_sc,const  char * b_sc,const  char * c_sc,const  char * d_sc,const  char* sol, int level, int time){
        this->id = id;
        this->token = token;
        this->a_script= a_sc;
        this->b_script= b_sc;
        this->c_script = c_sc;
        this->d_script=d_sc;
        this->sol = sol;
        this->level = level;
        this->time = time;
    }
    //get
    int getId(){
        return this->id;
    }
    
    const char * getToken(){
        return this->token;
    }

    const char* getA_script(){
        return this->a_script;
    }
    
    const char* getB_script(){
        return this->b_script;
    }
    
    const char* getC_script(){
        return this->c_script;
    }
    
    const char * getD_script(){
        return this->d_script;
    }
    
    const char * getSol(){
        return sol;
    }
    
    int getLevel(){
        return level;
    }

    int getTime(){
        return this->time;
    }
    //set

};

class ModelFavoritePart{
    private:
        int id;
        const char *date;

    public:
        ModelFavoritePart(int id , const char * date){
            this->id= id;
            this->date = date;
        }

        //get

        int getId(){
            return this->id;
        }

        const char* getDate(){
            return this->date;
        }
};

class ModelCheckPart{
    private:
        int id;
        const char *date;
        int result;

    public:
        ModelCheckPart(int id , const char * date, int result){
            this->id= id;
            this->date = date;
            this->result = result;
        }

        //get

        int getId(){
            return this->id;
        }

        const char* getDate(){
            return this->date;
        }
        int getResult(){
            return this->result;
        }
};

class PartSubject{
private:
    
    int type;
    int part_id;
    int part_subject;
    
public:
    PartSubject(int type, int part_id, int part_subject){
        this->type = type;
        this->part_id = part_id;
        this->part_subject = part_subject;
    }
    
    //get
    
    int getType(){
        return type;
    }
    int getPartId(){
        return part_id;
    }
    int getPartSubject(){
        return part_subject;
    }
};

class PartLearned{
private:
    
    int type;
    int part_id;
    string time;
    int result;
    
public:
    PartLearned(int type, int part_id, string time,int result){
        this->type = type;
        this->part_id = part_id;
        this->time = time;
        this->result = result;
    }
    
    //get
    
    int getType(){
        return type;
    }
    int getPartId(){
        return part_id;
    }
    string getTime(){
        return time;
    }
    int getResult(){
        return result;
    }
    
};


class PartWord{
private:
    int type;
    int part_id;
    int word_id;
    
public:
    PartWord(int type,int part_id, int word_id){
        this->type = type;
        this->part_id =part_id;
        this->word_id = word_id;
    }
    
    // get
    int getType(){
        return type;
    }
    
    int getPartId(){
        return part_id;
    }
    
    int getWordId(){
        return word_id;
    }
};

#endif /* model_hpp */



















































































