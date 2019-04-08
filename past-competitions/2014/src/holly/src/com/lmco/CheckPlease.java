package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * CodeQuest 2014
 * Problem 9: Check Please
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 * In this problem I build lists of reusable text for the digits based on position (ones and tens).  These can be reused in the hundreds and thousands digit position.
 * Teens digits are different however, they need their own list because the text for the teens differ too much from the ones and tens (i.e. Eleven).  I can still reuse
 * these in both the thousands and the hundreds.  This saves a lot of time and is simplier to implement. 
 * Now, its just a matter of breaking apart the given number into pieces and translating them in order.
 * 
 * Watch for cases of plural dollars and cents!
 */
public class CheckPlease {
	
	public static final String FILENAME = "Prob09.in.txt";

	public static Hashtable<Integer, String> tens = new Hashtable<Integer, String>();
	public static Hashtable<Integer, String> ones = new Hashtable<Integer, String>();
	public static Hashtable<Integer, String> teens = new Hashtable<Integer, String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		buildTables();

		try{
			//read file
			InputStream in = CheckPlease.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//loop through lines of input file
			String s;
			while((s = br.readLine()) != null) { 
				StringBuffer sb = new StringBuffer();

				if(!s.contains("."))
					s = s + ".00";
				
				String[] temp =  s.split("\\.");
				String dollars = temp[0];
				String cents = temp[1];
				
				//handle dollars first
				
				if(dollars.length()>=4){
					//handle thousands
					
					if(dollars.length()==6){
						
						String digit1 = dollars.substring(0, 1);
						if(!digit1.equals("0")){
							sb.append(doHundreds(digit1));
						}
						
						String digit2 = dollars.substring(1, 2);
						String digit3 = dollars.substring(2, 3);
						sb.append(doTensPosition(digit2, digit3));
						sb.append("Thousand ");
						
						String digit4 = dollars.substring(3, 4);
						if(!digit4.equals("0")){
							sb.append(doHundreds(digit4));
						}
						
						String digit5 = dollars.substring(4, 5);
						String digit6 = dollars.substring(5);
						sb.append(doTensPosition(digit5, digit6));
					}
					
					if(dollars.length()==5){
						
						String digit1 = dollars.substring(0, 1);
						String digit2 = dollars.substring(1, 2);
						sb.append(doTensPosition(digit1, digit2));
						sb.append("Thousand ");
						
						String digit3 = dollars.substring(2, 3);
						if(!digit3.equals("0")){
							sb.append(doHundreds(digit3));
						}
						
						String digit4 = dollars.substring(3, 4);
						String digit5 = dollars.substring(4);
						sb.append(doTensPosition(digit4, digit5));
					}
					
					if(dollars.length()==4){
						
						String digit1 = dollars.substring(0, 1);
						sb.append(ones.get(new Integer(digit1)) + " Thousand ");
						
						String digit2 = dollars.substring(1, 2);
						if(!digit2.equals("0")){
							sb.append(doHundreds(digit2));
						}
						
						String digit3 = dollars.substring(2, 3);
						String digit4 = dollars.substring(3, 4);
						sb.append(doTensPosition(digit3, digit4));
					}
					
					sb.append("Dollars");
					
				} else if(dollars.length()==3){
					//do hundreds
					
					String digit1 = dollars.substring(0, 1);
					sb.append(doHundreds(digit1));
					
					String digit2 = dollars.substring(1, 2);
					String digit3 = dollars.substring(2, 3);
					sb.append(doTensPosition(digit2, digit3));
					
					sb.append("Dollars");
					
				} else if(dollars.length()==2){
					String digit1 = dollars.substring(0, 1);
					String digit2 = dollars.substring(1, 2);
					sb.append(doTensPosition(digit1, digit2));
					
					sb.append("Dollars");
					
				} else if(dollars.length()==1){
					String digit1 = dollars.substring(0, 1);
					
					if(digit1.equals("0")){
						sb.append("Zero ");
					}else{
						sb.append(doOnesPosition(digit1));
					}
					
					if(digit1.equals("1"))
						sb.append("Dollar");
					else
						sb.append("Dollars");
					
				} else if(dollars.length()==0){
					//print Zero as Dollars, not 'Zero Dollar'
					sb.append("Zero Dollars");
				}
				
				
				//handle cents
				
				sb.append(" and ");
				
				if(cents.substring(0, 1).equals("0")){
					//single digit cent
					if(cents.substring(1).equals("1"))
						sb.append(cents.substring(1) + " Cent");
					else 
						sb.append(cents.substring(1) + " Cents");
				} else {
					sb.append(cents + " Cents");
				}
				
				System.out.println(sb.toString());
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * Do the hundreds position 
	 * @param s
	 * @return
	 */
	private static String doHundreds(String s){
		StringBuffer sb = new StringBuffer();
		sb.append(ones.get(new Integer(s)) + " Hundred ");
		return sb.toString();
	}
	
	/**
	 * Do tens position, while taking into account a 0, meaning that it is really just a 1 digit number
	 * @param d1
	 * @param d2
	 * @return
	 */
	private static String doTensPosition(String d1, String d2){
		StringBuffer sb = new StringBuffer();
		if(d1.equals("0")){
			//only do ones position, skip d1
			sb.append(doOnesPosition(d2));
			
		} else if(d1.equals("1")){
			//check next position too
			String d = d1 + "" + d2;
			//use teens list
			sb.append(teens.get(new Integer(d)) + " ");
			
		} else {
			//tens and ones position
			sb.append(tens.get(new Integer(d1)) + " ");
			sb.append(doOnesPosition(d2));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	private static String doOnesPosition(String s){
		StringBuffer sb = new StringBuffer();
		
		if(!s.equals("0")){
			sb.append(ones.get(new Integer(s)) + " ");
		}else{
			//a zero, don't add anything
			return "";
		}
		return sb.toString();
	}
	
	/**
	 * Build reusable text instead of typing out all possible combinations of tens, hundreds, thousands, and dollars
	 */
	private static void buildTables(){
		
		ones.put(0, "Zero");
		ones.put(1, "One");
		ones.put(2, "Two");
		ones.put(3, "Three");
		ones.put(4, "Four");
		ones.put(5, "Five");
		ones.put(6, "Six");
		ones.put(7, "Seven");
		ones.put(8, "Eight");
		ones.put(9, "Nine");
		
		tens.put(1, "Ten");
		tens.put(2, "Twenty");
		tens.put(3, "Thirty");
		tens.put(4, "Forty");
		tens.put(5, "Fifty");
		tens.put(6, "Sixty");
		tens.put(7, "Seventy");
		tens.put(8, "Eighty");
		tens.put(9, "Ninety");
		
		teens.put(0, "Ten");
		teens.put(1, "Eleven");
		teens.put(2, "Twelve");
		teens.put(3, "Thirteen");
		teens.put(4, "Fourteen");
		teens.put(5, "Fifteen");
		teens.put(6, "Sixteen");
		teens.put(7, "Seventeen");
		teens.put(8, "Eightteen");
		teens.put(9, "Nineteen");
		teens.put(10, "Ten");
		teens.put(11, "Eleven");
		teens.put(12, "Twelve");
		teens.put(13, "Thirteen");
		teens.put(14, "Fourteen");
		teens.put(15, "Fifteen");
		teens.put(16, "Sixteen");
		teens.put(17, "Seventeen");
		teens.put(18, "Eighteen");
		teens.put(19, "Nineteen");
		
	}
	
//	private static String[] mySplit(String s){
//		String[] temp = s.split(".");
//	}
}
