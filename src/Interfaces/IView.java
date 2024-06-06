package Interfaces;

public interface IView
{
    public abstract void initialise(IModel model, IController controller);
    public abstract void refreshView();
    public abstract void setupPlayers();
    public abstract void feedbackToUser(String message);
}
