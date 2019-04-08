

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob16 {
    private static final String INPUT_FILE_NAME = "Prob16.in.txt";
    
    // hold the x, y, and z coordinates of important places
    private static int[] x;
    private static int[] y;
    private static int[] z;
    
    // hold the light energy in each star
    private static int[] light;
    
    // store whether we've visited the point already or not
    private static boolean[] used;
    
    // distances from place to place
    private static int[][] distances;
    
    // best distance so far
    private static int best;
    
    // energy remaining
    private static int energyRemaining;
    
    // current distance traveled
    private static int currentDistance;
    
    // destination
    private static int destination;
    
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
                // read the dimensions of the board - really we just need this to tell us the ending coordinates
                int L = Integer.parseInt(br.readLine());
                
                // get the number of stars
                int N = Integer.parseInt(br.readLine());
                
                /**
                 * Create the arrays.  Total array length = N+2.
                 * Index 0: starting position
                 * Index 1-N: stars
                 * Index N+1: ending position
                 */
                x = new int[N+2];
                y = new int[N+2];
                z = new int[N+2];
                light = new int[N+2];
                used = new boolean[N+2];
                distances = new int[N+2][N+2];
                
                // start position
                x[0] = y[0] = z[0] = 0;
                light[0] = 0;
                
                // loop through the stars
                for (int i=1; i<=N; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // split the input
                    String[] tokens = inLine.split(",");
                    
                    // set the light
                    light[i] = decodeLight(tokens[0]);
                    
                    // set x, y, z
                    x[i] = Integer.parseInt(tokens[1]);
                    y[i] = Integer.parseInt(tokens[2]);
                    z[i] = Integer.parseInt(tokens[3]);
                }
                
                // end position
                x[N+1] = y[N+1] = z[N+1] = L-1;
                light[N+1] = 0;
                
                // haven't been anywhere yet
                Arrays.fill(used, false);
                
                // calculate distances
                calculateDistances(N);
                
                // reset best
                best = Integer.MAX_VALUE;
                
                // charge the battery
                energyRemaining = 20;
                
                // reset currentDistance
                currentDistance = 0;
                
                // place ourselves on the board
                used[0] = true;
                
                // set the destination
                destination = N+1;
                
                // go!
                solve(0);
                
                System.out.println(best);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void solve(int currentPosition) {
        // first check to see if this path can win
        if ((currentDistance + distances[currentPosition][destination]) < best) {
            // this path could work - first try to sprint to the end
            if (distances[currentPosition][destination] <= energyRemaining) {
                // we can make it!
                currentDistance += distances[currentPosition][destination];
                
                // check for new best
                if (currentDistance < best) {
                    best = currentDistance;
                }
                
                currentDistance -= distances[currentPosition][destination];
            } else {
                // need more energy - try all stars from here
                for (int i=1; i<=destination; i++) {
                    if (!used[i]) {
                        // this star has not been used, but can we make it?
                        if (distances[currentPosition][i] <= energyRemaining) {
                            // we can make it to this star - calculate new energy and distance
                            currentDistance += distances[currentPosition][i];
                            energyRemaining -= distances[currentPosition][i];
                            energyRemaining += light[i];
                            
                            // calculate excess energy
                            int excessEnergy = energyRemaining - 20;
                            
                            // take away excess energy if necessary
                            if (excessEnergy > 0) {
                                energyRemaining -= excessEnergy;
                            }
                            
                            // mark as used
                            used[i] = true;
                            
                            // take the next step
                            solve(i);
                            
                            // mark as unused
                            used[i] = false;
                            
                            // put back excess energy if necessary
                            if (excessEnergy > 0) {
                                energyRemaining += excessEnergy;
                            }
                            
                            // restore energy and distance
                            energyRemaining -= light[i];
                            energyRemaining += distances[currentPosition][i];
                            currentDistance -= distances[currentPosition][i];
                        }
                    }
                }
            }
        }
    }
    
    private static int decodeLight(String theLetter) {
        char letter = theLetter.charAt(0);
        switch (letter) {
            case 'M':
                return 3;
            case 'K':
                return 4;
            case 'G':
                return 5;
            case 'F':
                return 6;
            case 'A':
                return 7;
            case 'B':
                return 8;
            case 'O':
                return 9;
        }
        
        System.out.println("Error!");
        return -1;
    }
    
    private static void calculateDistances(int N) {
        for (int r=0; r<N+2; r++) {
            for (int c=0; c<N+2; c++) {
                distances[r][c] = calculateDistance(r, c);
            }
        }
    }
    
    private static int calculateDistance(int r, int c) {
        int retVal = 0;
        int temp = 0;
        
        // x
        temp = x[r] - x[c];
        if (temp < 0) temp *= -1;
        retVal += temp;
        
        // y
        temp = y[r] - y[c];
        if (temp < 0) temp *= -1;
        retVal += temp;
        
        // z
        temp = z[r] - z[c];
        if (temp < 0) temp *= -1;
        retVal += temp;
        
        return retVal;
    }
}
