/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Hashtable;


public class AuthenticateLogins {
    private static final String INPUT_FILE_NAME = "AuthenticateLogins.in.txt";
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            int numAccounts = -1;
            int accountsRead = 0;
            boolean blankLineSkipped = false;
            
            MessageDigest md = MessageDigest.getInstance("MD5");
            Hashtable<String, String> salts = new Hashtable<String, String>();
            Hashtable<String, String> digests = new Hashtable<String, String>();
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                if (numAccounts == -1) {
                    // the first line contains the number of accounts to read in
                    numAccounts = Integer.valueOf(inLine);
                } else {
                    // done reading the number of accounts
                    if (accountsRead < numAccounts) {
                        // we're reading accounts - split on the comma and space
                        String[] tokens = inLine.split(", ");
                        String username = tokens[0];
                        String theString = tokens[1];
                        
                        // now split on the colon
                        tokens = theString.split(":");
                        String salt = tokens[0];
                        String digest = tokens[1];
                        
                        salts.put(username, salt);
                        digests.put(username, digest);
                        accountsRead++;
                    } else {
                        // done reading the accounts
                        if (blankLineSkipped) {
                            // authenticating users
                            String[] tokens = inLine.split(", ");
                            String username = tokens[0];
                            String password = tokens[1];
                            String result = "";
                            
                            if (salts.containsKey(username)) {
                                result = username + " Authorized";
                                
                                md.reset();
                                String hashThis = salts.get(username);
                                hashThis += password;
                                byte[] byteArray = hashThis.getBytes();
                                md.update(byteArray);
                                byte[] hashedBytes = md.digest();
                                
                                StringBuffer buf = new StringBuffer();
                                for (int i=0; i<hashedBytes.length; i++) {
                                    buf.append(Integer.toHexString((hashedBytes[i] >>> 4) & 0x0F));
                                    buf.append(Integer.toHexString(0x0F & hashedBytes[i]));
                                }
                                String hashedString = buf.toString();
                                if (!hashedString.equals(digests.get(username))) {
                                    result += " Denied";
                                }
                            } else {
                                result = username + " Denied";
                            }
                            
                            System.out.println(result);
                        } else {
                            // skip the blank line
                            blankLineSkipped = true;
                        }
                    }
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
