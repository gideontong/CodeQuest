/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;

public class Prob10 {
    private static final String INPUT_FILE_NAME = "Prob10.in.txt";
    
    static ArrayList<Integer> numbers = new ArrayList<Integer>();
    
    public static void main(String[] args) {
        readInput();
        
        for (int i=0; i<numbers.size(); i++) {
            // get the number
            long num = new Integer(numbers.get(i)).longValue();
            
            // print the factorial of each number in the list
            System.out.println(factorial(num));
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
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                // keep track of numbers to use
                numbers.add(new Integer(inLine));
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
