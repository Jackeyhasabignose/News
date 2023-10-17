CREATE TABLE `news` (
   news_id	int	NO	PRI		auto_increment
title	varchar(50)	NO			
content	varchar(500)	NO			
public_time	datetime	NO			
category_id	int	NO			
parent_category_id	int	NO			
status	varchar(10)	NO			
sub_title	varchar(45)	YES			
alter_time	timestamp	YES			
 );
 CREATE TABLE `parent_category`(
category_id	int	NO	PRI		auto_increment
parent_category_id	int	NO			
category_name	varchar(45)	NO	UNI		
news_count	int	YES				
 );
 CREATE TABLE `news`(
parent_category_id	int	NO	PRI		auto_increment
parent_category_name	varchar(45)	NO	UNI		
news_count	int	YES					
 );