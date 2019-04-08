package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * CodeQuest 2014
 * Problem 7: Morse Code
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 * As the input file is read in, I check for the presense of "END OF TRANSMISSION" so that I know which direction to translate the string.
 * I break the line into words and then each letter in the word is translated using 
 * a simple string lookup table and then its printed to the stdout.  I keep track of the end of the word and line 
 * so that the appropriate spacing is maintained.
 * 
 */
public class MorseCode {

	public static final String FILENAME = "Prob07.in.txt";
	
	public static String SPACE_BETWEEN_WORDS = "_______";
	public static String SPACE_BETWEEN_LETTERS = "___";
	
	public static Hashtable<String, String> MORSE_CODE;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//This method initializes a hashtable of morse code translated values for the alphabet
		//I chose to initialize it in this method so that the code would be a little cleaner and easier to read
		buildKeyTable();
		
		try{
			//read file
			InputStream in = MorseCode.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//loop through lines of input file
			String s;

			boolean firstTransmission = true;
			
			while((s = br.readLine()) != null) { 
				String line = s;
				
				//don't attempt to translate the input line containing the END OF TRANSMISSION text
				if(!line.equals("END OF TRANSMISSION")){
					
					if(firstTransmission){
						//translating from english to morse code
						String[] wordOnThisLine = line.split(" ");
						
						//translate each word
						for(int i=0; i<wordOnThisLine.length; i++){
							
							String word = wordOnThisLine[i];
							
							//translate each letter
							for(int j=0; j<word.length(); j++){

								//using the charAt() method with an int index to translate each letter in this String
								String letter = word.charAt(j)+""; 
							
								//converting to uppercase because all my keys in the lookup table are uppercase
								letter = letter.toUpperCase();   
								
								//just to be sure I check contains before getting the translation
								if(MORSE_CODE.containsKey(letter)){
									
									System.out.print(MORSE_CODE.get(letter));
									//checking if at the end, needs a space between the letters if not at the end
									if(j<word.length()-1)  
										System.out.print(SPACE_BETWEEN_LETTERS);
								}
							} //end of for loop for letter
							
							//checking to see if we are at the end
							if(i<wordOnThisLine.length-1)
								System.out.print(SPACE_BETWEEN_WORDS);
						}
						
						//print return
						System.out.println("");
						
					}else{
						//translating from morse code to english
						
						String[] wordOnThisLine = line.split(SPACE_BETWEEN_WORDS);
						
						//translate each word
						for(int i=0; i<wordOnThisLine.length; i++){
							
							String word = wordOnThisLine[i];
							
							//splitting the word into an array of letters using the provided SPACE_BETWEEN_LETTERS value
							String[] code = word.split(SPACE_BETWEEN_LETTERS);
							
							//translate each letter
							for(int j=0; j<code.length; j++){
								
								String c = code[j].toUpperCase(); 
								
								if(MORSE_CODE.containsKey(c)){
									System.out.print(MORSE_CODE.get(c).toLowerCase());
								}
							}
							
							//checking to see if we are at the end
							//print a space between the words, but not at the end
							if(i<wordOnThisLine.length-1)
								System.out.print(" ");  
						}
						
						//print return
						System.out.println("");
					}
					
				}else{
					//skip and switch to next message
					System.out.println(line);
					
					//set boolean
					firstTransmission = false;
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private static void buildKeyTable() {

		MORSE_CODE = new Hashtable<String, String>();
		
		MORSE_CODE.put("A", "=_===");
		MORSE_CODE.put("B", "===_=_=_=");
		MORSE_CODE.put("C", "===_=_===_=");
		MORSE_CODE.put("D", "===_=_=");
		MORSE_CODE.put("E", "=");
		MORSE_CODE.put("F", "=_=_===_=");
		MORSE_CODE.put("G", "===_===_=");
		MORSE_CODE.put("H", "=_=_=_=");
		MORSE_CODE.put("I", "=_=");
		MORSE_CODE.put("J", "=_===_===_===");
		MORSE_CODE.put("K", "===_=_===");
		MORSE_CODE.put("L", "=_===_=_=");
		MORSE_CODE.put("M", "===_===");
		MORSE_CODE.put("N", "===_=");
		MORSE_CODE.put("O", "===_===_===");
		MORSE_CODE.put("P", "=_===_===_=");
		MORSE_CODE.put("Q", "===_===_=_===");
		MORSE_CODE.put("R", "=_===_=");
		MORSE_CODE.put("S", "=_=_=");
		MORSE_CODE.put("T", "===");
		MORSE_CODE.put("U", "=_=_===");
		MORSE_CODE.put("V", "=_=_=_===");
		MORSE_CODE.put("W", "=_===_===");
		MORSE_CODE.put("X", "===_=_=_===");
		MORSE_CODE.put("Y", "===_=_===_===");
		MORSE_CODE.put("Z", "===_===_=_=");
		
		MORSE_CODE.put("1", "=_===_===_===_===");
		MORSE_CODE.put("2", "=_=_===_===_===");
		MORSE_CODE.put("3", "=_=_=_===_===");
		MORSE_CODE.put("4", "=_=_=_=_===");
		MORSE_CODE.put("5", "=_=_=_=_=");
		MORSE_CODE.put("6", "===_=_=_=_=");
		MORSE_CODE.put("7", "===_===_=_=_=");
		MORSE_CODE.put("8", "===_===_===_=_=");
		MORSE_CODE.put("9", "===_===_===_===_=");
		MORSE_CODE.put("0", "===_===_===_===_===");
		
		MORSE_CODE.put("=_===", "A");
		MORSE_CODE.put("===_=_=_=", "B");
		MORSE_CODE.put("===_=_===_=", "C");
		MORSE_CODE.put("===_=_=", "D");
		MORSE_CODE.put("=", "E");
		MORSE_CODE.put("=_=_===_=", "F");
		MORSE_CODE.put("===_===_=", "G");
		MORSE_CODE.put("=_=_=_=", "H");
		MORSE_CODE.put("=_=", "I");
		MORSE_CODE.put("=_===_===_===", "J");
		MORSE_CODE.put("===_=_===", "K");
		MORSE_CODE.put("=_===_=_=", "L");
		MORSE_CODE.put("===_===", "M");
		MORSE_CODE.put("===_=", "N");
		MORSE_CODE.put("===_===_===", "O");
		MORSE_CODE.put("=_===_===_=", "P");
		MORSE_CODE.put("===_===_=_===", "Q");
		MORSE_CODE.put("=_===_=", "R");
		MORSE_CODE.put("=_=_=", "S");
		MORSE_CODE.put("===", "T");
		MORSE_CODE.put("=_=_===", "U");
		MORSE_CODE.put("=_=_=_===", "V");
		MORSE_CODE.put("=_===_===", "W");
		MORSE_CODE.put("===_=_=_===", "X");
		MORSE_CODE.put("===_=_===_===", "Y");
		MORSE_CODE.put("===_===_=_=", "Z");
		
		MORSE_CODE.put("=_===_===_===_===", "1");
		MORSE_CODE.put("=_=_===_===_===", "2");
		MORSE_CODE.put("=_=_=_===_===", "3");
		MORSE_CODE.put("=_=_=_=_===", "4");
		MORSE_CODE.put("=_=_=_=_=", "5");
		MORSE_CODE.put("===_=_=_=_=", "6");
		MORSE_CODE.put("===_===_=_=_=", "7");
		MORSE_CODE.put("===_===_===_=_=", "8");
		MORSE_CODE.put("===_===_===_===_=", "9");
		MORSE_CODE.put("===_===_===_===_===", "0");
	}

}
