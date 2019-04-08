package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * CodeQuest 2014
 * Problem 16: Treasure Hunt
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 * After taking some time to really think about what this question is asking, and even trying to exhaustively
 * step in every direction from every position on the map, I realized that the question is not about HOW to get
 * to the treasure, but how many STEPS did it take.  Think about the map in terms of just torches and Treasure.
 * How can the Hunter jump from torch to torch to get to the Treasure?
 * This is where I came up with the term Leap...
 * First in this solution I read in the file and start processing the map.  I look to see if the Treasure is 
 * already in reach.  But how do I know this???  The method calculateLeapDistancesOnTreasureMap()!!
 * The calculateLeapDistancesOnTreasureMap() method looks between the Hunter and the Treasure, and the Hunter
 * and each torch and calculates the steps to get there (not thinking about light yet! that will come).  After
 * it calculates these ints they are stored in the Location 2D array.  Then the method loops through all the torches
 * on the map doing the same thing...counting steps to other torches and to the Treasure. 
 * So what I end up with is a object (2D array) that contains the number of steps between each torch, hunter, and
 * treasure.  Look at the TreasureHuntNotes.txt in this zip file.
 * 
 * This basically sets up a weighted shortest path problem.  Which can be solved using recursion to exhaustively look at
 * each torch the hunter can go to, and then the next torch, and next, and next, etc. if necessary......Each time checking
 * the distance to the Treasure first to see if you can just sprint there without any torches, and incrementing/decrementing
 * the light the Hunter has.  The light the Hunter has needs to be checked against the number of steps to the torch (from the Location 2D
 * array) to see if you have enough to get there.  Otherwise, you can skip that torch and cut down on the recursion time.
 *  
 * To trim down the execution time and save yourself from out of memory errors, check to see if the next torch in the recursion
 * can actually get the Hunter to the treasure in less steps than you already found in previous iterations.  If not, then why even
 * visit that torch.  This trims the number of recursion cycles and makes it run faster. I call this minDistance in my code below.
 */
public class TreasureHunt {
	
	public static final String FILENAME = "Prob16.in.txt";  
//	public static final String FILENAME = "SecondSimpleTH.in.txt";
//	public static final String FILENAME = "SimpleTH.in.txt";    
//	public static final String FILENAME = "HardTH.in.txt";     
//	public static final String FILENAME = "SuperHardTH.in.txt"; 
//	public static final String FILENAME = "LotsOfTorchesTH.in.txt";
//	public static final String FILENAME = "FourStepsTH.in.txt"; 
//	public static final String FILENAME = "UltraHardTH.in.txt"; 
//	public static final String FILENAME = "TreasureHunt.in.txt";  //JUDGING
	
	Location[][] map;
	Location treasure;
	Location hunter;
	Location curLocation;
	int numberOfTorches;
	ArrayList<Integer> totals;
	int minDistance;
	
