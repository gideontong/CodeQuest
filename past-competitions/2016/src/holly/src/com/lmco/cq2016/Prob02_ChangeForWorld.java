package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Completion time = 7m
 * Easiest so far
 * 
 * @author nortoha
 *
 */
public class Prob02_ChangeForWorld {
    private static final String INPUT_FILE_NAME = "Prob02.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob02_ChangeForWorld.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
            
                // read the line of text
                inLine = br.readLine();
                
                findFewestCoins(inLine);
                
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findFewestCoins(String inLine) {

        int qCnt = 0;
        int dCnt = 0;
        int nCnt = 0;
        int pCnt = 0;
        
        //remove $ sign
        String money = inLine.substring(1);
        
        //convert to pennies
        double d = Double.parseDouble(money);
        d = Math.round(d * 100);
        
        //try as many quarters as possible
        while(d>=25){
            d = d - 25;
            qCnt++;
        }
        
        //try as many dimes as possible
        while(d>=10){
            d = d - 10;
            dCnt++;
        }
        
        //try as many nickels as possible
        while(d>=5){
            d = d - 5;
            nCnt++;
        }
        
        //try as many pennies as possible
        while(d>=1){
            d = d - 1;
            pCnt++;
        }

        //print
        System.out.println(inLine);
        System.out.println("Quarters="+qCnt);
        System.out.println("Dimes="+dCnt);
        System.out.println("Nickels="+nCnt);
        System.out.println("Pennies="+pCnt);
        
        
    }
}
