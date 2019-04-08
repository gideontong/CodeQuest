/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Prob06 {
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
    static ArrayList<String> originalStrings = new ArrayList<String>();
    
    public static void main(String[] args) {
        readInput();
        
        for (int i=0; i<originalStrings.size(); i++) {
            // get the original string we're working on
            String original = new String(originalStrings.get(i));
            
            // replace every character that is not a digit or a letter with an empty string
            original = original.replaceAll("[^0-9a-zA-Z]", "");
            
            // reverse the original
            String reversed = new String(new StringBuffer(original).reverse());
            
            // compare the original and reversed versions
            String result = ((original.equalsIgnoreCase(reversed)) ? "yes" : "no");
            System.out.println(result);
        }
        
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                originalStrings.add(inLine);
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
