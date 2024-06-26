package Interfaces;

public interface IController
{
    void initialise(IModel model, IView view);
    void startup();
    void update();
    void playCard(int playerNum, int handNum);
    void doAutomatedMove(int playerNum);
}
