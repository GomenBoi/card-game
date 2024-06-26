package Classes;

import java.util.Collections;
import java.util.Stack;

// Wrapper class for stack class
public class Deck extends Stack<Card>
{
    public int maxDeckSize;

    // Constructs a stack of cards based on the length of the deck size, such that every card is numbered from [1-deckSize]
    public Deck(int deckSize) {
        for (int index = 0; index < deckSize; index++) {
            Card card = new Card(index + 1);
            this.push(card);
        }

        this.maxDeckSize = deckSize;
    }

    // Shuffles the deck randomly
    public void shuffle() {
        Collections.shuffle(this);
    }

}
