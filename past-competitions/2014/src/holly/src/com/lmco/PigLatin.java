package com.lmco;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * CodeQuest 2014
 * Problem 4: Pig Latin
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 * In this problem the input file contains phrase, so the first thing to do is split these phrases
 * into words so that they can be translated.
 * For each word, call the translate() method and then print.  
 * Be careful during printing...since these words need to be put back into phrases
 * you need to use print() instead of println() inbetween the words and then for the last
 * word on a line use println() to terminate that line.
 *
 */
public class PigLatin {
	
	public static final String FILENAME = "Prob04.in.txt";
	
	//I decided to key my pig latin translate() method off of the occurrence of a vowel.  
	//so i need a lookup variable for all possible vowels.
	//Making it final because i don't want anything modifying this constant
	public static final String[] vowels = {"a", "e", "i", "o", "u" };
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{
			//read the file in as a List of String objects
			ArrayList<String> phrases = readFile(FILENAME);
		
			//use a counter so you know when you are at the end of the input and can terminate the loop
			int counter = 0;
			
			while(counter<phrases.size()){
				
				String phrase = phrases.get(counter);
				
				//call split on a space to get an array of words
				String[] words = phrase.split(" ");
				
				for(int i=0; i<words.length; i++){
					//for each word on this line of input, call translate and print
					System.out.print(translate(words[i]));
					
					if(i==words.length-1){
						//last one: use println to terminate this line
						System.out.println("");
					}else{
						//else, print a space to separate the words, essentially building the phrase back
						System.out.print(" "); 
					}
				}
				
				//don't forget to increment the counter or you will have an endless while loop
				counter++;
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	/**
	 * This translate gets called for every word in a phrase
	 * First thing to do is determine where to split the string.  I do this
	 * by finding the first index of a vowel character in the given String.
	 * This index tells me where to call substring.  The first part of the String
	 * becomes the last, then append the appropriate pig latin term.
	 * @param word
	 * @return
	 */
	private static String translate(String word){
		
		//find index of first vowel so you know where to substring
		int idx = getFirstVowelIndex(word);
		
		if(idx==0){
			//word starts with a vowel, just add 'yay' to the end
			//return statement terminates this method, no other if stmts below will be executed
			return word + "yay";
		}
		
		if(idx>=1){
			//found a vowel in the given String
			
			//check for the 'qu' exception as specified in the instructions
			if(word.startsWith("q")){
				//when the word starts with a q, the u character needs to follow the q to the end of the string
				if(word.charAt(idx+1)=='u')
					idx=idx+1; //want to include the u in the firstPart, so increase index by one
			}
			//substring from beginning of the word to the index of the first vowel
			String firstPart = word.substring(0, idx);
			//substring the rest
			String lastPart = word.substring(idx);
			//reorder the parts and add 'ay' to the end
			//this gets returned to the calling method
			return lastPart + firstPart + "ay";
		}else{
			//no vowel found....return the word
			return word;  
		}
		
	}
	
	/**
	 * This method takes the given string and loops through each character
	 * to see if it is a vowel.  When the for loop finds a vowel it stops
	 * and returns the index int.
	 * @param s
	 * @return
	 */
	public static int getFirstVowelIndex(String s){
		
		if(s!=null){
			//for each character in the given String s
			for(int i=0; i<s.length(); i++){
				//grab the character at the index i in the String
				String letter = s.charAt(i) + "";
				//check if the letter is one of the vowels in the constant vowel array
				for(int j=0; j<vowels.length; j++){
					if(letter.equalsIgnoreCase(vowels[j])){
						//letter is vowel, return index for substringing
						return i;
					}
				}
			}
		}
		//if no vowels found
		return -1;    
	}
	
	private static ArrayList<String> readFile(String fileName) throws Exception{
		ArrayList<String> retVal = new ArrayList<String>();
		
		try{
			//read file
			InputStream in = PigLatin.class.getResourceAsStream(fileName);
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
