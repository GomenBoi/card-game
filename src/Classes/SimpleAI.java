package Classes;

import Interfaces.IModel;

import java.util.ArrayList;

public class SimpleAI{

    private final IModel model;

    public SimpleAI(IModel model) {
        this.model = model;
    }

    public double calculateProbability(int playerID, int handNum) {
        ArrayList<Player> players = model.getPlayers();
        Player currentPlayer = players.get(playerID);
        Hand hand = currentPlayer.getHand();
        Deck deck = currentPlayer.getDeck();

        Card currentCard = hand.getCard(handNum);

        // If the final player were to make an assisted move, if the card number is higher than all other cards, the function will by default return 1.0 (guaranteed win).
        double cardProbability = 1.0;

        // Iterate over every player
        for (Player player : players) {
            // If the current player is equal to the player, then skip.
            if (currentPlayer.equals(player)) {continue;}

            // If the player has already played a card, check if it's higher than the card in the player's hand.
            // If it is, then return 0 as there is 0 chance the player will win
            // If it isn't, then calculate the probability that the card will win.
            if (player.currentCardPlayed != null) {
                Card cardPlayed = player.currentCardPlayed;
                if (cardPlayed.getNumber() > currentCard.getNumber()) {
                    return 0.0;
                }
            } else {
                int totalCards = 0;
                int highCards = 0;

                // Construct an image of the opponent's deck
                for (int cardNum = 1; cardNum <= deck.maxDeckSize; cardNum++) {
                    int finalCardNum = cardNum;
                    // If none of the played cards is equal to the card number, then increment the total number of cards remaining
                    if (player.cardsPlayed.stream().noneMatch(card -> card.getNumber() == finalCardNum)) {
                        totalCards++;
                        // If the card number is greater than the current card's number, increase the number of high cards available
                        if (cardNum > currentCard.getNumber()) {
                            highCards++;
                        }
                    }
                }

                // Calculate the probability the card wins against the opponent
                // This is done against every player sequentially and assumes independence of events
                cardProbability *= (double) (totalCards - highCards) / totalCards;
            }
        }

        return cardProbability;
    }
}
