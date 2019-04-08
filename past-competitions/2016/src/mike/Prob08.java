

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Prob08 {
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                // create an array to hold the songs
                Song[] songs = new Song[N];
                
                // loop through the lines
                for (int i=0; i<N; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // create a new song object
                    songs[i] = new Song(inLine);
                }
                
                // sort the lines
                Arrays.sort(songs);
                
                // print them out
                for (int i=0; i<N; i++) {
                    System.out.println(songs[i]);
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class Song implements Comparable<Song> {
        private String original;
        private String name;
        private String sortName;
        private String artist;
        private String sortArtist;
        
        public Song(String inLine) {
            original = inLine;
            String[] tokens = inLine.split(" - ");
            name = tokens[0];
            artist = tokens[1];
            sortArtist = artist.toLowerCase().startsWith("the ") ? sortArtist = artist.substring(4) : artist;
            sortArtist = sortArtist.toLowerCase();
            sortName = name.toLowerCase();
        }
        
        @Override
        public int compareTo(Song other) {
            // use artist first
            if (sortArtist.compareTo(other.getSortArtist()) != 0) {
                // artists are different
                return sortArtist.compareTo(other.getSortArtist());
            } else {
                // artists are the same, use the song title
                return sortName.compareTo(other.getSortName());
            }
        }
        
        @Override
        public String toString() {
            return original;
        }
        
        public String getName() {
            return name;
        }
        
        public String getSortArtist() {
            return sortArtist;
        }
        
        public String getSortName() {
            return sortName;
        }
    }
}
