import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob07 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            for (int i=0; i<T; i++) {
                // BEGINNING OF TEST CASE CODE
                
                // get the line
                String inline = br.readLine();
                
                // separate words
                String[] tokens = inline.split(" ");
                String wrong = tokens[0];
                String right = tokens[1];
                
                // check to see if we would interpret it correctly
                if ((wrong.charAt(0) == right.charAt(0)) && (wrong.charAt(wrong.length()-1) == right.charAt(right.length()-1))) {
                    // interpret as correct
                    System.out.println(right);
                } else {
                    System.out.println(wrong);
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
