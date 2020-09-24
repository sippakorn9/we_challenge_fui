drop table IF EXISTS Review;
drop table IF EXISTS Keyword;

create TABLE Review(ID INT PRIMARY KEY, CONTENT NVARCHAR(MAX))
    AS select TOP(20) ID, CONTENT from CSVREAD('src/main/resources/test_file.csv',null, 'fieldSeparator=,');

alter table Review add VERSION INT NOT NULL DEFAULT 0;

CREATE TABLE Keyword (
ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
KEYWORD NVARCHAR(100)
);

INSERT INTO Keyword (KEYWORD)
select * FROM TOP(100) CSVREAD('src/main/resources/n_food_dictionary.csv');