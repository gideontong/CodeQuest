

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob06 {
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    private static final String[] words = {"Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet", "Kilo", "Lima", "Mike", "November", "Oscar", "Papa", "Quebec", "Romeo", "Sierra", "Tango", "Uniform", "Victor", "Whiskey", "Xray", "Yankee", "Zulu"};
    private static final char lowercaseA = 'a';
    
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
            for (int i=0; i<T; i++) {
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                // loop through the lines
                for (int j=0; j<N; j++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // split on spaces
                    String[] tokens = inLine.split(" ");
                    
                    // loop through words - make sure to print a space after the first word!
                    boolean firstWordPrinted = false;
                    for (String currentWord : tokens) {
                        // do we need to print a space?
                        if (firstWordPrinted) System.out.print(" ");
                        
                        // make the word lower case
                        currentWord = currentWord.toLowerCase();
                        
                        // print the word
                        boolean firstCharacterPrinted = false;
                        for (char currentCharacter : currentWord.toCharArray()) {
                            // do we need to print a dash?
                            if (firstCharacterPrinted) System.out.print("-");
                            
                            // print the word associated with this character
                            System.out.print(words[currentCharacter - lowercaseA]);
                            
                            firstCharacterPrinted = true;
                        }
                        
                        firstWordPrinted = true;
                    }
                    
                    // next line
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
