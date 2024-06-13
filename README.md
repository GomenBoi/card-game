# Card Game

## Description
- This project uses the MVC architecture in Java to create a simple card game which involves a variable number of players using numbered cards to rack up points to determine who wins a certain round. The person who has the most amount of points wins the entire game.

## Requirements
- [Requirements Brief](docs/RequirementsBrief.md)
- Java version: 22.0.1

## Usage
- **Fork** the repository
- **Clone** the repository onto your local machine:

      git clone https://github.com/GomenBoi/card-game
- Otherwise, simply just download the repository as a zip file using the **Code** button in green.
- **Import** the project onto your IDE (IntelliJ, Eclipse, VSCode, etc.)
- **Navigate** to the main method on the **Controller** class and run it
- The program should then start running, follow the instructions displayed.

## Customisation 
- By default, the controller will run the text view, but I have implemented a small GUI view.
  - By replacing the line:
             
        IView view = new View();
  - by:

        IView view = new GUI();
- I might make a small text UI which allows you to enter the number of players and type of UI you want. 
- This may also allow you to select AIs for each player as well.