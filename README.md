# Card Game

## Description
- This project uses the MVC architecture in Java to create a simple card game which involves a variable number of players using numbered cards to rack up points to determine who wins a certain round. The person who has the most amount of points wins the entire game.

## User Requirements
- [Requirements Brief](docs/RequirementsBrief.md)

## System Requirements
- [Java version: 22.0.1](https://www.oracle.com/uk/java/technologies/downloads/)
- [Maven version: 3.9.8](https://maven.apache.org/download.cgi)

## Usage
- **Fork** the repository
- **Clone** the repository onto your local machine:

      git clone https://github.com/GomenBoi/card-game
- Otherwise, simply just download the repository as a zip file using the **Code** button in green.

- For IntelliJ users, download the Maven plugins and add **framework support** for the project.
- Otherwise, you can run this project on command line in a few easy steps:
  - **cd** to the directory the project is currently in
  - **mvn clean install**
  - **java -jar target/card-game-1.0.0.jar**
- The program should then start running, follow the instructions displayed.

## Customisation 
- By default, the controller will run the text view, but I have implemented a small GUI view.
  - To do this, replace the line in the controller main method where:
             
        IView view = new View();
  - by:

        IView view = new GUI();