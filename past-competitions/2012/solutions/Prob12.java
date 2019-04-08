/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Prob12 {
    private static final String INPUT_FILE_NAME = "Prob12.in.txt";
    
    static int numRows = 0;
    static int numCols = 0;
    
    // the data will be an ArrayList of ArrayLists of Strings
    static ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    
    public static void main(String[] args) {
        readInput();
        
        // need to keep track if we've modified the data in the current pass
        boolean modified = true;
        
        // what is around us
        String up, down, left, right = null;
        
        // for computing how to prune
        int numPaths = 0;
        int necessaryPaths = 2;
        boolean isBorder = false;
        
        // continue making passes until we don't make a modification to the data
        while (modified) {
            // assume we're done
            modified = false;
            
            // loop through rows and columns
            for (int row=0; row<numRows; row++) {
                for (int col=0; col<numCols; col++) {
                    // get the surrounding data
                    up = ((row == 0) ? null : data.get(row-1).get(col));
                    left = ((col == 0) ? null : data.get(row).get(col-1));
                    down = ((row == (numRows-1)) ? null : data.get(row+1).get(col));
                    right = ((col == (numCols-1)) ? null : data.get(row).get(col+1));
                    
                    // check to see if we're on a border
                    isBorder = false;
                    if ((up == null) || (down == null) || (left == null) || (right == null)) {
                        isBorder = true;
                    }
                    
                    // count the surrounding O's
                    numPaths = 0;
                    numPaths += (("O".equals(up)) ? 1 : 0);
                    numPaths += (("O".equals(down)) ? 1 : 0);
                    numPaths += (("O".equals(left)) ? 1 : 0);
                    numPaths += (("O".equals(right)) ? 1 : 0);
                    
                    // prune when there are less than 2 ways out of this square (1 for borders)
                    boolean prune = false;
                    necessaryPaths = 2;
                    if (isBorder) {
                        necessaryPaths--;
                    }
                    
                    if (numPaths < necessaryPaths) {
                        if (numPaths == 0) {
                            // this is a O surrounded by X's
                            if ("O".equals(data.get(row).get(col))) {
                                prune = true;
                            }
                        } else {
                            // this is a dead end O
                            prune = true;
                        }
                        
                        if (prune) {
                            if ("O".equals(data.get(row).get(col))) {
                                // only set modified if we change a square
                                data.get(row).set(col, "X");
                                modified = true;
                            }
                        }
                    }
                }
            }
        }
        
        // done making modifications - print out the modified picture
        StringBuffer buf = null;
        for (int row=0; row<numRows; row++) {
            // new buffer for each row
            buf = new StringBuffer();
            ArrayList<String> rowData = data.get(row);
            for (int col=0; col<numCols; col++) {
                // add each column's data to the row
                buf.append(rowData.get(col));
            }
            
            // print the row
            System.out.println(buf.toString());
        }
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            String tempString = null;
            
            while ((inLine = br.readLine()) != null) {
                
                // save the number of columns
                if (numCols == 0) {
                    numCols = inLine.length();
                }
                
                // add to number of rows
                numRows++;
                
                // create a new ArrayList for this row and add it to the total data set
                ArrayList<String> rowData = new ArrayList<String>();
                data.add(rowData);
                
                // add each character to this row's data
                for (int i=0; i<inLine.length(); i++) {
                    tempString = "" + inLine.charAt(i);
                    rowData.add(tempString);
                }
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
