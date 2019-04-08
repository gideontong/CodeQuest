

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob06 {
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
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
                // school name
                inLine = br.readLine();
                System.out.print(inLine + " = ");
                
                // get the number of students
                int N = Integer.parseInt(br.readLine());
                
                // for storing the winner
                String valedictorian = null;
                double bestGPA = 0.0;
                int numCredits = 0;
                
                // loop through the students
                for (int i=0; i<N; i++) {
                    // read the student record
                    inLine = br.readLine();
                    
                    // break it up
                    String[] tokens = inLine.split(":");
                    
                    // get the name
                    String currentName = tokens[0];
                    
                    // get the classes
                    inLine = tokens[1];
                    
                    // break up the classes
                    tokens = inLine.split(",");
                    
                    // total hours and points
                    int totalHours = 0;
                    int totalPoints = 0;
                    
                    // loop through classes
                    for (int j=0; j<tokens.length; j++) {
                        String currentClass = tokens[j];
                        
                        // get the hours
                        int hours = Integer.parseInt(""+currentClass.charAt(1));
                        totalHours += hours;
                        
                        // get the grade
                        int grade = 0;
                        switch (currentClass.charAt(0)) {
                            case 'A':
                                grade = 4;
                                break;
                            case 'B':
                                grade = 3;
                                break;
                            case 'C':
                                grade = 2;
                                break;
                            case 'D':
                                grade = 1;
                                break;
                        }
                        
                        // add to total points
                        totalPoints += (grade * hours);
                    }
                    
                    // calculate GPA
                    double gpa = ((double)totalPoints + 0.0) / ((double)totalHours + 0.0);
                    
                    // compare
                    if (gpa > bestGPA) {
                        // new winner
                        valedictorian = currentName;
                        bestGPA = gpa;
                        numCredits = totalHours;
                    } else if (gpa == bestGPA) {
                        // tie
                        if (totalHours > numCredits) {
                            // new winner
                            valedictorian = currentName;
                            bestGPA = gpa;
                            numCredits = totalHours;
                        } else if (totalHours == numCredits) {
                            System.out.println("ERROR!");
                        }
                    }
                }
                
                // print the winner
                System.out.println(valedictorian);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
