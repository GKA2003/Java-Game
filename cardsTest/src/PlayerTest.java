import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PlayerTest class to test Player.java.
 *
 * @author 221808, 202859
 * @version 1.0
 */
class PlayerTest {

    private int playerNum;
    private Player player;
    private Random random = new Random();

    @BeforeEach
    void setUp() {
        // Create a new player that's available for every test method
        playerNum = random.nextInt(50);
        player = new Player(playerNum);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetPlayerNum() {
        assertEquals(playerNum, player.getPlayerNum());
    }

    @Test
    void testWriteOutputFile() throws Exception {
        // Set filename and generate random alphanumeric string
        String filename = "gameOutputFiles/player" + playerNum + "_output.txt";
        String expectedText = Long.toHexString(Double.doubleToLongBits(Math.random()));

        // Create an instance of Player
        Player playerClass = new Player(playerNum);

        // Obtain the private method using reflection
        Method writeOutputFile = Player.class.getDeclaredMethod("writeOutputFile", String.class);
        writeOutputFile.setAccessible(true);

        // Invoke the private method on the instance
        writeOutputFile.invoke(playerClass, expectedText);

        // Expected line in output file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            assertEquals(expectedText, line);
        } catch (Exception e) {
            fail("An exception was thrown: " + e);
        }
    }

    @Test
    void testHandToString() {
        int value;
        Card card;
        String expectedHandToString = "";

        // Add cards to the hand so that we can test converting the hand to a string
        for (int i = 0; i < 4; i++) {
            value = random.nextInt(50);
            card = new Card(value);
            player.addCardToHand(card);
            expectedHandToString = expectedHandToString + value;
            // If not the last card in the hand, we want to add a space
            if (i < 3) {
                expectedHandToString = expectedHandToString + " ";
            }
        }
        assertEquals(expectedHandToString, player.handToString());
    }

    @Test
    void testAddCardToHand() {
        // For testing addCardToHand when it is the initial hand
        int value;
        Card card;
        ArrayList<Card> expectedHand = new ArrayList<Card>();
        String expectedHandToString = "";
        String filename = "gameOutputFiles/player" + playerNum + "_output.txt";

        // Add four cards to the hand to mimic being dealt the initial four cards
        for (int i = 0; i < 4; i++) {
            value = random.nextInt(50);
            card = new Card(value);
            player.addCardToHand(card);
            expectedHand.add(card);
            expectedHandToString = expectedHandToString + value;
            // If not the last card in the hand, we want to add a space
            if (i < 3) {
                expectedHandToString = expectedHandToString + " ";
            }
        }

        // Expected first line in output file
        String expectedOutput1 = ("player " + playerNum + " initial hand is " + expectedHandToString);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            assertEquals(expectedOutput1, line);
        } catch (Exception e) {
            fail("An exception was thrown: " + e);
        }

        // For testing addCardToHand when it is not the initial hand
        value = random.nextInt(50);
        card = new Card(value);
        player.addCardToHand(card);

