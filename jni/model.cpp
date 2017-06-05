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
<<<<<<< HEAD
#include <vector>
=======
#include<vector>
>>>>>>> vocabularry

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
<<<<<<< HEAD
        const char *  title ;
        const char *  content;
    
    public :
    Grammar(int id, const char * title , const char * content){
=======
        char *  title ;
        char *  content;
    
    public :
    Grammar(int id, char * title , char * content){
>>>>>>> vocabularry
        this ->id = id;
        this ->title = title;
        this ->content = content;
    };
    //get
    int getId(){
        return this->id;
    }
    
<<<<<<< HEAD
    const char* getTitle(){
        return this ->title;
    }
    
   const  char * getContent(){
=======
    char* getTitle(){
        return this ->title;
    }
    
    char * getContent(){
>>>>>>> vocabularry
        return this->content;
    }
    //set
    void setId(int id){
        this->id = id;
    }
    void setTitle(char * title){
<<<<<<< HEAD

        this ->title = title;
    }
    void setContent(char * content){

        this ->content = content;
    }
=======
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
>>>>>>> vocabularry
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
<<<<<<< HEAD
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
=======
    char * img;
    char * img_token;
    char * audio;
    char * audio_token;
    char * a_script;
    char * b_script;
    char * c_script;
    char * d_script;
    string sol;
    int level;
public:
    Part1(int id, char * img, char *img_token, char * audio, char * audio_token, char * a_sc, char * b_sc, char * c_sc, char * d_sc, string sol, int level){
        this->id = id;
        this->img= img;
        this->img_token = img_token;
        this->audio= audio;
        this->audio_token = audio_token;
>>>>>>> vocabularry
        this->a_script= a_sc;
        this->b_script= b_sc;
        this->c_script = c_sc;
        this->d_script=d_sc;
        this->sol = sol;
        this->level = level;
<<<<<<< HEAD
        this->time = time;
=======
>>>>>>> vocabularry
    }
    //get
    int getId(){
        return this->id;
    }
    
<<<<<<< HEAD
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
=======
    char * getImg(){
        return this->img;
    }
    
    char* getImgToken(){
        return this->img_token;
    }
    
    char*getAudio(){
        return this->audio;
    }
    
    char*getAudioToken(){
        return this->audio_token;
    }
    
    char* getA_script(){
        return this->a_script;
    }
    
    char* getB_script(){
        return this->b_script;
    }
    
    char* getC_script(){
        return this->c_script;
    }
    
    char * getD_script(){
        return this->d_script;
    }
    
    string getSol(){
>>>>>>> vocabularry
        return sol;
    }
    
    int getLevel(){
        return level;
    }
<<<<<<< HEAD

    int getTime(){
        return this->time;
    }
    //set

};

class Part5{
    private:
        int id;
        const char * question;
        const char * a;
        const char * b;
        const char * c;
        const char * d;
        const char * sol;
        const char * explan;
        int level;
        int time;
    public :
        Part5(int id, const char * question, const char * a, const char * b,const char * c, const char *d, const char * sol, const char * explan , int level , int time){
            this ->id = id;
            this ->question = question;
            this->a = a;
            this->b = b;
            this ->c = c;
            this->d = d;
            this->sol = sol;
            this->explan = explan;
            this->level = level;
            this->time = time;
        }

        //get
        int getId(){
            return this ->id;
        }
        const char * getQuestion(){
            return this->question;
        }
        const char * getA(){
            return this->a;
        }
        const char * getB(){
            return this->b;

        }
        const char * getC(){
            return this->c;
        }
        const char * getD(){
            return this->d;
        }
        const char * getSol(){
            return this->sol;
        }
        const char * getExplan(){
           return this->explan;
        }
        int getLevel(){
            return this->level;
        }
        int getTime(){
            return this->time;
        }
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
=======
    
    //set
    
    //de
    ~Part1(){
        if(img!= NULL){
            delete [] img;
            img= NULL;
        }
        
        if(img_token!= NULL){
            delete [] img_token;
            img_token= NULL;
        }
        
        if(audio!= NULL){
            delete [] audio;
            audio = NULL;
        }
        
        if(audio_token!= NULL){
            delete [] audio_token;
            audio_token = NULL;
        }
        
        if(a_script!= NULL){
            delete [] a_script;
            a_script= NULL;
        }
        
        if(b_script!= NULL){
            delete [] b_script;
            b_script= NULL;
        }
        
        if(c_script!= NULL){
            delete [] c_script;
            c_script= NULL;
        }
        
        if(d_script!= NULL){
            delete [] d_script;
            d_script= NULL;
        }
    }
>>>>>>> vocabularry
};

class PartSubject{
private:
<<<<<<< HEAD

    int part_id;
    const char* part_subject;
    
public:
    PartSubject(int part_id, const char * part_subject){
        this->part_id = part_id;
        this->part_subject = part_subject;
    }

    int getPartId(){
        return part_id;
    }
    const char * getPartSubject(){
=======
    
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
>>>>>>> vocabularry
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

class PartSubjectResult{
   private:
    int id;
     const char * title;
     int correct;
     int count;
     public :
     PartSubjectResult(int id, const char * title, int correct, int count){
        this->id = id;
        this->title = title;
        this->correct = correct;
        this->count = count;
     }

     // get
     int getId(){
            return this->id;
     }

     const char * getTitle(){
        return this->title;
     }

     int getCorrect(){
        return this->correct;
     }

     int getCount(){
        return this->count;
     }

};

#endif /* model_hpp */



















































































