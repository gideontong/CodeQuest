#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob15.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	// 1024 is deliberately excessive
	char string[1024];
	int stringLength = 0;

	// execute test cases
	while(testCases > 0){
		// get the string until we hit a newline
		fgets(string, 1024, inputFile);
		// determine the actual length of the string
		// don't count the newline itself
		stringLength = strlen(string) - 1;

		for(int i = 0; i < stringLength; i++){
			// read the string one character at a time
			char letter = string[i];
			// convert to 1, 2, 3,... 26
			// A == 65, so 'A' - 64 = 1
			letter = letter - 64;
			int originalValue = letter;

			if(letter <= 5){
				// A-E = Add 6
				letter = letter + 6;
			}
			else if(letter <= 10){
				// F-J = Square
				letter = letter * letter;
			}
			else if(letter <= 15){
				// K-O = Mod by 3, Multiply by 5, Add 1
				letter = ((letter % 3) * 5) + 1;
			}
			else if(letter <= 20){
				// P-T = Sum of digits times 8
				int sum = (letter / 10) + (letter % 10);
				letter = sum * 8;
			}
			else{
				// U-Z = Double largest integer factor
				int bigFactor = 0;
				for(int factor = letter / 2; factor > 0; factor--){
					if(letter % factor == 0){
						bigFactor = factor;
						break;
					}
				}
				letter = bigFactor * 2;
			}

			// pull back within 1-26 range
			while(letter > 26){
				letter = letter - 26;
			}
			// if result is 0, use original value
			if(letter == 0){
				letter = originalValue;
			}

			// convert back to A, B, C,... Z for printing
			letter = letter + 64;
			printf("%c", letter);
		}
		printf("\n");

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
