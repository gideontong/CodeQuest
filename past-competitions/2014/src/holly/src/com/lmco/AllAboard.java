package com.lmco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * CodeQuest 2014
 * Problem 10: All Aboard
 *  
 * Author: Holly Norton
 * (holly.norton@lmco.com)
 *
 */
public class AllAboard {
	
	public static final String FILENAME = "Prob10.in.txt";
	
	public ArrayList<String> input;
	//used to track starting station to ending station at any given point in time
	//as the end track changes due to a switch the value mapped to the start station(key)
	//will be changed
	public HashMap<String, String> stationMapping;
	public TrainTrack[] trainTrack;
	
	public AllAboard(){
		input = new ArrayList<String>();
		
		stationMapping = new HashMap<String, String>();
	}
	
	public void addInput(String s){
		this.input.add(s);
	}
	public void setTracks(TrainTrack[] tt){
		this.trainTrack=tt;
	}
	
	public void computeEndingTracks(){
		
		if(this.trainTrack!=null){
			
			//for each start station
			for(int i=0; i<trainTrack.length; i++){
				
				//leave original object alone and manipulate the copy
				TrainTrack tt = trainTrack[i].copyTrack(); 
				
				//holds the index into the trainTrack array for this track number.  all moves are relative of the index
				//as the train moved from one track to the other, this variable holds the index for that track in the trackTrack[]
				int trackIndex=i;  
				
				//position of the train on the track
				int trackPosition=3; //skipping first 3 characters in the track string bc it is the label

				while(tt.canMove(trackPosition)){
					int pos = tt.moveOnePosition(trackPosition);
					if(pos==0){
						//tt stays on current track
						;
					} else{
						if(pos==-1){
							//move up one
							TrainTrack newTrack = trainTrack[trackIndex-1];
							trackIndex=trackIndex-1;							
							tt.setTrack(newTrack);
							//if moved Up need to check if the train can go up more.
							//this check has to occur in the if condition otherwise the train will go back and forth across the same switch endlessly
							while(tt.canSwitchTracks(trackPosition, true)){
								int moveAgain = tt.moveOnePositionUp(trackPosition); 
								//move up one
								TrainTrack newTrackAgain = trainTrack[trackIndex-1];
								trackIndex=trackIndex-1;							
								tt.setTrack(newTrackAgain);
							} //end inner while 
						} else {
							//move down one
							TrainTrack newTrack = trainTrack[trackIndex+1];
							trackIndex=trackIndex+1;
							tt.setTrack(newTrack);
							//if moved Down need to check if the train can go Down more.
							while(tt.canSwitchTracks(trackPosition, false)){
								int moveAgain = tt.moveOnePositionDown(trackPosition);
								//move up one
								TrainTrack newTrackAgain = trainTrack[trackIndex+1];
								trackIndex=trackIndex+1;							
								tt.setTrack(newTrackAgain);
							} //end inner while 
						}
						
						//on a new track now...check for another switch at this same position
						//before incrementing trackPosition see if the train can move more down or up
						//need to know which direction the train just moved because you can't move back across that
						//same switch
						//if you don't use the direction the train just moved (above), then it will get in an endless loop
						//can't go back to the same track again
						//REMOVED THIS AND MOVED IT UP INTO THE IF/ELSE FOR POS
//						while(tt.canSwitchTracks(trackPosition, movedUp)){
//							int moveAgain = tt.moveOnePosition(trackPosition);
//							if(moveAgain==0){
//								//tt stays on current track
//								break;
//							} else{
//								if(moveAgain==-1){
//									//move up one
//									TrainTrack newTrack = trainTrack[trackIndex-1];
//									trackIndex=trackIndex-1;							
//									tt.setTrack(newTrack);
//								} else {
//									//move down one
//									TrainTrack newTrack = trainTrack[trackIndex+1];
//									trackIndex=trackIndex+1;
//									tt.setTrack(newTrack);
//								}
//							}
//						} 
					} // end else for switching track
					
					//done with this track position, so increment
					trackPosition++;
				}
				
				System.out.println(tt.toString());
				//done computing this track
				//put the barriers back to original position
				tt.resetBarriers();
			}
			
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			
			AllAboard a = new AllAboard();
			
			//read file
			InputStream in = AllAboard.class.getResourceAsStream(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			int trackCount = 0;
			//loop through lines of input file
			String s;
			while((s = br.readLine()) != null) { 
				a.addInput(s); 
				
				if(s.contains("=")){
					//this is a track not a barrier
					trackCount++;
				}
				
				if(!a.stationMapping.containsKey(s)){
					//add it to map with null value indicating no end station is reached yet
					a.stationMapping.put(s, null);
				}
			}
			
			
			//initialize train tracks and the adjacent barriers
			TrainTrack[] allTracks = new TrainTrack[trackCount];
			int idx = 0;
			for(int i=0; i<a.input.size(); i++){
				
				String line = a.input.get(i);
				
				//check for tracks above
				String barrierAbove = null;
				if(i>0)
					barrierAbove = a.input.get(i-1);  
				
				//check for tracks below
				String barrierBelow = null;
				if(i<a.input.size()-1)
					barrierBelow = a.input.get(i+1);
				
				//checking that this is a track not a barrier, skip barriers
				if(line.contains("=")){  
					//new track
					TrainTrack tt = a.new TrainTrack(line, barrierAbove, barrierBelow);
					allTracks[idx] = tt;
					idx++;
				}
			}
			a.setTracks(allTracks);
			
//			System.out.println(allTracks.length);
			a.computeEndingTracks();
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	private class TrainTrack{
		//variables that never change
		private String startStation;
		private int trackIndexFromFile;
		//used in resetBarrier()...
		private String origBarrierAbove = null; //need to keep the original from the input file for processing other tracks
		private String origBarrierBelow = null; //need to keep the original from the input file for processing other tracks
		
		//variables that change as the track changes
		String track;
		String barrierAbove = null; //this changes as the train changes tracks, orig value is stored above
		String barrierBelow = null; //this changes as the train changes tracks
		
		int position;
		int maxPosition;
		
		public TrainTrack(String s, String above, String below){
			
			this.maxPosition = s.length();
			this.track=s;
			this.barrierAbove=above;
			this.barrierBelow=below;
			
			this.startStation=s.substring(0,3);
			this.origBarrierAbove=above;
			this.origBarrierBelow=below;
		}
		
		public boolean canMove(int i){
			//check if we are not at the end of the track by checking position and if there is still track left (=)
			if(i<maxPosition && track.charAt(i)=='=')
				return true;
			return false;
		}
		public boolean canSwitchTracks(int i, boolean upDirection){
			//check if the barriers have a | at this position
			//not actually moving the train though
			
			boolean retVal = false; //assume stay on same track
			try{
				//check for switches to see if it stays on this track
				if(upDirection){
					//i is the track position to move to
					if(barrierAbove!=null && barrierAbove.charAt(i)=='|'){ //above is always the first choice if there are 2 switches
						//found a switch
						return true; 
					} 
				} else{
					if(barrierBelow!=null && barrierBelow.length()>0 && barrierBelow.charAt(i)=='|'){
						//found a switch
						return true; 
					}
				}
			}catch(Exception e){
				System.out.println("pos="+i);
				e.printStackTrace();
				
			}
			return retVal;
		}
		public void setPosition(int i){
			this.position = i;
		}
		public int moveOnePosition(int i){
			
			int retVal = 0; //assume stay on same track
			try{
				//check for switches to see if it stays on this track
				//i is the track position to move to
				if(barrierAbove!=null && barrierAbove.charAt(i)=='|'){ //above is always the first choice if there are 2 switches
					//found a switch
					setPosition(i);
					return -1; //move up one track
				} 
				if(barrierBelow!=null && barrierBelow.length()>0 && barrierBelow.charAt(i)=='|'){
					//found a switch
					setPosition(i);
					return 1; //move down one track
				}
				
				//else stay one track
				setPosition(i);
			}catch(Exception e){
				System.out.println("pos="+i);
				e.printStackTrace();
				
			}
			return retVal;
			
		}
		public int moveOnePositionUp(int i){
			
			int retVal = 0; //assume stay on same track
			try{
				//check for switches to see if it stays on this track
				//i is the track position to move to
				if(barrierAbove!=null && barrierAbove.charAt(i)=='|'){ //above is always the first choice if there are 2 switches
					//found a switch
					setPosition(i);
					return -1; //move up one track
				} 
			
				//else stay one track
				setPosition(i);
			}catch(Exception e){
				System.out.println("pos="+i);
				e.printStackTrace();
				
			}
			return retVal;
			
		}
		public int moveOnePositionDown(int i){
			
			int retVal = 0; //assume stay on same track
			try{
				//check for switches to see if it stays on this track
				//i is the track position to move to 
				if(barrierBelow!=null && barrierBelow.length()>0 && barrierBelow.charAt(i)=='|'){
					//found a switch
					setPosition(i);
					return 1; //move down one track
				}
				
				//else stay one track
				setPosition(i);
			}catch(Exception e){
				System.out.println("pos="+i);
				e.printStackTrace();
				
			}
			return retVal;
			
		}
		public void setTrack(TrainTrack tt){
			this.track = tt.track;
			this.maxPosition = tt.track.length();
			this.barrierAbove=tt.barrierAbove;
			this.barrierBelow=tt.barrierBelow;
		}

		public TrainTrack copyTrack(){
			TrainTrack temp = new TrainTrack(this.track, this.barrierAbove, this.barrierBelow);
			return temp;
		}
		/*
		 * when I didnt have a reset method, i ran into a problem where after train from station 1 moved all the way down the
		 * track, the barriers were left at their last positions.  But for the next train from station 2 the barriers on
		 * each track needed to be back in their original positions
		 */
		public void resetBarriers(){
			this.barrierAbove = this.origBarrierAbove;
			this.barrierBelow = this.origBarrierBelow;
		}
		public String toString(){
			return ("Start: " + this.startStation + ", End: " + this.track.substring(track.length()-3));
		}
	}
}
