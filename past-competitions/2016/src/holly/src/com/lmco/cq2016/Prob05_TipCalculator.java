package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Completion time = 8m
 * @author nortoha
 *
 */
public class Prob05_TipCalculator {
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob05_TipCalculator.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {

                // read the line of text
                inLine = br.readLine();
                
                generateTips(inLine);
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateTips(String inLine) {
        
        //remove $ sign
        String money = inLine.substring(1);
        
        double d = Double.parseDouble(money);
        //convert to pennies to deal with money
        d = Math.round(d * 100);
        
        DecimalFormat df = new DecimalFormat("0.00");
        
        System.out.println("Total of the bill: " + inLine);
        
        //15%
        System.out.println("15% = $" + df.format((Math.round(d * 0.15)) / 100.0));
        
        //18%
        System.out.println("18% = $" + df.format((Math.round(d * 0.18)) / 100.0));
        
        //20%
        System.out.println("20% = $" + df.format((Math.round(d * 0.20)) / 100.0));
    }
    
//    private static double
}
