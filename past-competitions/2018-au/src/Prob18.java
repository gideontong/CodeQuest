import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Prob18 {
    // change this file name for each problem!
    private static final String INPUT_FILE_NAME = "Prob18.in.txt";
    
    private static final String[] DATA_HEADERS = {"Name: ", "Age: ", "Instagram: ", "Twitter: ", "Phone: ", "Email: "};
    
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
                
                // get the number of lines in each test case
                int N = Integer.parseInt(br.readLine());
                
                // create the data arrays - N people with 6 data items each.  data[personNum][dataItem] retrieves dataItem of personNum.
                String[][] data = new String[N][6];
                
                // read the next line of text - contains the data
                inLine = br.readLine();
                
                // eat the leading and trailing []
                inLine = inLine.substring(1, inLine.length()-1);
                
                // separate into arrays
                String[] arrays = inLine.split("\\],\\[");
                
                for (int dataElement=0; dataElement<6; dataElement++) {
                    String dataString = arrays[dataElement];
                    
                    // eat the leading [ if necessary
                    if (dataElement == 0) {
                        dataString = dataString.substring(1);
                    }
                    
                    // eat the ending ] if necessary
                    if (dataElement == 5) {
                        dataString = dataString.substring(0, dataString.length()-1);
                    }
                    
                    // split the data
                    String[] dataElements = dataString.split(",");
                    
                    // store the data
                    for (int personNum=0; personNum<N; personNum++) {
                        data[personNum][dataElement] = dataElements[personNum];
                    }
                }
                
                // now data is stored, read each name and output their profile
                for (int i=0; i<N; i++) {
                    String profileName = br.readLine();
                    
                    // find the person we want
                    int index = -1;
                    for (int personNum=0; personNum<N; personNum++) {
                        if (data[personNum][0].equals(profileName)) {
                            index = personNum;
                            break;
                        }
                    }
                    
                    // print their profile
                    for (int dataItem=0; dataItem<6; dataItem++) {
                        System.out.println(DATA_HEADERS[dataItem] + data[index][dataItem]);
                    }
                }
                
                // END OF TEST CASE CODE
            }
            
            // clean up
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
