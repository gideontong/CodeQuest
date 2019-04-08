

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob06 {
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
    // start top left and move clockwise
    private static final int[] rowMods = {-1, -1, -1, 0, 1, 1, 1, 0};
    private static final int[] colMods = {-1, 0, 1, 1, 1, 0, -1, -1};
    
    // game board and word
    private static char[][] board = null;
    private static String theWord = null;
    
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
                // read rows and cols
                inLine = br.readLine();
                
                // split
                String[] tokens = inLine.split(" ");
                int numRows = Integer.parseInt(tokens[0]);
                int numCols = Integer.parseInt(tokens[1]);
                
                // create game board array - pad with dashes surrounding so we don't have to check borders
                board = new char[numRows+2][numCols+2];
                
                // fill the top and bottom row with dashes
                Arrays.fill(board[0], '-');
                Arrays.fill(board[numRows+1], '-');
                
                // read in the board
                for (int r=0; r<numRows; r++) {
                    inLine = br.readLine();
                    tokens = inLine.split(" ");
                    
                    // buffer
                    board[r+1][0] = '-';
                    board[r+1][numCols+1] = '-';
                    
                    for (int c=0; c<numCols; c++) {
                        board[r+1][c+1] = tokens[c].charAt(0);
                    }
                }
                
                // read the number of words
                int N = Integer.parseInt(br.readLine());
                
                // loop through words
                while (N-- > 0) {
                    // read the word
                    theWord = br.readLine();
                    
                    // start with each letter on the board
                    searchloop:
                    for (int r=1; r<=numRows; r++) {
                        for (int c=0; c<=numCols; c++) {
                            // try to find the word
                            if (findWord(r, c, 0)) {
                                System.out.println(theWord);
                                break searchloop;
                            }
                        }
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
    
    private static boolean findWord(int row, int col, int index) {
        if (board[row][col] == '-') {
            // out of bounds
            return false;
        }
        
        if (theWord.charAt(index) == board[row][col]) {
            // this letter matches, are we done?
            if (index+1 == theWord.length()) {
                // the word is complete
                return true;
            }
            
            // not done, look around and try the next letter
            for (int i=0; i<rowMods.length; i++) {
                if (findWord(row+rowMods[i], col+colMods[i], index+1)) {
                    // return early if we find the word
                    return true;
                }
            }
            
            // wr've looked all around and found no match
            return false;
        } else {
            // no match at this position
            return false;
        }
    }
}
