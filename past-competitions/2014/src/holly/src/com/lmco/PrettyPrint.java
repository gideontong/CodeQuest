package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * CodeQuest 2014
 * Problem 15: Pretty Print
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 * The idea here is to break up the file even more than it already is and then put it back together again.
 * I read the input file in as one long String and ignore the line breaks.  The line breaks that are given are
 * irrelevant because they are incorrect...so why keep them around.
 * I next put every element on its own line (in a String[]) so that I can deal with each element and its contents
 * separately.  Even closing element are a separate index in the String[] sArray.
 * Then I loop through the String[], apply some of the rules to the tags themselves and the contents of the tag
 * Then I put it back together by matching begin and end tags, using a LinkedList pop and push methods to keep track
 * of nested elements.
 * Lastly, I apply the indentation
 * 
 */
public class PrettyPrint {

	public static final String FILENAME = "Prob15.in.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Reading in the file as one long String, ignoring the current line breaks because they are irrelevant to the end results
		String inputAllOnOneLine = readFileAsString(FILENAME);
		
		//First break elements onto there own lines, one element per line (#3).  This will help with further iteration
		//#3(and below), 4(and below)
		String newInput = putElementsOnOwnLine(inputAllOnOneLine);

		//Creating an array of Strings where each index contains only 1 xml element (plus content if it exists)
		String[] sArray = newInput.split("\n");
		
		//Clean up characters within each line, no moving of the lines yet
		//Having each element on its own line helps to simplify fixing the characters within the line because you can deal with whats inside the tag first
		for(int lineCounter=0; lineCounter<sArray.length; lineCounter++){
			
			String s = sArray[lineCounter];
			
			//trim white space
			s=s.trim();
			
			if(s.length()==0){
				//skip this index but don't remove it while inside a loop! Will remove later.
				; 
			}else{
				
				if(s.startsWith("<?")){
					//declaration line, trim the whitespace at this index
					sArray[lineCounter] = s.trim();
					
				} else{
					//Reqs #7, 6
					
					//Starting with the simpler formatting fixes of removing excess space around the tag < and > signs
					s = fixBeginTags(s);
					
					s = fixEndTags(s);
					
					s = fixClosingTags(s);
					
					//Fix the spaces in and surrounding the tag attributes
					//Reqs #8, 9, 10, 11, 13
					s = fixSpaces(s);
					
					//Put back formatted s into the sArray
					sArray[lineCounter] = s;
				}
			}
		}

		//Now nested content, removal of space (above) will help with this iteration
		//#12, 5
		String[] nestedInput = moveNestedContent(sArray);
		
		
		//#1, 2, 4 Clean up blank lines by copying over only the ones with length > 0
		//And putting content onto the next line
		ArrayList<String> linesRemoved = new ArrayList<String>();
		
		for(int lineCounter=0; lineCounter<nestedInput.length; lineCounter++){
			
			String s = nestedInput[lineCounter];
				
			s=s.trim();
			
			if(s.length()==0){
				//blank line, remove it #12
				//don't copy it over
			}else{
				//if has an open tag, with no self-close and no closing tag
				if(s.contains("<") && s.contains(">") && !s.contains("</")){
					//open tag only
					//check for extra text needed to move to nest line
					String theTag = s.substring(s.indexOf("<"), s.indexOf(">")+1);
					String theRest = s.substring(s.indexOf(">")+1);
					
					linesRemoved.add(theTag);
					
					if(theRest!=null && theRest.length()>0){
						//move the rest to next line
						linesRemoved.add(theRest);
					}
					
				}else{
					//just add the whole thing
					linesRemoved.add(s);
				}
			}
		}
		
		//Go through and indent everything with periods #3
		linesRemoved = doIndentation(linesRemoved);
	
