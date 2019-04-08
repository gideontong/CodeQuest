import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Christopher Szewczyk
 */
public class TennisGame
{
    /** The file that needs to be read in. */
    private static final String INPUT_FILE_PATH = "TennisGame.in.txt";

    private static int[] s_score = new int[2];

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
            // Read all the serve results.
            boolean newGame = true;
            for (String line = reader.readLine(); line != null; line = reader.readLine())
            {
                if (newGame)
                {
                    System.out.println("Game start");
                    s_score[0] = 0;
                    s_score[1] = 0;
                }
                newGame = addScore(Integer.parseInt(line));
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

    private static boolean addScore(int player)
    {
        boolean gameWon = false;
        s_score[player - 1]++;
        if ((s_score[0] > 3) || (s_score[1] > 3))
        {
            if (((s_score[0] - s_score[1]) >= 2) || ((s_score[1] - s_score[0]) >= 2))
            {
                System.out.println("Game Player " + (s_score[0] > s_score[1] ? "1" : "2"));
                gameWon = true;
            }
            else if (s_score[0] == s_score[1])
            {
                System.out.println("deuce");
            }
            else
            {
                System.out.println("Advantage Player " + (s_score[0] > s_score[1] ? "1" : "2"));
            }
        }
        else if ((s_score[0] == 3) && (s_score[1] == 3))
        {
            System.out.println("deuce");
        }
        else
        {
            System.out.println(getScoreString(s_score[0]) + "-"
                    + (s_score[0] == s_score[1] ? "all" : getScoreString(s_score[1])));
        }
        return gameWon;
    }

    private static String getScoreString(int score)
    {
        if (score == 0)
        {
            return "love";
        }
        else if (score == 1)
        {
            return "15";
        }
        else if (score == 2)
        {
            return "30";
        }
        else if (score == 3)
        {
            return "40";
        }
        return "";
    }
}
