

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Prob08 {
    private static final String INPUT_FILE_NAME = "Prob08.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // data structures
            ArrayList<String> fileTypeList = new ArrayList<String>();
            HashMap<String, Integer> fileTypes = new HashMap<String, Integer>();
            
            // loop through test cases
            for (int t=0; t<T; t++) {
                // read the line of text
                inLine = br.readLine();
                
                // get the file type
                String[] tokens = inLine.split("\\.");
                String type = tokens[1];
                
                // add it if necessary
                if (fileTypes.get(type) == null) {
                    fileTypeList.add(type);
                    fileTypes.put(type, 0);
                }
                
                // add to the count
                int count = fileTypes.get(type);
                count++;
                fileTypes.put(type, count);
            }
            
            // now print
            for (String type : fileTypeList) {
                System.out.println(type + " " + fileTypes.get(type));
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
