import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob15 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob15.in.txt";
    
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
                
                // split into characters
                char[] chars = inLine.toCharArray();
                
                // encode each character
                for (int i=0; i<chars.length; i++) {
                    char currentChar = chars[i];
                    
                    // get the int value - A is 65
                    int value = currentChar - 64;
                    
                    if (value <= 5) {
                        // A-E - add 6
                        value += 6;
                    } else if (value <= 10) {
                        // F-J - square
                        value *= value;
                    } else if (value <= 15) {
                        // K-O - divide by 3, multiply remainder by 5, add 1
                        value %= 3;
                        value *= 5;
                        value += 1;
                    } else if (value <= 20) {
                        // P-T - add sum of digits, multiply that by 8
                        value = (value / 10) + (value % 10);
                        value *= 8;
                    } else {
                        // U-Z - find biggest factor less than itself, multiply that by 2
                        for (int factor=value-1; factor>0; factor--) {
                            if ((value % factor) == 0) {
                                value = factor;
                                break;
                            }
                        }
                        
                        value *= 2;
                    }
                    
                    // handle numbers bigger than 26
                    while (value > 26) {
                        value -= 26;
                    }
                    
                    // print the encoded character
                    char outChar = (char) (64 + value);
                    System.out.print(outChar);
                }
                
                // new line
                System.out.println();
                
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
