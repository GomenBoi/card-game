package Classes;

import java.util.EmptyStackException;

public class Player
{
    public Card currentCardPlayed;
    public int playerPoints;
    public int playerID;
    private Deck deck;
    private Hand hand;

    // Constructor for default deck size and hand size
    public Player(int index) {
        this.playerID = index;
        this.initialise(52, 5);
    }

    // Constructor for custom deck size and hand size
    public Player(int index, int deckSize, int handSize) {
        this.playerID = index;
        this.initialise(deckSize, handSize);
    }

    // Initialises the deck, hand, player points and current card played by setting them to default values
    public void initialise(int deckSize, int handSize) {
        this.deck = new Deck(deckSize);
        this.hand = new Hand(handSize);
        this.playerPoints = 0;
        this.currentCardPlayed = null;
    }

    // Draws a card from the deck and places into hand, returns error message if deck is empty
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

    // Getter for deck
    public Deck getDeck() {
        return this.deck;
    }

    // Getter for hand
    public Hand getHand() {
        return this.hand;
    }
}
