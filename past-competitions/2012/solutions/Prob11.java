/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


public class Prob11 {
    private static final String INPUT_FILE_NAME = "Prob11.in.txt";
    
    static Integer numCols, numRows;
    
    // values is a HashMap of HashMaps of Integers.  The first key is the column, the second is the row
    static HashMap<String, HashMap<String, Integer>> values = new HashMap<String, HashMap<String, Integer>>();
    
    // for keeping track of the best solution found so far
    static int plotStartX = 0;
    static int plotStartY = 0;
    static int plotEndX = 0;
    static int plotEndY = 0;
    static int maxPlotValue = 0;
    static int maxPlotSize = 0;
    
    public static void main(String[] args) {
        readInput();
        
        int plotSize = 0;
        int plotValue = 0;
        HashMap<String, Integer> column = null;
        Integer value = null;
        
        // iterate through all possibilities of plot sizes
        for (int plotCols=1; plotCols<=numCols; plotCols++) {
            for (int plotRows=1; plotRows<=numRows; plotRows++) {
                plotSize = plotRows * plotCols;
                
                // now iterate through each position of this plot size
                for (int startX=1; startX<=numCols-plotCols+1; startX++) {
                    for (int startY=1; startY<=numRows-plotRows+1; startY++) {
                        // start with 0 value
                        plotValue = 0;
                        
                        // now add the total value of the plot
                        for (int x=startX; x<startX+plotCols; x++) {
                            column = values.get(""+x);
                            if (column != null) {
                                // there is data for this column - look for the rows we want
                                for (int y=startY; y<startY+plotRows; y++) {
                                    value = column.get(""+y);
                                    if (value != null) {
                                        // there is data for this row - add its value to the total
                                        plotValue += value.intValue();
                                    }
                                }
                            }
                        }
                        
                        // compare the plot value to the current best
                        if (plotValue >= maxPlotValue) {
                            boolean replace = false;
                            
                            if (plotValue == maxPlotValue) {
                                // size is the tie breaker if value is equal
                                if (plotSize > maxPlotSize) {
                                    replace = true;
                                }
                            } else {
                                // new value is bigger - always replace
                                replace = true;
                            }
                            
                            if (replace) {
                                // remember this as the best solution so far
                                plotStartX = startX;
                                plotStartY = startY;
                                plotEndX = startX + plotCols - 1;
                                plotEndY = startY + plotRows - 1;
                                maxPlotValue = plotValue;
                                maxPlotSize = plotSize;
                            }
                        }
                    }
                }
            }
        }
        printOutput();
    }
    
    private static void printOutput() {
        System.out.println(plotStartX + "," + plotStartY);
        System.out.println(plotEndX + "," + plotEndY);
        System.out.println("$" + maxPlotValue + "k");
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            boolean firstLine = true;
            
            while ((inLine = br.readLine()) != null) {
                // split using the commas
                String[] tokens = inLine.split(",");
                
                if (firstLine) {
                    // the first line gives us plot information
                    numCols = new Integer(tokens[0]);
                    numRows = new Integer(tokens[1]);
                    firstLine = false;
                } else {
                    // get the land value information
                    String x = tokens[0];
                    String y = tokens[1];
                    Integer value = new Integer(tokens[2]);
                    
                    // get the HashMap for this column
                    HashMap<String, Integer> column = values.get(x);
                    if (column == null) {
                        // no data for this column yet - create a new HashMap
                        column = new HashMap<String, Integer>();
                        values.put(x, column);
                    }
                    
                    // add the value data to this column's HashMap
                    column.put(y, value);
                }
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
