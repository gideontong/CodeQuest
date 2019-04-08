import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob04 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob04.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // get the color
                String color = br.readLine();
                
                // check it
                String VIOLET = "violet";
                String GREEN = "green";
                String ORANGE = "orange";
                if (color.contains(VIOLET) || color.contains(GREEN) || color.contains(ORANGE)) {
                    // need to mix
                    System.out.print("In order to make " + color + ", ");
                    
                    if (color.contains(VIOLET)) System.out.print("blue and red");
                    if (color.contains(GREEN)) System.out.print("blue and yellow");
                    if (color.contains(ORANGE)) System.out.print("red and yellow");
                    
                    System.out.println(" must be mixed.");
                } else {
                    // no mixing
                    System.out.println("No colors need to be mixed to make " + color + ".");
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
