# Rulemasters World
Rulemasters World was a game that I have worked on.
The goal was to create a MMO Framework based on scripting systems, to develop the Rulemasters World game.
I'm still working on the project when I have time, but I want to let the sources online, and wanna make this project alive, by getting it open-source.
The final goal of the project is to make an open-source, script-based (actually Full TCP and prediction) MMO framework.

### How to use
#### Requirements
This project requires thoes to be installed on your system:
- Java 7+
- Maven (needs oracle java version for build, not openjdk)

All other requirements are in the "maven-repo" directory.

To build the server part, use this command:
```bash
cd server
mvn package
```
To run the server:
```bash
cd target
java -jar SERVER_JAR
```
To build the client part, use this command:
```bash
cd client
mvn package
```
To run the client:
```bash
cd target
java -Djava.library.path=natives/ -jar CLIENT_JAR
```
