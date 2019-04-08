

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob04 {
    private static final String INPUT_FILE_NAME = "Prob04.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // compute numbers
            long[] nums = new long[90];
            nums[0] = 0;
            nums[1] = 1;
            for (int i=2; i<90; i++) {
                nums[i] = nums[i-1] + nums[i-2];
            }
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                // get the sequence place
                int sequencePlace = Integer.parseInt(inLine);
                
                // convert to array index
                int index = sequencePlace - 1;
                
                // print
                System.out.println(sequencePlace + " = " + nums[index]);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
