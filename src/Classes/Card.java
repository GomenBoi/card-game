package Classes;

public class Card
{
    private int number;

    // Constructor which sets the number of the card
    public Card(int number) {
        this.setNumber(number);
    }

    // Getter for the card number
    public int getNumber() {
        return this.number;
    }

    // Setter for the card number
    public void setNumber(int number) {
        this.number = number;
    }
}
