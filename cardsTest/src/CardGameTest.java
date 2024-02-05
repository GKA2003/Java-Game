import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CardGameTest class to test CardGame.java.
 *
 * @author 221808, 202859
 * @version 1.0
 */

public class CardGameTest {
    private int numPlayers;
    private String getFilenameForPlayers(int numPlayers) {
        return "./packs/" + numPlayers + "_players.txt";
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testRunGame(int number) {
        // Setup attributes
        numPlayers = number;
        String validFilename = getFilenameForPlayers(numPlayers);
        CardGame game = new CardGame();

        // Run the game and check it completes without exceptions
        assertDoesNotThrow(() -> {game.runGame(numPlayers, validFilename);});
    }
}
