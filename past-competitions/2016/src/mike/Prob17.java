

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Prob17 {
    private static final String INPUT_FILE_NAME = "Prob17.in.txt";
    
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
                // get the number of rows in this honeycomb
                int N = Integer.parseInt(br.readLine());
                
                // loop through the rows and create the nodes
                Node[][] nodes = new Node[N][N];
                for (int i=0; i<N; i++) {
                    // read the line of text
                    inLine = br.readLine();
                    
                    // break it up
                    String[] tokens = inLine.split(",");
                    
                    int numNodes = i+1;
                    
                    // create nodes
                    for (int j=0; j<numNodes; j++) {
                        nodes[i][j] = new Node(tokens[j]);
                    }
                }
                
                // now nodes are created - link them together
                for (int r=0; r<N; r++) {
                    for (int c=0; c<=r; c++) {
                        // get the current node
                        Node node = nodes[r][c];
                        if (c > 0) {
                            node.setL(nodes[r][c-1]);
                            node.setUL(nodes[r-1][c-1]);
                        }
                        if (c < r) {
                            node.setUR(nodes[r-1][c]);
                            node.setR(nodes[r][c+1]);
                        }
                        if (r < (N-1)) {
                            node.setDR(nodes[r+1][c+1]);
                            node.setDL(nodes[r+1][c]);
                        }
                    }
                }
                
                // start nodes
                Node[] start = new Node[3];
                start[0] = nodes[0][0];
                start[1] = nodes[N-1][N-1];
                start[2] = nodes[N-1][0];
                
                // end nodes
                Node[] end = new Node[3];
                end[0] = nodes[N-1][N-1];
                end[1] = nodes[N-1][0];
                end[2] = nodes[0][0];
                
                // best distances
                int[] best = new int[3];
                Arrays.fill(best, 0);
                
                // take 3 trips
                for (int trip=0; trip<3; trip++) {
                    // reset the nodes
                    for (int i=0; i<N; i++) {
                        for (int j=0; j<i+1; j++) {
                            nodes[i][j].setBest(Integer.MAX_VALUE);
                        }
                    }
                    
                    // start at the end
                    end[trip].setBest(end[trip].getCost());
                    
                    // queue it up
                    LinkedList<Node> queue = new LinkedList<Node>();
                    queue.add(end[trip]);
                    end[trip].setQueued(true);
                    
                    // chew through the queue
                    while (!queue.isEmpty()) {
                        // get the next node
                        Node currentNode = queue.pop();
                        currentNode.setQueued(false);
                        
                        // push the best
                        currentNode.publishBest(queue);
                    }
                    
                    // remember the best
                    best[trip] = start[trip].getBest() - start[trip].getCost();
                }
                
                int grandTotal = best[0] + best[1] + best[2];
                System.out.println(grandTotal);
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class Node {
        // cost to step in this node
        private int cost;
        
        // the best path distance
        private int best;
        
        // connections to other nodes: L, UL, UR, R, DR, DL
        private Node[] connections;
        
        private boolean queued;
        
        public Node(String costStr) {
            cost = Integer.parseInt(costStr);
            
            connections = new Node[6];
            Arrays.fill(connections, null);
            
            best = Integer.MAX_VALUE;
            
            queued = false;
        }
        
        public void setL(Node n) {
            connections[0] = n;
        }
        
        public void setUL(Node n) {
            connections[1] = n;
        }
        
        public void setUR(Node n) {
            connections[2] = n;
        }
        
        public void setR(Node n) {
            connections[3] = n;
        }
        
        public void setDR(Node n) {
            connections[4] = n;
        }
        
        public void setDL(Node n) {
            connections[5] = n;
        }
        
        public int getCost() {
            return cost;
        }
        
        public int getBest() {
            return best;
        }
        
        public void setBest(int newBest) {
            best = newBest;
        }
        
        public void publishBest(LinkedList<Node> queue) {
            for (Node node : connections) {
                if (node != null) {
                    node.receiveBest(best, queue);
                }
            }
        }
        
        public void receiveBest(int newBest, LinkedList<Node> queue) {
            int total = newBest + cost;
            if (total <= best) {
                // we have a new best
                best = total;
                if (!queued) queue.add(this);
            }
        }

        public boolean isQueued() {
            return queued;
        }

        public void setQueued(boolean queued) {
            this.queued = queued;
        }
    }
}
