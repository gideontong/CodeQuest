

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob17 {
    private static final String INPUT_FILE_NAME = "Prob17.in.txt";
    
    // X is player 0, O is player 1
    private static final char X = 'X';
    private static final char O = 'O';
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            int index = 0;
            char[] originalBoard = new char[9];
            int[] board = new int[9];
            
            // loop through test cases
            for (int t=0; t<T; t++) {
                int numMoves = 0;
                
                // read in the board
                for (int row=0; row<3; row++) {
                    // read the line
                    inLine = br.readLine();
                    
                    // fill the board
                    for (int col=0; col<3; col++) {
                        index = (row * 3) + col;
                        originalBoard[index] = inLine.charAt(col);
                        
                        if (originalBoard[index] == X) {
                            board[index] = 0;
                            numMoves++;
                        } else if (originalBoard[index] == O) {
                            board[index] = 1;
                            numMoves++;
                        } else {
                            board[index] = -1;
                        }
                    }
                }
                
                // create our base node
                Node base = new Node(board);
                
                // solve it!
                base.evaluate();
                
                int nextMoveIndex = base.getNextMoveIndex();
                
                // find out who is next
                char nextMark = ((numMoves % 2) == 0) ? X : O;
                
                // make the move
                originalBoard[nextMoveIndex] = nextMark;
                
                // print the board
                for (int row=0; row<3; row++) {
                    for (int col=0; col<3; col++) {
                        index = (row * 3) + col;
                        System.out.print(originalBoard[index]);
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
    
    public static class Node {
        private int[] board;
        private int numMoves;
        private int nextMove;
        private int bestScore;
        private int nextMoveIndex;
        private int winningScore;
        
        public Node(int[] originalBoard) {
            board = new int[9];
            numMoves = 0;
            
            for (int i=0; i<9; i++) {
                // copy original board
                board[i] = originalBoard[i];
                
                // count the moves made already
                if (board[i] > -1) {
                    numMoves++;
                }
            }
            
            // calculate whose turn is next
            nextMove = numMoves % 2;
            
            if (nextMove == 0) {
                // X is next - need the minimum score
                bestScore = Integer.MAX_VALUE;
                winningScore = -1;
            } else {
                // O is next - need the maximum score
                bestScore = Integer.MIN_VALUE;
                winningScore = 1;
            }
            
            nextMoveIndex = -1;
        }
        
        // returns the best score - call getNextMoveIndex to know where to go
        public int evaluate() {
            // check to see if the game is over - first check for a winner
            int winner = getWinner(board);
            
            if (winner > -1) {
                // winner winner chicken dinner
                if (winner == 0) {
                    // X won, that's bad for the AI
                    return -1;
                } else {
                    // O won, that's good for the AI
                    return 1;
                }
            } else if (numMoves == 9) {
                // no more moves and no winner - draw!
                return 0;
            } else {
                // more to play - can we win with the next move?
                for (int i=0; i<9; i++) {
                    if (board[i] == -1) {
                        // this space is available - make the move
                        board[i] = nextMove;
                        
                        // check for winner
                        winner = getWinner(board);
                        if (winner > -1) {
                            // we can win!
                            nextMoveIndex = i;
                            
                            // undo the move
                            board[i] = -1;
                            
                            // done
                            break;
                        }
                        
                        // undo the move
                        board[i] = -1;
                    }
                }
                
                if (winner > -1) {
                    // winner winner chicken dinner
                    if (winner == 0) {
                        // X won, that's bad for the AI
                        return -1;
                    } else {
                        // O won, that's good for the AI
                        return 1;
                    }
                } else {
                    // more to play - loop through possible moves
                    for (int i=0; i<9; i++) {
                        if (board[i] == -1) {
                            // this space is available - make the move
                            board[i] = nextMove;
                            
                            // create a new board state
                            Node child = new Node(board);
                            
                            // play that board out
                            int childScore = child.evaluate();
                            
                            // check to see if this score is better than we have
                            if (nextMove == 0) {
                                // X is next - need the minimum score
                                if (childScore < bestScore) {
                                    bestScore = childScore;
                                    nextMoveIndex = i;
                                }
                            } else {
                                // O is next - need the maximum score
                                if (childScore > bestScore) {
                                    bestScore = childScore;
                                    nextMoveIndex = i;
                                }
                            }
                            
                            // undo the move
                            board[i] = -1;
                            
                            if (bestScore == winningScore) {
                                // short circuit - we've found the best we can do
                                break;
                            }
                        }
                    }
                }
            }
            
            return bestScore;
        }
        
        public int getNextMoveIndex() {
            return nextMoveIndex;
        }
    }
    
    private static int getWinner(int[] board) {
        // assume a tie
        int retVal = -1;
        
        if (checkForWin(board, 1, 2, 3, 0)) retVal = 0;
        else if (checkForWin(board, 4, 5, 6, 0)) retVal = 0;
        else if (checkForWin(board, 7, 8, 9, 0)) retVal = 0;
        else if (checkForWin(board, 1, 4, 7, 0)) retVal = 0;
        else if (checkForWin(board, 2, 5, 8, 0)) retVal = 0;
        else if (checkForWin(board, 3, 6, 9, 0)) retVal = 0;
        else if (checkForWin(board, 1, 5, 9, 0)) retVal = 0;
        else if (checkForWin(board, 3, 5, 7, 0)) retVal = 0;
        else if (checkForWin(board, 1, 2, 3, 1)) retVal = 1;
        else if (checkForWin(board, 4, 5, 6, 1)) retVal = 1;
        else if (checkForWin(board, 7, 8, 9, 1)) retVal = 1;
        else if (checkForWin(board, 1, 4, 7, 1)) retVal = 1;
        else if (checkForWin(board, 2, 5, 8, 1)) retVal = 1;
        else if (checkForWin(board, 3, 6, 9, 1)) retVal = 1;
        else if (checkForWin(board, 1, 5, 9, 1)) retVal = 1;
        else if (checkForWin(board, 3, 5, 7, 1)) retVal = 1;
        
        return retVal;
    }
    
    private static boolean checkForWin(int[] board, int idx1, int idx2, int idx3, int value) {
        boolean retVal = false;
        
        if (board[idx1-1] == value) {
            if (board[idx2-1] == value) {
                if (board[idx3-1] == value) {
                    retVal = true;
                }
            }
        }
        
        return retVal;
    }
}
