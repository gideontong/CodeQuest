

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob06 {
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
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
                
                // split by the space
                String[] tokens = inLine.split(" ");
                
                // get subscribers
                int times = Integer.parseInt(tokens[0]);
                int herald = Integer.parseInt(tokens[1]);
                
                // print the message
                if (times == herald) {
                    System.out.println("Times and Herald have the same number of subscribers");
                } else if (times > herald) {
                    int difference = times - herald;
                    System.out.println("Times has " + difference + " more subscribers");
                } else {
                    int difference = herald - times;
                    System.out.println("Herald has " + difference + " more subscribers");
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
