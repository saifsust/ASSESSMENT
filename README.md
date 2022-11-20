### Features

- It is a multi-threading project using core java and Mysq.
- Enter 1 for insert data into mysql table name customers. Customers divided into two parts one is for valid customers name valid_customers and other is invalid_customers for invalid customers.
- Valid and  Invalid customers separate by using email and phone numbers.
- Here have been used Chain of Responsible pattern for multi-threading, Builder pattern for customers and Adapter pattern for pooling threads.

### Preparation
1. create database using following Mysql DDL:
```jql
 CREATE DATABASE oragetoolz;
```
2. create table for valid customers using following mysql DDL:
```jql
create table valid_customers(first_name varchar(100), last_name varchar(100), city varchar(100),
state varchar(100),postal_zip_code varchar(20), phone_number varchar(50), email varchar(100), ip_address varchar(30));
```
3. create table for invalid customers using following mysql DDL:
```jql
create table invalid_customers(first_name varchar(100), last_name varchar(100), city varchar(100),
state varchar(100),postal_zip_code varchar(20), phone_number varchar(50), email varchar(100), ip_address varchar(30));
```
4. change MysqlConnector properties according to your mysql configuration:
```java
public class MysqlConnector {
    private static final Log LOGGER = LogFactory.getLog(MysqlConnector.class);
    private static final String URL = "jdbc:mysql://localhost:3306/oragetoolz";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static  final String username = "admin";
    private static final String password = "admin";
    /** hide other codes**/
}
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
