Hi.

It's sources for my web project. Project - Test resource.

Here you can pass test, watch history of all passings, if you are an admin -> you can ban/unban users, create, update, delete tests and their questions.
Also you can login/register to the resource.

For running this project you must have -> Tomcat 7+, MySQL, Eclipse and good mood.

Run instruction:
1. Clone/download project.
2. Import to eclipse.
3. Right click on project, Java Build Path -> fix it, here can be some problems.
4. Import scripts for creating database, putting data to db( for example by MySQL Workbench). Scripts are in folder ->sql_scripts.
5. Run project from Eclipse on Tomcat.

6. Enjoy.



About. Project created for studying, here can be some bad solutions, bad code, some problems.
Now for DAO used JDBC, in plans -> migrate to Hibernate. Authorization is used by Tomcat form authorization, in
plans migrate to Spring Security. I'll try to do refactoring and improve my simple project.
