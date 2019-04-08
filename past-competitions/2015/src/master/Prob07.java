

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;

public class Prob07 {
    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
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
                // get the number of ships
                int N = Integer.parseInt(br.readLine());
                
                // create list
                LinkedList<Ship> ships = new LinkedList<Ship>();
                
                // loop through the ships
                while (N-- > 0) {
                    // read ship data
                    inLine = br.readLine();
                    
                    // split on colon
                    String[] tokens = inLine.split(":");
                    
                    // split the name and class
                    String[] subTokens = tokens[0].split("_");
                    String name = subTokens[0];
                    String shipClass = subTokens[1];
                    
                    // split the X and Y coordinates
                    subTokens = tokens[1].split(",");
                    int x = Integer.parseInt(subTokens[0]);
                    int y = Integer.parseInt(subTokens[1]);
                    
                    // add ship to the list
                    ships.add(new Ship(name, shipClass, x, y));
                }
                
                // start destroying
                while (ships.peek() != null) {
                    // sort the ships
                    Collections.sort(ships);
                    
                    // destroy the closest ship
                    ships.pop().destroy();
                    
                    // move the remaining ships
                    for (Ship currentShip : ships) {
                        currentShip.move();
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
    
    public static class Ship implements Comparable<Ship> {
        public String name;
        public String shipClass;
        public int x;
        public int y;
        
        public Ship(String nameString, String shipClassString, int xVal, int yVal) {
            name = nameString;
            shipClass = shipClassString;
            x = xVal;
            y = yVal;
        }
        
        public void destroy() {
            System.out.println("Destroyed Ship: " + name + " xLoc: " + x);
        }
        
        public void move() {
            if (shipClass.equals("A")) {
                x -= 10;
            } else if (shipClass.equals("B")) {
                x -= 20;
            } else if (shipClass.equals("C")) {
                x -= 30;
            }
        }
        
        /**
         * -1 means we are less than them (we come first)
         *  1 means they are less than us (they come first)
         *  0 means we are equal
         */
        @Override
        public int compareTo(Ship otherShip) {
            if (this.x < otherShip.x) {
                // we come first
                return -1;
            } else if (this.x > otherShip.x) {
                // they come first
                return 1;
            } else {
                // tie for x, check y
                if (this.y > otherShip.y) {
                    // we come first
                    return -1;
                } else if (this.y < otherShip.y) {
                    // they come first
                    return 1;
                } else {
                    // should never come to this
                    return 0;
                }
            }
        }
    }
}
