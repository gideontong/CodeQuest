

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob03 {
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
            
            // loop through test cases
            while (T-- > 0) {
                // read the line of text
                inLine = br.readLine();
                
                // get the side lengths
                String[] tokens = inLine.split(", ");
                
                // get an array of ints
                int[] sides = new int[3];
                
                for (int i=0; i<3; i++) {
                    sides[i] = Integer.parseInt(tokens[i]);
                }
                
                // sort it
                Arrays.sort(sides);
                
                // is the triangle viable?
                if ((sides[0] + sides[1]) <= sides[2]) {
                    // triangle doesn't work
                    System.out.println("Not a Triangle");
                } else {
                    if ((sides[0] == sides[1]) && (sides[1] == sides[2])) {
                        // three equal sides
                        System.out.println("Equilateral");
                    } else {
                        if ((sides[0] == sides[1]) || (sides[1] == sides[2]) || (sides[2] == sides[0])) {
                            // two equal sides
                            System.out.println("Isosceles");
                        } else {
                            // no equal sdes
                            System.out.println("Scalene");
                        }
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
}
