#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob02.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int vowelCount = 0;

	// execute test cases
	while(testCases > 0){
		// reset vowel count
		vowelCount = 0;

		// get the next character
		char letter = fgetc(inputFile);
		// until we hit a newline
		while(letter != '\n'){
			// count the vowels - a, e, i, o, u
			if(letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u'){
				vowelCount++;
			}
			// get the next character
			letter = fgetc(inputFile);
		}

		// print the count
		printf("%d\n", vowelCount);

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
