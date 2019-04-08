/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class Prob18 {
    private static final String INPUT_FILE_NAME = "Prob18.in.txt";
    private static final String ONE = "1";
    private static final String ZERO = "0";
    
    // gate types with key = node number
    private static HashMap<String, String> gateTypes = new HashMap<String, String>();
    
    // ArrayList of input nodes to each gate with key = node number
    private static HashMap<String, ArrayList<String>> gateInputs = new HashMap<String, ArrayList<String>>();
    
    // list of inputs
    private static ArrayList<String> primaryInputList = new ArrayList<String>();
    
    // list of outputs
    private static ArrayList<String> primaryOutputList = new ArrayList<String>();
    
    // list of gates that we need to evaluate upon applying new circuit inputs
    private static ArrayList<String> evaluationList = new ArrayList<String>();
    
    // input list
    private static ArrayList<String> inputLines = new ArrayList<String>();
    
    // values and stuck at nodes in the circuit
    private static HashMap<String, String> nodeValues = new HashMap<String, String>();
    private static HashMap<String, String> stuckAtList = new HashMap<String, String>();
    
    public static void main(String[] args) {
        readInput();
        
        String currentNode = null;
        
        // loop through the input sets
        for (int inputNum=0; inputNum<inputLines.size(); inputNum++) {
            if (inputNum > 0) {
                // print out an extra line between runs of the circuit
                System.out.println();
            }
            
            // reset the circuit
            resetCircuit();
            
            // get the new inputs
            String inLine = inputLines.get(inputNum);
            
            // split using spaces
            String[] tokens = inLine.split(" ");
            
            // set primary input values
            for (int i=0; i<primaryInputList.size(); i++) {
                currentNode = primaryInputList.get(i);
                nodeValues.put(currentNode, tokens[i]);
            }
            
            // set the stuck at list with extra tokens
            if (tokens.length > primaryInputList.size()) {
                int extraTokenLength = 0;
                String stuckAtNode = null;
                String stuckAtVal = null;
                for (int i=primaryInputList.size(); i<tokens.length; i++) {
                    String currentToken = tokens[i];
                    switch (extraTokenLength % 4) {
                        case 0:
                            // the word NODE
                            break;
                        case 1:
                            // the node number that's stuck
                            stuckAtNode = currentToken;
                            break;
                        case 2:
                            // the word SA
                            break;
                        case 3:
                            // the value of the stuck node
                            stuckAtVal = currentToken;
                            stuckAtList.put(stuckAtNode, stuckAtVal);
                            break;
                    }
                    extraTokenLength++;
                }
            }
            
            // evaluate the circuit
            for (int i=0; i<evaluationList.size(); i++) {
                currentNode = evaluationList.get(i);
                evaluateNode(currentNode);
            }
            
            // print the outputs
            for (int i=0; i<primaryOutputList.size(); i++) {
                System.out.println("PO " + primaryOutputList.get(i) + " = " + nodeValues.get(primaryOutputList.get(i)));
            }
        }
    }
    
    private static void evaluateNode(String nodeNumber) {
        String currentValue = null;
        String stuckAtValue = stuckAtList.get(nodeNumber);
        if (stuckAtValue != null) {
            // gate is stuck
            currentValue = stuckAtValue;
        } else {
            // gate is not stuck - evaluate it
            String gateType = gateTypes.get(nodeNumber);
            ArrayList<String> inputs = gateInputs.get(nodeNumber);
            currentValue = evaluateGate(gateType, inputs);
        }
        
        // save the gate's value
        nodeValues.put(nodeNumber, currentValue);
    }
    
    private static String evaluateGate(String gateType, ArrayList<String> inputs) {
        String retVal = null;
        
        if (gateType.equals("PO")) {
            retVal = evaluatePO(inputs);
        } else if (gateType.equals("NOT")) {
            retVal = evaluateNOT(inputs);
        } else if (gateType.equals("AND")) {
            retVal = evaluateAND(inputs);
        } else if (gateType.equals("NAND")) {
            retVal = evaluateAND(inputs);
            retVal = evaluateNOT(retVal);
        } else if (gateType.equals("OR")) {
            retVal = evaluateOR(inputs);
        } else if (gateType.equals("NOR")) {
            retVal = evaluateOR(inputs);
            retVal = evaluateNOT(retVal);
        } else if (gateType.equals("XOR")) {
            retVal = evaluateXOR(inputs);
        } else if (gateType.equals("XNOR")) {
            retVal = evaluateXOR(inputs);
            retVal = evaluateNOT(retVal);
        }
        
        return retVal;
    }
    
    private static String evaluateXOR(ArrayList<String> inputs) {
        String retVal = null;
        
        int oneCount = 0;
        for (int i=0; i<inputs.size(); i++) {
            String input = nodeValues.get(inputs.get(i));
            if (input.equals(ONE)) {
                oneCount++;
            }
        }
        
        if ((oneCount % 2) == 0) {
            // even number of ones - return 0
            retVal = ZERO;
        } else {
            // odd number of ones - return 1
            retVal = ONE;
        }
        
        return retVal;
    }
    
    private static String evaluateOR(ArrayList<String> inputs) {
        // assume all zeroes
        String retVal = ZERO;
        
        for (int i=0; i<inputs.size(); i++) {
            String input = nodeValues.get(inputs.get(i));
            if (input.equals(ONE)) {
                // found a one - return 1
                retVal = ONE;
                i = inputs.size();
            }
        }
        
        return retVal;
    }
    
    private static String evaluateAND(ArrayList<String> inputs) {
        // assume all ones
        String retVal = ONE;
        
        for (int i=0; i<inputs.size(); i++) {
            String input = nodeValues.get(inputs.get(i));
            if (input.equals(ZERO)) {
                // found a zero - return 0
                retVal = ZERO;
                i = inputs.size();
            }
        }
        
        return retVal;
    }
    
    private static String evaluateNOT(String input) {
        String retVal = null;
        
        // just flip what comes in
        if (input.equals(ZERO)) {
            retVal = ONE;
        } else {
            retVal = ZERO;
        }
        
        return retVal;
    }
    
    private static String evaluateNOT(ArrayList<String> inputs) {
        String retVal = null;
        
        String input = nodeValues.get(inputs.get(0));
        retVal = evaluateNOT(input);
        
        return retVal;
    }
    
    private static String evaluatePO(ArrayList<String> inputs) {
        String retVal = null;
        
        retVal = nodeValues.get(inputs.get(0));
        
        return retVal;
    }
    
    private static void resetCircuit() {
        nodeValues = new HashMap<String, String>();
        stuckAtList = new HashMap<String, String>();
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            boolean readCircuit = false;
            
            while ((inLine = br.readLine()) != null) {
                
                if (readCircuit) {
                    // now reading inputs
                    inputLines.add(inLine);
                } else {
                    // still reading the circuit description
                    if (inLine.equals("INPUTS")) {
                        // end of the circuit description
                        readCircuit = true;
                    } else {
                        // new circuit node - split on the spaces
                        String[] tokens = inLine.split(" ");
                        
                        String nodeNumber = tokens[0];
                        String gateType = tokens[1];
                        gateTypes.put(nodeNumber, gateType);
                        
                        // remember nodes that feed this gate
                        ArrayList<String> inputs = new ArrayList<String>();
                        if (tokens.length > 2) {
                            for (int i=2; i<tokens.length; i++) {
                                inputs.add(tokens[i]);
                            }
                        }
                        gateInputs.put(nodeNumber, inputs);
                        
                        if (gateType.equals("PI")) {
                            // PI gates cannot be stuck
                            primaryInputList.add(nodeNumber);
                        } else {
                            // need to evaluate every gate except PI
                            evaluationList.add(nodeNumber);
                            
                            if (gateType.equals("PO")) {
                                // remember PO's for printing later
                                primaryOutputList.add(nodeNumber);
                            }
                        }
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
