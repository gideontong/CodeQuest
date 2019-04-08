/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;


public class ThreeMs {
    private static final String INPUT_FILE_NAME = "Prob09.in.txt";
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            DecimalFormat df = new DecimalFormat("#.#");
            int setNum = 1;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                Hashtable<String, Integer> frequency = new Hashtable<String, Integer>();
                ArrayList<String> modeList = new ArrayList<String>();
                int modeCount = 0;
                int sum = 0;
                String[] tokens = inLine.split(",");
                int[] nums = new int[tokens.length];
                for (int i=0; i<tokens.length; i++) {
                    nums[i] = Integer.valueOf(tokens[i]).intValue();
                    sum += nums[i];
                    if (!frequency.containsKey(tokens[i])) {
                        frequency.put(tokens[i], new Integer(0));
                    }
                    Integer count = frequency.get(tokens[i]);
                    frequency.put(tokens[i], new Integer(count.intValue() + 1));
                }
                Arrays.sort(nums);
                
                // get mean
                double mean = (double)sum / (double)nums.length;
                
                // get median
                double median;
                if ((nums.length%2) == 1) {
                    median = (double)nums[nums.length/2];
                } else {
                    median = ((double)nums[nums.length/2] + (double)nums[(nums.length/2)-1]) / 2.0;
                }
                
                // get mode
                String previousNum = "";
                for (int i=0; i<nums.length; i++) {
                    String theNum = "" + nums[i];
                    if (!theNum.equals(previousNum)) {
                        previousNum = theNum;
                        if (frequency.get(theNum).intValue() > modeCount) {
                            // new mode
                            modeCount = frequency.get(theNum).intValue();
                            modeList.clear();
                            modeList.add(theNum);
                        } else if (frequency.get(theNum).intValue() == modeCount) {
                            modeList.add(theNum);
                        }
                    }
                }
                System.out.print("Set " + setNum + ": ");
                System.out.print("Mean=" + df.format(mean) + ", ");
                System.out.print("Median=" + df.format(median) + ", ");
                System.out.print("Mode=");
                boolean firstPrinted = false;
                for (int i=0; i<modeList.size(); i++) {
                    if (firstPrinted) {
                        System.out.print(",");
                    }
                    System.out.print(modeList.get(i));
                    firstPrinted = true;
                }
                System.out.println();
                setNum++;
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
