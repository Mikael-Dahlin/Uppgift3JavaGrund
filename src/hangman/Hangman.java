package hangman;

import java.util.Random;
import java.util.Scanner;

public class Hangman {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		// Start a new game with a random word.
		newGame(randomWord(), scan);
	}

	/**
	 * Method with the main game loop.
	 * 
	 * @param wordToFind, should contain the word that will be guessed on.
	 * @param reader,     should contain the input source.
	 */
	public static void newGame(String wordToFind, Scanner reader) {
		// Declaration of variables used in the game.
		boolean continuous = false;
		boolean gameOn = true;
		boolean guessWord = false;
		
		char letter;
		char[] letters = wordToFind.toCharArray();
		
		int incorrectGuesses = 0;
		final int MAX_CHANCES = 6;
		
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String input;
		String lettersTried = "";
		String newWord = "";

		// Make the letters of the word appear as '_' characters.
		for (int i = 0; i < letters.length; i++) {
			letters[i] = '_';
		}

		System.out.println("The word has " + wordToFind.length() + " letters.");
		while (gameOn) {
			for (int i = 0; i < letters.length; i++) {
				System.out.print(letters[i]);
			}
			System.out.println();

			// Checks if we want a word or a letter.
			if (guessWord) {
				System.out.println("Guess the word:");
			} else {
				System.out.println("Guess a letter:");
			}

			// Wait for input from player.
			input = reader.nextLine().toLowerCase();
			letter = input.charAt(0);

			// If the first letter is '-' go to menu options.
			if (letter == '-') {
				switch (input) {
				case "-game status":
					printStatus(incorrectGuesses, MAX_CHANCES);
					break;
				case "-guess char":
					guessWord = false;
					break;
				case "-guess word":
					guessWord = true;
					break;
				case "-new word":
					System.out.println("Enter your word:");
					newWord = reader.nextLine().toLowerCase();
					continuous = true;
					gameOn = endGame("Game ended by player, the word was " + wordToFind + ".");
					// Make sure the word contains characters in the alphabet.
					for (int i = 0; i < newWord.length(); i++) {
						if (!alphabet.contains("" + newWord.charAt(i))) {
							System.out.println("The word contains invalid character '" + newWord.charAt(i)
									+ "' a random word will be used.");
							newWord = "";
						}
					}
					break;
				default:
					System.out.println("Invalid menu command, valid commands are: ");
					System.out.println(" -Game status");
					System.out.println(" -Guess char");
					System.out.println(" -Guess word");
					System.out.println(" -New word");
				}
			} else {
				
				if (guessWord) {
					if (wordToFind.equals(input)) {
						letters = input.toCharArray();
					} else {
						incorrectGuesses++;
						printHangman(incorrectGuesses);
					}

				} else if (lettersTried.contains("" + letter)){
					// Happens if the letter has been guessed already.
					System.out.println("You have already tried the letter " + letter + ".");
					
				} else if (alphabet.contains("" + letter)) {
					lettersTried += letter;
					
					// Check if the guess is correct.
					if (wordToFind.contains("" + letter)) {
						for (int i = 0; i < wordToFind.length(); i++) {
							if (wordToFind.charAt(i) == letter) {
								letters[i] = letter;
							}
						}
					} else {
						incorrectGuesses++;
						printHangman(incorrectGuesses);
					}
					
				} else {
					System.out.println("The letter " + letter + " is not in the alphabet, try again.");
				}

				// Check if incorrectGuesses reached the maximum chances. (Game Lost)
				if (incorrectGuesses == MAX_CHANCES) {
					gameOn = endGame("You lost!, the word was " + wordToFind + ".");
				}

				// Check if we got the word correct. (Game Won)
				if (String.copyValueOf(letters).equals(wordToFind)) {
					gameOn = endGame("You won!, the word was " + wordToFind + ".");
				}
			}
		}

		// If continuous is true start a new game.
		if (continuous) {
			if (newWord == "") {
				newWord = randomWord();
			}
			newGame(newWord, reader);
		}

		reader.close();
	}

	/**
	 * Method for getting a random word.
	 * 
	 * @return returns a random word from the list.
	 */
	public static String randomWord() {
		
		Random random = new Random();
		
		// List of possible words to get.
		String[] wordsList = { "fascinated", "digestion", "deceive", "zephyr", "divide", "quiver", "share", "hurry",
				"learn", "false", "talented", "carriage", "dazzling", "answer", "collect", "prepare", "crayon",
				"imminent", "comparison", "unequaled", "unarmed", "confuse", "attempt", "surprise", "hilarious",
				"realize", "apparatus", "opposite", "modern", "concern", "instinctive", "quaint", "puzzling" };
		
		return wordsList[random.nextInt(wordsList.length)];
	}

	/**
	 * Method to print out the current status of the game.
	 * 
	 * @param wrong,   contains the number of incorrect guesses.
	 * @param chances, contains the total number of chances.
	 */
	public static void printStatus(int wrong, int chances) {
		printHangman(wrong);
		System.out.println("There are " + wrong + " incorrect guesses.");
		System.out.println("You have " + (chances - wrong) + " chances remaining.");
	}

	/**
	 * Method to print out the gallows depending on the number of incorrect guesses.
	 * 
	 * @param numberOfLines, contains the number of incorrect guesses.
	 */
	public static void printHangman(int numberOfLines) {
		String hangmanLine1 = " ____";
		String hangmanLine2 = " |  |";
		String hangmanLine3 = " |   ";
		String hangmanLine4 = " |    ";
		String hangmanLine5 = " |   ";
		String hangmanLine6 = " |";
		String hangmanLine7 = "/|\\ ";

		if (numberOfLines > 0) {
			hangmanLine3 = " |  o";
		}
		if (numberOfLines > 1) {
			hangmanLine4 = " | /  ";
		}
		if (numberOfLines > 2) {
			hangmanLine4 = " | /| ";
		}
		if (numberOfLines > 3) {
			hangmanLine4 = " | /|\\";
		}
		if (numberOfLines > 4) {
			hangmanLine5 = " | / ";
		}
		if (numberOfLines > 5) {
			hangmanLine5 = " | /\\";
		}

		System.out.println(hangmanLine1);
		System.out.println(hangmanLine2);
		System.out.println(hangmanLine3);
		System.out.println(hangmanLine4);
		System.out.println(hangmanLine5);
		System.out.println(hangmanLine6);
		System.out.println(hangmanLine7);
	}

	/**
	 * Method for ending the game round.
	 * 
	 * @param text, text containing a message to the player.
	 * @return returns false to exit the game loop.
	 */
	public static boolean endGame(String text) {
		System.out.println(text);
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		return false;
	}

}
