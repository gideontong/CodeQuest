/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

/**
 * Axis definitions:
 *   - The x axis goes left and right (right is positive)
 *   - The y axis goes up and down (up is positive)
 *   - The z axis goes into and out of the screen (into the screen is positive)
 * 
 * If the cube were sitting on a table and I was sitting in front of it:
 *   - Face 1 is the front of the cube facing me
 *   - Face 2 is the top
 *   - Face 3 is the right side as I look at it
 *   - Face 4 is the bottom
 *   - Face 5 is the left side as I look at it
 *   - Face 6 is the back
 * 
 * Folding out:
 *   - We always rotate the cube until point 1 is on face 1
 *   
 *   - If the two points are on the same face, then it's a simple distance calculation with x and y
 *   
 *   - If the two points are on opposite faces (1 and 6 after rotating), then there are four cases to consider.
 *     When we fold out the cube, we turn z coordinates into x and y coordinates depending on how we unfold.
 *   
 *   Fold up:                          Fold left:
 *   
 *          +-------+                                                   
 *          |       |                                                   
 *          |   6   |                                                   
 *          |       |                                                   
 *          +-------+                                  +-------+        
 *          |       |                                  |       |        
 *          |   2   |                                  |   2   |        
 *          |       |                                  |       |        
 *  +-------+-------+-------+          +-------+-------+-------+-------+
 *  |       |       |       |          |       |       |       |       |
 *  |   5   |   1   |   3   |          |   6   |   5   |   1   |   3   |
 *  |       |       |       |          |       |       |       |       |
 *  +-------+-------+-------+          +-------+-------+-------+-------+
 *          |       |                                  |       |        
 *          |   4   |                                  |   4   |        
 *          |       |                                  |       |        
 *          +-------+                                  +-------+        
 * 
 * 
 *   Fold down:                          Fold right:
 *   
 *          +-------+                          +-------+        
 *          |       |                          |       |        
 *          |   2   |                          |   2   |        
 *          |       |                          |       |        
 *  +-------+-------+-------+          +-------+-------+-------+-------+
 *  |       |       |       |          |       |       |       |       |
 *  |   5   |   1   |   3   |          |   5   |   1   |   3   |   6   |
 *  |       |       |       |          |       |       |       |       |
 *  +-------+-------+-------+          +-------+-------+-------+-------+
 *          |       |                          |       |        
 *          |   4   |                          |   4   |        
 *          |       |                          |       |        
 *          +-------+                          +-------+        
 *          |       |                                                   
 *          |   6   |                                                   
 *          |       |                                                   
 *          +-------+                                                   
 * 
 * 
 *   - If the two points are on adjacent faces, we rotate until point 2 is on face 2, then there are 5 cases to consider:
 * 
 *  No rotation:      Rotate once right:          Rotate once left:
 *
 *  +-------+                  +-------+          +-------+
 *  |       |                  |       |          |       |
 *  |   2   |                  |   2   |          |   2   |
 *  |       |                  |       |          |       |
 *  +-------+          +-------+-------+          +-------+-------+
 *  |       |          |       |       |          |       |       |
 *  |   1   |          |   1   |   3   |          |   5   |   1   |
 *  |       |          |       |       |          |       |       |
 *  +-------+          +-------+-------+          +-------+-------+
 *
 *
 *  Rotate twice left:                 Rotate twice right:
 *
 *  +-------+                                          +-------+
 *  |       |                                          |       |
 *  |   2   |                                          |   2   |
 *  |       |                                          |       |
 *  +-------+-------+-------+          +-------+-------+-------+
 *  |       |       |       |          |       |       |       |
 *  |   6   |   5   |   1   |          |   1   |   3   |   6   |
 *  |       |       |       |          |       |       |       |
 *  +-------+-------+-------+          +-------+-------+-------+
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Formatter;


public class Prob16 {
    private static final String INPUT_FILE_NAME = "Prob16.in.txt";
    
    // keep track of cube dimensions
    static double sideLength = 0.0;
    static double faceDistance = 0.0;
    
    // keep track of the two points we're working with
    static double x1, y1, z1 = 0.0;
    static double x2, y2, z2 = 0.0;
    static int face1, face2 = 0;
    
    public static void main(String[] args) {
        // read the input
        readInput();
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            int lineNum = 0;
            String[] tokens = null;
            
            while ((inLine = br.readLine()) != null) {
                lineNum++;
                
                if (lineNum == 1) {
                    // first line gives cube data
                    sideLength = new Double(inLine).doubleValue();
                    faceDistance = sideLength / 2.0;
                } else if (lineNum == 2) {
                    // second line gives point 1 data - split on the spaces
                    tokens = inLine.split(" ");
                    x1 = new Double(tokens[0]).doubleValue();
                    y1 = new Double(tokens[1]).doubleValue();
                    z1 = new Double(tokens[2]).doubleValue();
                } else if (lineNum == 3) {
                    // third line gives point 2 data - split on the spaces
                    tokens = inLine.split(" ");
                    x2 = new Double(tokens[0]).doubleValue();
                    y2 = new Double(tokens[1]).doubleValue();
                    z2 = new Double(tokens[2]).doubleValue();
                    
                    // do the calculation for these two points
                    calculate();
                    
                    // reset the line number to get the next set of points
                    lineNum = 0;
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void calculate() {
        // figure out which face each point is on
        updateFaces();
        
        // start with 0 distance
        double distance = 0;
        
        // rotate the cube so point1 is on face 1
        if ((face1 == 2) || (face1 == 4)) {
            flipCube();
        }
        while (face1 != 1) {
            rotateCube();
        }
        
        // now make sure that point 2 is either on face 1, 2, or 6
        if (face2 == 1) {
            // simple calculation - both points are on the same face
            distance = calculateDistance(x1, y1, x2, y2);
        } else if (face2 == 6) {
            // points are on opposite faces - only four conditions to check
            double temp = 0;
            distance = Double.MAX_VALUE;
            double tempDistance = 0;
            
            // fold up
            temp = 2.0 * sideLength - y2;
            tempDistance = calculateDistance(x1, y1, x2, temp);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            
            // fold down
            temp = -2.0 * sideLength - y2;
            tempDistance = calculateDistance(x1, y1, x2, temp);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            
            // fold right
            temp = 2.0 * sideLength - x2;
            tempDistance = calculateDistance(x1, y1, temp, y2);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            
            // fold left
            temp = -2.0 * sideLength - x2;
            tempDistance = calculateDistance(x1, y1, temp, y2);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
        } else {
            // points are on adjacent faces - 5 conditions to check
            // spin until we get the configuration we want so we're always checking the same conditions
            while (face2 != 2) {
                spinCube();
            }
            
            double tempX = x2;
            double tempZ = z2;
            double saveX = 0;
            double modX = 0;
            double modZ = 0;
            distance = Double.MAX_VALUE;
            double tempDistance = 0;
            
            // no rotation
            modX = tempX;
            modZ = sideLength + tempZ;
            tempDistance = calculateDistance(x1, y1, modX, modZ);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            
            // rotate once right
            saveX = tempX;
            tempX = tempZ;
            tempZ = saveX * -1.0;
            modX = sideLength + tempX;
            modZ = sideLength + tempZ;
            tempDistance = calculateDistance(x1, y1, modX, modZ);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            
            // rotate twice right
            saveX = tempX;
            tempX = tempZ;
            tempZ = saveX * -1.0;
            modX = sideLength * 2.0 + tempX;
            modZ = sideLength + tempZ;
            tempDistance = calculateDistance(x1, y1, modX, modZ);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            
            // rotate once left
            tempX = x2;
            tempZ = z2;
            saveX = tempX;
            tempX = tempZ * -1.0;
            tempZ = saveX;
            modX = -sideLength + tempX;
            modZ = sideLength + tempZ;
            tempDistance = calculateDistance(x1, y1, modX, modZ);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
            
            // rotate twice left
            saveX = tempX;
            tempX = tempZ * -1.0;
            tempZ = saveX;
            modX = -sideLength * 2.0 + tempX;
            modZ = sideLength + tempZ;
            tempDistance = calculateDistance(x1, y1, modX, modZ);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
        }
        
        // print out the smallest distance
        Formatter fmt = new Formatter();
        fmt.format("%.4f", distance);
        System.out.println(fmt);
    }
    
    static void updateFaces() {
        updateFace(x1, y1, z1, 1);
        updateFace(x2, y2, z2, 2);
    }
    
    static void updateFace(double x, double y, double z, int pointNum) {
        int faceNum = 0;
        
        if (z == -faceDistance) {
            faceNum = 1;
        } else if (y == faceDistance) {
            faceNum = 2;
        } else if (x == faceDistance) {
            faceNum = 3;
        } else if (y == -faceDistance) {
            faceNum = 4;
        } else if (x == -faceDistance) {
            faceNum = 5;
        } else if (z == faceDistance) {
            faceNum = 6;
        }
        
        if (pointNum == 1) {
            face1 = faceNum;
        } else {
            face2 = faceNum;
        }
    }
    
    // rotate the cube around the x axis, so only change y and z
    static void flipCube() {
        // move point 1
        double oldY = y1;
        double oldZ = z1;
        y1 = oldZ * -1.0;
        z1 = oldY;
        
        // move point 2
        oldY = y2;
        oldZ = z2;
        y2 = oldZ * -1.0;
        z2 = oldY;
        
        updateFaces();
    }
    
    // rotate the cube around the y axis, so only change x and z
    static void rotateCube() {
        // move point 1
        double oldX = x1;
        double oldZ = z1;
        x1 = oldZ;
        z1 = oldX * -1.0;
        
        // move point 2
        oldX = x2;
        oldZ = z2;
        x2 = oldZ;
        z2 = oldX * -1.0;
        
        updateFaces();
    }
    
    // spin the cube around the z axis, so only change x and y
    static void spinCube() {
        // move point 1
        double oldX = x1;
        double oldY = y1;
        x1 = oldY;
        y1 = oldX * -1.0;
        
        // move point 2
        oldX = x2;
        oldY = y2;
        x2 = oldY;
        y2 = oldX * -1.0;
        
        updateFaces();
    }
    
    // calculate the distance between the two points
    static double calculateDistance(double a1, double b1, double a2, double b2) {
        double diff1 = a2 - a1;
        double diff2 = b2 - b1;
        
        diff1 *= diff1;
        diff2 *= diff2;
        
        double retVal = Math.sqrt(diff1 + diff2);
        
        return retVal;
    }
}
