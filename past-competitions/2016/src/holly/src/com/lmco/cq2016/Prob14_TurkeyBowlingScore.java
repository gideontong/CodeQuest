package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 19
 * 20m
 * @author nortoha
 *
 */
public class Prob14_TurkeyBowlingScore {
    private static final String INPUT_FILE_NAME = "Prob14.in.txt";
    
    private static final String STRIKE = "X";
    private static final String SPARE = "/";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob14_TurkeyBowlingScore.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // read the line of text
                inLine = br.readLine();
                
                // determine score and print
                System.out.println(getScore(inLine));
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getScore(String inLine) {

        // score starts at 0
        int retVal = 0;
        
        // for keeping the score for each frame so we can handle strikes and spares
        int[] scoreByFrame = new int[10];
        
        // stripping out gutter balls and replacing with 0 points
        inLine = inLine.replaceAll("\\-", "0");
        
        // break inLine into frames using array data structure
        String[] frames = inLine.split("\\,");
        
        int[] ballsBowled = new int[inLine.length() - 9];
        
        int ballIdx = 0;
        // parse the values in each frame into balls bowled
        for(int i=0; i<inLine.length(); i++){
            
            String frame = String.valueOf(inLine.charAt(i));

            if(frame.equals(",")){
                //skip commas
                
            } else {
                
                if(frame.equals(STRIKE)){
                    ballsBowled[ballIdx] = 10;
                } else if(frame.equals(SPARE)){
                    ballsBowled[ballIdx] = 10 - Integer.parseInt(String.valueOf(inLine.charAt(i-1)));
                } else {
                    ballsBowled[ballIdx] = Integer.parseInt(String.valueOf(inLine.charAt(i)));
                }
                ballIdx++;
            }
        }

        
        //reset for next loop
        ballIdx = 0;
        
        // loop through 10 frames
        for(int i=0; i<frames.length; i++){
            
            String curFrame = frames[i];
            
            if(i<9){
                
                if(curFrame.equals(STRIKE)){
                    scoreByFrame[i] = ballsBowled[ballIdx] + ballsBowled[ballIdx + 1] + ballsBowled[ballIdx + 2];
                    ballIdx = ballIdx + 1;
                    
                } else if(curFrame.contains(SPARE)){
                    scoreByFrame[i] = 10 + ballsBowled[ballIdx+2];
                    ballIdx = ballIdx + 2;
                    
                } else {
                    scoreByFrame[i] = ballsBowled[ballIdx] + ballsBowled[ballIdx + 1];
                    ballIdx = ballIdx + 2;
                }
                
            } else {
                // add last frame
                
                if(curFrame.contains(STRIKE)){
                    scoreByFrame[i] = ballsBowled[ballIdx] + ballsBowled[ballIdx + 1] + ballsBowled[ballIdx + 2];
                    ballIdx = ballIdx + 1;
                    
                } else if(curFrame.contains(SPARE)){
                    scoreByFrame[i] = 10 + ballsBowled[ballIdx+2];
                    ballIdx = ballIdx + 2;
                    
                } else {
                    scoreByFrame[i] = ballsBowled[ballIdx] + ballsBowled[ballIdx + 1];
                    ballIdx = ballIdx + 2;
                }
                
            }
            
        }
        
      for(int i=0; i<10; i++){
          retVal += scoreByFrame[i];
      }
        

      return retVal;
    }
    
}
