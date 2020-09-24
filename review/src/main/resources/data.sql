drop table IF EXISTS Review;
drop table IF EXISTS Keyword;

create TABLE Review(ID INT PRIMARY KEY, CONTENT NVARCHAR(MAX))
    AS select ID, CONTENT from CSVREAD('classpath:/test_file.csv',null, 'fieldSeparator=,');

alter table Review add VERSION INT NOT NULL DEFAULT 0;

CREATE TABLE Keyword (
ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
KEYWORD NVARCHAR(100)
);

CREATE INDEX keyword
ON Keyword(keyword);

INSERT INTO Keyword (KEYWORD)
select * FROM CSVREAD('classpath:/n_food_dictionary.csv');