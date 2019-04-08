

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob11 {
    private static final String INPUT_FILE_NAME = "Prob11.in.txt";
    
    // A is 65, a is 97
    private static final int CAP_OFFSET = ('a' - 'A');
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                // to hold the characters
                char[] original = new char[inLine.length()];
                char[] reversed = new char[inLine.length()];
                
                int reverseIndex = 0;
                int currentWordLength = 0;
                
                for (int i=0; i<inLine.length(); i++) {
                    // record the original
                    original[i] = inLine.charAt(i);
                    
                    if (isLetter(original[i])) {
                        // part of a word
                        currentWordLength++;
                        
                        // are we at the end?
                        if (i == inLine.length()-1) {
                            // reverse the previous word
                            for (int wordIndex=0; wordIndex<currentWordLength; wordIndex++) {
                                // is the letter in the original string capitalized?
                                boolean originalCap = isCapital(original[reverseIndex]);
                                
                                // is the current letter capitalized?
                                boolean currentCap = isCapital(original[i-wordIndex]);
                                
                                if (currentCap == originalCap) {
                                    // same capitalization, just copy
                                    reversed[reverseIndex++] = original[i-wordIndex];
                                } else if (originalCap) {
                                    // original is caps, the current is not - change the current to caps
                                    reversed[reverseIndex++] = (char)(original[i-wordIndex] - CAP_OFFSET);
                                } else {
                                    // current is caps, original is not - change the current to lower
                                    reversed[reverseIndex++] = (char)(original[i-wordIndex] + CAP_OFFSET);
                                }
                            }
                            
                            // reset word counter
                            currentWordLength = 0;
                        }
                    } else {
                        // not part of a word - is there a word behind us?
                        if (currentWordLength > 0) {
                            // reverse the previous word
                            for (int wordIndex=1; wordIndex<=currentWordLength; wordIndex++) {
                                // is the letter in the original string capitalized?
                                boolean originalCap = isCapital(original[reverseIndex]);
                                
                                // is the current letter capitalized?
                                boolean currentCap = isCapital(original[i-wordIndex]);
                                
                                if (currentCap == originalCap) {
                                    // same capitalization, just copy
                                    reversed[reverseIndex++] = original[i-wordIndex];
                                } else if (originalCap) {
                                    // original is caps, the current is not - change the current to caps
                                    reversed[reverseIndex++] = (char)(original[i-wordIndex] - CAP_OFFSET);
                                } else {
                                    // current is caps, original is not - change the current to lower
                                    reversed[reverseIndex++] = (char)(original[i-wordIndex] + CAP_OFFSET);
                                }
                            }
                            
                            // reset word counter
                            currentWordLength = 0;
                        }
                        
                        // copy the non-letter
                        reversed[reverseIndex++] = original[i];
                    }
                }
                
                // print
                for (char currentChar : reversed) {
                    System.out.print(currentChar);
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
    
    private static boolean isLetter(char theChar) {
        boolean retVal = false;
        
        if ((theChar >= 'a') && (theChar <= 'z')) {
            retVal = true;
        } else if ((theChar >= 'A') && (theChar <= 'Z')) {
            retVal = true;
        }
        
        return retVal;
    }
    
    private static boolean isCapital(char theChar) {
        boolean retVal = false;
        
        if ((theChar >= 'A') && (theChar <= 'Z')) {
            retVal = true;
        }
        
        return retVal;
    }
}
