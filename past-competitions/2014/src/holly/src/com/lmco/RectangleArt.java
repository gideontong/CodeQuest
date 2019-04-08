package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * CodeQuest 2014
 * Problem 8: Rectangle Art
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 */
public class RectangleArt {

	public static final String FILENAME = "Prob08.in.txt";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			ArrayList<String> input = readFile(FILENAME);
		
			String arraySize = input.get(0);
			String arraySizeArray[] = arraySize.split(",");
			int x = Integer.parseInt(arraySizeArray[0]);
			int y = Integer.parseInt(arraySizeArray[1]);
			
			
			String[][] output = new String[x][y];
			
			int idx = 1; //skipping first line of input
			while(idx<input.size()){
				
				String rect = input.get(idx);
				//split on " "
				String[] coordinates = rect.split(" ");
				String[] coord1 = coordinates[0].split(",");
				String[] coord2 = coordinates[1].split(",");
				
				output = fillRectangle(output, coord1, coord2);
				
				idx++;
			}

			printWorkspace(output, x, y);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void printWorkspace(String[][] output, int x, int y) {

		for(int row=y-1; row>=0; row--){
			for(int col=0; col<x; col++){
				if(output[row][col]==null)
					System.out.print(" ");
				else
					System.out.print(output[row][col]);
				
			}
			System.out.println("");
		}
	}

	/**
	 * NOTE:  Can't only increment through the loop...if the second coordinate is less than the first you need to decrement instead!
	 * @param output
	 * @param coord1
	 * @param coord2
	 * @return
	 */
	private static String[][] fillRectangle(String[][] output, String[] coord1, String[] coord2) {
		
		//(col(x) - wide, row(y) - tall) 
		//plot point 1
		int x1 = Integer.parseInt(coord1[0]);
		int y1 = Integer.parseInt(coord1[1]);
		//plot point 2
		int x2 = Integer.parseInt(coord2[0]);
		int y2 = Integer.parseInt(coord2[1]);
		
		//GOTCHA HERE!!
		//find smaller x of the rectangle
		if(x2<x1){
			int newX1 = x2;
			x2 = x1;
			x1 = newX1;
		}
		//find smaller y of the rectangle
		if(y2<y1){
			int newY1 = y2;
			y2 = y1;
			y1 = newY1;
		}
		
		//use a loop to fill inbetween
		for(int col=x1; col<x2; col++){
			
			for(int row=y1; row<y2; row++){
				
				if(output[row][col]==null){
					//not visited yet
					//place first rectangle (odd)
					output[row][col] = "*"; 				
				}else{
					if(output[row][col]=="*"){
						//adding another rectangle would make count event (2 rects) so change to space
						output[row][col] = " ";
					}else{
						//adding another rectangle would make it odd again
						output[row][col] = "*";
					}
				}
				
			}
		}
		
		return output;
	}

	private static ArrayList<String> readFile(String fileName) throws Exception{
		ArrayList<String> retVal = new ArrayList<String>();
		
		try{
			//read file
			InputStream in = RectangleArt.class.getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			//loop through lines of input file
			String s;
			while((s = br.readLine()) != null) { 
				retVal.add(s);
			}
		}catch(Exception e){
			throw e;
		}
		
		return retVal;
	}
}
