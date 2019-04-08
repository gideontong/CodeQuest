

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
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // read the number of temperatures that follow
                int N = Integer.parseInt(br.readLine());
                
                // loop through the temperatures
                while (N-- > 0) {
                    inLine = br.readLine();
                    
                    // start the output line
                    System.out.print(inLine + " = ");
                    
                    // split on the space
                    String[] tokens = inLine.split(" ");
                    
                    // get the temperature
                    double temp = Double.parseDouble(tokens[0]);
                    
                    // get the scale
                    String scale = tokens[1];
                    
                    if (scale.equals("C")) {
                        convertToF(temp);
                    } else if (scale.equals("F")) {
                        convertToC(temp);
                    }
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void convertToF(double c) {
        double f = (c * (9.0 / 5.0)) + 32.0;
        System.out.println(getRounded(f) + " F");
    }
    
    private static void convertToC(double f) {
        double c = (f - 32.0) * (5.0 / 9.0);
        System.out.println(getRounded(c) + " C");
    }
    
    private static String getRounded(double num) {
        boolean isNegative = false;
        
        // multiply by 100 to preserve the tenths and hundredths positions
        num *= 100;
        
        // cast to int to drop decimal places
        int temp = (int) num;
        
        // check to see if it will be negative
        if (temp < 0) {
            // negative number, is it negative enough to get a minus sign?
            if (temp <= -5) {
                isNegative = true;
            }
            
            // turn it positive
            temp *= -1;
        }
        
        // get the real part we want
        int rounded = temp / 10;
        
        // see if we need to round up
        if ((temp % 10) >= 5) {
            rounded++;
        }
        
        // build the string and return
        String retVal = (isNegative ? "-" : "") + (rounded / 10) + "." + (rounded % 10);
        return retVal;
    }
}
