/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;


public class WordSearch {
    private static final String INPUT_FILE_NAME = "Prob14.in.txt";
    
    static int numRows = 0;
    static int numCols = 0;
    static char[][] letters = null;
    static ArrayList<String> puzzleRows = new ArrayList<String>();
    static ArrayList<String> words = new ArrayList<String>();
    
    public static void main(String[] args) {
        readInput();
        buildPuzzle();
        checkWords();
    }
    
    private static void checkWords() {
        // first sort the array
        Collections.sort(words);
        
        // now loop through the words and look for matches
        for (int i=0; i<words.size(); i++) {
            String currentWord = words.get(i);
            char firstLetter = currentWord.charAt(0);
            
            int startX = Integer.MAX_VALUE;
            int startY = Integer.MAX_VALUE;
            boolean match = false;
            
            // search for the first letter
            for (int row=0; row<numRows; row++) {
                for (int col=0; col<numCols; col++) {
                    if (firstLetter == letters[row][col]) {
                        // potential match
                        boolean matchN = true;
                        boolean matchNE = true;
                        boolean matchE = true;
                        boolean matchSE = true;
                        boolean matchS = true;
                        boolean matchSW = true;
                        boolean matchW = true;
                        boolean matchNW = true;
                        
                        // check for matches
                        for (int loop=0; loop<currentWord.length(); loop++) {
                            // N
                            try { if (currentWord.charAt(loop) != letters[row-loop][col]) matchN = false; } catch (ArrayIndexOutOfBoundsException e) { matchN = false; }
                            
                            // NE
                            try { if (currentWord.charAt(loop) != letters[row-loop][col+loop]) matchNE = false; } catch (ArrayIndexOutOfBoundsException e) { matchNE = false; }
                            
                            // E
                            try { if (currentWord.charAt(loop) != letters[row][col+loop]) matchE = false; } catch (ArrayIndexOutOfBoundsException e) { matchE = false; }
                            
                            // SE
                            try { if (currentWord.charAt(loop) != letters[row+loop][col+loop]) matchSE = false; } catch (ArrayIndexOutOfBoundsException e) { matchSE = false; }
                            
                            // S
                            try { if (currentWord.charAt(loop) != letters[row+loop][col]) matchS = false; } catch (ArrayIndexOutOfBoundsException e) { matchS = false; }
                            
                            // SW
                            try { if (currentWord.charAt(loop) != letters[row+loop][col-loop]) matchSW = false; } catch (ArrayIndexOutOfBoundsException e) { matchSW = false; }
                            
                            // W
                            try { if (currentWord.charAt(loop) != letters[row][col-loop]) matchW = false; } catch (ArrayIndexOutOfBoundsException e) { matchW = false; }
                            
                            // NW
                            try { if (currentWord.charAt(loop) != letters[row-loop][col-loop]) matchNW = false; } catch (ArrayIndexOutOfBoundsException e) { matchNW = false; }
                        }
                        match = (matchN || matchNE || matchE || matchSE || matchS || matchSW || matchW || matchNW);
                        
                        if (match) {
                            if ((col < startX) || ((col == startX) && (row < startY))) {
                                startX = col;
                                startY = row;
                            }
                        }
                    }
                }
            }
            
            if (startX < Integer.MAX_VALUE) {
                System.out.println(currentWord + " [" + startY + "," + startX + "]");
            }
        }
    }
    
    private static void buildPuzzle() {
        numRows = puzzleRows.size();
        letters = new char[numRows][numCols];
        for (int row=0; row<numRows; row++) {
            String currentRow = puzzleRows.get(row);
            for (int col=0; col<numCols; col++) {
                letters[row][col] = currentRow.charAt(col);
            }
        }
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            boolean readingPuzzle = false;
            boolean readingWords = false;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                if (inLine.equals("#PUZZLE")) {
                    readingPuzzle = true;
                } else if (inLine.equals("#WORDS")) {
                    readingPuzzle = false;
                    readingWords = true;
                } else {
                    if (inLine.length() > 0) {
                        if (readingPuzzle) {
                            // reading the puzzle
                            numCols = inLine.length();
                            puzzleRows.add(inLine);
                        } else if (readingWords) {
                            words.add(inLine);
                        }
                    }
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
