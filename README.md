### Features

- It is a multi-threading project using core java and Mysq.
- Enter 1 for insert data into mysql table name customers. Customers divided into two parts one is for valid customers name valid_customers and other is invalid_customers for invalid customers.
- Valid and  Invalid customers separate by using email and phone numbers.
- Enter 2 for splitting customer's information concurrently within separate files. 
- Here have been used Chain of Responsible pattern for multi-threading, Builder pattern for customers and Adapter pattern for pooling threads.

### Preparation
1. create database using following Mysql DDL:
```roomsql
 create database oragetoolz;
```
2. create table for valid customers using following mysql DDL:
```roomsql
create table valid_customers(first_name varchar(100), last_name varchar(100), city varchar(100),
state varchar(100),postal_zip_code varchar(20), phone_number varchar(50), email varchar(100), ip_address varchar(30));
```
3. create table for invalid customers using following mysql DDL:
```roomsql
create table invalid_customers(first_name varchar(100), last_name varchar(100), city varchar(100),
state varchar(100),postal_zip_code varchar(20), phone_number varchar(50), email varchar(100), ip_address varchar(30));
```
4. change application.properties file according to your mysql configuration:
```properties
jdbc.url=jdbc:mysql://localhost:3306/oragetoolz
jdbc.username=admin
jdbc.password=admin
jdbc.drive=com.mysql.cj.jdbc.Driver
```
### Execution procedure
just create jar and used following cmd.
```roomsql
java -jar ASSESSMENT-1.0-SNAPSHOT.jar 
```

### Checking Results
1. input file is store into following directory:
```aidl
/resources/
```
2. output will generate into followin directory:
```aidl
/resources/
```
### Input File:
Input is a txt which is below :
```aidl
1M-customers.txt
```

### problem description is below:
```aidl
Assessment.pdf
```
