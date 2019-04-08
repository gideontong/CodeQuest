

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob02 {
    private static final String INPUT_FILE_NAME = "Prob02.in.txt";
    
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
                
                boolean printed = false;
                StringBuffer buf = new StringBuffer();
                
                int currentInt = Integer.parseInt(inLine);
                
                if ((currentInt % 3) == 0) {
                    buf.append("LOCKHEED");
                    printed = true;
                }
                
                if ((currentInt % 7) == 0) {
                    buf.append("MARTIN");
                    printed = true;
                }
                
                if (!printed) {
                    buf.append(inLine);
                }
                
                buf.append("\n");
                System.out.print(buf.toString());
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
