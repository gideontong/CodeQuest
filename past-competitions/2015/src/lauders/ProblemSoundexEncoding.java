package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ProblemSoundexEncoding {
	private static PrintWriter m_writer;
	private static final boolean PRINT_TO_FILE = false; // for debugging

	public static void main(String[] args) {
		try {
			// Match input file to correct problem
			final String FILE = "\\Soundex.in.txt";
			final String OUTPUT = "\\Soundex.out.txt";

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

			if (PRINT_TO_FILE) {
				m_writer = new PrintWriter(OUT_DIR + OUTPUT, "UTF-8");
			}

			int testCases, names;

			// Data used to solve problem
			String code;

			final String GROUP[] = { "", "bfpv", "cgjkqsxz", "dt", "l", "mn",
					"r", "hw", "aeiouy" }; // 0 is a blank - not used

			HashMap<String, Integer> map = new HashMap<String, Integer>();

			try {
				// get number of test cases
				testCases = Integer.parseInt(line);

				for (int c = 0; c < testCases; c++) {
					map.clear();
					println("OUTPUT");

					names = Integer.parseInt(br.readLine());
					for (int n = 0; n < names; n++) {
						line = br.readLine();
						line = line.toLowerCase();

						// combine groups
						String tempString = "";
						char letter = line.charAt(0);
						tempString += letter + "";

						for (int l = 1; l < line.length(); l++) {
							for (int g = 1; g <= 6; g++) {
								if ((GROUP[g].contains("" + line.charAt(l))
										&& !GROUP[g].contains("" + letter)
										&& !GROUP[7].contains("" + line.charAt(l)))
										|| GROUP[8].contains("" + line.charAt(l))) {
									letter = line.charAt(l);
									tempString += "" + letter;
									break;
								}
							}
						}
						line = tempString;

						// first letter
						code = ("" + line.charAt(0)).toUpperCase();
						line = line.substring(1);

						// remove vowels
						line = line.replaceAll("[" + GROUP[8] + "]", "");

						// remove wildcards
						line = line.replaceAll("[" + GROUP[7] + "]", "");

						// replace groups
						line = line.replaceAll("[" + GROUP[1] + "]", "1");
						line = line.replaceAll("[" + GROUP[2] + "]", "2");
						line = line.replaceAll("[" + GROUP[3] + "]", "3");
						line = line.replaceAll("[" + GROUP[4] + "]", "4");
						line = line.replaceAll("[" + GROUP[5] + "]", "5");
						line = line.replaceAll("[" + GROUP[6] + "]", "6");

						code += line;

						// add zeros or trim
						if (code.length() > 4) {
							code = code.substring(0, 4);
						} else {
							while (code.length() < 4) {
								code += "0";
							}
						}

						// count
						if (map.containsKey(code)) {
							map.put(code, map.get(code) + 1);
						} else {
							map.put(code, 1);
						}
					}

					// sort and print
					Map<String, Integer> treeMap = new TreeMap<String, Integer>(
							map);

					for (Entry<String, Integer> entry : treeMap.entrySet()) {
						println(entry.getKey() + " " + entry.getValue());
					}
				}
			} catch (NumberFormatException e) {
				// debug use - only print if input file has a problem, or
				// parsing problem
				println("Test Case Error");
				return;
			}

			if (PRINT_TO_FILE) {
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
		if (PRINT_TO_FILE) {
			m_writer.println(out);
		}
	}

	private static void print(String out) {
		System.out.print(out);
		if (PRINT_TO_FILE) {
			m_writer.print(out);
		}
	}
}
