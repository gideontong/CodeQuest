/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class CamelCase {
    private static final String INPUT_FILE_NAME = "Prob11.in.txt";
    
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
                // split on the semicolon
                String[] tokens = inLine.split(";");
                String operation = tokens[0];
                String objectType = tokens[1];
                String theString = tokens[2];
                String result = "";
                
                if (operation.equals("S")) {
                    // split the words
                    for (int i=0; i<theString.length(); i++) {
                        char currentChar = theString.charAt(i);
                        
                        // skip parentheses
                        if ((currentChar == '(') || (currentChar == ')')) {
                            // do nothing
                        } else {
                            if (Character.isUpperCase(currentChar)) {
                                // add a space if it's not the first letter
                                if (i > 0) {
                                    result += " ";
                                }
                            }
                            result += ("" + currentChar).toLowerCase();
                        }
                    }
                } else {
                    // combine the words
                    String[] words = theString.split(" ");
                    // loop through words
                    for (int i=0; i<words.length; i++) {
                        String theWord = words[i];
                        // loop through characters
                        for (int j=0; j<theWord.length(); j++) {
                            if (j == 0) {
                                // first letter of a word
                                if (i == 0) {
                                    // first letter of first word - check object type
                                    if (objectType.equals("C")) {
                                        // capital for class
                                        result += ("" + theWord.charAt(j)).toUpperCase();
                                    } else {
                                        // lowercase for method and variable
                                        result += ("" + theWord.charAt(j)).toLowerCase();
                                    }
                                } else {
                                    // first letter should be capitalized
                                    result += ("" + theWord.charAt(j)).toUpperCase();
                                }
                            } else {
                                // not the first letter - lowercase
                                result += ("" + theWord.charAt(j)).toLowerCase();
                            }
                        }
                    }
                    
                    // add parentheses for methods
                    if (objectType.equals("M")) {
                        result += "()";
                    }
                }
                
                System.out.println(result);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
