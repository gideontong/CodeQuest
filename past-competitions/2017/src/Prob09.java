

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
            
            // loop through test cases
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                // read in the keyword
                String keyword = br.readLine();
                int keywordLength = keyword.length();
                int keywordIndex = -1;
                
                // loop through chracters
                for (int i=0; i<inLine.length(); i++) {
                    char currentChar = inLine.charAt(i);
                    
                    if (currentChar == ' ') {
                        // print the space
                        System.out.print(" ");
                    } else if ((currentChar < 'A') || (currentChar > 'Z')) {
                        // illegal - take out later
                        System.out.println("ERROR!");
                    } else {
                        // encode this letter
                        keywordIndex++;
                        if (keywordIndex == keywordLength) {
                            keywordIndex = 0;
                        }
                        
                        // get the keyword character that the alphabet starts with
                        char keywordChar = keyword.charAt(keywordIndex);
                        
                        // get the offset from the real alphabet
                        int keywordOffset = keywordChar - 'A';
                        
                        // get the offset of the character we're encoding
                        int currentCharOffset = currentChar - 'A';
                        
                        // compute the encoded letter - mod by 26 in case we go over.
                        int result = ((keywordOffset + currentCharOffset) % 26) + 'A';
                        char newChar = (char) result;
                        System.out.print(newChar);
                    }
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
}
