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
public class HammingCode
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "HammingCode.in.txt";

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
                	for(String binary : block)
                	{
                		calculateCode(binary);
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

    private static void calculateCode(String binary)
    {
        int numParity = getNumParity(binary.length());
        int[] parityBits = new int[numParity];
        for (int i = 0; i < numParity; ++i)
        {
            parityBits[i] = 1 << i;
        }

        int[] bits = new int[binary.length() + numParity];
        for (int i = 0, j = 0; i < bits.length; ++i)
        {
            if (!isParity(parityBits, i + 1))
            {
                bits[i] = Integer.parseInt(String.valueOf(binary.charAt(j)));
                j++;
            }
        }

        for (int i = 0; i < parityBits.length; ++i)
        {
            int currentParity = parityBits[i];
            bits[currentParity - 1] = calculateParity(bits, parityBits, currentParity);
        }

        printBits(bits);
    }

    private static int getNumParity(int numberOfBits)
    {
        int parityBits = 1;
        int paritySupported = 0;
        while (paritySupported < numberOfBits)
        {
            paritySupported += (int)Math.pow(2, parityBits) - 1;
            parityBits++;
        }
        return parityBits;
    }

    private static boolean isParity(int[] parityBits, int bit)
    {
        for (int i = 0; i < parityBits.length; ++i)
        {
            if (parityBits[i] == bit)
            {
                return true;
            }
        }
        return false;
    }

    private static int calculateParity(int[] bits, int[] parityBits, int parity)
    {
        int count = 0;
        for (int i = 0; i < bits.length; ++i)
        {
            if ((bits[i] == 1) && !isParity(parityBits, i + 1) && (((i + 1) & parity) >= 1))
            {
                count++;
            }
        }
        return count % 2;
    }

    private static void printBits(int[] bits)
    {
        for (int i = 0; i < bits.length; ++i)
        {
            System.out.print(String.valueOf(bits[i]));
        }
        System.out.println();
    }
}
