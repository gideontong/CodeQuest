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
public class Monograms
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "Monograms.in.txt";

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
                // Go through each test block and process it.
                for (int i = 0; i < numberOfTests; i++)
                {
                    // Read in a test block.
                    List<String> block = readTestBlock(reader);

                    // Execute the test block.
                    executeTest(block);
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
     * @param block the test block to execute
     */
    private static void executeTest(List<String> block)
    {
        // Print out the monograms.
        for (String line : block)
        {
            String[] names = line.split(" ");
            String firstInitial = String.valueOf(names[0].toUpperCase().charAt(0));
            String middleInitial = String.valueOf(names[1].toUpperCase().charAt(0));
            String lastInitial = String.valueOf(names[2].toUpperCase().charAt(0));
            System.out.println(firstInitial + lastInitial + middleInitial);
        }
    }
}
