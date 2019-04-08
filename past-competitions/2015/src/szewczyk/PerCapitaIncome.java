import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Christopher Szewczyk
 */
public class PerCapitaIncome
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "PerCapitaIncome.in.txt";

    private static String s_country = "";

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
            // Read in the country's name.
            s_country = reader.readLine();

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
        // Gather up the per capita incomes for each year.
        SortedMap<Integer, Integer> perCapitas = new TreeMap<Integer, Integer>();
        for (String line : block)
        {
            try
            {
                String[] perCapitaYears = line.split(" ");
                Double perCapita = Double.parseDouble(perCapitaYears[0]);
                Integer year = Integer.parseInt(perCapitaYears[1]);

                int perCapita1k = (int)(perCapita / 1000.0);
                if ((perCapita - (perCapita1k * 1000)) >= 500)
                {
                    perCapita1k++;
                }
                perCapitas.put(year, perCapita1k);
            }
            catch (NumberFormatException exception)
            {
                exception.printStackTrace();
            }
        }

        // Output the results.
        System.out.println(s_country + ":");
        for (Entry<Integer, Integer> entry : perCapitas.entrySet())
        {
            System.out.print(entry.getKey() + " ");
            for (int i = 0; i < entry.getValue(); ++i)
            {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
