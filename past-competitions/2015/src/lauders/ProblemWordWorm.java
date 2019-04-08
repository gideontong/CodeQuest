package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ProblemWordWorm {
	private static PrintWriter m_writer;
	private static final boolean PRINT_TO_FILE = false; // for debugging

	public static void main(String[] args) {
		try {
			// Match input file to correct problem
			final String FILE = "\\WordWorm.in.txt";
			final String OUTPUT = "\\WordWorm.out.txt";

			final String SAMPLE = "Sample Data";
			final String JUDGING = "Judging Data";
			final String CURRENT_DIR = JUDGING; // sample or judging dir
			final String DIR = new File(".").getAbsolutePath() + "\\src\\"
					+ CURRENT_DIR;
			final String OUT_DIR = new File(".").getAbsolutePath()
					+ "\\src\\Output";

			FileReader fr = new FileReader(DIR + FILE);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			if (PRINT_TO_FILE)
			{
				m_writer = new PrintWriter(OUT_DIR + OUTPUT, "UTF-8");
			}

			int testCases, words;
			int row, col;
			String[][] puzzle;

			try {
				// get number of test cases
				testCases = Integer.parseInt(line);
			} catch (NumberFormatException e) {
				// debug use - only print if input file has a problem, or
				// parsing problem
				println("Test Case Error");
				return;
			}

			// Data used to solve problem
			ArrayList<String> image = new ArrayList<String>();

			for (int i = 0; i < testCases; i++) {
				line = br.readLine();
				String[] split = line.split(" ");

				try {
					row = Integer.parseInt(split[0]);
					col = Integer.parseInt(split[1]);

					puzzle = new String[row][col];

					for (int r = 0; r < row; r++) {
						line = br.readLine();

						puzzle[r] = line.split(" ");
					}

					line = br.readLine();
					words = Integer.parseInt(line);

					for (int w = 0; w < words; w++) {
						line = br.readLine();

						if (searchPuzzle(line, puzzle, row, col)) {
							println(line);
						}
					}

				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
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

	private static boolean searchPuzzle(String word, String[][] puzzle,
			int maxRow, int maxCol) {
		for (int row = 0; row < maxRow; row++) {
			for (int col = 0; col < maxCol; col++) {
				if (findWord(word, 0, puzzle, row, col, maxRow, maxCol)) {
					return true;
				}
			}
		}

		return false;
	}

	private static boolean findWord(String word, int index, String[][] puzzle,
			int row, int col, int maxRow, int maxCol) {
		if (index == word.length()) {
			return true;
		}

		boolean up = false, down = false, left = false, right = false, upLeft = false, upRight = false, downLeft = false, downRight = false;

		if (puzzle[row][col].equals("" + word.charAt(index))) {
			// up
			if (row + 1 < maxRow) {
				up = findWord(word, index + 1, puzzle, row + 1, col, maxRow,
						maxCol);
			}

			// down
			if (row > 0) {
				down = findWord(word, index + 1, puzzle, row - 1, col, maxRow,
						maxCol);
			}

			// left
			if (col > 0) {
				left = findWord(word, index + 1, puzzle, row, col - 1, maxRow,
						maxCol);
			}

			// right
			if (col + 1 < maxCol) {
				right = findWord(word, index + 1, puzzle, row, col + 1, maxRow,
						maxCol);
			}
			
			// up left
			if(row + 1 < maxRow && col > 0)
			{
				upLeft = findWord(word, index + 1, puzzle, row + 1, col - 1, maxRow,
						maxCol);
			}
			
			// up right
			if(row + 1 < maxRow && col + 1 < maxCol)
			{
				upRight = findWord(word, index + 1, puzzle, row + 1, col + 1, maxRow,
						maxCol);
			}
			
			// down left
			if(row > 0 && col > 0)
			{
				downLeft = findWord(word, index + 1, puzzle, row - 1, col - 1, maxRow,
						maxCol);
			}
			
			// down right
			if(row > 0 && col + 1 < maxCol)
			{
				downRight = findWord(word, index + 1, puzzle, row - 1, col + 1, maxRow,
						maxCol);
			}
		}

		return up || down || left || right || upLeft || upRight || downLeft || downRight;
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
