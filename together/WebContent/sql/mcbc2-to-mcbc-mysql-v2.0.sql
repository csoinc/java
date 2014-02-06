insert into blog_user (
  id,user_type_id,login_name,login_password,email,firstname,lastname,phone_home,phone_cell,unit,street,city,province,
  country,create_time,update_time,access_time,status) 
  select 
  id,user_type_id,login_name,login_password,email,firstname,lastname,phone_home,phone_cell,unit,street,city,province,
  country,create_time,update_time,access_time,status 
  from mcbc2.bbs_user; 

insert into blog_group (
  id,security_type_id,group_name,description,create_time,update_time,status,group_type_id,language_type_id) 
  select  
  id,security_type_id,group_name,description,create_time,update_time,status,group_type_id,language_type_id
  from mcbc2.bbs_group; 

insert into blog_user_group (
  id,user_id,group_id,group_owner,create_time,update_time,status)
  select 
  id,user_id,group_id,group_owner,create_time,update_time,status
  from mcbc2.bbs_user_group;

insert into blog_message (
  id,group_id,user_id,message,upload_file,link_url,message_type_id,create_time,update_time,status)
  select 
  id,group_id,user_id,message,upload_file,link_url,message_type_id,create_time,update_time,status
  from mcbc2.bbs_message;
  
insert into blog_reply_message (
  id,message_id,user_id,message,upload_file,link_url,message_type_id,create_time,update_time,status)
  select 
  id,message_id,user_id,message,upload_file,link_url,message_type_id,create_time,update_time,status 
  from mcbc2.bbs_reply_message;
  
insert into blog_statistic (
  id,statistic_id,statistic_table,view_times,update_times,status)
  select 
  id,statistic_id,statistic_table,view_times,update_times,status 
  from mcbc2.bbs_statistic;
  
insert into bible_book (
  id,sequence,number,testament,code,name,name_cn,name_tw)
  select 
  id,sequence,number,testament,code,name,name_cn,name_tw 
  from mcbc2.bible_book;

insert into bible_line (
  id,sequence,code,chapter,section,content,content_cn,content_tw)
  select 
  id,sequence,code,chapter,section,content,content_cn,content_tw
  from mcbc2.bible_line;
  
  