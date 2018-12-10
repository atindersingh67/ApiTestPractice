# RESTful APIs Automation test using Rest Assured 
## Pre-requisite
1. Java above 1.8.
2. Maven version above 3.0.
3. Update invalidOrderId in application.properties file, i.e the order which should not exist in database.
4. Update baseUrl in application.properties file. 


## How to install & Run using command prompt
1. Please extract the project at your desired path.
1. Go to Resources folder and update configurations as per your requirement 
1. Open the command prompt and go to the project path.
1. Run the command "mvn clean test" to execute all tests.
1. Run "mvn surefire-report:report" command to execute all tests and generate a html report for test results.Please find html report under "target\site" directory. 


