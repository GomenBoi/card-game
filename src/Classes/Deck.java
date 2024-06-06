package Classes;

import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

public class Deck
{
    public Stack<Card> cards;
    public int maxDeckSize;

    public Deck(int deckSize) {
        this.cards = new Stack<Card>();

        for (int index = 0; index < deckSize; index++) {
            Card card = new Card(index);
            this.cards.push(card);
        }

        this.maxDeckSize = deckSize;
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public Card peek() {
        return this.cards.peek();
    }

    public Card pop() {
        return this.cards.pop();
    }

}
