

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob18 {
    private static final String INPUT_FILE_NAME = "Prob18.in.txt";
    
    private static int[] original;
    private static int targetNumerator;
    private static int targetDenominator;
    private static int bestAnswer;
    private static boolean finished;
    private static int numTanks;
    private static boolean[] open;
    private static int tanksOpened;
    private static int lastTankOpened;
    private static int numBuckets;
    private static int[] water;
    private static int[] count;
    private static int tanksToOpen;
    
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
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                // split on the spaces
                String[] tokens = inLine.split(" ");
                
                // write out the name
                for (String word : tokens) {
                    System.out.print(word.substring(0, 1).toUpperCase());
                }
                System.out.print(": ");
                
                // read the next line
                inLine = br.readLine();
                
                // split on the spaces
                tokens = inLine.split(" ");
                
                // get the number of tanks
                numTanks = Integer.parseInt(tokens[0]);
                int totalWater = 0;
                
                // create array
                original = new int[numTanks];
                open = new boolean[numTanks];
                
                // record original values
                for (int i=0; i<numTanks; i++) {
                    original[i] = Integer.parseInt(tokens[i+1]);
                    totalWater += original[i];
                    open[i] = false;
                }
                
                // compute the correct answer and remainder
                targetNumerator = totalWater;
                targetDenominator = numTanks;
                
                // opening all but 1 is the baseline
                bestAnswer = numTanks - 1;
                finished = false;
                
                // get ready
                tanksOpened = 0;
                lastTankOpened = -1;
                
                // solve
                for (tanksToOpen=0; tanksToOpen<numTanks; tanksToOpen++) {
                    // compute number of buckets
                    numBuckets = numTanks - tanksToOpen;
                    
                    // arrays to hold water values and tanks per bucket
                    water = new int[numBuckets];
                    count = new int[numBuckets];
                    
                    // go!
                    solve();
                    
                    // stop if we found an answer
                    if (finished) break;
                }
                
                System.out.println(bestAnswer);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void solve() {
        if (tanksOpened < tanksToOpen) {
            // find all combinations of tanks to open
            for (int i=lastTankOpened+1; i<numTanks; i++) {
                if (!open[i]) {
                    // remember the last tank opened
                    int temp = lastTankOpened;
                    
                    // open this valve
                    open[i] = true;
                    tanksOpened++;
                    lastTankOpened = i;
                    
                    solve();
                    
                    // close it back
                    lastTankOpened = temp;
                    open[i] = false;
                    tanksOpened--;
                }
            }
        } else {
            // everything is open - reset
            Arrays.fill(water, 0);
            Arrays.fill(count, 0);
            int bucketIndex = 0;
            boolean done = false;
            
            // add water to buckets - remember tank 0 is always with bucket 0
            for (int i=0; i<numTanks; i++) {
                // add water and tank to the current bucket
                water[bucketIndex] += original[i];
                count[bucketIndex]++;
                
                if (!open[i]) {
                    // the valve for this tank is closed - bucket 0 might have wraparound
                    if (bucketIndex > 0) {
                        // this bucket is done - compute its values and quit if they don't match the answer we need
                        if ((targetNumerator * count[bucketIndex]) != (water[bucketIndex] * targetDenominator)) {
                            // no match
                            done = true;
                            break;
                        }
                    }
                    
                    // move to the next bucket with wraparound
                    bucketIndex++;
                    bucketIndex %= numBuckets;
                }
            }
            
            // are we done here?
            if (!done) {
                // all buckets but 0 have been checked
                bucketIndex = 0;
                if ((targetNumerator * count[bucketIndex]) == (water[bucketIndex] * targetDenominator)) {
                    // we are done!
                    bestAnswer = tanksToOpen;
                    finished = true;
                }
            }
        }
    }
}
