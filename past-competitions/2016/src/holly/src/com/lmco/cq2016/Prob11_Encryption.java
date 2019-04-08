package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 1:14 --1:40 = 26 minutes
 * @author nortoha
 *
 */
public class Prob11_Encryption {
    private static final String INPUT_FILE_NAME = "Prob11.in.txt";
    
    static String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    static String[] key = new String[26];
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob11_Encryption.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                
                // read the line that indicated that we are encrypting or decrypting
                String operation = br.readLine();
                
                // read the line with the cipher key
                String cipherKey = br.readLine();
                
                //TODO: will the cipher key always be lowercase??
                
                // set cipher key into my array
                for(int i=0; i<cipherKey.length(); i++){
                    key[i] = String.valueOf(cipherKey.charAt(i));
                }
                
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                // loop through the lines
                for (int i=0; i<N; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    if(operation.equals("ENCRYPT")){
                        System.out.println(encrypt(inLine));
                    } else {
                        System.out.println(decrypt(inLine));
                    }
                }
                
                
                // TODO: this is the only write up so far that you separate output with a blank line (not consistent).
                System.out.println();
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String decrypt(String encryptedString) {

        String retVal = "";
        
        // for each character in the encrypted string, find its index in the cipher key
        for(int i=0; i<encryptedString.length(); i++){
            
            String letter = String.valueOf(encryptedString.charAt(i));
            
            // check if character is a space
            if(letter.equals(" ")){
                // we need to skip over it
                retVal += " "; 
            } else {
                // encrypt this letter
                
                int index = findKeyIndex(letter);
                
                // get the letter at the same index in the cipher key array
                String decryptedLetter = alphabet[index];
                
                // check the case
                if(letter.toUpperCase().equals(letter)){
                    
                    // performing a toUpperCase results in the original value means it was uppercase to begin with.
                    retVal += decryptedLetter.toUpperCase();
                } else {
                    retVal += decryptedLetter.toLowerCase();
                }
            }
        }
        
        return retVal;
    }

    private static String encrypt(String s) {

        String retVal = "";
        
        // for each character in the given string, find its index in the base alphabet
        for(int i=0; i<s.length(); i++){
            
            String letter = String.valueOf(s.charAt(i));
            
            // check if character is a space
            if(letter.equals(" ")){
                // we need to skip over it
                retVal += " "; 
            } else {
                // encrypt this letter
                
                int index = findAlphabetIndex(letter);
                
                // get the letter at the same index in the cipher key array
                String encryptedLetter = key[index];
                
                // check the case
                if(letter.toUpperCase().equals(letter)){
                    // performing a toUpperCase results in the original value means it was uppercase to begin with.
                    retVal += encryptedLetter.toUpperCase();
                } else {
                    retVal += encryptedLetter.toLowerCase();
                }
            }
        }
        
        return retVal;
    }
    
    /** 
     * helper method
     * @param s
     * @return
     */
    private static int findAlphabetIndex(String s){
        for(int i=0; i<alphabet.length; i++){
            if(alphabet[i].equalsIgnoreCase(s)){
                // found it, return this index
                return i;
            }
        }
        return -1;
    }
    
    /**
     * helper method
     * @param s
     * @return
     */
    private static int findKeyIndex(String s){
        for(int i=0; i<key.length; i++){
            if(key[i].equalsIgnoreCase(s)){
                // found it, return this index
                return i;
            }
        }
        return -1;
    }
}
