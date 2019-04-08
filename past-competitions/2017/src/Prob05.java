

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Prob05 {
    private static final String INPUT_FILE_NAME = "Prob05.in.txt";
    
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
                // map of donors by name
                HashMap<String, Node> nodeMap = new HashMap<String, Node>();
                
                // read last year's donors
                inLine = br.readLine();
                
                // split on the commas
                String[] tokens = inLine.split(",");
                
                // look through names
                for (String currentName : tokens) {
                    // get the node
                    Node currentNode = nodeMap.get(currentName);
                    
                    // do we need to create a new one?
                    if (currentNode == null) {
                        currentNode = new Node(currentName);
                        nodeMap.put(currentName, currentNode);
                    }
                    
                    // flag this as a last year donor
                    currentNode.setLastYear(true);
                }
                
                // read this year's donors
                inLine = br.readLine();
                
                // split on the commas
                tokens = inLine.split(",");
                
                // look through names
                for (String currentName : tokens) {
                    // get the node
                    Node currentNode = nodeMap.get(currentName);
                    
                    // do we need to create a new one?
                    if (currentNode == null) {
                        currentNode = new Node(currentName);
                        nodeMap.put(currentName, currentNode);
                    }
                    
                    // flag this as a this year donor
                    currentNode.setThisYear(true);
                }
                
                // now sort them
                ArrayList<Node> lastYearDonorList = new ArrayList<Node>();
                ArrayList<Node> thisYearDonorList = new ArrayList<Node>();
                ArrayList<Node> bothYearDonorList = new ArrayList<Node>();
                
                for (Node currentNode : nodeMap.values()) {
                    if (currentNode.isLastYear()) {
                        // donated last year, what about this year?
                        if (currentNode.isThisYear()) {
                            // donated both years
                            bothYearDonorList.add(currentNode);
                        } else {
                            // just last year
                            lastYearDonorList.add(currentNode);
                        }
                    } else {
                        // didn't donate last year, so they must just be this year
                        thisYearDonorList.add(currentNode);
                    }
                }
                
                // now sort and print
                sortAndPrint(lastYearDonorList);
                sortAndPrint(bothYearDonorList);
                sortAndPrint(thisYearDonorList);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void sortAndPrint(ArrayList<Node> nodeList) {
        // put the list into an array
        Node[] nodeArray = new Node[nodeList.size()];
        nodeArray = nodeList.toArray(nodeArray);
        
        // sort the array
        Arrays.sort(nodeArray);
        
        // print
        for (int i=0; i<nodeArray.length; i++) {
            if (i > 0) System.out.print(",");
            System.out.print(nodeArray[i].getName());
        }
        System.out.println();
    }
    
    public static class Node implements Comparable<Node> {
        String name;
        boolean lastYear = false;
        boolean thisYear = false;
        
        public Node(String theName) {
            this.name = theName;
        }
        
        public String getName() {
            return name;
        }
        
        public boolean isLastYear() {
            return lastYear;
        }
        
        public void setLastYear(boolean lastYear) {
            this.lastYear = lastYear;
        }
        
        public boolean isThisYear() {
            return thisYear;
        }
        
        public void setThisYear(boolean thisYear) {
            this.thisYear = thisYear;
        }
        
        @Override
        public int compareTo(Node o) {
            return this.name.compareTo(o.getName());
        }
    }
}
