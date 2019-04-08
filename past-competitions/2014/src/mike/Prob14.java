/**
 * CodeQuest 2014
 * Problem 14: 3D Bingo
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob14 {
    private static final String INPUT_FILE_NAME = "Prob14.in.txt";
    
    static ArrayList<BingoCard> cards = new ArrayList<BingoCard>();
    static int numberOfBingos = 0; 
    
    public static void main(String[] args) {
        // read the input file and process it
        readInput();
        
        // print the number of bingos found
        System.out.println("Number of bingos: " + numberOfBingos);
    }

    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            boolean gameStarted = false;
            int linesRead = 0;
            BingoCard currentCard = null;
            ArrayList<String> rowData = new ArrayList<String>();
            boolean bingoFound = false;
            BingoCube cube = new BingoCube();
            
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
//                        System.out.println(inLine);
                        // haven't found a bingo yet - loop through the cards
                        for (int i=0; i<cards.size(); i++) {
                            // cover the space on every card it's on
                            cards.get(i).coverSpace(inLine);
                            
                            // check each card for a bingo by itself since we're already here
                            int temp = 0;
                            if ((temp = cards.get(i).isWinner()) > 0) {
                                bingoFound = true;
                                numberOfBingos += temp;
                            }
                        }
//                        if (bingoFound) System.out.println("card bingos: " + numberOfBingos);
                        
                        // now do a massive loop to build and check each possible cube for a bingo
                        for (int a=0; a<cards.size(); a++) {
                            for (int b=0; b<cards.size(); b++) {
                                if (b!=a) {
                                    for (int c=0; c<cards.size(); c++) {
                                        if ((c!=a) && (c!=b)) {
                                            for (int d=0; d<cards.size(); d++) {
                                                if ((d!=a) && (d!=b) && (d!=c)) {
                                                    for (int e=0; e<cards.size(); e++) {
                                                        if ((e!=a) && (e!=b) && (e!=c) && (e!=d)) {
                                                            // reset the cube
                                                            cube.reset();
                                                            int temp = 0;
                                                            
                                                            // add the cards
                                                            cube.addCard(cards.get(a));
                                                            cube.addCard(cards.get(b));
                                                            cube.addCard(cards.get(c));
                                                            cube.addCard(cards.get(d));
                                                            cube.addCard(cards.get(e));
                                                            
                                                            if ((temp = cube.isWinner()) > 0) {
                                                                bingoFound = true;
                                                                numberOfBingos += temp;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // ignore extra input
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
    
    // inner class representing a single bingo card
    public static class BingoCard {
        private int index = 0;
        private boolean[] covered = new boolean[25];
        private String[] spaces = new String[25];
        public static String[] prefixArray = {"B", "I", "N", "G", "O"};
        
        public BingoCard() {
        }
        
        public BingoCard(boolean[] newCovered) {
            for (int i=0; i<covered.length; i++) {
                covered[i] = newCovered[i];
            }
        }
        
        // adds a row of data to the card
        public void addRow(ArrayList<String> row) {
            for (int column=0; column<prefixArray.length; column++) {
                covered[index] = false;
                spaces[index] = prefixArray[column] + row.get(index%5);
                index++;
            }
        }
        
        // covers spaces on the card with the called letter-number combo
        public void coverSpace(String number) {
            for (int i=0; i<covered.length; i++) {
                if (spaces[i].equals(number)) {
                    covered[i] = true;
                }
            }
        }
        
        // checks this card for a winning condition
        public int isWinner() {
            int retVal = 0;
            int index;
            
            // check rows
            for (int row=0; row<5; row++) {
                index = row*5;
                if (covered[index] && covered[index+1] && covered[index+2] && covered[index+3] && covered[index+4]) {
                    retVal++;
                }
            }
            
            // check cols
            for (int col=0; col<5; col++) {
                index = col;
                if (covered[index] && covered[index+5] && covered[index+10] && covered[index+15] && covered[index+20]) {
                    retVal++;
                }
            }
            
            // check diagonals
            if (covered[0] && covered[6] && covered[12] && covered[18] && covered[24]) {
                retVal++;
            }
            if (covered[4] && covered[8] && covered[12] && covered[16] && covered[20]) {
                retVal++;
            }
            
            return retVal;
        }
        
        public boolean isCovered(int idx) {
            return covered[idx];
        }
//        
//        public String toString() {
//            String hDiv = "+-+-+-+-+-+\n";
//            StringBuffer buf = new StringBuffer();
//            buf.append(hDiv);
//            for (int i=0; i<spaces.length; i++) {
//                buf.append("+");
//                buf.append(covered[i] ? "*" : " ");
//                if (i%5 == 4) {
//                    buf.append("+\n");
//                    buf.append(hDiv);
//                }
//            }
//            return buf.toString();
//        }
    }
    
    // inner class representing a bingo cube
    public static class BingoCube {
        private BingoCard[] cards = new BingoCard[5];
        private int index = 0;
        private int[][] rowSliceSpaces = {
                // each row as a slice
                {0, 1, 2, 3, 4}, 
                {4, 3, 2, 1, 0}
        };
        private int[][] colSliceSpaces = {
                // each column as a slice
                {0, 5, 10, 15, 20}, 
                {20, 15, 10, 5, 0}
        };
        private int[][] diagonalWinnerSpaces = {
                // through the cube
                {0, 6, 12, 18, 24}, 
                {24, 18, 12, 6, 0}, 
                {4, 8, 12, 16, 20}, 
                {20, 16, 12, 8, 4}
        };
        
        // resets the cube for another configuration
        public void reset() {
            index = 0;
            for (int i=0; i<cards.length; i++) {
                cards[i] = null;
            }
        }
        
        // adds a card to the cube
        public void addCard(BingoCard card) {
            cards[index] = card;
            index++;
        }
        
        // check this cube for a winning condition
        public int isWinner() {
            // assume false unless we find a bingo
            int retVal = 0;
            
            // first check straight up on each column
            for (int i=0; i<5; i++) {
                for (int j=0; j<5; j++) {
                    // now we have the space number, see if all 5 cards are covered
                    int index = (i*5) + j;
                    boolean winner = true;
                    for (int k=0; k<5; k++) {
                        winner = winner && cards[k].isCovered(index);
                    }
                    
                    if (winner) {
                        retVal++;
                    }
                }
            }
            
            // check row slices
            for (int i=0; i<rowSliceSpaces.length; i++) {
                for (int row=0; row<5; row++) {
                    boolean winner = true;
                    for (int card=0; card<5; card++) {
                        winner = winner && cards[card].isCovered(rowSliceSpaces[i][card] + (row*5));
                    }
                    
                    if (winner) {
                        retVal++;
                    }
                }
            }
            
            // check column slices
            for (int i=0; i<colSliceSpaces.length; i++) {
                for (int col=0; col<5; col++) {
                    boolean winner = true;
                    for (int card=0; card<5; card++) {
                        winner = winner && cards[card].isCovered(colSliceSpaces[i][card] + col);
                    }
                    
                    if (winner) {
                        retVal++;
                    }
                }
            }
            
            // check diagonals
            for (int i=0; i<diagonalWinnerSpaces.length; i++) {
                boolean winner = true;
                for (int j=0; j<5; j++) {
                    winner = winner && cards[j].isCovered(diagonalWinnerSpaces[i][j]);
                }
                
                if (winner) {
                    retVal++;
                }
            }
            
            return retVal;
        }
    }
}
