package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ProblemReflection {
	private static PrintWriter m_writer;
	private static final boolean PRINT_TO_FILE = false; // for debugging

	public static void main(String[] args) {
		try {
			// Match input file to correct problem
			final String FILE = "\\Reflection.in.txt";
			final String OUTPUT = "\\Reflection.out.txt";

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

			int testCases;
			int lines;

			try {
				// get number of test cases
				testCases = Integer.parseInt(line);
			} catch (NumberFormatException e) {
				// debug use - only print if input file has a problem, or
				// parsing problem
				System.out.println("Test Case Error");
				return;
			}

			// Data used to solve problem
			ArrayList<String> image = new ArrayList<String>();

			for (int i = 0; i < testCases; i++) {
				try {
					image.clear();
					line = br.readLine();
					lines = Integer.parseInt(line);
					
					int maxY = 0, maxX;

					for (int j = 0; j < lines; j++) {
						line = br.readLine();
						image.add(line);
						
						if (maxY < line.length()) {
							maxY = line.length();
						}
					}
					
					maxX = image.size();
					
					// create a box of image with spaces
					String[][] imageBox = new String[maxX][maxY];
					for(int row = 0; row < maxX; row++)
					{
						for(int col = 0; col < maxY; col++)
						{
							imageBox[row][col] = " ";
						}
					}
					
					// add image to box
					for(int row = 0; row < maxX; row++)
					{
						for(int col = 0; col < image.get(row).length(); col++)
						{
							imageBox[row][col] = image.get(row).charAt(col) + "";
						}
					}
					
					String flip = br.readLine();

					if(flip.equals("X"))
					{
						for(int row = maxX - 1; row > -1; row--)
						{
							for(int col = 0; col < maxY; col++)
							{
								print(imageBox[row][col]);
							}
							println();
						}
					}else if(flip.equals("Y"))
					{
						for(int row = 0; row < maxX; row++)
						{
							for(int col = maxY - 1; col > -1 ; col--)
							{
								print(imageBox[row][col]);
							}
							println();
						}
					}else if(flip.equals("INVERSE"))
					{
						for(int col = 0; col < maxY; col++)
						{
							for(int row = 0; row < maxX; row++)
							{
								print(imageBox[row][col]);
							}
							println();
						}
					}
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
