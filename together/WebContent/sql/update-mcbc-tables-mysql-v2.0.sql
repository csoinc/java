DROP TABLE blog_user_type;
CREATE TABLE blog_user_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200),
  name_cn VARCHAR(200),
  name_tw VARCHAR(200),
  status BOOLEAN
);

INSERT INTO blog_user_type VALUES (1,"Admin","管理员","管理員",true);
INSERT INTO blog_user_type VALUES (2,"Leader","经理","經理",true);
INSERT INTO blog_user_type VALUES (3,"User","用户","用戶",true);
INSERT INTO blog_user_type VALUES (4,"Guest","访客","訪客",true);

DROP TABLE blog_security_type;
CREATE TABLE blog_security_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200),
  name_cn VARCHAR(200),
  name_tw VARCHAR(200),
  status BOOLEAN
);

INSERT INTO blog_security_type VALUES (1,"Admin","管理员","管理員",true);
INSERT INTO blog_security_type VALUES (2,"Leader","经理","經理",true);
INSERT INTO blog_security_type VALUES (3,"User","用户","用戶",true);
INSERT INTO blog_security_type VALUES (4,"Guest","访客","訪客",true);

DROP TABLE blog_language_type;
CREATE TABLE blog_language_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200),
  name_cn VARCHAR(200),
  name_tw VARCHAR(200),
  status BOOLEAN
);

INSERT INTO blog_language_type VALUES (1,"Cantonese","粤语","粵語",true);
INSERT INTO blog_language_type VALUES (2,"English","English","English",true);
INSERT INTO blog_language_type VALUES (3,"Mandarin","国语","國語",true);

DROP TABLE blog_group_type;
CREATE TABLE blog_group_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200),
  name_cn VARCHAR(200),
  name_tw VARCHAR(200),
  status BOOLEAN
);

INSERT INTO blog_group_type VALUES (1,"Album","相册","相冊",true);
INSERT INTO blog_group_type VALUES (2,"Cell Group","生命小组","生命小組",true);
INSERT INTO blog_group_type VALUES (3,"Church","教会","教會",true);
INSERT INTO blog_group_type VALUES (4,"Fellowship","团契","團契",true);
INSERT INTO blog_group_type VALUES (5,"Generic","普通","普通",true);
INSERT INTO blog_group_type VALUES (6,"Worship","敬拜","敬拜",true);
INSERT INTO blog_group_type VALUES (7,"New","热点","熱點",true);
UPDATE blog_group_type SET id = 0 where id = 7;

DROP TABLE blog_message_type;
CREATE TABLE blog_message_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200),
  name_cn VARCHAR(200),
  name_tw VARCHAR(200),
  status BOOLEAN
);

INSERT INTO blog_message_type VALUES (1,"Image","","",true);
INSERT INTO blog_message_type VALUES (2,"Music","","",true);
INSERT INTO blog_message_type VALUES (3,"Link","","",true);
INSERT INTO blog_message_type VALUES (4,"Text","","",true);
INSERT INTO blog_message_type VALUES (5,"Word","","",true);
INSERT INTO blog_message_type VALUES (6,"HTML","","",true);
INSERT INTO blog_message_type VALUES (7,"File","","",true);
INSERT INTO blog_message_type VALUES (8,"Plain","","",true);

