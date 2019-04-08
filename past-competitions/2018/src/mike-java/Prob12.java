import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob12 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob12.in.txt";
    
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
                
                // get the limit of the sieve
                int N = Integer.parseInt(br.readLine());
                
                // create an array to tell us if numbers are prime - just ignore position 0 and 1.
                boolean[] isPrime = new boolean[N+1];
                
                // make sure it's initialized to true - assume we're a prime until we get sieved out
                Arrays.fill(isPrime, true);
                
                // index to tell us where we are - always start at 2!
                int index = 2;
                
                // string to hold the prime list
                String primeList = "{";
                
                // loop until we're through the list
                while (index <= N) {
                    // is this number a prime?
                    if (isPrime[index]) {
                        // we have a new prime!  Add it to the string
                        if (index > 2) {
                            // add a comma if it isn't first in the list
                            primeList += ",";
                        }
                        primeList += index;
                        
                        // index to tell us where we are when looking for composites - start with the first composite
                        int compositeIndex = index * index;
                        
                        // size of the composite set
                        int compositeSetSize = 0;
                        
                        // find composites of this prime
                        while (compositeIndex <= N) {
                            if (isPrime[compositeIndex]) {
                                // sieve this number out!
                                isPrime[compositeIndex] = false;
                                compositeSetSize++;
                            } else {
                                // this number has been sieved out, do nothing
                            }
                            
                            // move to the next number
                            compositeIndex += index;
                        }
                        
                        // done sieving numbers - did we get any?
                        if (compositeSetSize > 0) {
                            // print the size of the set we just eliminated
                            System.out.println("Prime " + index + " Composite Set Size: " + compositeSetSize);
                        } else {
                            // no numbers sieved by this prime - do nothing
                        }
                    } else {
                        // this number has been sieved out, do nothing
                    }
                    
                    // move to the next number
                    index++;
                }
                
                // add the end curly brace
                primeList += "}";
                
                // print the prime list
                System.out.println(primeList);
                
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
