package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 12:41 -> 12:59
 * @author nortoha
 *
 */
public class Prob09_NavigatingWorld {
    private static final String INPUT_FILE_NAME = "Prob09.in.txt";
    
    static final double PI = 3.141592653589793;
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob09_NavigatingWorld.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {

                // read the line of text
                inLine = br.readLine();
                
                // parse input
                String[] inArray = inLine.split(" ");
                int zoom = Integer.parseInt(inArray[0]);
                double lat = Double.parseDouble(inArray[1]);
                double lon = Double.parseDouble(inArray[2]);
                
                // print it out
                int x = findX(zoom, lon);
                int y = findY(zoom, lat);

                System.out.println("http://tile.openstreetmap.org/" + zoom + "/" + x + "/" + y + ".png");
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int findY(int zoom, double lat) {
        
        double top = Math.log(Math.tan((lat * (PI/180)))  + (1 / ( Math.cos(lat * (PI/180))) ) );
        
        double d = (1 - (top/PI)) * ( Math.pow(2, (zoom-1))) ;
        
        return (int)d;
    }

    private static int findX(int zoom, double lon) {

        double d = ((lon + 180)/360) * Math.pow(2, zoom);
        
        return (int) d;
    }
}
