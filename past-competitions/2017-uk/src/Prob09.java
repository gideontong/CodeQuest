

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob09 {
    private static final String INPUT_FILE_NAME = "Prob09.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            char[] keyArray = null;
            
            // loop through test cases
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                if (t == 0) {
                    // this is the key
                    keyArray = inLine.toCharArray();
                } else {
                    // not the key - need to decode.  Start by splitting on spaces
                    String[] words = inLine.split(" ");
                    
                    // loop through words
                    boolean firstWordPrinted = false;
                    for (String word : words) {
                        // split on dashes
                        String[] tokens = word.split("-");
                        
                        // print the word
                        if (firstWordPrinted) System.out.print(" ");
                        for (String indexString : tokens) {
                            int index = Integer.parseInt(indexString);
                            index--;
                            System.out.print(keyArray[index]);
                        }
                        
                        firstWordPrinted = true;
                    }
                    
                    System.out.println();
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
