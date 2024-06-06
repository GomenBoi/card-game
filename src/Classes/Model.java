package Classes;

import Interfaces.IModel;

import java.util.ArrayList;

public class Model implements IModel
{
    private ArrayList<Player> players;
    private int currentPlayer;
    private ArrayList<Card> cardsPlayedInRound;
    private ArrayList<Card> cardsPlayedInGame;
    private boolean gameFinished;

    public void initialise(int numPlayers)
    {
        if (numPlayers > 1) {
            this.players = new ArrayList<>();
            this.cardsPlayedInGame = new ArrayList<>();
            this.cardsPlayedInRound = new ArrayList<>();
            this.gameFinished = false;

            for (int i = 0; i < numPlayers; i++) {
                Player player = new Player();
                this.players.add(player);
            }

            this.currentPlayer = 1;
        } else {
            System.out.println("Attempt to play game with 1 or less people.");
        }
    }

    public ArrayList<Player> getPlayers()
    {
       return players;
    }

    public void setCurrentPlayer(int playerNum)
    {
        if (playerNum >= 0 && playerNum < this.players.size()) {
            this.currentPlayer = playerNum;
        }
    }

    public int getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    public void setFinished(boolean finished)
    {
        this.gameFinished = finished;
    }

    public boolean hasFinished()
    {
        return this.gameFinished;
    }
}
