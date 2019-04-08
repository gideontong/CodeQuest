import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob19 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob19.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            /**
             * Notes: Rotating a rotor down is the same as moving the index we're at up
             */
            
            // rotors[rotor #][Forward/Backward][0-9]
            int[][][] rotors = {
                    {{1, 2, 4, -3, 1, -1, 2, 0, 1, -7}, {3, -1, 7, -2, 1, -1, -4, 0, -2, -1}}, 
                    {{0, 2, 3, -1, 2, 4, -5, -3, 0, -2}, {0, 5, 1, -2, 3, -3, -2, 2, 0, -4}}, 
                    {{5, 8, -1, 4, -1, 3, -6, -5, -4, -3}, {6, 1, 5, 1, 4, -5, 3, -4, -3, -8}}, 
                    {{1, 5, 3, -1, 5, -5, 1, -3, -5, -1}, {5, -1, 1, 5, 3, -3, -5, -1, 1, -5}}};
            
            // reflector
            int[] reflector = {3, 5, 6, -3, 1, -1, -5, 2, -6, -2};
            
//            int[] rotor1forward = {1, 2, 4, -3, 1, -1, 2, 0, 1, -7};
//            int[] rotor1backward = {3, -1, 7, -2, 1, -1, -4, 0, -2, -1};
//            int[] rotor2forward = {0, 2, 3, -1, 2, 4, -5, -3, 0, -2};
//            int[] rotor2backward = {0, 5, 1, -2, 3, -3, -2, 2, 0, -4};
//            int[] rotor3forward = {5, 8, -1, 4, -1, 3, -6, -5, -4, -3};
//            int[] rotor3backward = {6, 1, 5, 1, 4, -5, 3, -4, -3, -8};
//            int[] rotor4forward = {1, 5, 3, -1, 5, -5, 1, -3, -5, -1};
//            int[] rotor4backward = {5, -1, 1, 5, 3, -3, -5, -1, 1, -5};
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // current machine
                int[][] currentMachine = new int[7][];
                
                // offsets for rotor rotation
                int[] rotorOffsets = new int[7];
                Arrays.fill(rotorOffsets, 0);
                
                // original rotor positions
                int[] initialPositions = new int[3];
                
                // read in the rotors and their initial offsets
                for (int i=0; i<3; i++) {
                    // read the line and split
                    inLine = br.readLine();
                    String[] tokens = inLine.split(" ");
                    
                    // get the rotor number and its position
                    int rotorNumber = Integer.parseInt(tokens[0]) - 1;
                    int rotorOffset = Integer.parseInt(tokens[1]);
                    
                    // remember the original rotor position
                    initialPositions[i] = rotorOffset;
                    
                    // set the rotor going forward
                    currentMachine[i] = rotors[rotorNumber][0];
                    rotorOffsets[i] = rotorOffset;
                    
                    // set the rotor going backwards
                    currentMachine[6-i] = rotors[rotorNumber][1];
                    rotorOffsets[6-i] = rotorOffset;
                }
                
                // the reflector is always fourth and doesn't move
                currentMachine[3] = reflector;
                
                // read in the number to encode
                inLine = br.readLine();
                
                // encode one digit at a time
                for (int i=0; i<inLine.length(); i++) {
                    int currentIndex = Integer.parseInt(inLine.substring(i, i+1));
                    
                    for (int step=0; step<7; step++) {
//                        System.out.print("Step " + step + ": " + currentIndex + "->");
                        // look for the next wire to follow
                        int nextWireIndex = currentIndex - rotorOffsets[step];
                        if (nextWireIndex < 0) nextWireIndex += 10;
                        
                        // step through the rotor
                        currentIndex += currentMachine[step][nextWireIndex];
                        
                        // correct for going out of bounds
                        if (currentIndex < 0) currentIndex += 10;
                        if (currentIndex > 9) currentIndex -= 10;
//                        System.out.println(currentIndex);
                    }
                    
                    // print out the encoded digit
                    System.out.print(currentIndex);
                    
                    // rotate right rotor and check for overflow
                    rotorOffsets[2]++;
                    rotorOffsets[4]++;
                    if (rotorOffsets[2] > 9) {
                        rotorOffsets[2] = 0;
                        rotorOffsets[4] = 0;
                    }
                    
                    // check for full revolution
                    if (rotorOffsets[2] == initialPositions[2]) {
                        // rotate the middle rotor and check for overflow
                        rotorOffsets[1]++;
                        rotorOffsets[5]++;
                        if (rotorOffsets[1] > 9) {
                            rotorOffsets[1] = 0;
                            rotorOffsets[5] = 0;
                        }
                        
                        // check for full revolution
                        if (rotorOffsets[1] == initialPositions[1]) {
                            // rotate the left rotor and check for overflow
                            rotorOffsets[0]++;
                            rotorOffsets[6]++;
                            if (rotorOffsets[0] > 9) {
                                rotorOffsets[0] = 0;
                                rotorOffsets[6] = 0;
                            }
                        }
                    }
                }
                
                // newline
                System.out.println();
                
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
