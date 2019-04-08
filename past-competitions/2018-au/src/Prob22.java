import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob22 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob22.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // variables
            int[][] board = new int[3][3];
            int[] rowSums = new int[3];
            int[] colSums = new int[3];
            boolean winner;
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // reset winner
                winner = false;
                
                // reset sums
                Arrays.fill(rowSums, 0);
                Arrays.fill(colSums, 0);
                
                // read the next line of text
                inLine = br.readLine();
                
                // print the original line with the equal sign
                System.out.print(inLine + " = ");
                
                // fill the board: X=1, O=-1, -=0
                int index = 0;
                for (int row=0; row<3; row++) {
                    for (int col=0; col<3; col++) {
                        // get the next character in the string
                        char currentChar = inLine.charAt(index++);
                        
                        // put the move on the board
                        if (currentChar == 'X') board[row][col] = 1;
                        else if (currentChar == 'O') board[row][col] = -1;
                        else board[row][col] = 0;
                        
                        // add to rowSums and colSums
                        rowSums[row] += board[row][col];
                        colSums[col] += board[row][col];
                        
                        // if a sum gets to 3 or -3, there's a winner
                        if ((rowSums[row] == 3) || (colSums[col] == 3)) {
                            // X wins
                            System.out.println("X WINS");
                            winner = true;
                        } else if ((rowSums[row] == -3) || (colSums[col] == -3)) {
                            // O wins
                            System.out.println("O WINS");
                            winner = true;
                        }
                    }
                }
                
                // need to check diagonal sums too - both contain the center space
                int d1 = board[1][1];
                int d2 = board[1][1];
                
                d1 = d1 + board[0][0] + board[2][2];
                d2 = d2 + board[0][2] + board[2][0];
                
                if ((d1 == 3) || (d2 == 3)) {
                    // X wins
                    System.out.println("X WINS");
                    winner = true;
                } else if ((d1 == -3) || (d2 == -3)) {
                    // O wins
                    System.out.println("O WINS");
                    winner = true;
                }
                
                // check for no winner
                if (!winner) {
                    System.out.println("TIE");
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
