/**
 * CodeQuest 2014
 * Prob05: Book Worm
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob05 {
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
    public static void main(String[] args) {
        // read the input file and process it
        readInput();
    }

    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                int multiplier = 1;
                int total = 0;
                int digitsProcessed = 0;
                int checkDigit = 0;
                for (int i=0; i<inLine.length(); i++) {
                    // get the current character
                    String currentCharacter = inLine.substring(i, i+1);
                    
                    // check to see if it's a number
                    try {
                        int value = Integer.parseInt(currentCharacter);
                        
                        // if we get here the we found the next digit - adjust the value and add it to the total
                        value *= multiplier;
                        total += value;
                        
                        // toggle the multiplier and count the digit as processed
                        multiplier = (multiplier == 1) ? 3 : 1;
                        digitsProcessed++;
                        
                        if (digitsProcessed == 12) {
                            // save what the check bit value should be just in case
                            checkDigit = (10 - (total % 10)) % 10;
                        } else if (digitsProcessed == 13) {
                            // done - check for validity
                            if ((total % 10) == 0) {
                                System.out.println("VALID");
                            } else {
                                System.out.println("" + checkDigit);
                            }
                        }
                    } catch (NumberFormatException nfe) {
                        // ignore errors, we just care about the digits
                    }
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
