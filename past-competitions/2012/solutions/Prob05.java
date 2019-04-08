/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob05 {
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
    static double principal;
    static double rate;
    static int loanLength;
    static int monthsPaid;
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                // split on the spaces
                String[] tokens = inLine.split(" ");
                
                principal = new Double(tokens[0]).doubleValue();
                rate = new Double(tokens[1]).doubleValue();
                loanLength = new Integer(tokens[2]).intValue();
                monthsPaid = new Integer(tokens[3]).intValue();
                
                calculate();
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void calculate() {
        // compute monthly payment
        double payment = principal * ((rate / 1200) / (1 - Math.pow(1 + rate / 1200, -loanLength)));
        
        double interest = 0;
        double remainingPrincipal = principal;
        
        for (int i = 1; i <= monthsPaid; i++) {
            // monthly interest payment
            interest = remainingPrincipal * rate / 1200;
            
            // compute new remaining principal by adding interest and subtracting payment
            remainingPrincipal = remainingPrincipal + interest - payment;
            
            // check for negative loan balance!
            if (remainingPrincipal < 0) {
                remainingPrincipal = 0;
            }
        }
        
        // format the output
        System.out.format("%.2f%n", remainingPrincipal);
    }
}
