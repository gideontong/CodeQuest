/**
 * CodeQuest 2014
 * Problem 11: Spiral Text
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob11 {
    private static final String INPUT_FILE_NAME = "Prob11.in.txt";
    
    private static String text = null;
    private static char[][] square = null;
    private static int index = 0;
    private static int boundary = 1;
    private static int row = 0;
    private static int col = 0;
    
    public static void main(String[] args) {
        // read the input file and process it
        readInput();
        
        // find out how big the text is
        int textSize = text.length();
        
        // compute the smallest square we need to hold the text
        int i = 1;
        while (i*i < textSize) {
            i += 2;
        }
        
        int middle = i/2;
        row = middle;
        col = middle;
        square = new char[i][i];
        
        // fill in the middle spot and move left
        fillSpace();
        col--;
        
        while (index < textSize) {
            // fill down and move right
            while (row < middle+boundary) {
                fillSpace();
                row++;
            }
            
            // fill right and move up
            while (col < middle+boundary) {
                fillSpace();
                col++;
            }
            
            // fill up and move left
            while (row > middle-boundary) {
                fillSpace();
                row--;
            }
            
            // fill left and move down
            while (col >= middle-boundary) {
                fillSpace();
                col--;
            }
            
            boundary++;
        }
        
        // print
        for (row=0; row<i; row++) {
            for (col=0; col<i; col++) {
                System.out.print(square[row][col]);
            }
            System.out.println();
        }
    }
    
    private static void fillSpace() {
        square[row][col] = (index < text.length() ? text.charAt(index) : ' ');
        index++;
    }

    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            boolean previousLineEndsWithSpace = true;
            boolean currentLineStartsWithSpace = false;
            StringBuffer buf = new StringBuffer();
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                currentLineStartsWithSpace = inLine.startsWith(" ");
                
                if (!previousLineEndsWithSpace && !currentLineStartsWithSpace) {
                    // need to add a space between words
                    buf.append(" ");
                }
                
                // add the current line
                buf.append(inLine);
                
                previousLineEndsWithSpace = inLine.endsWith(" ");
            }
            
            text = buf.toString();
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
