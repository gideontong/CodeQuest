

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Prob16 {
    private static final String INPUT_FILE_NAME = "Prob16.in.txt";
    
    private static final String ADDITION = "+";
    private static final String SUBTRACTION = "-";
    private static final String MULTIPLICATION = "*";
    private static final String DIVISION = "/";
    
    // arrays and variables to keep track of things during recursion
    private static String[] operators;
    private static String[] operands;
    private static String[] equation;
    private static boolean[] operatorsUsed;
    private static boolean[] operandsUsed;
//    private static boolean[] used;
    private static boolean foundSolution = false;
    private static int targetValue;
    
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
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                // split on the colon
                String[] tokens = inLine.split(":");
                
                // get the target result
                targetValue = Integer.parseInt(tokens[0]);
                
                // split the other side on the space
                equation = tokens[1].split(" ");
                
                // how much do we have?
                int numOperands = equation.length / 2;
                int numOperators = numOperands + 1;
                
                // create arrays
                operators = new String[numOperators];
                operands = new String[numOperands];
                
                operatorsUsed = new boolean[numOperators];
                operandsUsed = new boolean[numOperands];
                
                Arrays.fill(operatorsUsed, false);
                Arrays.fill(operandsUsed, false);
                
                int operatorIndex = 0;
                int operandIndex = 0;
                
                // break the items up
                for (String item : equation) {
                    try {
                        // try to parse it as a number
                        Integer.parseInt(item);
                        
                        // it worked, it's an operator
                        operators[operatorIndex++] = item;
                    } catch (NumberFormatException e) {
                        // it must be an operand
                        operands[operandIndex++] = item;
                    }
                }
                
                // flag for being done
                foundSolution = false;
                
                // solve the problem
                solve(0);
                
                if (foundSolution) {
                    System.out.println("TRUE");
                } else {
                    System.out.println("FALSE");
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void solve(int currentPosition) {
        if (currentPosition == equation.length) {
            // get the equation
            LinkedList<String> equationList = new LinkedList<String>();
            
            // create a linked list
            for (int i=0; i<equation.length; i++) {
                equationList.add(equation[i]);
            }
            
            // Multiplication and Division
            int currentIndex = 0;
            while (currentIndex < equationList.size()) {
                String currentElement = equationList.get(currentIndex);
                if (currentElement.equals(MULTIPLICATION) || currentElement.equals(DIVISION)) {
                    // multiplication or division - get set up in case we need to do multiple operations
                    int operator1 = Integer.parseInt(equationList.get(currentIndex-1));
                    
                    int numerator = operator1;
                    int denominator = 1;
                    
                    // loop until we are done multiplying or dividing or until we get to the end
                    boolean done = false;
                    while ((currentElement.equals(MULTIPLICATION) || currentElement.equals(DIVISION)) && !done) {
                        // remove the current set
                        int operator2 = Integer.parseInt(equationList.remove(currentIndex+1));
                        equationList.remove(currentIndex);
                        
                        if (currentElement.equals(MULTIPLICATION)) {
                            // multiply
                            numerator *= operator2;
                        } else {
                            // divide
                            denominator *= operator2;
                        }
                        
                        if (currentIndex >= equationList.size()) {
                            done = true;
                        } else {
                            // get the next operation
                            currentElement = equationList.get(currentIndex);
                        }
                    }
                    
                    // finally remove the first operator
                    equationList.remove(currentIndex-1);
                    
                    // don't divide by 0!
                    if (denominator == 0) return;
                    
                    // check for remainder
                    int remainder = numerator % denominator;
                    if (remainder > 0) {
                        // this will not be an integer answer - it can't work!
                        return;
                    } else {
                        // no remainder - continue to divide
                        int result = numerator / denominator;
                        
                        // put the result back
                        equationList.add(currentIndex-1, ""+result);
                    }
                } else {
                    // move along
                    currentIndex++;
                }
            }
            
            // Addition and Subtraction
            currentIndex = 0;
            while (currentIndex < equationList.size()) {
                String currentElement = equationList.get(currentIndex);
                if (currentElement.equals(ADDITION) || currentElement.equals(SUBTRACTION)) {
                    // found addition or subtraction - get the numbers
                    int operator2 = Integer.parseInt(equationList.remove(currentIndex+1));
                    String operand = equationList.remove(currentIndex);
                    int operator1 = Integer.parseInt(equationList.remove(currentIndex-1));
                    
                    // flip sign for subtraction
                    if (operand.equals(SUBTRACTION)) operator2 *= -1;
                    
                    // add them
                    int result = operator1 + operator2;
                    
                    // put the result back
                    equationList.add(currentIndex-1, ""+result);
                } else {
                    // move along
                    currentIndex++;
                }
            }
            
            // all operations should be done
            if (equationList.size() > 1) {
                System.out.println("ERROR! equationList.size() = " + equationList.size());
            } else {
                int result = Integer.parseInt(equationList.pop());
                if (result == targetValue) {
                    // found a working solution!
                    foundSolution = true;
                }
            }
        } else {
            // equation is not complete - fill in the next spot
            if ((currentPosition % 2) == 0) {
                // even number - operator
                for (int i=0; i<operators.length; i++) {
                    if (!operatorsUsed[i]) {
                        // try this operator
                        operatorsUsed[i] = true;
                        equation[currentPosition] = operators[i];
                        solve(currentPosition+1);
                        operatorsUsed[i] = false;
                    }
                    
                    if (foundSolution) break;
                }
            } else {
                // even number - operand
                for (int i=0; i<operands.length; i++) {
                    if (!operandsUsed[i]) {
                        // try this operand
                        operandsUsed[i] = true;
                        equation[currentPosition] = operands[i];
                        solve(currentPosition+1);
                        operandsUsed[i] = false;
                    }
                    
                    if (foundSolution) break;
                }
            }
        }
    }
}
