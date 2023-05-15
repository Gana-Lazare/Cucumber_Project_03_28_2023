# Cucumber_Project_03_28_2023
Initial Project as a Portfolio using JAVA/CUCUMBER 
with:
 - Postgres Connection 
 - saucelabs or browserstack username and access key retrieved from DB , would be more efficient to have that stored in a vaulted based 
 - read configuration from properties file 
 - allows cross browser testing 
 
 Cici Pipeline :
  Tools: Jenkins 
  * the project runs from a jenkins job in order to run it same way :
       -Intall jenkins as an msi file or 
       -download jenkins war file , the navigate to location of the file and open cmd if you use windows or terminal use command java -jar jenkins.war file
  this file will start jenkins 
       -Create Multibranch project  and paste the git url in the repository section
       -in the Root directory of the project create Jenkinsfile (makes sure that the spelling is matching how its in jenkins by default or change it to the same name )
  * Please navigate to Jenkinsfile to understand what is implemented such as Creating mutiple stages and assigning different scripts and steps 
  as in this project a performance test is performed using  curl command from jenkins file and  mvn clean test as bat 
       
