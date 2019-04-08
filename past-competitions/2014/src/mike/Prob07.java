/**
 * CodeQuest 2014
 * Problem 07: Morse Code
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Prob07 {
    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
    private static HashMap<String, String> morseToTextMap = new HashMap<String, String>();
    private static HashMap<String, String> textToMorseMap = new HashMap<String, String>();
    private static final String LETTER_SPACE = "___";
    private static final String WORD_SPACE = "_______";
    private static final String SECTION_BREAK = "END OF TRANSMISSION";
    
    public static void main(String[] args) {
        // read the input file and process it
        initMaps();
        readInput();
    }

    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            boolean readingMorseCode = false;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                if (inLine.equals(SECTION_BREAK)) {
                    // section break
                    readingMorseCode = true;
                    System.out.println(SECTION_BREAK);
                } else if (readingMorseCode) {
                    // change morese code to text
                    morseToText(inLine);
                } else {
                    // change text to morse code
                    textToMorse(inLine);
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void textToMorse(String inLine) {
        // split the line using spaces to get the list of words
        String[] words = inLine.split(" ");
        for (int i=0; i<words.length; i++) {
            if (i > 0) {
                // not the first word - add a space between words
                System.out.print(WORD_SPACE);
            }
            
            // get the current word
            String currentWord = words[i];
            for (int j=0; j<currentWord.length(); j++) {
                if (j > 0) {
                    // not the first letter - add a space between letters
                    System.out.print(LETTER_SPACE);
                }
                
                // print the current letter
                String currentLetter = currentWord.substring(j, j+1);
                System.out.print(textToMorseMap.get(currentLetter));
            }
        }
        
        // newline at the end of the line
        System.out.println();
    }
    
    private static void morseToText(String inLine) {
        // split the line into words
        String[] words = inLine.split(WORD_SPACE);
        
        if (words.length > 0) {
            StringBuffer buf = new StringBuffer();
            
            for (int i=0; i<words.length; i++) {
                if (i > 0) {
                    // not the first word, add a space
                    buf.append(" ");
                }
                String currentWord = words[i];
                
                // split the word into letters
                String[] letters = currentWord.split(LETTER_SPACE);
                for (int j=0; j<letters.length; j++) {
                    String currentLetter = letters[j];
                    if (morseToTextMap.get(currentLetter) != null) {
                        buf.append(morseToTextMap.get(currentLetter));
                    }
                }
            }
            
            System.out.print(buf.toString());
        }
        
        System.out.println();
    }
    
    private static void initMaps() {
        String[] letter = {
                "a", "b", "c", "d", "e", 
                "f", "g", "h", "i", "j", 
                "k", "l", "m", "n", "o", 
                "p", "q", "r", "s", "t", 
                "u", "v", "w", "x", "y", 
                "z"
        };
        
        String[] morse = {
                "=_===", "===_=_=_=", "===_=_===_=", "===_=_=", "=", 
                "=_=_===_=", "===_===_=", "=_=_=_=", "=_=", "=_===_===_===", 
                "===_=_===", "=_===_=_=", "===_===", "===_=", "===_===_===", 
                "=_===_===_=", "===_===_=_===", "=_===_=", "=_=_=", "===", 
                "=_=_===", "=_=_=_===", "=_===_===", "===_=_=_===", "===_=_===_===", 
                "===_===_=_="
        };
        
        for (int i=0; i<letter.length; i++) {
            String currentLetter = letter[i];
            String currentMorse = morse[i];
            morseToTextMap.put(currentMorse, currentLetter);
            textToMorseMap.put(currentLetter, currentMorse);
            currentLetter = currentLetter.toUpperCase();
            textToMorseMap.put(currentLetter, currentMorse);
        }
    }
}
