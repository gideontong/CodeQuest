

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Prob14 {
    private static final String INPUT_FILE_NAME = "Prob14.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // quick way to find nodes
            HashMap<String, Node> nodeMap = new HashMap<String, Node>();
            
            // top level node to store things with no parent
            Node topLevel = new Node("None");
            nodeMap.put("None", topLevel);
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // loop through test cases
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                // split on the comma
                String[] tokens = inLine.split(",");
                String childName = tokens[0];
                String parentName = tokens[1];
                
                // get the parent
                Node parent = nodeMap.get(parentName);
                
                // create a new node
                Node child = new Node(childName);
                nodeMap.put(childName, child);
                
                // add the child to the parent
                parent.add(child);
            }
            
            // now print out the heirarchy
            topLevel.print(-1);
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class Node implements Comparable<Node> {
        String name;
        ArrayList<Node> children;
        
        public Node(String theName) {
            this.name = theName;
            children = new ArrayList<Node>();
        }
        
        public void add(Node childNode) {
            children.add(childNode);
        }
        
        public String getName() {
            return this.name;
        }
        
        public void print(int depth) {
            if (depth >= 0) {
                // print myself
                for (int i=0; i<depth; i++) {
                    System.out.print("-");
                }
                System.out.println(this.name);
            }
            
            if (children.size() > 0) {
                // get an array of the children
                Node[] childrenArray = new Node[children.size()];
                childrenArray = children.toArray(childrenArray);
                
                //sort the child nodes
                Arrays.sort(childrenArray);
                
                for (Node child : childrenArray) {
                    child.print(depth + 1);
                }
            }
        }
        
        @Override
        public int compareTo(Node otherNode) {
            return this.name.compareTo(otherNode.getName());
        }
    }
}
