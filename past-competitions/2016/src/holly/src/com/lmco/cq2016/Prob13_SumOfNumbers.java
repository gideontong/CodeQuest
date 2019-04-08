package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 5:45 -> 5:56 9:06
 * 
 * @author nortoha
 * 
 */
public class Prob13_SumOfNumbers {
    
    private static final String INPUT_FILE_NAME = "Prob13.in.txt";

    public static List<String> output = new ArrayList<String>();
    private static int[] numsArray;
    private static boolean[] used;
    private static int findSum;
    
    public static void main(String[] args) {

        try {
            // prepare to read the file
            InputStream in = Prob13_SumOfNumbers.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            // get the number of test cases
            int T = Integer.parseInt(br.readLine());

            // loop through test cases
            while (T-- > 0) {

                // read the sum
                String sumStr = br.readLine();

                // split and get the int we are looking for
                String[] sumParts = sumStr.split("=");
                findSum = Integer.parseInt(sumParts[1]);

                // read the given numbers
                String numbers = br.readLine();

                // split them apart
                String[] numParts = numbers.split(",");

                numsArray = new int[numParts.length];

                // sort them in ascending order
                for (int i=0; i<numParts.length; i++) {
                    numsArray[i] = Integer.parseInt(numParts[i]);
                }
                
                // sort them
                Arrays.sort(numsArray);

                // fill used array to false before we start
                used = new boolean[numsArray.length];
                Arrays.fill(used, false);
                
                // recursively find sums
                findSums(0, "");
                
                for(String s : output){
                     System.out.println(s);
                }
                 
                //reset output List
                output = null;
                output = new ArrayList<String>(); 
                                
            }

            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findSums(int runningSum, String outStr) {

        // did we get to the right sum
        if(runningSum == findSum){
            // found it
            
            // check that we haven't already add it to out output
            if(!output.contains(outStr)){
                output.add(outStr);
            }
        } else {
            // flag
            boolean keepGoing = true;
            
            // keep going, try the next number
            for(int i=0; i<numsArray.length; i++){
                
                // check that this number hasn't already been used using same index
                 if(!used[i] && keepGoing){
                    
                    // are we still less than the findSum
                    if(runningSum + numsArray[i] <= findSum){
                        // we can use this one
                        
                        // set same index as used
                        used[i] = true;
                        
                        // add it to the string to pass in again
                        String temp = "";
                        if(outStr.equals("")){
                            //don't add a plus because its the first one
                            temp = outStr + numsArray[i];
                        } else {
                            // add +
                            temp = outStr + "+" + numsArray[i];
                        }
                        
                        
                        findSums(runningSum + numsArray[i], temp);
                        
                        used[i] = false;
                    } else {
                        // we are already past the findSum so we can stop
                        i = numsArray.length;
                        keepGoing = false;
                    }
                    
                }
            }
            
        }

    }

}
