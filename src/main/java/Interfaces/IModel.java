package Interfaces;

import Classes.Player;

import java.util.ArrayList;

public interface IModel
{
    void initialise(int numPlayers);
    ArrayList<Player> getPlayers();
    void setCurrentPlayer(int playerNum);
    int getCurrentPlayer();
    void setFinished(boolean finished);
    boolean hasFinished();
    void setIterations(int iterations);
    int getIterations();
}
