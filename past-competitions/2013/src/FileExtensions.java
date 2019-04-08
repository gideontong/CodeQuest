/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;


public class FileExtensions {
    private static final String INPUT_FILE_NAME = "Prob03.in.txt";
    
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
            ArrayList<String> names = new ArrayList<String>();
            Hashtable<String, Integer> counts = new Hashtable<String, Integer>();
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                String extension = inLine.split("\\.")[1];
                if (!counts.containsKey(extension)) {
                    names.add(extension);
                    counts.put(extension, new Integer(0));
                }
                
                Integer count = counts.get(extension);
                counts.put(extension, new Integer(count.intValue() + 1));
            }
            
            for (int i=0; i<names.size(); i++) {
                System.out.println(names.get(i) + " " + counts.get(names.get(i)).intValue());
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
