import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Christopher Szewczyk
 */
public class LeapYear
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "LeapYear.in.txt";

    /**
     * The entry point for the application.
     *
     * @param arguments the command line arguments
     */
    public static void main(String[] arguments)
    {
        // Check to see if a file was specified on the command line.
        String filePath = arguments.length == 1 ? arguments[0] : INPUT_FILE_PATH;

        // Load up the file to be read.
        File input = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(input)))
        {
        	// Get the number of tests to perform.
            int numberOfTests = Integer.parseInt(reader.readLine());
            if (numberOfTests > 0)
            {
                // Go through each test and process it.
                for (int i = 0; i < numberOfTests; i++)
                {
                	List<String> block = readTestBlock(reader);
                	for(String yearString : block)
                	{
                		executeTest(yearString);
                	}
                }
            }
        }
        catch (FileNotFoundException exception)
        {
            exception.printStackTrace();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    /**
     * Read in the full test block.
     *
     * @param reader the reader to use to read in the lines
     * @return the full test block, stops at a test marker, blank line or end of file
     */
    private static List<String> readTestBlock(BufferedReader reader)
    {
        try
        {
            // Read in the number of lines for this test.
            int lineCount = Integer.parseInt(reader.readLine());

            // Keep reading until we find a stopping point.
            List<String> block = new ArrayList<>();
            for (int i = 0; i < lineCount; ++i)
            {
                String line = reader.readLine();
                block.add(line);
            }
            return block;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Execute a test block.
     *
     * @param test the test to execute
     */
    private static void executeTest(String test)
    {
        try
        {
            long year = Long.parseLong(test);
            if ((year >= 1582) && ((year % 4) == 0) && (((year % 100) != 0) || ((year % 400) == 0)))
            {
                System.out.println("Yes");
            }
            else
            {
                System.out.println("No");
            }
        }
        catch (NumberFormatException exception)
        {
            System.out.println(test + " is not a year!");
        }
    }
}
