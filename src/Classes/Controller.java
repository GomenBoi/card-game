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
        ArrayList<Player> players = this.model.getPlayers();

        for (Player player : players) {
            player.initialise();
            player.deck.shuffle();
            while (player.hand.cards.size() < player.hand.maxHandSize) {
                player.drawCard();
            }
        }

        this.model.setCurrentPlayer(1);
        this.model.setFinished(false);

        this.view.refreshView();
    }

    public void update()
    {
        ArrayList<Player> players = this.model.getPlayers();
        int currentPlayerID = this.model.getCurrentPlayer();

        boolean isFinished = true;

        for (Player player : players) {
            if (!player.hand.cards.isEmpty() || !player.deck.cards.isEmpty()) {
                isFinished = false;
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
            if (currentPlayerID == players.size() - 1) {
                int highestNumber = 0;
                int highestPlayerID = 0;
                for (Player player : players) {
                    if (player.currentCardPlayed.getNumber() > highestNumber) {
                        highestNumber = player.currentCardPlayed.getNumber();
                        highestPlayerID = player.playerID;
                    }
                }
                players.get(highestPlayerID).playerPoints += 1;
                reorderPlayers(highestPlayerID);
            }
        }
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

        if (!(handNum >= 1 && handNum <= player.hand.maxHandSize)) {
            return;
        }

        player.currentCardPlayed = player.hand.removeCard(handNum);

        this.update();
    }

    public void reorderPlayers(int startingPlayer)
    {

    }
}
