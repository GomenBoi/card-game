package Interfaces;

public interface IView
{
    void initialise(IModel model, IController controller);
    void refreshView();
    void setupPlayers();
    void feedbackToUser(int player, String message);
}
