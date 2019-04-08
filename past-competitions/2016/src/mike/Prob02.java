

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob02 {
    private static final String INPUT_FILE_NAME = "Prob02.in.txt";
    
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
            while (T-- > 0) {
                // read the line of text
                inLine = br.readLine();
                
                // print the line as it is
                System.out.println(inLine);
                
                // strip the $
                inLine = inLine.substring(1);
                
                // split on the decimal - escape it because . matches everything!
                String[] tokens = inLine.split("\\.");
                
                // get dollars
                int dollars = Integer.parseInt(tokens[0]);
                
                // get cents
                int cents = Integer.parseInt(tokens[1]);
                
                // add dollars to cents
                cents = cents + (dollars * 100);
                
                int quarters = 0;
                int dimes = 0;
                int nickels = 0;
                
                // quarters
                while (cents >= 25) {
                    quarters++;
                    cents -= 25;
                }
                System.out.println("Quarters=" + quarters);
                
                // dimes
                while (cents >= 10) {
                    dimes++;
                    cents -= 10;
                }
                System.out.println("Dimes=" + dimes);
                
                // nickels
                while (cents >= 5) {
                    nickels++;
                    cents -= 5;
                }
                System.out.println("Nickels=" + nickels);
                
                // pennies
                System.out.println("Pennies=" + cents);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
