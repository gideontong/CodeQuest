

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob03 {
    private static final String INPUT_FILE_NAME = "Prob03.in.txt";
    
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
                // read the number of years in the group
                int N = Integer.parseInt(br.readLine());
                
                // loop through the years in this group
                while (N-- > 0) {
                    // read in the year
                    int year = Integer.parseInt(br.readLine());
                    
                    // check for leap year
                    System.out.println(isLeapYear(year) ? "Yes" : "No");
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static boolean isLeapYear(int year) {
        // if the year was prior to 1582, there were no leap years
        if (year < 1582) {
            return false;
        }
        
        // if the year is not divisible by 4 then it is a common year
        if ((year % 4) != 0) {
            return false;
        }
        
        // else if the year is not divisible by 100 then it is a leap year
        if ((year % 100) != 0) {
            return true;
        }
        
        // else if the year is not divisible by 400 then it is a common year
        if ((year % 400) != 0) {
            return false;
        }
        
        // else the year is a leap year
        return true;
    }
}
