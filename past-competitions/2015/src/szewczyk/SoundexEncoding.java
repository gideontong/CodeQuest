import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Christopher Szewczyk
 */
public class SoundexEncoding
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "Soundex.in.txt";

    private static final List<List<String>> s_groups = new ArrayList<>(6);

    private static final List<String> s_wild = new ArrayList<>(Arrays.asList(new String[] {"h", "w"}));

    private static final List<String> s_vowels = new ArrayList<>(Arrays.asList(new String[] {"a", "e", "i", "o", "u",
    "y"}));

    private static Map<String, Integer> s_processedSoundex = new TreeMap<>();

    static
    {
        s_groups.add(Arrays.asList(new String[] {"b", "f", "p", "v"}));
        s_groups.add(Arrays.asList(new String[] {"c", "g", "j", "k", "q", "s", "x", "z"}));
        s_groups.add(Arrays.asList(new String[] {"d", "t"}));
        s_groups.add(Arrays.asList(new String[] {"l"}));
        s_groups.add(Arrays.asList(new String[] {"m", "n"}));
        s_groups.add(Arrays.asList(new String[] {"r"}));
    }

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
                    s_processedSoundex.clear();
                    System.out.println("OUTPUT");
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
        // Go through each line in the test block.
        for (String line : block)
        {
            String encodedString = String.valueOf(line.charAt(0)).toUpperCase();
            int previousGroup = -1;
            for (int i = 0; i < line.length(); ++i)
            {
                String character = String.valueOf(line.charAt(i));
                int currentGroup = getGroup(character);
                if (currentGroup == 7 && previousGroup != -1)
                {
                	currentGroup = previousGroup;
                }
                if(i > 0 && currentGroup != previousGroup && currentGroup >= 1 && currentGroup <= 6)
                {
                    encodedString += String.valueOf(currentGroup);
                }
                previousGroup = currentGroup;
            }

            if (encodedString.length() > 4)
            {
                encodedString = encodedString.substring(0, 4);
            }

            while (encodedString.length() < 4)
            {
                encodedString += "0";
            }

            Integer count = s_processedSoundex.get(encodedString);
            s_processedSoundex.put(encodedString, count == null ? 1 : count + 1);
        }

        for (String encodedString : s_processedSoundex.keySet())
        {
            System.out.println(encodedString + " " + s_processedSoundex.get(encodedString));
        }
    }

    private static int getGroup(String character)
    {
        character = character.toLowerCase();
        if (s_wild.contains(character))
        {
            return 7;
        }
        else
        {
            for (int i = 0; i < s_groups.size(); ++i)
            {
                if (s_groups.get(i).contains(character))
                {
                    return i + 1;
                }
            }
        }
        return -1;
    }
}
