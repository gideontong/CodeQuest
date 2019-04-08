

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Prob15 {
    private static final String INPUT_FILE_NAME = "Prob15.in.txt";
    
    // array of disks
    private static Disk[] disks;
    
    // array or towers
    private static Tower[] towers;
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // get the number of disks
                int N = Integer.parseInt(br.readLine());
                System.out.println(N);
                
                // create the disks and towers
                disks = new Disk[N];
                towers = new Tower[N];
                for (int i=0; i<N; i++) {
                    disks[i] = new Disk(i+1, 0);
                    
                    towers[i] = new Tower(0);
                    for (int j=i; j>=0; j--) {
                        towers[i].push(disks[j]);
                    }
                }
                
                // set up the move
                int toPeg = 2;
                int helperPeg = 1;
                
                // do it!
                towers[N-1].moveTower(toPeg, helperPeg);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class Disk {
        public int size;
        
        // 0=A, 1=B, 2=C
        public int location;
        
        public Disk(int theSize, int theLocation) {
            size = theSize;
            location = theLocation;
        }
        
        @Override
        public String toString() {
            return "" + size;
        }
        
        private void printLocation() {
            switch (location) {
                case 0:
                    System.out.print("A");
                    break;
                case 1:
                    System.out.print("B");
                    break;
                case 2:
                    System.out.print("C");
                    break;
                default:
                    System.out.print("UNKNOWN!");
            }
        }
        
        public void moveDisk(int toPeg) {
            // print the old location
            printLocation();
            
            // arrow
            System.out.print("->");
            
            // move it
            location = toPeg;
            
            // print the new location
            printLocation();
            
            System.out.println();
        }
    }
    
    @SuppressWarnings("serial")
    // a single tower of disks
    public static class Tower extends LinkedList<Disk> {
        // 0=A, 1=B, 2=C
        public int location;
        
        public Tower(int theLocation) {
            location = theLocation;
        }
        
        public void moveTower(int toPeg, int helperPeg) {
            int height = this.size();
            
            if (height > 1) {
                // move the sub-tower above the base disk if there is one, but move it to the helperPeg
                towers[height-2].moveTower(helperPeg, toPeg);
            }
            
            // now move the base disk
            this.peekLast().moveDisk(toPeg);
            
            if (height > 1) {
                // now move the sub-tower on top of the base disk - the old location of this tower becomes the helper peg
                towers[height-2].moveTower(toPeg, location);
            }
            
            // now the tower has been moved
            this.location = toPeg;
        }
    }
}
