# camunda-graphQL
Steps

A. Fetch Code from Git
B. Run mvn clean package -Dmaven.test.skip=true
C. Run below Docker file "docker build --tag arupsarkar/camunda-graphql ."
*****
FROM  camunda/camunda-bpm-platform:latest
  
ADD  camunda-graphql.war /camunda/webapps/
*******
E. Run Docker image "docker run -d --name camunda -p 8080:8080 arupsarkar/camunda-graphql"
