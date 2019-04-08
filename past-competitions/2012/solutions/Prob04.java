/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Prob04 {
    private static final String INPUT_FILE_NAME = "Prob04.in.txt";
    
    // use an ArrayList so we can cake advantage of the integer index
    static ArrayList<String> alphabet = new ArrayList<String>();
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            // set the first line marker true
            boolean firstLine = true;
            
            while ((inLine = br.readLine()) != null) {
                
                if (firstLine) {
                    // read in the decoder key on the first line
                    for (int i=0; i<inLine.length(); i++) {
                        String tempString = "" + inLine.charAt(i);
                        alphabet.add(tempString);
                    }
                    
                    // unset the first line marker
                    firstLine = false;
                } else {
                    // new line - start with a empty buffer
                    StringBuffer buf = new StringBuffer();
                    
                    // split the line into words using spaces
                    String[] words = inLine.split(" ");
                    for (int i=0; i<words.length; i++) {
                        // new word
                        String currentWord = words[i];
                        
                        // split the word into letters using dashes
                        String[] letters = currentWord.split("-");
                        for (int j=0; j<letters.length; j++) {
                            // offset the index and find the letter
                            int index = new Integer(letters[j]).intValue() - 1;
                            buf.append(alphabet.get(index));
                        }
                        if (i < (words.length - 1)) {
                            // only put a space if there are more words
                            buf.append(" ");
                        }
                    }
                    
                    // println gives us the newline character
                    System.out.println(buf.toString());
                }
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
