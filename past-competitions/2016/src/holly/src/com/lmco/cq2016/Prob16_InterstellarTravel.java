package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/** 
 * 90 min
 * @author nortoha
 *
 */
public class Prob16_InterstellarTravel {
    private static final String INPUT_FILE_NAME = "Prob16.in.txt";
    
    static int END;
    static Star[] space;
    static int distToEnd;
    static Star curPosition;
    static Star endStar;

    static int myEnergy = 20;
    static int minDistance = Integer.MAX_VALUE;
    
    //debugging
    static ArrayList<Integer> totals = new ArrayList<Integer>();
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob16_InterstellarTravel.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // get dimension of the space we are traveling
                int L = Integer.parseInt(br.readLine());
                
                // destination is L-1
                END = L-1;
                
                // initialize minimum distance from 0,0,0 to END
                // 3 steps for each move diagonally from 0,0,0 to END
                distToEnd = END * 3;
                
                // get the number of stars
                int S = Integer.parseInt(br.readLine());
                
                // create space array with given number of stars plus 1 to account for the end point star
                space = new Star[S+1];
                
                // loop through the star coords
                for (int i=0; i<S; i++) {
                    // read the line of text
                    inLine = br.readLine();

                    // split on delimiter
                    String[] inArray = inLine.split("\\,");
                    
                    int x = Integer.parseInt(inArray[1]);
                    int y = Integer.parseInt(inArray[2]);
                    int z = Integer.parseInt(inArray[3]);
                    
                    Star s = new Star(inArray[0], x, y, z);
                    
                    //put this star into space
                    space[i] = s;
                }
                
                // initialize current position as a Star with no energy
                curPosition = new Star("S", 0, 0, 0);
                curPosition.visited = true;
                
                // initalize the end point as a Star with no energy
                endStar = new Star("S", END, END, END);
                endStar.visited = false;
                endStar.isEnd = true;
                
                space[space.length-1] = endStar;
                
                start();
                
                System.out.println(minDistance);
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void start() {
        
        //reset energy and distance
        myEnergy = 20;
        minDistance = Integer.MAX_VALUE;
        
        if(distToEnd < myEnergy){
            // we win!
            // move to this location and dont need any stars
            System.out.println("Immediate win!");
            System.out.println(distToEnd);
            
        } else {
            
            // goToStar
            goToStar(curPosition, myEnergy, 0);
            
        }
    }
    
    private static void goToStar(Star curStar, int energy, int numStars){

        if(numStars <= space.length-1){
            
            // find reachable stars given my energy
            ArrayList<Star> starList = findReachableStars(curStar, energy);
            
            if(starList.size() > 0 && !starList.isEmpty()){
                
                // if this list contains the end point lets go there
                if(canReachEndStar(starList)){
                    
                    Star endStar = getEndStar(starList);
                    
                    int totalDistance = curStar.distanceTraveled + endStar.distanceToThisStar;
                    
                    if(totalDistance < minDistance){
                        // this is my new best path
                        minDistance = totalDistance;
                    }
                    
                } else {
                    // this list only contains other stars
                    
                    Iterator<Star> itr = starList.iterator();
                    
                    while(itr.hasNext()){
                        Star nextStar = itr.next();
                        
                        // check that we haven't been ther already
                        if(!nextStar.visited){
                            
                            // distance = distance so far + the next move
                            int distanceToThisStar = diff(nextStar.x,curStar.x) + diff(nextStar.y,curStar.y) + diff(nextStar.z,curStar.z);
                           
                            int distance = curStar.distanceTraveled + distanceToThisStar;
                            
                            // does this have the potential to get us to the end within the next move
                            if(distance + nextStar.getDistanceToEndPoint() < minDistance){
                                //pick this one
                                
                                // increment the distance traveled
                                nextStar.distanceTraveled = distance;
                                
                                // adjust my energy
                                myEnergy = (energy - nextStar.distanceToThisStar) + nextStar.energy;
                                
                                // cant be more than 20
                                if(myEnergy > 20){
                                    myEnergy = 20;
                                }
                                
                                // mark this location as visited
                                nextStar.visited = true;
                                
                                // move to this star and continue looking for the end star
                                goToStar(nextStar, myEnergy, numStars+1);
                                
                                // recursion for this path is over, allow this Star to be visited again
                                nextStar.visited = false;
                            }
                        }
                        
                    }
                    
                }
            }
            
        }
        
    }

    private static int diff(int y, int y2) {

        if(y >= y2)
            return y - y2;
        else
            return y2 - y;
    }

    private static ArrayList<Star> findReachableStars(Star curStar, int energy) {
        // search through the space to find stars I can reach with the given energy

        ArrayList<Star> retVal = new ArrayList<Star>();
        
        for(int i=0; i<space.length; i++){
            
            Star s = space[i];
            
            // can only go to stars not already visited
            if(!s.visited && s != curStar){
                
                // how far away
                int dist = (s.x - curStar.x) + (s.y - curStar.y) + (s.z - curStar.z);
                
                // can i make it
                if(dist <= energy){
                    // set the value it takes to reach this star
                    s.distanceToThisStar = dist;
                    // add it to the list
                    retVal.add(s);
                }
                
            }
        }
        
        return retVal;
    }

    private static boolean canReachEndStar(ArrayList<Star> starList) {

        Iterator<Star> itr = starList.iterator();
        
        while(itr.hasNext()){
            Star s = itr.next();
            if(s.isEnd)
                return true;
        }
        
        return false;
    }
    
    private static Star getEndStar(ArrayList<Star> starList){
        Iterator<Star> itr = starList.iterator();
        
        if(canReachEndStar(starList)){
            while(itr.hasNext()){
                Star s = itr.next();
                if(s.isEnd){
                    return s;
                }
            }
        }
        return null;
    }

    public static class Star {
        
        int x;
        int y;
        int z;
        int energy;
        boolean isEnd;
        boolean visited;
        
        int distanceTraveled;
        int distanceToThisStar;
        
        
        public Star(String type, int xPos, int yPos, int zPos){
            this.x = xPos;
            this.y = yPos;
            this.z = zPos;
            
            visited = false;
            isEnd = false;
            
            if(type.equals("M"))
                energy = 3;
            else if(type.equals("H"))
                energy = 4;
            else if(type.equals("G"))
                energy = 5;
            else if(type.equals("F"))
                energy = 6;
            else if(type.equals("A"))
                energy = 7;
            else if(type.equals("B"))
                energy = 8;
            else if(type.equals("O"))
                energy = 9;
            else if(type.equals("S")){
                //fake star for beginning and end
                energy = Integer.MAX_VALUE;
            }
        }
        
        // calculates the distance in light years from this position to the end point
        public int getDistanceToEndPoint(){
            return (END - x) + (END - y) + (END - z);
        }
        
        public String toString(){
            return x + ", " + y + ", " + z;
        }
    }
}
