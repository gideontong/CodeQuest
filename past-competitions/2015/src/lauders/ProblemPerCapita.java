package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ProblemPerCapita {
	private static PrintWriter m_writer;
	private static final boolean PRINT_TO_FILE = false; // for debugging

	public static void main(String[] args) {
		try {
			// Match input file to correct problem
			final String FILE = "\\PerCapitaIncome.in.txt";
			final String OUTPUT = "\\PerCapitaIncome.out.txt";

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

			int testCases, numData;

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
			String region;
			String[] input;
			Map<Integer, Double> perCapitaMap = new HashMap<Integer, Double>();

			for (int i = 0; i < testCases; i++) {
				line = br.readLine();
				region = line;

				perCapitaMap.clear();

				line = br.readLine();

				try {
					numData = Integer.parseInt(line);

					for (int n = 0; n < numData; n++) {
						line = br.readLine();

						input = line.split(" ");
						Double income = new Double(input[0]);

						if (income % 1000.0 >= 500.0) {
							income += 500.0;
						}

						perCapitaMap.put(new Integer(input[1]), income);
					}

					Map<Integer, Double> treeMap = new TreeMap<Integer, Double>(
							perCapitaMap);

					println(region + ":");

					for (Entry<Integer, Double> entry : treeMap.entrySet()) {
						print(entry.getKey() + " ");

						Double income = entry.getValue();

						while (income > 1000.0) {
							print("*");
							income -= 1000.0;
						}
						println();
					}

				} catch (NumberFormatException e) {
					System.out.println("error");
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
