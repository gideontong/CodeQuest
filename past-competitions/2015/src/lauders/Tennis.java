package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Tennis {
	private static PrintWriter m_writer;
	private static final boolean PRINT_TO_FILE = false; // for debugging

	public static void main(String[] args) {
		try {
			// Match input file to correct problem
			final String FILE = "\\Tennis.in.txt";
			final String OUTPUT = "\\Tennis.out.txt";

			final String SAMPLE = "Sample Data";
			final String JUDGING = "Judging Data";
			final String CURRENT_DIR = JUDGING; // sample or judging dir
			final String DIR = new File(".").getAbsolutePath() + "\\src\\"
					+ CURRENT_DIR;
			final String OUT_DIR = new File(".").getAbsolutePath()
					+ "\\src\\Output";

			FileReader fr = new FileReader(DIR + FILE);
			BufferedReader br = new BufferedReader(fr);
			String line;

			if (PRINT_TO_FILE)
			{
				m_writer = new PrintWriter(OUT_DIR + OUTPUT, "UTF-8");
			}

			// Data used to solve problem
			boolean gameStart = true;
			int playerOne = 0;
			int playerTwo = 0;
			int point;

			final String[] OUTPUT_SCORE = { "love", "15", "30", "40" };

			// each game
			while ((line = br.readLine()) != null) {
				// Reset data
				if (gameStart) {
					println("Game start");
					gameStart = false;
					playerOne = 0;
					playerTwo = 0;
				}

				// ================== Calculate ========================
				try {
					point = Integer.parseInt(line);

					if (point == 1) {
						playerOne++;
					} else {
						playerTwo++;
					}

					// ================== RESULTS ==========================
					if (playerOne == playerTwo) {
						if (playerOne == 1) {
							println(OUTPUT_SCORE[playerOne] + "-all");
						} else if (playerOne == 2) {
							println(OUTPUT_SCORE[playerOne] + "-all");
						} else {
							println("deuce");
						}
					} else if (playerOne > 3 || playerTwo > 3) {
						if (playerOne >= playerTwo + 2) {
							println("Game Player 1");
							gameStart = true;
						} else if (playerOne + 2 <= playerTwo) {
							println("Game Player 2");
							gameStart = true;
						} else if (playerOne == playerTwo + 1) {
							println("Advantage Player 1");
						} else {
							println("Advantage Player 2");
						}
					} else {
						println(OUTPUT_SCORE[playerOne] + "-"
								+ OUTPUT_SCORE[playerTwo]);
					}

				} catch (Exception e) {

				}
			}

			if (PRINT_TO_FILE)
			{
				m_writer.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void println() {
		println("");
	}

	private static void println(String out) {
		System.out.println(out);
		if (PRINT_TO_FILE)
		{
			m_writer.println(out);
		}
	}

	private static void print(String out) {
		System.out.print(out);
		if (PRINT_TO_FILE)
		{
			m_writer.print(out);
		}
	}
}
