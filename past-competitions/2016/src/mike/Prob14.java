

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob14 {
    private static final String INPUT_FILE_NAME = "Prob14.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // read the line of text
                inLine = br.readLine();
                
                // break into frames
                String[] tokens = inLine.split(",");
                
                // hold each throw's score
                ArrayList<Integer> scoreList = new ArrayList<Integer>();
                
                // loop through frames and make the list of scores
                for (int frame=1; frame<=10; frame++) {
                    // start each frame with 10 pins
                    int pinsLeft = 10;
                    
                    // get this frame's score
                    String frameScore = tokens[frame-1];
                    
                    // break each frame's score up into balls thrown
                    for (int i=0; i<frameScore.length(); i++) {
                        int currentScore = getScore(frameScore.charAt(i), pinsLeft);
                        scoreList.add(currentScore);
                        pinsLeft -= currentScore;
                        
                        // 10th frame protection
                        if (pinsLeft == 0) {
                            pinsLeft = 10;
                        }
                    }
                }
                
                // transfer the list to an array
                int[] scores = new int[scoreList.size()];
                for (int i=0; i<scoreList.size(); i++) {
                    scores[i] = scoreList.get(i);
                }
                
                // now score the game
                int index = 0;
                int frame = 1;
                int total = 0;
                int pinsLeft = 10;
                
                while (frame < 11) {
                    // reset the pins
                    pinsLeft = 10;
                    
                    // add the current score
                    total += scores[index];
                    pinsLeft -= scores[index];
                    
                    if (pinsLeft == 0) {
                        // strike - add the next two scores to this
                        total += scores[index+1];
                        total += scores[index+2];
                    } else {
                        // not a strike - move to the next score
                        index++;
                        
                        // add the current score
                        total += scores[index];
                        pinsLeft -= scores[index];
                        
                        if (pinsLeft == 0) {
                            // spare - add the next score to this
                            total += scores[index+1];
                        }
                    }
                    
                    // move to the next frame
                    index++;
                    frame++;
                }
                
                // print the final score
                System.out.println(total);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static int getScore(char theChar, int pinsLeft) {
        int retVal = 0;
        
        switch (theChar) {
            case 'X':
                retVal = 10;
                break;
            case '/':
                retVal = pinsLeft;
                break;
            case '-':
                retVal = 0;
                break;
            default:
                retVal = theChar - '0';
                break;
        }
        
        return retVal;
    }
}
