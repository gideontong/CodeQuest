

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob04 {
    private static final String INPUT_FILE_NAME = "Prob04.in.txt";
    
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
            while (T-- > 0) {
                // read the line of text
                inLine = br.readLine();
                
                // start the output
                System.out.print(inLine + " = ");
                
                // split on the pipe - escape it because pipes are special in regular expressions!
                String[] tokens = inLine.split("\\|");
                
                // get the words
                String word1 = tokens[0];
                String word2 = tokens[1];
                
                if (word1.equals(word2)) {
                    // same word - not an anagram
                    System.out.print("NOT AN ");
                } else if (word1.length() == word2.length()) {
                    // same length - check 'em
                    int[] counts = new int[26];
                    Arrays.fill(counts, 0);
                    
                    int index = 0;
                    
                    // add letters in word1
                    for (int i=0; i<word1.length(); i++) {
                        // do some char math to get the index
                        index = word1.charAt(i);
                        index -= 'A';
                        
                        counts[index]++;
                    }
                    
                    // subtract letters from word2
                    for (int i=0; i<word2.length(); i++) {
                        // do some char math to get the index
                        index = word2.charAt(i);
                        index -= 'A';
                        
                        counts[index]--;
                    }
                    
                    // if any count is not 0, there's a mismatch
                    for (int i=0; i<counts.length; i++) {
                        if (counts[i] != 0) {
                            System.out.print("NOT AN ");
                            break;
                        }
                    }
                } else {
                    // different lengths - not an anagram
                    System.out.print("NOT AN ");
                }
                
                // finish it
                System.out.println("ANAGRAM");
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
