package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * CodeQuest 2014
 * Problem 5: Book Worm
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 * 
 * For this problem I was able to run the logic as the program was reading the file.  For each line you first
 * have to remove any non-numeric digits since these need to be ignored by the program...Regular expression is good for this
 * Next, compute the sum by alternating the multiplication by 1 or by 3.
 * Once you have the sum use the mod operator to check if its divisible by 10.
 * If it's not, you need to remove that last digit because its the wrong one!  If you leave it there and then
 * try to calculate the correct digit you will not get the correct answer.
 * I then loop through possible digits till I get to a sum that is divisible by 10  
 *
 */
public class BookWorm {
	
	public static final String FILENAME = "Prob05.in.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			//read file
			InputStream in = BookWorm.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//loop through lines of input file
			String s;
			
			while((s = br.readLine()) != null) { 
				//remove all other non-numeric characters using a regular expression
				s = s.replaceAll("[^\\d]", "");
				
				//this is a boolean to control when to multiply by 1 or by 3
				boolean multiplyOne = true;
				
				//loop through and find sum
				int sum = 0;
				for(int i=0; i<s.length(); i++){
					
					char temp = s.charAt(i);
					
					//Integer.parseInt works with String not char, so add an empty String to the end of the char to make it a String while not effecting the value
					int tempInt = Integer.parseInt(temp+"");
					
					if(multiplyOne){
						//by 1
						sum += tempInt*1;
						multiplyOne = false;
						
					}else{
						//by 3
						sum += tempInt*3;
						multiplyOne = true;
						
					}
					
				} //end for loop

				if(sum % 10 == 0)
					System.out.println("VALID");
				else{
					//find right check bit
					
					//remove last bit (index length()-1)
					int newSum = sum - Integer.parseInt(s.charAt(s.length()-1)+"");
					
					//what's left over
					for(int i=0; i<10; i++){
						
						int tempSum = newSum+i;
						
						//is it now divisible by 10?
						if(tempSum%10==0){
							//yes, print the i value that was added to get the right sum
							System.out.println(i);
							break;
						}
					}
					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
