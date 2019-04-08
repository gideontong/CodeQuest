

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob08 {
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
    private static final String ABORT = ", Abort Landing!";
    private static final String CLEAR = ", Clear To Land!";
    
    private static final double MIN_SLOPE = -1.6;
    private static final double MAX_SLOPE = -0.8;
    
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
                // read the number of planes
                int N = Integer.parseInt(br.readLine());
                
                // loop through the planes
                while (N-- > 0) {
                    // read the name
                    String name = br.readLine();
                    
                    // get the coordinates of the plane
                    inLine = br.readLine();
                    String[] tokens = inLine.split(",");
                    int planeX = Integer.parseInt(tokens[0]);
                    int planeY = Integer.parseInt(tokens[1]);
                    
                    // get the coordinates of the start of the landing zone
                    inLine = br.readLine();
                    tokens = inLine.split(",");
                    int startX = Integer.parseInt(tokens[0]);
                    int startY = Integer.parseInt(tokens[1]);
                    
                    // get the coordinates of the end of the landing zone
                    inLine = br.readLine();
                    tokens = inLine.split(",");
                    int endX = Integer.parseInt(tokens[0]);
                    int endY = Integer.parseInt(tokens[1]);
                    
                    // calculate slope between plane and start of zone
                    int deltaY = planeY - startY;
                    int deltaX = planeX - startX;
                    if (deltaX != 0) {
                        double slope = (double) deltaY / (double) deltaX;
                        
                        // check tolerance
                        if ((slope >= MIN_SLOPE) && (slope <= MAX_SLOPE)) {
                            // good to go - check the plane to end of zone slope
                            deltaY = planeY - endY;
                            deltaX = planeX - endX;
                            if (deltaX != 0) {
                                slope = (double) deltaY / (double) deltaX;
                                
                                // check tolerance
                                if ((slope >= MIN_SLOPE) && (slope <= MAX_SLOPE)) {
                                    // clear to land!
                                    System.out.println(name + CLEAR);
                                } else {
                                    // plane to end is out of tolerance
                                    System.out.println(name + ABORT);
                                }
                            } else {
                                // divide by 0
                                System.out.println(name + ABORT);
                            }
                        } else {
                            // plane to start is out of tolerance
                            System.out.println(name + ABORT);
                        }
                    } else {
                        // divide by 0
                        System.out.println(name + ABORT);
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
