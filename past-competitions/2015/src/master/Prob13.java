

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Prob13 {
    private static final String INPUT_FILE_NAME = "Prob13.in.txt";
    
    // to store blocks
    private static Block[] blocks = null;
    
    // to tell if a block has been used in this tower already
    private static boolean[] used = null;
    
    // current height of the tower
    private static int currentHeight = 0;
    
    // max height found so far
    private static int maxHeight = 0;
    
    // the tower
    private static LinkedList<Block> tower = null;
    
    // universal base
    private static Block base = new Block(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    
    // convenience
    private static int numBlocks = 0;
    
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
                // reset
                currentHeight = 0;
                maxHeight = 0;
                
                // get the number of blocks
                int N = Integer.parseInt(br.readLine());
                numBlocks = N;
                
                // set up structures
                blocks = new Block[N];
                used = new boolean[N];
                
                // start the tower
                tower = new LinkedList<Block>();
                tower.push(base);
                
                // initialize arrays
                Arrays.fill(blocks, null);
                Arrays.fill(used, false);
                
                // read the block data and instantiate blocks
                for (int i=0; i<N; i++) {
                    inLine = br.readLine();
                    String[] tokens = inLine.split("x");
                    blocks[i] = new Block(tokens[0], tokens[1], tokens[2]);
                }
                
                // build the tower recursively
                build();
                
                // print the result
                System.out.println(maxHeight);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // recursive build method
    private static void build() {
        // before we even start, check the possibility of building a taller tower
        if (isWinPossible()) {
            // tells us whether we added a block to the tower during this method call
            boolean addedBlock = false;
            
            // check the top of the tower
            Block currentTop = tower.peek();
            
            // try every block to see if it can fit next
            for (int i=0; i<numBlocks; i++) {
                // has this one been used?
                if (!used[i]) {
                    // this block has not been used yet - try every rotation
                    for (int j=0; j<3; j++) {
                        // try it
                        if (blocks[i].willFitOnTopOf(currentTop)) {
                            // this block can go next - flag that this is not the top of the tower
                            addedBlock = true;
                            
                            // add this block
                            tower.push(blocks[i]);
                            currentHeight += blocks[i].h;
                            used[i] = true;
                            
                            // continue building
                            build();
                            
                            // remove this block
                            tower.pop();
                            currentHeight -= blocks[i].h;
                            used[i] = false;
                        }
                        
                        // rotate it to try another another way
                        blocks[i].rotate();
                    }
                }
            }
            
            // done trying blocks - did we add anything?
            if (!addedBlock) {
                // nothing to add - this must be the top
                if (currentHeight > maxHeight) {
                    // new max height
                    maxHeight = currentHeight;
//                    System.out.println("Found new max height = " + maxHeight);
                }
            }
        }
    }
    
    private static boolean isWinPossible() {
        int possibleHeight = currentHeight;
        
        // loop through the blocks
        for (int i=0; i<numBlocks; i++) {
            if (!used[i]) {
                // this block has potential
                possibleHeight += blocks[i].getMaxDim();
            }
        }
        
        return (possibleHeight > maxHeight);
    }
    
    public static class Block {
        // length, width, height
        public int l, w, h;
        
        private int numRotations = 0;
        
        // constructor needs the side lengths
        public Block(String s1, String s2, String s3) {
            this(Integer.parseInt(s1), Integer.parseInt(s2), Integer.parseInt(s3));
        }
        
        // constructor needs the side lengths
        public Block(int s1, int s2, int s3) {
            int[] dims = new int[3];
            dims[0] = s1;
            dims[1] = s2;
            dims[2] = s3;
            Arrays.sort(dims);
            
            // numbers will be sorted by now so l >= w >= h
            h = dims[0];
            w = dims[1];
            l = dims[2];
        }
        
        // rotate the block in such a way that l >= w always
        public void rotate() {
            int temp;
            
            switch (numRotations++) {
                case 0:
                    // first rotation: 3, 2, 1 --> 3, 1, 2
                    temp = w;
                    w = h;
                    h = temp;
                    break;
                case 1:
                    // second rotation: 3, 1, 2 --> 2, 1, 3
                    temp = l;
                    l = h;
                    h = temp;
                    break;
                case 2:
                    // third rotation: 2, 1, 3 --> 3, 2, 1
                    temp = l;
                    l = h;
                    h = w;
                    w = temp;
                default:
                    // set this here to prevent overflow from too much rotating!
                    numRotations = 0;
                    break;
            }
        }
        
        public boolean willFitOnTopOf(Block otherBlock) {
            // check length
            if (otherBlock.l >= this.l) {
                // length is okay, check width
                if (otherBlock.w >= this.w) {
                    // we fit on top of the other block
                    return true;
                }
            }
            
            return false;
        }
        
        public int getMaxDim() {
            int retVal = Math.max(l, w);
            retVal = Math.max(retVal, h);
            return retVal;
        }
    }
}
