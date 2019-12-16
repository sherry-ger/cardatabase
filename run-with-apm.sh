#!/bin/bash
# set -x

AGENT_VERSION=1.12.0
AGENT_FILE=elastic-apm-agent-${AGENT_VERSION}.jar

if [ ! -f "${AGENT_FILE}" ]; then
  curl -O  https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/${AGENT_VERSION}/elastic-apm-agent-${AGENT_VERSION}.jar
fi

./mvnw clean package -Dmaven.test.skip=true

java -javaagent:./${AGENT_FILE} \
-Delastic.apm.server_urls="http://localhost:8200" \
-Delastic.apm.secret_token="mysecrettoken" \
-Delastic.apm.service_name="cardatabase" \
-Delastic.apm.trace_methods="com.packt.cardatabase.*" \
-Delastic.apm.enable_log_correlation=true \
-jar target/cardatabase-0.0.1-SNAPSHOT.jar

# Add this to above if you have a self signed cert for testing 
#-Delastic.apm.verify_server_cert=false \