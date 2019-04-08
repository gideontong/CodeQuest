package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * CodeQuest 2014
 * Problem 14: 3D Bingo
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 * I FIRST GOT AN OUT OF MEMORY EXCEPTION ON THIS PROBLEM SO I HAD TO SET MY ARGUMENTS AS:
 * -Xms1024M
 * -Xmx1024M
 */
public class ThreeDBingo {

	public static final String FILENAME = "Prob14.in.txt";
	
	//array indexes for the letters called during the game
	private static int B_index = 0;
	private static int I_index = 1;
	private static int N_index = 2;
	private static int G_index = 3;
	private static int O_index = 4;
	
	private static String X = "X";
	
	//contains all the cards read in from the file
	public static ArrayList<String[][]> myCards = new ArrayList<String[][]>();
	//contains all the numbers called during a game
	public static Collection<String> gameNumbers = new ArrayList<String>();
	//contains all the possible cube combinations
//	public static ArrayList<Hashtable<Integer,String[][]>> myCubes = new ArrayList<Hashtable<Integer,String[][]>>();
	//contains all the possible combinations of cards from slicing all the possible cubes on each axis;
	public static ArrayList<String[][]> allPossibleCardSlicesFromCubes = new ArrayList<String[][]>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			String output = "0";
			
			//read file, build objects
			loadData();
			
			//LOOP: mark card with number called, then check for bingo.
//			if(myCards!=null && gameNumbers!=null && myCubes!=null){
			if(myCards!=null && gameNumbers!=null){
				//game has begun
				
				//TODO: talk to trink about order of marking cards when numbers are called
//				makeAllPossibleCardFacesFromCubes(); //don't need to mark these because the source cards are already marked with X
				
				Iterator<String> gameItr = gameNumbers.iterator();

				while(gameItr.hasNext()){
					
					String number = (String) gameItr.next();
					
					int col = getBingoLetterIndex(number);
					int num = getBingoNumber(number);
					
//					System.out.println("CALLING NUMBER " + number);
					
					//loop through cards and mark them with X
					markCards(col, num);
					
					//check single cards
					int bingo = checkForBingoOnSingleCard();

					//check 3d bingos
					int cubeBingos = 0;
					if(myCards.size()>4){
						allPossibleCardSlicesFromCubes.clear();
						allPossibleCardSlicesFromCubes = new ArrayList<String[][]>();  //reset
						ArrayList<Hashtable<Integer,String[][]>> myCubes = makeCubes();
//						makeAllPossibleCardSlicesFromCubes(); //don't need to mark these because the source cards are already marked with X
						
						cubeBingos = checkForBingoOnCube(myCubes);
					}

					
					if(bingo + cubeBingos>0){
						//Bingo called, stop game 
//						output = number + " resulted in " + (bingo) + " CARD BINGOS";
//						output += "\n" + number + " resulted in " + (cubeBingos) + " CUBE BINGOS";
						output = "Number of bingos: "+ (bingo+cubeBingos);
						break;
					}
				}

			}
			System.out.println(output);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private static Hashtable<Integer, String[][]>  makeEmptyCube(){
		Hashtable<Integer, String[][]> cube = new Hashtable<Integer, String[][]>();
		return cube;
	}
	
	private static Hashtable<Integer, String[][]> makeCubeFromIndexString(String indexStr){

		Hashtable<Integer, String[][]> cube = makeEmptyCube(); //need a collection with 5 slots
		
		if(myCards!=null && !myCards.isEmpty()){
			
			//make a cube
			cube.put(0, myCards.get(Integer.parseInt(indexStr.substring(0, 1))));
			cube.put(1, myCards.get(Integer.parseInt(indexStr.substring(1, 2))));
			cube.put(2, myCards.get(Integer.parseInt(indexStr.substring(2, 3))));
			cube.put(3, myCards.get(Integer.parseInt(indexStr.substring(3, 4))));
			cube.put(4, myCards.get(Integer.parseInt(indexStr.substring(4))));
			
		}
		return cube;
	}

