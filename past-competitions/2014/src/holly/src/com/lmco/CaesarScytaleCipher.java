package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * CodeQuest 2014
 * Problem 12: Caesar Scytale Cipher
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 * 
 * This problem can be worked in the same order you would do by hand....decoding one string and then decoding it again.
 * The trick here is to figure out the size of the 2D array that is needed for the Scytale cipher.  Since the size is not
 * given in the problem you have to calculate all the possible sizes and try each one till the breakout condition is achieved
 * (the presense of Dear in the output string)
 *
 */
public class CaesarScytaleCipher {
	
	public static final String FILENAME = "Prob12.in.txt";
	
	//I used a single array here to contain both uppers and lowers for the alphabet. 
	//In the loop below I control shifting across lowercase letters by forcing the index after 0 to be 25
	//and likewise the index of 26 to go back to the end 51.
	//Therefore, lowercase letters will shift between 0-->25 and uppercase letters from 26-->51
	public static char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<String> output = new ArrayList<String>();
		
		try{

			//get input file as List
			ArrayList<String> input = readFile(FILENAME);
			
			//get input file as one long String
			String longString = "";
			int i=0;
			while(i<input.size()){
				String temp = input.get(i);
				longString+=temp;
				i++;
			}
			
			
			//get all possible array sizes based on the longString.length() since you don't yet know the size
			ArrayList<String[][]> arraySizes = buildArrays(longString.length());
			
			boolean done = false;
			int arrayCounter = 0;
			
			//loop through the array sizes until done is achieved or all sizes have been exhausted
			while(!done && arrayCounter < arraySizes.size()){
				
				String[][] sArray = arraySizes.get(arrayCounter);
				
				//fill Array with long string going top->down, then left->right
				sArray = fillArray(sArray, longString);
	
				//unravel the Array using Scytale cipher method, return as one String
				String scytaleDecoded = ""; 
				
				for(int row=0; row<sArray.length; row++){
					for(int col=0; col<sArray[row].length; col++){
						scytaleDecoded += sArray[row][col];
					}
				}
				
				//check for extra X's, recursively remove
				scytaleDecoded = removeExtraX(scytaleDecoded);
				
				//now do Caesar cipher decoding (one String)
				String caesarScytaleDecoded = decodeCaesar(scytaleDecoded);
				
				//check the breakout condition of the cipher
				if(caesarScytaleDecoded.contains("Dear")){
					//substring into original input lengths 
					i=0;
					int startIdx = 0;
					int endIdx = 0;
					while(i<input.size() && endIdx<caesarScytaleDecoded.length()){
						int length = input.get(i).length();
						endIdx = (startIdx + length);

						String temp = caesarScytaleDecoded.substring(startIdx, endIdx);
							
						output.add(temp);
						//move index down the string
						startIdx = endIdx; 
						//increment
						i++;
					}
					
					printOutput(output);
					//all done, terminate loop
					done = true;
					
				} else{
					//This decoded String does not contain the breakout condition so we need to try other array size
					done = false;
				}
				
				//don't forget to increment your counter to prevent a never-ending loop
				arrayCounter++;  
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	private static String decodeCaesar(String string) {
		//contains the string as it is shifting
		String newString = "";
	
		//keep track of number of shifts, can only shift 25 times or it will be back to the original String string
		int shiftCounter = 0;  
		
		//keep shifting 1 until find DEAR
		while(!newString.contains("Dear") && shiftCounter<26){
			//reset because of the usage of += in the while loop
			newString = ""; 
			
			//shift all letters 1
			int idx=0;
			while(idx<string.length()){
				newString += shiftLetter(string.charAt(idx));
				idx++;
			}
			
			string = newString;
			shiftCounter++;
		}
		
		
		return newString;
	}

	/**
	 * Using array with both lower and uppercase letters
	 * @param c
	 * @return
	 */
	private static String shiftLetter(char c){
		
		for(int i=0; i<alpha.length; i++){
			if(alpha[i] == c){
				//found letter in uppercase array
				//return one index prior
				//check for end of array, while being careful to return same case.
				
				 //at beginning of lower case section,
				if(i==0)
					return alpha[25] + "";  //jump to the end of lower case section
				//at the beginning of the uppercase section
				else if(i==26) 
					return alpha[51] + "";  //jump to the end of the upper case section
				else
					return alpha[i-1] + "";
			}
		}
		return null;
	}
	
	private static void printOutput(ArrayList<String> output) {
		int i=0;
		while(i<output.size()){
			System.out.println(output.get(i));
			i++;
		}		
	}

	private static String removeExtraX(String temp) {
		
		if(temp!=null && temp.length()>0 && temp.endsWith("X")){
			return removeExtraX(temp.substring(0, temp.length()-1));
		}
		return temp;
	}

	private static String[][] fillArray(String[][] sArray, String longString) {
		
		if(sArray!=null){
			int index = 0;
			for(int col=0; col<sArray.length && index<longString.length(); col++){
				for(int row=0; row<sArray.length; row++){
					sArray[row][col] = longString.charAt(index) + "";
					index++;
				}
			}
		}
		
		return sArray;
	}

	/**
	 * Using the given length of the String calculate the possible sizes where
	 * the String can fit perfectly into the 2D array.
	 * These sizes will be used later in the cipher
	 * @param length
	 * @return
	 */
	private static ArrayList<String[][]> buildArrays(int length) {
		ArrayList<String[][]> listOfArrays = new ArrayList<String[][]>();
		
		for(int i=3; i<length; i++){
			if(length % i ==0){
				//is a multipe
				String[][] s = new String[length/i][i];
				listOfArrays.add(s);
			}
		}
		
		return listOfArrays;
	}

	private static ArrayList<String> readFile(String fileName) throws Exception{
		ArrayList<String> retVal = new ArrayList<String>();
		
		try{
			//read file
			InputStream in = CaesarScytaleCipher.class.getResourceAsStream(fileName);
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
