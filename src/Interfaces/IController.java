package Interfaces;

public interface IController
{
    public abstract void initialise(IModel model, IView view);
    public abstract void startup();
    public abstract void update();
    public abstract void playCard(int playerNum, int handNum);
    public abstract void reorderPlayers();
}
