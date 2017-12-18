#!/bin/bash
cd "$(dirname "$0")"/code

./gradlew fatJar
java -jar build/libs/lift-emulator-fat-1.0.jar --floors-quantity=10 --floor-height=2750 --lift-speed=1.3 --doors-open-close-time=2000