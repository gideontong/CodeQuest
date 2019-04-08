/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Golf {
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            String name1, name2, name3, nameLast;
            name1 = name2 = name3 = nameLast = null;
            int score1, score2, score3;
            score1 = score2 = score3 = Integer.MAX_VALUE;
            int scoreLast = Integer.MIN_VALUE;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                // split the name from the scores
                String[] tokens = inLine.split(":");
                String playerName = tokens[0];
                
                // split the scores
                tokens = tokens[1].split(",");
                
                // compute the score
                int score = 0;
                for (int i=0; i<18; i++) {
                    score += Integer.valueOf(tokens[i]);
                }
                
                // compare scores
                if (score > scoreLast) {
                    // new last place
                    nameLast = playerName;
                    scoreLast = score;
                }
                
                if (score < score1) {
                    // new first place
                    name3 = name2;
                    score3 = score2;
                    name2 = name1;
                    score2 = score1;
                    name1 = playerName;
                    score1 = score;
                } else if (score < score2) {
                    // new second place
                    name3 = name2;
                    score3 = score2;
                    name2 = playerName;
                    score2 = score;
                } else if (score < score3) {
                    // new third place
                    name3 = playerName;
                    score3 = score;
                }
            }
            
            // print output
            System.out.println("FIRST:" + name1);
            System.out.println("SECOND:" + name2);
            System.out.println("THIRD:" + name3);
            System.out.println("LAST:" + nameLast);
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
