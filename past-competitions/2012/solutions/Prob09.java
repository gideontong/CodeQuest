/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Prob09 {
    private static final String INPUT_FILE_NAME = "Prob09.in.txt";
    
    // list of words to compare
    static ArrayList<String> words = new ArrayList<String>();
    
    // keep track of used words in a HashMap
    static HashMap<String, String> usedWords = new HashMap<String, String>();
    
    public static void main(String[] args) {
        readInput();
        
        // put the words into an array so we can sort them before we start comparing
        int numWords = words.size();
        String[] wordArray = new String[numWords];
        words.toArray(wordArray);
        Arrays.sort(wordArray);
        
        // loop through the words in bubble sort fashion
        for (int i=0; i<numWords-1; i++) {
            for (int j=i+1; j<numWords; j++) {
                // use lowercase copies to do the comparison
                String word1 = new String(wordArray[i]).toLowerCase();
                String word2 = new String(wordArray[j]).toLowerCase();
                
                // first check the length to see if we need to compare
                if (word1.length() == word2.length()) {
                    // compare the two words - start with 0 differences
                    int numDifs = 0;
                    
                    // loop through the characters and compare them one at a time
                    for (int k=0; k<word1.length(); k++) {
                        if (word1.charAt(k) != word2.charAt(k)) {
                            // characters are different
                            numDifs++;
                            if (numDifs > 1) {
                                // too many differences - stop checking
                                k = word1.length();
                            }
                        }
                    }
                    if (numDifs <= 1) {
                        // these are one character different - print out the original words
                        System.out.println(wordArray[i] + " " + wordArray[j]);
                    }
                }
            }
        }
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                // split the line into words using spaces
                String[] tokens = inLine.split(" ");
                
                for (int i=0; i<tokens.length; i++) {
                    // get a lowercase copy of the word to see if it's been encountered before
                    String tempString = new String(tokens[i]);
                    tempString = tempString.toLowerCase();
                    if (usedWords.get(tempString) == null) {
                        // remember that we've used this word, but use the original version for alphabetizing!
                        words.add(tokens[i]);
                        usedWords.put(tempString, tempString);
                    }
                }
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
