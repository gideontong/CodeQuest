

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob12 {
    private static final String INPUT_FILE_NAME = "Prob12.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // create the constant
            int[] chunk = new int[4];
            Arrays.fill(chunk, 1);
            chunk[1] = 0;
            
            // loop through test cases
            for (int i=0; i<T; i++) {
                // read the line of text
                inLine = br.readLine();
                
                // get the data
                String data = inLine;
                
                // split the data
                int[] digits = new int[data.length()];
                for (int j=0; j<data.length(); j++) {
                    digits[j] = Integer.parseInt(""+data.charAt(j));
                }
                
                // do the xor routine
                for (int j=0; j<=(data.length()-4); j++) {
                    if (digits[j] == 1) {
                        // do the xor
                        for (int k=0; k<4; k++) {
                            digits[j+k] = xor(digits[j+k], chunk[k]);
                        }
                    }
                }
                
                // check for non-zero items
                boolean corrupt = false;
                for (int j=0; j<data.length(); j++) {
                    if (digits[j] == 1) {
                        corrupt = true;
                        break;
                    }
                }
                
                // print if corrupt
                System.out.println(corrupt ? "corrupt" : "ok");
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static int xor(int a, int b) {
        return ((a + b) == 1) ? 1 : 0;
    }
}