		//Print output using a loop, put it in a method because this one was long enough!
		printOutput(linesRemoved);
		
	}
	

	/**
	 * All the lines in the sArray now beginning with a < because of previous formatting, 
	 * So, we need to check each closing tag to match it with a beginning tag.  I am not considering
	 * the indentation yet.  That will be a later pass through the sArray
	 * This method uses a LIFO stack approach to keep track of moving nested and non-nested tags to other lines.
	 * It uses push, peek, and pop methods.  You start by putting a begin tag onto the stack.  If the next
	 * tag is a closing tag and is the same element name as the top of the stack they are a match with no tags in 
	 * between them.  However, if the next tag in the loop is another begin tag, that element name needs to be
	 * added to the stack until its closing tag is found.  This means there is nested content because it pushed
	 * two element names but hasn't popped any. 
	 * When an element name does match the top element in the stack, it is popped off the stack.
	 * If there are no tags between the two (elementName at counter index == elementName at counter-1 index)
	 * the lines are combined because there is no nested content.
	 * @param sArray
	 * @return
	 */
	private static String[] moveNestedContent(String[] sArray) {
		
		//Stack of the element names contained in the input file
		LinkedList<String> elementNames = new LinkedList<String>();
		
		for(int counter=0; counter<sArray.length; counter++){
			
			String line = sArray[counter];
			
			//all lines are going to start with a < now
			//but we need to check if they are a closing tag so we can match it to a beginning tag
			
			if(!line.contains("<?xml")){
				
				if(line.startsWith("</")){
					
					//Opening of a close tag, Get the name of this tag and see if the matching tag is on the stack 
					//substring using index 2 so you skip the "</" characters i.e. 2-->next space
					String elementName = line.substring(2, line.indexOf(">")); 
					
					//For this we can trim the whitespace to get just the tag name
					elementName = elementName.trim();
					
					//Check if this name is the next one on the stack using a peek method
					if(elementNames.peekFirst().equalsIgnoreCase(elementName)){
						
						//We have matched the tags, no nested tags
						//Now pop it from stack and add it to the previous line, if no nested content!
						if(sArray[counter-1].contains(elementName)){  
							//Meaning the tag above is the same element name
							
							//Combine the linds by adding them together because there is no nested tags, this include the content
							sArray[counter-1] += sArray[counter]; 
							
							//Clear the current loop index out but don't attempt to remove it during a loop, will be removed later
							sArray[counter] = "";  
							
							//pop it off the stack
							elementNames.pop();
						}
						
					}else{
						//This element name is not on the top of the stack
						//There is nested content, nested tags stay on their own line in this case, so we can't combine the lines
						//Push this element to the stack, unless self-closing, because there will not be an explicit closing tag
						if(!elementName.contains("/>")){
							//push element name
							elementNames.push(elementName);
						}
					}
					
				} else if(line.startsWith("<") && line.contains("/>")){
					//Self closing-tag, skip because there is no tag to match with
					;
					
				} else{
					//If not a closing tag and not a self-closing tag, then this line contains an open tag

					//Find the element name
					String elementName = null;
					
					if(line!=null && line.length()>0){
						//Substring to get just the tag
						String tag = line.substring(line.indexOf("<"), line.indexOf(">"));
						
						//Check if this tag has attribute that we need to consider when getting the tag name
						if(tag.indexOf(" ")>-1){
							//Tag with attributes, substring till the first space character, but don't include the space character
							elementName = line.substring(line.indexOf("<")+1, line.indexOf(" "));
						}else{
							//No attributes, just substring between the < and the >
							elementName = line.substring(line.indexOf("<")+1, line.indexOf(">"));
						}
						//This really isn't necessary, but i did it anyway
						elementName = elementName.trim();
						
						//Push this element on to the stack of elementNames, it will be checked later for nested content and closing tag
						elementNames.push(elementName);
					
					}

				}
			
			}
		}
		
		return sArray;
		
	}

	/**
	 * This method is similar to the moveNestedContent method in that is uses push and pop of a stack
	 * to keep track of beginning tags, closing tags, and nested tags.
	 * I did this at the end because I wanted all the other formatting to be done first so I didn't have
	 * to worry about the extra periods getting in the way.  All the tags and content are on the correct
	 * lines so I only have to worry about preceeding the content with the right number of periods
	 * @param sList
	 * @return
	 */
	private static ArrayList<String> doIndentation(ArrayList<String> sList) {
		
		LinkedList<String> elementNames = new LinkedList<String>();
		
		for(int counter=0; counter<sList.size(); counter++){
			
			String line = sList.get(counter);
			
			if(!line.contains("<?xml")){
				
				String elementName = null;
				
				if(line.contains("<") || line.contains(">")){
					
					String tag = line.substring(line.indexOf("<"), line.indexOf(">"));
					
					if(tag.indexOf(" ")>-1){
						//tag with attributes
						elementName = line.substring(line.indexOf("<")+1, line.indexOf(" "));
					}else{
						elementName = line.substring(line.indexOf("<")+1, line.indexOf(">"));
					}
				
					//closing tag, try to match it to the open tag on the stack
					if(line.startsWith("</")){
						//opening of a close tag
						//get name of this tag
						elementName = elementName.substring(1);  //2-->to end
						//for this we can trim the whitespace to get just the tag name
						elementName = elementName.trim();
						//check if this name is the next one on the stack
						if(elementNames.peekFirst().equalsIgnoreCase(elementName)){
							//we have matched the tags, no nested
							elementNames.pop();
							sList.set(counter, addPeriods(elementNames.size(), line));
						}
						
					} else 	if( (line.startsWith("<") && line.contains("/>")) || (line.contains("<"+elementName) && line.contains("</"+elementName))){
						//self closing-tag || a tag that already closes on this line
						//add same level of indentation as above
						//don't put this one on the stack because its closing tag is already matched with the open tag
						sList.set(counter, addPeriods(elementNames.size(), line));
						
					} else{
						//opening tag only, or plain text
	
						sList.set(counter, addPeriods(elementNames.size(), line));
						
						//add name to stack
						elementNames.push(elementName);
					}
					
				} else{
					//just plain text
					sList.set(counter, addPeriods(elementNames.size(), line));
				}
			}
		}
		
		//Return the list with the periods inserted
		return sList;
	}
	
	/**
	 * Reusable util method 
	 * Creates a prefix by using the given nesting level: It adds the correct multiple of periods using a loop from 0 to nestingLevel
	 * Then appends the prefix to the original String origString.
	 * @param nestingLevel
	 * @param origString
	 * @return
	 */
	private static String addPeriods(int nestingLevel, String origString){
		String prefix = "";
		
		if(nestingLevel==0)
			return origString;  //no change
		
		for(int i=0; i<nestingLevel; i++){
			prefix = "...."+prefix;
		}
		
		return prefix + origString;
	}
	
	private static String readFileAsString(String fileName) {
		
		StringBuffer sb = new StringBuffer();
		
		try{
			//read file
			InputStream in = PrettyPrint.class.getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			//loop through lines of input file
			String s;
			while((s = br.readLine()) != null) { 
				sb.append(s);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sb.toString();
	}


	private static String putElementsOnOwnLine(String input) {
		
		StringBuffer sb = new StringBuffer();
		
		for(int counter=0; counter<input.length(); counter++){
			
			char c = input.charAt(counter);
			
			if(c=='<'){
				//add line return
				sb.append("\n"+c);
			}else{
				sb.append(c);
			}
			
		}
		return sb.toString();
	}
	
	/**
	 * Takes the given String and removes excess spaces before, after, and inbetween tag attributes
	 * It does this by evaluating each character (and what preceeds and follows it), then copies 
	 * the character to a temp String, while also leaving out the extra spaces.
	 * @param s
	 * @return a condensed copy of S
	 */
	private static String fixSpaces(String s){
		//temp string for building a formatted s, 
		//the method will copy character by character from s to temp, leaving out extra spaces
		String temp = "";

		//booleans for keeping track of where you are in the string
		boolean insideTag = false;
		boolean insideAttribute = false;
		//boolean for keep track of if a space has already been copied, can ignore the spaces after it
		boolean oneSpaceAlreadyCopied = false;
		//boolean for a self closing tag 
		boolean selfClosingTag = false;
		
		int sCounter = 0;
		
		while(sCounter < s.length()){
			//using a charAt() with the index and adding an empty string to the end to make it of String type instead of char
			String c = s.charAt(sCounter) + "";
			
			//Check flags, setting booleans
			if(c.equals("<")){
				insideTag = true;
			}
			
			if(c.equals(">")){
				insideTag = false;
			}
			
			if(c.equals("\"")){
				//switch it
				insideAttribute = !insideAttribute; 
			}
			
			if(c.equals("/")){
				
				//possible self-closing tag
				if(s.charAt(sCounter+1)=='>'){
					//self-closing tag
					selfClosingTag = true;
					
				}else{
					selfClosingTag = false;
				}
			}else{
				
				selfClosingTag = false;
			}
			
			
			//Using the booleans to format the string; looking at the different conditions
			if(insideTag){
				//Space rules apply
				
				if(!insideAttribute){
					//inside the tag but not the attribute content
					
					if(c.equals(" ")){
						//Decide whether to copy it or if its extra space
						
						if(oneSpaceAlreadyCopied){
							; //skip this space, we don't need it
						} else{
							temp += c;
							//Set this to true so no other spaces are copied to temp
							oneSpaceAlreadyCopied = true;
						}
					} else{
						
						oneSpaceAlreadyCopied = false;
						
						//Copy non space character normally
						if(c.equals("=")){
							//setting this so true already because we don't want spaces between the attribute name and the equals sign
							oneSpaceAlreadyCopied = true;
							
							if(temp.charAt(temp.length()-1)==' '){
								//If the last character is a space remove it
								temp = temp.substring(0, temp.length()-1); 
							}
							
							//Get the attribute name directly before this = sign and change to lower case
							int idx = temp.lastIndexOf(' ');
							
							if(idx>-1){
								//Take everything befor attribute name up to and including the first space
								String beforeAttributeName = temp.substring(0, idx+1);
								//Get the attribute name and make it lower case
								String attrName = temp.substring(idx+1).toLowerCase();
								//Then reassemble the tag with everything before (including the space) + attribute name + the character =
								temp = beforeAttributeName + attrName + c;
								
							} else {
								//no space character so treat normally
								temp += c;
							}
							
						}else if(c.equals("/")){
							
							if(selfClosingTag){
								//Insert a space if there is not already one
								if(temp.charAt(temp.length()-1)!=' '){
									//Insert one
									temp += " " + c;
								} else {
									//Not needed
									temp += c;
								}
							}else{
								//Not a selfclosing tag, copy normally
								temp += c;
							}
							
						}else{
							//all other conditions (not / and not =), copy character normally to temp
							temp += c;
						}
						
					}
					
				}else{
					//this character is inside the attribute content, which does not need formatting
					//so copy to temp string unaltered
					temp += c;
				}
				
			} else{
				//Tag content, Condense to one space
				if(c.equals(" ")){
					//Decide whether to copy it or not
					
					if(oneSpaceAlreadyCopied){
						; //skip
					} else{
						temp += c;
						//Set to true so no mor spaces are copied to temp
						oneSpaceAlreadyCopied = true;
					}
					
				} else{
					//character is not a space
					oneSpaceAlreadyCopied = false;
					//copy normally
					temp +=c;
				}
				
			}
			//increment counter, wow this is a big loop ;)
			sCounter++;
		}
		
		//return formatted string, which I call a condensed copy of s
		return temp;
	}
	
	
	/**
	 * End of Tags '>'
	 * Removes the space surrounding the > sign of the end tag
	 * @param s
	 * @return
	 */
	private static String fixEndTags(String s){

		//beginning of close tag tags
		int index = s.indexOf(">");
		
		if(index>-1){
			//trim before, trim after the > sign
			//using the index of the >, this method trims the whitespace toward the right (or toward end) until it reaches a non-whitespace character
			String before = trimOnRightOnly(s.substring(0, index));
			
			//using the index of the > +1, this method trims the whitespace toward the left (or toward beginning) of the string until it reaches a non-whitespace character
			String after = trimOnLeftOnly(s.substring(index+1));
			
			//reassemble
			return before + ">" + after;
		}
		return s;
	}
	
	/**
	 * Beginning of tags: 
	 * 	1. First loop through all cases of the '<'
	 *  2. Skip any closing tags '</'
	 *  3. Trim tag of whitespace between the < and preceding character
	 *  4. Trim tag of whitespace between the < and succeeding character
	 * @param s
	 * @return
	 */
	private static String fixBeginTags(String s){
		
		//Loop through all cases of the <
		
		int index = s.indexOf("<");
		
		String temp = "";

		//if String s contains the '<'
		if(index>-1){

			boolean skip = false;
			
			//if not at the end
			if(index<=s.length()-2){ 
				//get next character
				char c = s.charAt(index+1);

				//if next character is a / then skip this one because it a closing tag
				if(c=='/')
					skip = true;
				else
					skip = false;
				
			}else{
				skip = false;	
			}
			
			if(index>-1 && !skip){
				//using the index of the <, this method trims the whitespace to the right until it reaches a non-whitespace character
				String before = trimOnRightOnly(s.substring(0, index));
				
				//using the index of the < +1, this method trims the whitespace to the left (or beginning) of the string until it reaches a non-whitespace character
				String after = trimOnLeftOnly(s.substring(index+1));
				
				//reassemble
				temp = (before + "<" + after);
				
			}
		}
		
		//if after the substringing the temp.length==0, return the string s
		if(temp.length()==0)
			return s;
		
		//return the formatted string temp
		return temp;
	}
	

	/**
	 * Closing Tags '</'
	 * Removes the space surrounding the '</'  of the closing tag
	 * @param s
	 * @return
	 */
	private static String fixClosingTags(String s){

		//beginning of close tag tags
		int index = s.indexOf("</");
		
		if(index>-1){
			//trim before, trim after
			String before = trimOnRightOnly(s.substring(0, index));
			
			String after = trimOnLeftOnly(s.substring(index+2));
			
			//reassemble
			return before + "</" + after;
		}
		
		return s;
	}
	
	/**
	 * Reusable util method
	 * Removes whitespace from the left or start of the given String
	 * Can't do just a .trim() on the String because it could remove the valid whitespace in the content of the tag
	 * @param string
	 * @return
	 */
	private static String trimOnLeftOnly(String string) {

		//check if any spaces at the beginning, if they aren't any then we have nothing to trim
		if(string.startsWith(" ")){
			
			//index for the non-whitespace character, starting at the beginning (or left side) of this string
			int index = countSpaceToNextChar(string, 0);
			
			//trim to that index by using the substring() with one argument 
			//the substring(int) method starts at the index and extends to the end of the string
			return string.substring(index);
		}
			
		return string;
	}

	/**
	 * Reusable util method
	 * Removes white space from the right side (or end) of the given String
	 * Can't do just a .trim() on the String because it could remove the valid whitespace in the content of the tag
	 * @param substring
	 * @return
	 */
	private static String trimOnRightOnly(String string) {

		//check if any spaces at the end, if they aren't any then we have nothing to trim
		if(string.endsWith(" ")){
			//find the index for the non-whitespace starting at the end of the string 
			//because we are trimming from the right, so we start at the end
			int index = countSpaceToPrevChar(string, string.length()-1);
			
			if(index==0){
				//none found
					return "";
			}
				
			//trim that many by substringing from the beginning to the return index+1
			return string.substring(0, index+1);
		}
		
		return string;
	}

	
	/**
	 * Reusable util method
	 * Returns the index of the next non-space character
	 * @param string
	 * @param start
	 * @return
	 */
	private static int countSpaceToNextChar(String string, int start){
		int count = 0;
		
		if(string!=null){
			
			int index = start;
			
			//loop till reach a non-space, incrementing i each time
			for(int i=index; i<string.length(); i++){
				char c = string.charAt(i);
				
				if(c!=' ')
					return i;
			}
		}
		return count;
	}
	
	/**
	 * Reusable util method
	 * Uses the charAt() with the given index and decrements till a non-whitespace character is found
	 * it returns that index int.
	 * Decrementing the index in this loop, looks at the character preceding the given index
	 * @param string
	 * @param index
	 * @return
	 */
	private static int countSpaceToPrevChar(String string, int index){
		int count = 0;
		
		if(string!=null && index>=0){
			
			//loop till reach a non-space
			for(int i=index; i>=0; i--){
				char c = string.charAt(i);
				if(c!=' ')
					return i;
			}
		}
		return count;
	}
	

	private static void printOutput(ArrayList<String> list){
		Iterator<String> itr = list.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
	}
}
