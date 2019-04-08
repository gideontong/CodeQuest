import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob08 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
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
                
                // split into x, y, and z
                String[] tokens = inLine.split(" ");
                
                // now subtract and print
                for (int angleNum=0; angleNum<3; angleNum++) {
                    // get the angle and split on the decimal
                    String angle = tokens[angleNum];
                    String[] angleTokens = angle.split("\\.");
                    
                    // get the whole part of the angle
                    String wholePartString = angleTokens[0];
                    int wholePart = Integer.parseInt(wholePartString);
                    
                    // subtract 180
                    wholePart -= 180;
                    
                    // handle negative angle result
                    if (wholePart < 0) {
                        wholePart += 360;
                    }
                    
                    // print whole part
                    if (wholePart < 100) {
                        System.out.print("0");
                    }
                    if (wholePart < 10) {
                        System.out.print("0");
                    }
                    System.out.print(wholePart);
                    
                    // print decimal
                    System.out.print(".");
                    
                    // print decimal part
                    System.out.print(angleTokens[1]);
                    
                    // print space unless it's the last one
                    if (angleNum < 2) {
                        System.out.print(" ");
                    }
                }
                
                // new line
                System.out.println();
                
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
