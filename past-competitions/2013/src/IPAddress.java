/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class IPAddress {
    private static final String INPUT_FILE_NAME = "Prob10.in.txt";
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                // loop through characters
                int index = 0;
                String ipClass = "";
                for (int i=0; i<4; i++) {
                    int val = 0;
                    for (int j=0; j<8; j++) {
                        val *= 2;
                        val += Integer.valueOf(""+inLine.charAt(index++));
                    }
                    System.out.print(val);
                    if (index < 32) {
                        System.out.print(".");
                    }
                    
                    if (i == 0) {
                        // determine class
                        if (val < 128) {
                            ipClass = "A";
                        } else if (val < 192) {
                            ipClass = "B";
                        } else if (val < 224) {
                            ipClass = "C";
                        } else if (val < 240) {
                            ipClass = "D";
                        } else {
                            ipClass = "E";
                        }
                    }
                }
                System.out.println(" [CLASS " + ipClass + "]");
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
