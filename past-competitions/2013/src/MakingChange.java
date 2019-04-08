/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class MakingChange {
    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
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
            
            String[] names = {"TWENTY", "TEN", "FIVE", "ONE", "QUARTER", "DIME", "NICKEL", "PENNY"};
            int[] values = {2000, 1000, 500, 100, 25, 10, 5, 1};
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                String[] tokens = inLine.split(", ");
                
                String oweString = tokens[0];
                String paidString = tokens[1];
                
                
                tokens = oweString.split("\\.");
                int owe = (Integer.valueOf(tokens[0]).intValue() * 100) + (Integer.valueOf(tokens[1]).intValue());
                
                tokens = paidString.split("\\.");
                int paid = (Integer.valueOf(tokens[0]).intValue() * 100) + (Integer.valueOf(tokens[1]).intValue());
                
                int change = paid - owe;
                
                if (change > 0) {
                    System.out.print("$");
                    if ((change / 100) > 0) {
                        System.out.print((change / 100));
                    }
                    System.out.print(".");
                    System.out.print((change % 100));
                    if ((change % 100) == 0) {
                        System.out.print("0");
                    }
                    
                    for (int i=0; i<names.length; i++) {
                        if ((change / values[i]) > 0) {
                            System.out.print(", " + names[i] + "=" + (change / values[i]));
                            change %= values[i];
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println("NONE");
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
