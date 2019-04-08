/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Prob02 {
    private static final String INPUT_FILE_NAME = "Prob02.in.txt";
    
    static ArrayList<Integer> tValues = new ArrayList<Integer>();
    static ArrayList<Integer> hValues = new ArrayList<Integer>();
    
    public static void main(String[] args) {
        readInput();
        
        String message = null;
        
        for (int i=0; i<tValues.size(); i++) {
            int t = tValues.get(i).intValue();
            int h = hValues.get(i).intValue();
            
            // compute twice the difference
            int difference = 2 * (t - h);
            
            // check the condition and print out the correct phrase
            if (difference == 0) {
                message = "Times and Herald have the same number of subscribers";
            } else if (difference > 0) {
                message = "Times has " + difference + " more subscribers";
            } else {
                difference *= -1;
                message = "Herald has " + difference + " more subscribers";
            }
            
            System.out.println(message);
        }
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                // split on the space
                String[] tokens = inLine.split(" ");
                
                tValues.add(new Integer(tokens[0]));
                hValues.add(new Integer(tokens[1]));
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
