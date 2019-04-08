import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob11 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob11.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            int[] values = new int[10];
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // read the next line of text
                inLine = br.readLine();
                
                // split the line
                String[] tokens = inLine.split(" ");
                
                // get the values
                for (int i=0; i<10; i++) {
                    values[i] = Integer.parseInt(tokens[i]);
                }
                
                // find distance between foreground and chroma key
                int intDist = 0;
                for (int i=0; i<3; i++) {
                    int tempDist = values[i+4] - values[i];
                    tempDist = tempDist * tempDist;
                    intDist += tempDist;
                }
                double dist = Math.sqrt(intDist);
                
                // check for tolerance
                double tolerance = (double) values[3];
                
                int index = 4;
                if (dist < tolerance) {
                    index += 3;
                }
                
                // print values
                for (int i=0; i<3; i++) {
                    if (i > 0) System.out.print(" ");
                    System.out.print(values[i+index]);
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
