package Interfaces;

import Classes.*;
import java.util.ArrayList;

public interface IModel
{
    public abstract void initialise(int numPlayers);
    public ArrayList<Player> getPlayers();
    public void setCurrentPlayer(int playerNum);
    public int getCurrentPlayer();
}
