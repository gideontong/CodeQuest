

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Prob10 {
    private static final String INPUT_FILE_NAME = "Prob10.in.txt";
    
    private static HashMap<String, String> keyMap;
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // create the map of keys
            createKeyMap();
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            for (int i=0; i<T; i++) {
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                // keep track of caps lock
                boolean capsLock = false;
                
                // loop through the lines
                for (int j=0; j<N; j++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // loop through the letters
                    for (int index=0; index<inLine.length(); index++) {
                        // get the current letter
                        String currentLetter = "" + inLine.charAt(index);
                        
                        if (currentLetter.equalsIgnoreCase("Z")) {
                            // ignore - pushing Z has no effect
                        } else if (currentLetter.equalsIgnoreCase("A")) {
                            // toggle capsLock
                            capsLock = capsLock ? false : true;
                        } else if (currentLetter.equalsIgnoreCase(" ")) {
                            // print spaces
                            System.out.print(" ");
                        } else {
                            // need to translate this character - is it a letter?
                            boolean isLetter = true;
                            if ((currentLetter.toUpperCase().charAt(0) < 'A') || (currentLetter.toUpperCase().charAt(0) > 'Z')) {
                                // not a letter - caps only matters for caps lock
                                isLetter = false;
                            }
                            
                            boolean isCapital = false;
                            if (isLetter) {
                                if (currentLetter.equals(currentLetter.toUpperCase())) isCapital = true;
                            }
                            
                            if (capsLock) {
                                // toggle capital when capsLock is on
                                isCapital = isCapital ? false : true;
                            }
                            
                            // get the replacement letter
                            String replacement = keyMap.get(currentLetter.toUpperCase());
                            
                            // convert to lowercase if necessary
                            if (!isCapital) {
                                replacement = replacement.toLowerCase();
                            }
                            
                            // print it
                            System.out.print(replacement);
                        }
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
    
    private static void createKeyMap() {
        // we'll use caps for ease of use, and keep track of case somewhere else
        keyMap = new HashMap<String, String>();
        
        keyMap.put("Q", "    ");
        keyMap.put("W", "Q");
        keyMap.put("E", "W");
        keyMap.put("R", "E");
        keyMap.put("T", "R");
        keyMap.put("Y", "T");
        keyMap.put("U", "Y");
        keyMap.put("I", "U");
        keyMap.put("O", "I");
        keyMap.put("P", "O");
        
        keyMap.put("S", "A");
        keyMap.put("D", "S");
        keyMap.put("F", "D");
        keyMap.put("G", "F");
        keyMap.put("H", "G");
        keyMap.put("J", "H");
        keyMap.put("K", "J");
        keyMap.put("L", "K");
        
        keyMap.put("X", "Z");
        keyMap.put("C", "X");
        keyMap.put("V", "C");
        keyMap.put("B", "V");
        keyMap.put("N", "B");
        keyMap.put("M", "N");
        keyMap.put(",", "M");
        keyMap.put(".", ",");
    }
}
