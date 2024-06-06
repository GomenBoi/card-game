package Interfaces;

import Classes.*;
import java.util.ArrayList;

public interface IModel
{
    void initialise(int numPlayers);
    ArrayList<Player> getPlayers();
    void setCurrentPlayer(int playerNum);
    int getCurrentPlayer();
    void setFinished(boolean finished);
    boolean hasFinished();
}
