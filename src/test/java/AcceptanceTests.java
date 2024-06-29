import Classes.*;

import Interfaces.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Nested
    @DisplayName("Tests to test the possible number of players")
    class testNumPlayers
    {
        @ParameterizedTest
        @DisplayName("1.0: Test valid number of people can play")
        @ValueSource(ints = {2, 3, 4})
        void testValidPlayers(int numPlayers) {
            model.initialise(numPlayers);

            assertNotNull(model.getPlayers());
            assertFalse(model.hasFinished());
            assertEquals(numPlayers, model.getPlayers().size());
        }

        @ParameterizedTest
        @DisplayName("1.1: Test invalid number of people can play")
        @ValueSource(ints = {0, 1, -1, Integer.MIN_VALUE})
        void testInvalidPlayers(int numPlayers) {
            model.initialise(numPlayers);

            assertNull(model.getPlayers());
            assertFalse(model.hasFinished());
        }
    }

    @Nested
    @DisplayName("Tests to test whether player can make moves")
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
    @DisplayName("Tests to test functionality of a player's deck")
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
            listIntegers.forEach(System.out::println);
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


}
