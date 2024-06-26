package Classes;

import Interfaces.IModel;

import java.util.ArrayList;

public class Model implements IModel
{
    private ArrayList<Player> players;
    private int currentPlayer;
    private boolean gameFinished;
    private int updateIterations;

    // Initialises all the players given an integer, returns an error message if there are less than 2 players
    public void initialise(int numPlayers)
    {
        if (numPlayers > 1) {
            this.players = new ArrayList<>();
            this.gameFinished = false;

            for (int i = 0; i < numPlayers; i++) {
                Player player = new Player(i);
                this.players.add(player);
            }

        } else {
            System.out.println("Attempt to play game with 1 or less people.");
        }
    }

    // Getter for the player array
    public ArrayList<Player> getPlayers()
    {
       return players;
    }

    // Getter for iterations/passes of the current round
    public int getIterations() {
        return updateIterations;
    }

    // Setter for iterations/passes of the current round
    public void setIterations(int iterations) {
        this.updateIterations = iterations;
    }

    // Sets the current player if they are a valid player (within bounds of player array)
    public void setCurrentPlayer(int playerNum)
    {
        if (playerNum >= 0 && playerNum < this.players.size()) {
            this.currentPlayer = playerNum;
        }
    }

    // Getter for the current player
    public int getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    // Sets finished flag to boolean
    public void setFinished(boolean finished)
    {
        this.gameFinished = finished;
    }

    // Getter for the finished flag
    public boolean hasFinished()
    {
        return this.gameFinished;
    }
}
