import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Christopher Szewczyk
 */
public class SkyScraper
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "SkyScraper.in.txt";

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
        // Create all the blocks.
        List<Block> blocks = new ArrayList<Block>(block.size());
        for (String line : block)
        {
            blocks.add(new Block(line));
        }

        // Go through all the permutations of the blocks to find the longest height.
        int longestHeight = getLongestHeight(Collections.<Block>emptyList(), blocks);
        System.out.println(longestHeight);
    }

    private static int getLongestHeight(List<Block> skyScraper, List<Block> remainingBlocks)
    {
        // Check to see if there are any remaining blocks.
        if (remainingBlocks.isEmpty())
        {
            return getHeight(skyScraper);
        }

        Block base = skyScraper.isEmpty() ? null : skyScraper.get(skyScraper.size() - 1);
        int longestHeight = getHeight(skyScraper);
        for (int i = 0; i < remainingBlocks.size(); ++i)
        {
            List<Block> newRemainingBlocks = new ArrayList<Block>(remainingBlocks);
            Block test = newRemainingBlocks.remove(i);
            List<Block> newSkyScraper = new ArrayList<Block>(skyScraper);
            newSkyScraper.add(test);
            
            for (int rotation = 0; rotation < 3; ++rotation)
            {
                test.setRotation(rotation);
                if (test.canSitOn(base))
                {
                    longestHeight = Math.max(longestHeight, getLongestHeight(newSkyScraper, newRemainingBlocks));
                }
            }
        }
        return longestHeight;
    }
    
    private static int getHeight(List<Block> blocks)
    {
        int height = 0;
        for (Block block : blocks)
        {
            height += block.m_height;
        }
        return height;
    }

    private static final Pattern DIMENSION_PATTERN = Pattern.compile("(\\d+)x(\\d+)x(\\d+)");

    private static class Block
    {

        public int[] m_dimensions = new int[3];
        public int[] m_base = new int[2];
        public int m_height;

        public Block(String dimensions)
        {
            Matcher matcher = DIMENSION_PATTERN.matcher(dimensions);
            if (matcher.matches())
            {
                m_dimensions[0] = Integer.parseInt(matcher.group(1));
                m_dimensions[1] = Integer.parseInt(matcher.group(2));
                m_dimensions[2] = Integer.parseInt(matcher.group(3));
            }
            else
            {
                System.out.println("Illegal block pattern: " + dimensions);
            }

            m_base[0] = m_dimensions[0];
            m_base[1] = m_dimensions[1];
            m_height = m_dimensions[2];
        }

        public void setRotation(int rotation)
        {
            m_base[0] = m_dimensions[rotation];
            m_base[1] = m_dimensions[(rotation + 1) % 3];
            m_height = m_dimensions[(rotation + 2) % 3];
        }

        public boolean canSitOn(Block base)
        {
            if (base == null)
            {
                return true;
            }
            boolean firstTest = base.m_base[0] >= m_base[0] && base.m_base[1] >= m_base[1];
            boolean secondTest = base.m_base[0] >= m_base[1] && base.m_base[1] >= m_base[0];
            return firstTest || secondTest;
        }
    }
}
