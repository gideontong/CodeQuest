import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob05 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // get the starting number
                int n = Integer.parseInt(br.readLine());
                
                // sequence length
                int length = 1;
                
                // start the output line
                System.out.print(n + ":");
                
                while (n > 1) {
                    if ((n % 2) == 0) {
                        // current number is even
                        n /= 2;
                    } else {
                        // current number is odd
                        n = (3 * n) + 1;
                    }
                    
                    // increment sequence length
                    length++;
                }
                
                // finish the output line
                System.out.println(length);
                
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
