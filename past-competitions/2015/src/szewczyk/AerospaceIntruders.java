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
public class AerospaceIntruders
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "AerospaceIntruders.in.txt";

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
        // Create all the ships.
        List<Ship> ships = new ArrayList<Ship>(block.size());
        for (String line : block)
        {
            ships.add(new Ship(line));
        }

        // Shoot all of the ships.
        while (!ships.isEmpty())
        {
            // Find the closest ship and shoot it.
            Ship closestShip = getClosestShip(ships);
            System.out.println("Destroyed Ship: " + closestShip.m_name + " xLoc: " + closestShip.m_x);
            ships.remove(closestShip);

            // Advance all the ships.
            for (Ship ship : ships)
            {
                ship.advance();
            }
        }
    }

    private static Ship getClosestShip(List<Ship> ships)
    {
        Ship closestShip = ships.get(0);
        for (int i = 1; i < ships.size(); ++i)
        {
            Ship newShip = ships.get(i);
            if ((newShip.m_x < closestShip.m_x)
                    || ((newShip.m_x == closestShip.m_x) && (newShip.m_y > closestShip.m_y)))
            {
                closestShip = newShip;
            }
        }
        return closestShip;
    }

    private static enum ShipClass
    {
        A(10), B(20), C(30);

        private int m_speed;

        private ShipClass(int speed)
        {
            m_speed = speed;
        }

        public int getSpeed()
        {
            return m_speed;
        }
    }

    private static class Ship
    {
        private static final Pattern SHIP_PATTERN = Pattern.compile("(\\w+)_([ABC]):(\\d+),(\\d+)");
        public String m_name;
        public ShipClass m_class;
        public int m_x;
        public int m_y;

        public Ship(String shipString)
        {
            Matcher matcher = SHIP_PATTERN.matcher(shipString);
            if (!matcher.matches())
            {
                System.out.println("Illegal ship string: " + shipString);
                m_name = "badship";
                m_class = ShipClass.A;
            }
            else
            {
                m_name = matcher.group(1);
                m_class = ShipClass.valueOf(matcher.group(2));
                m_x = Integer.parseInt(matcher.group(3));
                m_y = Integer.parseInt(matcher.group(4));
            }
        }

        public void advance()
        {
            m_x -= m_class.getSpeed();
        }
    }
}
