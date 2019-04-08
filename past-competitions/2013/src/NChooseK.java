/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;


public class NChooseK {
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                // split on the spaces
                String[] tokens = inLine.split(" ");
                
                // get the number of enemies and the number of missiles
                long n = Long.valueOf(tokens[0]);
                long k = Long.valueOf(tokens[1]);
                
                // compute the factorials
                BigInteger nFactorial = factorial(n);
                BigInteger kFactorial = factorial(k);
                BigInteger nMinusKFactorial = factorial(n-k);
                
                // compute the result
                BigInteger result = nFactorial;
                result = result.divide(kFactorial);
                result = result.divide(nMinusKFactorial);
                
                // print the result
                System.out.println(result);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static BigInteger factorial(long number) {
        // get the factorial
        return fact(BigInteger.valueOf(number));
    }
    
    private static BigInteger fact(BigInteger n) {
        // recursion to get the factorial
        if (n.compareTo(BigInteger.ZERO) == 0) {
            // 0! = 1
            return BigInteger.ONE;
        } else {
            // multiply this number by n-1!
            return n.multiply(fact(n.subtract(BigInteger.ONE)));
        }
    }
}
