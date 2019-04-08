/**
 * CodeQuest 2014
 * Prob03: Valley Sort
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob03 {
    private static final String INPUT_FILE_NAME = "Prob03.in.txt";
    
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
                // split on the spaces
                String tokens[] = inLine.split(" ");
                
                // create an array to hold the values and the valley sort
                int[] intArray = new int[tokens.length];
                int[] valleyArray = new int[tokens.length];
                
                // put the original sorted array together and sort it
                for (int i=0; i<tokens.length; i++) {
                    intArray[i] = Integer.valueOf(tokens[i]).intValue();
                }
                Arrays.sort(intArray);
                
                // valley sort indexes
                int lowIndex = 0;
                int highIndex = intArray.length - 1;
                int index = highIndex;
                
                while (lowIndex < highIndex) {
                    // if low is less than high then there are at least two more numbers to fill
                    valleyArray[lowIndex++] = intArray[index--];
                    valleyArray[highIndex--] = intArray[index--];
                }
                
                // might be done, except there could be an odd number of items in the list
                if (lowIndex == highIndex) {
                    valleyArray[lowIndex] = intArray[index];
                }
                
                // now print
                for (int i=0; i<valleyArray.length; i++) {
                    if (i > 0) {
                        System.out.print(" ");
                    }
                    System.out.print(valleyArray[i]);
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
