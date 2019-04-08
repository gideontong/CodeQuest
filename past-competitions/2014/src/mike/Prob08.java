/**
 * CodeQuest 2014
 * Problem 08: Rectangle Art
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob08 {
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
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
            
            boolean grid[][] = null;
            String[] tokens = null;
            int x1, y1, x2, y2 = 0;
            int numCols = 0;
            int numRows = 0;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                if (grid == null) {
                    // get the number of rows and columns
                    tokens = inLine.split(",");
                    numCols = Integer.parseInt(tokens[0]);
                    numRows = Integer.parseInt(tokens[0]);
                    
                    // make the grid
                    grid = new boolean[numRows][numCols];
                    
                    for (int i=0; i<numRows; i++) {
                        for (int j=0; j<numCols; j++) {
                            grid[i][j] = false;
                        }
                    }
                } else {
                    // get the two coordinates
                    tokens = inLine.split(" ");
                    x1 = Integer.parseInt(tokens[0].split(",")[0]);
                    y1 = Integer.parseInt(tokens[0].split(",")[1]);
                    x2 = Integer.parseInt(tokens[1].split(",")[0]);
                    y2 = Integer.parseInt(tokens[1].split(",")[1]);
                    
                    // order x and y low to high
                    if (x1 > x2) {
                        int temp = x1;
                        x1 = x2;
                        x2 = temp;
                    }
                    
                    if (y1 > y2) {
                        int temp = y1;
                        y1 = y2;
                        y2 = temp;
                    }
                    
                    // flip the grid - remember x is col and y is row
                    for (int i=y1; i<y2; i++) {
                        for (int j=x1; j<x2; j++) {
                            grid[i][j] = !grid[i][j];
                        }
                    }
                }
            }
            
            // now print
            for (int i=numRows-1; i>=0; i--) {
                for (int j=0; j<numCols; j++) {
                    System.out.print((grid[i][j] ? "*" : " "));
                }
                System.out.println();
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
