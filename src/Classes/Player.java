package Classes;

import java.util.EmptyStackException;

public class Player
{
    public Card currentCardPlayed;
    public int playerPoints;
    public int playerID;
    public Deck deck;
    public Hand hand;

    public Player(int index) {
        this.playerID = index;
        this.initialise();
    }

    public void initialise() {
        Deck deck = new Deck(52);
        Hand hand = new Hand(5);
        playerPoints = 0;
        currentCardPlayed = null;
    }

    public void drawCard() {
        try {
            Card card = deck.pop();
            if (hand.cards.size() < 5) {
                hand.addCard(card);
            }
        } catch (EmptyStackException e) {
            System.out.println("Empty deck, cannot draw a card.");
        }
    }
}
