/**
 * CodeQuest 2014
 * Prob04: Pig Latin
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Prob04 {
    private static final String INPUT_FILE_NAME = "Prob04.in.txt";
    
    private static final String[] vowels = {"a", "e", "i", "o", "u"};
    private static HashMap<String, String> vowelMap = new HashMap<String, String>();
    
    public static void main(String[] args) {
        // build a HashMap of vowels so we can use the containsKey method on it
        buildVowelList();
        
        // read the input file and process it
        readInput();
    }
    
    private static void buildVowelList() {
        for (int i=0; i<vowels.length; i++) {
            vowelMap.put(vowels[i], vowels[i]);
        }
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
                // break the line into words
                String[] tokens = inLine.split(" ");
                
                // loop through the words
                for (int i=0; i<tokens.length; i++) {
                    if (i > 0) {
                        System.out.print(" ");
                    }
                    // write the word
                    writeWord(tokens[i]);
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
    
    private static void writeWord(String theWord) {
        String firstLetter = "" + theWord.charAt(0);
        if (vowelMap.containsKey(firstLetter)) {
            // this word starts with a vowel
            System.out.print(theWord);
            System.out.print("yay");
        } else {
            // this word starts with a consonant
            int index = 0;
            String beginning = "";
            
            try {
                // look for a vowel
                while (!vowelMap.containsKey("" + theWord.charAt(index))) {
                    // take the next consonant
                    boolean foundQ = (theWord.charAt(index) == 'q');
                    beginning += theWord.charAt(index++);
                    if (foundQ) {
                        // if we find a 'q', take the 'u' after it as well
                        if (theWord.charAt(index) == 'u') {
                            beginning += theWord.charAt(index++);
                        }
                    }
                }
                
                // print it out
                System.out.print(theWord.substring(index));
                System.out.print(beginning);
                System.out.print("ay");
            } catch (StringIndexOutOfBoundsException sioobe) {
                // this means there were no vowels - write the word as is
                System.out.print(theWord);
            }
        }
    }
}
