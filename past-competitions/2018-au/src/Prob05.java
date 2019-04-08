import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob05 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            String[] names = new String[T];
            
            // outer loop through test cases
            for (int i=0; i<T; i++) {
                // BEGINNING OF TEST CASE CODE
                
                // get the name
                names[i] = br.readLine();
                
                // END OF TEST CASE CODE
            }
            
            // sort
            Arrays.sort(names);
            
            // print
            for (int i=0; i<T; i++) {
                System.out.println(names[i]);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
