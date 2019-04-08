

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob05 {
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
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
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                // split on commas
                String[] tokens = inLine.split(",");
                
                // get an array
                int[] intArray = new int[tokens.length];
                
                // add numbers to array
                for (int i=0; i<tokens.length; i++) {
                    intArray[i] = Integer.parseInt(tokens[i]);
                }
                
                // sort the array
                Arrays.sort(intArray);
                
                for (int j=0; j<intArray.length; j++) {
                    if (j > 0) System.out.print(",");
                    System.out.print(intArray[j]);
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
