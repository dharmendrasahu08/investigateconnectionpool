# investigateconnectionpool

With tomcat JDBC connection pooling in my web application, sometimes the tomcat JDBC connections become unhealthy causing
connection closed issues. This causes the application to cease working making it completely unusable and the only option to make
it work again is to bounce tomcat server. Interestingly, the same issue doesn't seem to occur with C3P0 and everything works fine.
Seems like the connections somehow become stale and are no longer returned back to the pool.
Lowering the valiationQueryInterval was also not of much help as the issue was still reproducable with large no of simultaneous
requests and besides that, more frequentconnection validation will eat out the performance offered by Tomcat.
This mini app is to simulate the same issue by exploiting the limit of no of parameters we can pass to an IN clause in
postgreSQL. With tomcat JDBC connection pooling and everything working fine , we should get IOException but we get the 
connection closed issue instead. The behaviour is inline with the expectation with using C3P0.

# Steps to run this app and reproduce the issue : 
1- PostgreSQL database is used in the application. It should be installed in the machine. We need to create a schema named 
   "tomcat_test". This app uses default username as "postgres" and empty passsword.
2- Uncomment log_statement = note and set to log_statement = note in  /etc/postgresql/9.2/main/postgresql.conf(in ubuntu), After this changes restart postgreSQL service 
   $ sudo service postgresql restart
   
3- Build application using maven : 
   $ mvn package
    
4- Copy generated war file to tomcat webApps folder.

5- Copy the jdbc driver postgresql-9.1-901jdbc4.jar from WEB-INF/lib(once the project is built and the dependencies are
downloaded from maven repo) to tomcat lib folder.

6- Start tomcat and hit url localhost:8080/investigateconnectionpool

7- View the log /var/log/postgresql/postgresql-9.2-main.log
