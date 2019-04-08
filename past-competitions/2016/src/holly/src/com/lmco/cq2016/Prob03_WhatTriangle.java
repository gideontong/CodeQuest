package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 7:54 --> 8:07
 * @author nortoha
 *
 */
public class Prob03_WhatTriangle {
    private static final String INPUT_FILE_NAME = "Prob03.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob03_WhatTriangle.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {

                // read the line of text
                inLine = br.readLine();
                
                // parse input into List<Integer> so i can sort the sides
                String[] inArray = inLine.split("\\,");
                List<Integer> triangleSides = new ArrayList<Integer>();
                
                for(String s : inArray){
                    triangleSides.add(Integer.parseInt(s.trim()));
                }
                
                // sort it
                Collections.sort(triangleSides);
                
                // check for triangle first
                if(triangleSides.get(0) + triangleSides.get(1) > triangleSides.get(2)){

                    // its a triangle, keep going
                    boolean b = false;
                    
                    if( (triangleSides.get(0) == triangleSides.get(1)) && (triangleSides.get(1) == triangleSides.get(2))){
                        // equilateral
                        System.out.println("Equilateral");
                        
                    } else if((triangleSides.get(0) != triangleSides.get(1)) && (triangleSides.get(1) != triangleSides.get(2))){
                        System.out.println("Scalene");
                    } else{
                        System.out.println("Isosceles");
                    }
                    
                } else {
                    System.out.println("Not a Triangle");
                }
                
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
