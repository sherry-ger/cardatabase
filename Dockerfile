FROM openjdk:10
VOLUME /tmp
ADD target/cardatabase-0.0.1-SNAPSHOT.jar app.jar
ADD elastic-apm-agent-1.12.0.jar apm-agent.jar
ENV JAVA_OPTS="-javaagent:./apm-agent.jar"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]