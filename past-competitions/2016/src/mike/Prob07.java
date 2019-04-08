

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
            while (T-- > 0) {
                // get the number of lines in the text
                int M = Integer.parseInt(br.readLine());
                
                String[] lines = new String[M];
                
                // loop through the lines
                for (int i=0; i<M; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // save it
                    lines[i] = inLine;
                }
                
                // read the start position
                inLine = br.readLine();
                
                // get the row and column for the offset
                String[] tokens = inLine.split(",");
                int row = Integer.parseInt(tokens[0]);
                int col = Integer.parseInt(tokens[1]);
                
                // get the number of lines in the key
                int N = Integer.parseInt(br.readLine());
                
                // read the key
                while (N-- > 0) {
                    String currentLine = lines[row++];
                    
                    // read the next key line
                    inLine = br.readLine();
                    
                    for (int i=0; i<inLine.length(); i++) {
                        if (inLine.charAt(i) == 'O') {
                            System.out.print(currentLine.charAt(col + i));
                        }
                    }
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
}
