

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Prob14 {
    private static final String INPUT_FILE_NAME = "Prob14.in.txt";
    
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
                // keep track of the max length and the names
                int maxLength = 0;
                ArrayList<String> names = new ArrayList<String>();
                
                // read in the sample
                byte[] sample = br.readLine().getBytes();
                
                // read in the number of names
                int N = Integer.parseInt(br.readLine());
                
                // loop through the names
                while (N-- > 0) {
                    // read the line
                    inLine = br.readLine();
                    
                    // split it up
                    String[] tokens = inLine.split("=");
                    
                    // get the dna record
                    byte[] record = tokens[1].getBytes();
                    
                    // find the longest subsequence
                    int longestSubsequenceLength = findLongestSubsequenceLength(sample, record, sample.length, record.length);
                    
                    // is it the longest?
                    if (longestSubsequenceLength > maxLength) {
                        // new max length
                        maxLength = longestSubsequenceLength;
                        names.clear();
                        names.add(tokens[0]);
                    } else if (longestSubsequenceLength == maxLength) {
                        // tie for max
                        names.add(tokens[0]);
                    }
                }
                
                // sort the names
                String[] sortedNames = new String[names.size()];
                sortedNames = names.toArray(sortedNames);
                Arrays.sort(sortedNames);
                
                // print the names
                boolean firstPrinted = false;
                for (String name : sortedNames) {
                    if (firstPrinted) System.out.print(",");
                    System.out.print(name);
                    firstPrinted = true;
                }
                System.out.println();
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * find the longest subsequence by working backwards and memorizing subproblem solutions
     * 
     * string1 is a string of length len1, so the array is: string1[0..len1-1]
     * string2 is a string of length len2, so the array is: string2[0..len2-1]
     */
    private static int findLongestSubsequenceLength(byte[] string1, byte[] string2, int len1, int len2) {
        // lengths will be our subproblem memorization array
        // need to add one because we're adding a row of zeroes at the top and a colummn of zeroes on the left
        int[][] lengths = new int[len1+1][len2+1];
        
        // loop through string1's characters
        for (int i=0; i<=len1; i++) {
            // loop through string2's characters
            for (int j=0; j<=len2; j++) {
                if ((i == 0) || (j == 0)) {
                    // base condition - no matches yet
                    lengths[i][j] = 0;
                } else if (string1[i-1] == string2[j-1]) {
                    // matched a character - increase the length of the subsequence from one character back on each string
                    lengths[i][j] = lengths[i-1][j-1] + 1;
                } else {
                    // no match - pick the longest from one caracter back on either string to carry forward
                    lengths[i][j] = Math.max(lengths[i-1][j], lengths[i][j-1]);
                }
            }
        }
        
        // should be done now
        return lengths[len1][len2];
    }
}
