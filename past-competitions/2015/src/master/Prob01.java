

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob01 {
    private static final String INPUT_FILE_NAME = "Prob01.in.txt";
    
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
                // read in the number of names
                int N = Integer.parseInt(br.readLine());
                
                // loop through the names
                while (N-- > 0) {
                    // read the name
                    inLine = br.readLine();
                    
                    // split on spaces
                    String[] tokens = inLine.split(" ");
                    
                    // print the monogram
                    System.out.print(tokens[0].substring(0, 1).toUpperCase());
                    System.out.print(tokens[2].substring(0, 1).toUpperCase());
                    System.out.print(tokens[1].substring(0, 1).toUpperCase());
                    System.out.println();
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
