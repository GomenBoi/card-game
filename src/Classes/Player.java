package Classes;

import java.util.Collections;
import java.util.EmptyStackException;

public class Player
{
    public Card currentCardPlayed;
    public int playerPoints;
    public int playerID;
    private Deck deck;
    private Hand hand;

    public Player(int index) {
        this.playerID = index;
        this.initialise(52, 5);
    }

    public Player(int index, int deckSize, int handSize) {
        this.playerID = index;
        this.initialise(deckSize, handSize);
    }

    public void initialise(int deckSize, int handSize) {
        this.deck = new Deck(deckSize);
        this.hand = new Hand(handSize);
        this.playerPoints = 0;
        this.currentCardPlayed = null;
    }

    public void drawCard() {
        try {
            Card card = deck.pop();
            if (hand.size() < 5) {
                hand.addCard(card);
            }
        } catch (EmptyStackException e) {
            System.out.println("Empty deck, cannot draw a card.");
        }
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Hand getHand() {
        return this.hand;
    }
}
