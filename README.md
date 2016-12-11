# RMI_Distributed_Systems
# RMI String Comparison Application

- **Student Name:** Shane Gleeson
- **Student Number:** G00311793
- **College Name:** GMIT
- **Lecturer:** John Healy
- **Course:** Software Development
- **Module:** Distributed Systems


---

# Overview

For this project I created a Web application for a module called Distributed systems lectured by Dr John Healy where the application compares two strings and returns the distance for the selected Algorithm. The user will be asked to input two strings and then must select an algorithm from the dropdown list. You then must click compare and are brought on to another page where they are informed it is doing its work. From here the page will refresh every 10 seconds until your request has been processed.

# Instructions
To run this application you need a War and Jar file which is given to you above. To run this application you will also need to have apache tomcat installed [http://tomcat.apache.org/](http://tomcat.apache.org/). Once all is complete you need to put the comparator.war into the webapps folder of apache tomcats installation folder, change the name to .zip and unzip the war file and then using the command line, go to the bin directory and type startup.bat which will start tomcat. Start the RMI server then by using the command line while navigating to the directory it's in java –cp ./string-service.jar ie.gmit.sw.StringServiceServant which will then start your server. Now go to your browser and type localhost:8080/comparator for the application to run.


