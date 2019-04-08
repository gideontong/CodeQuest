package com.lmco.cq2016;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 15m
 * @author nortoha
 *
 */
public class Prob06_Valedictorian {
    private static final String INPUT_FILE_NAME = "Prob06.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            InputStream in = Prob06_Valedictorian.class.getResourceAsStream(INPUT_FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            while (T-- > 0) {
                
                // variable to hold the highest GPA
                Student highestStudent = null;
                
                // get the name of the school
                String school = br.readLine();
                
                // get the number of students
                int N = Integer.parseInt(br.readLine());
                
                // loop through the students replacing the highest when a higher GPA is found
                for (int i=0; i<N; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // parse line into student info
                    //TODO:  will student names be guaranteed to be unique?
                    String[] inArray = inLine.split("\\:");
                    String name = inArray[0];
                    String grades = inArray[1];
                    
                    // construct Student
                    Student student = new Student(name, grades);
                    
                    // if the first then they are the highest so far
                    if(highestStudent == null) {
                        highestStudent = student;
                    } else {
                    
                        // check if this student is the highest for this school
                        if(student.getGPA() == highestStudent.getGPA()){
                            // another student has this same GPA
                            // look at total credit hours
                            
                            if(student.getTotalCreditHours() > highestStudent.getTotalCreditHours()){
                                // new highest student
                                highestStudent = student;
                            }
                        } else {
                            if(student.getGPA() > highestStudent.getGPA()){
                                // new highest student
                                highestStudent = student;
                            }
                        }
                    }
                } // end for loop
                
                // print output
                System.out.println(school + " = " + highestStudent.getName());
            }
            
            // clean up
            br.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class Student{
        
        String name;
        ArrayList<String> gradeList = new ArrayList<String>();
        int totalHours = 0;
        
        public Student(String n, String grades){
            name = n;
            
            // parse grades
            String[] gArray = grades.split("\\,");
            for(String g : gArray){
                //add to list
                gradeList.add(g);
                
                //add to total hours
                totalHours += Integer.parseInt(String.valueOf(g.charAt(1)));
            }
        }
        
        public double getGPA(){
            // parse grades and loop through the determine GPA
            
            // get total grade points
            double gradePoints = 0;
            
            for(String grade : gradeList){
                // determine grade points
                
                char g = grade.charAt(0);
                double gradeValue = 0;
                
                if(g == 'A'){
                    gradeValue = 4;
                }
                if(g == 'B'){
                    gradeValue = 3;
                }
                if(g == 'C'){
                    gradeValue = 2;
                }
                if(g == 'D'){
                    gradeValue = 1;
                }
                
                gradePoints += gradeValue * Integer.parseInt(String.valueOf(grade.charAt(1)));
            }
            
            // divide grade points by total hours
            return (gradePoints / totalHours);
            
        }
        
        public int getTotalCreditHours(){
            return totalHours;
        }
        
        public String getName(){
            return name;
        }
        
    }
}

