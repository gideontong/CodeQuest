/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RemoveSoftware {
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
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
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date referenceDate = null;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                if (referenceDate == null) {
                    // set the reference date
                    referenceDate = sdf.parse(inLine);
                } else {
                    // new item
                    String[] tokens = inLine.split(":");
                    String name = tokens[0];
                    Date lastUsed = sdf.parse(tokens[1]);
                    
                    int timeDiff = (int)((referenceDate.getTime() - lastUsed.getTime()) / (1000 * 60 * 60 * 24));
                    if (timeDiff > 180) {
                        System.out.println(name + ", " + timeDiff);
                    }
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
