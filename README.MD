# ECM2414 CA 2023 Card Game
The following instructions are for Windows systems due to inability to test on Mac/Linux systems.

## Run the Game From cards.jar
1. Open Command Prompt in the directory where cards.jar is stored
2. Run this command: `java -jar cards.jar`
3. Type the number of players and press ENTER
4. Type the filename of the pack of cards
    - The pack file can be entered using just the filename if it is in the same directory as cards.jar
    - Otherwise, you can enter a relative path to the pack file

## Run the Game From Source Code
To run the game, run the main method of the `CardGame` class. You will be prompted to enter the number of players and a pack of cards. The pack file can be entered using just the filename if it is in the same working directory that you're running from, otherwise, you can enter a relative path to the pack file. The game will then execute and output a winner, with the output files for Players and Decks being stored in the `gameOutputFiles` folder.

### Windows Command Prompt Instructions
1. Firstly, extract `cardsTest.zip` to a folder (eg. `cardsTest`)
2. Open Command Prompt in this directory (eg. `cardsTest`)
3. Run the command `java -cp bin CardGame`
4. Type the number of players and press ENTER
5. Type the filename of the pack of cards
    - The pack file can be entered using just the filename if it is in the same directory as cards.jar
    - Otherwise, you can enter a relative path to the pack file
    - To use one of the pack files included in the `packs` directory, type `.\packs\pack_name.txt` (eg. `.\packs\4_players.txt`)

## Run the Test Suite
To run the full test suite, run the `CardGameTestSuite` class - this will automatically run the individual test files for each class.

### Windows Command Prompt Instructions
1. Firstly, extract `cardsTest.zip` to a folder (eg. `cardsTest`)
2. Open Command Prompt in this directory (eg. `cardsTest`)
3. Run the command `java -jar lib\junit-platform-console-standalone-1.9.0.jar -cp bin -c CardGameTestSuite`
4. All being well, JUnit should output that 25 tests were successful