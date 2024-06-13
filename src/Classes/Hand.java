package Classes;

import java.util.ArrayList;

// Wrapper class for array list
public class Hand extends ArrayList<Card>
{
    public int maxHandSize;

    // Initialises the arraylist and sets the max hand size
    public Hand(int handSize) {
        this.maxHandSize = handSize;
    }

    // Adds a card to the arraylist if the current size is less than the max size
    public void addCard(Card card) {
        if (this.size() < this.maxHandSize) {
            super.add(card);
        }
    }

    // Removes a card from the arraylist if the hand is non-empty
    public Card removeCard(int index) {
        if (!this.isEmpty()) {
            return super.remove(index);
        }
        return null;
    }

    // Gets a card if the index provided is within the range, or returns null if no card found
    public Card getCard(int index) {
        if (index >= 0 && index < this.size()) {
            return this.get(index);
        }
        return null;
    }
}