	private static Hashtable<Integer, String[][]> makeCubeFromIndexString(List<String> indexStr){

		Hashtable<Integer, String[][]> cube = makeEmptyCube(); //need a collection with 5 slots
		
		if(myCards!=null && !myCards.isEmpty()){
			
			//make a cube
			cube.put(0, myCards.get(Integer.parseInt(indexStr.get(0))));
			cube.put(1, myCards.get(Integer.parseInt(indexStr.get(1))));
			cube.put(2, myCards.get(Integer.parseInt(indexStr.get(2))));
			cube.put(3, myCards.get(Integer.parseInt(indexStr.get(3))));
			cube.put(4, myCards.get(Integer.parseInt(indexStr.get(4))));
			
		}
		return cube;
	}
	
	private static Hashtable<Integer, String[][]> makeCubeFromIndexes(int a, int b, int c, int d, int e){

		Hashtable<Integer, String[][]> cube = makeEmptyCube(); //need a collection with 5 slots
		
		if(myCards!=null && !myCards.isEmpty()){
			
			//make a cube
			cube.put(0, myCards.get(a));
			cube.put(1, myCards.get(b));
			cube.put(2, myCards.get(c));
			cube.put(3, myCards.get(d));
			cube.put(4, myCards.get(e));
			
		}
		return cube;
	}
	
//	private static void markCards(int col, int num) {
//
//		if(myCards!=null && !myCards.isEmpty()){
//			
//			Iterator cardItr = myCards.iterator();
//			
//			while(cardItr.hasNext()){  //for each card in my Collection
//				
//				String[][] card = (String[][]) cardItr.next();  //get a card
//				
//				for(int row=0; row<5; row++){
//					//we know the column, no need to loop those
//					if(card[row][col].equals(num+"")){
//						//found it, mark it
//						card[row][col] = X;
//						//it can't occur again on this card so break
//						break;
//					}
//				}
//			}
//			
//		}
//	}
//	
	
	private static void markCards(int col, int num) {

		if(myCards!=null && !myCards.isEmpty()){
			markCards(myCards.iterator(), col, num);
		}
//		if(allPossibleCardSlicesFromCubes!=null && !allPossibleCardSlicesFromCubes.isEmpty()){
//			markCards(allPossibleCardSlicesFromCubes.iterator(), col, num);
//		}
	}
	
	private static void markCards(Iterator cardItr, int col, int num) {

		while(cardItr.hasNext()){  //for each card in my Collection
			
			String[][] card = (String[][]) cardItr.next();  //get a card
			
			for(int row=0; row<5; row++){
				//we know the column, no need to loop those
				if(card[row][col].equals(num+"")){
					//found it, mark it
					card[row][col] = X;
					//it can't occur again on this card so break
					break;
				}
			}
		}
			
	}
	
	private static ArrayList<Hashtable<Integer,String[][]>> makeCubes(){
		
		ArrayList<Hashtable<Integer,String[][]>> myCubes = new ArrayList<Hashtable<Integer,String[][]>>();
		
		if(myCards.size()>=5){
			//go through cards and build cubes
			//generate all possible combinations of 5 cards by card index number
	    	int count=0; //for testing
	    	
	    	//loop 1
	    	for(int a=0; a<myCards.size(); a++){
	    		//loop 2
	    		for(int b=0; b<myCards.size(); b++){
	    			if(b!=a){
		    			//loop 3
		    			for(int c=0; c<myCards.size(); c++){
		    				if(c!=a && c!=b){
			    				//loop 4
			    				for(int d=0; d<myCards.size(); d++){
			    					if(d!=c && d!=b && d!=a){
				    					//loop 5
				    					for(int e=0; e<myCards.size(); e++){
				    						if(e!=d && e!=c && e!=b && e!=a){ //can't repeat any cards
				    							count++;
//				    							System.out.println("index= "+a+","+b+","+c+","+d+","+e);
				    							//make cube from these indexes
				    				            Hashtable<Integer, String[][]> cube = makeCubeFromIndexes(a, b, c, d, e);
				    				            //save cube for later, or check cube for bingo and then throw away?
				    				            myCubes.add(cube);
				    						}
				    					}
			    					}
			    				}
		    				}
		    			}
	    			}
	    		}
	    	}
	    	
//	        System.out.println(count);
//	        System.out.println(myCubes.size());
		    	//
////		    	Collection<String> cubeIndxSet = new ArrayList<String>();
//		    	List<List<String>> cubeIndxSet = new ArrayList<List<String>>();
//		    	Iterator<String[]> listItr = cardIndexList.iterator();
//		    	while(listItr.hasNext()){
//		    		cubeIndxSet.addAll(getPermutations(listItr.next()));
//		    	}
//	
//		        itr = cubeIndxSet.iterator();
		        
	        
	    	//get possible number of combinations of cubes
//	        while (itr.hasNext()) {
//	            Hashtable<Integer, String[][]> cube = makeCubeFromIndexString(itr.next());
//	            myCubes.add(cube);
////	            printCube(cube);
//	        }
	        
	        return myCubes;
		}
		return null;
	}
	
