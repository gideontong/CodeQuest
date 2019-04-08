

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob12 {
    private static final String INPUT_FILE_NAME = "Prob12.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            for (int i=0; i<T; i++) {
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                // for dividing
                long numerator = 1;
                long denominator = 1;
                
                // multiply by 18% interest rate
                numerator *= 18;
                denominator *= 100;
                
                // divide by number of days
                denominator *= N;
                
                // divide by number of billing periods
                denominator *= 12;
                
                // when you work with money you always want to work with pennies!
                long balanceTotal = 0;
                long currentBalance = 0;
                int dollars = 0;
                int cents = 0;
                
                // loop through the lines
                for (int j=0; j<N; j++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // split on the comma
                    String[] tokens = inLine.split(",");
                    
                    if (tokens.length > 1) {
                        // there is a purchase amount, but it could be empty!
                        String purchaseString = tokens[1];
                        
                        if (purchaseString.length() > 0) {
                            // we have a purchase - unfortunately, a period is special in a regex
                            String[] dollarsAndCentsArray = purchaseString.split("\\.");
                            
                            dollars = Integer.parseInt(dollarsAndCentsArray[0]);
                            cents = Integer.parseInt(dollarsAndCentsArray[1]);
                            
                            currentBalance += (dollars * 100);
                            currentBalance += cents;
                        }
                        
                        if (tokens.length > 2) {
                            // there is a payment amount, but it could be empty!
                            String paymentString = tokens[2];
                            
                            if (paymentString.length() > 0) {
                                // we have a payment - unfortunately, a period is special in a regex
                                String[] dollarsAndCentsArray = paymentString.split("\\.");
                                
                                dollars = Integer.parseInt(dollarsAndCentsArray[0]);
                                cents = Integer.parseInt(dollarsAndCentsArray[1]);
                                
                                currentBalance -= (dollars * 100);
                                currentBalance -= cents;
                            }
                        }
                    }
                    // add the current balance to the total
                    balanceTotal += currentBalance;
                }
                
                // multiply by sum of balances
                numerator *= balanceTotal;
                
                // divide by 100 because our values are all in pennies!
                denominator *= 100;
                
                // now divide
                System.out.println("$" + round(numerator, denominator, 2));
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Roll my own rounding so I can be sure it's correct
    public static String round(long numerator, long denominator, long numDecimalPlaces) {
        StringBuffer buf = new StringBuffer();
        
        // always have to multiply by 10 to see the next digit after the decimal
        long multiplyBy = 1;
        
        // multiply by 10 for each decimal place to print
        for (int i=0; i<numDecimalPlaces; i++) {
            multiplyBy *= 10;
        }
        
        // calculate the answer times ten
        long answerTimesTen = (numerator * multiplyBy * 10) / denominator;
        
        // get the real answer and the first decimal
        long firstDecimalPoint = answerTimesTen % 10;
        long answer = answerTimesTen / 10;
        
        // round if necessary
        if (firstDecimalPoint >= 5) {
            answer++;
        }
        
        buf.append((answer/multiplyBy));
        buf.append(".");
        
        String remainder = "" + (answer%multiplyBy);
        
        // print extra zeroes if necessary
        for (int i=0; i<(numDecimalPlaces-remainder.length()); i++) {
            buf.append("0");
        }
        
        buf.append(remainder);
        
        return buf.toString();
    }
}
