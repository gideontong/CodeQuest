import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob06 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
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
                
                // separate numbers
                String[] tokens = inline.split(" ");
                String densityString = tokens[0];
                String volumeString = tokens[1];
                
                int density = getNumber(densityString);
                int volume = getNumber(volumeString);
                
                // multiply
                int mass = density * volume;
                
                // round
                int realMass = mass / 100;
                int remainder = mass % 100;
                if (remainder >= 50) {
                    // round up
                    realMass++;
                }
                
                // print
                System.out.print("" + (realMass / 100));
                System.out.print(".");
                
                int decimalPart = realMass % 100;
                if (decimalPart < 10) System.out.print("0");
                System.out.println(decimalPart);
                
                // END OF TEST CASE CODE
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static int getNumber(String numberString) {
        int retVal = 0;
        
        // split the string
        String[] tokens = numberString.split("\\.");
        
        // integer part
        retVal = Integer.parseInt(tokens[0]);
        
        // multiply
        retVal *= 100;
        
        // decimal part
        retVal += Integer.parseInt(tokens[1]);
        
        return retVal;
    }
}
