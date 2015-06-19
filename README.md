# investigateconnectionpool

With tomcat JDBC connection pooling in my web application, sometimes the tomcat JDBC connections become unhealthy causing
connection closed issues. This causes the application to cease working making it completely unusable and the only option to make
it work again is to bounce tomcat server. Interestingly, the same issue doesn't seem to occur with C3P0 and everything works fine.
Seems like the connections somehow become stale and are no longer returned back to the pool.
Lowering the valiationQueryInterval was also not of much help as the issue was still reproducable with large number of simultaneous
requests and besides that, more frequentconnection validation will eat out the performance offered by Tomcat.
This mini app is to simulate the same issue by exploiting the limit of number of parameters we can pass to an IN clause in
postgreSQL. With tomcat JDBC connection pooling and everything working fine , we should get IOException but we get the 
connection closed issue instead. The behaviour is inline with the expectation with using C3P0.

# Steps to run this app and reproduce the issue : 
1- PostgreSQL database is used in the application. It should be installed in the machine. We need to create two schemas named
   db_for_tomcat_jdbc and db_for_c3p0 (one to use tomcat JDBC pooling and another for C3P0) and execute queries                 from DB_SCRIPT.sql (investigateconnectionpool\src\main\resources). This app uses default username as "postgres" and empty    passsword.

2- Uncomment log_statement = note and set to log_statement = note in  /etc/postgresql/9.2/main/postgresql.conf(in ubuntu), this changes will show all quries fired in postgresql.
After this changes restart postgreSQL service. 
   $ sudo service postgresql restart
   
3- Build application using maven : 
   $ mvn package
    
4- Copy generated war file, target/investigateconnectionpool.war, to tomcat webapps folder.

5- Copy the jdbc driver from target/investigateconnectionpool/WEB-INF/lib/postgresql-9.1-901.jdbc4.jar to tomcat lib folder.

6- Start tomcat and hit url localhost:8080/investigateconnectionpool
   
   Tomcat-Jdbc connection pool section
   
     a. Click the "Query with 10 params in IN clause"  link, it redirect to "success page" (normal execution).
     
     b. Click the "Query with 40000 params in IN clause" link, now it generate: "This connection has been closed           exception".
     
     c. If we again click any of the link then application is trying to use old connection which was closed. We            need to restart the app to fix this.
       
  
   C3PO connection pool
   
     a. Click the "Query with 10 params in IN clause"  link, it redirect to no "success page" (normal execution).
     
     b. Click the "Query with 40000 params in IN clause" link, now it generate: "This connection has been closed           exception".
     
     c. If we again click "Query with 10 params in IN clause", it redirect to "success page".
  
  
7- View the log /var/log/postgresql/postgresql-9.2-main.log
