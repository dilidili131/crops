FROM java:8-alpine
COPY ./coud-identification-8004-1.0-SNAPSHOT.jar /tmp/coud-identification-8004-1.0-SNAPSHOT.jar
ENTRYPOINT java -jar -Xms1024M -Xmx1024M -Xmn256M -Xss256K /tmp/coud-identification-8004-1.0-SNAPSHOT.jar