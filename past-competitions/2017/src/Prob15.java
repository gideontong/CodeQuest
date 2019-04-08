

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob15 {
    private static final String INPUT_FILE_NAME = "Prob15.in.txt";
    private static final String[] handArray = {"FIVE OF A KIND", "FOUR OF A KIND", "FULL HOUSE", "STRAIGHT", "THREE OF A KIND", "TWO PAIR", "PAIR"};
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // array to hold occurrences of each number
            int[] occurrenceArray = new int[10];
            
            // array to tell us which hands we've made
            int[] handFlagArray = new int[handArray.length];
            
            // loop through test cases
            for (int i=0; i<T; i++) {
                // read the line of text
                inLine = br.readLine();
                
                // start the output
                System.out.print(inLine + " = ");
                
                // empty the occurrence  and flag arrays
                Arrays.fill(occurrenceArray, 0);
                Arrays.fill(handFlagArray, 0);
                
                // hand helper flags
                int numPairs = 0;
                int numThree = 0;
                
                // get the numbers from the serial number
                for (int position=0; position<8; position++) {
                    int currentNumber = Integer.parseInt(""+inLine.charAt(position));
                    
                    // ignore zeroes
                    if (currentNumber > 0) {
                        occurrenceArray[currentNumber]++;
                        
                        // handle hands of occurrences of the same number
                        if (occurrenceArray[currentNumber] == 5) {
                            handFlagArray[0] = 1;
                        } else if (occurrenceArray[currentNumber] == 4) {
                            handFlagArray[1] = 1;
                        } else if (occurrenceArray[currentNumber] == 3) {
                            handFlagArray[4] = 1;
                            numThree++;
                            numPairs--;
                        } else if (occurrenceArray[currentNumber] == 2) {
                            handFlagArray[6] = 1;
                            numPairs++;
                        }
                    }
                }
                
                // full house
                if (((numThree > 0) && (numPairs > 0)) || (numThree > 1)) {
                    handFlagArray[2] = 1;
                }
                
                // two pair
                if (numPairs > 1) {
                    handFlagArray[5] = 1;
                }
                
                // straight
                for (int start=1; start<=5; start++) {
                    if ((occurrenceArray[start] > 0) 
                            && (occurrenceArray[start+1] > 0)
                            && (occurrenceArray[start+2] > 0)
                            && (occurrenceArray[start+3] > 0)
                            && (occurrenceArray[start+4] > 0)) {
                        handFlagArray[3] = 1;
                    }
                }
                
                // check for a real hand
                boolean foundHand = false;
                for (int handNum=0; handNum<handFlagArray.length; handNum++) {
                    if (handFlagArray[handNum] == 1) {
                        System.out.println(handArray[handNum]);
                        foundHand = true;
                        break;
                    }
                }
                
                // use high digit if we didn't find a hand
                if (!foundHand) {
                    for (int digitNum=9; digitNum>0; digitNum--) {
                        if (occurrenceArray[digitNum] > 0) {
                            System.out.println(digitNum);
                            break;
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
}
