/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Bingo {
    private static final String INPUT_FILE_NAME = "Prob16.in.txt";
    
    static ArrayList<BingoCard> cards = new ArrayList<BingoCard>();
    
    public static void main(String[] args) {
        readInput();
        
        boolean firstWinnerPrinted = false;
        
        for (int i=0; i<cards.size(); i++) {
            if (cards.get(i).isWinner()) {
                if (firstWinnerPrinted) {
                    System.out.print(" ");
                }
                System.out.print(i+1);
                firstWinnerPrinted = true;
            }
        }
        if (firstWinnerPrinted) {
            System.out.println();
        }
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            int linesRead = 0;
            boolean gameStarted = false;
            BingoCard currentCard = null;
            ArrayList<String> rowData = new ArrayList<String>();
            boolean bingoFound = false;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                if (!gameStarted) {
                    // not playing yet - check for the end of the card section
                    if (inLine.startsWith("PLAY")) {
                        gameStarted = true;
                    } else {
                        // reading card data
                        if (linesRead == 0) {
                            // start a new card
                            currentCard = new BingoCard();
                            cards.add(currentCard);
                        }
                        
                        // empty the row data
                        rowData.clear();
                        
                        // split the data on spaces and add it to the row
                        String[] tokens = inLine.split(" ");
                        for (int i=0; i<tokens.length; i++) {
                            rowData.add(tokens[i]);
                        }
                        
                        // adjust for the third row
                        if (linesRead == 2) {
                            ArrayList<String> temp = new ArrayList<String>();
                            temp.add(rowData.get(0));
                            temp.add(rowData.get(1));
                            temp.add("FREE");
                            temp.add(rowData.get(2));
                            temp.add(rowData.get(3));
                            rowData = temp;
                        }
                        
                        // add the row to the card
                        currentCard.addRow(rowData);
                        
                        // increment lines read and check for end of card
                        linesRead++;
                        if (linesRead == 5) {
                            linesRead = 0;
                        }
                    }
                } else {
                    // playing the game
                    if (!bingoFound) {
                        for (int i=0; i<cards.size(); i++) {
                            cards.get(i).coverSpace(inLine);
                            if (cards.get(i).isWinner()) {
                                bingoFound = true;
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
    
    private static class BingoCard {
        private int index = 0;
        private boolean[] covered = new boolean[25];
        private String[] spaces = new String[25];
        private String[] prefixArray = {"B", "I", "N", "G", "O"};
        
        public void addRow(ArrayList<String> row) {
            for (int column=0; column<prefixArray.length; column++) {
                if (index == 12) {
                    // free space
                    covered[index] = true;
                    spaces[index] = "FREE";
                } else {
                    // normal space
                    covered[index] = false;
                    spaces[index] = prefixArray[column] + row.get(index%5);
                }
                
                index++;
            }
        }
        
        public void coverSpace(String number) {
            for (int i=0; i<covered.length; i++) {
                if (spaces[i].equals(number)) {
                    covered[i] = true;
                }
            }
        }
        
        public boolean isWinner() {
            boolean retVal = false;
            
            // check rows
            for (int row=0; row<5; row++) {
                int index = row*5;
                if (covered[index] && covered[index+1] && covered[index+2] && covered[index+3] && covered[index+4]) {
                    retVal = true;
                }
            }
            
            // check cols
            for (int col=0; col<5; col++) {
                index = col;
                if (covered[index] && covered[index+5] && covered[index+10] && covered[index+15] && covered[index+20]) {
                    retVal = true;
                }
            }
            
            // check diagonals
            if (covered[0] && covered[6] && covered[12] && covered[18] && covered[24]) {
                retVal = true;
            }
            if (covered[4] && covered[8] && covered[12] && covered[16] && covered[20]) {
                retVal = true;
            }
            
            return retVal;
        }
    }
}
