package Classes;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class Player
{
    public Card currentCardPlayed;
    public int playerPoints;
    public int playerID;
    public ArrayList<Card> cardsPlayed;
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
        this.cardsPlayed = new ArrayList<>();
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

    public void playCard(int handNum) {
        this.currentCardPlayed = hand.removeCard(handNum);
        cardsPlayed.add(this.currentCardPlayed);
    }

    // Getter for deck
    public Deck getDeck() {
        return this.deck;
    }

    // Getter for hand
    public Hand getHand() {
        return this.hand;
    }

    // Setter for deck
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    // Setter for hand
    public void setHand(Hand hand) {
        this.hand = hand;
    }
}
