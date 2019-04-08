/**
 * CodeQuest 2014
 * Problem 10: All Aboard!
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob10 {
    private static final String INPUT_FILE_NAME = "Prob10.in.txt";
    private static final char PIPE = '|';
    
    private static ArrayList<String> lines = new ArrayList<String>();
    private static int longestTrackLength = 0;
    private static ArrayList<TrainTrack> tracks = new ArrayList<TrainTrack>();
    
    public static void main(String[] args) {
        // read the input file and process it
        readInput();
        processLines();
        
        // loop through starting positions
        for (int i=0; i<tracks.size(); i++) {
            StringBuffer buf = new StringBuffer();
            TrainTrack currentTrack = tracks.get(i);
            int timeIndex = 0;
            
            buf.append("Start: " + currentTrack.getStartPoint() + ", ");
//            System.out.println("Starting track " + currentTrack.getStartPoint());
            while (timeIndex < (currentTrack.getTrackLength())) {
                // there is more track
                boolean moved = false;
                
                // can we move up?
                while (currentTrack.canMoveUp(timeIndex)) {
                    currentTrack = currentTrack.moveUp(timeIndex);
                    moved = true;
//                    System.out.println("Moved up to track " + currentTrack.getStartPoint());
                }
                
                // move down if we didn't move up
                if (!moved) {
                    while (currentTrack.canMoveDown(timeIndex)) {
                        currentTrack = currentTrack.moveDown(timeIndex);
                        moved = true;
//                        System.out.println("Moved down to track " + currentTrack.getStartPoint());
                    }
                }
                
                // move the time
                timeIndex++;
            }
            buf.append("End: " + currentTrack.getEndPoint());
            System.out.println(buf.toString());
        }
    }
    
    private static void processLines() {
        // set up the first track
        TrainTrack previousTrack = null;
        TrainTrack nextTrack = new TrainTrack(lines.get(0));
        tracks.add(nextTrack);
        
        // loop through the switch lines
        for (int i=1; i<lines.size(); i+=2) {
            String currentLine = lines.get(i);
            currentLine = currentLine.trim();
            
            // switch the tracks
            previousTrack = nextTrack;
            nextTrack = new TrainTrack(lines.get(i+1));
            tracks.add(nextTrack);
            
            // build the switch arrays
            for (int position=0; position<currentLine.length(); position++) {
                if (currentLine.charAt(position) == PIPE) {
                    // only create a bridge if both tracks are long enough
                    if ((position < previousTrack.getTrackLength()) && (position < nextTrack.getTrackLength())) {
                        previousTrack.setDown(position, nextTrack);
                        nextTrack.setUp(position, previousTrack);
                    }
                }
            }
        }
    }

    private static void readInput() {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                lines.add(inLine);
                if (inLine.length() > longestTrackLength) {
                    longestTrackLength = inLine.length();
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class TrainTrack {
        private boolean[] hasUp = null;
        private boolean[] hasDown = null;
        private TrainTrack[] upTrack = null;
        private TrainTrack[] downTrack = null;
        private String startPoint = null;
        private String endPoint = null;
        private int trackLength = 0;
        
        public TrainTrack(String trackLine) {
            hasUp = new boolean[longestTrackLength];
            hasDown = new boolean[longestTrackLength];
            upTrack = new TrainTrack[longestTrackLength];
            downTrack = new TrainTrack[longestTrackLength];
            
            for (int i=0; i<longestTrackLength; i++) {
                hasUp[i] = false;
                hasDown[i] = false;
                upTrack[i] = null;
                downTrack[i] = null;
            }
            
            // set start and end points
            startPoint = trackLine.substring(0,  3);
            endPoint = trackLine.substring(trackLine.length()-3);
            trackLength = trackLine.length() - 6;
        }
        
        public void setDown(int position, TrainTrack nextTrack) {
            hasDown[position] = true;
            downTrack[position] = nextTrack;
        }
        
        public void setUp(int position, TrainTrack previousTrack) {
            hasUp[position] = true;
            upTrack[position] = previousTrack;
        }
        
        public String getStartPoint() {
            return startPoint;
        }
        
        public String getEndPoint() {
            return endPoint;
        }
        
        public int getTrackLength() {
            return trackLength;
        }
        
        public boolean canMoveUp(int index) {
            return hasUp[index];
        }
        
        public boolean canMoveDown(int index) {
            return hasDown[index];
        }
        
        public TrainTrack moveUp(int index) {
            return upTrack[index];
        }
        
        public TrainTrack moveDown(int index) {
            return downTrack[index];
        }
    }
}
