import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Prob18 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob18.in.txt";
    
    private static final int[] fromNodes = {0, 1, 0, 2, 0, 3, 0};
    private static final int[] toNodes = {1, 2, 2, 3, 3, 4, 4};
    private static final int[] pathNums = {1, 2, 3, 4, 5, 6, 7};
    
    // static variables
    public static Node[] nodes = new Node[5];
    public static boolean[] visited = new boolean[5];
    public static int[] timesVisited = new int[5];
    public static int bestTime;
    public static String bestPath;
    public static ArrayList<Integer> path;
    public static int time;
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            int[] weights = new int[7];
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                // reset
                bestTime = Integer.MAX_VALUE;
                bestPath = "";
                path = new ArrayList<Integer>();
                Arrays.fill(visited, false);
                Arrays.fill(timesVisited, 0);
                time = 0;
                
                // read the next line of text
                inLine = br.readLine();
                
                String[] tokens = inLine.split(" ");
                
                // get path weights
                for (int i=0; i<7; i++) {
                    weights[i] = Integer.parseInt(tokens[i]);
                }
                
                // create nodes
                createNodes(weights);
                
                // visit the start
                visited[0] = true;
                timesVisited[0]++;
                
                // go!
                solve(0);
                
                // print the answer
//                System.out.println("time = " + bestTime);
                System.out.println(bestPath);
                
                // END OF TEST CASE CODE
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void solve(int currentIndex) {
        // never visit a node more than 4 times!
        if (timesVisited[currentIndex] == 5) {
            return;
        }
        
        // if we've gone too far, stop!
        if (time >= bestTime) {
            return;
        }
        
        // if we're done, stop!
        boolean done = true;
        for (int i=0; i<5; i++) {
            if (!visited[i]) {
                done = false;
                break;
            }
        }
        
        if (done) {
            // check to see if we beat the best time
            if (time < bestTime) {
                bestTime = time;
//                System.out.println("new best time = " + bestTime);
                
                bestPath = "";
                for (int i=0; i<path.size(); i++) {
                    if (i > 0) bestPath += " ";
                    bestPath += path.get(i).intValue();
                }
                
//                System.out.println(bestPath);
            }
            return;
        }
        
        // get the current node
        Node currentNode = nodes[currentIndex];
        
        // get the destinations and weights
        int[] destinations = currentNode.getDestinationsArray();
        int[] weights = currentNode.getWeightsArray();
        int[] nums = currentNode.getPathNumsArray();
        
        // loop through paths and try unvisited destinations first
        for (int i=0; i<destinations.length; i++) {
            int nextNode = destinations[i];
            int nextWeight = weights[i];
            int pathNum = nums[i];
            
//            if (!visited[nextNode]) {
                // not visited yet, try this path
                time += nextWeight;
                boolean saveVisited = visited[nextNode];
                visited[nextNode] = true;
                timesVisited[nextNode]++;
                path.add(pathNum);
                
                solve(nextNode);
                
                // step back
                time -= nextWeight;
                visited[nextNode] = saveVisited;
                timesVisited[nextNode]--;
                path.remove(path.size()-1);
//            }
        }
        
        // now loop through backtracking
//        for (int i=0; i<destinations.length; i++) {
//            int nextNode = destinations[i];
//            int nextWeight = weights[i];
//            
//            if (visited[nextNode]) {
//                // not visited yet, try this path
//                time += nextWeight;
//                visited[nextNode] = true;
//                timesVisited[nextNode]++;
//                
//                solve(nextNode);
//                
//                // step back
//                time -= nextWeight;
//                visited[nextNode] = false;
//                timesVisited[nextNode]--;
//            }
//        }
    }
    
    // Node 0: Start
    // Node 1: Pirates
    // Node 2: Splash
    // Node 3: Dwarves
    // Node 4: Space
    private static void createNodes(int[] weights) {
        // create the 5 nodes
        for (int i=0; i<5; i++) {
            nodes[i] = new Node();
        }
        
        // add paths
        for (int i=0; i<7; i++) {
            addPath(fromNodes[i], toNodes[i], weights[i], pathNums[i]);
        }
    }
    
    private static void addPath(int fromNode, int toNode, int weight, int pathNum) {
        nodes[fromNode].addPath(toNode, weight, pathNum);
        nodes[toNode].addPath(fromNode, weight, pathNum);
    }
    
    public static class Node {
        ArrayList<Integer> destinations;
        ArrayList<Integer> weights;
        ArrayList<Integer> nums;
        
        public Node() {
            destinations = new ArrayList<Integer>();
            weights = new ArrayList<Integer>();
            nums = new ArrayList<Integer>();
        }
        
        public void addPath(int destination, int weight, int pathNum) {
            destinations.add(destination);
            weights.add(weight);
            nums.add(pathNum);
        }
        
        public int[] getGenericArray(ArrayList<Integer> arrayList) {
            int[] retVal = new int[arrayList.size()];
            for (int i=0; i<arrayList.size(); i++) {
                retVal[i] = arrayList.get(i);
            }
            return retVal;
        }
        
        public int[] getDestinationsArray() {
            return getGenericArray(destinations);
        }
        
        public int[] getWeightsArray() {
            return getGenericArray(weights);
        }
        
        public int[] getPathNumsArray() {
            return getGenericArray(nums);
        }
    }
}
