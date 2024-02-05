import org.junit.platform.suite.api.*;

@Suite
@SelectClasses({CardDeckTest.class, CardGameTest.class, CardTest.class, PackTest.class, PlayerTest.class})
public class CardGameTestSuite {}
