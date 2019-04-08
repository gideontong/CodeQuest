package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 4:20
 * 5:22 -5:41
 * @author nortoha
 *
 */
public class Prob10_GCAS {
    private static final String INPUT_FILE_NAME = "Prob10.in.txt";
    
    public static void main(String[] args) {
        
        
        try {
            // prepare to read the file
            InputStream in = Prob10_GCAS.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // get the number of lines in each test case
                int timeUnits = Integer.parseInt(br.readLine());
                
                int[] altitude = new int[timeUnits];
                int[] elevation = new int[timeUnits+1];
                
                // loop through the lines
                for (int i=0; i<timeUnits; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    String[] inLineArray = inLine.split("\\,");
                    
                    int currA = Integer.parseInt(inLineArray[0]);
                    int nextE = Integer.parseInt(inLineArray[1]);
                    
                    altitude[i] = currA;
                    
                    if(i==0){
                        elevation[i] = 0;
                        elevation[i+1] = nextE;
                    } else{ 
                        elevation[i+1] = nextE;
                    }
                }
                
                //now run ground avoidance for each time unit
                for(int i=0; i<timeUnits; i++){
                    
                    int changeInAlt = altitude[i];
                    if(i>0){
                        changeInAlt = altitude[i] - altitude[i-1];
                    }
                        
                    int predictedNextAlt = altitude[i] + changeInAlt;
                    
                    if(elevation[i+1] >= predictedNextAlt){
                        // it will crash
                        System.out.println("PULL UP!");
                    } else {
                        //check for low altitude
                        
                        if((altitude[i] - elevation[i]) <=500){
                            // low altitude
                            System.out.println("Low Altitude!");
                        } else {
                            System.out.println("ok");
                        }
                        
                    }
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
