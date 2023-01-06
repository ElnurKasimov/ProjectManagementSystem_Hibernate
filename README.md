# hibernate_postgresql
a simple web application for a project management system using Hibernate and PostgreSQL DB
-	web application for simple CRM for IT market using a servlet container Tomcat 9.0.64
- Libraries used - JSTL, GSON, LOMBOK
-	Patterns used - MVC, DAO/DTO, Singleton

For proper work of code from this repository please make such steps:
1. insert into file application.properties (which located in root directory) initial settings of your database;
2. set in VM option in Your Tomcat such variables: dbUsername, dbPassword and dbUrl, which You will use for the database;
3. for creating database use initDB.sql which is located in the root directory of the project.
