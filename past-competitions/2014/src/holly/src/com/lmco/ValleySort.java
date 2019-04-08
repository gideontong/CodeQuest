package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CodeQuest 2014
 * Problem 3: Valley Sort
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 * 
 * As the file is read, the program splits the input upon the space character and builds an array of 
 * Integer objects.  I chose to deal with Integer objects instead of String so that I can use the Collections
 * class to naturally sort the Integers first before doing the Valley Sort.  If String was used they would not be
 * sorted correctly. (number 10 would come after number 1).
 * After I have the List of sort Integers, I initialize a String to contain the output that will eventually be
 * printed.  I start that string out with the first digit in the Integer List, which is the lowest number. I build
 * the rest of the string around this number, keeping it in the middle.
 * I loop through the sorted Integer list and build the output String by alternating the location where I 
 * insert the Integer.  The Integer is either appended to the beginning or the end of the output String and then a boolean
 * switch is switched for the next iteration of the loop, thus guaranteeing that the lowest Integer stays in the middle
 *
 * For checking evenness, I chose to use the binary AND (&). Whenever the bitwise version of the number ends in a 1, it is odd
 * and when it ends in a 0 is even. Therefore, doing a bitwise & with a 1 will return 0 for even number, and 1 for odd number.  
 * You can also check evenness using the remainder (%) check of division by 2.
 */
public class ValleySort {

	public static final String FILENAME="Prob03.in.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			//read file
			InputStream in = ValleySort.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//loop through lines of input file
			String s;

			while((s = br.readLine()) != null) { 
				//array to contain one line of input
				String[] sArray = s.split(" ");
				
				//turn it to list of Integers so we can sort them naturally first
				List<Integer> sList = arrayToIntegerList(sArray);
				
				//sort list naturally
				Collections.sort(sList);
				
				//outputLine will contain the final sorted string
				//start with the first index from the sorted list (sList) of Integers (lowest number).  Add a space
				String outputLine = sList.get(0) + " ";

				//check for evenness to decide where to start adding to the outputLine String
				//if the number of digits in the input string is even, add the next digit to the beginning of the outputLine
				//if the number of digits in the input string is odd, add the next digit to the end of the outputLine
				boolean addToEnd = ((sList.size() & 1) == 0) ? (false) : true;
				
				//loop through the rest of the input line, start at index 1 because we already added the first digit to our outputLine String
				for(int i=1; i<sList.size(); i++){
					
					//if number of digits in sList is odd
					if(addToEnd){
						//add the value at this index to the end of the outputLine String that we are building, plus a space between
						outputLine = outputLine + sList.get(i) + " ";
						
						//switch, so that next time it adds to the beginning
						addToEnd = false;
					
					}else{
						//add the value at this index to the beginning of the outputLine String that we are building, plus a space between
						outputLine = sList.get(i) + " " + outputLine;
						
						//switch, so that next time it adds to the beginning
						addToEnd = true;
					}
					
				}//end of for loop
				
				//print output
				System.out.println(outputLine.trim());
				
			} //end line

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Method that loops through the given array of Strings 
	 * and uses the Integer parseInt() to create a List of Integer
	 * objects.
	 * @param s
	 * @return
	 */
	private static List<Integer> arrayToIntegerList(String[] s){
		
		List<Integer> list = new ArrayList<Integer>();
		
		if(s!=null && s.length>0){
			for(int i=0; i<s.length; i++){
				list.add(Integer.parseInt(s[i]));
			}
		}
		
		return list;
	}
}
