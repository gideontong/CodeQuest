import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob02 {
    // change this file name for each problem!
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
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // read the next line of text
                inLine = br.readLine();
                
                // find the vowels!
                int numVowels = 0;
                for (int i=0; i<inLine.length(); i++) {
                    char currentChar = inLine.charAt(i);
                    
                    if ((currentChar == 'a') || (currentChar == 'e') || (currentChar == 'i') || (currentChar == 'o') || (currentChar == 'u')) {
                        numVowels++;
                    }
                }
                
                // print the answer
                System.out.println(numVowels);
                
                // END OF TEST CASE CODE
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
