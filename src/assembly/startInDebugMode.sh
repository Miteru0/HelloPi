#!/bin/bash
cd /home/pi/deploy/HelloPi
pkill java
DISPLAY=:0 XAUTHORITY=/home/pi/.Xauthority java -XX:+UseZGC -Xmx1G -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 -jar HelloPi.jar
exit 0
