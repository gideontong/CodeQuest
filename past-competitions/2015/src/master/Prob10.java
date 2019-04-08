

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob10 {
    private static final String INPUT_FILE_NAME = "Prob10.in.txt";
    
    private static final String DEUCE = "deuce";
    private static final String WINNER1 = "Game Player 1";
    private static final String WINNER2 = "Game Player 2";
    private static final String ADVANTAGE1 = "Advantage Player 1";
    private static final String ADVANTAGE2 = "Advantage Player 2";
    
    // to tell when to print the game start message
    private static boolean firstPoint = true;
    
    // to keep track of points
    private static int[] scores = new int[2];
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get ready
            reset();
            
            // loop throguh the lines in the file
            while ((inLine = br.readLine()) != null) {
                // play the point
                play(Integer.parseInt(inLine));
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void reset() {
        firstPoint = true;
        Arrays.fill(scores, 0);
    }
    
    private static void play(int pointWinner) {
        // print that this is a new game if it's the first point
        if (firstPoint) {
            System.out.println("Game start");
            firstPoint = false;
        }
        
        // award the point
        scores[pointWinner-1]++;
        
        // print the score
        printScore();
    }
    
    private static void printScore() {
        if ((scores[0] < 4) && (scores[1] < 4)) {
            // nobody could have won yet
            if (scores[0] == scores[1]) {
                // we're tied - is it deuce?
                if ((scores[0] == 3) && (scores[1] == 3)) {
                    // deuce
                    System.out.println(DEUCE);
                } else {
                    // not deuce
                    System.out.println(decodeScore(scores[0]) + "-all");
                }
            } else {
                // not tied and no winner - print the score like normal
                System.out.println(decodeScore(scores[0]) + "-" + decodeScore(scores[1]));
            }
        } else {
            // someone has at least 4 points, so there could be a winner
            if (scores[0] == scores[1]) {
                // deuce
                System.out.println(DEUCE);
            } else if ((scores[0] - scores[1]) >= 2) {
                // player 1 wins
                System.out.println(WINNER1);
                reset();
            } else if ((scores[1] - scores[0]) >= 2) {
                // player 2 wins
                System.out.println(WINNER2);
                reset();
            } else if ((scores[0] - scores[1]) == 1) {
                // player 1 advantage
                System.out.println(ADVANTAGE1);
            } else if ((scores[1] - scores[0]) == 1) {
                // player 2 advantage
                System.out.println(ADVANTAGE2);
            }
        }
    }
    
    private static String decodeScore(int numPoints) {
        switch (numPoints) {
            case 0:
                return "love";
            case 1:
                return "15";
            case 2:
                return "30";
            case 3:
                return "40";
            default:
                return "Should not see this";
        }
    }
}
