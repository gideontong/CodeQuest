

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

public class Prob13 {
    private static final String INPUT_FILE_NAME = "Prob13.in.txt";
    
    private static int[] nums;
    private static boolean[] used;
    private static int target;
    private static HashMap<String, String> usedStrings = new HashMap<String, String>();
    
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
                // read the line with the sum
                inLine = br.readLine();
                
                // get the target sum
                target = Integer.parseInt(inLine.split("=")[1]);
                
                // read the line with the numbers
                inLine = br.readLine();
                
                // get the numbers
                String[] tokens = inLine.split(",");
                
                // make an array of ints from this
                nums = new int[tokens.length];
                for (int i=0; i<tokens.length; i++) {
                    nums[i] = Integer.parseInt(tokens[i]);
                }
                
                // reset the used array
                used = new boolean[tokens.length];
                Arrays.fill(used, false);
                
                // sort the list
                Arrays.sort(nums);
                
                // find the combinations
                findCombos(0, "");
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // recursive method to find combinations of numbers that work
    private static void findCombos(int currentSum, String currentString) {
        if (currentSum == target) {
            // done - check if this string has been used
            if (usedStrings.get(currentString) == null) {
                // this key is available - flag it and print it
                usedStrings.put(currentString, currentString);
                System.out.println(currentString);
            } else {
                // key exists, ignore
            }
        } else {
            // not done - add another number
            for (int i=0; i<nums.length; i++) {
                if (!used[i]) {
                    // this number is still available
                    if ((currentSum + nums[i]) <= target) {
                        // this number is viable - try it!
                        used[i] = true;
                        
                        // add to the current string
                        StringBuffer buf = new StringBuffer(currentString);
                        if (currentString.length() > 0) buf.append("+");
                        buf.append(nums[i]);
                        
                        // recurse
                        findCombos(currentSum + nums[i], buf.toString());
                        
                        used[i] = false;
                    } else {
                        // the current number (and all those above it) are too big.  We're done here.
                        i = nums.length;
                    }
                }
            }
        }
    }
}
