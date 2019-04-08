

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;

public class Prob02 {
    private static final String INPUT_FILE_NAME = "Prob02.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                BigInteger total = BigInteger.ZERO;
                
                // read the number of teams
                int N = Integer.parseInt(br.readLine());
                
                // loop through the counts from each team
                while (N-- > 0) {
                    BigInteger currentCount = new BigInteger(br.readLine());
                    total = total.add(currentCount);
                }
                
                // print the result
                System.out.println(total);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
