-- create 2 databases db_for_tomcat_jdbc and db_for_c3p0 using below commented queries in postgres terminal
-- or create database from pgadmin 

-- CREATE DATABASE db_for_tomcat_jdbc;
-- CREATE DATABASE db_for_c3p0;


-- execute create table and insert statement in both databases
-- create table
CREATE TABLE EMP(
   ID INT PRIMARY KEY     NOT NULL,
   NAME           TEXT    NOT NULL,
   AGE            INT     NOT NULL,
   ADDRESS        CHAR(50),
   SALARY         REAL
);

--insert statement 
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (1, 'A', 1, 'ADDESS_1', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (2, 'B', 2, 'ADDESS_2', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (3, 'C', 3, 'ADDESS_3', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (4, 'D', 4, 'ADDESS_4', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (5, 'E', 5, 'ADDESS_5', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (6, 'F', 6, 'ADDESS_6', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (7, 'G', 7, 'ADDESS_7', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (8, 'H', 8, 'ADDESS_8', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (9, 'I', 9, 'ADDESS_9', 1000);
INSERT INTO EMP (ID, NAME, AGE, ADDRESS, SALARY) VALUES (10, 'J', 10, 'ADDESS_10', 1000);
