/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Prob08 {
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
    static String thePhrase = null;
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void process() {
        // assume it's a square until we find otherwise
        boolean rectangle = false;
        
        // compute the length of the phrase
        int phraseLength = thePhrase.length();
        
        // get the remainder - this tells us if we need to add a period and if it's a square or rectangle
        int remainder = phraseLength % 4;
        switch (remainder) {
            case 0:
                // square with no added period
                break;
            case 1:
                // rectangle with an added period
                rectangle = true;
                thePhrase = thePhrase + ".";
                break;
            case 2:
                // rectangle with no added period
                rectangle = true;
                break;
            case 3:
                // square with an added period
                thePhrase = thePhrase + ".";
                break;
        }
        
        // get the new phrase length
        phraseLength = thePhrase.length();
        
        // compute side length - start with the phrase length
        int sideLength = phraseLength;
        
        // add 2 for a rectangle so we're working with a square
        sideLength += (rectangle ? 2 : 0);
        if ((sideLength % 4) > 0) {
            // sanity check - if we did things right, this should never happen
            System.out.println("Problem with side length computation!");
        }
        
        // subtract the corners
        sideLength -= 4;
        
        // divide by 4 sides - this should be the width of the square without corners
        sideLength /= 4;
        
        // the top and bottom (width) should contain the corners
        int topBottomLength = sideLength + 2;
        
        // if it's a rectangle, subtract one from the height
        int leftRightLength = (rectangle ? sideLength-1 : sideLength);
        
        // index for where we are in the phrase breaking it up
        int index = 0;
        
        // top
        String topString = thePhrase.substring(index, index + topBottomLength);
        index = index + topBottomLength;
        
        // right
        String rightString = thePhrase.substring(index, index + leftRightLength);
        index = index + leftRightLength;
        
        // bottom - reverse it
        String bottomString = thePhrase.substring(index, index + topBottomLength);
        bottomString = new String(new StringBuffer(bottomString).reverse());
        index = index + topBottomLength;
        
        // left - reverse it
        String leftString = thePhrase.substring(index, index + leftRightLength);
        leftString = new String(new StringBuffer(leftString).reverse());
        
        // print the top
        System.out.println(topString);
        
        // print left and right with spaces inbetween
        for (int i=0; i<rightString.length(); i++) {
            // start with an empty buffer
            StringBuffer buf = new StringBuffer();
            
            // add the character from the left side
            buf.append(leftString.charAt(i));
            
            // add the spaces - this is why we took away the corners when computing side length above
            for (int j=0; j<sideLength; j++) {
                buf.append(" ");
            }
            
            // add the character from the right side
            buf.append(rightString.charAt(i));
            
            // print out the line
            System.out.println(buf.toString());
        }
        // print the bottom
        System.out.println(bottomString);
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // firstLine indicator
            boolean firstLine = true;
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                if (firstLine) {
                    // don't print a blank line if this is the first phrase
                    firstLine = false;
                } else {
                    // print the extra blank line between phrases
                    System.out.println();
                }
                
                thePhrase = new String(inLine);
                if (!thePhrase.endsWith(" ")) {
                    // add a space at the end if there isn't one
                    thePhrase = thePhrase + ".";
                }
                
                // replace spaces with periods
                thePhrase = thePhrase.replace(" ", ".");
                process();
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
