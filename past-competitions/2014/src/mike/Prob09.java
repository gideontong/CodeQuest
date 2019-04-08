/**
 * CodeQuest 2014
 * Problem 09: Check Please!
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob09 {
    private static final String INPUT_FILE_NAME = "Prob09.in.txt";
    
    private static final String[] englishNumbers = {
        "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
        "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen",
        "Twenty", "", "", "", "", "", "", "", "", "",
        "Thirty", "", "", "", "", "", "", "", "", "",
        "Forty", "", "", "", "", "", "", "", "", "",
        "Fifty", "", "", "", "", "", "", "", "", "",
        "Sixty", "", "", "", "", "", "", "", "", "",
        "Seventy", "", "", "", "", "", "", "", "", "",
        "Eighty", "", "", "", "", "", "", "", "", "",
        "Ninety", "", "", "", "", "", "", "", "", "",
        };
    
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
                int numPennies = 0;
                
                if (inLine.indexOf(".") > -1) {
                    // split on the decimal
                    String[] tokens = inLine.split("\\.");
                    
                    // get the dollar amount
                    numPennies = Integer.parseInt(tokens[0]);
                    numPennies *= 100;
                    
                    // add the pennies
                    numPennies += Integer.parseInt(tokens[1]);
                } else {
                    numPennies = Integer.parseInt(inLine);
                    numPennies *= 100;
                }
                
                int cents = numPennies % 100;
                int dollars = numPennies / 100;
                
                if (dollars == 0) {
                    System.out.print("Zero Dollars ");
                } else if (dollars == 1) {
                    // treat a single dollar differently
                    System.out.print("One Dollar ");
                } else {
                    // thousands
                    writeNumber(dollars/1000, "Thousand ");
                    
                    // dollars
                    dollars %= 1000;
                    writeNumber(dollars, "Dollars ");
                }
                
                System.out.print("and " + cents + ((cents == 1) ? " Cent" : " Cents"));
                System.out.println();
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void writeNumber(int theNumber, String placeName) {
        if (theNumber > 0) {
            // there is a number to write - start with hundreds
            if ((theNumber/100) > 0) {
                System.out.print(englishNumbers[theNumber/100] + " Hundred ");
                theNumber %= 100;
            }
            
            if (theNumber > 0) {
                // tens
                if (theNumber <= 20) {
                    System.out.print(englishNumbers[theNumber] + " ");
                } else {
                    System.out.print(englishNumbers[theNumber - (theNumber%10)] + " ");
                    theNumber %= 10;
                    
                    // ones
                    if (theNumber > 0) {
                        System.out.print(englishNumbers[theNumber] + " ");
                    }
                }
            }
            
            System.out.print(placeName);
        }
    }
}
