FROM java:8-alpine
COPY ./cloud-messageservice-8003-1.0-SNAPSHOT.jar /tmp/cloud-messageservice-8003-1.0-SNAPSHOT.jar
ENTRYPOINT java -jar -Xms1024M -Xmx1024M -Xmn256M -Xss256K /tmp/cloud-messageservice-8003-1.0-SNAPSHOT.jar