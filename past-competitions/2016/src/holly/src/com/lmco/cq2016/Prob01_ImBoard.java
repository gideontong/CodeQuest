package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 2 minutes
 * @author nortoha
 *
 */
public class Prob01_ImBoard {
    private static final String INPUT_FILE_NAME = "Prob01.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob01_ImBoard.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {

                // read the line of text
                int n = Integer.parseInt(br.readLine());
                
                // print it out
                for(int i=0; i<n; i++){
                    for(int j=0; j<n; j++){
                        System.out.print("#");
                        if(j<n-1)
                            System.out.print(" ");
                    }
                    System.out.println();
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
