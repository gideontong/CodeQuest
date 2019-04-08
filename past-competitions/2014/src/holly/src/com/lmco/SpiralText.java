package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * CodeQuest 2014
 * Problem 11: Spiral Text
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 */
public class SpiralText {

	public static final String FILENAME = "Prob11.in.txt";
	
	public static String[][] sArray = new String[0][0];
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<StringBuffer> output = new ArrayList<StringBuffer>();
		
		
		StringBuffer sb = new StringBuffer();
		String input = "";
		
		try{
			//read file
			InputStream in = SpiralText.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//loop through lines of input file
			String s;
			int lineCount = 0;
			boolean hasSpace = false;
			while((s = br.readLine()) != null) {
				if(input.length()>0){
					if(input.endsWith(" ")){
						input = input + s;
					} else {
						//check if we need to add a space
						if(s.startsWith(" "))
							input = input + s;
						else
							input = input + " " + s;
					}
				} else {
					input = input + s;
				}

				lineCount++;
			}
			int charCount = input.length();
			
			//figure out square size based on input length
			int size = findSquareSize(input.length());

			//fill string with left over spaces
			int maxSpaces = size * size;
			if(maxSpaces>input.length()){
				//pad with spaces
				for(int i=input.length(); i<maxSpaces; i++){
					input = input + " ";
				}
			}
			//now the input string exactly fits the square
			
			//initialize to right size
			sArray = new String[size][size];
			
			//find the middle position
			int middle = findMiddle(size);
			
			int startRow = middle;
			int startCol = middle;
			//fill middle to start off
			sArray[startRow][startCol] = input.charAt(0)+"";
			
			int itrCount = 0;
			String whatsleft = input.substring(1);
			for(int i=startCol-1; i>=0; i--){
				
				itrCount++;
				int min=startCol-itrCount;
				int max=startCol+itrCount;
				
//				System.out.println("Calling: " + itrCount + ", min:" + min + " max:" + max);
				whatsleft = makeLoop(startRow, i, min, max, whatsleft, 0);
//				printDebugArray();
				
			}
			
			printArray();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private static int findMiddle(int size) {
		
		if(size%2==0){
			//even
			return size/2;
		} else{
			//odd
			return (size-1)/2;
		}
		
	}

	private static String makeLoop(int sR, int sC, int min, int max, String s, int idx){
		int sIdx = idx;
		
		//fill first one
//		sArray[sR][sC] = s.charAt(sIdx) + "";
		
		//start at postion
		int rowPos = sR;
		int colPos = sC;
		
		//move down till MAX row
		for(int i=rowPos; i<=max; i++){
			//increment row, same col
			sArray[i][colPos] = s.charAt(sIdx++) + "";
		}
		
		
		//move right till MAX col
		for(int i=sC; i<=max; i++){
			//increment col, same row
			if(sArray[max][i]==null)  //don't fill this spot if it already has a letter
				sArray[max][i] = s.charAt(sIdx++) + "";
			
		}
		
		//move up till MIN row
		for(int i=max; i>=min; i--){
			//move up, decrement row,  same col
			if(sArray[i][max] == null) 
				sArray[i][max] = s.charAt(sIdx++) + "";
			
			rowPos = i;
			
		}
		
		//move left till MIN col
		for(int i=max; i>=min; i--){
			//move left, decrement col, same row
			if(sArray[min][i]==null)
				sArray[min][i] = s.charAt(sIdx++) + "";
			
			colPos = i;
		}

		//now turn the corner if we are not at the end
		if(min>0){
			sArray[min][min-1] = s.charAt(sIdx++) + "";
			colPos = min-1;
		}

		//are we back at start position
		if(rowPos==(sR-1)){
			//we are back
		}else{
			//move down till we get there
			for(int i=rowPos+1; i<sR && sIdx<s.length(); i++){
				//increment row, same col
				sArray[i][colPos] = s.charAt(sIdx++) + "";
			}
		}
		
		
		
		return s.substring(sIdx);
	}


//	private static int findMiddle();
	
	private static int findSquareSize(int length) {

		double d = Math.sqrt(length);
		
		int retVal = (int) Math.ceil(d);
		
		if(retVal%2==0){
			//check for evenness, if even, then there is no exact middle point for the start
			return retVal+1;
		} else{
			return retVal;
		}
	}
	
	private static void printArray(){
		
		for(int i=0; i<sArray.length; i++){
			for(int j=0; j<sArray.length; j++){
				if(sArray[i][j] ==null)
					System.out.print(" ");
				else 
					System.out.print(sArray[i][j]);
			}
			System.out.println("");
		}
	}
	
	private static void printDebugArray(){
		
		for(int i=0; i<sArray.length; i++){
			for(int j=0; j<sArray.length; j++){
				if(sArray[i][j] ==null)
					System.out.print(" , ");
				else 
					System.out.print(sArray[i][j] + ", ");
			}
			System.out.println("");
		}
	}

}
