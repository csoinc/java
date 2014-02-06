--ALTER TABLE blog_message ADD title VARCHAR(250), ADD summary VARCHAR(500);

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
INSERT INTO blog_group_type VALUES (2,"Blogger","博客","博客",true);
INSERT INTO blog_group_type VALUES (3,"Business","商业","商業",true);
INSERT INTO blog_group_type VALUES (4,"Careers","生涯","生涯",true);
INSERT INTO blog_group_type VALUES (5,"Community","社区","社區",true);
INSERT INTO blog_group_type VALUES (6,"News","新闻","新聞",true);
INSERT INTO blog_group_type VALUES (7,"Religion","宗教","宗教",true);
INSERT INTO blog_group_type VALUES (8,"Technology","科技","科技",true);
INSERT INTO blog_group_type VALUES (100,"Hot","热点","熱點",true);
UPDATE blog_group_type SET id = 0 whene id = 100;

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

