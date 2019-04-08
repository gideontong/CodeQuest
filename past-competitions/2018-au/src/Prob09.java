import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob09 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob09.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            int[] rps = new int[3];
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                Arrays.fill(rps, 0);
                
                // read the next line of text
                inLine = br.readLine();
                
                // split on spaces
                String[] tokens = inLine.split(" ");
                
                for (String token : tokens) {
                    if (token.equals("R")) {
                        rps[0]++;
                    } else if (token.equals("P")) {
                        rps[1]++;
                    } else {
                        rps[2]++;
                    }
                }
                
                // find a winner
                boolean winner = false;
                
                if (rps[0] == 1) {
                    // one rock - we need scissors and no paper
                    if ((rps[1] == 0) && (rps[2] > 0)) {
                        System.out.println("ROCK");
                        winner = true;
                    }
                }
                
                if (rps[1] == 1) {
                    // one paper - we need rock and no scissors
                    if ((rps[2] == 0) && (rps[0] > 0)) {
                        System.out.println("PAPER");
                        winner = true;
                    }
                }
                
                if (rps[2] == 1) {
                    // one scissors - we need paper and no rock
                    if ((rps[0] == 0) && (rps[1] > 0)) {
                        System.out.println("SCISSORS");
                        winner = true;
                    }
                }
                
                if (!winner) {
                    System.out.println("NO WINNER");
                }
                
                // END OF TEST CASE CODE
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
