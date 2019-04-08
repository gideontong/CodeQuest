/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Prob07 {
    private static final String INPUT_FILE_NAME = "Prob07.in.txt";
    
    // points per problem
    static int pointsPerProblem = 0;
    
    // answer key is an ArrayList of Strings
    static ArrayList<String> keyAnswers = new ArrayList<String>();
    
    // student list is an ArrayList of Strings
    static ArrayList<String> students = new ArrayList<String>();
    
    // student answers is an ArrayList of ArrayLists of Strings
    static ArrayList<ArrayList<String>> studentAnswers = new ArrayList<ArrayList<String>>();
    
    public static void main(String[] args) {
        readInput();
        
        for (int i=0; i<students.size(); i++) {
            // get the current student
            String currentStudent = students.get(i);
            
            // get the ArrayList of this student's answers
            ArrayList<String> currentAnswerList = studentAnswers.get(i);
            
            // reset the grade to 0
            int grade = 0;
            
            // loop through the questions
            for (int j=0; j<keyAnswers.size(); j++) {
                // compare the key to the student answer
                if (keyAnswers.get(j).equals(currentAnswerList.get(j))) {
                    // correct answer gets points for this problem
                    grade += pointsPerProblem;
                }
            }
            
            // print the grade
            System.out.println(currentStudent + ": " + grade);
        }
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            // keep track of whether this is the first line, and whether or not the key has been read in
            boolean firstLine = true;
            boolean keyRead = false;
            
            // empty ArrayList to hold an answer set
            ArrayList<String> currentAnswerList = null;
            
            while ((inLine = br.readLine()) != null) {
                if (firstLine) {
                    // remember the points per problem
                    pointsPerProblem = new Integer(inLine).intValue();
                    
                    // un-set firstLine indicator
                    firstLine = false;
                } else if (!keyRead) {
                    // might not be done with the key yet
                    if (inLine.contains("STUDENT")) {
                        // start of a new student - the key is finished.  Record this student name
                        students.add(inLine);
                        
                        // create an empty ArrayList to hold their answers and add it to the list of answer sets
                        currentAnswerList = new ArrayList<String>();
                        studentAnswers.add(currentAnswerList);
                        
                        // set the keyRead indicator
                        keyRead = true;
                    } else {
                        // still reading the key - add this answer to it
                        keyAnswers.add(inLine);
                    }
                } else {
                    // this is the student section - check if this is the start of a new student, or just an answer line
                    if (inLine.contains("STUDENT")) {
                        // start of a new student - record this student name
                        students.add(inLine);
                        
                        // create an empty ArrayList to hold their answers and add it to the list of answer sets
                        currentAnswerList = new ArrayList<String>();
                        studentAnswers.add(currentAnswerList);
                    } else {
                        // still reading the student answers
                        currentAnswerList.add(inLine);
                    }
                }
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
