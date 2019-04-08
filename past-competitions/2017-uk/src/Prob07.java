

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob07 {
    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
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
                
                // put the string in a char array
                char[] charArray = inLine.toCharArray();
                
                // print it in reverse
                for (int i=charArray.length-1; i>=0; i--) {
                    System.out.print(charArray[i]);
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
