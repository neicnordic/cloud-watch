FROM tomcat

RUN rm -rf /usr/local/tomcat/webapps/ROOT

RUN apt update && apt upgrade -q -y

RUN apt install -y openjdk-8-jdk-headless maven git-all

RUN git clone https://github.com/neicnordic/cloud-watch.git

WORKDIR cloud-watch/web

RUN mvn install

RUN cp cloud-watch-ui/target/cloud-watch-ui-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
