import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob14 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob14.in.txt";
    
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
                
                // read the next test case
                inLine = br.readLine();
                
                // split it up
                String[] tokens = inLine.split(",");
                
                // get the two numbers
                int num1 = Integer.parseInt(tokens[0]);
                int num2 = Integer.parseInt(tokens[1]);
                
                // minuend, subtrahend, difference
                int minuend = getMax(num1, num2);
                int subtrahend = getMin(num1, num2);
                int difference = minuend - subtrahend;
                System.out.println(minuend + "-" + subtrahend + "=" + difference);
                
                // keep going until we are done
                while (difference > 0) {
                    num1 = subtrahend;
                    num2 = difference;
                    
                    minuend = getMax(num1, num2);
                    subtrahend = getMin(num1, num2);
                    difference = minuend - subtrahend;
                    System.out.println(minuend + "-" + subtrahend + "=" + difference);
                }
                
                // are they coprime?
                if (minuend > 1) {
                    System.out.println("NOT COPRIME");
                } else {
                    System.out.println("COPRIME");
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
    
    private static int getMax(int num1, int num2) {
        if (num1 > num2) {
            return num1;
        } else {
            return num2;
        }
    }
    
    private static int getMin(int num1, int num2) {
        return -getMax(-num1, -num2);
    }
}
