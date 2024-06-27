import Classes.*;

import Interfaces.*;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTests {

    @Nested
    @DisplayName("Tests to test the possible number of players")
    class testNumPlayers
    {
        @ParameterizedTest
        @DisplayName("1.0: Test valid number of people can play")
        @ValueSource(ints = {2, 3, 4})
        void testValidPlayers(int numPlayers) {
            IModel model = new Model();
            model.initialise(numPlayers);

            assertNotNull(model.getPlayers());
            assertFalse(model.hasFinished());
            assertEquals(numPlayers, model.getPlayers().size());
        }

        @ParameterizedTest
        @DisplayName("1.1: Test invalid number of people can play")
        @ValueSource(ints = {0, 1, -1, Integer.MIN_VALUE})
        void testInvalidPlayers(int numPlayers) {
            IModel model = new Model();
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
            IModel model = new Model();
            IController controller = new Controller();
            IView view = new View();

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
            IModel model = new Model();
            IController controller = new Controller();
            IView view = new View();

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


}
