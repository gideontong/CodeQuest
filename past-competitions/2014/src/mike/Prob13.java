/**
 * CodeQuest 2014
 * Problem 13: Diamond Path
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

package cq2014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Prob13 {
    private static final String INPUT_FILE_NAME = "Prob13.in.txt";
    
    private static ArrayList<String> lines = new ArrayList<String>();
    private static ArrayList<Node> nodes = new ArrayList<Node>();
    private static int numNodes = 0;
    private static Node startNode = null;
    private static Node endNode = null;
    private static ArrayList<String> pathNames = new ArrayList<String>();
    
    public static void main(String[] args) {
        // read the input file and process it
        readInput();
        
        for (int i=nodes.size()-1; i>=0; i--) {
            Node currentNode = nodes.get(i);
            currentNode.findBestPath();
        }
        
        int bestPathLength = startNode.getBestPathCost();
        int numBestPaths = startNode.getNumBestPaths();
        
        
        System.out.println("Lowest path difficulty: " + bestPathLength);
        System.out.println("Number of paths with the lowest difficulty: " + numBestPaths);
    }
    
    public static int play() {
        lines.clear();
        nodes.clear();
        numNodes = 0;
        startNode = null;
        endNode = null;
        pathNames.clear();
        
        readInput();
        for (int i=nodes.size()-1; i>=0; i--) {
            Node currentNode = nodes.get(i);
            currentNode.findBestPath();
        }
        return startNode.getNumBestPaths();
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
            }
            
            // calculate total number of nodes
            int numLines = lines.size();
            int i = 0;
            for (i=3; i<numLines; i+=2) {
                numNodes += i*2;
            }
            numNodes += i;
            
            // build the starting node for later use
            startNode = new Node();
            addNode(startNode);
            
            // build the nodes
            for (i=0; i<numNodes; i++) {
                addNode(new Node());
            }
            
            // build the destination node for later use
            endNode = new Node();
            for (i=0; i<3; i++) {
                endNode.setChild(i, null, 0);
            }
            addNode(endNode);
            
            // build the diamond
            int currentNodeIndex = 0;
            int startNodeIndex = 0;
            int endNodeIndex = 0;
            int nodesOnLevel = 1;
            Node currentNode = null;
            for (i=0; i<numLines; i++) {
                // move the end node index
                endNodeIndex += nodesOnLevel;
                
                // get the weights
                String[] tokens = lines.get(i).split(" ");
                int currentTokenIndex = 0;
                
                for (currentNodeIndex=startNodeIndex; currentNodeIndex<endNodeIndex; currentNodeIndex++) {
                    // get the node
                    currentNode = nodes.get(currentNodeIndex);
                    
                    if (i < numLines/2) {
                        // the diamond is expanding
                        for (int j=0; j<3; j++) {
                            int targetIndex = currentNodeIndex + nodesOnLevel + j;
                            currentNode.setChild(j, nodes.get(targetIndex), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                        }
                    } else {
                        // the diamond is shrinking
                        if (i == (numLines-1)) {
                            // last line - do this differently
                            currentNode.setChild(0, null, 0);
                            currentNode.setChild(1, null, 0);
                            currentNode.setChild(2, nodes.get(currentNodeIndex+nodesOnLevel), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                            currentNodeIndex++;
                            
                            currentNode = nodes.get(currentNodeIndex);
                            currentNode.setChild(0, null, 0);
                            currentNode.setChild(1, nodes.get(currentNodeIndex+nodesOnLevel-1), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                            currentNode.setChild(2, null, 0);
                            currentNodeIndex++;
                            
                            currentNode = nodes.get(currentNodeIndex);
                            currentNode.setChild(0, nodes.get(currentNodeIndex+nodesOnLevel-2), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                            currentNode.setChild(1, null, 0);
                            currentNode.setChild(2, null, 0);
                            currentNodeIndex++;
                        } else {
                            if (currentNodeIndex == (endNodeIndex - nodesOnLevel)) {
                                // first node - set first two cheldren to null
                                currentNode.setChild(0, null, 0);
                                currentNode.setChild(1, null, 0);
                                currentNode.setChild(2, nodes.get(currentNodeIndex+nodesOnLevel), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                            } else if (currentNodeIndex == (endNodeIndex - nodesOnLevel + 1)) {
                                // second node - set first child to null
                                currentNode.setChild(0, null, 0);
                                for (int j=1; j<3; j++) {
                                    int targetIndex = currentNodeIndex + nodesOnLevel-2 + j;
                                    currentNode.setChild(j, nodes.get(targetIndex), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                                }
                            } else if (currentNodeIndex == (endNodeIndex - 2)) {
                                // second to last node - set last child to null
                                currentNode.setChild(2, null, 0);
                                for (int j=0; j<2; j++) {
                                    int targetIndex = currentNodeIndex + nodesOnLevel-2 + j;
                                    currentNode.setChild(j, nodes.get(targetIndex), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                                }
                            } else if (currentNodeIndex == (endNodeIndex - 1)) {
                                // last node - set last two children to null
                                currentNode.setChild(1, null, 0);
                                currentNode.setChild(2, null, 0);
                                currentNode.setChild(0, nodes.get(currentNodeIndex+nodesOnLevel-2), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                            } else {
                                // in the middle - do like normal
                                for (int j=0; j<3; j++) {
                                    int targetIndex = currentNodeIndex + nodesOnLevel-2 + j;
                                    currentNode.setChild(j, nodes.get(targetIndex), Integer.valueOf(tokens[currentTokenIndex++]).intValue());
                                }
                            }
                        }
                    }
                }
                
                // shift the start position and the nodes per level
                startNodeIndex = endNodeIndex;
                nodesOnLevel += (i < numLines/2) ? 2 : -2;
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void addNode(Node theNode) {
        theNode.setNodeNum(nodes.size());
        nodes.add(theNode);
    }
    
    public static class Node {
        private Node[] children = new Node[3];
        private int[] weights = new int[3];
        private int nodeNum = -1;
        private int numBestPaths = 0;
        private int bestPathCost = Integer.MAX_VALUE;
        
        public void setChild(int idx, Node theNode, int theWeight) {
            children[idx] = theNode;
            weights[idx] = theWeight;
        }
        
        public Node getChild(int idx) {
            return children[idx];
        }
        
        public int getWeight(int idx) {
            return weights[idx];
        }
        
        public int getNodeNum() {
            return nodeNum;
        }
        
        public void setNodeNum(int nodeNum) {
            this.nodeNum = nodeNum;
        }
        
        public int getNumBestPaths() {
            return numBestPaths;
        }
        
        public int getBestPathCost() {
            return bestPathCost;
        }
        
        public void setBestPathCost(int bestPathCost) {
            this.bestPathCost = bestPathCost;
        }
        
        public void findBestPath() {
            if (this == endNode) {
                // treat the end node differently
                bestPathCost = 0;
                numBestPaths = 1;
            } else {
                int leftPathCost = (children[0] == null) ? Integer.MAX_VALUE : children[0].getBestPathCost() + weights[0];
                int centerPathCost = (children[1] == null) ? Integer.MAX_VALUE : children[1].getBestPathCost() + weights[1];
                int rightPathCost = (children[2] == null) ? Integer.MAX_VALUE : children[2].getBestPathCost() + weights[2];
                
                // set the return value
                int lowestValue = findLowestValue(leftPathCost, centerPathCost, rightPathCost);
                
                bestPathCost = lowestValue;
                if (leftPathCost == lowestValue) {
                    numBestPaths += children[0].getNumBestPaths();
                }
                if (centerPathCost == lowestValue) {
                    numBestPaths += children[1].getNumBestPaths();
                }
                if (rightPathCost == lowestValue) {
                    numBestPaths += children[2].getNumBestPaths();
                }
            }
        }
    }
    
    private static int findLowestValue(int a, int b, int c) {
        if ((a <= b) && (a <= c)) {
            return a;
        } else if ((b <= a) && (b <= c)) {
            return b;
        } else {
            return c;
        }
    }
}
