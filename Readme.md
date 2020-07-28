###About
A simple html simulator is available at http://localhost:8080

####Available pieces
- ROOK
- KNIGHT
- BISHOP
- QUEEN
- KING
- PAWN

#### Setup
The project uses Java 11. For setting up refer below
- Java 11 (Use [Jenv](https://www.jenv.be) to effectively manage switching Java versions between 8 & 11)
    - For MacOS you can follow the following steps to install JENV & Java11
        - ```brew install jenv```
        - ```brew tap homebrew/cask-versions```
        - ```brew search java```
        - ```brew cask install java11```
        - ```jenv add /Library/Java/JavaVirtualMachines/openjdk-11.*.*.jdk/Contents/Home/```
        - ```jenv shell 11.0.2```
        - And you are done!
---
### Execute tests
mvn clean test

### Package
mvn clean package

### Execute from Jar
- mvn clean package
- cd [project]/target
- java -jar chess-1.0.0-SNAPSHOT.jar

### Run
- mvn clean spring-boot:run

