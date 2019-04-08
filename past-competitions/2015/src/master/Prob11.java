

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

public class Prob11 {
    private static final String INPUT_FILE_NAME = "Prob11.in.txt";
    
    private static final String group1 = "bfpv";
    private static final String group2 = "cgjkqsxz";
    private static final String group3 = "dt";
    private static final String group4 = "l";
    private static final String group5 = "mn";
    private static final String group6 = "r";
    // wild
    private static final String group7 = "hw";
    // vowels
    private static final String group8 = "aeiouy";
    
    private static final int WILD = 7;
    
    private static final String[] groups = {group1, group2, group3, group4, group5, group6, group7, group8};
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // read the number of names
                int N = Integer.parseInt(br.readLine());
                
                // keep track of totals
                HashMap<String, Integer> soundexMap = new HashMap<String, Integer>();
                
                // loop through the names
                while (N-- > 0) {
                    // get the original name as a lowercase array of chars
                    char[] encoded = br.readLine().toLowerCase().toCharArray();
                    
                    // keep track of our place
                    int index = 0;
                    
                    // build the next iteration of the name
                    String newString = "";
                    
                    /**
                     * Step 1: Remove adjacent letters in the same group
                     */
                    
                    // find the first letter in group 1-6 (skip wilds and vowels)
                    while ((index < encoded.length) && (getGroupNum(encoded[index]) > 6)) {
                        newString += encoded[index];
                        index++;
                    }
                    
                    // loop through this process until there are no more letters
                    while (index < encoded.length) {
                        // retain the first letter in the group and remamber its group number
                        int currentGroupNum = getGroupNum(encoded[index]);
                        newString += encoded[index];
                        index++;
                        
                        // look for the start of the next group
                        while ((index < encoded.length) && ((getGroupNum(encoded[index]) == WILD) || (getGroupNum(encoded[index]) == currentGroupNum))) {
                            index++;
                        }
                    }
                    
                    // get the next iteration
                    encoded = newString.toCharArray();
                    index = 0;
                    newString = "";
                    
                    /**
                     * Step 2: Remove vowels and wilds after the first position
                     */
                    
                    // retain the first letter
                    newString += encoded[index];
                    index++;
                    
                    // skip all other vowels and wilds
                    while (index < encoded.length) {
                        if (getGroupNum(encoded[index]) < WILD) {
                            newString += encoded[index];
                        }
                        index++;
                    }
                    
                    // get the next iteration
                    encoded = newString.toCharArray();
                    index = 0;
                    newString = "";
                    
                    /**
                     * Step 3: Replace letters with their group number
                     */
                    
                    // retain the first letter
                    newString += encoded[index];
                    index++;
                    
                    // convert all other letters
                    while (index < encoded.length) {
                        newString += getGroupNum(encoded[index]);
                        index++;
                    }
                    
                    /**
                     * Step 4: Pad and save
                     */
                    
                    // pad just in case
                    newString += "000";
                    
                    // convert to uppercase - only the first character is a letter
                    newString = newString.toUpperCase();
                    
                    // use the first 4 characters as the code
                    String key = newString.substring(0, 4);
                    
                    // check to see if this is a new code
                    if (soundexMap.get(key) == null) {
                        // create an entry with 0 count
                        soundexMap.put(key, 0);
                    }
                    
                    // increase the count for this key
                    soundexMap.put(key, soundexMap.get(key) + 1);
                }
                
                // sort the keys and print the results
                String[] results = new String[soundexMap.size()];
                int index = 0;
                
                // create result strings - keys are unique so we can do this before sorting
                for (String key : soundexMap.keySet()) {
                    results[index] = key;
                    results[index] += " ";
                    results[index] += soundexMap.get(key);
                    index++;
                }
                
                // sort
                Arrays.sort(results);
                
                // print
                System.out.println("OUTPUT");
                for (int i=0; i<results.length; i++) {
                    System.out.println(results[i]);
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static int getGroupNum(char theChar) {
        for (int i=0; i<groups.length; i++) {
            String group = groups[i];
            if (group.indexOf(theChar) > -1) {
                return i+1;
            }
        }
        
        // should never get here
        return 0;
    }
}
