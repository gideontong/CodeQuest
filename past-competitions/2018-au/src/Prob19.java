import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob19 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob19.in.txt";
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String inLine = null;
            
            // get the number of test cases
            int T = Integer.parseInt(br.readLine());
            
            // outer loop through test cases
            while (T-- > 0) {
                // BEGINNING OF TEST CASE CODE
                
                String[] tokens = null;
                
                // number of uppercase lines
                inLine = br.readLine();
                int numUpperCaseLines = Integer.parseInt(inLine);
                
                // uppercase line lengths
                int[] upperCaseLineLengths = new int[numUpperCaseLines];
                inLine = br.readLine();
                tokens = inLine.split(" ");
                for (int i=0; i<tokens.length; i++) {
                    upperCaseLineLengths[i] = Integer.parseInt(tokens[i]);
                }
                
                // number of lowercase lines
                inLine = br.readLine();
                int numLowerCaseLines = Integer.parseInt(inLine);
                
                // lowercase line lengths
                int[] lowerCaseLineLengths = new int[numLowerCaseLines];
                inLine = br.readLine();
                tokens = inLine.split(" ");
                for (int i=0; i<tokens.length; i++) {
                    lowerCaseLineLengths[i] = Integer.parseInt(tokens[i]);
                }
                
                String zippedText = null;
                StringBuffer buf = new StringBuffer();
                
                // loop through the lines in the file
                while ((inLine = br.readLine()) != null) {
                    buf.append(inLine);
                }
                zippedText = buf.toString();
                
                StringBuffer upperCaseBuffer = new StringBuffer();
                StringBuffer lowerCaseBuffer = new StringBuffer();
                
                int index = 0;
                char currentChar = 0;
                int upperCaseIndex = 0;
                int lowerCaseIndex = 0;
                String upperCaseLine = "";
                String lowerCaseLine = "";
                while (index < zippedText.length()) {
                    currentChar = zippedText.charAt(index++);
                    
                    if (isUpperCase(currentChar)) {
                        // add to uppercase buffer
                        if (currentChar == '-') {
                            currentChar = ' ';
                        }
                        upperCaseLine += currentChar;
                        if (upperCaseLine.length() == upperCaseLineLengths[upperCaseIndex]) {
                            upperCaseBuffer.append(upperCaseLine);
                            upperCaseBuffer.append("\n");
                            upperCaseIndex++;
                            upperCaseLine = "";
                        }
                    } else {
                        // add to lowercase buffer
                        if (currentChar == '=') {
                            currentChar = ' ';
                        }
                        lowerCaseLine += currentChar;
                        if (lowerCaseLine.length() == lowerCaseLineLengths[lowerCaseIndex]) {
                            lowerCaseBuffer.append(lowerCaseLine);
                            lowerCaseBuffer.append("\n");
                            lowerCaseIndex++;
                            lowerCaseLine = "";
                        }
                    }
                }
                
                System.out.println(upperCaseBuffer.toString());
                System.out.print(lowerCaseBuffer.toString());
                
                // END OF TEST CASE CODE
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static boolean isUpperCase(char theChar) {
        boolean retVal = false;
        
        if ((""+theChar).equals((""+theChar).toUpperCase())) {
            retVal = true;
        }
        
        if (theChar == '=') {
            retVal = false;
        }
        
        return retVal;
    }
}
