package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Completion time = 12m
 * Super easy if you think to write your own Comparator!
 * 
 * @author nortoha
 *
 */
public class Prob08_MuddledMusicSongSort {
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
    
    public static void main(String[] args) {
        
        try {
            // prepare to read the file
            InputStream in = Prob08_MuddledMusicSongSort.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                ArrayList<String> songList = new ArrayList<String>();
                
                // loop through the lines
                for (int i=0; i<N; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    songList.add(inLine);
                }
                
                SongComparator songComparator = new SongComparator();
                
                Collections.sort(songList, songComparator);
                
                printSongList(songList);
                
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private static class SongComparator implements Comparator<String>{

        @Override
        public int compare(String s1, String s2) {

            //rearrange the given strings and remove the "The"
            
            String[] s1Array = s1.split(" \\- ");
            String[] s2Array = s2.split(" \\- ");
            
            String s1Artist = s1Array[1].trim();
            if(s1Artist.startsWith("The ")){
                s1Artist = s1Artist.replace("The ", "");
            }
            
            
            String s2Artist = s2Array[1].trim();
            if(s2Artist.startsWith("The ")){
                s2Artist = s2Artist.replace("The ", "");
            }
            
            
            String s1Title = s1Array[0].trim();
            String s2Title = s2Array[0].trim();
            
            int c = s1Artist.toLowerCase().compareTo(s2Artist.toLowerCase());
            
            //if the c value is 0 then these two are equal and we need to secondary sort
            if(c == 0){
                //secondary sort by Title
                return s1Title.toLowerCase().compareTo(s2Title.toLowerCase());
            }
            
            return c;
        }
        
    }
    
    private static void printSongList(ArrayList<String> songList){
        for(String s : songList){
            System.out.println(s);
        }
    }
    
}
