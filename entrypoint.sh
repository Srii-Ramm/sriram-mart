#!/bin/sh
set -e

echo "Starting H2 server (TCP mode)..."
java -cp /opt/h2.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092 -ifNotExists -baseDir /opt/srirammart/data &

sleep 3

echo "Starting Tomcat..."
exec catalina.sh run