package Classes;

import java.util.ArrayList;

public class Hand extends ArrayList<Card>
{
    public int maxHandSize;

    public Hand(int handSize) {
        super();
        this.maxHandSize = handSize;
    }

    public void addCard(Card card) {
        if (this.size() < this.maxHandSize) {
            super.add(card);
        }
    }

    public Card removeCard(int index) {
        if (!this.isEmpty()) {
            return super.remove(index);
        }
        return null;
    }

    public Card getCard(int index) {
        if (index >= 0 && index < this.size()) {
            return this.get(index);
        }
        return null;
    }
}
