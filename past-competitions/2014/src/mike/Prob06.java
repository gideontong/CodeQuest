/**
 * CodeQuest 2014
 * Problem 06: Product of a Grid
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob06 {
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
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
            ArrayList<String> lines = new ArrayList<String>();
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                lines.add(inLine);
            }
            
            int numRows = lines.size();
            int numCols = lines.get(0).split(" ").length;
            long[][] nums = new long[numRows][numCols];
            
            // start at the top left
            for (int row=0; row<numRows; row++) {
                String[] tokens = lines.get(row).split(" ");
                for (int col=0; col<numCols; col++) {
                    nums[row][col] = Long.parseLong(tokens[col]);
                }
            }
            
            // start at the top left
            long biggestProduct = Long.MIN_VALUE;
            long currentProduct = 1;
            for (int row=0; row<numRows; row++) {
                for (int col=0; col<numCols; col++) {
                    // right
                    if (col <= numCols-4) {
                        currentProduct = 1;
                        for (int loop=0; loop<4; loop++) {
                            currentProduct *= nums[row][col+loop];
                        }
                        if (currentProduct > biggestProduct) {
//                            System.out.println(row + "," + col);
                            biggestProduct = currentProduct;
                        }
                    }
                    
                    // down
                    if (row <= numRows-4) {
                        currentProduct = 1;
                        for (int loop=0; loop<4; loop++) {
                            currentProduct *= nums[row+loop][col];
                        }
                        if (currentProduct > biggestProduct) {
//                            System.out.println(row + "," + col);
                            biggestProduct = currentProduct;
                        }
                    }
                    
                    // down and right
                    if ((row <= numRows-4) && (col <= numCols-4)) {
                        currentProduct = 1;
                        for (int loop=0; loop<4; loop++) {
                            currentProduct *= nums[row+loop][col+loop];
                        }
                        if (currentProduct > biggestProduct) {
//                            System.out.println(row + "," + col);
                            biggestProduct = currentProduct;
                        }
                    }
                    
                    // down and left
                    if ((row <= numRows-4) && (col >= 3)) {
                        currentProduct = 1;
                        for (int loop=0; loop<4; loop++) {
                            currentProduct *= nums[row+loop][col-loop];
                        }
                        if (currentProduct > biggestProduct) {
//                            System.out.println(row + "," + col);
                            biggestProduct = currentProduct;
                        }
                    }
                }
            }
            
            System.out.println("Greatest product: " + biggestProduct);
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