	private static void makeAllPossibleCardSlicesFromCubes(ArrayList<Hashtable<Integer,String[][]>> myCubes){
		
		if(myCubes!=null && !myCubes.isEmpty()){
			
			Iterator<Hashtable<Integer, String[][]>> cubeItr = myCubes.iterator();
			
			//for each cube
			while(cubeItr.hasNext()){
				Hashtable<Integer, String[][]> cube = cubeItr.next();
				allPossibleCardSlicesFromCubes.addAll(makeRowSlices(cube));
				allPossibleCardSlicesFromCubes.addAll(makeColumnSlices(cube));
//				//COLUMN SLICES, turn columns into rows
//				//make B slice
//				String[][] sliceCardB = makeNewCardFromCubeColumns(cube, B_index);
//				//store slice for looking up BINGOs
//				allPossibleCardSlicesFromCubes.add(sliceCardB);
//				
//				//make I slice
//				String[][] sliceCardI = makeNewCardFromCubeColumns(cube, I_index);
//				//store slice for looking up BINGOs
//				allPossibleCardSlicesFromCubes.add(sliceCardI);
//				
//				//make N slice
//				String[][] sliceCardN = makeNewCardFromCubeColumns(cube, N_index);
//				//store slice for looking up BINGOs
//				allPossibleCardSlicesFromCubes.add(sliceCardN);
//				
//				//make G slice
//				String[][] sliceCardG = makeNewCardFromCubeColumns(cube, G_index);
//				//store slice for looking up BINGOs
//				allPossibleCardSlicesFromCubes.add(sliceCardG);
//				
//				//make O slice
//				String[][] sliceCardO = makeNewCardFromCubeColumns(cube, O_index);
//				//store slice for looking up BINGOs
//				allPossibleCardSlicesFromCubes.add(sliceCardO);
//				
//				//ROW SLICES, rows...column int stays constant
//				//make row 1 slices,
//				//B4, I4, N4, G4, 04,
//				String[][] sliceCardR1 = makeNewCardFromCubeRows(cube, 0);
//				allPossibleCardSlicesFromCubes.add(sliceCardR1);
//				
//				//make row 2 slices
//				String[][] sliceCardR2 = makeNewCardFromCubeRows(cube, 1);
//				allPossibleCardSlicesFromCubes.add(sliceCardR2);
//				
//				//make row 3 slices
//				String[][] sliceCardR3 = makeNewCardFromCubeRows(cube, 2);
//				allPossibleCardSlicesFromCubes.add(sliceCardR3);
//				
//				//make row 4 slices
//				String[][] sliceCardR4 = makeNewCardFromCubeRows(cube, 3);
//				allPossibleCardSlicesFromCubes.add(sliceCardR4);
//				
//				//make row 5 slices
//				String[][] sliceCardR5 = makeNewCardFromCubeRows(cube, 4);
//				allPossibleCardSlicesFromCubes.add(sliceCardR5);
				
			}
			
		}
		
	}
	
	
	private static ArrayList<String[][]> makeColumnSlices(Hashtable<Integer, String[][]> cube){
		ArrayList<String[][]> retVal = new ArrayList<String[][]>();
		
		if(cube!=null){
			//COLUMN SLICES, turn columns into rows
			//make B slice
			String[][] sliceCardB = makeNewCardFromCubeColumns(cube, B_index);
			//store slice for looking up BINGOs
			retVal.add(sliceCardB);
			
			//make I slice
			String[][] sliceCardI = makeNewCardFromCubeColumns(cube, I_index);
			//store slice for looking up BINGOs
			retVal.add(sliceCardI);
			
			//make N slice
			String[][] sliceCardN = makeNewCardFromCubeColumns(cube, N_index);
			//store slice for looking up BINGOs
			retVal.add(sliceCardN);
			
			//make G slice
			String[][] sliceCardG = makeNewCardFromCubeColumns(cube, G_index);
			//store slice for looking up BINGOs
			retVal.add(sliceCardG);
			
			//make O slice
			String[][] sliceCardO = makeNewCardFromCubeColumns(cube, O_index);
			//store slice for looking up BINGOs
			retVal.add(sliceCardO);
			
		}
		return retVal;
	}
	
