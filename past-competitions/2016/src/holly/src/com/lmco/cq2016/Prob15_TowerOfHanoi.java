package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * 9:24 --> 9:57
 * @author nortoha
 *
 */
public class Prob15_TowerOfHanoi {
    private static final String INPUT_FILE_NAME = "Prob15.in.txt";
    
    static TowerStack pegA;
    static TowerStack pegB;
    static TowerStack pegC;
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob15_TowerOfHanoi.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                
                // get the number of lines in each test case
                int numDisks = Integer.parseInt(br.readLine());
                
                System.out.println(numDisks);
                
                // initialize pegs
                pegA = new TowerStack("A");
                pegB = new TowerStack("B");
                pegC = new TowerStack("C");
                
                // put disks on A peg
                for(int i=1; i<= numDisks; i++){
                    pegA.add(i);
                }
//                printPegs();
                
                solveTower(numDisks, pegA, pegB, pegC);
                
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void solveTower(int numDisks, TowerStack fromPeg, TowerStack middlePeg, TowerStack toPeg) {

        if(numDisks >= 1){
            // always move from the fromPeg
            solveTower(numDisks-1, fromPeg, toPeg, middlePeg);
            
            // always move the top disk
            toPeg.push(fromPeg.pop());
            
            // print FromPeg->ToPeg
            System.out.println(fromPeg.label + "->" + toPeg.label);
            
            solveTower(numDisks-1, middlePeg, fromPeg, toPeg);
            
        }
    }

    /**
     * debugging method
     */
    private static void printPegs(){
        
        System.out.println("A contains:");
        
        for(Integer i : pegA ){
            System.out.println(i + " ");
        }
        System.out.println();
    }
    
    private static class TowerStack extends Stack<Integer>{

        // used for the printing of the peg name
        String label;
        
        public TowerStack(String s){
            super();
            this.label = s;
        }
    }
}
