

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob12 {
    private static final String INPUT_FILE_NAME = "Prob12.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // read how many numbers there are
                int N = Integer.parseInt(br.readLine());
                
                // loop through the numbers
                while (N-- > 0) {
                    // to store the real bits and the list of parity bits
                    ArrayList<Bit> bits = new ArrayList<Bit>();
                    ArrayList<Bit> parityBits = new ArrayList<Bit>();
                    Bit currentBit = null;
                    
                    // for keeping track of where we are
                    int nextBitIndex = 1;
                    
                    // the first bit is always a parity bit
                    currentBit = new Bit(nextBitIndex);
                    bits.add(currentBit);
                    parityBits.add(currentBit);
                    nextBitIndex++;
                    
                    // the second bit is always a parity bit
                    currentBit = new Bit(nextBitIndex);
                    bits.add(currentBit);
                    parityBits.add(currentBit);
                    nextBitIndex++;
                    
                    // read the number
                    inLine = br.readLine();
                    
                    // get the number of data bits
                    int numDataBits = inLine.length();
                    
                    // data bits we have left to allocate
                    int dataBitsLeft = numDataBits;
                    
                    // bit 4 is the next parity
                    int nextParityBitIndex = 4;
                    
                    // figure out how many total bits we need
                    while (dataBitsLeft > 0) {
                        if (nextBitIndex == nextParityBitIndex) {
                            // this is a parity bit
                            currentBit = new Bit(nextBitIndex);
                            parityBits.add(currentBit);
                            
                            // move the next parity index
                            nextParityBitIndex *= 2;
                        } else {
                            // this is not a parity bit
                            currentBit = new Bit(nextBitIndex, inLine.charAt(numDataBits - dataBitsLeft));
                            
                            // consume a data bit
                            dataBitsLeft--;
                            
                            // check to see if this bit will affect parity
                            if (currentBit.getValue() == 1) {
                                // need to consider this bit in our parity calculations
                                for (Bit parityBit : parityBits) {
                                    parityBit.calculateParity(nextBitIndex);
                                }
                            }
                        }
                        
                        // add a bit and move the index no matter what
                        bits.add(currentBit);
                        nextBitIndex++;
                    }
                    
                    // print out the bits
                    for (Bit bit : bits) {
                        System.out.print(bit.getValue());
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
    
    public static class Bit {
        // the index of this bit (first bit is 1, not 0)
        private int index = -1;
        
        // the current value - really this keeps track of even or odd
        private int value = 0;
        
        // parity bit constructor
        public Bit(int bitIndex) {
            // even parity starts with 0
            this(bitIndex, '0');
        }
        
        // data bit constructor
        public Bit(int bitIndex, char charValue) {
            this.index = bitIndex;
            this.value = (charValue == '1') ? 1 : 0;
        }
        
        // only called from a data bit with a value of 1
        public void calculateParity(int bitIndex) {
            if ((this.index & bitIndex) > 0) {
                // we care about this bit index
                this.value++;
            }
        }
        
        // is the value even or odd?
        public int getValue() {
            return value % 2;
        }
    }
}
