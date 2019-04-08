/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Prob03 {
    private static final String INPUT_FILE_NAME = "Prob03.in.txt";
    
    static ArrayList<Integer> numbers = new ArrayList<Integer>();
    static boolean invalid = false;
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                // split on the spaces
                String[] tokens = inLine.split(" ");
                
                for (int i=0; i<tokens.length; i++) {
                    String currentToken = tokens[i];
                    
                    // this pattern matches a string that has either 0 or 1 dash (minus sign) and then 1 or more numbers
                    if (currentToken.matches("-?\\d+")) {
                        // valid number - keep it
                        Integer tempInt = new Integer(currentToken);
                        numbers.add(tempInt);
                    } else {
                        // invalid number - mark the line invalid
                        invalid = true;
                    }
                }
                processNumbers();
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void processNumbers() {
        boolean ascending = true;
        boolean descending = true;
        
        if (!invalid) {
            for (int i=0; i<numbers.size()-1; i++) {
                int previousValue = numbers.get(i).intValue();
                int nextValue = numbers.get(i+1).intValue();
                
                if (ascending) {
                    // still could be in ascending order
                    if (nextValue < previousValue) {
                        // value went down - can't be ascending any more
                        ascending = false;
                    }
                }
                
                if (descending) {
                    // still could be in descending order
                    if (nextValue > previousValue) {
                        // value went up - can't be descending any more
                        descending = false;
                    }
                }
                
                if ((!ascending) && (!descending)) {
                    // random order - stop comparing
                    i = numbers.size();
                }
            }
        }
        
        // write the correct phrase for this line
        writeOutput(ascending, descending);
        
        // reset the invalid indicator and the list of numbers
        invalid = false;
        numbers = new ArrayList<Integer>();
    }
    
    private static void writeOutput(boolean ascending, boolean descending) {
        try {
            String outString = "This should get overwritten.";
            
            if (invalid) {
                outString = "The input was invalid";
            } else if (ascending) {
                outString = "The numbers are in ascending order";
            } else if (descending) {
                outString = "The numbers are in descending order";
            } else {
                outString = "The numbers are in random order";
            }
            
            System.out.println(outString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
