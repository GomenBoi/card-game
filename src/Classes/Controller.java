package Classes;

import Interfaces.IController;
import Interfaces.IModel;
import Interfaces.IView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller implements IController
{
    private IModel model;
    private IView view;

    public void initialise(IModel model, IView view)
    {
        this.model = model;
        this.view = view;
    }

    public void startup()
    {
        // Gets all the players from the model
        ArrayList<Player> players = this.model.getPlayers();

        // Loops over every player to initialise their deck and hand
        // Shuffles the deck and fills up the player's hand with the max number of cards allowed
        for (Player player : players) {
            player.initialise(52, 5);
            Deck deck = player.getDeck();
            Hand hand = player.getHand();
            deck.shuffle();
            while (hand.size() < hand.maxHandSize) {
                player.drawCard();
            }
        }

        sendStartingMessages(players);

        // Sets the current player to the first player, sets the finished flag start the game and refreshes the UI
        this.model.setCurrentPlayer(0);
        this.model.setFinished(false);
        this.model.setIterations(0);

        this.view.refreshView();
    }

    private void sendStartingMessages(ArrayList<Player> players) {
        for (Player player : players) {
            if (model.getCurrentPlayer() != player.playerID) {
                view.feedbackToUser(player.playerID, "Player " + (player.playerID + 1) + ", it is not your turn.");
            } else {
                view.feedbackToUser(player.playerID, "Player " + (player.playerID + 1) + ", it is your turn. Please play a card.");
            }
        }
    }

    private int awardHighestCard(ArrayList<Player> players) {
        // This section does a linear search and finds the player ID who has the highest numbered card.
        // Initial values set to -1 as this is when no one has played any card.
        // This state should not be reachable as the game should have ended BEFORE the game can get to this state.
        // This function will return -1 if such state is reached.
        int highestNumber = -1;
        int highestPlayerID = -1;
        for (Player player : players) {
            if (player.currentCardPlayed != null) {
                if (player.currentCardPlayed.getNumber() > highestNumber) {
                    highestNumber = player.currentCardPlayed.getNumber();
                    highestPlayerID = player.playerID;
                }
            }
        }

        // Awards the player who played the highest numbered card one point and sets the current starting player to them
        players.get(highestPlayerID).playerPoints += 1;

        return highestPlayerID;
    }

    // Gets the player with highest points with a linear search and returns the ID of the highest player
    private int getHighestPoints(ArrayList<Player> players) {
        int highestPoints = 0;
        int highestPlayerID = 0;
        for (Player player : players) {
            if (player.playerPoints > highestPoints) {
                highestPoints = player.playerPoints;
                highestPlayerID = player.playerID;
            }
        }
        return highestPlayerID;
    }

    public void update()
    {
        ArrayList<Player> players = this.model.getPlayers();
        boolean isFinished = true;

        // Checks every player's hand and deck, ensuring that if ALL of them are empty, then the game is finished.
        for (Player player : players) {
            Deck deck = player.getDeck();
            Hand hand = player.getHand();
            if (!hand.isEmpty() || !deck.isEmpty()) {
                isFinished = false;
                break;
            }
        }

        // Sets the finished flag to true if the above criteria is met
        this.model.setFinished(isFinished);

        if (isFinished) {
            // Do one last count of who won the last round to award the final points
            awardHighestCard(players);
            // If the game is finished, do a linear search and find the player ID with the highest points
            int highestPlayerID = getHighestPoints(players);
            // Send a feedback message to all players, alerting them of the winning player ID
            for (Player player : players) {
                view.feedbackToUser(player.playerID, "Player " + (highestPlayerID + 1) + " has won with a total number of " + players.get(highestPlayerID).playerPoints + " points.");
            }
        } else {
            if (model.getIterations() >= players.size()) {
                // If the number of passes is equal to the total number of players, the current round is finished
                model.setIterations(0);

                int highestPlayerID = awardHighestCard(players);
                model.setCurrentPlayer(highestPlayerID);

                // Makes all players draw an additional card to top up their hand
                // Resets the current card played for the next round
                for (Player player: players) {
                    player.drawCard();
                    player.currentCardPlayed = null;
                }
            }
            sendStartingMessages(players);
        }

        // Refreshes the UI so a new hand and points are displayed for each player
        view.refreshView();
    }

    public void playCard(int playerNum, int handNum)
    {
        ArrayList<Player> players = this.model.getPlayers();
        int currentPlayer = this.model.getCurrentPlayer();

        // If the game is finished, reject the input
        if (model.hasFinished()) {
            return;
        }

        // If anyone but the current player tries to play a card, reject the input and send a feedback message
        if (currentPlayer != playerNum) {
            view.feedbackToUser(playerNum, "It is not your turn.");
            return;
        }

        // Gets the current player and their hand
        Player player = players.get(playerNum);
        Hand hand = player.getHand();

        // If index of the hand is outside the bounds of the array, reject the input
        // This should already be enforced by the regex in view, but good to have nonetheless
        if (!(handNum >= 0 && handNum < hand.maxHandSize)) {
            return;
        }

        // If index is valid but card does not exist, then reject the input and send a feedback message
        if (hand.getCard(handNum) == null) {
            view.feedbackToUser(playerNum, "Card " + (handNum + 1) + " is not in your hand, please enter a valid card in your hand.");
            return;
        }

        // Set the current card played by the player to the card in their hand that they selected
        // Removes the card from the player's hand and sets the current player to the next in the list
        // By using the '%' operator, this guarantees the current player will be in the range [0-maxPlayers]
        // Increments the iterations/passes to track the round's status and to judge when the round will end
        player.currentCardPlayed = hand.removeCard(handNum);
        model.setCurrentPlayer((currentPlayer + 1) % players.size());
        model.setIterations(model.getIterations() + 1);

        this.update();
    }

    public static void main(String[] args) {
        IController controller = new Controller();
        IModel model = new Model();
        // Text View
        IView view = new View();
        // GUI View
        //IView view = new GUI();

        model.initialise(5);
        controller.initialise(model, view);
        view.initialise(model, controller);
        controller.startup();
    }
}
