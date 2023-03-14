#!/bin/bash
# set -x

AGENT_VERSION=1.36.0
AGENT_FILE=elastic-apm-agent-${AGENT_VERSION}.jar
ELASTIC_APM_SERVER_URLS="http://localhost:8200"
ELASTIC_APM_SECRET_TOKEN="mysecrettoken"

if [ ! -f "${AGENT_FILE}" ]; then
  curl -O  https://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/${AGENT_VERSION}/elastic-apm-agent-${AGENT_VERSION}.jar
fi

./mvnw clean package -Dmaven.test.skip=true

java -javaagent:./${AGENT_FILE} \
-Delastic.apm.server_urls=${ELASTIC_APM_SERVER_URLS}\
-Delastic.apm.secret_token=${ELASTIC_APM_SECRET_TOKEN} \
-Delastic.apm.service_name="cardatabase" \
-Delastic.apm.enable_log_correlation=true \
-jar target/cardatabase-0.0.1-SNAPSHOT.jar

# Add this to above if you have a self signed cert for testing 
#-Delastic.apm.profiling_inferred_spans_enabled=true \
#-Delastic.apm.verify_server_cert=false \
#-Delastic.apm.trace_methods="com.packt.cardatabase.*" \
#-Delastic.apm.transaction_sample_rat="0.5" \
