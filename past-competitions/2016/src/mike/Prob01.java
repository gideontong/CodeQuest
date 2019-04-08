

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
                // read the line of text
                inLine = br.readLine();
                
                // get N
                int n = Integer.parseInt(inLine);
                
                // print the board
                for (int i=0; i<n; i++) {
                    for (int j=0; j<n; j++) {
                        if (j > 0) System.out.print(" ");
                        System.out.print("#");
                    }
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
