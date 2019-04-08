/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;


public class SortNumbers {
    private static final String INPUT_FILE_NAME = "Prob04.in.txt";
    
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
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                String tokens[] = inLine.split(",");
                int[] intArray = new int[tokens.length];
                for (int i=0; i<tokens.length; i++) {
                    intArray[i] = Integer.valueOf(tokens[i]).intValue();
                }
                Arrays.sort(intArray);
                for (int i=0; i<intArray.length; i++) {
                    if (i > 0) {
                        System.out.print(",");
                    }
                    System.out.print(intArray[i]);
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
