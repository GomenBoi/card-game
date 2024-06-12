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

    public void initialise(IModel model, IController controller) {
        this.controller = controller;
        this.model = model;

        new Thread(this::setupPlayers).start();
    }

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
        }
    }

    public void setupPlayers() {
        Scanner in = new Scanner(System.in);
        while (true) {

            try {
                String input = in.nextLine();

                Pattern pattern = Pattern.compile("^[1-5]:[1-5]$");
                Matcher matcher = pattern.matcher(input);
                boolean matchFound = matcher.find();

                if (input.toLowerCase().compareTo("e") == 0) {
                    System.exit(0);
                } else if (matchFound) {
                    String[] parameters = input.split(":");

                    int playerNumber = Integer.parseInt(parameters[0]);
                    int handNumber = Integer.parseInt(parameters[1]);

                    controller.playCard(playerNumber - 1, handNumber - 1);
                } else if (input.toLowerCase().compareTo("r") == 0) {
                    controller.startup();
                } else {
                    System.out.println("Sorry, the input you entered is invalid. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Sorry, it appears you haven't entered anything. Please try again.");
            }
        }
    }

    public void feedbackToUser(int player, String message) {
        System.out.println("Message to player " + (player + 1) + ": " + message);
    }
}
