#!/bin/bash
cd /home/pi/deploy/HelloPi
DISPLAY=:0 XAUTHORITY=/home/pi/.Xauthority java  -XX:+UseZGC -Xmx1G  -jar HelloPi.jar
exit 0
