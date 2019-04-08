

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob02 {
    private static final String INPUT_FILE_NAME = "Prob02.in.txt";
    
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
                
                // split on the space
                String[] tokens = inLine.split(" ");
                
                // get the word and the index to remove
                String word = tokens[0];
                int indexToRemove = Integer.parseInt(tokens[1]);
                
                // print everything except the character at the index
                for (int i=0; i<word.length(); i++) {
                    if (i != indexToRemove) {
                        System.out.print(word.charAt(i));
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
