BEGIN TRANSACTION;
CREATE TABLE word_lesson
(
  word_id  Integer NOT NULL,
  lesson_tag_id Integer NOT NULL,
  CONSTRAINT word_tag_ibfk_1
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT word_tag_ibfk_2
  FOREIGN KEY (lesson_tag_id) REFERENCES lesson_tag (lesson_tag_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE word_favorite(
  id INTEGER ,
  time Text,
  FOREIGN KEY (id) REFERENCES word(word_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE word_checked(
  id INTEGER ,
  time Text,
  result Integer,
  FOREIGN KEY (id) REFERENCES word(word_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE word
(
  word_id   Integer NOT NULL PRIMARY KEY,
  token Blob,
  word      BLOB      ,
  pronounce BLOB                DEFAULT '',
  example   BLOB                DEFAULT '',
  aware     Integer             DEFAULT 0
);
CREATE TABLE w_tag
(
  w_tag_id     Integer NOT NULL   PRIMARY KEY,
  w_section_id Integer NULL,

  tag_title    TEXT      NOT NULL DEFAULT 'TAG TITLE',
  img_token   BLOB,
  CONSTRAINT w_tag_ibfk_1
  FOREIGN KEY (w_section_id) REFERENCES w_section (w_section_id)
    ON UPDATE CASCADE
    ON DELETE SET NULL
);
CREATE TABLE w_section
(
  w_section_id  Integer NOT NULL PRIMARY KEY,
  section_title Text      NOT NULL DEFAULT 'SECTION TITLE'
);
CREATE TABLE part7_word
(
  part7_id Integer NOT NULL,
  word_id  Integer NOT NULL,
  CONSTRAINT part7_id
  UNIQUE (part7_id, word_id),
  CONSTRAINT part7_word_ibfk_1
  FOREIGN KEY (part7_id) REFERENCES part7 (part7_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part7_word_ibfk_2
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part7_subject
(
  part7_id   Integer NOT NULL,
  subject_id Integer NOT NULL,
  CONSTRAINT part7_id
  UNIQUE (part7_id, subject_id),
  CONSTRAINT part7_subject_ibfk_1
  FOREIGN KEY (part7_id) REFERENCES part7 (part7_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part7_subject_ibfk_2
  FOREIGN KEY (subject_id) REFERENCES p7_subject (subject_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part7_question
(
  part7_question_id Integer                                      NOT NULL
    PRIMARY KEY,
  part7_id          Integer                                      NOT NULL,
  question          BLOB                                            ,
  a                 TEXT                                            ,
  b                 TEXT                                            ,
  c                 TEXT                                            ,
  d                 TEXT                                            ,
  sol               TEXT NOT NULL,

  CONSTRAINT part7_question_ibfk_1
  FOREIGN KEY (part7_id) REFERENCES part7 (part7_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part7_passage
(
  part7_id              Integer    NOT NULL,
  part7_passage_id     Integer      NOT NULL
    PRIMARY KEY,
  is_text               Integer DEFAULT 1 ,
  content               BLOB,
  CONSTRAINT part7_passage_ibfk_1
  FOREIGN KEY (part7_id) REFERENCES part7 (part7_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part7_grammar
(
  part7_id   Integer NOT NULL,
  grammar_id Integer NOT NULL,
  CONSTRAINT part7_id
  UNIQUE (part7_id, grammar_id),
  CONSTRAINT part7_grammar_ibfk_1
  FOREIGN KEY (part7_id) REFERENCES part7 (part7_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part7_grammar_ibfk_2
  FOREIGN KEY (grammar_id) REFERENCES grammar (grammar_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part7_favorite(
  id INTEGER ,
  time Text,
  FOREIGN KEY (id) REFERENCES part7(part7_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part7_checked(
  id INTEGER ,
  time Text,
  result Integer,
  FOREIGN KEY (id) REFERENCES part7(part7_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part7
(
  part7_id    Integer                     NOT NULL PRIMARY KEY,
  token BLOB,
  explanation BLOB                                  ,
  level       Integer DEFAULT 0                    ,
  time        Integer               DEFAULT  0,
  count_question Integer DEFAULT 0

);
CREATE TABLE part6_word
(
  part6_id Integer NOT NULL,
  word_id  Integer NOT NULL,
  CONSTRAINT part6_id
  UNIQUE (part6_id, word_id),
  CONSTRAINT part6_word_ibfk_1
  FOREIGN KEY (part6_id) REFERENCES part6 (part6_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part6_word_ibfk_2
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part6_subject
(
  part6_id   Integer NOT NULL,
  subject_id Integer NOT NULL,
  CONSTRAINT part6_id
  UNIQUE (part6_id, subject_id),
  CONSTRAINT part6_subject_ibfk_1
  FOREIGN KEY (part6_id) REFERENCES part6 (part6_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part6_subject_ibfk_2
  FOREIGN KEY (subject_id) REFERENCES p6_subject (subject_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part6_grammar
(
  part6_id   Integer NOT NULL,
  grammar_id Integer NOT NULL,
  CONSTRAINT part6_id
  UNIQUE (part6_id, grammar_id),
  CONSTRAINT part6_grammar_ibfk_1
  FOREIGN KEY (part6_id) REFERENCES part6 (part6_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part6_grammar_ibfk_2
  FOREIGN KEY (grammar_id) REFERENCES grammar (grammar_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part6_favorite(
  id INTEGER ,
  time Text,
  FOREIGN KEY (id) REFERENCES part6(part6_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part6_checked(
  id INTEGER ,
  time Text,
  result Integer,
  FOREIGN KEY (id) REFERENCES part6(part6_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part6
(
  part6_id  Integer NOT NULL PRIMARY KEY,
  token BLOB,
  content   BLOB                        ,
  a1 text,
  b1 text,
  c1 text,
  d1 text,
  sol1 text,
  a2 text,
  b2 text,
  c2 text,
  d2 text,
  sol2 text,
  a3 text,
  b3 text,
  c3 text,
  d3 text,
  sol3 text,
  explanation   BLOB,
  level         Integer,
  time        Integer               DEFAULT  0
);
CREATE TABLE part5_word
(
  part5_id Integer NOT NULL,
  word_id  Integer NOT NULL,
  CONSTRAINT part5_id
  UNIQUE (part5_id, word_id),
  CONSTRAINT part5_word_ibfk_1
  FOREIGN KEY (part5_id) REFERENCES part5 (part5_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part5_word_ibfk_2
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part5_subject
(
  part5_id   Integer NOT NULL,
  subject_id Integer NOT NULL,
  CONSTRAINT part5_id
  UNIQUE (part5_id, subject_id),
  CONSTRAINT part5_subject_ibfk_1
  FOREIGN KEY (part5_id) REFERENCES part5 (part5_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part5_subject_ibfk_2
  FOREIGN KEY (subject_id) REFERENCES p5_subject (subject_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part5_grammar
(
  part5_id   Integer NOT NULL,
  grammar_id Integer NOT NULL,
  CONSTRAINT part5_id
  UNIQUE (part5_id, grammar_id),
  CONSTRAINT part5_grammar_ibfk_1
  FOREIGN KEY (part5_id) REFERENCES part5 (part5_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part5_grammar_ibfk_2
  FOREIGN KEY (grammar_id) REFERENCES grammar (grammar_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part5_favorite(
  id INTEGER ,
  time Text,
  FOREIGN KEY (id) REFERENCES part5(part5_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part5_checked(
  id INTEGER ,
  time Text,
  result Integer,
  FOREIGN KEY (id) REFERENCES part5(part5_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part5
(
  part5_id    Integer                                                NOT NULL PRIMARY KEY,
  token  BLOB      ,
  question    BLOB                                                      ,
  a           BLOB                                                      ,
  b           BLOB                                                     ,
  c           BLOB                                                      ,
  d           BLOB                                                      ,
  sol         TEXT                                                      ,
  explanation BLOB  ,
  level SMALLINT  DEFAULT 0,
  time        Integer               DEFAULT  0
);
CREATE TABLE part4_word
(
  part4_id Integer NOT NULL,
  word_id  Integer NOT NULL,
  CONSTRAINT part4_id
  UNIQUE (part4_id, word_id),
  CONSTRAINT part4_word_ibfk_1
  FOREIGN KEY (part4_id) REFERENCES part4 (part4_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part4_word_ibfk_2
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part4_subject
(
  part4_id   Integer NOT NULL,
  subject_id Integer NOT NULL,
  CONSTRAINT part4_id
  UNIQUE (part4_id, subject_id),
  CONSTRAINT part4_subject_ibfk_1
  FOREIGN KEY (part4_id) REFERENCES part4 (part4_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part4_subject_ibfk_2
  FOREIGN KEY (subject_id) REFERENCES p4_subject (subject_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part4_favorite(
  id INTEGER ,
  time Text,
  FOREIGN KEY (id) REFERENCES part4(part4_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part4_checked(
  id INTEGER ,
  time Text,
  result Integer,
  FOREIGN KEY (id) REFERENCES part4(part4_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part4
(
  part4_id        Integer NOT NULL  PRIMARY KEY,
  question_token  BLOB      ,
  question_script BLOB      ,
  q1 text,
  a1 text,
  b1 text,
  c1 text,
  d1 text,
  sol1 text,
  q2 text,
  a2 text,
  b2 text,
  c2 text,
  d2 text,
  sol2 text,
  q3 text,
  a3 text,
  b3 text,
  c3 text,
  d3 text,
  sol3 text,
  level Integer  DEFAULT 0,
  time        Integer               DEFAULT  0
);
CREATE TABLE part3_word
(
  part3_id Integer NOT NULL,
  word_id  Integer NOT NULL,
  CONSTRAINT part3_id
  UNIQUE (part3_id, word_id),
  CONSTRAINT part3_word_ibfk_1
  FOREIGN KEY (part3_id) REFERENCES part3 (part3_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part3_word_ibfk_2
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part3_subject
(
  part3_id   Integer NOT NULL,
  subject_id Integer NOT NULL,
  CONSTRAINT part3_id
  UNIQUE (part3_id, subject_id),
  CONSTRAINT part3_subject_ibfk_1
  FOREIGN KEY (part3_id) REFERENCES part3 (part3_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part3_subject_ibfk_2
  FOREIGN KEY (subject_id) REFERENCES p3_subject (subject_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part3_favorite(
  id INTEGER ,
  time Text,
  FOREIGN KEY (id) REFERENCES part3(part3_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part3_checked(
  id INTEGER ,
  time Text,
  result INTEGER,
  FOREIGN KEY (id) REFERENCES part3(part3_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part3
(
  part3_id        Integer NOT NULL PRIMARY KEY,
  question_token  BLOB         UNIQUE,
  question_script BLOB         DEFAULT '',
  q1 text,
  a1 text,
  b1 text,
  c1 text,
  d1 text,
  sol1 text,
  q2 text,
  a2 text,
  b2 text,
  c2 text,
  d2 text,
  sol2 text,
  q3 text,
  a3 text,
  b3 text,
  c3 text,
  d3 text,
  sol3 text,
  level     Integer    DEFAULT 0,
  time        Integer               DEFAULT  0
);
CREATE TABLE part2_word
(
  part2_id SMALLINT UNSIGNED NOT NULL,
  word_id  SMALLINT UNSIGNED NOT NULL,
  CONSTRAINT part2_id
  UNIQUE (part2_id, word_id),
  CONSTRAINT part2_word_ibfk_1
  FOREIGN KEY (part2_id) REFERENCES part2 (part2_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part2_word_ibfk_2
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part2_subject
(
  part2_id   Integer NOT NULL,
  subject_id Integer NOT NULL,
  CONSTRAINT part2_id
  UNIQUE (part2_id, subject_id),
  CONSTRAINT part2_subject_ibfk_1
  FOREIGN KEY (part2_id) REFERENCES part2 (part2_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part2_subject_ibfk_2
  FOREIGN KEY (subject_id) REFERENCES p2_subject (subject_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part2_favorite(
  id INTEGER ,
  time Text,
  FOREIGN KEY (id) REFERENCES part2(part2_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part2_checked(
  id INTEGER ,
  time Text,
  result Integer,
  FOREIGN KEY (id) REFERENCES part2(part2_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part2
(
  part2_id        Integer                                 NOT NULL PRIMARY KEY,
  question_token  BLOB                                            ,
  question_script TEXT                                      ,
  a_script        TEXT,
  b_script        TEXT,
  c_script        TEXT,
  sol             TEXT ,
  level           Integer                                           DEFAULT 0,
  time        Integer               DEFAULT  0
);
CREATE TABLE part1_word
(
  part1_id SMALLINT UNSIGNED NOT NULL,
  word_id  SMALLINT UNSIGNED NOT NULL,
  CONSTRAINT part1_id
  UNIQUE (part1_id, word_id),
  CONSTRAINT part1_word_ibfk_1
  FOREIGN KEY (part1_id) REFERENCES part1 (part1_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part1_word_ibfk_2
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part1_subject
(
  part1_id   SMALLINT UNSIGNED,
  subject_id SMALLINT UNSIGNED,
  CONSTRAINT part1_id
  UNIQUE (part1_id, subject_id),
  CONSTRAINT part1_subject_ibfk_1
  FOREIGN KEY (part1_id) REFERENCES part1 (part1_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT part1_subject_ibfk_2
  FOREIGN KEY (subject_id) REFERENCES p1_subject (subject_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE part1_favorite(
  id INTEGER ,
  time Text,
  FOREIGN KEY (id) REFERENCES part1(part1_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part1_checked(
  id INTEGER ,
  time Text,
  result Integer,
  FOREIGN KEY (id) REFERENCES part1(part1_id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE part1
(
  part1_id    Integer                                      NOT NULL
    PRIMARY KEY,
  token   BLOB                                                ,
  a_script    BLOB                                           ,
  b_script    BLOB                                            ,
  c_script    BLOB                                            ,
  d_script    BLOB                                            ,
  sol         TEXT  ,
  level       Integer                                         DEFAULT 0,
  time        Integer               DEFAULT  0
);
CREATE TABLE p7_subject
(
  subject_id    Integer NOT NULL  PRIMARY KEY,
  subject_title TEXT      NOT NULL DEFAULT 'PART7 SUBJECT TITLE'
);
CREATE TABLE p6_subject
(
  subject_id    Integer NOT NULL  PRIMARY KEY,
  subject_title TEXT      NOT NULL DEFAULT 'PART6 SUBJECT TITLE'
);
CREATE TABLE p5_subject
(
  subject_id    Integer NOT NULL PRIMARY KEY,
  subject_title TEXT      NOT NULL DEFAULT 'PART5 SUBJECT TITLE'
);
CREATE TABLE p4_subject
(
  subject_id    Integer NOT NULL  PRIMARY KEY,
  subject_title TEXT      NOT NULL DEFAULT 'PART4 SUBJECT TITLE'
);
CREATE TABLE p3_subject
(
  subject_id    Integer NOT NULL  PRIMARY KEY,
  subject_title TEXT      NOT NULL DEFAULT 'PART3 SUBJECT TITLE'
);
CREATE TABLE p2_subject
(
  subject_id    Integer NOT NULL PRIMARY KEY ,
  subject_title TEXT      NOT NULL DEFAULT 'PART2 SUBJECT TITLE'
);
CREATE TABLE p1_subject
(
  subject_id    Integer NOT NULL PRIMARY KEY ,
  subject_title TEXT      NOT NULL DEFAULT 'Part1 SUBJECT TITLE'
);
CREATE TABLE meaning
(
  meaning_id   Integer       NOT NULL  PRIMARY KEY,
  word_id      Integer       NOT NULL,
  meaning      BLOB             ,
  meaning_type BLOB             ,
  explanation  BLOB             ,
  similar      BLOB             ,
  CONSTRAINT meaning_ibfk_1
  FOREIGN KEY (word_id) REFERENCES word (word_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE lesson_tag
(
  lesson_tag_id     Integer NOT NULL   PRIMARY KEY,
  w_tag_id Integer NULL,
  lesson_title    TEXT      NOT NULL DEFAULT 'TAG TITLE',
  CONSTRAINT lesson_tag_ibfk_1
  FOREIGN KEY (w_tag_id) REFERENCES w_tag (w_tag_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE grammar
(
  grammar_id      Integer NOT NULL PRIMARY KEY,
  grammar_title   TEXT      NOT NULL             DEFAULT 'GRAMMAR TITLE',
  plain_text      BLOB
);
CREATE TABLE dictionary_favorite(
  id Integer ,
  time Text,
  meaning Text,
  FOREIGN KEY (id) REFERENCES dictionary(id)
);
CREATE TABLE dictionary_checked(
  id INTEGER ,
  time Text,
  result Integer,
  FOREIGN KEY (id) REFERENCES dictionary_favorite(id)
    ON DELETE CASCADE  ON UPDATE CASCADE
);
CREATE TABLE dictionary(
  id Integer Primary Key AutoIncrement ,
  word TEXT ,
  mean BLOB
);
CREATE VIEW part6_result_view AS SELECT p6_subject.subject_id, p6_subject.subject_title, ( SELECT COUNT (*) FROM part6_checked WHERE part6_checked.id IN (SELECT part6_subject.part6_id FROM part6_subject WHERE part6_subject.subject_id = p6_subject.subject_id) AND part6_checked.result = 1 ) as correct, ( SELECT COUNT (*) FROM part6_checked WHERE part6_checked.id IN (SELECT part6_subject.part6_id FROM part6_subject WHERE part6_subject.subject_id = p6_subject.subject_id) AND part6_checked.result = 0 ) as wrong FROM p6_subject;
CREATE VIEW part5_result_view AS SELECT p5_subject.subject_id, p5_subject.subject_title, ( SELECT COUNT (*) FROM part5_checked WHERE part5_checked.id IN (SELECT part5_subject.part5_id FROM part5_subject WHERE part5_subject.subject_id = p5_subject.subject_id) AND part5_checked.result = 1 ) as correct, ( SELECT COUNT (*) FROM part5_checked WHERE part5_checked.id IN (SELECT part5_subject.part5_id FROM part5_subject WHERE part5_subject.subject_id = p5_subject.subject_id) ) as wrong FROM p5_subject;
CREATE VIEW part4_result_view AS SELECT p4_subject.subject_id, p4_subject.subject_title, ( SELECT COUNT (*) FROM part4_checked WHERE part4_checked.id IN (SELECT part4_subject.part4_id FROM part4_subject WHERE part4_subject.subject_id = p4_subject.subject_id) AND part4_checked.result = 1 ) as correct, ( SELECT COUNT (*) FROM part4_checked WHERE part4_checked.id IN (SELECT part4_subject.part4_id FROM part4_subject WHERE part4_subject.subject_id = p4_subject.subject_id) AND part4_checked.result = 0 ) as wrong FROM p4_subject;
CREATE VIEW part3_result_view AS SELECT p3_subject.subject_id, p3_subject.subject_title, ( SELECT COUNT (*) FROM part3_checked WHERE part3_checked.id IN (SELECT part3_subject.part3_id FROM part3_subject WHERE part3_subject.subject_id = p3_subject.subject_id) AND part3_checked.result = 1 ) as correct, ( SELECT COUNT (*) FROM part3_checked WHERE part3_checked.id IN (SELECT part3_subject.part3_id FROM part3_subject WHERE part3_subject.subject_id = p3_subject.subject_id) AND part3_checked.result = 0 ) as wrong FROM p3_subject;
CREATE VIEW part2_result_view AS SELECT p2_subject.subject_id, p2_subject.subject_title, ( SELECT COUNT (*) FROM part2_checked WHERE part2_checked.id IN (SELECT part2_subject.part2_id FROM part2_subject WHERE part2_subject.subject_id = p2_subject.subject_id) AND part2_checked.result = 1 ) as correct, ( SELECT COUNT (*) FROM part2_checked WHERE part2_checked.id IN (SELECT part2_subject.part2_id FROM part2_subject WHERE part2_subject.subject_id = p2_subject.subject_id) AND part2_checked.result = 0 ) as wrong FROM p2_subject;
CREATE VIEW part1_result_view AS SELECT p1_subject.subject_id, p1_subject.subject_title, ( SELECT COUNT (*) FROM part1_checked WHERE part1_checked.id IN (SELECT part1_subject.part1_id FROM part1_subject WHERE part1_subject.subject_id = p1_subject.subject_id) AND part1_checked.result = 1 ) as correct, ( SELECT COUNT (*) FROM part1_checked WHERE part1_checked.id IN (SELECT part1_subject.part1_id FROM part1_subject WHERE part1_subject.subject_id = p1_subject.subject_id) AND part1_checked.result = 0 ) as wrong FROM p1_subject;
COMMIT;
