import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob07 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
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
                
                // reset series result
                boolean allPalindromes = true;
                String nonPalindromes = "";
                
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                // loop through the N lines
                for (int i=0; i<N; i++) {
                    // read the next line of text
                    inLine = br.readLine();
                    
                    // check if it's a palindrome
                    if (!isPalindrome(inLine)) {
                        // not!
                        allPalindromes = false;
                        
                        if (nonPalindromes.length() > 0) nonPalindromes += ", ";
                        nonPalindromes += (i+1);
                    }
                    
                }
                
                // print result
                if (allPalindromes) {
                    System.out.println("True");
                } else {
                    System.out.println("False - " + nonPalindromes);
                }
                
                // END OF TEST CASE CODE
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static boolean isPalindrome(String theString) {
        boolean retVal = true;
        
        for (int i=0; i<theString.length(); i++) {
            char char1 = toLowerCase(theString.charAt(i));
            char char2 = toLowerCase(theString.charAt(theString.length()-1-i));
            
            if (char1 != char2) {
                retVal = false;
            }
        }
        
        return retVal;
    }
    
    private static char toLowerCase(char theChar) {
        char retVal = theChar;
        
        if ((theChar >= 65) && (theChar <= 90)) {
            // capital letter
            retVal += 32;
        }
        
        return retVal;
    }
}
