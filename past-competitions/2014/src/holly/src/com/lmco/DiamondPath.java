package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * CodeQuest 2014
 * Problem 13: Diamond Path
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 * I see this problem in two parts: (1) figure out how to read the file and (2) do a weighted path exhaustive search.
 * The key thing you have to do in this problem is figure out how to read the file.  This is more complicated
 * than the paths.  Because the file doesn't provide you with the actual diamond you have to build the diamond based
 * on the paths that exist in the file.  In my approach i had to loop through the input data twice.  Once to count
 * and instantiate all the Nodes that will exist and a second time to build the paths, with weights, between the nodes.
 * Be careful as you loop through.  You will need to keep track of how many paths are on the NEXT line of data so that you know
 * when the diamond has reached its max width and will start descending in shape.  And therefore, the nodes will have fewer paths on the edges
 * and edge+1 and edge-1.
 * Once you build the diamond its just a weighted path problem.  
 *
 * I FIRST GOT AN OUT OF MEMORY EXCEPTION ON THIS PROBLEM SO I HAD TO SET MY ARGUMENTS AS:
 * -Xms1024M
 * -Xmx1024M
 */
public class DiamondPath {

	public static final String FILENAME = "Prob13.in.txt";
	
	ArrayList<Node> nodes;
	ArrayList<Node[]> diamond;
	ArrayList<String> input;
	ArrayList<Integer> allPathTotals;
	
	
	public DiamondPath(){
		nodes = new ArrayList<Node>();
	}
	
