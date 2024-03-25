
---------------
--- F1APP ---
---------------

This is a full-stack application (F1APP) for managing Formula One Team data.

The purpose is to list, create, update and delete Formula 1 Teams with some additional data like the **Foundation Year**, **Number of
Championships** and if **Entry Fee Paid**. 

---------------
--- Backend ---   
---------------
### Environment

Made with using Spring Boot 3.2.2, with Gradle (jdk: Amazon Corretto v17) in IntelliJ IDEA CE.

### Setup
So running the F1APP requires downloaded Gradle and JDK.

For maintaining, building, running the application, running the tests please use proper IDE (IntelliJ IDEA, Eclipse, VSCode).

To run the F1APP from IDE, run the <code>org.demo.FormulaOneApplication</code>

To build project the project manually use <code>$ ./gradlew build</code>

To run the built jar file use this command <code>java -jar formula-one-1.0-SNAPSHOT.jar</code>. 
By default the application is placed into the <code>builds/libs</code> folder

### H2

The F1APP uses inmemory H2 database, which is initialized with three lines of data. 
To add more configuration settings add these values to <code>application.yml</code>
  ```
   datasource:
     platform: h2
     url: jdbc:h2:~/test
     driverClassName: org.h2.Driver
     initialize: true
     username: <default user>
     password: <default password>
   sql:
     init:
     mode: always
  ```
To use file based storing change the url to <code>jdbc:h2:file:~/test</code>.
To keep the data instead of creating them every time the application starts, change <code>ddl-auto</code> to <code>update</code>  
  ```
  spring:
   jpa:
     hibernate
       ddl-auto: update     
  ```

### API test
Since the app uses basic authentication, testing the APIs (e.g. Postman) requires adding basic auth data.

The F1APP currently uses InMemoryUserDetailsManager with one user data only.

username: <code>admin</code>, password: <code>f1test2018</code>


---------------
--- Frontend ---
---------------
### Environment
Created with using Angular v17.2.0 (with **RxJS**, **NgRX** store functions and **PrimeNG** UI component)

### Setup
The project can be found in the <code>webapp</code> folder.

To run the frontend, you need installed node.js first.

Then Angular CLI is also needed: <code>npm install -g @angular/cli</code>.

To install dependencies run <code>npm install</code> command.

To run the application use the <code>npm start</code> command. The F1APP starts at http://localhost:4200/

### How to use

The F1APP currently supports CRUD operations and login functions.

Since the app uses basic authentication, which requires username and password data to be sent in the header, and I did not want to store the password in the browser (neither local nor session storage), every page refresh removes the stored password from the memory.

The user is still logged in, but functions which requires authentication will be unavailable. Logout and login is required.

The only user, which is stored in the application is: 

username: <code>admin</code>, password: <code>f1test2018</code>




