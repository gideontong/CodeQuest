#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char *argv[])
{
	// open file for reading
	FILE *inputFile = fopen("Prob07.in.txt", "r");
	int testCases;

	// read number of test cases
	fscanf(inputFile, "%d\n", &testCases);
	
	int numLines = 0;
	int allPalindromes = 1;
	int stringLength = 0;
	char string[1024];

	// execute test cases
	while(testCases > 0){
		fscanf(inputFile, "%d\n", &numLines);
		// 1 == true; if set to 0, something failed
		allPalindromes = 1;

		// for each line...
		for(int lineNum = 1; lineNum <= numLines; lineNum++){
			// read it in (1024 characters is deliberately excessive)
			fgets(string, 1024, inputFile);

			// determine the string's actual length
			stringLength = strlen(string) - 1;
			// subtract 1 to account for the newline character

			for(int i = 0; i < stringLength / 2; i++){
				// pull characters from both ends and compare
				char char1 = string[i];
				char char2 = string[stringLength - 1 - i];
				// convert lowercase letters to uppercase
				// 97 == a, 122 == z, 65 == A, 90 == Z
				// 97 - 65 = 32, so 'a' - 32 = 'A'
				if(char1 >= 97 && char1 <= 122){
					char1 = char1 - 32;
				}
				if(char2 >= 97 && char2 <= 122){
					char2 = char2 - 32;
				}
				// if they don't match, it's not a palindrome
				if(char1 != char2){
					if(allPalindromes == 0){
						// if something else had already failed, just print the line number
						printf(", %d", lineNum);
					}
					else{
						// otherwise, print "False" and start the list
						allPalindromes = 0;
						printf("False - %d", lineNum);
					}
					break;
				}
			}
		}

		if(allPalindromes == 1){
			// if everything passed, print True
			printf("True");
		}
		// newline
		printf("\n");

		testCases = testCases - 1;
	}

	// close the file
	fclose(inputFile);
}
