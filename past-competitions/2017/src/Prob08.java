

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob08 {
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
    private static final long HOURS_PER_DAY = 24;
    private static final long MINUTES_PER_HOUR = 60;
    private static final long SECONDS_PER_MINUTE = 60;
    private static final long SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    private static final long SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
    
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
                
                // split on the space
                String[] tokens = inLine.split(" ");
                String distanceString = tokens[0];
                String speedString = tokens[1];
                
                // numbers for rounding
                long numerator = 1;
                long denominator = 1;
                
                // get the distance
                tokens = distanceString.split("\\.");
                numerator *= Integer.parseInt(tokens[0]);
                if (tokens.length > 1) {
                    // there is a decimal part
                    String decimal = tokens[1];
                    for (int i=0; i<decimal.length(); i++) {
                        denominator *= 10;
                        numerator *= 10;
                        numerator += Integer.parseInt(""+decimal.charAt(i));
                    }
                }
                
                // get the speed
                tokens = speedString.split("\\.");
                int tempInt = Integer.parseInt(tokens[0]);
                if (tokens.length > 1) {
                    // there is a decimal part
                    String decimal = tokens[1];
                    for (int i=0; i<decimal.length(); i++) {
                        numerator *= 10;
                        tempInt *= 10;
                        tempInt += Integer.parseInt(""+decimal.charAt(i));
                    }
                }
                denominator *= tempInt;
                
                // distance is measured in millions of miles
                numerator *= 1000000;
                
                // speed is miles per hour, so convert to seconds
                numerator *= MINUTES_PER_HOUR;
                numerator *= SECONDS_PER_MINUTE;
                
                // find time it takes to the nearest second
                long seconds = round(numerator, denominator);
                
                // get days
                long days = seconds / SECONDS_PER_DAY;
                seconds %= SECONDS_PER_DAY;
                
                // get hours
                long hours = seconds / SECONDS_PER_HOUR;
                seconds %= SECONDS_PER_HOUR;
                
                // get minutes
                long minutes = seconds / SECONDS_PER_MINUTE;
                seconds %= SECONDS_PER_MINUTE;
                
                // print
                System.out.println("Time to Mars: " + days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static long round(long numerator, long denominator) {
        // calculate the answer times ten
        long answerTimesTen = (numerator * 10) / denominator;
        
        // get the real answer and the first decimal
        long firstDecimalPoint = answerTimesTen % 10;
        long answer = answerTimesTen / 10;
        
        // round if necessary
        if (firstDecimalPoint >= 5) {
            answer++;
        }
        
        return answer;
    }
}