	public TreasureHunt(String fileName){

		this.map = readFile(fileName);
		
		this.treasure = findTreasure();
		
		this.hunter = findHunter();
		
		//contain a list of distance totals to the treasure, this is used for debugging, not for the answer
		this.totals = new ArrayList<Integer>();  

		this.minDistance = 0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{

			long time = System.currentTimeMillis();
			
			//Initialize a TreasureHunt, which loads the file and initializes some fields (above)
			TreasureHunt th = new TreasureHunt(FILENAME);  
			
			//Go!
			th.startHunting();
			
			//Determining the time it took for debugging purposes
			double d = (System.currentTimeMillis() - time);
			
			System.out.println( d/1000 + " seconds");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * GO!
	 */
	public void startHunting(){
		
		try{
			//The term Leap in this solution refers to the concept of just moving from torch to torch,
			//or leaping.  You don't need to worry about stepping on each space.  Only the torches, hunter, and treasure matter
			//This will significantly reduce the program execution time.
			calculateLeapDistancesOnTreasureMap();
			
			//check if we can just sprint there directly, no torches
			if(hunter.distanceToTreasure()<=15){
				//we can!
				minDistance = hunter.distanceToTreasure();
				
				System.out.println(hunter.distanceToTreasure());
				
			} else{
				
				//start at this spot with 15
				hunter.lightAtTorch = 15; 
				
				//recursively until find the Treasure
				leapToTorch(hunter, 0);
				
				System.out.println(minDistance + "");
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * RECURSION!
	 * this search needs to be exhaustive otherwise you cannot be sure you traveled the shortest distance
	 * but we do not have to compute any distances since we already computed them prior to this.
	 * The hunter should only be concerned about the Treasure, first, and torches.  The spaces are irrelevant because
	 * we don't need to compute the actual path, just the cost to get there.  So, our map of distances we computed earlier
	 * plays a key role in this recursion.
	 * @param l
	 */
	private void leapToTorch(Location l, int num){
		
		if(num<=numberOfTorches){
		
			//get possible leaps from this location
			ArrayList<Leap> leaps = l.getReachableLeaps();
			
			if(leaps.size()>0 && !leaps.isEmpty()){
				
				//Can I just go directly to the treasure without anymore torches, a.k.a is the Treasure in this ArrayList			
				if(containsTreasure(leaps)){
					
					Leap toTreasure = getTreasure(leaps);
					
					//add the steps where I came from plus last leap distance to Treasure
					int totalDistance = toTreasure.from.distanceTraveled + toTreasure.getDistance();
					
					//this is just for keeping track of all the paths to Treasure, only need to minimum to print though
					totals.add(totalDistance);
					
					if(totalDistance < minDistance){
						//this is a new best path
						minDistance = totalDistance;
						System.out.println("MinDistance= " + minDistance);
					}
					
				} else{
					//This ArrayList<Leaps> only contains torches as destinations
					Iterator<Leap> itr = leaps.iterator();
					
					while(itr.hasNext()){
						Leap leap = itr.next();
						
						//check if its been visited yet 
						if(!leap.destination.visited){
							
							//distance = steps you taken to get to this location + the distance you are about to leap
							int distance = leap.from.distanceTraveled + leap.getDistance();
						
							//if we take this torch will this torch provide us with a new min distance to the treasure, if not, why go here
							//we can check this before we actual make this move to the torch to reduce recursion time
							int distanceFromThisLeapToTreasure = leap.destination.getLeapDistanceToTreasure();
							
							//if this Leap distanceTraveled is already greater than the minDistance, don't bother trying this torch
							if( (distance + distanceFromThisLeapToTreasure)  < minDistance){
								//Hunter is going to move to this torch
								//Need to modify member variables for the Location the hunter is moving to
								
								//Increment the distance traveled
								leap.destination.distanceTraveled = distance;
								
								//Adjust the light for the new Location		
								leap.destination.lightAtTorch=(leap.from.lightAtTorch - leap.distance) + 15;
								
								//Mark the Location the hunter is leaving as visited so he doesn't come back here
								l.visited = true;
	
								//Move the hunter to the Location and continue to look for Treasure or torches
								leapToTorch(leap.destination, num+1);
								
								//recursion for this path is over, allow this Location to be visited again
								l.visited = false;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Returns true if the list contains a Location where .isTreasure() is true
	 * @param leaps
	 * @return
	 */
	private boolean containsTreasure(ArrayList<Leap> leaps) {
		
		if(leaps!=null){
			Iterator<Leap> itr = leaps.iterator();
			while(itr.hasNext()){
				Leap l = itr.next();
				if(l.destination.isTreasure()){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Loops through the list of Leaps and returns the Leap where the destination is the treasure
	 * @param leaps
	 * @return
	 */
	private Leap getTreasure(ArrayList<Leap> leaps) {
		
		if(containsTreasure(leaps)){
			if(leaps!=null){
				Iterator<Leap> itr = leaps.iterator();
				while(itr.hasNext()){
					Leap l = itr.next();
					if(l.destination.isTreasure()){
						return l;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * The map object is an array of Location objects. It contain every index including spaces.
	 * The key is to disregard the spaces and leap from torch to torch,  but we need the spaces
	 * in this method to determine the distance, or cost of each leap.
	 * So lets calculate these possible distances:
	 * from Hunter to each torch and to treasure
	 * from each torch to every other torch
	 * from each torch to the treasure
	 * 
	 * Now at any point in time I can ask the map Array how far it is from any torch
	 */
	private void calculateLeapDistancesOnTreasureMap() {
		
		//a list of all the torches on the map
		ArrayList<Location> torches = findTorches();
		numberOfTorches = torches.size();
		
		//get all the distances starting at the hunter Location object
		findDistancesFromThisPosition(hunter.rowIdx, hunter.colIdx, 1);
		
		//uncomment this next line to see what the map looks like now that the steps from the Hunter have been calculated
//		printMap();
		
		//add all the torches and T to the Hunters ArrayList<Leaps>
		hunter.addLeap(treasure, treasure.stepNum);
		minDistance = treasure.stepNum+1;
		//torches
		for(int i=0; i<torches.size(); i++){
			Location torch = torches.get(i);
			//only adding the ones that are reachable at the beginning?
			if(torch.stepNum>0 && torch.stepNum<=15)
				hunter.addLeap(torch, torch.stepNum);
			minDistance+=torch.stepNum;
		}
		
		/* So now the hunter Location object has the cost to each torch and the cost to the Treasure
		 * Now we need to find the distances from each torch to the other torches 
		 */
		int torchCount = torches.size();
		//for each torch on the map
		for(int cnt=0; cnt<torchCount; cnt++){
			//reset map
			resetMap();  //remove all the distances from before
			
			Location torch = torches.get(cnt);

			//map the steps from this torch
			findDistancesFromThisPosition(torch.rowIdx, torch.colIdx, 1);

			//add distance to Treasure
			torch.addLeap(treasure, treasure.stepNum);
			
			//add distances to other torches
			for(int i=0; i<torches.size(); i++){
				Location t = torches.get(i);
				if(torch!=t && t.stepNum>0){ //don't add it to the Collection if you can't get from torch to t
					//don't map to itself
					torch.addLeap(t, t.stepNum);
				}
			}
			
			//debugging
//			torch.printLeaps();  //show me the distances I can travel from this torch
		}
		printMap();
	}
	
	/**
	 * Helper method to find the all the torches on the map 
	 * Loops through the map array and adds each Location where .isTorch() is true
	 * @return
	 */
	private ArrayList<Location> findTorches(){
		ArrayList<Location> retVal = new ArrayList<Location>();
		
		if(this.map!=null){
			//loop through this array till we find t
			
			for(int row=0; row<map.length; row++){
				
				for(int col=0; col<map[row].length; col++){
					
					if(map[row][col].isTorch())
						retVal.add(map[row][col]);
				}
			}
		}
		return retVal;
	}
	
	/**
	 * Helper method to find the hunter Location
	 * Loops through the map array until .isHunter() is true
	 * @return
	 */
	private Location findHunter(){
		
		if(this.map!=null){
			//loop through this array till we find H
			
			for(int row=0; row<map.length; row++){
				
				for(int col=0; col<map[row].length; col++){
					
					if(map[row][col].isHunter())
						return map[row][col];
				}
			}
		}
		
		//something strange happend and there is no H
		return null;
	}
	
	/**
	 * Helper method to find the Treasure Location
	 * Loops through the map array until .isTreasure() is true
	 * @return
	 */
	private Location findTreasure(){
		
		if(this.map!=null){
			//loop through this array till we find T
			
			for(int row=0; row<map.length; row++){
				
				for(int col=0; col<map[row].length; col++){
					
					if(map[row][col].isTreasure())
						return map[row][col];
				}
			}
		}
		
		//something strange happened and there is no T
		return null;
	}
	
	/**
	 * Loops through the map array and returns an ArrayList of Locations
	 * that match the given number of steps
	 * @param stepNumber
	 * @return
	 */
	private ArrayList<Location> findLocationsByStepNumber(int stepNumber){
		ArrayList<Location> retVal = new ArrayList<Location>();
		if(this.map!=null){
			//loop through this array till we find stepNumber
			
			for(int row=0; row<map.length; row++){
				
				for(int col=0; col<map[row].length; col++){
					
					if(map[row][col].stepNum==stepNumber)
						retVal.add(map[row][col]);
				}
			}
		}
		
		return retVal;
	}
	
	
	/**
	 * Resets the stepNum for all Location objects in the map array
	 * so that new number of steps can be calculated
	 */
	public void resetMap(){
		
		if(this.map!=null){
			//loop through this array till we find H
			
			for(int row=0; row<map.length; row++){
				
				for(int col=0; col<map[row].length; col++){
					
					map[row][col].stepNum=0;
				}
			}
		}
	}
	
	/**
	 * Reads the input file as an ArrayList of Strings, then loops through the indexes 
	 * creating a Locatoin object for each.  We end up with a 2-dimensional array of Location
	 * objects.
	 * @param fileName
	 * @return
	 */
	private Location[][] readFile(String fileName){
		
		ArrayList<String> temp = new ArrayList<String>();
		
		try{
			//read file
			InputStream in = TreasureHunt.class.getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			//loop through lines of input file
			String s;
			while((s = br.readLine()) != null) { 
				
				temp.add(s);
			}
			
			int width = temp.get(0).length();
			int height = temp.size();
			//convert to array because I like to deal with these
			Location[][] retVal = new Location[height][width];
			//now fill the array
			for(int row=0; row<height; row++){
				String string = temp.get(row);
				//now loop through each character and store in the 2D array
				for(int j=0; j<string.length(); j++){
					Location l = new Location(row, j, string.charAt(j)+"");
					retVal[row][j]= l;
				}
			}
			
			return retVal;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Starting at the given indexes this method will set a number of steps to each Location
	 * relative to the starting Location. This distance is used by each torch and the treasure so
	 * that we can know the distance between any torch and another torch and the treasure
	 * @param rowIdx
	 * @param colIdx
	 * @param startCounter
	 */
	private void findDistancesFromThisPosition(int rowIdx, int colIdx, int startCounter){
		
		mapStepsFromPosition(rowIdx, colIdx, 1);
		
		boolean keepSearching = true;
		int counter=1;
		//max number of steps in one direction is the dimensions of the map multiplied together
		//as if the hunter had to step on each square
		while(counter< (map.length*map[0].length) && (keepSearching)){
			ArrayList<Location> locations = findLocationsByStepNumber(counter);
			if(locations!=null && !locations.isEmpty()){
				//map these
				for(int i=0; i<locations.size(); i++){
					Location l = locations.get(i);
					if(l.isTreasure()){
						//stop...we are already there
						this.treasure = l;
						keepSearching = false;
//						counter=(map.length*map[0].length);
					}else{
						mapStepsFromPosition(l.rowIdx, l.colIdx, counter+1);
					}
				}
//				printMap();
			}
			
			counter++;
		}
		
//		printMap();
	}
	
	/**
	 * The method starts at the given index and goes out in each direction until
	 * it reaches a barrier (see Location.isValid())
	 * @param rowIdx
	 * @param colIdx
	 * @param startCounter
	 */
	private void mapStepsFromPosition(int rowIdx, int colIdx, int startCounter){
		
		//from index going down on the map
		int counter = startCounter;
		//minus 2 here instead of 1 because the last line of the map is all dashes and not real moves
		if(rowIdx<map.length-2){
			for(int row=rowIdx+1; row<map.length; row++){
				Location l = map[row][colIdx];
				if(l.isValid() && l.stepNum==0){
					l.stepNum=counter;
					counter++;
				} else{
					//can't go any further in this direction.
					//exit loop by setting row to map.length
					row = map.length;
				}
			}
		}
		
		//from index going up on the map
		counter = startCounter; //reset
		if(rowIdx>1){
			for(int row=rowIdx-1; row>=0; row--){
				Location l = map[row][colIdx];
				if(l.isValid() && l.stepNum==0){
					l.stepNum=counter;
					counter++;
				} else{
					//can't go any further in this direction.
					//exit loop by setting row to -1
					row = -1;
				}
			}
		}
		//from Hunter looking right
		counter = startCounter;
		if(colIdx<map[0].length-2){
			for(int col=colIdx+1; col<map[0].length; col++){
				Location l = map[rowIdx][col];
				if(l.isValid() && l.stepNum==0){
					l.stepNum=counter;
					counter++;
				} else{
					//can't go any further in this direction.
					//exit loop by setting col to map.length
					col = map[0].length;
				}
			}
		}
		
		//from Hunter looking left
		counter = startCounter;
		if(colIdx>1){
			for(int col=colIdx-1; col>=0; col--){
				Location l = map[rowIdx][col];
				if(l.isValid() && l.stepNum==0){
					l.stepNum=counter;
					counter++;
				} else{
					//can't go any further in this direction.
					//exit loop by setting col to -1
					col = -1;
				}
			}
		}
		
//		printMap();
	}
	
	/**
	 * Used for debugging when I wanted to see the map
	 */
	public void printMap(){
		
		for(int i=0; i<map.length; i++){
			for(int j=0; j<map[i].length; j++){
				String s = map[i][j].value;
				if(s.trim().length()==0)
					s = map[i][j].stepNum + "";
				System.out.print(s + "   ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * Inner class to store location data on the map
	 * @author nortoha
	 *
	 */
	public class Location{
		
		int rowIdx;
		int colIdx;
		String value;
		boolean visited;
		int stepNum;
		int lightAtTorch;
		int distanceTraveled;
		ArrayList<Leap> leaps;
		
		public Location(int row, int col, String v){
			this.rowIdx = row;
			this.colIdx = col;
			this.value= v;
			this.visited = false;
			this.leaps = new ArrayList<Leap>();
		}
		public int getLeapDistanceToTreasure() {
			if(leaps!=null && !leaps.isEmpty()){
				//loop through till find the treasure and return distance
				for(int i=0; i<leaps.size(); i++){
					Leap l = leaps.get(i);
					if(l.destination.isTreasure())
						return l.getDistance();
				}
			}
			//return zero if there is no Leap to the Treasure
			return 0;
		}
		public void addLeap(Location to, int d){
			Leap l = new Leap(this, to, d);
			this.leaps.add(l);
		}
		public ArrayList<Leap> getLeaps(){
			//only return the list if this Location has not been visited yet
			if(!visited)
				return leaps;
			else
				return null;
		}
		public ArrayList<Leap> getReachableLeaps(){
			ArrayList<Leap> temp = getLeaps();
			ArrayList<Leap> retVal = new ArrayList<Leap>();
			//loop through and return ones with in steps
			for(int i=0; i<temp.size(); i++){
				Leap l = temp.get(i);
				if(l.getDistance()>0 && l.getDistance()<=lightAtTorch  && !l.destination.isHunter() && !l.destination.visited && (l.from.distanceTraveled + l.getDistance()<minDistance)){
					retVal.add(l);
				}
			}
			//sorting the list does not affect the functionality.  it was an attempt to shorten exec time
			//sorting is optional...it actually had little affect on execution time.  Try running this both ways: sorted and unsorted
			Collections.sort(retVal, new LeapComparator());
			return retVal;
		}
		public int distanceToTreasure(){
			for(int i=0; i<leaps.size(); i++){
				Leap l = leaps.get(i);
				if(l.destination.isTreasure())
					return l.getDistance();
			}
			return -1;
		}
		public void printLeaps(){
			if(!visited){
				for(int i=0; i<leaps.size(); i++){
					System.out.println("Leap " + leaps.get(i));
				}
			}
		}
		public boolean isTorch(){
			if(value.equals("t"))
				return true;
			return false;
		}
		public boolean isTreasure(){
			if(value.equals("T"))
				return true;
			return false;
		}	
		public boolean isHunter(){
			if(value.equals("H"))
				return true;
			else
				return false;
		}
		public boolean isValid(){
			if(this.value.equals("|"))
				return false;  //boundary
			if(this.value.equals("-"))
				return false;  //boundary
			if(this.value.equals("x"))
				return false;  //wall
			
			return true;
		}
		public void resetVisited(){
			this.visited=false;
		}
		public String toString(){
			return "("+value+","+rowIdx+","+colIdx+")";
		}

	}
	
	/**
	 * The Leap class is used by a Location to store the possible destinations the Location can move toward
	 * In this solution Leaps will only be torches.  This will significantly reduce processing time.
	 * To see how this class is instantiated see calculateLeapDistancesOnTreasureMap()
	 * @author nortoha
	 *
	 */
	public class Leap {
		
		Location from;
		Location destination;
		int distance;
		
		public Leap(Location f, Location dest, int d){
			from = f;
			destination = dest;
			distance = d;
		}
		
		public int getDistance(){
			return distance;
		}
		public String toString(){
			return "("+distance+")" + from + "-->" + destination;
		}

	}
	
	/**
	 * This Comparator is optional and had differing affect on the execution time.
	 * The intent was to try to prioritize the list of Leaps by distance so that the shorter 
	 * paths are evaluated first.  However, in this problem the recursion has to be exhaustive to 
	 * guarantee a minimum answer.  But, once you find the first minDistance the time really speeds 
	 * up.
	 * @author nortoha
	 *
	 */
	public class LeapComparator implements Comparator<Leap>{

		@Override
		public int compare(Leap l1, Leap l2) {
			if(l1.getDistance()==l2.getDistance())
				return 0;
			if(l1.getDistance()<l2.getDistance())
				return -1;
			return 1;
		}
		
	}
}
