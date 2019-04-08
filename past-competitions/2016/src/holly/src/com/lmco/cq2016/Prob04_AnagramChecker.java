package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Completion time = 12m
 * @author nortoha
 *
 */
public class Prob04_AnagramChecker {
    private static final String INPUT_FILE_NAME = "Prob04.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob00.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                
                // read the line of text
                inLine = br.readLine();
                
                if(isAnagram(inLine)){
                    System.out.println(inLine + " = ANAGRAM");
                } else {
                    System.out.println(inLine + " = NOT AN ANAGRAM");
                }
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isAnagram(String inLine) {

        if(inLine!=null){
            
            //split on pipe
            StringTokenizer strTok = new StringTokenizer(inLine, "|");

            //split into tokens
            String firstWord = strTok.nextToken();
            String secondWord = strTok.nextToken();
            
            if(firstWord.equals(secondWord))
                return false;
            
            //turn into char arrays 
            char[] firstWordArray = firstWord.toCharArray();
            char[] secondWordArray = secondWord.toCharArray();
            
            //loop through each char of first word
            for(int i=0; i<firstWordArray.length; i++){
                
                //for each character in the first word
                char c = firstWordArray[i];
                
                //check its existence in the second word
                int index = getIndex(secondWordArray, c);
                
                if(index == -1){
                    //character not found in second string.  Cannot be an anagram
                    return false;
                } else {
                    //character found in second string. convert it and keep searching through firstWord.
                    secondWordArray[index] = '|';                  
                }
            }
            
            //check if all letters where found in second word with none left over
            for(char c : secondWordArray){
                //| indicates letter is in both words, if there is any other character then not anagram.
                if(c != '|'){
                    return false;
                }
            }
            return true;
            
        }
        
        return false;
    }

    private static int getIndex(char[] secondWordArray, char c) {
        //find index of this char in this array
        for(int i=0; i<secondWordArray.length; i++){
            
            char s = secondWordArray[i];
            
            if(c == s){
                return i;
            }
        }
        
        //return not found
        return -1;
    }

}

