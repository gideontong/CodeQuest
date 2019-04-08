import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Prob16 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob16.in.txt";
    
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
                
                // get the data
                String[] tokens = inLine.split(" ");
                double xc = Double.parseDouble(tokens[0]);
                double yc = Double.parseDouble(tokens[1]);
                double p = Double.parseDouble(tokens[2]);
                double r1 = Double.parseDouble(tokens[3]);
                double r2 = Double.parseDouble(tokens[4]);
                
                // compute angle difference
                double angleDiff = Math.PI / p;
                
                // get number of points
                int numPoints = Integer.parseInt(tokens[2]) * 2;
                
                // start at pi/2
                double currentAngle = Math.PI / 2;
                
                // compute points
                double x, y, r;
                for (int i=0; i<numPoints; i++) {
                    if (i > 0) System.out.print(" ");
                    
                    r = ((i % 2) == 0) ? r1 : r2;
                    x = (r * Math.cos(currentAngle)) + xc;
                    y = (r * Math.sin(currentAngle)) + yc;
                    
                    roundAndPrint(x);
                    System.out.print(",");
                    roundAndPrint(y);
                    
                    currentAngle += angleDiff;
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
    
    private static void roundAndPrint(double value) {
        int places = 2;
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.print(df.format(bd.doubleValue()));
    }
}
