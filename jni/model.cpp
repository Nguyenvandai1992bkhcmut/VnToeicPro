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
#include <vector>

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
        const char *  title ;
        const char *  content;

    public :
    Grammar(int id, const char * title , const char * content){
        this ->id = id;
        this ->title = title;
        this ->content = content;
    };
    //get
    int getId(){
        return this->id;
    }

    const char* getTitle(){
        return this ->title;
    }

   const  char * getContent(){
        return this->content;
    }
    //set
    void setId(int id){
        this->id = id;
    }
    void setTitle(char * title){

        this ->title = title;
    }
    void setContent(char * content){

        this ->content = content;
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

class Part2 {
    private:
        int id;
        const char * token;
        const char * script;
        const char * a;
        const char * b;
        const char * c;
        const char * sol;
        int level ;
        int time;
    public :
        Part2(int id, const char * token, const char * script ,const char * a, const char* b, const char *c, const char * sol , int level , int time){
        this->id =id ;
        this->token = token ;
        this->script = script;
        this->sol = sol;
        this->level = level;
        this->a =a;
        this->b= b;
        this->c=c;
        this->time = time;
    }

    //get
    int getId(){
        return this->id ;
    }


    const char * getToken(){
        return this->token;
    }

    const char *getScript(){
        return this->script;
    }

    const char * getA(){
        return this->a;
    }

    const char * getB(){
        return this->b;
    }

    const char *getC(){
        return this->c;
    }

    const char * getSol(){
        return this->sol;
    }

    int getLevel(){
        return this->level;
    }

    int getTime(){
        return this->time;
    }
};

class Part3{
    private:
        int id;
        const char * token;
        const char * script;
        const char * q1;
        const char * a1;
        const char * b1;
        const char * c1;
        const char * d1;
        const char * sol1;

        const char * q2;
        const char * a2;
        const char * b2;
        const char * c2;
        const char * d2;
        const char * sol2;

        const char * q3;
        const char * a3;
        const char * b3;
        const char * c3;
        const char * d3;
        const char * sol3;

        int level;
        int time;

     public:
        Part3(int id , const char * token , const char * script , const char * q1 , const char * a1 , const char * b1, const char * c1, const char *d1 , const char * sol1 , const char * q2 , const char * a2, const char * b2, const char * c2, const char *d2, const char * sol2 , const char* q3, const char * a3, const char * b3, const char * c3, const char * d3, const char * sol3 , int level,  int time ){
            this->id = id;
            this->token = token;
            this->script=  script;

            this->q1= q1;
            this->a1= a1;
            this->b1= b1;
            this->c1= c1;
            this->d1= d1;
            this->sol1 = sol1;

                        this->q2= q2;
                        this->a2= a2;
                        this->b2= b2;
                        this->c2= c2;
                        this->d2= d2;
                        this->sol2 = sol2;

                                    this->q3= q3;
                                    this->a3= a3;
                                    this->b3= b3;
                                    this->c3= c3;
                                    this->d3= d3;
                                    this->sol3 = sol3;

                                           this->time = time ;
                                           this->level = level;
        }

        //get

        int getId(){
            return this->id;
        }

        const char * getToken(){
            return this->token;
        }

        const char * getScript(){
            return this->script;
        }

        const char * getQ1(){
            return this->q1;
        }

        const char * getA1(){
            return this->a1;
        }

        const char * getB1(){
            return this->b1;
        }

        const char * getC1(){
            return this->c1;
        }

        const char * getD1(){
            return this->d1;
        }

        const char * getSol1(){
            return this->sol1;
        }

        const char * getQ2(){
                    return this->q2;
                }

                const char * getA2(){
                    return this->a2;
                }

                const char * getB2(){
                    return this->b2;
                }

                const char * getC2(){
                    return this->c2;
                }

                const char * getD2(){
                    return this->d2;
                }

                const char * getSol2(){
                    return this->sol2;
                }


                const char * getQ3(){
                            return this->q3;
                        }

                        const char * getA3(){
                            return this->a3;
                        }

                        const char * getB3(){
                            return this->b3;
                        }

                        const char * getC3(){
                            return this->c3;
                        }

                        const char * getD3(){
                            return this->d3;
                        }

                        const char * getSol3(){
                            return this->sol3;
                        }

                        int getLevel(){
                            return this->level;
                        }

                        int getTime(){
                            return this->time;
                        }
};

// part 4

class Part4{
    private:
        int id;
        const char * token;
        const char * script;
        const char * q1;
        const char * a1;
        const char * b1;
        const char * c1;
        const char * d1;
        const char * sol1;

        const char * q2;
        const char * a2;
        const char * b2;
        const char * c2;
        const char * d2;
        const char * sol2;

        const char * q3;
        const char * a3;
        const char * b3;
        const char * c3;
        const char * d3;
        const char * sol3;

        int level;
        int time;

     public:
        Part4(int id , const char * token , const char * script , const char * q1 , const char * a1 , const char * b1, const char * c1, const char *d1 , const char * sol1 , const char * q2 , const char * a2, const char * b2, const char * c2, const char *d2, const char * sol2 , const char* q3, const char * a3, const char * b3, const char * c3, const char * d3, const char * sol3 , int level,  int time ){
            this->id = id;
            this->token = token;
            this->script=  script;

            this->q1= q1;
            this->a1= a1;
            this->b1= b1;
            this->c1= c1;
            this->d1= d1;
            this->sol1 = sol1;

                        this->q2= q2;
                        this->a2= a2;
                        this->b2= b2;
                        this->c2= c2;
                        this->d2= d2;
                        this->sol2 = sol2;

                                    this->q3= q3;
                                    this->a3= a3;
                                    this->b3= b3;
                                    this->c3= c3;
                                    this->d3= d3;
                                    this->sol3 = sol3;

                                           this->time = time ;
                                           this->level = level;
        }

        //get

        int getId(){
            return this->id;
        }

        const char * getToken(){
            return this->token;
        }

        const char * getScript(){
            return this->script;
        }

        const char * getQ1(){
            return this->q1;
        }

        const char * getA1(){
            return this->a1;
        }

        const char * getB1(){
            return this->b1;
        }

        const char * getC1(){
            return this->c1;
        }

        const char * getD1(){
            return this->d1;
        }

        const char * getSol1(){
            return this->sol1;
        }

        const char * getQ2(){
                    return this->q2;
                }

                const char * getA2(){
                    return this->a2;
                }

                const char * getB2(){
                    return this->b2;
                }

                const char * getC2(){
                    return this->c2;
                }

                const char * getD2(){
                    return this->d2;
                }

                const char * getSol2(){
                    return this->sol2;
                }


                const char * getQ3(){
                     return this->q3;
                }

                const char * getA3(){
                     return this->a3;
                }

                const char * getB3(){
                     return this->b3;
                }

                        const char * getC3(){
                            return this->c3;
                        }

                        const char * getD3(){
                            return this->d3;
                        }

                        const char * getSol3(){
                            return this->sol3;
                        }

                        int getLevel(){
                            return this->level;
                        }

                        int getTime(){
                            return this->time;
                        }
};

// part 5
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

//
//Part6
//
class Part6{
    private:
        int id;
        const char * content;

        const char * a1;
        const char * b1;
        const char * c1;
        const char * d1;
        const char * sol1;


        const char * a2;
        const char * b2;
        const char * c2;
        const char * d2;
        const char * sol2;


        const char * a3;
        const char * b3;
        const char * c3;
        const char * d3;
        const char * sol3;

        const char * explan;
        int level;
        int time;

     public:
        Part6(int id , const char * content ,
          const char * a1 , const char * b1, const char * c1, const char *d1 , const char * sol1 ,
          const char * a2, const char * b2, const char * c2, const char *d2, const char * sol2 ,
          const char * a3, const char * b3, const char * c3, const char * d3, const char * sol3 ,
         const char * explan, int level,  int time ){


            this->id = id;
            this->content = content;
            this->explan = explan;

            this->a1= a1;
            this->b1= b1;
            this->c1= c1;
            this->d1= d1;
            this->sol1 = sol1;


                        this->a2= a2;
                        this->b2= b2;
                        this->c2= c2;
                        this->d2= d2;
                        this->sol2 = sol2;


                                    this->a3= a3;
                                    this->b3= b3;
                                    this->c3= c3;
                                    this->d3= d3;
                                    this->sol3 = sol3;

                                           this->time = time ;
                                           this->level = level;
        }

        //get

        int getId(){
            return this->id;
        }

        const char * getContent(){
            return this->content;
        }

        const char * getExplan(){
            return this->explan;
        }



        const char * getA1(){
            return this->a1;
        }

        const char * getB1(){
            return this->b1;
        }

        const char * getC1(){
            return this->c1;
        }

        const char * getD1(){
            return this->d1;
        }

        const char * getSol1(){
            return this->sol1;
        }



                const char * getA2(){
                    return this->a2;
                }

                const char * getB2(){
                    return this->b2;
                }

                const char * getC2(){
                    return this->c2;
                }

                const char * getD2(){
                    return this->d2;
                }

                const char * getSol2(){
                    return this->sol2;
                }


                const char * getA3(){
                     return this->a3;
                }

                const char * getB3(){
                     return this->b3;
                }

                        const char * getC3(){
                            return this->c3;
                        }

                        const char * getD3(){
                            return this->d3;
                        }

                        const char * getSol3(){
                            return this->sol3;
                        }

                        int getLevel(){
                            return this->level;
                        }

                        int getTime(){
                            return this->time;
                        }
};
//
class Passage{
    private :
        int id;
         int istext;
         const char * token;
         const char * content;
     public:
        Passage(int id, int istext , const char * token , const char * content){
            this->id = id;
            this->istext = istext;
            this->token = token;
            this->content=content;
        }

        //get

        int getId(){
            return this->id;
        }

        int getIsText(){
            return this->istext;

        }

        const char * getToken(){
            return this->token;
        }

        const char * getContent(){
            return this->content;
        }

};

class QuestionPart7{
    private:
        int id;
        const char * question;
        const char * a;
        const char * b;
        const char * c;
        const char * d;
        const char * sol;
    public :
        QuestionPart7(int id , const char * question, const char * a , const char *b , const char * c, const char * d , const char *sol){
            this ->id = id ;
            this->question = question;
            this->a = a;
            this->b = b;
            this->c = c;
            this->d = d;
            this->sol = sol;
        }

        int getId(){
            return this->id;
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

};

class Part7{
    private:
        int id;
        const char * explan;
        int level ;
        int time;
        int countQuestion;
        vector<Passage> passages;
        vector<QuestionPart7>questions;

    public :
    Part7(int id , const char * explan , int level , int time ,int countQuestion, vector<Passage>passages , vector<QuestionPart7>questions){
        this->id = id;
        this->explan = explan;
        this->level = level;
        this->time= time;
        this->passages = passages;
        this->questions = questions;
        this->countQuestion = countQuestion;
    }

    //get
    int getId(){
        return this->id;
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

    int getCountQuestion(){
        return this->countQuestion;
    }

    vector<Passage> getPassage(){
        return this->passages;
    }

    vector<QuestionPart7>getQuestions(){
        return this->questions;
    }

    //set
    void setPassages(vector<Passage>passages){
        this->passages = passages;
    }

    void setQuestions(vector<QuestionPart7>questions){
        this->questions = questions;
    }

};

//


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



















































































