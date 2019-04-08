/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Prob13 {
    private static final String INPUT_FILE_NAME = "Prob13.in.txt";
    
    // problem number per key item
    static ArrayList<String> keyProblemNumbersList = new ArrayList<String>();
    
    // points per key item
    static ArrayList<Integer> keyPointsList = new ArrayList<Integer>();
    
    // condition list per key item
    static ArrayList<String> keyConditionsList = new ArrayList<String>();
    
    // ArrayList of Strings of conditions per key item
    static ArrayList<ArrayList<String>> keyAnswersList = new ArrayList<ArrayList<String>>();
    
    public static void main(String[] args) {
        readInput();
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            boolean keyFinished = false;
            int studentTotal = 0;
            String currentStudent = null;
            
            while ((inLine = br.readLine()) != null) {
                // first, split on the spaces
                String[] tokens = inLine.split(" ");
                
                if (!keyFinished) {
                    // still reading key answers
                    if (tokens[0].equals("999")) {
                        // the key is done
                        keyFinished = true;
                    } else {
                        // new key item - save its data
                        String problemNumber = tokens[0];
                        Integer points = new Integer(tokens[1]);
                        String condition = tokens[2];
                        
                        keyProblemNumbersList.add(problemNumber);
                        keyPointsList.add(points);
                        keyConditionsList.add(condition);
                        
                        // make a new ArrayList to hold the list of conditions
                        ArrayList<String> keyAnswers = new ArrayList<String>();
                        keyAnswersList.add(keyAnswers);
                        
                        // split the line again using double quotes - start with token 1
                        tokens = inLine.split("\"");
                        for (int i=1; i<tokens.length; i++) {
                            if (!tokens[i].equals(",")) {
                                // found a new answer for this question - add to the list of conditions
                                keyAnswers.add(tokens[i]);
                            }
                        }
                    }
                } else {
                    // reading student responses
                    String studentNumber = tokens[0];
                    String problemNumber = tokens[1];
                    
                    // split using double quotes to get the student's answer
                    tokens = inLine.split("\"");
                    String answer = tokens[1];
                    
                    if (!studentNumber.equals(currentStudent)) {
                        // start of a new student
                        if (currentStudent != null) {
                            // print out the previous student's total if this isn't the first student
                            System.out.println("STUDENT " + currentStudent + " TOTAL " + studentTotal);
                        }
                        // remember student number and reset total
                        currentStudent = studentNumber;
                        studentTotal = 0;
                    }
                    
                    // check each answer and print the points received
                    int pointsReceived = checkAnswer(problemNumber, answer);
                    System.out.println("STUDENT " + studentNumber + " " + problemNumber + " " + pointsReceived);
                    
                    // add points received to the total
                    studentTotal += pointsReceived;
                }
            }
            // print the total of the final student
            System.out.println("STUDENT " + currentStudent + " TOTAL " + studentTotal);
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int checkAnswer(String problemNumber, String studentAnswer) {
        // start with 0 points
        int retVal = 0;
        
        // loop through all key items
        for (int i=0; i<keyProblemNumbersList.size(); i++) {
            String keyProblemNumber = keyProblemNumbersList.get(i);
            
            // check to see if the key item is for the answer number we're scoring
            if (keyProblemNumber.equals(problemNumber)) {
                // problem numbers match - get key item data
                Integer keyPoints = keyPointsList.get(i);
                String keyCondition = keyConditionsList.get(i);
                ArrayList<String> keyAnswers = keyAnswersList.get(i);
                
                // assume we don't award points
                boolean awardPoints = false;
                
                if (keyCondition.equals("EQ")) {
                    // student answer must match one key answer exactly
                    for (int j=0; j<keyAnswers.size(); j++) {
                        String keyAnswer = keyAnswers.get(j);
                        if (studentAnswer.equals(keyAnswer)) {
                            // found a match - stop looking and award points
                            awardPoints = true;
                            j=keyAnswers.size();
                        }
                    }
                } else if (keyCondition.equals("IN")) {
                    // student answer must contain one key answer as a substring
                    for (int j=0; j<keyAnswers.size(); j++) {
                        String keyAnswer = keyAnswers.get(j);
                        if (studentAnswer.indexOf(keyAnswer) >= 0) {
                            // found a substring - stop looking and award points
                            awardPoints = true;
                            j=keyAnswers.size();
                        }
                    }
                } else if (keyCondition.equals("EX")) {
                    // student answer cannot have any of the answers as a substring - assume they get the points for this one
                    awardPoints = true;
                    for (int j=0; j<keyAnswers.size(); j++) {
                        String keyAnswer = keyAnswers.get(j);
                        if (studentAnswer.indexOf(keyAnswer) >= 0) {
                            // found a substring - stop looking and do not award points
                            awardPoints = false;
                            j=keyAnswers.size();
                        }
                    }
                }
                
                if (awardPoints) {
                    // add this key item's points to the total
                    retVal += keyPoints.intValue();
                }
            }
        }
        if (retVal < 0) {
            retVal = 0;
        }
        return retVal;
    }
}
