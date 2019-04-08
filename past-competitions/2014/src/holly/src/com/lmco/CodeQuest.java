package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * CodeQuest 2014
 * Problem 1: When I Say Code, You Say Quest!
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 * The key to this problem is the mod operator %
 */
public class CodeQuest {

	public static final String FILENAME = "Prob01.in.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		try{
			//read file
			InputStream in = CodeQuest.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//loop through lines of input file
			String s;
			while((s = br.readLine()) != null) { 
				//parse the String as an int for math operations below
				int sInt = Integer.parseInt(s);
				
				String output = "";
				
				//use the mod operator to check the remainder of division.  If the mod is a 0,  the number divides evenly into sInt
				if(sInt % 3==0){
					output = "CODE";
				}
				if(sInt % 7==0){
					output = "QUEST";
				}
				if((sInt%3==0) && (sInt%7==0)){
					output = "CODEQUEST";
				}
				
				if(output.length()==0){
					//no condition met, output original string
					output=s;
				}
				
				System.out.println(output);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
