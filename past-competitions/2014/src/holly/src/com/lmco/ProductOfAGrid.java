package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * CodeQuest 2014
 * Problem 6: Product of A Grid
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 */
public class ProductOfAGrid {

	public static final String FILENAME = "Prob06.in.txt";
	
	public static void main(String[] args) {

		try{
			
			Integer[][] grid = readFile(FILENAME);

			//variable used to retain the maximum product found during execution
			Integer maxProduct = 1;
			
			/*
			 * The order which you run through the combinations of 4 numbers does not matter in this case
			 * because if the current iteration is greater than the maxProduct you replace maxProduct with 
			 * the current product....and so on till all possibilities are exhausted.
			 */
			
			//horizontal products across the rows, incrementing from the starting position +0, then +1, then +2, then +3.
			//for each row
			for(int row=0; row<grid.length; row++){
				//initialize a counter to control the incrementing value from the starting position
				int colCounter=0;
				//substracting 3 here in the condition because you don't want to step out of bounds of the array
				//if you attempted to start at position 19 and then grabbed the next 3 numbers you would attempt indexes: 19, 20, 21, 22
				//20, 21, and 22 are all out of bounds
				while(colCounter<grid.length-3){  
					Integer product=1;
					//starting at colCounter grab the next adjecent numbers
					for(int col=colCounter; col<colCounter+4; col++){
						product = product * grid[row][col];
					}
					
					//check if the current product is greater than current max, replace it if true
					if(product>maxProduct)
						maxProduct = product;
					
					colCounter++;
				}
			}
			
			
			//vertical products down each row, incrementing from the starting position +0, then +1, then +2, then +3.
			//for each column
			for(int col=0; col<grid.length; col++){
				int counter=0;
				while(counter<grid.length-3){
					Integer product=1;
					for(int row=counter; row<counter+4; row++){
						product = product * grid[row][col];
					}
					
					if(product>maxProduct)
						maxProduct = product;
					
					counter++;
				}
			}
			
			
			//diagonals, top left to bottom right
			//-------------------------------------------------------------------------------------------------
			//for each row, and for each column: use each position as a starting point
			//then increment for the 3 adjacent numbers,
			//incrementing the indexes by the same amount (incr variable) each time guarantees a diagonal going
			//from top left to bottom right: (0,0)*(1,1)*(2,2)*(3,3).  
			//Uncomment the println statements to see good debug messages for each iteration of this loop
			for(int row=0; row<grid.length-3; row++){
				
				for(int col=0; col<grid.length-3; col++){
					
					Integer product=1;
					for(int incr=0; incr<4; incr++){
//						System.out.println("Getting (row:"+(row+incr)+", col:"+(col+incr)+") = " + grid[row+incr][col+incr]);
						product = product * grid[row+incr][col+incr];
					}
//					System.out.println("PRODUCT=               " + product);
					if(product>maxProduct)
						maxProduct = product;
					
				}
				
			}


			//diagonals, from top right to bottom left
			//-------------------------------------------------------------------------------------------------
			//for each row, and for each column: use each position as a starting point
			//NOTICE the starting value of the int(s) in the for loops.
			//Make sure not to step out of bounds of the array by initializing the values with the correct offset
			//and also stopping the loop before you reach the max bound (length-3)
			//Then increment for the 3 adjacent numbers,
			//To gaurantee the diagonal in thise case we add the incr variable to the row index and
			//decrement the incr variable from the col index.
			//from top right to bottom left: (0,3)*(1,2)*(2,1)*(3,0).  
			//Uncomment the println statements to see good debug messages for each iteration of this loop
			for(int row=0; row<grid.length-3; row++){
				
				//now explore each column in the row, being mindful of the bounds of the array
				//since this diagonal is going backward, we start at index 3 and then decrement in the for loop below to get product
				for(int col=3; col<grid.length; col++){
					
					Integer product=1;
					for(int incr=0; incr<4; incr++){
//						System.out.println("Getting (row:"+(row+incr)+", col:"+(col-incr)+") = " + grid[row+incr][col-incr]);
						//look! decrementing the col index by the incr amount
						product = product * grid[row+incr][col-incr];
					}
//					System.out.println("PRODUCT=               " + product);
					if(product>maxProduct)
						maxProduct = product;
					
				}
			}

			
			System.out.println("Greatest product: "+maxProduct);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	private static Integer[][] readFile(String fileName) throws Exception{
		
		Integer[][] retVal = new Integer[20][20];  //initializing to a number, will use the first line of the input to set the real size
		
		try{
			//read file
			InputStream in = ProductOfAGrid.class.getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			//loop through lines of input file
			String s;
			int counter=0;
			while((s = br.readLine()) != null) { 
				
				String[] sArray = s.split(" ");
				
				if(counter==0){
					//initialize 2-D array based on first line of input which determines the size
					retVal = new Integer[sArray.length][sArray.length];
				}
				
				for(int i=0; i<sArray.length; i++){
					retVal[counter][i] = Integer.parseInt(sArray[i]);
				}
				counter++;
			}
		}catch(Exception e){
			throw e;
		}
		
		return retVal;
	}
	
}
