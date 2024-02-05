import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


/**
 * PackTest class to test Pack.java.
 *
 * @author 221808, 202859
 * @version 1.0
 */
public class PackTest {

    private int numPlayers;
    private int expectedCards;
    private final Random random = new Random();
    private String getFilenameForPlayers(int numPlayers) {
        return "./packs/" + numPlayers + "_players.txt";
    }

    @BeforeEach
    void setUp() {
        numPlayers = 2 + random.nextInt(3); // 2, 3 or 4 players
        expectedCards = 8 * numPlayers;
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void testPackConstructorWithValidFile() throws IOException {
        String validFilename = getFilenameForPlayers(numPlayers);
        Pack pack = new Pack(numPlayers, validFilename);

        assertNotNull(pack.getCards(), "Cards array should not be null.");
        assertEquals(expectedCards, pack.getCards().length, "Pack should contain the correct number of cards.");
    }

    @Test
    void testPackConstructorWithTooManyCards() {
        String tooManyCardsFilename = "./packs/too_many_cards.txt";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Pack(numPlayers, tooManyCardsFilename);
        });

        String expectedMessage = "too many cards";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testPackConstructorWithTooFewCards() {
        String tooFewCardsFilename = "./packs/too_few_cards.txt";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Pack(numPlayers, tooFewCardsFilename);
        });

        String expectedMessage = "too few cards";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testPackConstructorWithInvalidData() {
        String invalidDataFilename = "./packs/invalid_data.txt";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Pack(numPlayers, invalidDataFilename);
        });

        String expectedMessage = "non-integer value";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