        // Expected second line in output file
        String expectedOutput2 = ("player " + playerNum + " draws a " + value + " from deck " + playerNum);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            reader.readLine();
            String line = reader.readLine();
            assertEquals(expectedOutput2, line);
        } catch (Exception e) {
            fail("An exception was thrown: " + e);
        }
    }

    @Test
    void testChooseCardToDiscard() {
        // chooseCardToDiscard takes the total number of players as a parameter, so arbitrarily choose 50
        int numPlayers = 50;
        int value;
        Card card;
        String filename = "gameOutputFiles/player" + playerNum + "_output.txt";

        // Add one random card so the hand has five cards to mimic game
        // Keep choosing a random card until it isn't the player's preferred card
        do {
            value = random.nextInt(50);
        } while (value == playerNum);
        card = new Card(value);
        player.addCardToHand(card);

        // Add four cards to hand that match the player number
        // Hand has five cards because the player picks up a new card immediately before discarding one here
        player.addCardToHand(new Card(playerNum));
        player.addCardToHand(new Card(playerNum));
        player.addCardToHand(new Card(playerNum));
        player.addCardToHand(new Card(playerNum));

        // Discard a card
        Card discardCard = player.chooseCardToDiscard(numPlayers);

        // For testing that the discarded card value isn't equal to the player number
        assertNotEquals(playerNum, discardCard.getValue());

        // For testing that the correct output is written to the output file
        // Expected third line in output file
        String expectedOutput1 = ("player " + playerNum + " discards a " + value + " to deck " + ((playerNum % numPlayers) + 1));
        // Expected fourth line in output file
        String expectedOutput2 = ("player " + playerNum + " current hand is " + playerNum + " " + playerNum + " " + playerNum + " " + playerNum);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            reader.readLine();
            reader.readLine();
            String line = reader.readLine();
            assertEquals(expectedOutput1, line);
            line = reader.readLine();
            assertEquals(expectedOutput2, line);
        } catch (Exception e) {
            fail("An exception was thrown: " + e);
        }
    }

    @Test
    void testSameValueHands() {
        int value = random.nextInt(50);
        Card card = new Card(value);
        player.addCardToHand(card);
        player.addCardToHand(card);
        player.addCardToHand(card);

        // Test what happens if hand has less than four cards
        assertFalse(player.sameValueHands());

        // Add one more card such that all four cards are the same value
        player.addCardToHand(card);
        assertTrue(player.sameValueHands());

        // Test what happens if hand has more than four cards
        player.addCardToHand(card);
        assertFalse(player.sameValueHands());

        // Remove two cards from hand and add one that doesn't match the rest
        player.chooseCardToDiscard(50);
        player.chooseCardToDiscard(50);
        player.addCardToHand(new Card(value + 1));
        assertFalse(player.sameValueHands());
    }

    @Test
    void testPlayerHasWonSelf() {
        String filename = "gameOutputFiles/player" + playerNum + "_output.txt";

        // This player has won the game
        // Generate a winning hand
        int value = random.nextInt(50);
        Card card = new Card(value);
        player.addCardToHand(card);
        player.addCardToHand(card);
        player.addCardToHand(card);
        player.addCardToHand(card);

        player.playerHasWon(playerNum);

        // For testing that the correct output is written to the output file
        // Expected second line in output file
        String expectedOutput1 = ("player " + playerNum + " wins");
        // Expected third line in output file
        String expectedOutput2 = ("player " + playerNum + " exits");
        // Expected fourth line in output file
        String expectedOutput3 = ("player " + playerNum + " final hand is " + value + " " + value + " " + value + " " + value);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            reader.readLine();
            String line = reader.readLine();
            assertEquals(expectedOutput1, line);
            line = reader.readLine();
            assertEquals(expectedOutput2, line);
            line = reader.readLine();
            assertEquals(expectedOutput3, line);
        } catch (Exception e) {
            fail("An exception was thrown: " + e);
        }
    }

    @Test
    void testPlayerHasWonAnotherPlayer() {
        int value;
        Card card;
        ArrayList<Card> expectedHand = new ArrayList<Card>();
        String expectedHandToString = "";
        String filename = "gameOutputFiles/player" + playerNum + "_output.txt";

        // Another player has won the game
        // Generate a random, non-winning hand
        for (int i = 0; i < 4; i++) {
            value = random.nextInt(50);
            card = new Card(value);
            player.addCardToHand(card);
            expectedHand.add(card);
            expectedHandToString = expectedHandToString + value;
            // If not the last card in the hand, we want to add a space
            if (i < 3) {
                expectedHandToString = expectedHandToString + " ";
            }
        }

        int randomWinner = random.nextInt(50);
        player.playerHasWon(randomWinner);

        // For testing that the correct output is written to the output file
        // Expected second line in output file
        String expectedOutput1 = ("player " + randomWinner + " has informed player " + playerNum + " that player " + randomWinner + " has won");
        // Expected third line in output file
        String expectedOutput2 = ("player " + playerNum + " exits");
        // Expected fourth line in output file
        String expectedOutput3 = ("player " + playerNum + " hand is " + expectedHandToString);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            reader.readLine();
            String line = reader.readLine();
            assertEquals(expectedOutput1, line);
            line = reader.readLine();
            assertEquals(expectedOutput2, line);
            line = reader.readLine();
            assertEquals(expectedOutput3, line);
        } catch (Exception e) {
            fail("An exception was thrown: " + e);
        }
    }
}