

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Prob15 {
    private static final String INPUT_FILE_NAME = "Prob15.in.txt";
    
    private static final String allOperatorsString = "()^*/+-";
    private static final String operatorsString = "^*/+-";
    private static final String level2Ops = "*/";
    private static final String level1Ops = "+-";
    
    /**
     * Disclaimer: When I was researching if this would be a good problem or not, 
     * I came across this Wikipedia page about the shunting-yard algorithm:
     * 
     * http://en.wikipedia.org/wiki/Shunting-yard_algorithm
     * 
     * Once I saw this, I could not un-see it, so it drove my implementation.
     * My first instinct was to do a recursive implementation and find the 
     * innermost parenthesis and work out from there.  But, that never happened
     * because I couldn't get this algorithm out of my head.
     * 
     * Such is life.  :)
     */
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
                // read the number of lines
                int N = Integer.parseInt(br.readLine());
                
                // loop through the lines
                while (N-- > 0) {
                    // read in the expression
                    inLine = br.readLine();
                    
                    // split on spaces
                    String[] tokens = inLine.split(" ");
                    
                    // holder for final output
                    LinkedList<String> output = new LinkedList<String>();
                    
                    // temp storage for stuff we're not ready for yet
                    LinkedList<String> opStack = new LinkedList<String>();
                    
                    // loop through the arguments
                    for (int i=0; i<tokens.length; i++) {
                        // get the current token
                        String currentToken = tokens[i];
                        
                        if (isOperand(currentToken)) {
                            // this token is an operand - add to the output
                            output.addLast(currentToken);
                        } else {
                            // this token is not an operand, so it's either a paren or an operator
                            if (isOperator(currentToken)) {
                                // this token is an operator - is it exponentiation?
                                // If so, it's pushed to the operator stack
                                if (!currentToken.equals("^")) {
                                    // not an exponent - look at the top of the stack
                                    String topItem = opStack.peek();
                                    
                                    while (isOperator(topItem)) {
                                        // an operator is on top - check precedence
                                        if (comparePrecedence(currentToken, topItem) <= 0) {
                                            // current token has lower or equal precedence - pop!
                                            output.addLast(opStack.pop());
                                            topItem = opStack.peek();
                                        } else {
                                            // current token has higher precedence - stop
                                            break;
                                        }
                                    }
                                }
                                
                                // push the current token on the operator stack
                                opStack.push(currentToken);
                            } else {
                                // this token is a paren
                                if (currentToken.equals("(")) {
                                    // left paren - just push
                                    opStack.push(currentToken);
                                } else {
                                    // right paren - pop until we find the matching left paren
                                    String topItem = opStack.peek();
                                    
                                    // pop until we see the left paren at the top of the list
                                    while (!topItem.equals("(")) {
                                        output.addLast(opStack.pop());
                                        topItem = opStack.peek();
                                    }
                                    
                                    // throw away the left paren
                                    opStack.pop();
                                }
                            }
                        }
                    }
                    
                    // move remaining operators to the output
                    while (opStack.peek() != null) {
                        output.addLast(opStack.pop());
                    }
                    
                    // print the output
                    while (output.peekFirst() != null) {
                        // print the first character
                        System.out.print(output.removeFirst());
                        
                        if (output.peekFirst() != null) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static boolean isOperand(String x) {
        if (x == null) return false;
        return (allOperatorsString.indexOf(x) == -1);
    }
    
    private static boolean isOperator(String x) {
        if (x == null) return false;
        return (operatorsString.indexOf(x) > -1);
    }
    
    /**
     * @return:
     * -1 if o1 < o2
     * 0 if o1 == o2
     * 1 if o1 > o2
     */
    private static int comparePrecedence(String o1, String o2) {
        if (o1.equals(o2)) {
            // same operator
            return 0;
        } else {
            // find level of each operator
            int l1 = findLevel(o1);
            int l2 = findLevel(o2);
            
            if (l1 > l2) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    
    private static int findLevel(String o) {
        if (level1Ops.indexOf(o) > -1) {
            // + or -
            return 1;
        } else if (level2Ops.indexOf(o) > -1) {
            // * or /
            return 2;
        } else {
            // ^
            return 3;
        }
    }
}
