

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob09 {
    private static final String INPUT_FILE_NAME = "Prob09.in.txt";
    
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
                // read the number of lines
                int N = Integer.parseInt(br.readLine());
                
                // max line length (for padding)
                int maxLineLength = 0;
                
                // temporary line storage
                String[] lines = new String[N];
                
                // loop through lines
                for (int i=0; i<N; i++) {
                    inLine = br.readLine();
                    
                    // is this the longest line?
                    if (inLine.length() > maxLineLength) {
                        maxLineLength = inLine.length();
                    }
                    
                    // store the line
                    lines[i] = inLine;
                }
                
                // build a 2D array from the lines
                char[][] pic = new char[N][maxLineLength];
                
                // r = y and c = x
                for (int r=0; r<N; r++) {
                    String currentLine = lines[r];
                    for (int c=0; c<maxLineLength; c++) {
                        if (c < currentLine.length()) {
                            // there are still characters on this line
                            pic[r][c] = currentLine.charAt(c);
                        } else {
                            // pad with spaces at the end of the line
                            pic[r][c] = ' ';
                        }
                    }
                }
                
                // read what to do with the lines
                String reflectionType = br.readLine();
                
                if (reflectionType.equals("X")) {
                    // reflect up-down: just print the lines in reverse order
                    for (int r=N-1; r>=0; r--) {
                        System.out.println(lines[r]);
                    }
                } else if (reflectionType.equals("Y")) {
                    // reflect left-right: print lines in order, but reverse each one
                    for (int r=0; r<N; r++) {
                        for (int c=maxLineLength-1; c>=0; c--) {
                            System.out.print(pic[r][c]);
                        }
                        System.out.println();
                    }
                } else if (reflectionType.equals("INVERSE")) {
                    // switch x and y: write the columns as rows
                    for (int r=0; r<maxLineLength; r++) {
                        for (int c=0; c<N; c++) {
                            System.out.print(pic[c][r]);
                        }
                        System.out.println();
                    }
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
