drop table IF EXISTS Review;
drop table IF EXISTS Keyword;

create TABLE Review(ID INT PRIMARY KEY, CONTENT NVARCHAR(MAX))
    AS select TOP(1000) ID, CONTENT from CSVREAD('src/main/resources/test_file.csv',null, 'fieldSeparator=,');

alter table Review add VERSION INT NOT NULL DEFAULT 0;

CREATE TABLE Keyword (
ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
KEYWORD NVARCHAR(100)
);

INSERT INTO Keyword (KEYWORD)
select TOP(4000) * FROM CSVREAD('src/main/resources/n_food_dictionary.csv');