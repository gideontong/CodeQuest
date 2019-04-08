

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob13 {
    private static final String INPUT_FILE_NAME = "Prob13.in.txt";
    
    private static final double[] thresholds = {(double)70/(double)9, (double)85/(double)9, (double)90/(double)9};
    
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
                
                // split on the spaces
                String[] tokens = inLine.split(" ");
                
                double[] times = new double[3];
                
                // get the three times
                for (int i=0; i<3; i++) {
                    // get the time
                    times[i] = Double.parseDouble(tokens[i]);
                }
                
                // get the previous quality level
                int previousQuality = Integer.parseInt(tokens[3]);
                int newQuality = previousQuality;
                
                // was the last frame critical?
                if (times[2] > thresholds[2]) {
                    // critical
                    newQuality = previousQuality - 2;
                } else if (times[2] > thresholds[1]) {
                    // extrapolate
                    newQuality = extrapolate(previousQuality, times);
                } else if ((times[2] < thresholds[0])
                        && (times[1] < thresholds[0]) 
                        && (times[0] < thresholds[0])) {
                    // increase quality
                    newQuality = previousQuality + 1;
                }
                
                if (newQuality < 1) newQuality = 1;
                if (newQuality > 10) newQuality = 10;
                
                System.out.println(newQuality);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static int extrapolate(int previousQuality, double[] times) {
        int retVal = previousQuality;
        
        double point1, point2, slope;
        
        // calculate point1
        slope = (times[2] - times[0]) / (double)2;
        point1 = times[2] + slope;
        
        // calculate point2
        slope = (times[2] - times[1]);
        point2 = times[2] + slope;
        
        // find the bigger one
        double maxPoint = Math.max(point1, point2);
        
        if (maxPoint > thresholds[2]) {
            // max extrapolated value is critical
            retVal -= 2;
        }
        
        return retVal;
    }
}
