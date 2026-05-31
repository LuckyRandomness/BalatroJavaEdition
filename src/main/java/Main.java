package src.main.java;

import java.util.Scanner;
import java.util.Random;

import src.main.java.Types.Stake;
import src.main.java.Types.Deck;

public class Main {

    private static final String DECK_PROMPT = "What deck type would you like to use? Type 'help' for a list of decks.";
    private static final String STAKE_PROMPT = "What stake would you like to use? Type 'help' for a list of stakes.";
    private static final String SEED_YN_PROMPT = "Would you like a seeded run? Type yes/no.";
    private static final String SEED_PROMPT = "Type between one and eight numbers.";

    private static final String RETRY = "Invalid argument.";

    private static final String HELP_DECK = "\nRed - +1 discard every round\nBlue - +1 hand every round\nYellow - Start with an extra $10\nGreen - At end of each round, earn $2 per remaining hand and $1 per remaining discard; Earn no interest\n";
    private static final String HELP_STAKE = "\nWhite - Base difficulty\nRed - Small Blind gives no reward money\nGreen - Required score scales faster for each Ante\nBlue - -1 Discard\n";

    /** 
     * Start a game of Balatro.
     * @param args Leave empty.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Deck deckType = getDeck(scan);
        Stake stakeType = getStake(scan);
        Random rand = getSeed(scan);

        //GameState game = getGame(deckType, stakeType, rand);

        //game.start();
    }

    /**
     * Prompt user for deck type, and validate response.
     * @param scan Scanner for System.in
     * @return Deck selected (as enum).
     */
    private static Deck getDeck(Scanner scan) {
        Deck deckType = null;
        while (true) {
            System.out.println(DECK_PROMPT);
            String d = scan.nextLine();

            //check for 'help'
            if (d.equals("help")) {
                System.out.println(HELP_DECK);
                continue;
            }

            for (Deck deck : Deck.values()) {
                if (deck.name().equalsIgnoreCase(d)) {
                    deckType = deck;
                }
            }

            if (deckType == null) {
                System.out.println(RETRY);
            } else {
                return deckType;
            }
        }
    }

    /**
     * Prompt user for stake type, and validate response.
     * @param scan Scanner for System.in
     * @return Stake selected (as enum).
     */
    private static Stake getStake(Scanner scan) {
        Stake stakeType = null;
        while (true) {
            System.out.println(STAKE_PROMPT);
            String d = scan.nextLine();

            //check for 'help'
            if (d.equals("help")) {
                System.out.println(HELP_STAKE);
                continue;
            }

            for (Stake stake : Stake.values()) {
                if (stake.name().equalsIgnoreCase(d)) {
                    stakeType = stake;
                }
            }

            if (stakeType == null) {
                System.out.println(RETRY);
            } else {
                return stakeType;
            }
        }
    }

    /**
     * Prompt user for a seeded run, and what that seed is.
     * @param scan Scanner for System.in
     * @return java.util.Random to use for our game.
     */
    private static Random getSeed(Scanner scan) {
        while (true) {
            System.out.println(SEED_YN_PROMPT);
            String answer = scan.nextLine();

            //do we want a seeded run?
            if (answer.equals("yes")) {
                //yes -> what seed do we want? Between 1 - 8 digits
                long seed;
                System.out.println(SEED_PROMPT);

                try {
                    seed = scan.nextLong();
                } catch (Exception e) {
                    scan.nextLine(); //clears the buffer
                    System.out.println(RETRY);
                    continue;
                }
                
                if (String.valueOf(seed).length() > 0 && String.valueOf(seed).length() < 9) {
                    return new Random(seed);
                } else {
                    System.out.println(RETRY);
                }
            } else if (answer.equals("no")) {
                return new Random();
            } else {
                System.out.println(RETRY);
            }
        }
    }
}
