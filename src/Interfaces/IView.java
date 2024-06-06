package Interfaces;

public interface IView
{
    void initialise(IModel model, IController controller);
    void refreshView();
    void setupPlayers();
    void feedbackToUser(String message);
}
