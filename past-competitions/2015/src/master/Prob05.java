

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob05 {
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
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
                // read the region's name and print it
                String regionName = br.readLine();
                System.out.println(regionName + ":");
                
                // read the number of data points
                int N = Integer.parseInt(br.readLine());
                
                // create arrays
                int[] years = new int[N];
                int[] incomes = new int[N];
                
                // loop through data
                for (int i=0; i<N; i++) {
                    // read each line
                    inLine = br.readLine();
                    
                    // split into tokens
                    String[] tokens = inLine.split(" ");
                    
                    // get the year first
                    int year = Integer.parseInt(tokens[1]);
                    
                    // get the income
                    String incomeString = tokens[0];
                    
                    // ignore cents
                    tokens = incomeString.split("\\.");
                    incomeString = tokens[0];
                    
                    // set array items
                    years[i] = year;
                    incomes[i] = roundToNearestThousand(incomeString);
                }
                
                // sort the data
                for (int j=0; j<N-1; j++) {
                    for (int k=j+1; k<N; k++) {
                        if (years[j] > years[k]) {
                            int temp = years[j];
                            years[j] = years[k];
                            years[k] = temp;
                            
                            temp = incomes[j];
                            incomes[j] = incomes[k];
                            incomes[k] = temp;
                        }
                    }
                }
                
                // print output
                for (int i=0; i<N; i++) {
                    System.out.print(years[i] + " ");
                    
                    for (int j=0; j<incomes[i]; j++) {
                        System.out.print("*");
                    }
                    
                    System.out.println();
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static int roundToNearestThousand(String incomeString) {
        int income = Integer.parseInt(incomeString);
        
        // divide by 1000
        int retVal = income / 1000;
        
        // get the remainder
        int remainder = income % 1000;
        
        // round up?
        if (remainder >= 500) {
            retVal ++;
        }
        
        return retVal;
    }
}
