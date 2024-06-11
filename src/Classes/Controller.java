package Classes;

import Interfaces.IController;
import Interfaces.IModel;
import Interfaces.IView;

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

        // Sets the current player to the first player, sets the finished flag start the game and refreshes the UI
        this.model.setCurrentPlayer(0);
        this.model.setFinished(false);

        this.view.refreshView();
    }

    public void update()
    {
        ArrayList<Player> players = this.model.getPlayers();

        boolean isFinished = true;

        for (Player player : players) {
            Deck deck = player.getDeck();
            Hand hand = player.getHand();
            if (!hand.isEmpty() || !deck.isEmpty()) {
                isFinished = false;
                break;
            }
        }

        this.model.setFinished(isFinished);

        if (isFinished) {
            int highestPoints = 0;
            int highestPlayerID = 0;
            for (Player player : players) {
                if (player.playerPoints > highestPoints) {
                    highestPoints = player.playerPoints;
                    highestPlayerID = player.playerID;
                }
            }
            for (Player player : players) {
                view.feedbackToUser(player.playerID, "Player " + highestPlayerID + " has won with a total number of " + highestPoints + " points.");
            }
        } else {
            if (model.getIterations() >= players.size()) {
                model.setIterations(0);

                int highestNumber = 0;
                int highestPlayerID = 0;
                for (Player player : players) {
                    if (player.currentCardPlayed.getNumber() > highestNumber) {
                        highestNumber = player.currentCardPlayed.getNumber();
                        highestPlayerID = player.playerID;
                    }
                }
                players.get(highestPlayerID).playerPoints += 1;
                model.setCurrentPlayer(highestPlayerID);

                for (Player player: players) {
                    player.drawCard();
                    player.currentCardPlayed = null;
                }
            }
        }

        view.refreshView();
    }

    public void playCard(int playerNum, int handNum)
    {
        ArrayList<Player> players = this.model.getPlayers();
        int currentPlayer = this.model.getCurrentPlayer();

        if (model.hasFinished()) {
            return;
        }

        if (currentPlayer != playerNum) {
            return;
        }

        Player player = players.get(playerNum);
        Hand hand = player.getHand();

        if (!(handNum >= 0 && handNum < hand.maxHandSize)) {
            return;
        }

        if (hand.getCard(handNum) == null) {
            return;
        }

        player.currentCardPlayed = hand.removeCard(handNum);
        model.setCurrentPlayer((currentPlayer + 1) % players.size());
        model.setIterations(model.getIterations() + 1);

        this.update();
    }

    public static void main(String[] args) {
        IController controller = new Controller();
        IModel model = new Model();
        IView view = new View();

        model.initialise(5);
        controller.initialise(model, view);
        view.initialise(model, controller);
        controller.startup();
    }
}
