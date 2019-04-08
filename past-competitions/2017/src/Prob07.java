

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob07 {
    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
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
                
                // split on the colon
                String[] tokens = inLine.split(":");
                
                // get the name
                String name = tokens[0];
                
                // split the second part on the comma
                tokens = tokens[1].split(",");
                
                // store the at-bat info
                int numAtBats = 0;
                int numSingles = 0;
                int numDoubles = 0;
                int numTriples = 0;
                int numHomeRuns = 0;
                
                // loop through the at-bats
                for (String result : tokens) {
                    // first check for a walk
                    if (result.equals("BB")) {
                        // ignore this - it doesn't count as an at-bat
                    } else {
                        // not a walk, so it counts as an at-bat
                        numAtBats++;
                        
                        // what happened?
                        if (result.equals("1B")) {
                            numSingles++;
                        } else if (result.equals("2B")) {
                            numDoubles++;
                        } else if (result.equals("3B")) {
                            numTriples++;
                        } else if (result.equals("HR")) {
                            numHomeRuns++;
                        }
                    }
                }
                
                System.out.print(name);
                System.out.print("=");
                
                // now compute slugging percentage and print it
                if (numAtBats == 0) {
                    // no at bats - slugging percentage is 0
                    System.out.println(round(0, 1, 3));
                } else {
                    // at least one at bat
                    int numerator = numSingles + (2 * numDoubles) + (3 * numTriples) + (4 * numHomeRuns);
                    int denominator = numAtBats;
                    System.out.println(round(numerator, denominator, 3));
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Roll my own rounding so I can be sure it's correct
    public static String round(int numerator, int denominator, int numDecimalPlaces) {
        StringBuffer buf = new StringBuffer();
        
        // always have to multiply by 10 to see the next digit after the decimal
        int multiplyBy = 1;
        
        // multiply by 10 for each decimal place to print
        for (int i=0; i<numDecimalPlaces; i++) {
            multiplyBy *= 10;
        }
        
        // calculate the answer times ten
        int answerTimesTen = (numerator * multiplyBy * 10) / denominator;
        
        // get the real answer and the first decimal
        int firstDecimalPoint = answerTimesTen % 10;
        int answer = answerTimesTen / 10;
        
        // round if necessary
        if (firstDecimalPoint >= 5) {
            answer++;
        }
        
        buf.append((answer/multiplyBy));
        buf.append(".");
        
        String remainder = "" + (answer%multiplyBy);
        buf.append(remainder);
        
        // print extra zeroes if necessary
        for (int i=0; i<(numDecimalPlaces-remainder.length()); i++) {
            buf.append("0");
        }
        
        return buf.toString();
    }
}
