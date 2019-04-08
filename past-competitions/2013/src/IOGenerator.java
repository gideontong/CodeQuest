import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;


public class IOGenerator {
    
    public static void main(String[] args) {
        try {
            // prepare to read the file
            File inFile = new File("Prob01.in.txt");
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String[] values = {"A", "B", "X", "1", "5", "8", "U", "Q", "Y", "3", "R", "G", "K", "4", "E", "7"};
            Hashtable<String, Integer> valueMap = new Hashtable<String, Integer>();
            for (int i=0; i<values.length; i++) {
                valueMap.put(values[i], Integer.valueOf(i));
            }
            String inLine = null;
            
            int numLines = 0;
            
            // loop through the lines in the file
            while ((inLine = br.readLine()) != null) {
                if ((numLines++ % 2) == 0) {
                    System.out.print("Alice: ");
                } else {
                    System.out.print("Bob: ");
                }
                System.out.print("https://www.youtube.com/watch?v=");
                
                for (int i=0; i<inLine.length(); i++) {
                    char currentChar = inLine.charAt(i);
                    int topHalf = ((int)currentChar) / 16;
                    int bottomHalf = ((int)currentChar) % 16;
                    System.out.print(values[topHalf]);
                    System.out.print(values[bottomHalf]);
                }
                System.out.println();
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
//    public static void main(String[] args) {
//        Random randomGenerator = new Random();
//        ArrayList<String> words = new ArrayList<String>();
//        
//        try {
//            // prepare to read the file
//            File inFile = new File("enable1.txt");
//            FileReader fr = new FileReader(inFile);
//            BufferedReader br = new BufferedReader(fr);
//            
//            String inLine = null;
//            
//            // loop through the lines in the file
//            while ((inLine = br.readLine()) != null) {
//                if (inLine.length() > 7) {
//                    words.add(inLine);
//                }
//            }
//            
//            // clean up
//            br.close();
//            fr.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        for (int i=0; i<100; i++) {
//            int theInt = randomGenerator.nextInt(words.size());
//            
//            if ((theInt%2) == 0) {
//                // combine - just need a list of words
//                System.out.print("C;");
//                if ((theInt%3) == 0) {
//                    System.out.print("M;");
//                } else if ((theInt%3) == 1) {
//                    System.out.print("C;");
//                } else {
//                    System.out.print("V;");
//                }
//                for (int j=0; j<4; j++) {
//                    if (j > 0) System.out.print(" ");
//                    System.out.print(words.get(randomGenerator.nextInt(words.size())));
//                }
//            } else {
//                // split - need to build the rigth type of thing
//                System.out.print("S;");
//                if ((theInt%3) == 0) {
//                    // method
//                    System.out.print("M;");
//                } else if ((theInt%3) == 1) {
//                    // class
//                    System.out.print("C;");
//                } else {
//                    // variable
//                    System.out.print("V;");
//                }
//                for (int j=0; j<4; j++) {
//                    String theWord = words.get(randomGenerator.nextInt(words.size()));
//                    if (j == 0) {
//                        // only capitalize class first letter
//                        if ((theInt%3) == 1) {
//                            theWord = capFirst(theWord);
//                        }
//                    } else {
//                        // always cap later words
//                        theWord = capFirst(theWord);
//                    }
//                    System.out.print(theWord);
//                }
//                if ((theInt%3) == 0) {
//                    System.out.print("()");
//                }
//            }
//            
//            // method: plasticCup()
//            // class: LargeSoftwareBook
//            // variable: pictureFrame
//            
//            System.out.println();
//        }
//    }
//    
//    private static String capFirst(String aString) {
//        StringBuffer buf = new StringBuffer();
//        
//        buf.append(("" + aString.charAt(0)).toUpperCase());
//        
//        for (int i=1; i<aString.length(); i++) {
//            buf.append(aString.charAt(i));
//        }
//        
//        return buf.toString();
//    }
    
}
