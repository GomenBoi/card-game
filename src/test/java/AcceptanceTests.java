import Classes.*;

import Interfaces.*;

import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTests {

    IModel model;
    IController controller;
    IView view;

    @BeforeEach
    void setupMVC() {
        model = new Model();
        controller = new Controller();
        view = new View();
    }

    static void forgeHand(Player player, int[] handNumbers) {
        Hand newHand = new Hand(52);
        for (int integer : handNumbers) {
            newHand.addCard(new Card(integer));
        }
        player.setHand(newHand);
    }

    @Nested
    @DisplayName("1. Tests to test the possible number of players")
    class testNumPlayers
    {
        @ParameterizedTest
        @DisplayName("1.1: Test valid number of people can play")
        @ValueSource(ints = {2, 3, 4})
        void testValidPlayers(int numPlayers) {
            model.initialise(numPlayers);

            assertNotNull(model.getPlayers());
            assertFalse(model.hasFinished());
            assertEquals(numPlayers, model.getPlayers().size());
        }

        @ParameterizedTest
        @DisplayName("1.2: Test invalid number of people can play")
        @ValueSource(ints = {0, 1, -1, Integer.MIN_VALUE})
        void testInvalidPlayers(int numPlayers) {
            model.initialise(numPlayers);

            assertNull(model.getPlayers());
            assertFalse(model.hasFinished());
        }
    }

    @Nested
    @DisplayName("2. Tests to test whether player can make moves")
    class testCanMakeMove
    {
        @Test
        @DisplayName("2.1: Test player can make their own move")
        void testNormalMove() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            try {
                controller.playCard(0, 1);
            } catch (Exception e) {
                fail("Exception thrown when playing valid move for player 1.");
            }

            try {
                controller.playCard(1, 1);
            } catch (Exception e) {
                fail("Exception thrown when playing valid move for player 2.");
            }
        }

        @Test
        @DisplayName("2.2: Test player can make automated move")
        void testAutomatedMove() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            try {
                controller.doAutomatedMove(0);
            } catch (Exception e) {
                fail("Exception thrown when playing valid automated move for player 1");
            }

            try {
                controller.doAutomatedMove(1);
            } catch (Exception e) {
                fail("Exception thrown when playing valid automated move for player 2.");
            }
        }
    }

    @Nested
    @DisplayName("3. Tests to test functionality of a player's deck")
    class testDeck {
        @Test
        @DisplayName("3.1: Test player has deck")
        void testHasDeck() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            for (Player player : model.getPlayers()) {
                assertNotNull(player.getDeck());
            }
        }

        @Test
        @DisplayName("3.2: Test player's deck size")
        void testDeckSize() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            for (Player player : model.getPlayers()) {
                Deck deck = player.getDeck();

                assertEquals(52, deck.size());
                for (Card card : deck) {
                    int cardNumber = card.getNumber();
                    assertTrue(cardNumber > 0 && cardNumber <= 52);
                }
            }
        }

        @Test
        @DisplayName("3.3: Test player's deck is shuffled")
        void testDeckShuffle() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            // Create new list of integers [1-52] inclusive
            List<Integer> listIntegers = IntStream.rangeClosed(1, 52).boxed().toList();
            for (Player player : model.getPlayers()) {
                Deck deck = player.getDeck();
                // Convert deck (Stack<Card> into List<Integer> using .stream and .map functions)
                List<Integer> list = deck.stream().map(Card::getNumber).toList();
                // If the current list of integers from the deck is equal to the default list of integers, the deck has not been shuffled
                if (listIntegers.equals(list)) {
                    fail("Deck has not been shuffled.");
                }
            }
        }
    }

    @Nested
    @DisplayName("4. Test functionality of player's hand")
    class testHand {
        @Test
        @DisplayName("4.1: Test player has hand")
        void testHasHand() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            for (Player player : model.getPlayers()) {
                assertNotNull(player.getHand());
            }
        }

        @Test
        @DisplayName("4.2: Test player draws 5 cards")
        void testDrawsCards() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            for (Player player : model.getPlayers()) {
                assertEquals(5, player.getHand().size());
            }
        }
    }

    @Nested
    @DisplayName("5. Test logic of cards played in current round")
    class testCardRoundLogic {
        @Test
        @DisplayName("5.1: Test highest numbered card wins")
        void testHighCardWins() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            Player playerOne = model.getPlayers().get(0);
            Player playerTwo = model.getPlayers().get(1);

            int playerTwoPoints = playerTwo.playerPoints;

            // Create new hand and add pre-set cards for testing
            forgeHand(playerOne, new int[]{1});
            forgeHand(playerTwo, new int[]{20});

            controller.playCard(0, 0);
            controller.playCard(1, 0);

            assertEquals(playerTwoPoints + 1, playerTwo.playerPoints);
        }

        @Test
        @DisplayName("5.2: Test played cards are removed from hand")
        void testCardRemoved() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            for (Player player : model.getPlayers()) {
                Card card = player.getHand().getCard(0);
                controller.playCard(player.playerID, 0);
                assertFalse(player.getHand().contains(card) || player.getDeck().contains(card));
            }
        }

        @Test
        @DisplayName("5.3: Test all players draw another card from deck")
        void testAllPlayersDraw() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            Player playerOne = model.getPlayers().get(0);
            Player playerTwo = model.getPlayers().get(1);

            controller.playCard(0, 0);
            controller.playCard(1, 0);

            assertEquals(5, playerOne.getHand().size());
            assertEquals(5, playerTwo.getHand().size());
            assertEquals(46, playerOne.getDeck().size());
            assertEquals(46, playerTwo.getDeck().size());
        }

        @Test
        @DisplayName("5.4: Test player can't draw card from deck")
        void testPlayerCanNotDraw() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            Player playerOne = model.getPlayers().get(0);

            while (!playerOne.getDeck().isEmpty()) {
                controller.playCard(0, 0);
                controller.playCard(1, 0);
            }

            controller.playCard(0, 0);
            controller.playCard(1, 0);

            assertEquals(4, playerOne.getHand().size());
            assertEquals(0, playerOne.getDeck().size());
        }
    }

    @Nested
    @DisplayName("6. Test end of round logic")
    class testEndOfRoundLogic {
        @Test
        @DisplayName("6.1: Test winner is current player")
        void testWinnerIsCurrentPlayer() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            Player playerOne = model.getPlayers().get(0);
            Player playerTwo = model.getPlayers().get(1);

            // Create new hand and add pre-set cards for testing
            forgeHand(playerOne, new int[]{1});
            forgeHand(playerTwo, new int[]{20});

            controller.playCard(0, 0);
            controller.playCard(1, 0);

            // Assert that winning player is current player (player 2 should be current player)
            assertEquals(model.getCurrentPlayer(), 1);
        }

        @Test
        @DisplayName("6.2: Test round order from current player")
        void testEndRoundOrder() {
            model.initialise(4);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            Player playerOne = model.getPlayers().get(0);
            Player playerTwo = model.getPlayers().get(1);
            Player playerThree = model.getPlayers().get(2);
            Player playerFour = model.getPlayers().get(3);

            // Create new hand and add pre-set cards for testing
            forgeHand(playerOne, new int[]{1});
            forgeHand(playerTwo, new int[]{20});
            forgeHand(playerThree, new int[]{42});
            forgeHand(playerFour, new int[]{5});


            controller.playCard(0, 0);
            controller.playCard(1, 0);
            controller.playCard(2, 0);
            controller.playCard(3, 0);

            // Play cards in order Player: 3, 4, 1, 2 and assert on each iteration
            assertEquals(model.getCurrentPlayer(), 2);
            controller.playCard(2, 0);
            assertEquals(model.getCurrentPlayer(), 3);

            controller.playCard(3, 0);
            assertEquals(model.getCurrentPlayer(), 0);

            controller.playCard(0, 0);
            assertEquals(model.getCurrentPlayer(), 1);
        }
    }

    @Nested
    @DisplayName("7. Test the game's logic at the start and end")
    class testEndToEnd {
        @Test
        @DisplayName("7.1: Test game start to finish")
        void testGameStartToFinish() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            Player playerOne = model.getPlayers().get(0);
            Player playerTwo = model.getPlayers().get(1);

            while (!model.hasFinished()) {
                controller.playCard(0, 0);
                controller.playCard(1, 0);
            }

            assertEquals(0, playerOne.getHand().size());
            assertEquals(0, playerOne.getDeck().size());
            assertEquals(0, playerTwo.getHand().size());
            assertEquals(0, playerTwo.getDeck().size());
        }

        @Test
        @DisplayName("7.2: Test winning player with highest points")
        void testWinningPlayer() {
            PrintStream originalOut = System.out;
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            while (!model.hasFinished()) {
                controller.playCard(0, 0);
                controller.playCard(1, 0);
            }

            // Fetch max player point player
            Player winningPlayer = null;
            int greatestPoints = 0;

            for (Player player : model.getPlayers()) {
                if (player.playerPoints > greatestPoints) {
                    greatestPoints = player.playerPoints;
                    winningPlayer = player;
                }
            }

            assertNotNull(winningPlayer);

            String expectedOutput = "Player " + (winningPlayer.playerID + 1) + " has won with a total number of " + winningPlayer.playerPoints + " points.";

            assertTrue(outContent.toString().contains(expectedOutput));
            System.setOut(originalOut);
        }
    }

    @Nested
    @DisplayName("8. Test player text UI")
    class testTextUI {
        private final PrintStream originalOut = System.out;
        private ByteArrayOutputStream outContent;

        @BeforeEach
        void setUp() {
            outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
        }

        @AfterEach
        void tearDown() {
            System.setOut(originalOut);
        }

        @Test
        @DisplayName("8.1: Test UI displays hand")
        void testDisplayHand() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            Player playerOne = model.getPlayers().get(0);
            Player playerTwo = model.getPlayers().get(1);

            forgeHand(playerOne, new int[]{1, 2, 3, 4, 23});
            forgeHand(playerTwo, new int[]{20, 15, 2, 8, 10});

            view.refreshView();

            assertTrue(outContent.toString().contains("Card 1: 1 | Card 2: 2 | Card 3: 3 | Card 4: 4 | Card 5: 23 |"));
            assertTrue(outContent.toString().contains("Card 1: 20 | Card 2: 15 | Card 3: 2 | Card 4: 8 | Card 5: 10 |"));
        }

        @Test
        @DisplayName("8.2: Test UI displays points")
        void testDisplayPoints() {
            model.initialise(2);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            assertTrue(outContent.toString().contains("Player 1's current points: 0"));
            assertTrue(outContent.toString().contains("Player 2's current points: 0"));

            Player playerOne = model.getPlayers().get(0);
            Player playerTwo = model.getPlayers().get(1);

            forgeHand(playerOne, new int[]{1});
            forgeHand(playerTwo, new int[]{20});

            controller.playCard(0, 0);
            controller.playCard(1, 0);

            assertTrue(outContent.toString().contains("Player 1's current points: 0"));
            assertTrue(outContent.toString().contains("Player 2's current points: 1"));
        }
    }

    @Nested
    @DisplayName("Test automated functionality")
    class testAutomatedFunctionality {
        @Test
        @DisplayName("9.1: Test AI move")
        void testAutomatedMove() {
            model.initialise(3);
            controller.initialise(model, view);
            view.initialise(model, controller);

            controller.startup();

            Player playerOne = model.getPlayers().get(0);
            Player playerTwo = model.getPlayers().get(1);
            Player playerThree = model.getPlayers().get(2);

            forgeHand(playerOne, new int[]{1, 2, 3, 4, 5});
            forgeHand(playerTwo, new int[]{20, 10, 2, 3, 4});
            forgeHand(playerThree, new int[]{1, 5, 8, 3, 9});

            controller.doAutomatedMove(0);
            assertEquals(5, playerOne.currentCardPlayed.getNumber());
            controller.doAutomatedMove(1);
            assertEquals(20, playerTwo.currentCardPlayed.getNumber());
            controller.doAutomatedMove(2);

            // Player 2 wins
            assertEquals(playerTwo.playerPoints, 1);

            forgeHand(playerOne, new int[]{5, 2, 3, 4, 5});
            forgeHand(playerTwo, new int[]{40, 10, 2, 3, 4});
            forgeHand(playerThree, new int[]{20, 13, 8, 3, 9});

            controller.doAutomatedMove(1);
            assertEquals(40, playerTwo.currentCardPlayed.getNumber());

            // Player 1 and 3 will play lowest cards to minimise loss
            controller.doAutomatedMove(2);
            assertEquals(3, playerThree.currentCardPlayed.getNumber());
            controller.doAutomatedMove(0);

            assertEquals(2, playerTwo.playerPoints);

            forgeHand(playerOne, new int[]{1, 3, 5, 4, 5});
            forgeHand(playerTwo, new int[]{20, 10, 2, 3, 4});
            forgeHand(playerThree, new int[]{40, 5, 8, 3, 9});

            // Player 3 will play 40 to beat 20, 1 will play lowest card to minimise loss
            controller.doAutomatedMove(1);
            assertEquals(20, playerTwo.currentCardPlayed.getNumber());

            controller.doAutomatedMove(2);
            assertEquals(40, playerThree.currentCardPlayed.getNumber());

            controller.doAutomatedMove(0);

            assertEquals(1, playerThree.playerPoints);
        }
    }
}
