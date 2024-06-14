package Classes;

import Interfaces.IController;
import Interfaces.IModel;
import Interfaces.IView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI implements IView {

    private IModel model;
    private IController controller;
    private final ArrayList<JLabel> messageLabels = new ArrayList<>();
    private final ArrayList<InfoLabel> infoLabels = new ArrayList<>();
    private final ArrayList<CardButton> cardButtons = new ArrayList<>();

    public void initialise(IModel model, IController controller) {
        this.model = model;
        this.controller =  controller;

        this.setupPlayers();
    }

    private static class CardButton extends JButton {
        private final IModel model;
        private final int cardIndex;
        private final int playerNum;

        public CardButton(IModel model, IController controller, int playerNum, int cardIndex) {
            this.model = model;
            this.cardIndex = cardIndex;
            this.playerNum = playerNum;

            this.setPreferredSize(new Dimension(50, 50));

            this.addActionListener(e -> {controller.playCard(playerNum, cardIndex);});
        }

        protected void updateCardButton() {
            ArrayList<Player> players = model.getPlayers();
            Player player = players.get(playerNum);
            Hand hand = player.getHand();
            Card card = hand.getCard(cardIndex);

            if (card != null) {
                int cardNum = card.getNumber();
                this.setText("Card number: " + cardNum);
            } else {
                this.setText("No card in this slot.");
            }
        }
    }

    private static class InfoLabel extends JLabel {
        private final IModel model;
        private final int playerNum;

        public InfoLabel(IModel model, String text, int playerNum) {
            super(text);
            this.model = model;

            this.playerNum = playerNum;
        }

        protected void updateInfo() {
            ArrayList<Player> players = model.getPlayers();
            Player player = players.get(playerNum);
            Deck deck = player.getDeck();
            String out = "";

            int playerPoints = player.playerPoints;

            out += "Player points: " + playerPoints + ", ";

            if (player.currentCardPlayed != null) {
                out += "Current card played: " + player.currentCardPlayed.getNumber() + ", ";
            } else {
                out += "Current card played: None, ";
            }

            out += "Deck size: " + deck.size();

            this.setText(out);
        }
    }

    public void refreshView() {
        for (CardButton cardButton : cardButtons) {
            cardButton.updateCardButton();
        }
        for (InfoLabel infoLabel : infoLabels) {
            infoLabel.updateInfo();
        }
    }

    public void setupPlayers() {
        ArrayList<Player> players = model.getPlayers();

        for (Player player : players) {
            int tempID = player.playerID + 1;
            Hand hand = player.getHand();
            Deck deck = player.getDeck();

            // Creates a new window for each player
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Player " + tempID);

            // Creates the outer frame with a border layout
            JPanel outerPanel = new JPanel();
            outerPanel.setLayout(new BorderLayout());

            JPanel upperGrid = new JPanel();
            upperGrid.setLayout(new GridLayout(1, 2));

            JLabel messageLabel = new JLabel("Initialised for player " + tempID);
            upperGrid.add(messageLabel);
            InfoLabel infoLabel = new InfoLabel(model, "Player points: 0, Current card played: None, Deck size: " + deck.maxDeckSize, player.playerID);
            upperGrid.add(infoLabel);

            infoLabels.add(infoLabel);
            messageLabels.add(messageLabel);
            outerPanel.add(upperGrid, BorderLayout.NORTH);

            JPanel innerGrid = new JPanel();
            innerGrid.setLayout(new GridLayout(1, hand.maxHandSize));

            for (int i = 0; i < hand.maxHandSize; i++) {
                CardButton cardButton = new CardButton(model, controller, player.playerID, i);
                innerGrid.add(cardButton);
                cardButtons.add(cardButton);
            }

            outerPanel.add(innerGrid, BorderLayout.CENTER);

            JButton restartButton = new JButton("Restart");
            restartButton.addActionListener(e -> {controller.startup();});
            outerPanel.add(restartButton, BorderLayout.SOUTH);

            // Adds the outer frame to the window frame and sets it to visible
            frame.getContentPane().add(outerPanel);
            frame.pack();
            frame.setVisible(true);
        }
    }

    public void feedbackToUser(int player, String message) {
        JLabel messageLabel = messageLabels.get(player);
        messageLabel.setText(message);
    }
}
