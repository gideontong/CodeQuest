

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob10 {
    private static final String INPUT_FILE_NAME = "Prob10.in.txt";
    
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
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                int previousAltitude = 0;
                int currentAltitude = 0;
                int futureAltitude = 0;
                
                int currentElevation = 0;
                int futureElevation = 0;
                
                // loop through the lines
                for (int i=0; i<N; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // split on the comma
                    String[] tokens = inLine.split(",");
                    
                    // get current altitude and future elevation
                    currentAltitude = Integer.parseInt(tokens[0]);
                    futureElevation = Integer.parseInt(tokens[1]);
                    
                    // calculate future altitude
                    int altitudeDifference = currentAltitude - previousAltitude;
                    futureAltitude = currentAltitude + altitudeDifference;
                    
                    // check to see if we will crash
                    if (futureAltitude <= futureElevation) {
                        // crash is coming
                        System.out.println("PULL UP!");
                    } else {
                        // no crash - check for low altitude
                        if ((currentAltitude - currentElevation) <= 500) {
                            // low altitude
                            System.out.println("Low Altitude!");
                            if ((currentAltitude - currentElevation) <= 0) {
                                System.out.println("*****CRASH*****");
                            }
                        } else {
                            // ok
                            System.out.println("ok");
                        }
                    }
                    
                    // move the plane
                    previousAltitude = currentAltitude;
                    currentElevation = futureElevation;
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
