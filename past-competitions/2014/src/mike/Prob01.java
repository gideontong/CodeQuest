/**
 * CodeQuest 2014
 * Prob01: When I Say Code, You Say Quest!
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob01 {
    private static final String INPUT_FILE_NAME = "Prob01.in.txt";
    
    public static void main(String[] args) {
        // read the input file and process it
        readInput();
    }

    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            boolean printed = false;
            StringBuffer buf = new StringBuffer();
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                printed = false;
                
                int currentInt = Integer.parseInt(inLine);
                
                if ((currentInt % 3) == 0) {
                    buf.append("CODE");
                    printed = true;
                }
                
                if ((currentInt % 7) == 0) {
                    buf.append("QUEST");
                    printed = true;
                }
                
                if (!printed) {
                    buf.append(inLine);
                }
                
                buf.append("\n");
            }
            
            System.out.print(buf.toString());
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
