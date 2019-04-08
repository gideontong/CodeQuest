/**
 * CodeQuest 2014
 * Problem 16: Treasure Hunt
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob16 {
//    private static final String INPUT_FILE_NAME = "TreasureHunt.in.txt";
//    private static final String INPUT_FILE_NAME = "SimpleTH.in.txt";
//    private static final String INPUT_FILE_NAME = "SecondSimpleTH.in.txt";
//    private static final String INPUT_FILE_NAME = "HardTH.in.txt";
//    private static final String INPUT_FILE_NAME = "SuperHardTH.in.txt";
//    private static final String INPUT_FILE_NAME = "LotsOfTorchesTH.in.txt";
//    private static final String INPUT_FILE_NAME = "FourStepsTH.in.txt";
//    private static final String INPUT_FILE_NAME = "UltraHardTH.in.txt";
//    private static final String INPUT_FILE_NAME = "InsaneHardTH.in.txt";
//    private static final String INPUT_FILE_NAME = "InsaneHarderTH.in.txt";
//    private static final String INPUT_FILE_NAME = "InsaneHardTorchesTH.in.txt";
    private static final String INPUT_FILE_NAME = "Prob16.in.txt";
    
    // convenience values
    private static int numRows = 0;
    private static int numCols = 0;
    private static int startRow = 0;
    private static int startCol = 0;
    private static int endRow = 0;
    private static int endCol = 0;
    
    // the playing field
    private static char[][] field = null;
    
    // used for looking around while computing distances
    private static int[] rowMods = {-1, 1, 0, 0};
    private static int[] colMods = {0, 0, -1, 1};
    
    // keeps track of the torch coordinates
    private static ArrayList<String> torchArray = new ArrayList<String>();
    
    // starting position, torches, treasure and an array to tell if they have been used or not
    private static String[] artifactArray = null;
    private static boolean[] used = null;
    
    // distances from place to place, and a temp array for computing them
    private static int[][] artifactDistances = null;
    private static int[][] tempDistance = null;
    
    // holds the best route distance
    private static int minDistance = Integer.MAX_VALUE;
    
    // index of the treasure in the artifactArray
    private static int treasureIndex = -1;
    
    // used in the recursion
    private static int currentDepth = 0;
    private static int[] currentRoute = null;
    private static StringBuffer buf = new StringBuffer();
    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        
        // read the input file and process it
        readInput();
        
        // create the arrays we need to solve the map
        createArrays();
        
        // use the starting point
        used[0] = true;
        currentRoute[0] = 0;
        
        // solve it - current index, distance, light remaining
        solveRecursively(0, 0, 15);
        
        long endTime = System.currentTimeMillis();
        
//        System.out.println("File name: " + INPUT_FILE_NAME);
//        System.out.println("Shortest distance: " + minDistance);
//        System.out.println("Path: " + buf.toString());
//        System.out.println(((endTime - startTime) / 1000) + "." + (endTime-startTime) + " seconds");
//        System.out.println((endTime-startTime) + " milliseconds");
        
        System.out.println(minDistance);
    }
    
    /**
     * Method to create the playing field and set some convenience variables
     */
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            ArrayList<String> lines = new ArrayList<String>();
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                if (numCols == 0) {
                    // get the number of columns
                    numCols = inLine.length();
                }
                
                // for now just save the lines to process later
                lines.add(inLine);
            }
            
            // get the number of rows
            numRows = lines.size();
            
            // create the field and an array we will use to compute distances
            field = new char[numRows][numCols];
            tempDistance = new int[numRows][numCols];
            
            // process each line
            for (int row=0; row<numRows; row++) {
                inLine = lines.get(row);
                for (int col=0; col<numCols; col++) {
                    field[row][col] = inLine.charAt(col);
                    
                    // handle special characters
                    if (field[row][col] == '|' || field[row][col] == '-') {
                        // change | and - to x
                        field[row][col] = 'x';
                    } else if (field[row][col] == 'H') {
                        // mark the starting position of the hunter
                        startRow = row;
                        startCol = col;
                    } else if (field[row][col] == 'T') {
                        // mark the ending position of the hunter
                        endRow = row;
                        endCol = col;
                    } else if (field[row][col] == 't') {
                        // mark torches
                        torchArray.add(row+","+col);
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
    
    /**
     * This method creates some arrays that we will need later on.  Specifically, 
     * the artifactArray, artifactDistances, used, and currentRoute arrays.  It also sets the
     * value of the treasureIndex variable for convenience.
     */
    private static void createArrays() {
        // create the artifact array
        artifactArray = new String[torchArray.size()+2];
        
        // artifact 0 is the start point
        artifactArray[0] = startRow+","+startCol;
        
        // the next artifacts are the torches
        for (int i=0; i<torchArray.size(); i++) {
            artifactArray[i+1] = torchArray.get(i);
        }
        
        // the last artifact is the treasure
        artifactArray[artifactArray.length-1] = endRow+","+endCol;
        treasureIndex = artifactArray.length-1;
        
        // create the array to hold distances
        artifactDistances = new int[artifactArray.length][artifactArray.length];
        
        // initialize distances to -1 so we know which ones we haven't computed
        for (int i=0; i<artifactArray.length; i++) {
            for (int j=0; j<artifactArray.length; j++) {
                artifactDistances[i][j] = -1;
            }
        }
        
        // create the used array and the current route array
        used = new boolean[artifactArray.length];
        currentRoute = new int[artifactArray.length];
        for (int i=0; i<artifactArray.length; i++) {
            used[i] = false;
            currentRoute[i] = -1;
        }
    }
    
    /**
     * Recursive method to solve the game.  See the solution notes for a detailed explanation.
     */
    private static void solveRecursively(int currentIndex, int currentDistance, int currentLightLeft) {
        // increase our depth to put the next step at the right spot in the route array
        currentDepth++;
        
        // check to see if we are finished
        if (currentIndex == treasureIndex) {
            // done
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                
                buf = new StringBuffer();
                for (int i=0; i<currentDepth; i++) {
                    if (i > 0) buf.append(", ");
                    buf.append(new Integer(artifactArray[currentRoute[i]].split(",")[0]).intValue() + 1);
                    buf.append(",");
                    buf.append(new Integer(artifactArray[currentRoute[i]].split(",")[1]).intValue() + 1);
//                    buf.append(artifactArray[currentRoute[i]]);
                }
//                System.out.println(minDistance);
            }
        } else {
            // not at the treasure yet - try to sprint to the treasure
            int distanceToTreasure = getDistance(currentIndex, treasureIndex);
            
            if (currentLightLeft >= distanceToTreasure) {
                // next stop - the treasure
                currentRoute[currentDepth] = treasureIndex;
                
                solveRecursively(treasureIndex, currentDistance+distanceToTreasure, currentLightLeft-distanceToTreasure);
                
                currentRoute[currentDepth] = -1;
            } else {
                // can't get to the treasure from here - consider all paths
                for (int i=1; i<treasureIndex; i++) {
                    // loop through the artifacts
                    if (!used[i]) {
                        // this one is not used yet - get the distance
                        int distanceToNextTorch = getDistance(currentIndex, i);
                        
                        if (distanceToNextTorch > -1) {
                            // there is a path to the next torch, do we have enough light?
                            if (currentLightLeft >= distanceToNextTorch) {
                                int distanceFromNextTorchToTreasure = getDistance(i, treasureIndex);
                                
                                // we have enough light - will our step count be too high?  Remember that we'll also have to go to the treasure after this
                                if ((currentDistance + distanceToNextTorch + distanceFromNextTorchToTreasure) < minDistance) {
                                    // the path is not too long - do it
                                    currentRoute[currentDepth] = i;
                                    used[i] = true;
                                    
                                    solveRecursively(i, currentDistance+distanceToNextTorch, currentLightLeft+15-distanceToNextTorch);
                                    
                                    used[i] = false;
                                    currentRoute[currentDepth] = -1;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        currentDepth--;
    }
    
    /**
     * Method to get the distance from place to place.  Uses a lookup table.
     */
    private static int getDistance(int fromIndex, int toIndex) {
        // assume we've calculated the distance
        int retVal = artifactDistances[fromIndex][toIndex];
        
        // we need to calculate this distance
        if (retVal == -1) {
            // calculate the distance between artifacts
            computeDistance(toIndex);
            
            // get the distance we just calculated
            retVal = artifactDistances[fromIndex][toIndex];
        }
        
        return retVal;
    }
    
    /**
     * Computes the distance to a destination
     */
    private static void computeDistance(int toIndex) {
        // reset the array
        for (int row=0; row<numRows; row++) {
            for (int col=0; col<numCols; col++) {
                tempDistance[row][col] = Integer.MAX_VALUE;
            }
        }
        
        // get the toIndex row and column
        int toRow = Integer.parseInt(artifactArray[toIndex].split(",")[0]);
        int toCol = Integer.parseInt(artifactArray[toIndex].split(",")[1]);
        
        // work backwards from where we want to go
        tempDistance[toRow][toCol] = 0;
        
        // flag to tell when the map doesn't change any more
        boolean newDistance = true;
        
        // keep computing until the map settles down
        while (newDistance) {
            newDistance = false;
            for (int row=1; row<numRows-1; row++) {
                for (int col=1; col<numCols-1; col++) {
                    if (field[row][col] != 'x') {
                        // save the original distance so we know if something is changing
                        int originalDistance = tempDistance[row][col];
                        
                        // look around
                        for (int i=0; i<rowMods.length; i++) {
                            int newRow = row + rowMods[i];
                            int newCol = col + colMods[i];
                            
                            // found a new best distance to this space
                            if (tempDistance[newRow][newCol] < tempDistance[row][col]) {
                                tempDistance[row][col] = tempDistance[newRow][newCol] + 1;
                            }
                        }
                        
                        // if a distance changed, then we need to do another pass
                        if (tempDistance[row][col] != originalDistance) {
                            newDistance = true;
                        }
                    }
                }
            }
        }
        
        // now we have the distances to get to the destination from anywhere - save them
        for (int i=0; i<artifactArray.length; i++) {
            // get the row and column of this artifact
            int row = Integer.parseInt(artifactArray[i].split(",")[0]);
            int col = Integer.parseInt(artifactArray[i].split(",")[1]);
            
            // get the distance from this artifact to the destination
            int distance = tempDistance[row][col];
            if (distance == Integer.MAX_VALUE) {
                // we can't get from here to the destination
                distance = -2;
            }
            artifactDistances[i][toIndex] = artifactDistances[toIndex][i] = distance;
        }
    }
}
