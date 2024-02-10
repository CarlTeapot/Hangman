

import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

import java.io.IOException;
import java.util.ArrayList;

public class Hangman extends ConsoleProgram {
    HangmanLexicon lexicon;
    RandomGenerator rgen = RandomGenerator.getInstance();
    String word = "";
    char[] letters = null;
    private int guesses = 8;
    ArrayList<String> guessedChars;

    private HangmanCanvas canvas;

    public void run() {
        try {
            lexicon = new HangmanLexicon();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        playGame();
    }

    public void init() {
        canvas = new HangmanCanvas();
        add(canvas);
    }

    /**
     * draw() method writes the introduction text,
     * randomly chooses a word from the lexicon and converts the word to an array of characters
     */
    private void draw() {
        guessedChars = new ArrayList<>();
        println("Welcome to Hangman!");
        int random = rgen.nextInt(0, lexicon.getWordCount());
        word = lexicon.getWord(random);
        letters = word.toCharArray();
    }

    /**
     * this method writes the necessary messages
     *
     * @param contains - a boolean parameter that is used to decide whether
     *                 the method should print a correct guess message or an incorrect one
     * @param aChar    - the character that the user chose
     */
    private void drawStep(boolean contains, String aChar) {
        if (!contains) {
            println("There are no " + aChar + "'s in the word.");
        } else
            println("Your guess is correct.");
        println("The word now looks like this: " + displayWord());
        print("You have " + guesses + " guesses left.");
    }

    // method for displaying the win message
    private void winMessage() {
        println("You guessed the word: " + word);
        println("You win.");
    }

    // method for displaying the lose message
    private void loseMessage() {
        println("The word was: " + word);
        println("You Lose.");
    }

    /**
     * convert() method converts an array of characters
     * to an arraylist of strings to simplify code and avoid unnecessary for loops
     *
     * @return - returns the arraylist of strings
     */
    private ArrayList<String> convert() {
        char[] chars = word.toUpperCase().toCharArray();
        ArrayList<String> characters = new ArrayList<>();
        for (char aChar : chars) {
            characters.add("" + aChar);
        }
        return characters;
    }

    /**
     * the method displays the sequence of characters that the user correctly guessed
     * the method uses an Arraylist which contains the correctly guessed letters to correctly display the word
     */
    private String displayWord() {
        String str = "";
        for (int i = 0; i < word.length(); i++) {
            if (!guessedChars.contains("" + word.charAt(i)))
                str += "-";
            else str += "" + word.charAt(i);
        }
        return str;
    }

    /**
     * play() method calls the draw method in it and then proceeds to start the game in a while(true) loop
     * the break statements prevent the loop from running infinitely
     */
    private void play() {
        draw();
        guesses = 8;
        ArrayList<String> characters = convert();
        while (true) {
            canvas.displayWord(displayWord());
            if (guesses == 0) {
                loseMessage();
                break;
            }
            if (displayWord().equals(word)) {
                winMessage();
                break;
            }
            print("Your guess: ");
            String letter = readLine();
            letter = letter.toUpperCase();
            letter = letter.replaceAll(" ", "");

            // if the length of the input is greater than 1 (without spaces), then the input is incorrect
            if (letter.length() > 1)
                println("Enter a character");
            else if (!Character.isLetter(letter.charAt(0)))
                println("Enter a valid symbol");
            else if (characters.contains(letter) && !guessedChars.contains(letter)) {
                guessedChars.add(letter);
                drawStep(true, letter);
            } else if (guessedChars.contains(letter))
                println("You already guessed this letter");
            else {
                canvas.noteIncorrectGuess(letter.charAt(0), guesses);
                guesses--;
                drawStep(false, letter);
            }
        }
    }
    /**
     * method for playing the game
     * if the user enters X at the end of the round, the game is over
     * otherwise, game starts over
     */
    private void playGame() {
        String again = "";
        while(!again.equals("x")) {
            canvas.reset();
            play();
            println("Enter X if you wish to end the game: ");
            again = readLine();
        }
        println("Game over, time to die");
    }

}
