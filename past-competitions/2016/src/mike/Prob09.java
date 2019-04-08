

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob09 {
    private static final String INPUT_FILE_NAME = "Prob09.in.txt";
    
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
                
                String[] tokens = inLine.split(" ");
                
                // get the zoom level, latitude, and longitude
                int z = Integer.parseInt(tokens[0]);
                double latitude = Double.parseDouble(tokens[1]);
                double longitude = Double.parseDouble(tokens[2]);
                
                double x = Math.pow(2, z);
                x = x * (((double) longitude) + 180.0) / 360.0;
                
                double y = 1.0 / (Math.cos((double)latitude * Math.PI / 180.0));
                y += Math.tan((double)latitude * Math.PI / 180.0);
                y = Math.log(y) / Math.PI;
                y = 1.0 - y;
                y = y * Math.pow(2, z-1);
                
                System.out.println("http://tile.openstreetmap.org/" + z + "/" + (int)x + "/" + (int)y + ".png");
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
