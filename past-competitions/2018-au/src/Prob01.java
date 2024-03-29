import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob01 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob01.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // get the grade
                int grade = Integer.parseInt(br.readLine());
                
                if (grade >= 70) {
                    // pass
                    System.out.println("PASS");
                } else {
                    // fail
                    System.out.println("FAIL");
                }
                
                // END OF TEST CASE CODE
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
