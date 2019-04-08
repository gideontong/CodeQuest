/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class FileIO {
    private static final String INPUT_FILE_NAME = "Prob01.in.txt";
    
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
                // print the line as is
                System.out.println(inLine);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
