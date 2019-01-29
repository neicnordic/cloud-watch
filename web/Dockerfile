FROM ubuntu:16.04
RUN apt update && apt upgrade -q -y
RUN apt install -y openjdk-8-jdk-headless
RUN apt install -y maven
RUN apt install -y git-all
RUN git clone https://github.com/neicnordic/cloud-watch.git
WORKDIR cloud-watch/web
RUN mvn install
WORKDIR cloud-watch-ui

CMD mvn jetty:run
