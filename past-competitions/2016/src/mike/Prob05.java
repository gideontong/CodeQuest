

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob05 {
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
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
                
                // print out the original
                System.out.println("Total of the bill: " + inLine);
                
                // strip the $
                inLine = inLine.substring(1);
                
                // split on . - remember to escape!
                String[] tokens = inLine.split("\\.");
                
                // get dollars
                int dollars = Integer.parseInt(tokens[0]);
                
                // get cents
                int cents = Integer.parseInt(tokens[1]);
                
                // add dollars to cents
                cents += (dollars * 100);
                
                // calculate tips
                calculate(cents, 15);
                calculate(cents, 18);
                calculate(cents, 20);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void calculate(int cents, int percent) {
        // calculate the tip not as a percentage yet
        int tip = cents * percent;
        
        // get the remainder when dividing by 100
        int remainder = tip % 100;
        
        // divide by 100 to lose the remainder
        tip /= 100;
        
        // round up if we're at half a penny or more
        if (remainder >= 50) {
            // round up
            tip++;
        }
        
        // build the output
        StringBuffer buf = new StringBuffer();
        buf.append(percent);
        buf.append("% = $");
        buf.append(tip/100);
        buf.append(".");
        if ((tip%100) < 10) buf.append("0");
        buf.append(tip%100);
        
        // write it out
        System.out.println(buf.toString());
    }
}
