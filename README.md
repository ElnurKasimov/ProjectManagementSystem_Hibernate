# hibernate_postgresql
-	web application for simple PMS for IT market using a servlet container Tomcat 9.0.64
- Libraries used - HIBERNATE, JSTL, GSON, LOMBOK
-	Patterns used - MVC, DAO/DTO, Singleton

# how to deploy the project locally

1. Requirements:
    - Java 17 or later - early versions work not stable,
    - Hibernate 3.0 or later,
    - PostgreSQL 11 or later.

2. For proper work of code from this repository please make such steps:
    - create database,
    - set in your IntelliJ IDEA (Run/Debug Configurations -> VM options) such options:   
        - dbUsername, 
        - dbPassword,
        - dbUrl,
        
    which You will use for created database;
    - for creating database structure use initDB.sql which is located in the root directory of the project.
