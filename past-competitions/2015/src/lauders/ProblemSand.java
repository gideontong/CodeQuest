package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class ProblemSand {
	private static PrintWriter m_writer;
	private static final boolean PRINT_TO_FILE = false; // for debugging

	public static void main(String[] args) {
		try {
			// Match input file to correct problem
			final String FILE = "\\GrainsOfSand.in.txt";
			final String OUTPUT = "\\GrainsOfSand.out.txt";

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

			int testCases, counts;

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
			BigInteger sandCount;

			for (int i = 0; i < testCases; i++) {
				line = br.readLine();
				
				sandCount = new BigInteger("0");
				
				try
				{
					counts = Integer.parseInt(line);
					
					for(int c = 0; c < counts; c++)
					{
						line = br.readLine();
						sandCount = sandCount.add(new BigInteger(line));
					}
					
					println(sandCount.toString());
				}
				catch (NumberFormatException e)
				{
					println("error");
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
