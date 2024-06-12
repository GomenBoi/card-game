package Classes;

import java.util.Collections;
import java.util.Stack;

public class Deck extends Stack<Card>
{
    public int maxDeckSize;

    public Deck(int deckSize) {
        super();

        for (int index = 0; index < deckSize; index++) {
            Card card = new Card(index + 1);
            this.push(card);
        }

        this.maxDeckSize = deckSize;
    }

    public void shuffle() {
        Collections.shuffle(this);
    }

}