	private static ArrayList<String[][]> makeRowSlices(Hashtable<Integer, String[][]> cube){
		ArrayList<String[][]> retVal = new ArrayList<String[][]>();
		
		if(cube!=null){			
			//ROW SLICES, rows...column int stays constant
			//make row 1 slices,
			//B4, I4, N4, G4, 04,
			String[][] sliceCardR1 = makeNewCardFromCubeRows(cube, 0);
			retVal.add(sliceCardR1);
			
			//make row 2 slices
			String[][] sliceCardR2 = makeNewCardFromCubeRows(cube, 1);
			retVal.add(sliceCardR2);
			
			//make row 3 slices
			String[][] sliceCardR3 = makeNewCardFromCubeRows(cube, 2);
			retVal.add(sliceCardR3);
			
			//make row 4 slices
			String[][] sliceCardR4 = makeNewCardFromCubeRows(cube, 3);
			retVal.add(sliceCardR4);
			
			//make row 5 slices
			String[][] sliceCardR5 = makeNewCardFromCubeRows(cube, 4);
			retVal.add(sliceCardR5);
		}
		return retVal;
	}
	
	private static String[][] makeNewCardFromCubeRows(Hashtable<Integer, String[][]> cube, int index){
		String[][] sliceCard = new String[5][5];
		
		//cube is made from 5 cards
		for(int numCards=0; numCards<5; numCards++){
			//generate the possible slices of the cube as new cards to mark
			
			String[][] card = cube.get(numCards);
			for(int i=4; i>=0; i--){
				sliceCard[numCards][i] = card[index][i];

			}
		}
		
		return sliceCard;
		
	}
	
	private static String[][] makeNewCardFromCubeColumns(Hashtable<Integer, String[][]> cube, int index){
		
		String[][] sliceCard = new String[5][5];
		
		//cube is made from 5 cards
		for(int numCards=0; numCards<5; numCards++){
			//generate the possible slices of the cube as new cards to mark
			
			String[][] card = cube.get(numCards);
			for(int row=0; row<5; row++){
				sliceCard[numCards][row] = card[row][index];
			}
			//loop through slices to mark cards.
		}
		
		return sliceCard;
	}


	
	private static int checkForBingoOnSingleCard(){
		
		int retVal = 0;
		
		if(myCards!=null && !myCards.isEmpty()){
			//loop through cards
			Iterator cardItr = myCards.iterator();
			
			while(cardItr.hasNext()){
				String[][] card = (String[][])cardItr.next();
				retVal += checkForBingoOnCard(card, true, true, true);
			}
		}
		return retVal;
	}
	
//	private static int checkForBingoOnCube(){
//		
//		int retVal = 0;
//		
//		if(allPossibleCardSlicesFromCubes!=null && !allPossibleCardSlicesFromCubes.isEmpty()){
//			//loop through cards
//			Iterator cardItr = allPossibleCardSlicesFromCubes.iterator();
//			
//			while(cardItr.hasNext()){
//				String[][] card = (String[][])cardItr.next();
//				int b = checkForBingoOnCard(card, true);
//				
//				if(b>0 && retVal<100){
//					System.out.println("Bingo found on: ");
//					printCard(card);
//					System.out.println("");
//				}
//				retVal += b;
//			}
//		}
//		return retVal;
//	}
	
