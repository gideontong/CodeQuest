import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob15 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob15.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // get the board size - this actually doesn't matter.  Muahahaha!!
                inLine = br.readLine();
                
                // get the start position
                inLine = br.readLine();
                String[] tokens = inLine.split(",");
                int r1 = Integer.parseInt(tokens[0]);
                int c1 = Integer.parseInt(tokens[1]);
                
                // get the end position
                inLine = br.readLine();
                tokens = inLine.split(",");
                int r2 = Integer.parseInt(tokens[0]);
                int c2 = Integer.parseInt(tokens[1]);
                
                // get the total difference between the start and end position
                int difference = (r2 - r1) + (c2 - c1);
                
                // if the difference is even, we can get there.  If not, we can't
                if ((difference % 2) == 0) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
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
