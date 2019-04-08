/**
 * CodeQuest 2014
 * Problem 12: Caesar Scytale
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob12 {
    private static final String INPUT_FILE_NAME = "Prob12.in.txt";
    
    private static char[] alphabet = {
        'a', 'b', 'c', 'd', 'e',
        'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y',
        'z'};
    private static int[] indexMap = new int[((int)'z') + 1];
    private static ArrayList<Integer> lineLengths = new ArrayList<Integer>();
    private static String encryptedString = null;
    private static String decryptedString = null;
    
    public static void main(String[] args) {
        // build the map of char values to their alphabet index
        buildIndexMap();
        
        // read the input file and process it
        readInput();
        
        // decrypt message
        decrypt();
        
        StringBuffer buf = new StringBuffer();
        int startIndex = 0;
        int endIndex = 0;
        for (int i=0; i<lineLengths.size(); i++) {
            endIndex += lineLengths.get(i).intValue();
            if (endIndex > decryptedString.length()) {
                buf.append(decryptedString.substring(startIndex));
            } else {
                buf.append(decryptedString.substring(startIndex, endIndex));
                buf.append("\n");
            }
            startIndex = endIndex;
        }
        if (!(startIndex >= decryptedString.length())) {
            buf.append(decryptedString.substring(startIndex));
        }
        
        System.out.print(buf.toString());
    }
    
    private static void decrypt() {
        boolean done = false;
        int inputLength = encryptedString.length();
        
        // loop through all possible array sizes
        for (int numRows=1; numRows<=inputLength; numRows++) {
            if (!done) {
                // check to see if the input will completely fill an array with this many rows
                if ((inputLength % numRows) == 0) {
                    int numCols = inputLength / numRows;
//                    System.out.println(numRows + " rows, " + numCols + " cols");
                    char[][] array = new char[numRows][numCols];
                    
                    // start at the top left and fill down then right
                    int index = 0;
                    for (int col=0; col<numCols; col++) {
                        for (int row=0; row<numRows; row++) {
                            array[row][col] = encryptedString.charAt(index++);
                        }
                    }
                    
                    // unravel the array
                    StringBuffer buf = new StringBuffer();
                    for (int row=0; row<numRows; row++) {
                        for (int col=0; col<numCols; col++) {
                            buf.append(array[row][col]);
                        }
                    }
                    String unraveledString = buf.toString();
                    
                    // kill trailing X spaces
                    while (unraveledString.endsWith("X")) {
                        unraveledString = unraveledString.substring(0, unraveledString.length()-1);
                    }
                    
                    // now shift to decrypt
                    boolean uppercase = false;
                    int indexShift = 0;
                    
                    for (int offset=0; offset<alphabet.length; offset++) {
                        if (!done) {
//                            System.out.println("Offset: " + offset);
                            buf = new StringBuffer();
                            
                            for (int i=0; i<unraveledString.length(); i++) {
                                char currentChar = unraveledString.charAt(i);
                                
                                uppercase = ((""+currentChar).equals((""+currentChar).toUpperCase())) ? true : false;
                                indexShift = uppercase ? 32 : 0;
                                
                                index = indexMap[(int)currentChar + indexShift];
                                index += offset;
                                if (index >= alphabet.length) {
                                    index -= alphabet.length;
                                }
                                
                                buf.append(uppercase ? (""+alphabet[index]).toUpperCase() : alphabet[index]);
                            }
                            
                            decryptedString = buf.toString();
                            if (decryptedString.indexOf("Dear") > -1) {
                                done = true;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void buildIndexMap() {
        for (int i=0; i<indexMap.length; i++) {
            indexMap[i] = -1;
        }
        
        int alphabetIndex = 0;
        for (int i=(int)'a'; i<=(int)'z'; i++) {
            indexMap[i] = alphabetIndex++;
        }
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            StringBuffer buf = new StringBuffer();
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                buf.append(inLine);
                lineLengths.add(new Integer(inLine.length()));
            }
            
            encryptedString = buf.toString();
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
