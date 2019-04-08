import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Christopher Szewczyk
 */
public class CSI
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "CSI.in.txt";

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
                    // Read in the DNA sequence.
                    String dna = reader.readLine();

                    // Read in a test block.
                    List<String> block = readTestBlock(reader);

                    // Execute the test block.
                    executeTest(dna, block);
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
     * @param dna the current DNA sequence being tested
     * @param block the test block to execute
     */
    private static void executeTest(String dna, List<String> block)
    {
        // Split out everyone's name and DNA sequence.
        Map<String, String> peopleDNA = new HashMap<String, String>();
        for (String line : block)
        {
            String[] lineParts = line.split("=");
            peopleDNA.put(lineParts[0], lineParts[1]);
        }

        // Find the longest sequence between the test DNA and everyone's DNA.
        Map<String, String> longestSequences = new TreeMap<String, String>();
        int longestSequenceLength = 0;
        // System.out.println("Testing against " + dna + "...");
        for (Entry<String, String> entry : peopleDNA.entrySet())
        {
            // System.out.println("   " + entry.getKey() + " - " + entry.getValue());
            String longestSequence = getLongestSequence(dna, entry.getValue());
            longestSequences.put(entry.getKey(), longestSequence);
            longestSequenceLength = Math.max(longestSequenceLength, longestSequence.length());
            // System.out.println("      " + longestSequence + "," + longestSequence.length());
        }

        // Print out the results.
        boolean first = true;
        for (Entry<String, String> entry : longestSequences.entrySet())
        {
            if (entry.getValue().length() == longestSequenceLength)
            {
                if (!first)
                {
                    System.out.print(",");
                }
                System.out.print(entry.getKey());
                // System.out.print(entry.getKey() + "[" + entry.getValue() + "," + entry.getValue().length() + "]");
                first = false;
            }
        }
        System.out.println();
    }

    private static String getLongestSequence(String dna1, String dna2)
    {
        return getLongestSequence(dna1, 0, dna2, 0, "", "");
    }

    private static String getLongestSequence(String dna1,
                                             int dna1Offset,
                                             String dna2,
                                             int dna2Offset,
                                             String currentSequence,
                                             String longestSequence)
    {
        for (int i = dna2Offset; i < dna2.length(); ++i)
        {
            int nextDNA1Offset = dna1.indexOf(dna2.charAt(i), dna1Offset);
            if (nextDNA1Offset >= 0)
            {
                String newSequence = currentSequence + dna2.charAt(i);
                if (newSequence.length() > longestSequence.length())
                {
                    longestSequence = newSequence;
                }

                int dna1Remaining = dna1.length() - (nextDNA1Offset + 1);
                int dna2Remaining = dna2.length() - (i + 1);
                int sequenceDifference = longestSequence.length() - newSequence.length();
                if ((dna1Remaining > 0) && (dna2Remaining > 0) && (dna1Remaining > sequenceDifference))
                {
                    newSequence = getLongestSequence(dna1, nextDNA1Offset + 1, dna2, i + 1, newSequence,
                            longestSequence);
                }
                if (newSequence.length() > longestSequence.length())
                {
                    longestSequence = newSequence;
                }
            }
        }
        return longestSequence;
    }
}
