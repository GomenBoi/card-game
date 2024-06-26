import Classes.Model;

import Interfaces.*;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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


}
