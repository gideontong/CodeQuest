/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Thinning {
    private static final String INPUT_FILE_NAME = "Prob17.in.txt";
    
    static int numRows = 0;
    static int numCols = 0;
    static int[][] pixels = null;
    static boolean[][] delete = null;
    static ArrayList<String> imageRows = new ArrayList<String>();
    
    public static void main(String[] args) {
        readInput();
        buildImage();
        thinImage();
        printImage();
    }
    
    private static void printImage() {
        for (int row=0; row<numRows; row++) {
            for (int col=0; col<numCols; col++) {
                System.out.print(""+pixels[row][col]);
            }
            System.out.println();
        }
    }
    
    private static void thinImage() {
        boolean done = false;
        while (!done) {
            done = true;
            if (computeDelete()) {
                done = false;
                removeDeletes();
            }
            if (computeDelete2()) {
                done = false;
                removeDeletes();
            }
        }
    }
    
    private static void removeDeletes() {
        for (int row=0; row<numRows; row++) {
            for (int col=0; col<numCols; col++) {
                if (delete[row][col]) {
                    pixels[row][col] = 0;
                    delete[row][col] = false;
                }
            }
        }
    }
    
    private static boolean computeDelete() {
        boolean retVal = false;
        for (int row=0; row<numRows; row++) {
            for (int col=0; col<numCols; col++) {
                // get p1 through p8
                int currentPixel;
                try { currentPixel = pixels[row-1][col]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p1 = currentPixel;
                try { currentPixel = pixels[row-1][col+1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p2 = currentPixel;
                try { currentPixel = pixels[row][col+1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p3 = currentPixel;
                try { currentPixel = pixels[row+1][col+1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p4 = currentPixel;
                try { currentPixel = pixels[row+1][col]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p5 = currentPixel;
                try { currentPixel = pixels[row+1][col-1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p6 = currentPixel;
                try { currentPixel = pixels[row][col-1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p7 = currentPixel;
                try { currentPixel = pixels[row-1][col-1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p8 = currentPixel;
                
                // compute n
                int n = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
                
                // compute s
                int s = 0;
                if ((p1 == 0) && (p2 == 1)) s++;
                if ((p2 == 0) && (p3 == 1)) s++;
                if ((p3 == 0) && (p4 == 1)) s++;
                if ((p4 == 0) && (p5 == 1)) s++;
                if ((p5 == 0) && (p6 == 1)) s++;
                if ((p6 == 0) && (p7 == 1)) s++;
                if ((p7 == 0) && (p8 == 1)) s++;
                if ((p8 == 0) && (p1 == 1)) s++;
                
                if (pixels[row][col] == 1) {
                    if ((2 <= n) && (n <= 6)) {
                        if (s == 1) {
                            if ((p1 * p3 * p5) == 0) {
                                if ((p3 * p5 * p7) == 0) {
                                    delete[row][col] = true;
                                    retVal = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return retVal;
    }
    
    private static boolean computeDelete2() {
        boolean retVal = false;
        for (int row=0; row<numRows; row++) {
            for (int col=0; col<numCols; col++) {
                // get p1 through p8
                int currentPixel;
                try { currentPixel = pixels[row-1][col]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p1 = currentPixel;
                try { currentPixel = pixels[row-1][col+1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p2 = currentPixel;
                try { currentPixel = pixels[row][col+1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p3 = currentPixel;
                try { currentPixel = pixels[row+1][col+1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p4 = currentPixel;
                try { currentPixel = pixels[row+1][col]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p5 = currentPixel;
                try { currentPixel = pixels[row+1][col-1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p6 = currentPixel;
                try { currentPixel = pixels[row][col-1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p7 = currentPixel;
                try { currentPixel = pixels[row-1][col-1]; } catch (ArrayIndexOutOfBoundsException e) { currentPixel = 0; }
                int p8 = currentPixel;
                
                // compute n
                int n = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
                
                // compute s
                int s = 0;
                if ((p1 == 0) && (p2 == 1)) s++;
                if ((p2 == 0) && (p3 == 1)) s++;
                if ((p3 == 0) && (p4 == 1)) s++;
                if ((p4 == 0) && (p5 == 1)) s++;
                if ((p5 == 0) && (p6 == 1)) s++;
                if ((p6 == 0) && (p7 == 1)) s++;
                if ((p7 == 0) && (p8 == 1)) s++;
                if ((p8 == 0) && (p1 == 1)) s++;
                
                if (pixels[row][col] == 1) {
                    if ((2 <= n) && (n <= 6)) {
                        if (s == 1) {
                            if ((p1 * p3 * p7) == 0) {
                                if ((p1 * p5 * p7) == 0) {
                                    delete[row][col] = true;
                                    retVal = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return retVal;
    }
    
    private static void buildImage() {
        numRows = imageRows.size();
        pixels = new int[numRows][numCols];
        delete = new boolean[numRows][numCols];
        
        for (int row=0; row<numRows; row++) {
            String currentRow = imageRows.get(row);
            for (int col=0; col<numCols; col++) {
                pixels[row][col] = Integer.valueOf(("" + currentRow.charAt(col))).intValue();
            }
        }
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                numCols = inLine.length();
                imageRows.add(inLine);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
