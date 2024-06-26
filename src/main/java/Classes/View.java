package Classes;

import Interfaces.IController;
import Interfaces.IModel;
import Interfaces.IView;

import java.util.ArrayList;
import java.util.Scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View implements IView
{
    private IController controller;
    private IModel model;

    // Initialises the input thread
    public void initialise(IModel model, IController controller) {
        this.controller = controller;
        this.model = model;

        new Thread(this::setupPlayers).start();
    }

    // Refreshes the UI based on the current information stored in the players array
    // If the game has finished, display restart and exit
    // If game has not finished, display information about points, current card played, deck size and hand
    // Moreover, display restart, exit and playing a card commands onto the screen
    public void refreshView() {
        ArrayList<Player> players = this.model.getPlayers();

        if (model.hasFinished()) {
            System.out.println("Do you want to play again?");
            System.out.println("Please enter one of these commands: ");
            System.out.println("[E] -> Exiting the program");
            System.out.println("[R] -> Restarting the game");
        } else {
            for (Player player : players) {
                int tempID = player.playerID + 1;

                System.out.println("Player " + tempID + "'s current points: " + player.playerPoints);
                if (player.currentCardPlayed != null) {
                    System.out.println("Player " + tempID + "'s current card played: " + player.currentCardPlayed.getNumber());
                }
                System.out.println("Player " + tempID + "'s deck size: " + player.getDeck().size());
                System.out.println("Player " + tempID + "'s current hand:");
                Hand hand = player.getHand();
                for (int counter = 0; counter < hand.size(); counter++) {
                    Card card = hand.getCard(counter);
                    System.out.print("Card " + (counter + 1) + ": " + card.getNumber() + " | ");
                }
                System.out.println();
                System.out.println();
            }

            System.out.println("Current player: Player " + (model.getCurrentPlayer() + 1));
            System.out.println("Please enter one of these commands: ");
            System.out.println("[E] -> Exiting the program");
            System.out.println("[R] -> Restarting the game");
            System.out.println("[(Player Number):(Card Number)] -> Playing a card");
            System.out.println("[(Player Number)] -> Do automated move");
        }
    }

    // Input thread runs as a while true loop, taking in:
    // E/e: Exits the program
    // The regex '^[1-5]:[1:5]$' matches an exact string such that the first and third characters are [1-5] inclusive joined by a ':'.
    // R/r: Restarts the game and resets variables
    // Otherwise: Return an error message, prompting the user to try again.
    public void setupPlayers() {
        Scanner in = new Scanner(System.in);
        while (true) {

            try {
                String input = in.nextLine();

                Pattern selectPlayerHandPattern = Pattern.compile("^[1-5]:[1-5]$");
                Matcher selectPlayerHandMatcher = selectPlayerHandPattern.matcher(input);
                boolean selectPlayerHandFound = selectPlayerHandMatcher.find();

                Pattern selectPlayerPattern = Pattern.compile("^[1-5]$");
                Matcher selectPlayerMatcher = selectPlayerPattern.matcher(input);
                boolean selectPlayerMatchFound = selectPlayerMatcher.find();

                if (input.toLowerCase().compareTo("e") == 0) {
                    System.exit(0);
                }
                else if (input.toLowerCase().compareTo("r") == 0) {
                    controller.startup();
                } else if (selectPlayerHandFound) {
                    String[] parameters = input.split(":");

                    int playerNumber = Integer.parseInt(parameters[0]);
                    int handNumber = Integer.parseInt(parameters[1]);

                    controller.playCard(playerNumber - 1, handNumber - 1);
                } else if (selectPlayerMatchFound) {
                    int playerNumber = Integer.parseInt(input);

                    controller.doAutomatedMove(playerNumber - 1);
                }else {
                    System.out.println("Sorry, the input you entered is invalid. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Sorry, it appears you haven't entered anything. Please try again.");
            }
        }
    }

    // Sends a feedback message addressed to the player with attached message
    public void feedbackToUser(int player, String message) {
        System.out.println("Message to player " + (player + 1) + ": " + message);
    }
}
