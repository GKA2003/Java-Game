import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CardTest class to test Card.java.
 *
 * @author 221808, 202859
 * @version 1.0
 */
public class CardTest {

    private Card card;
    private int randomValue;
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        randomValue = random.nextInt(50);
        card = new Card(randomValue);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCardConstructorAndGetValue() {
        assertEquals(randomValue, card.getValue(), "The value should be equal to what was passed in the constructor.");
    }

    @Test
    void testToString() {
        assertEquals(Integer.toString(randomValue), card.toString(), "The toString method should return the string representation of the card's value.");
    }
}