	private static int checkForBingoOnCube(ArrayList<Hashtable<Integer,String[][]>> myCubes){
		
		int retVal = 0;
		
		if(myCubes!=null && !myCubes.isEmpty()){
			
//			makeAllPossibleCardSlicesFromCubes(myCubes);
			//iterator over the cubes and check each one  
			
			Iterator<Hashtable<Integer, String[][]>> cubeItr = myCubes.iterator();
			
			//for each cube
			while(cubeItr.hasNext()){
				Hashtable<Integer, String[][]> cube = cubeItr.next();
			
				//CHECK SLICES OF CUBE
				ArrayList<String[][]> rowSlices = makeRowSlices(cube);
				
				//Check Row slices first (all at once)
				if(rowSlices!=null && !rowSlices.isEmpty()){
					//loop through cards
					Iterator<String[][]> cardItr = rowSlices.iterator();
					
					while(cardItr.hasNext()){
						String[][] card = (String[][])cardItr.next();
						int b = checkForBingoOnCard(card, false, true, true);
						
						if(b>0 && retVal<100){
//							System.out.println("Bingo found on: ");
//							printCard(card);
//							System.out.println("");
						}
						retVal += b;
					}
				}
				
				//Check Column slices, only check for diagonal bingos here
				//straight down bingos are covered by the rows.  checking them here would return double the bingos
				ArrayList<String[][]> colSlices = makeColumnSlices(cube);
				
				//Check Row slices first (all at once)
				if(colSlices!=null && !colSlices.isEmpty()){
					//loop through cards
					Iterator<String[][]> cardItr = colSlices.iterator();
					
					while(cardItr.hasNext()){
						String[][] card = (String[][])cardItr.next();
						int b = checkForBingoOnCard(card, false, false, true);
						
						if(b>0 && retVal<100){
//							System.out.println("Bingo found on: ");
//							printCard(card);
//							System.out.println("");
						}
						retVal += b;
					}
				}
				
				//CHECK THROUGH the cube BINGO, corner to opposite corner
				if(cube.get(2)[2][2].equals(X)){  //all corner to corner bingos have to have the middle space
					//continue checking spaces
					int count = 0;
					//top left-top corner to bottom right-bottom corner: card0.[0,0] to card4.[4,4]
					for(int i=0; i<5; i++){
						if(cube.get(i)[i][i].equals(X)){
							count++;
						}else{
							//skip row, no possible bingo here
							i=5;
						}
					}
					if(count==5){
						retVal ++;
					}
					count = 0; //reset, check other side
					//top left-bottom corner to right left-top corner: card0.[4,4] to card4.[0,0]
					for(int i=0, j=4; i<5 && j>=0; i++, j--){
						if(cube.get(i)[j][j].equals(X)){
							count++;
//						}else if(cube.get(i)[3][3].equals(X)){
//							count++;
//						}else if(cube.get(i)[2][2].equals(X)){
//							count++;
//						}else if(cube.get(i)[1][1].equals(X)){
//							count++;
//						}else if(cube.get(i)[0][0].equals(X)){
//							count++;
						}else{
							//skip row, no possible bingo here
							i=5;
						}
					}
					if(count==5){
						retVal ++;
					}
					count = 0;
					//bottom right-top corner to top left-bottom corner: card4.[0,4] to card0.[4,0]
					for(int i=4, j=0; i>=0 && j<5; i--, j++){
						if(cube.get(i)[j][i].equals(X)){
							count++;
						}else{
							//skip row, no possible bingo here
							i=5;
						}
					}
					if(count==5){
						retVal ++;
					}
					count=0;
					//bottom top-left corner to top right-bottom corner: card4.[4,0] to card0.[0,4]
					for(int i=4, j=0; i>=0 && j<5; i--, j++){
						if(cube.get(i)[i][j].equals(X)){
							count++;
						}else{
							//skip row, no possible bingo here
							i=5;
						}
					}
					if(count==5){
						retVal ++;
					}
				
				}//end check through the cube, corner to corner
				
			} //end while loop
		}
		return retVal;
	}
	
