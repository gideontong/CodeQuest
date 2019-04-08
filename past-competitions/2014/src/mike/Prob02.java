/**
 * CodeQuest 2014
 * Prob02: Deal the Cards
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Prob02 {
    private static final String INPUT_FILE_NAME = "Prob02.in.txt";
    
    private static String[] suitNames = {"CLUB", "DIAMOND", "HEART", "SPADE"};
    private static String[] shortSuitNames = {"C", "D", "H", "S"};
    private static String[] faceValueNames = {"2 card", "3 card", "4 card", "5 card", "6 card", "7 card", "8 card", "9 card", "10 card", "Jack", "Queen", "King", "Ace"};
    private static String[] shortFaceValueNames = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    
    private static HashMap<String, Integer> suitIndexMap = new HashMap<String, Integer>();
    private static HashMap<String, Integer> faceValueIndexMap = new HashMap<String, Integer>();
    
    private static int[] suitValues = null;
    private static int[] faceValues = null;
    
    public static void main(String[] args) {
        init();
        
        // read the input file and process it
        readInput();
    }
    
    private static void init() {
        for (int i=0; i<shortSuitNames.length; i++) {
            suitIndexMap.put(shortSuitNames[i], new Integer(i));
        }
        
        for (int i=0; i<shortFaceValueNames.length; i++) {
            faceValueIndexMap.put(shortFaceValueNames[i], new Integer(i));
        }
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            int handNumber = 1;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                System.out.println("HAND " + handNumber);
                handNumber++;
                
                suitValues = new int[shortSuitNames.length];
                for (int i=0; i<suitValues.length; i++) {
                    suitValues[i] = 0;
                }
                faceValues = new int[shortFaceValueNames.length];
                for (int i=0; i<faceValues.length; i++) {
                    faceValues[i] = 0;
                }
                
                String[] tokens = inLine.split(" ");
                for (int i=0; i<tokens.length; i++) {
                    String currentCard = tokens[i];
                    String currentSuit = currentCard.substring(currentCard.length()-1);
                    String currentFaceValue = currentCard.substring(0, currentCard.length()-1);
                    
                    suitValues[suitIndexMap.get(currentSuit).intValue()]++;
                    faceValues[faceValueIndexMap.get(currentFaceValue).intValue()]++;
                }
                
                // red and black
                System.out.println((suitValues[1]+suitValues[2]) + "-RED");
                System.out.println((suitValues[0]+suitValues[3]) + "-BLACK");
                
                // suits
                for (int i=0; i<suitNames.length; i++) {
                    System.out.print(suitValues[i] + "-" + suitNames[i]);
                    if (suitValues[i] != 1) {
                        System.out.print("S");
                    }
                    System.out.println();
                }
                
                // face values
                for (int i=0; i<faceValueNames.length; i++) {
                    if (faceValues[i] > 0) {
                        System.out.print(faceValues[i] + "-" + faceValueNames[i]);
                        if (faceValues[i] > 1) {
                            System.out.print("s");
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
