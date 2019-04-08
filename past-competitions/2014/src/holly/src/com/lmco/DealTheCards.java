package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;


/**
 * CodeQuest 2014
 * Problem 2: Deal The Cards
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 * 
 * As the file is read in, I increment counters in a Hashtable that present the suits and the face value cards.  There is no
 * need to keep track of color because that can be determined by the suit.  
 * Before each hand is read, i reset the counter Hashtable.  Next, I substring the input string and increment the appropriate
 * suit and face value card.  
 * After the entire hand is read, the totals are printed and the loop continues till there are no more input lines
 */
public class DealTheCards {
	
	public static final String FILENAME = "Prob02.in.txt";
	
	public static String CLUB = "C";
	public static String DIAMOND = "D";
	public static String HEART = "H";
	public static String SPADE = "S";
	
	static int clubCount = 0;
	static int diamondCount = 0;
	static int heartCount = 0;
	static int spadeCount = 0;
	static Hashtable<String, Integer> faceValueCounts = new Hashtable<String, Integer>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{
			//read file
			InputStream in = DealTheCards.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			int handCount=0;
			
			//loop through lines of input file
			String s;
			while((s = br.readLine()) != null) { 
				//increment number of hands
				handCount++;
				
				//new hand of cards, reset all counts otherwise all the hands would be added together
				clubCount = 0;
				diamondCount = 0;
				heartCount = 0;
				spadeCount = 0;
				faceValueCounts.clear();
				faceValueCounts = new Hashtable<String, Integer>();
				
				
				//split on the space character
				String[] sArray = s.split(" ");
				
				//loop through all the cards in this hand
				for(int i=0; i<sArray.length; i++){
					
					//The only card that will have length of 3 will be a 10
					String value = "";
					String suit = "";
					
					//assign the values
					if(sArray[i].length()>2){
						//its a 10, substring using index 2
						value = sArray[i].substring(0, 2);
						suit = sArray[i].substring(2);
					}else{
						value = sArray[i].substring(0, 1);
						suit = sArray[i].substring(1);
					}
					
					//increment counts
					incrementCounts(value, suit);
					
				}
				//print this hand
				printHandTotals(handCount);
			
			} //end while loop of lines in the file
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Increments the counts as the cards for each hand is read in
	 * @param v
	 * @param s
	 */
	private static void incrementCounts(String v, String s){
		//suit counts
		if(s.equals(CLUB)){
			clubCount++;
		} else if(s.equals(DIAMOND)){
			diamondCount++;
		} else if(s.equals(HEART)){
			heartCount++;
		} else if(s.equals(SPADE)){
			spadeCount++;
		}
		
		//no need to count red and black because those can be inferred by suit at the end
		
		//value counts
		if(faceValueCounts.containsKey(v)){
			//table already has this value, increment
			int curValue = faceValueCounts.get(v);
			//replaces key with incremented value
			faceValueCounts.put(v, curValue+1);
		} else{
			//first one, set to 1
			faceValueCounts.put(v, new Integer(1));
		}
	}

	
	private static void printHandTotals(int i){
		
		System.out.println("HAND " + i);
		System.out.println( (diamondCount+heartCount) + "-RED");
		System.out.println( (clubCount+spadeCount) + "-BLACK");
		System.out.println( (clubCount==1) ? (clubCount+"-CLUB") : (clubCount+"-CLUBS"));
		System.out.println( (diamondCount==1) ? (diamondCount+"-DIAMOND") : (diamondCount+"-DIAMONDS"));
		System.out.println( (heartCount==1) ? (heartCount+"-HEART") : (heartCount+"-HEARTS"));
		System.out.println( (spadeCount==1) ? (spadeCount+"-SPADE") : (spadeCount+"-SPADES"));
	
		//numeric cards, in order
		//check to see if table has each type of card using this loop
		for(int idx=2; idx<=10; idx++){
			//remember idx is an int but the key to the table is a String
			String idxString = idx + "";
			
			if(faceValueCounts.get(idxString)!=null){
				//short-hand version
				System.out.println( (faceValueCounts.get(idxString)==1) ? (faceValueCounts.get(idxString)+"-"+idxString+" card") : (faceValueCounts.get(idxString)+"-"+idxString+" cards") ); 
			}
		}
		
		
		//face cards, in the correct order
		//JACK
		printFaceCards("J", "Jack");
		printFaceCards("Q", "Queen");
		printFaceCards("K", "King");
		printFaceCards("A", "Ace");
		
	}
	
	
	/**
	 * Util method to take care of printing the face cards.  
	 * Takes care of plural and singular labels
	 * @param key
	 * @param label
	 */
	private static void printFaceCards(String key, String label){
		
		if(key!=null && label!=null){
			if(faceValueCounts.get(key)!=null){
				//a third way to handle the plural using a print instead of println
				System.out.print(faceValueCounts.get(key)+"-"+label);
				if(faceValueCounts.get(key)>1)
					System.out.println("s");
				else
					System.out.println("");
			}
		}
	}
}