	public DiamondPath(String inputFile){
		nodes = new ArrayList<Node>();
		diamond = new ArrayList<Node[]>();
		allPathTotals = new ArrayList<Integer>();
		
		//load the file  
		input = loadData(inputFile);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DiamondPath dp = new DiamondPath(FILENAME);
		
		try{
			//create all nodes based on input lengths
			dp.makeNodes();
			
			//debugging, uncomment this line if you want to see what the diamond looks like in the console as this point
//			dp.printDiamond();
			
			//loop through the Node[]s in the diamond and use the input file to assign weights to Edges between Nodes
			dp.makeEdgesBetweenNodes();
			
			//debugging, uncomment this line to see what the diamond looks like after the Edges have been added
//			dp.printDiamondPaths();

			//Now use recursion to search through the diamond that was just created to find the shortest path
			Node root = dp.diamond.get(0)[0];
			
			//Find all possible paths through the diamond, top to bottom, root to root, then add them up
			//We have to find ALL paths because the we have to output the number of times the shortest path occurs.
			//Start at root
			dp.addUpDiamondPaths(root, 0);
			
			//print output
			dp.printOutput();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	private void makeEdgesBetweenNodes() {

		try{
			//then loop through nodes and add Edges(LL) to them		
			int loopCount=0;
			
			while(loopCount < diamond.size()){
				
				Node[] n = diamond.get(loopCount);
				int nLength = n.length;
				
				//check gettingBigger, don't check on the last line, will get IndexOutOfBounds
				boolean gettingBigger = false;  //assume false
				
				//check if not the last row of diamond nodes. Don't process the last row because the last Node has no more paths
				if(loopCount < diamond.size()-1){
					
					//get the next row of diamond nodes. This will be used to create the paths between Nodes in the diamond
					Node[] nextRow = diamond.get(loopCount+1);
					
					//set gettingBigger boolean based on the length of the rows
					if(nextRow.length > nLength){
						gettingBigger = true;
					}else{
						gettingBigger = false;
					}
					
					//using the input file ArrayList for a second pass 
					//add paths from input file to this node
					String input = this.input.get(loopCount);
					
					String[] inputArray = input.split(" ");
					
					//counter for the number of paths on this input line.  We will need to know how many to assign to each node
					int pathsCount = 0;  
					
					//for all nodes on this line...
					for(int i=0; i<nLength; i++){
						//for this node, add paths including the Nodes that they lead to on the next line (nextRow[])
						Node node = n[i];  

						if(gettingBigger){
							//Getting bigger side of the diamond
							//Will have 3 children, starting at nextRow[curIndex] then +1, then +2.
							
							//The inputArray contains the weights needed to create the new Edge.
							//The destination of this Edge is the nextRow[]
							//The index to nextRow[] has to be relative to i so that you get the 3 paths for the Node above (node)
							node.addPath(0, new Edge(Integer.parseInt(inputArray[pathsCount]), nextRow[i]));
							node.addPath(1, new Edge(Integer.parseInt(inputArray[pathsCount+1]), nextRow[i+1]));
							node.addPath(2, new Edge(Integer.parseInt(inputArray[pathsCount+2]), nextRow[i+2]));
							//increment counter +3 to skip to the next paths in the input data
							pathsCount=pathsCount+3;
							
						} else{
							//getting smaller, don't get 3 paths each time:  The number of paths is dependent on the length of the line
							
							if(nLength==3){
								//second to last line of nodes in the diamond, assign each node 1 path from the input data
								//they all use the bottom root node as the destination in this case, 
								node.addPath(0, new Edge(Integer.parseInt(inputArray[pathsCount]), nextRow[0]));
								//increment
								pathsCount=pathsCount+1;
								
							} else {
								//All other lines on the descending side of the diamond
								//The number of paths (Edge) is based on the position of the Node in the diamond (i)
								
								if(i==0){
									//first node gets 1 path
									node.addPath(0, new Edge(Integer.parseInt(inputArray[pathsCount]), nextRow[i]));	
									pathsCount=pathsCount+1;
									
								} else if(i==1){
									//second token gets 2 paths
									node.addPath(0, new Edge(Integer.parseInt(inputArray[pathsCount]), nextRow[0]));
									node.addPath(1, new Edge(Integer.parseInt(inputArray[pathsCount+1]), nextRow[i]));	
									//skip over 2
									pathsCount=pathsCount+2;
									
								} else if(i==nLength-1){
									//last node gets 1 path
									//assigned to the last node in the nextRow
									node.addPath(0, new Edge(Integer.parseInt(inputArray[pathsCount]), nextRow[nextRow.length-1]));
									pathsCount=pathsCount+1;
									
								} else if(i==nLength-2){
									//second to last gets 2 paths
									//subtract 2 because there are 2 less nodes on the descending line
									node.addPath(0, new Edge(Integer.parseInt(inputArray[pathsCount]), nextRow[nextRow.length-2]));  
									node.addPath(1, new Edge(Integer.parseInt(inputArray[pathsCount+1]), nextRow[nextRow.length-1]));
									//skip over 2
									pathsCount=pathsCount+2;
									
								} else {
									//three paths in the middle
									//subtract 2 because there are 2 less nodes on the descending line
									node.addPath(0, new Edge(Integer.parseInt(inputArray[pathsCount]), nextRow[i-2]));    
									node.addPath(1, new Edge(Integer.parseInt(inputArray[pathsCount+1]), nextRow[i-1]));
									node.addPath(2, new Edge(Integer.parseInt(inputArray[pathsCount+2]), nextRow[i]));
									//skip over 3 to the next path weights
									pathsCount=pathsCount+3;
								}
							}
							
						}
					}
					
					
				} else{
					//on last line, no paths no this node
					;
				}
				
				//increment loop counter
				loopCount++;
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Loops through the allPathsTotals ArrayList and counts how many times the lowest path occurs
	 * Then it formats the output and prints to the console
	 */
	private void printOutput(){
		
		//find lowest
		int lowest = 0;
		
		Iterator<Integer> itr = this.allPathTotals.iterator();
		while(itr.hasNext()){
			Integer i = itr.next();
			
			if(lowest==0){
				lowest=i;
			} else{
				if(i<lowest){
					//found new lowest
					lowest=i;
				}
			}
		}
		System.out.println("Lowest path difficulty: "+lowest);
		
		//now loop through to see how many times the lowest sum exists
		int count = 0;
		
		itr = this.allPathTotals.iterator();
		while(itr.hasNext()){
			Integer i = itr.next();
			if(i==lowest)
				count++;
		}
		System.out.println("Number of paths with the lowest difficulty: "+count);
		
	}

	
	/*
	 * DEBUGGING
	 */
	private void printDiamond() {

		if(this.diamond!=null && !this.diamond.isEmpty()){
			
			for(int i=0; i<diamond.size(); i++){
				Node[] n = diamond.get(i);
				for(int j=0; j<n.length; j++){
					Node node = n[j];
					System.out.print(node + "  ");
				}
				System.out.println("");
			}
		}
	}

	/*
	 * DEBUGGING
	 */
	private void printDiamondPaths() {

		if(this.diamond!=null && !this.diamond.isEmpty()){
			
			for(int i=0; i<diamond.size(); i++){
				Node[] n = diamond.get(i);
				for(int j=0; j<n.length; j++){
					Node node = n[j];
					System.out.print(" " + node + " ");
				}
				System.out.println("");
				for(int j=0; j<n.length; j++){
					Node node = n[j];
					System.out.print(node.printPaths()+ " ");
				}
				System.out.println("");
			}
		}
	}
	
	
	/**
	 * Recursively add up the paths through the diamond and keep track of the total sums
	 * @param n
	 * @param runningSum
	 */
	private void addUpDiamondPaths(Node n, int runningSum) {

		if(n.hasChildren()){
			
			Iterator<Edge> itr = n.getPaths();
			while(itr.hasNext()){
				Edge e = itr.next();
				addUpDiamondPaths(e.getDestNode(), e.pathWeight+runningSum);
			}
			
		}else{
			//no more children, at the end.  add to total sum object
			addToTotals(runningSum);
		}

	}
	
	/**
	 * Adds the given int to the allPathTotals list
	 * @param sum
	 */
	private void addToTotals(int sum){
		this.allPathTotals.add(new Integer(sum));
	}
	
	/**
	 * This method will loop through the lines of the input file and create
	 * the correct amount of Node objects as a Node array for each line in the diamond
	 * The Node arrays will be added to the diamond object.
	 * This is the first pass a second pass through the file will assign Edges to each Node
	 */
	public void makeNodes() {

		//make first one
		Node[] n0Array = new Node[1];
		n0Array[0]= new Node(0);
		this.diamond.add(n0Array);
		
		int lineCount = 0;
		int nodeName = 1;
		//keep track of the length of the previously read line so that we can tell if the diamond is getting bigger or smaller 
		int prevLineLength=0;
		
		//now loop through the rest of the lines and determine the number of nodes for each line
		while(lineCount<this.input.size()){
			
			String line = this.input.get(lineCount);
			
			String[] lineArray = line.split(" ");
			
			if(lineCount==0){
				//first line are nodes as well as paths from the root n0 above, so take care of this one differently
				int numNode = 3;
				
				//create this many nodes and add them to diamond object
				Node[] temp = new Node[numNode];
				
				for(int i=0; i<numNode; i++){
					Node n = new Node(nodeName);
					temp[i] = n;
					nodeName++;
				}
				
				this.diamond.add(temp);
				
			} else if(lineCount==this.input.size()-1) {
				//last line, create the last node, just 1 
				Node[] temp = new Node[1];
				temp[0]= new Node(nodeName);
				this.diamond.add(temp);
				
			} else{
				//Not at the beginning and not at the end
				
				int lineLength = lineArray.length;
				
				if(prevLineLength==lineLength){
					//Starting to descend because the lengths are the same, this is the max width of the diamond
					//create fewer nodes: calculate numNode differently this time
					//check the number of nodes in the last line and create 2 less
					int curSizeOfDiamond = this.diamond.size();
					
					//get the last one
					Node[] longestNodes = this.diamond.get(curSizeOfDiamond-1);  
					
					//start descending in size now by removing two nodes
					int numNode = longestNodes.length-2;  
					
					//create this many nodes and add them to diamond object
					Node[] temp = new Node[numNode];
					for(int i=0; i<numNode; i++){
						Node n = new Node(nodeName);
						temp[i] = n;
						nodeName++;
					}
					
					this.diamond.add(temp);
					
				} else if(lineLength>prevLineLength){
					//Still getting bigger, keep adding 2 more nodes
					int curSizeOfDiamond = this.diamond.size();
					
					//get the last one
					Node[] longestNodes = this.diamond.get(curSizeOfDiamond-1);  
					
					int numNode = longestNodes.length+2;
					
					//create this many nodes and add them to diamond object
					Node[] temp = new Node[numNode];
					for(int i=0; i<numNode; i++){
						Node n = new Node(nodeName);
						temp[i] = n;
						nodeName++;
					}
					
					//add this array of Nodes to the diamond
					this.diamond.add(temp); 
					
				} else {
					//Still getting smaller
					
					//Check the number of nodes in the last line and create 2 less
					int curSizeOfDiamond = this.diamond.size();
					
					//get the last one
					Node[] longestNodes = this.diamond.get(curSizeOfDiamond-1);  
					
					//start descending in size now, keep removing two nodes
					int numNode = longestNodes.length-2;
					
					//create this many nodes and add them to diamond object
					Node[] temp = new Node[numNode];
					for(int i=0; i<numNode; i++){
						Node n = new Node(nodeName);
						temp[i] = n;
						nodeName++;
					}
					
					this.diamond.add(temp);
				}
				//set the previous line length for the next iteration of the loop
				prevLineLength=lineLength;
			}
			//increment line count
			lineCount++;
		}
		
	}

	/**
	 * Simply loading the input file as an ArrayList of Strings
	 * @param filename
	 * @return
	 */
	private ArrayList<String> loadData(String filename){
		ArrayList<String> input = new ArrayList<String>();

		try{
			
			InputStream in = DiamondPath.class.getResourceAsStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			//loop through lines of input file
			String s;
			int countLines = 0;
			Integer parentNode = 0;  //initializing root node to build list of all possible edges
			while((s = br.readLine()) != null) { 
				countLines++;
	
				//read all lines first and store in ArrayList for processing later
				input.add(s);
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return input;
	}
	


	private class Node{
		
		private int name;  //gonna leave the names as int since we can depend on the input file containing only ints
		private LinkedList<Edge> paths;
		
		public Node(int n){
			name = n;
			paths = new LinkedList<Edge>();
		}
		
		public void addPath(int index, Edge e){
			if(index<3){
//				Path p = new Path(this.name, destination, weight);
//				paths[index] = weight;
				paths.add(index, e);
				
			}
		}
		public boolean hasChildren(){
			if(paths!=null && !paths.isEmpty())
				return true;
			else
				return false;
		}
		
		
		/* DONT SEARCH FOR LOWEST HERE, WE DON'T CARE ABOUT THE LOWEST, WE WANT ALL THE POSSIBLE PATHS
		 * SO THAT WE CAN FIND THE NUMBER OF PATHS THAT HAVE THE LOWEST DIFFICULTY
		 * returns the index of the lowest weight, NOT the weight value itself
		 * the weight is added to the traveled int
		 */
//		public int getLowestWeightPath(int alreadyTraveled){
//			int index=-1;
//			int weight=-1;
//			
//			if(paths!=null){
//				
//				for(int i=0; i<3; i++){
////					Integer w = paths[i];
//					
//					if(weight==-1){
//						weight=w; //assign weight
//						index=i;
//					}else{
//						if(w<weight){
//							weight=w;
//							//this index found a lower weight, assign index
//							index=i;
//						}
//					}
//				}
//			}
//			alreadyTraveled+=weight;
//			
//			return index;
//		}
//		public int getPath(int index){
//			if(index<3){
//				return paths[index];
//			}
//			return -1;
//		}
		public String getName(){
			return this.name + "";
		}
		public Iterator<Edge> getPaths(){
			return this.paths.iterator();
		}

		public String printPaths(){
			
			String retVal = "";
		
			Iterator<Edge> itr = getPaths();
			while(itr.hasNext()){
				Edge e = itr.next();
				if(e!=null)
					retVal += e.getDestNode().getName();
				else
					retVal += " ";
			}
			return retVal;
		}
		
		public String toString(){
			return getName();
		}
	}
	
	private class Edge{
		
		int pathWeight;
		Node destNode;
		
		public Edge(int w, Node n){
			pathWeight = w;
			destNode = n;
		}
		
		public Node getDestNode(){
			return destNode;
		}
		public int getPathWeight(){
			return pathWeight;
		}
	}
	
	
	
	
	
	
	
//	public static void main(String[] args) {
//		
//		DiamondPath dp = new DiamondPath("ProbDiamondPath.in.txt");
//
//		
//		try{
//			//create all nodes based on input lengths
//			dp.makeNodes();
//			
//			//debugging
//			dp.printDiamond();
//			
//			//then generate all possible linked lists of paths through the diamond
//
//			//then loop through nodes and add paths to them		
//			int loopCount=0;
//			while(loopCount<dp.diamond.size()){
//				
//				Node[] n = dp.diamond.get(loopCount);
//				int nLength = n.length;
//				
//				boolean gettingBigger = false;  //assume false
//				//check gettingBigger, don't check on the last line, will get IndexOutOfBounds
//				
//				//check if last row of diamond nodes, don't process last row, there are no more paths
//				if(loopCount<dp.diamond.size()-1){
//					
//					Node[] nextRow = dp.diamond.get(loopCount+1);
//					if(nextRow.length>nLength){
//						gettingBigger = true;
//					}else{
//						gettingBigger = false;
//					}
//					
//					
//					//add paths from input file to this node
//					String paths = dp.input.get(loopCount);
//					String[] pathsArray = paths.split(" ");
//					
//					int pathsCount = 0;  //counter for the number of paths on this input line
//					
//					for(int i=0; i<nLength; i++){
//						Node node = n[i];
//
//						if(gettingBigger){
//							//getting bigger side of the diamond
//							node.addPath(0, Integer.parseInt(pathsArray[pathsCount]));  
//							node.addPath(1, Integer.parseInt(pathsArray[pathsCount+1]));
//							node.addPath(2, Integer.parseInt(pathsArray[pathsCount+2]));
//							pathsCount=pathsCount+3;
//						} else{
//							//getting smaller, don't get 3 paths each time
//							if(nLength==3){
//								//second to last line of nodes, assign each node 1 path from the input file, in order
//								node.addPath(0, null);
//								node.addPath(1, Integer.parseInt(pathsArray[pathsCount]));
//								node.addPath(2, null);	
//								pathsCount=pathsCount+1;
//							} else {
//								//all other lines on the descending side of the diamond
//								if(i==0){
//									//first node gets 1 path
//									node.addPath(0, null);
//									node.addPath(1, null);
//									node.addPath(2, Integer.parseInt(pathsArray[pathsCount]));	
//									pathsCount=pathsCount+1;
//								} else if(i==1){
//									//second token gets 2 paths
//									node.addPath(0, null);
//									node.addPath(1, Integer.parseInt(pathsArray[pathsCount]));
//									node.addPath(2, Integer.parseInt(pathsArray[pathsCount+1]));	
//									pathsCount=pathsCount+2;
//								} else if(i==nLength-1){
//									//last node gets 1 path
//									node.addPath(0, Integer.parseInt(pathsArray[pathsCount]));  
//									node.addPath(1, null);
//									node.addPath(2, null);
//									pathsCount=pathsCount+1;
//								} else if(i==nLength-2){
//									//second to last gets 2 paths
//									node.addPath(0, Integer.parseInt(pathsArray[pathsCount]));  
//									node.addPath(1, Integer.parseInt(pathsArray[pathsCount+1]));
//									node.addPath(2, null);
//									pathsCount=pathsCount+2;
//								} else {
//									//three paths in the middle
//									node.addPath(0, Integer.parseInt(pathsArray[pathsCount]));  
//									node.addPath(1, Integer.parseInt(pathsArray[pathsCount+1]));
//									node.addPath(2, Integer.parseInt(pathsArray[pathsCount+2]));
//									pathsCount=pathsCount+3;
//								}
//							}
//							
//						}
//					}
//					
//					
//				} else{
//					//on last line, no paths no this node
//					;
//				}
//				
//				
//				loopCount++;
//			}
//			
//			//debugging
//			dp.printDiamondPaths();
//
//			
//			//now loop through to find the lowest path
//			
//			//start at root
//			//find all possible paths through the diamond, then add them up
//			LinkedList lowestPath=dp.start();
//			
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//
//
//	}
	
	
}