	/**
	 * 
	 * @param card
	 * @param checkHorizontal
	 * @param checkVertical
	 * @param checkDiagonal
	 * @return
	 */
	private static int checkForBingoOnCard(String[][] card, boolean checkHorizontal, boolean checkVertical, boolean checkDiagonal){
		int numBingos = 0;
		
		if(card!=null){
			
			if(checkVertical){
				//check vertical (or straight down into the cube)
				for(int col=0; col<5; col++){
					int rowCount = 0;
					for(int row=0; row<5; row++){
						if(card[row][col].equals(X)){
							rowCount++;
						}else{
							//skip column, no possible bingo here
							row=5;
						}
					}
					if(rowCount==5){
						numBingos ++;
					}
				}
			}
			
			//check horizontal
			//do not check for horizontal bingos on a cube because that would be a card bingo as well
			if(checkHorizontal){
				for(int row=0; row<5; row++){
					int colCount = 0;
					for(int col=0; col<5; col++){
						if(card[row][col].equals(X)){
							colCount++;
						}else{
							//skip row, no possible bingo here
							col=5;
						}
					}
					if(colCount==5){
						numBingos ++;
					}
				}
			}
			
			//check diagonal (or diagonal on a slice)
			if(checkDiagonal){
				int count = 0;
				for(int i=0; i<5; i++){
					if(card[i][i].equals(X)){
						count++;
					}else{
						//skip row, no possible bingo here
						i=5;
					}
				}
				if(count==5){
					numBingos ++;
				}
				
				count = 0;
				int i=4;
				int j=0;
				while(i>=0 && j<5){
					if(card[i][j].equals(X)){
						count++;
					}else{
						//skip, no possible bingo here
						j=5;
						i=-1;
					}
					i--; j++;
				}
				if(count==5){
					numBingos++;
				}
			}
			
		}
		return numBingos;
	}
	

	
	private static int getBingoLetterIndex(String s){
		
		if(s!=null){
			if(s.substring(0, 1).equalsIgnoreCase("B"))
				return B_index;
		
			if(s.substring(0, 1).equalsIgnoreCase("I"))
				return I_index;
			
			if(s.substring(0, 1).equalsIgnoreCase("N"))
				return N_index;
			
			if(s.substring(0, 1).equalsIgnoreCase("G"))
				return G_index;
			
			if(s.substring(0, 1).equalsIgnoreCase("O"))
				return O_index;
		}
		return -1;
	}
	

	private static int getBingoNumber(String s){
		
		if(s!=null){
			
			return Integer.parseInt(s.substring(1));
		}
		return -1;
	}
	
