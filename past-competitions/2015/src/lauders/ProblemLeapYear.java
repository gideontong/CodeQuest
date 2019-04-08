package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ProblemLeapYear {
	private static PrintWriter m_writer;
	private static final boolean PRINT_TO_FILE = false; // for debugging

	public static void main(String[] args) {
		try {
			// Match input file to correct problem
			final String FILE = "\\LeapYear.in.txt";
			final String OUTPUT = "\\LeapYear.out.txt";

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

			int testCases, years;

			// Data used to solve problem
			int year;

			try {
				// get number of test cases
				testCases = Integer.parseInt(line);
				
				for(int c = 0; c < testCases; c++)
				{
					years = Integer.parseInt(br.readLine());
					
					for(int y = 0; y < years; y++)
					{
						year = Integer.parseInt(br.readLine());
						
						if (year > 1582) {
							if (year % 4 != 0) {
								println("No");
							} else if (year % 100 != 0) {
								println("Yes");
							} else if (year % 400 != 0) {
								println("No");
							} else {
								println("Yes");
							}
						} else {
							println("No");
						}
					}
				}
			} catch (NumberFormatException e) {
				// debug use - only print if input file has a problem, or
				// parsing problem
				println("Test Case Error");
				return;
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
