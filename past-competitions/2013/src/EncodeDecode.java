/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;


public class EncodeDecode {
    private static final String INPUT_FILE_NAME = "Prob13.in.txt";
    
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
            
            String[] values = {"A", "B", "X", "1", "5", "8", "U", "Q", "Y", "3", "R", "G", "K", "4", "E", "7"};
            Hashtable<String, Integer> valueMap = new Hashtable<String, Integer>();
            for (int i=0; i<values.length; i++) {
                valueMap.put(values[i], Integer.valueOf(i));
            }
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                String result = "";
                String[] tokens = inLine.split(":");
                String name = tokens[0];
                
                tokens = inLine.split("=");
                String encodedString = tokens[1];
                
                for (int i=0; i<encodedString.length(); i+=2) {
                    int decodedInt = (valueMap.get(""+encodedString.charAt(i)).intValue() * 16) + valueMap.get(""+encodedString.charAt(i+1)).intValue();
                    result += ("" + ((char)decodedInt));
                }
                System.out.println(name + ": " + result);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