	private static void printCard(String[][] s) {
		
		for(int i=0; i<s.length; i++){
			for(int j=0; j<s[i].length; j++){
				System.out.print(s[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	private static void printCards() {
		
		if(myCards!=null && !myCards.isEmpty()){
			Iterator<String[][]> itr = myCards.iterator();
			
			while(itr.hasNext()){
				String[][] s = (String[][]) itr.next();
				
				for(int i=0; i<s.length; i++){
					for(int j=0; j<s[i].length; j++){
						System.out.print(s[i][j] + " ");
					}
					System.out.println("");
				}
				System.out.println("");
			}
		}
	}
	
	private static void printGameNumbers(){
		if(gameNumbers!=null && !gameNumbers.isEmpty()){
			
			System.out.println(gameNumbers);
		}
	}
	
	private static void printCube(Hashtable<Integer, String[][]> cube){
		// cube is always 5 cards
		printCard(cube.get(0));
		printCard(cube.get(1));
		printCard(cube.get(2));
		printCard(cube.get(3));
		printCard(cube.get(4));
		
	}
	
	private static void loadData(){
		try{
			
			InputStream in = ThreeDBingo.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			String[][] card = new String[5][5];
			
			//loop through data lines
			int countLines = 0;
			int countCards = 0;
			String line;
			
			while((line = br.readLine()) != null) { 
			
				//array to contain one line of input
				String[] sArray = line.split(" ");
				
				if(sArray.length>4){
					//card information
					
					//add to card object
					for(int i=0; i<sArray.length; i++){
						card[countLines][i] = sArray[i];
					}
					
					countLines++;
					
					if(countLines==5){
						//we filled a card; reset line count and increment card count
						countCards++;
						countLines = 0;
						
						//add this card to list
						myCards.add(card);
						
						card = null;
						card = new String[5][5];
					}
				}else {
					if(line.contains("PLAY")){
						; //skip
					} else{
						//number called for game
						gameNumbers.add(line);
						
					}
				}
			}
//			printCards();
//			printGameNumbers();
			
//			if(myCards.size()>4){
//				//go through cards and build cubes
//				//generate all possible combinations of 5 cards by card number
//		    	String[] cardIndex = new String[myCards.size()];
//		    	Iterator<List<String>> itr;
//		    	
//		    	for(int i=0; i<myCards.size(); i++){
////		    		cardIndex+= i + ":"; //delimeter
//		    		cardIndex[i] = i+"";
//		    	}
//		    	
//		    	if(myCards.size()==5){
////			        Collection<String> cubeIndxSet = permutation(cardIndex);
//			        List<List<String>> cubeIndxSet = getPermutations(cardIndex);
//		
//			        System.out.println("\nThere are total of " + cubeIndxSet.size() + " permutations of these cards:");
//			        itr = cubeIndxSet.iterator();
//			        
//		    	} else{
//		    		//if number of cards is greater than 5 have to make more permutations
//
//		    		List<String> colIndex = Arrays.asList(cardIndex);
//			    	Collection<String[]> cardIndexList = new ArrayList<String[]>();
//			    	
//			    	//chop them up into all possible groups of 5
//			    	for(int c=0; c<myCards.size(); c++){
//			    		//rotate indexes 1
//			    		Collections.rotate(colIndex, 1); 
//			    		//take first 5
//			    		List<String> temp = colIndex.subList(0, 5);
//			    		//add to collection
//			    		String[] a = new String[5]; 
//			    		cardIndexList.add(temp.toArray(a));
//				    	
//			    	}
//			        
//			    	//
////			    	Collection<String> cubeIndxSet = new ArrayList<String>();
//			    	List<List<String>> cubeIndxSet = new ArrayList<List<String>>();
//			    	Iterator<String[]> listItr = cardIndexList.iterator();
//			    	while(listItr.hasNext()){
//			    		cubeIndxSet.addAll(getPermutations(listItr.next()));
//			    	}
//		
//			        System.out.println("\nThere are total of " + cubeIndxSet.size() + " permutations of these cards:");
//			        itr = cubeIndxSet.iterator();
//		    	}
//		    	//get possible number of combinations of cubes
//		        while (itr.hasNext()) {
//		            Hashtable<Integer, String[][]> cube = makeCubeFromIndexString(itr.next());
//		            myCubes.add(cube);
//	//	            printCube(cube);
//		        }
//			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
//    static Set<String> permutations;
//    static Set<String> result = new HashSet<String>();
    static Collection<String> permutations;
    static Collection<String> result = new ArrayList<String>();

//    public static Collection<String> getPermutation(String[] sArray) {
	public static List<List<String>> getPermutations(String[] sArray) {
        permutations = new ArrayList<String>();
        List<List<String>> perms = new ArrayList<List<String>>();
    	List<String> prefix = new ArrayList<String>();
    	List<String> strList = new ArrayList<String>();
    	
        int n = sArray.length;
        for (int i = n - 1; i >= 0; i--) {
        	strList.add(sArray[i]);
//        	shuffle(sArray[i]);
        }
        
        permute( prefix, strList, perms );
        
//        return permutations;
        return perms;
    }
    
	private static void permute( List<String> prefix, List<String> str, List<List<String>> perms ) {
        int n = str.size();
        if ( n == 0 ) {
	        perms.add(prefix);
        } else {
            for ( int i = 0; i < n; i++ ) {
            	List<String> newPrefix = new ArrayList<String>();
            	newPrefix.addAll(prefix);
            	newPrefix.add(str.get(i));
            	List<String> newString = new ArrayList<String>();
            	newString.addAll(str.subList(0, i));
            	newString.addAll(str.subList(i+1, n));
            	
	            permute(newPrefix, newString, perms );//3003
            }
	    }
	}
	

//    private static void shuffle(String c) {
//        if (permutations.size() == 0) {
//            permutations.add(c);
//        } else {
//            Iterator<String> it = permutations.iterator();
//            for (int i = 0; i < permutations.size(); i++) {
//
//                String temp1;
//                for (; it.hasNext();) {
//                    temp1 = it.next();
//                    for (int k = 0; k < temp1.length() + 1; k += 1) {
//                        StringBuffer sb = new StringBuffer(temp1);
//
//                        sb.insert(k, c);
//
//                        result.add(sb.toString());
//                    }
//                }
//            }
//            permutations = result;
//            //'result' has to be refreshed so that in next run it doesn't contain stale values.
//            result = new ArrayList<String>();
//        }
//    }

}
