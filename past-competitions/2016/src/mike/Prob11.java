

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Prob11 {
    private static final String INPUT_FILE_NAME = "Prob11.in.txt";
    
    private static final String normal = "abcdefghijklmnopqrstuvwxyz";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // read what to do
                inLine = br.readLine();
                boolean encrypt = inLine.equals("ENCRYPT");
                
                // read the key
                inLine = br.readLine();
                
                // decide what maps to what based on action
                String from = encrypt ? normal : inLine;
                String to = encrypt ? inLine : normal;
                
                // build the mapping
                HashMap<String, String> mapping = new HashMap<String, String>();
                for (int i=0; i<normal.length(); i++) {
                    // lowercase mapping
                    mapping.put(""+from.charAt(i), ""+to.charAt(i));
                    
                    // uppercase mapping
                    mapping.put((""+from.charAt(i)).toUpperCase(), (""+to.charAt(i)).toUpperCase());
                }
                
                // remember to map spaces!
                mapping.put(" ", " ");
                
                // get the number of lines to act on
                int N = Integer.parseInt(br.readLine());
                
                // loop through the lines
                for (int i=0; i<N; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // do the mapping
                    for (int index=0; index<inLine.length(); index++) {
                        String currentLetter = ""+inLine.charAt(index);
                        
                        System.out.print(mapping.get(currentLetter));
                    }
                    System.out.println();
                }
                
                System.out.println();
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
