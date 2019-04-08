package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author nortoha
 *
 */
public class Prob17_Honeycomb {
    private static final String INPUT_FILE_NAME = "Prob17.in.txt";
    
    static ArrayList<Hexagon[]> beehive;
    static Stack<Hexagon> reevaluate;
    
    public static void main(String[] args) {
        
        try {
            // prepare to read the file
            InputStream in = Prob17_Honeycomb.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                beehive = new ArrayList<Hexagon[]>();
                beehive.clear();
                
                // stack used for store Hexagons that need to be reevaluated during the loop
                reevaluate = new Stack<Hexagon>();
                
                // get the number of rows in each test case
                int numRows = Integer.parseInt(br.readLine());
                
                // store each node of the beehive triangle into an ArrayList
                for(int i=0; i<numRows; i++){
                    // read the line of text
                    inLine = br.readLine();
                    
                    // split on the comma 
                    String[] inArray = inLine.split("\\,");
                    
                    // initialize array
                    Hexagon[] hArray = new Hexagon[inArray.length];
                    
                    // create Hexagon objects and add to beehive
                    for(int j=0; j<inArray.length; j++){
                        Hexagon h = new Hexagon(Integer.parseInt(inArray[j]), i, j);

                        //add it to array
                        hArray[j] = h;
                    }
                    
                    // add it to the beehive
                    beehive.add(hArray);
                }

                int minTime = 0;

                // 3 sides to this triangle so we have to do this 3 times
                for(int i=0; i<3; i++){
                    travelHive();
                    minTime += beehive.get(beehive.size()-1)[beehive.size()-1].shortestPath;  // last index
                    // rotate triangle and do it again
                    rotateBeehiveCounterClockwise();
                }

                System.out.println(minTime);
                
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private static void travelHive(){
        
        // set the start node travel time, dont accrue the time at this spot
        beehive.get(0)[0].shortestPath = 0;
     
        for(int row=0; row<beehive.size(); row++){
            
            for(int pos=0; pos<beehive.get(row).length; pos++){
                
                Hexagon hex = beehive.get(row)[pos];
                
                Iterator<Hexagon> hexItr = getNeighbors(row, pos).iterator();
                
                // for each neighbor
                while(hexItr.hasNext()){
                    
                    Hexagon n = hexItr.next();
                    
                    // time is shortestePath already calculated plus the time spent at this neighbor
                    int travelTime = hex.shortestPath + n.time;
                    
                    // check if this is the new shortestPath for this neighbor
                    if(travelTime <= n.shortestPath){
                        
                        // check if this is the first time this neighbors shortestPath has been changed
                        if(n.shortestPath != Integer.MAX_VALUE){
                            // need to check this nodes neighbors again so push it to the stack for later
                            reevaluate.push(n);
                        }
                        
                        // set new shortestPath time for this neighbor
                        n.shortestPath = travelTime;
                    }
                    
                }
                
                // check to see if anything needs to be reevaluated as a result of evaluating this hex
                while(!reevaluate.isEmpty()){
                    
                    Hexagon h = reevaluate.pop();
                    
                    //get neighbors
                    Iterator<Hexagon> nItr = getNeighbors(h.row, h.pos).iterator();
                    
                    while(nItr.hasNext()){
                        
                        Hexagon n = nItr.next();
                        
                        int travelTime = h.shortestPath + n.time;
                        
                        // did we beat our best path
                        if(travelTime < n.shortestPath){
                            // new best path
                            n.shortestPath = travelTime;
                            reevaluate.push(n);
                        }
                        
                    }
                }
            }
        }
    }

    
    
    private static ArrayList<Hexagon> getNeighbors(int row, int pos){
        
        ArrayList<Hexagon> retVal = new ArrayList<Hexagon>();
        
        // make sure to check your boundaries
        if(pos > 0){
            retVal.add(beehive.get(row)[pos-1]);
            retVal.add(beehive.get(row-1)[pos-1]);
        }
        // check that i am not on the last row
        if(pos < beehive.get(row).length-1){
            retVal.add(beehive.get(row)[pos+1]);
            retVal.add(beehive.get(row-1)[pos]);    
        }
        
        if(row < beehive.size()-1){
            retVal.add(beehive.get(row+1)[pos]);
            retVal.add(beehive.get(row+1)[pos+1]);
        }
        
        
        return retVal;
    }
    
    
    private static void printBeehive(){
        
        for(int row=0; row<beehive.size(); row++){
            for(int pos=0; pos<beehive.get(row).length; pos++){
                System.out.print(beehive.get(row)[pos].time + "   ");
//                System.out.print(beehive.get(row)[pos].time + "(" + beehive.get(row)[pos].shortestPath + ") ");
            }
            System.out.println();
        }
    }
    
    private static void rotateBeehiveCounterClockwise(){
        
        ArrayList<Hexagon[]> temp = new ArrayList<Hexagon[]>();
        
        int tempIdx = 0;
        
        // init this index to the size of the original array
        int beehivePos = beehive.size()-1;
        
        //for each row in the original beehive we are going to add a new row of same length to the temp
        for(int row=0; row<beehive.size(); row++){
            
            // init this index to the size of this row
            int beehiveRow = beehive.get(beehivePos).length-1;
            
            // init a new array to be the same length at this row
            Hexagon[] hArray = new Hexagon[beehive.get(row).length];
            
            for(int pos=0; pos<beehive.get(row).length; pos++){
                Hexagon h = beehive.get(beehiveRow)[beehivePos];

                // reset
                h.shortestPath = Integer.MAX_VALUE;
               
                // set new row and pos 
                h.row = tempIdx;
                h.pos = pos;
               
                //decrement 
                beehiveRow++;
                
                //add it to the array at pos index
                hArray[pos] = h;
            }
            //decrement
            beehivePos--;
            
            //add it to temp
            temp.add(hArray);
            tempIdx++;
        }
        
        // clear beehive and add all
        beehive.clear();
        beehive = null;
        beehive = new ArrayList<Hexagon[]>();
        beehive.addAll(temp);
                      
        
    }
    

    private static class Hexagon {
        
        int row;
        int pos;
        int time;
        int shortestPath = Integer.MAX_VALUE; //initialize to really large
        
        public Hexagon(int t, int r, int p){
            this.time = t;
            this.row = r;
            this.pos = p;
        }
        
        public String toString(){
            return String.valueOf("Node: " + time + " shortestTime: " + shortestPath);
        }
    }
}
