package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 11:13 --> 11:42
 * 11:48 --> 11:53
 * @author nortoha
 *
 */
public class Prob12_MessageIntegrity {
    private static final String INPUT_FILE_NAME = "Prob12.in.txt";
    
    static final boolean[] P3 = new boolean[]{true, false, true, true};
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob12_MessageIntegrity.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // read the line of text
                inLine = br.readLine();
                
                // print output
                if(isCorrupt(inLine)){
                    System.out.println("corrupt");
                } else {
                    System.out.println("ok");
                }
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isCorrupt(String inLine) {

        boolean[] remainder = toBooleanArray(inLine);;
        
        // convert to booleans
        boolean[] inArray = toBooleanArray(inLine);
        
        int dataIdx = 0;
        boolean atTheEnd = false;
        
        while(!atTheEnd && dataIdx <= inArray.length-P3.length){
            
            int p=0;
            while(p<4){
                
                // go through each character
                boolean b = remainder[dataIdx];
                
                remainder[dataIdx] = b ^ P3[p];
                
                dataIdx++;
                p++;
            }
            
            if((p) == inArray.length){
                atTheEnd = true;
                
            } else {
                // copy the rest of the remainder down
                for(int i=dataIdx; i<inArray.length; i++){
                    remainder[i] = inArray[i];
                }
                
                
                // shift index over to the next 1 in the inLineArray
                dataIdx = findNextOne(remainder);
            }
        }
        
        // check remainder and return
        for(int i =0; i<remainder.length; i++){
            if(remainder[i])
                return true;
        }
        
        return false;
    }

    /**
     * helper method to find the next 1 (or true) in the remainder array and return that index 
     * so we know where to shift P3 to
     * @param remainder
     * @return
     */
    private static int findNextOne(boolean[] remainder) {

        for(int i=0; i<remainder.length; i++){
            if(remainder[i])
                return i;
        }
        return remainder.length;
    }

    /**
     * helper method to convert the given string into boolean array
     * @param inLine
     * @return
     */
    private static boolean[] toBooleanArray(String inLine) {

        boolean[] retVal = new boolean[inLine.length()];
        
        for(int i=0; i<inLine.length(); i++){
            int digit = Integer.parseInt(String.valueOf(inLine.charAt(i)));
            
            if(digit == 1){
                retVal[i]= true; 
            } else {
                retVal[i] = false;
            }
        }
        return retVal;
    }
}
