import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob03 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob03.in.txt";
    
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
                
                // read the next line of text
                inLine = br.readLine();
                
                // get the number
                String[] tokens = inLine.split("th");
                int theNumber = Integer.parseInt(tokens[0]);
                
                // print the number
                System.out.print(theNumber);
                
                // check it!
                if (((theNumber % 100) == 11) || ((theNumber % 100) == 12) || ((theNumber % 100) == 13)) {
                    // numbers ending in 11, 12, and 13 are special!
                    System.out.println("th");
                } else if ((theNumber % 10) == 1) {
                    // the number has a 1 in the ones place
                    System.out.println("st");
                } else if ((theNumber % 10) == 2) {
                    // the number has a 2 in the ones place
                    System.out.println("nd");
                } else if ((theNumber % 10) == 3) {
                    // the number has a 3 in the ones place
                    System.out.println("rd");
                } else {
                    // the number has a 4-9 or a 0 in the ones place
                    System.out.println("th");
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
