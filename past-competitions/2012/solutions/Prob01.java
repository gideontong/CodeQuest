/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Prob01 {
    private static final String INPUT_FILE_NAME = "Prob01.in.txt";
    
    static int numQuarters = 0;
    static int numDimes = 0;
    static int numPennies = 0;
    static int numNickels = 0;
    static int numHalfDollars = 0;
    
    public static void main(String[] args) {
        readInput();
        
        // get the total value in pennies
        int totalValue = numPennies;
        totalValue += (numNickels * 5);
        totalValue += (numDimes * 10);
        totalValue += (numQuarters * 25);
        totalValue += (numHalfDollars * 50);
        
        // compute dollars and cents
        int numDollars = totalValue / 100;
        int numCents = totalValue % 100;
        
        System.out.println("$" + numDollars + "." + numCents);
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                // split on the equals sign
                String[] tokens = inLine.split("=");
                
                // get the coin type and how many there are
                String coinType = tokens[0];
                int numCoins = new Integer(tokens[1]).intValue();
                
                // add to the running totals if we care about this coin type
                if (coinType.equals("QUARTER")) {
                    numQuarters += numCoins;
                } else if (coinType.equals("DIME")) {
                    numDimes += numCoins;
                } else if (coinType.equals("NICKEL")) {
                    numNickels += numCoins;
                } else if (coinType.equals("HALFDOLLAR")) {
                    numHalfDollars += numCoins;
                } else if (coinType.equals("PENNY")) {
                    numPennies += numCoins;
                }
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
