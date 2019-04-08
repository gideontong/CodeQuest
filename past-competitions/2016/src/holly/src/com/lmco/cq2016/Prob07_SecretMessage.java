package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Completion time = 17m
 * @author nortoha
 *
 */
public class Prob07_SecretMessage {

    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
    private static final char HOLE = 'O';
    
    public static void main(String[] args) {
        
        int row = 0;
        int col = 0;
        
        try {
            // prepare to read the file
            InputStream in = Prob00.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;           
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // get the number of lines in the message
                int N = Integer.parseInt(br.readLine());
                
                String[] msgArray = new String[N];
                
                // loop through the lines
                for (int i=0; i<N; i++) {
                    // read the line of message
                    inLine = br.readLine();
                    
                    //keeping track of original message as an array to determine starting position.
                    msgArray[i] = inLine;
                }
                
                // get the start coordinate
                String coord = br.readLine();
                String[] coordArray = coord.split(",");
                row = Integer.parseInt(coordArray[0]);
                col = Integer.parseInt(coordArray[1]);
                
                
                N = Integer.parseInt(br.readLine());
                
                ArrayList<String> coverMsg = new ArrayList<String>();
                
                // loop through the lines
                for (int i=0; i<N; i++) {
                    // read the line of message
                    inLine = br.readLine();

                    coverMsg.add(inLine);
                }
                
                String hiddenMsg = "";
                
                //search through the cover msg to find the holes
                for(int i=0; i<coverMsg.size(); i++){    
                    
                    String coverLine = coverMsg.get(i);
                    
                    if(coverLine != null){
                        
                        for(int j=0; j<coverLine.length(); j++){
    
                            char c = coverLine.charAt(j);
                            
                            if(c == HOLE){
                                //get char at same index in original msg from starting coord
                                String msgLine = msgArray[row];
                                
                                hiddenMsg += msgLine.charAt(col);
                            }
                            //else skip over
                            
                            //increment indexes in the messageArray
                            col++;
                        }
                    
                    }
                    //increment row
                    row++;
                    //reset col
                    col = Integer.parseInt(coordArray[1]);
                }
                
                System.out.println(hiddenMsg);
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}