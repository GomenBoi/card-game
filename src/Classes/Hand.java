package Classes;

import java.util.ArrayList;

public class Hand
{
    public ArrayList<Card> cards;
    public int maxHandSize;

    public Hand(int handSize) {
        this.cards = new ArrayList<>();

        for (int index = 0; index < handSize; index++) {
            Card card = new Card(index);
            this.cards.add(card);
        }

        this.maxHandSize = handSize;
    }

    public void addCard(Card card) {
        if (this.cards.size() < this.maxHandSize) {
            this.cards.add(card);
        }
    }

    public Card removeCard(int index) {
        if (!this.cards.isEmpty()) {
            return this.cards.remove(index);
        }
        return null;
    }

    public Card getCard(int index) {
        if (index < 0 || index >= this.cards.size()) {
            return cards.get(index);
        }
        return null;
    }
}
