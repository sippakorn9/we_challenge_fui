drop table IF EXISTS Review;

select * from CSVREAD('src/main/resources/test_file.csv');

create TABLE Review(ID INT PRIMARY KEY, CONTENT NVARCHAR(MAX))
    AS select ID, CONTENT from CSVREAD('src/main/resources/test_file.csv',null, 'fieldSeparator=,');

alter table Review
add VERSION INT NOT NULL DEFAULT 0;