import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob06 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
    private static final int[] values = {8, 4, 2, 1};
    
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
                
                // break the line up into components
                String[] tokens = inLine.split(" ");
                
                // convert the components to numbers
                int total = 0;
                for (int i=0; i<4; i++) {
                    if (tokens[i].equals("BROKEN")) {
                        total += values[i];
                    }
                }
                
                // convert the number into light states
                int[] lightStates = new int[2];
                lightStates[0] = total / 4;
                lightStates[1] = total % 4;
                
                // print out light states
                for (int i=0; i<2; i++) {
                    if (i == 1) System.out.print(" ");
                    
                    switch (lightStates[i]) {
                        case 0:
                            System.out.print("off");
                            break;
                        case 1:
                            System.out.print("red");
                            break;
                        case 2:
                            System.out.print("green");
                            break;
                        case 3:
                            System.out.print("blue");
                            break;
                        default:
                            break;
                    }
                }
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
